package com.brilliantreform.sc.qxcard.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brilliantreform.sc.qxcard.po.*;
import com.brilliantreform.sc.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

@Repository("qxCardDao")
public class QxCardDao {
	
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	public QxCard getQxCard(QxCard qxCard)
	{
		return (QxCard)sqlMapClient.queryForObject("qxcard.getQxCard", qxCard);
	}
	
	public void updateQxCard(QxCard qxCard)
	{
		sqlMapClient.update("qxcard.updateQxCard", qxCard);
	}
	
	public void insertQxCardLog(QxCardLog qxCardLog)
	{
		sqlMapClient.insert("qxcard.insertQxCardLog", qxCardLog);
	}
	
	public void updateUserQxCard(UserQxCard userQxCard)
	{
		sqlMapClient.update("qxcard.updateUserQxCard", userQxCard);
	}
	
	public Integer insertQxcardReglog(QxcardReglog qxcardReglog)
	{
		return (Integer)sqlMapClient.insert("qxcard.insertQxcardReglog", qxcardReglog);
	}
	
	public void updateQxcardReglog(QxcardReglog qxcardReglog)
	{
		sqlMapClient.update("qxcard.updateQxcardReglog", qxcardReglog);
	}
	
	public QxcardReglog getQxcardReglog(Integer id)
	{
		return (QxcardReglog)sqlMapClient.queryForObject("qxcard.getQxcardReglog", id);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<QxcardReglog> getQxcardReglogList(Map paramMap)
	{
		return (List<QxcardReglog>)sqlMapClient.queryForList("qxcard.getQxcardReglogList", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public Integer countQxcardReglog(Map paramMap)
	{
		
		return (Integer)sqlMapClient.queryForObject("qxcard.countQxcardReglog", paramMap);  
	 
	}

    public List<Map> searchRegLog(Map param) {
        return sqlMapClient.queryForList("qxcard.searchRegLog", param);
    }

    public int searchRegLogCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject("qxcard.searchRegLogCount", param), 0);
    }

    public List<Map> searchUserQxcard(Map param) {
        return sqlMapClient.queryForList("qxcard.searchUserQxcard", param);
    }

    public int searchUserQxcardCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject("qxcard.searchUserQxcardCount", param), 0);
    }

	@SuppressWarnings("unchecked")
	public List<UserQxCard> getUserQxcardList(Map<String,Object> paramMap){
		return (List<UserQxCard>) sqlMapClient.queryForList("qxcard.getUserQxcard", paramMap);
	}
	
	public Integer getUserQxcardListCount(Map<String,Object> paramMap)
	{
		return (Integer)sqlMapClient.queryForObject("qxcard.getUserQxcardCount", paramMap);  
	 
	}
	
	@SuppressWarnings("unchecked")
	public List<QxCardPayLog> getQxCardByOrder(String order_serial)
	{
		return (List<QxCardPayLog>)sqlMapClient.queryForList("qxcard.getQxCardByOrder", order_serial);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<QxCardLog> getCardOptList(Map<String,Object> paramMap){
		return (List<QxCardLog>) sqlMapClient.queryForList("qxcard.getCardOptList", paramMap);
	}
	
	public Integer getCardOptCount(Map<String,Object> paramMap)
	{
		return (Integer)sqlMapClient.queryForObject("qxcard.getCardOptCount", paramMap);  
	 
	}
	
	public void deleteCard(String order_serial) {
		sqlMapClient.delete("qxcard.deleteCard", order_serial);
	}
	
	public void updateQxCardPayLog(QxCardPayLog qxCardPayLog)
	{
		sqlMapClient.update("qxcard.updateQxCardPayLog", qxCardPayLog);
	}

	public List<Map> findQxcardRefund(String order_serial) {
		return sqlMapClient.queryForList("qxcard.findQxcardRefund", order_serial);
	}

	public void insertQxcardPayLog(QxCardPayLog payLog) {
		sqlMapClient.insert("qxcard.insertQxcardPayLog", payLog);
	}

	public UserQxCard getUserQxcardByNo(String qxcard_no) {
		return (UserQxCard)sqlMapClient.queryForObject("qxcard.getUserQxcardByNo", qxcard_no);
	}

	public void insertQxcardBase(QxCard qxCard) {
		sqlMapClient.insert("qxcard.insertQxcardBase", qxCard);
	}

	public List<QxCardCzRule> findQxCardCzRule(Integer card_value) {
		return sqlMapClient.queryForList("qxcard.findQxCardCzRule", card_value);
	}

	public int insertQxCardCzLog(QxCardCzLog czLog) {
		return (Integer)sqlMapClient.insert("qxcard.insertQxCardCzLog", czLog);
	}

    public QxCardCzRule getQxCardCzRule(int community_id, int card_value) {
        Map param = new HashMap();
        param.put("community_id", community_id);
        param.put("card_value", card_value);
        return (QxCardCzRule) sqlMapClient.queryForObject("qxcard.getQxCardCzRule", param);
    }

    public void updateQxCardCzLog(QxCardCzLog czLog) {
        sqlMapClient.update("qxcard.updateQxCardCzLog", czLog);
    }

    public QxCardCzLog saveQxCardCzLog(QxCardCzLog czLog) {
        if(czLog != null) {
            if(czLog.getObjid() == null) {
                insertQxCardCzLog(czLog);
            } else {
                updateQxCardCzLog(czLog);
            }
        }
        return czLog;
    }

	public void insertUserQxcard(UserQxCard userQxCard) {
		sqlMapClient.insert("qxcard.insertUserQxcard", userQxCard);
	}

	public void updateQxcardCzCardNo(int objid, String card_no) {
		Map map = new HashMap();
		map.put("objid", objid);
		map.put("card_no", card_no);
		sqlMapClient.update("qxcard.updateQxcardCzCardNo", map);
	}

	public int getCzAmountByCardValue(int user_id, int card_value) {
		Map map = new HashMap();
		map.put("user_id", user_id);
		map.put("card_value", card_value);
		return CommonUtil.safeToInteger(sqlMapClient.queryForObject("qxcard.getCzAmountByCardValue", map), 0);
	}

	/**
	 * 获取某小区的区享卡充值面值
	 * */
	public List<QxCardCzRule> findRechargeCardValue(int community_id) {
		return sqlMapClient.queryForList("qxcard.findRechargeCardValue", community_id);
	}

	/**
	 * 获取某小区的充值总额
	 * */
	public int getSumCzPriceByCid(int community_id) {
		return CommonUtil.safeToInteger(sqlMapClient.queryForObject("qxcard.getSumCzPriceByCid", community_id), 0);
	}

    public List<Map> searchQxcardCzLog(Map param) {
        return sqlMapClient.queryForList("qxcard.searchQxcardCzLog", param);
    }

    public int searchQxcardCzLogCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject("qxcard.searchQxcardCzLogCount", param), 0);
    }

    public QxCardCzLog getCzLogByCzSerial(String cz_serial) {
        return (QxCardCzLog) sqlMapClient.queryForObject("qxcard.getCzLogByCzSerial", cz_serial);
    }

    public void insertQxcardCzPayLog(QxCardCzPayLog czPayLog) {
        sqlMapClient.insert("qxcard.insertQxcardCzPayLog", czPayLog);
    }

    public QxCardCzLog getQxcardCzLogById(int objid) {
        return (QxCardCzLog)sqlMapClient.queryForObject("qxcard.getQxcardCzLogById", objid);
    }

    public List<Map> searchOptLog(Map param) {
        return sqlMapClient.queryForList("qxcard.searchOptLog", param);
    }

    public int searchOptLogCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject("qxcard.searchOptLogCount", param), 0);
    }
}
