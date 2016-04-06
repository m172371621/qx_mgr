package com.brilliantreform.sc.sys.service;

import com.brilliantreform.sc.sys.po.SysJob;
import com.brilliantreform.sc.sys.utils.JobUtil;
import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 同步
 * */
@DisallowConcurrentExecution
public class SyncJobFactory implements Job{

    private static final Logger LOGGER = Logger.getLogger(SyncJobFactory.class);
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SysJob job = (SysJob) context.getMergedJobDataMap().get("sysJob");
        JobUtil.invokMethod(job);
    }

}
