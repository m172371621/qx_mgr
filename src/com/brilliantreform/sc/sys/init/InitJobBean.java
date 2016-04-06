package com.brilliantreform.sc.sys.init;

import com.brilliantreform.sc.sys.po.SysJob;
import com.brilliantreform.sc.sys.service.SysJobService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 初始化动态定时任务
 * Created by shangwq on 2015/10/21.
 */
@Component
public class InitJobBean implements InitializingBean {

    private static final Logger LOGGER = Logger.getLogger(InitJobBean.class);

    @Autowired
    private SysJobService jobService;

   @Override
    public void afterPropertiesSet() throws Exception {

        // 这里获取任务信息数据
        List<SysJob> jobList = jobService.findAllJob();

        for (SysJob job : jobList) {
            jobService.addJob(job);
        }
    }

}
