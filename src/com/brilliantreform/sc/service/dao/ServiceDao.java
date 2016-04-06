package com.brilliantreform.sc.service.dao;

import java.util.List;
import java.util.Map;

import com.brilliantreform.sc.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.service.po.MainAD;
import com.brilliantreform.sc.service.po.ServiceVo;

@Repository("serviceDao")
public class ServiceDao {
	private static Logger logger = Logger.getLogger(ServiceDao.class);

	private static final String NAMESPACE = "service.";

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	@SuppressWarnings("unchecked")
	public List<ServiceVo> getServiceList(Map type){
		return (List<ServiceVo>)sqlMapClient.queryForList("service.getServiceList",type);
	}
	
	@SuppressWarnings("unchecked")
	public List<MainAD> getAdList(){
		return (List<MainAD>)sqlMapClient.queryForList("service.getADList");
	}
	
	public Integer insertService(ServiceVo vo){
		return (Integer)sqlMapClient.insert("service.insertService", vo);
	}
	
	public void updateService(ServiceVo vo){
		sqlMapClient.update("service.updateService",vo);
	}
	
	public void delService(Integer id){
		sqlMapClient.delete("service.delService", id);
	}

	public List<ServiceVo> searchServiceInfo(Map param) {
		return sqlMapClient.queryForList(NAMESPACE + "searchServiceInfo", param);
	}

	public int searchServiceInfoCount(Map param) {
		return (Integer)sqlMapClient.queryForObject(NAMESPACE + "searchServiceInfoCount", param);
	}

	public void insertServiceInfo(ServiceVo service) {
		sqlMapClient.insert(NAMESPACE + "insertServiceInfo", service);
	}

	public void updateServiceInfo(ServiceVo service) {
		sqlMapClient.update(NAMESPACE + "updateServiceInfo", service);
	}

	public ServiceVo getServiceInfoById(int service_id) {
		return (ServiceVo) sqlMapClient.queryForObject(NAMESPACE + "getServiceInfoById", service_id);
	}

	public void removeService(int service_id) {
		sqlMapClient.delete(NAMESPACE + "removeService", service_id);
	}

    public List<ServiceVo> findProductService(Map param) {
        return sqlMapClient.queryForList(NAMESPACE + "findProductService", param);
    }

    public List<Map> findProductServiceTree() {
        return sqlMapClient.queryForList(NAMESPACE + "findProductServiceTree");
    }

    public List<ServiceVo> findSecondProductService(int community_id) {
        return sqlMapClient.queryForList(NAMESPACE + "findSecondProductService", community_id);
    }

    public List<Map> searchServiceBase(Map param) {
        return sqlMapClient.queryForList(NAMESPACE + "searchServiceBase", param);
    }

    public int searchServiceBaseCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject(NAMESPACE + "searchServiceBaseCount", param), 0);
    }

    public ServiceVo getServiceById(int service_id) {
        return (ServiceVo) sqlMapClient.queryForObject(NAMESPACE + "getServiceById", service_id);
    }
}
