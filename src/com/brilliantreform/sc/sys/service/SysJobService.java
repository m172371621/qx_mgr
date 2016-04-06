package com.brilliantreform.sc.sys.service;

import com.brilliantreform.sc.sys.dao.SysDao;
import com.brilliantreform.sc.sys.dao.SysJobDao;
import com.brilliantreform.sc.sys.enumerate.JobStatusEnum;
import com.brilliantreform.sc.sys.po.SysJob;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

//参考  http://snailxr.iteye.com/blog/2076903
@Service
public class SysJobService {
    private static final Logger LOGGER = Logger.getLogger(SysJobService.class);

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private SysJobDao sysJobDao;
    @Autowired
    private SysDao sysDao;

    public List<SysJob> findAllJob() {
        return sysJobDao.findAllJob();
    }

    public List<Map> searchSysJob(Map param) {
        return sysJobDao.searchSysJob(param);
    }

    public int searchSysJobCount(Map param) {
        return sysJobDao.searchSysJobCount(param);
    }

    public void addJob(SysJob job) throws SchedulerException {
        if(job == null || job.getStatus() == null || job.getStatus().intValue() != JobStatusEnum.ENABLE.getValue()) {
            return;
        }

        TriggerKey triggerKey = TriggerKey.triggerKey("job" + job.getObjid());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        // 不存在，创建一个
        if (null == trigger) {
            Class clazz = job.getIsSync() != null ? (job.getIsSync() == 0 ? AsyncJobFactory.class : SyncJobFactory.class) : SyncJobFactory.class;
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity("job" + job.getObjid()).build();

            jobDetail.getJobDataMap().put("sysJob", job);

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

            trigger = TriggerBuilder.newTrigger().withIdentity("job" + job.getObjid()).withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    /**
     * 暂停一个job
     * */
    public void pauseJob(int objid) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey("job" + objid);
        if(scheduler.checkExists(jobKey)) {
            scheduler.pauseJob(jobKey);
        }
    }

    /**
     * 恢复一个job
     * */
    public void resumeJob(int objid) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey("job" + objid);
        if(scheduler.checkExists(jobKey)) {
            scheduler.resumeJob(jobKey);
        }
    }

    /**
     * 删除一个job，并从任务列表中移除该任务
     * @param removeFlag 是否删除数据库中对应的数据
     * */
    public void removeJob(int objid, boolean removeFlag) throws SchedulerException {
        if(removeFlag) {
            sysDao.removeJob(objid);
        }
        JobKey jobKey = JobKey.jobKey("job" + objid);
        if(scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }
    }

    /**
     * 立即执行一个job
     * */
    public void runOnce(int objid) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey("job" + objid);
        if(scheduler.checkExists(jobKey)) {
            scheduler.triggerJob(jobKey);
        }
    }

    /**
     * 更新job时间表达式
     * */
    public void updateJobCron(int objid, String cronExpression) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey("job" + objid);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    /**
     * 校验表达式是否正确
     * */
    public boolean checkCronExpression(String cronExpression) {
        boolean flag = false;
        try {
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            if(scheduleBuilder != null) {
                flag = true;
            }
        } catch (Exception e) {}
        return flag;
    }

    public Trigger.TriggerState getJobStatus(int objid) throws SchedulerException {
        TriggerKey key = TriggerKey.triggerKey("job" + objid);
        return scheduler.getTriggerState(key);
    }
}
