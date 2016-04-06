package com.brilliantreform.sc.sys.service;

import com.brilliantreform.sc.confgure.po.Config_community;
import com.brilliantreform.sc.sys.dao.SysDao;
import com.brilliantreform.sc.sys.enumerate.MsgNotifyTypeEnum;
import com.brilliantreform.sc.sys.po.*;
import com.brilliantreform.sc.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysService {

    private static Logger LOGGER = Logger.getLogger(SysService.class);

    @Autowired
    private SysDao sysDao;

    public List<SysSetting> findAllSetting() {
        return sysDao.findAllSetting();
    }

    public List<SysDict> findAllDict() {
        return sysDao.findAllDict();
    }

    public List<Map> searchCommunityConfig(Map param) {
        return sysDao.searchCommunityConfig(param);
    }

    public int searchCommunityConfigCount(Map param) {
        return sysDao.searchCommunityConfigCount(param);
    }

    public void saveCommunityConfig(Config_community config, Integer community_id_old, String config_id_old) {
        sysDao.saveCommunityConfig(config, community_id_old, config_id_old);
    }

    public Config_community getCommunityConfigById(int community_id, String config_id) {
        return sysDao.getCommunityConfigById(community_id, config_id);
    }

    public void removeCommunityConfig(int community_id, String config_id) {
        sysDao.removeCommunityConfig(community_id, config_id);
    }

    public List<Map> searchSetting(Map param) {
        return sysDao.searchSetting(param);
    }

    public int searchSettingCount(Map param) {
        return sysDao.searchSettingCount(param);
    }

    public List<String> findSettingModules() {
        return sysDao.findSettingModules();
    }

    public List<Map> findMenuTree() {
        return sysDao.findMenuTree();
    }

    public List<Map> searchMenu(Map param) {
        return sysDao.searchMenu(param);
    }

    public int searchMenuCount(Map param) {
        return sysDao.searchMenuCount(param);
    }

    public SysMenu getMenuById(int objid) {
        return sysDao.getMenuById(objid);
    }

    public int getMaxMenuSort(int parentid) {
        return sysDao.getMaxMenuSort(parentid);
    }

    public void saveMenu(SysMenu menu) {
        sysDao.saveMenu(menu);
    }

    public void removeMenu(int objid) {
        sysDao.removeMenu(objid);
    }

    public SysJob getJobById(int objid) {
        return sysDao.getJobById(objid);
    }

    public void saveJob(SysJob job) {
        if(job != null) {
            if(job.getObjid() == null) {
                sysDao.insertJob(job);
            } else {
                sysDao.updateJob(job);
            }
        }
    }

    public void removeJob(int objid) {
        sysDao.removeJob(objid);
    }

    public List<SysSendQueue> findWaitSendQueue(Integer status) {
        return sysDao.findWaitSendQueue(status);
    }

    public void saveSysSendQueue(SysSendQueue queue) {
        if(queue != null) {
            if(queue.getObjid() == null) {
                sysDao.insertSysSendQueue(queue);
            } else {
                sysDao.updateSysSendQueue(queue);
            }
        }
    }

    public List<SysSendDetail> findSendDetail(int queue_id, Integer status) {
        return sysDao.findSendDetail(queue_id, status);
    }

    public SysMsgTemplate getMsgTemplateByCode(String templateCode) {
        return sysDao.getMsgTemplateByCode(templateCode);
    }

    public SysSetting getSettingById(int objid) {
        return sysDao.getSettingById(objid);
    }

    public void saveSetting(SysSetting setting) {
        sysDao.saveSetting(setting);
    }

    public void deleteSetting(int objid) {
        sysDao.deleteSetting(objid);
    }

    /**
     * 发送消息
     * */
    public void sendMessage(MessageContext context) {
        //获取发送模板
        SysMsgTemplate template = getMsgTemplateByCode(context.getTemplateCode());
        if(template != null) {
            //todo 填充模板标题和内容
            String title = "";
            String content = "";


            //获取改消息的发送方式
            String notifyType = template.getNotify_type();
            String _notifyType = "|" + notifyType + "|";

            if(_notifyType.contains("|" + MsgNotifyTypeEnum.EMAIL.getValue() + "|")) {

            }

            if(_notifyType.contains("|" + MsgNotifyTypeEnum.SMS.getValue() + "|")) {

            }

            if(_notifyType.contains("|" + MsgNotifyTypeEnum.SYSMSG.getValue() + "|")) {

            }

            if(_notifyType.contains("|" + MsgNotifyTypeEnum.PUSH.getValue() + "|")) {

            }

            List<MessageReceiver> receiverList = context.getReceivers();

        }
    }

    private void initMsgSend(int template_id, String title, String content, int type, Integer sender_id, List<MessageReceiver> receiverList) {

    }
}
