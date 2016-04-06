package com.brilliantreform.sc.express.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brilliantreform.sc.express.po.ExpressSend;
import com.brilliantreform.sc.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.express.dao.ExpressDao;
import com.brilliantreform.sc.express.po.Express;

@Service("expressService")
public class ExpressService {

	private static Logger logger = Logger.getLogger(ExpressService.class);

	@Autowired
	private ExpressDao expressDao;
	
	
	public Integer insertExpress(Express express)
	{
		return expressDao.insertExpress(express);
	}
	
	
	public void updateExpress(Express express)
	{
		expressDao.updateExpress(express);
	}
	
	public Express getExpress(Integer id)
	{
		return expressDao.getExpress(id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Express> getExpressList(Express express,Integer begin,Integer size,String time_from,String time_to)
	{
		Map map = new HashMap();
		map.put("user_phone", express.getUser_phone());
		map.put("express_no", express.getExpress_no());
		map.put("express_com", express.getExpress_com());		
		map.put("user_type", express.getUser_type());
		map.put("status", express.getStatus());
		map.put("community_id", express.getCommunity_id());
		map.put("express_info", express.getExpress_info());
		map.put("time_from", time_from);
		map.put("time_to", time_to);
		map.put("begin", begin);
		map.put("size", size);
		

		return expressDao.getExpressList(map);
	}
	
	@SuppressWarnings("unchecked")
	public Integer countExpress(Express express,String time_from,String time_to)
	{
		Map map = new HashMap();
		map.put("user_phone", express.getUser_phone());
		map.put("express_no", express.getExpress_no());
		map.put("express_com", express.getExpress_com());		
		map.put("user_type", express.getUser_type());
		map.put("status", express.getStatus());
		map.put("community_id", express.getCommunity_id());
		map.put("express_info", express.getExpress_info());
		map.put("time_from", time_from);
		map.put("time_to", time_to);
		
		return expressDao.countExpress(map);
	}
	/**
	 * 根据手机查找user_id
	 */
	
	public Integer selUserlogin(String user_phone) {
		return expressDao.selUserlogin(user_phone);
	}

    public List<Map> searchExpress(Map param) {
        return expressDao.searchExpress(param);
    }

    public int searchExpressCount(Map param) {
        return expressDao.searchExpressCount(param);
    }

    public void saveExpress(Express express) {
        expressDao.saveExpress(express);
    }

    public void saveExpressSend(ExpressSend expressSend) {
        if(expressSend != null) {
            if(expressSend.getObjid() == null) {
                expressSend.setRemovetag(0);
                expressSend.setSend_time(new Date());
                expressDao.insertExpressSend(expressSend);
            } else {
                expressDao.updateExpressSend(expressSend);
            }
        }
    }

    public List<Map> searchExpressSend(Map param) {
        return expressDao.searchExpressSend(param);
    }

    public int searchExpressSendCount(Map param) {
        return expressDao.searchExpressSendCount(param);
    }

    public ExpressSend getExpressSendById(int objid) {
        return expressDao.getExpressSendById(objid);
    }

    public void removeExpressSend(int objid) {
        expressDao.removeExpressSend(objid);
    }
}
