package com.brilliantreform.sc.service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brilliantreform.sc.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.consultation.service.ConsultationService;
import com.brilliantreform.sc.service.dao.ServiceDao;
import com.brilliantreform.sc.service.po.MainAD;
import com.brilliantreform.sc.service.po.ServiceVo;

@Service("sevService")
public class SevService {
	private static Logger logger = Logger.getLogger(SevService.class);

	@Autowired
	private ServiceDao serviceDao;

	@Autowired
	private ConsultationService consultationService;

	@SuppressWarnings("unchecked")
	public List<ServiceVo> getServiceList(Integer type,Integer cid) {
		Map map = new HashMap();
		map.put("type", type);
		map.put("cid",cid );
		
		if(cid == null || cid < 6)
			map.put("cid",1 );
		
		List<ServiceVo> list = serviceDao.getServiceList(map);
		for (ServiceVo serviceVo : list) {
			System.out.println(serviceVo.getService_name());
		}
		logger.info("into SevService getServiceList:"+list);

		return list;
	}

	public List<MainAD> getAdList() {
		
		logger.info("into SevService getAdList:");
		
		return serviceDao.getAdList();
	}
	
	public Integer insertService(ServiceVo vo){
		return serviceDao.insertService(vo);
	}
	
	public void updateService(ServiceVo vo){
		serviceDao.updateService(vo);
	}
	
	public void delService(Integer id){
		serviceDao.delService(id);
	}

	public List<ServiceVo> searchServiceInfo(Map param) {
		return serviceDao.searchServiceInfo(param);
	}

	public int searchServiceInfoCount(Map param) {
		return serviceDao.searchServiceInfoCount(param);
	}

	public void saveServiceInfo(ServiceVo service) {
		if(service != null) {
			if(service.getService_id() == null) {
				serviceDao.insertServiceInfo(service);
			} else {
				serviceDao.updateServiceInfo(service);
			}
		}
	}

	public ServiceVo getServiceInfoById(int service_id) {
		return serviceDao.getServiceInfoById(service_id);
	}

	public void removeService(int service_id) {
		serviceDao.removeService(service_id);
	}

    public List<ServiceVo> findProductService(Map param) {
        return serviceDao.findProductService(param);
    }

    public List<Map> findProductServiceTree() {
        return serviceDao.findProductServiceTree();
    }

    public List<ServiceVo> findSecondProductService(int community_id) {
        return serviceDao.findSecondProductService(community_id);
    }

    public List<Map> searchServiceBase(Map param) {
        return serviceDao.searchServiceBase(param);
    }

    public int searchServiceBaseCount(Map param) {
        return serviceDao.searchServiceBaseCount(param);
    }

    public ServiceVo getServiceById(int service_id) {
        return serviceDao.getServiceById(service_id);
    }
}
