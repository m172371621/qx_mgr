package com.brilliantreform.sc.sys.utils;

import com.brilliantreform.sc.sys.po.SysJob;
import com.brilliantreform.sc.sys.service.SysService;
import com.brilliantreform.sc.system.SpringContextHolder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.Trigger;

import java.lang.reflect.Method;

public class JobUtil {

    private static final Logger LOGGER = Logger.getLogger(JobUtil.class);

    /**
     * 执行任务
     * */
    public static void invokMethod(SysJob job) {
        /*SysService sysService = SpringContextHolder.getBean("sysService");
        SysJob _job = sysService.getJobById(job.getObjid());
        if(_job != null) {
            _job.setRunningStatus(Trigger.TriggerState.NORMAL.name());
            sysService.saveJob(_job);
        }*/

        Object object = null;
        Class clazz = null;
        if (StringUtils.isNotBlank(job.getBeanId())) {
            try {
                object = SpringContextHolder.getBean(job.getBeanId());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (object == null) {
            LOGGER.error("任务名称 = [" + job.getName() + "]---------------未启动成功，请检查是否配置正确！！！");
            return;
        }
        clazz = object.getClass();
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(job.getMethod());
        } catch (NoSuchMethodException e) {
            LOGGER.error("任务名称 = [" + job.getName() + "]---------------未启动成功，方法名设置错误！！！");
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (method != null) {
            try {
                /*if(_job != null) {
                    _job.setRunningStatus(Trigger.TriggerState.BLOCKED.name());
                    sysService.saveJob(_job);
                }*/

                method.invoke(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("任务名称 = [" + job.getName() + "]----------执行完毕");

        /*if(_job != null) {
            _job.setRunningStatus(Trigger.TriggerState.NORMAL.name());
            sysService.saveJob(_job);
        }*/
    }
}
