package com.brilliantreform.sc.sys.job;

import com.brilliantreform.sc.sys.enumerate.SendDetailStatusEnum;
import com.brilliantreform.sc.sys.enumerate.SendQueueStatusEnum;
import com.brilliantreform.sc.sys.po.SysSendDetail;
import com.brilliantreform.sc.sys.po.SysSendQueue;
import com.brilliantreform.sc.sys.service.SysService;
import com.brilliantreform.sc.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;
import java.util.Map;

/**
 * 系统发送消息定时任务
 * Created by shangwq on 2016/1/5.
 */
public class SendMsgJob {

    private static final Logger LOGGER = Logger.getLogger(SendMsgJob.class);

    @Autowired
    private SysService sysService;

    public void sendMsg() {
        try {
            //获取所有待发送的队列
            List<SysSendQueue> queueList = sysService.findWaitSendQueue(SendQueueStatusEnum.INIT.getValue());
            for(SysSendQueue queue : queueList) {
                try {
                    //立即修改该队列状态为发送中
                    queue.setStatus(SendQueueStatusEnum.SENDING.getValue());
                    sysService.saveSysSendQueue(queue);
                } catch (Exception e) {
                    //如果发送异常，则跳过该信息
                    LOGGER.error("sendMsg error", e);
                    continue;
                }

                try {
                    //获取待发送的队列明细
                    List<SysSendDetail> detailList = sysService.findSendDetail(queue.getObjid(), SendDetailStatusEnum.INIT.getValue());
                    for(SysSendDetail detail : detailList) {

                    }
                } catch (Exception e) {

                }
            }

        } catch (Exception e) {
            LOGGER.error("sendMsg job error", e);
        }
    }
}
