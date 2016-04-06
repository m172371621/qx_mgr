package com.brilliantreform.sc.express.dao;

import java.util.List;
import java.util.Map;

import com.brilliantreform.sc.express.po.ExpressSend;
import com.brilliantreform.sc.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.express.po.Express;

@Repository("expressDao")
public class ExpressDao {
	
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	public Integer insertExpress(Express express)
	{
		return (Integer)sqlMapClient.insert("express.insertExpress", express);
	}
	

	public void updateExpress(Express express)
	{
		sqlMapClient.update("express.updateExpress", express);
	}
	
	public Express getExpress(Integer id)
	{
		return (Express)sqlMapClient.queryForObject("express.getExpress", id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Express> getExpressList(Map map)
	{
		return (List<Express>)sqlMapClient.queryForList("express.getExpressList", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public Integer countExpress(Map map)
	{
		return (Integer)sqlMapClient.queryForObject("express.countExpress", map);
	}
	
	public Integer selUserlogin(String user_phone) {
		return (Integer)sqlMapClient.queryForObject("express.selUserlogin", user_phone);
	}

    public List<Map> searchExpress(Map param) {
        return sqlMapClient.queryForList("express.searchExpress", param);
    }

    public int searchExpressCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject("express.searchExpressCount", param), 0);
    }

    public void insertExpressInfo(Express express) {
        sqlMapClient.insert("express.insertExpressInfo", express);
    }

    public void updateExpressInfo(Express express) {
        sqlMapClient.update("express.updateExpressInfo", express);
    }

    public void saveExpress(Express express) {
        if(express != null) {
            if(express.getExpress_id() == null) {
                insertExpressInfo(express);
            } else {
                updateExpressInfo(express);
            }
        }
    }

    public void insertExpressSend(ExpressSend expressSend) {
        sqlMapClient.insert("express.insertExpressSend", expressSend);
    }

    public void updateExpressSend(ExpressSend expressSend) {
        sqlMapClient.update("express.updateExpressSend", expressSend);
    }

    public List<Map> searchExpressSend(Map param) {
        return sqlMapClient.queryForList("express.searchExpressSend", param);
    }

    public int searchExpressSendCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject("express.searchExpressSendCount", param), 0);
    }

    public ExpressSend getExpressSendById(int objid) {
        return (ExpressSend) sqlMapClient.queryForObject("express.getExpressSendById", objid);
    }

    public void removeExpressSend(int objid) {
        sqlMapClient.update("express.removeExpressSend", objid);
    }
}
