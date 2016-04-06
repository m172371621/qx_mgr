package com.brilliantreform.sc.sys.dao;

import com.brilliantreform.sc.confgure.po.Config_community;
import com.brilliantreform.sc.sys.po.*;
import com.brilliantreform.sc.utils.CommonUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;
import sun.security.krb5.Config;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SysDao {

    private static Logger LOGGER = Logger.getLogger(SysDao.class);

    private static final String NAMESPACE = "sys.";

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    public List<SysSetting> findAllSetting() {
        return sqlMapClient.queryForList(NAMESPACE + "findAllSetting");
    }

    public List<SysDict> findAllDict() {
        return sqlMapClient.queryForList(NAMESPACE + "findAllDict");
    }

    public List<String> findDictModule() {
        return sqlMapClient.queryForList(NAMESPACE + "findDictModule");
    }

    public List<Map> searchCommunityConfig(Map param) {
        return sqlMapClient.queryForList(NAMESPACE + "searchCommunityConfig", param);
    }

    public int searchCommunityConfigCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject(NAMESPACE + "searchCommunityConfigCount", param), 0);
    }

    public Config_community getCommunityConfigById(int community_id, String config_id) {
        Map param = new HashMap();
        param.put("community_id", community_id);
        param.put("config_id", config_id);
        return (Config_community) sqlMapClient.queryForObject(NAMESPACE + "getCommunityConfigById", param);
    }

    public void updateCommunityConfig(Config_community config, int community_id_old, String config_id_old) {
        Map param = new HashMap();
        param.put("config_id", config.getConfig_id());
        param.put("config_value", config.getConfig_value());
        param.put("config_dec", config.getConfig_dec());
        param.put("community_id", config.getCommunity_id());
        param.put("config_ext1", config.getConfig_ext1());
        param.put("config_ext2", config.getConfig_ext2());
        param.put("config_ext3", config.getConfig_ext3());
        param.put("createTime", config.getCreateTime());
        param.put("community_id_old", community_id_old);
        param.put("config_id_old", config_id_old);
        sqlMapClient.insert(NAMESPACE + "updateCommunityConfig", param);
    }

    public void insertCommunityConfig(Config_community config) {
        sqlMapClient.insert(NAMESPACE + "insertCommunityConfig", config);
    }

    public void saveCommunityConfig(Config_community config, Integer community_id_old, String config_id_old) {
        if(config != null) {
            boolean isUpdate = false;
            if(community_id_old != null && StringUtils.isNotBlank(config_id_old)) {
                Config_community _config = getCommunityConfigById(community_id_old, config_id_old);
                if(_config != null) {
                    updateCommunityConfig(config, community_id_old, config_id_old);
                    isUpdate = true;
                }
            }

            if(!isUpdate) {
                config.setCreateTime(new Date());
                insertCommunityConfig(config);
            }
        }
    }

    public void removeCommunityConfig(int community_id, String config_id) {
        Map param = new HashMap();
        param.put("community_id", community_id);
        param.put("config_id", config_id);
        sqlMapClient.delete(NAMESPACE + "removeCommunityConfig", param);
    }

    public List<Map> searchSetting(Map param) {
        return sqlMapClient.queryForList(NAMESPACE + "searchSetting", param);
    }

    public int searchSettingCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject(NAMESPACE + "searchSettingCount", param), 0);
    }

    public List<String> findSettingModules() {
        return sqlMapClient.queryForList(NAMESPACE + "findSettingModules");
    }

    public List<Map> findMenuTree() {
        return sqlMapClient.queryForList(NAMESPACE + "findMenuTree");
    }

    public List<Map> searchMenu(Map param) {
        return sqlMapClient.queryForList(NAMESPACE + "searchMenu", param);
    }

    public int searchMenuCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject(NAMESPACE + "searchMenuCount", param), 0);
    }

    public SysMenu getMenuById(int objid) {
        return (SysMenu) sqlMapClient.queryForObject(NAMESPACE + "getMenuById", objid);
    }

    public int getMaxMenuSort(int parentid) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject(NAMESPACE + "getMaxMenuSort", parentid), 0);
    }

    public void insertMenu(SysMenu menu) {
        sqlMapClient.insert(NAMESPACE + "insertMenu", menu);
    }

    public void updateMenu(SysMenu menu) {
        sqlMapClient.insert(NAMESPACE + "updateMenu", menu);
    }

    public void saveMenu(SysMenu menu) {
        if(menu != null) {
            if(menu.getObjid() == null) {
                insertMenu(menu);
            } else {
                updateMenu(menu);
            }
        }
    }

    public void removeMenu(int objid) {
        sqlMapClient.update(NAMESPACE + "removeMenu", objid);
    }

    public SysJob getJobById(int objid) {
        return (SysJob)sqlMapClient.queryForObject(NAMESPACE + "getJobById", objid);
    }

    public void insertJob(SysJob job) {
        sqlMapClient.insert(NAMESPACE + "insertJob", job);
    }

    public void updateJob(SysJob job) {
        sqlMapClient.update(NAMESPACE + "updateJob", job);
    }

    public void removeJob(int objid) {
        sqlMapClient.delete(NAMESPACE + "removeJob", objid);
    }

    public List<SysSendQueue> findWaitSendQueue(Integer status) {
        return sqlMapClient.queryForList(NAMESPACE + "findWaitSendQueue", status);
    }

    public void updateSysSendQueue(SysSendQueue queue) {
        sqlMapClient.update(NAMESPACE + "updateSysSendQueue", queue);
    }

    public void insertSysSendQueue(SysSendQueue queue) {
        sqlMapClient.insert(NAMESPACE + "insertSysSendQueue", queue);
    }

    public List<SysSendDetail> findSendDetail(int queue_id, Integer status) {
        Map param = new HashMap();
        param.put("queue_id", queue_id);
        param.put("status", status);
        return sqlMapClient.queryForList(NAMESPACE + "findSendDetail", param);
    }

    public SysMsgTemplate getMsgTemplateByCode(String templateCode) {
        return (SysMsgTemplate) sqlMapClient.queryForObject(NAMESPACE + "getMsgTemplateByCode", templateCode);
    }

    public SysSetting getSettingById(int objid) {
        return (SysSetting) sqlMapClient.queryForObject(NAMESPACE + "getSettingById", objid);
    }

    public void insertSetting(SysSetting setting) {
        sqlMapClient.insert(NAMESPACE + "insertSetting", setting);
    }

    public void updateSetting(SysSetting setting) {
        sqlMapClient.update(NAMESPACE + "updateSetting", setting);
    }

    public void saveSetting(SysSetting setting) {
        if(setting != null) {
            if(setting.getObjid() == null) {
                insertSetting(setting);
            } else {
                updateSetting(setting);
            }
        }
    }

    public void deleteSetting(int objid) {
        sqlMapClient.delete(NAMESPACE + "deleteSetting", objid);
    }
}
