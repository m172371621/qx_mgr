package com.brilliantreform.sc.distri.service;

import com.brilliantreform.sc.distri.dao.DistriDao;
import com.brilliantreform.sc.distri.po.DistriOrderBean;
import com.brilliantreform.sc.utils.HttpUtil;
import com.brilliantreform.sc.utils.SettingUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Service("distriService")
public class DistriService {
	@Autowired
	private DistriDao distriDao;
	
	public List<DistriOrderBean> getDistriList(Map<String, Object> map) {
		return distriDao.getDistriList(map);
	}
	public Integer getDistriCount(Map<String, Object> map){
		return distriDao.getDistriCount(map);
	}

	public List<DistriOrderBean> getDistriDetail(Map<String, Object> map) {
		return distriDao.getDistriDetail(map);
	}
	public Integer getDistriDetailCount(Map<String, Object> map){
		return distriDao.getDistriDetailCount(map);
	}

	public Integer insertDistri(DistriOrderBean distri) {
		return distriDao.insertDistri(distri);
	}

	public void insertDistriProduct(Map<String,Object> distri) {
		distriDao.insertProductDelTemp(distri);
		distriDao.insertDistriProduct(distri);
		
	}
	
	@Transactional
	public void insertDistriDetail(Map<String,Object> distri,DistriOrderBean distriBean) {
		distriDao.insertDetailDelTemp(distri);
		//distriDao.insertProductDelTemp(distri);
		int count=distriDao.insertDistriDetail(distri);
		distriDao.insertDistriProduct(distri);
		distriDao.updateOrderStatus(distri);
		if(count>0) distriDao.insertDistri(distriBean);
	}
	
	public void insertProductDelTemp(Map<String,Object> distri) {
		distriDao.insertProductDelTemp(distri);
	}
	
	public void insertDetailDelTemp(Map<String,Object> distri) {
		distriDao.insertDetailDelTemp(distri);
	}

	public Integer insertDistriWorker(DistriOrderBean distri) {
		return distriDao.insertDistriWorker(distri);
	}

	public void optDistriDetail(DistriOrderBean distri) {
		distriDao.optDistriDetail(distri);
	}

	public void optDistri(DistriOrderBean distri) {
		distriDao.optDistri(distri);
	}
	public void updateOrderStatus(Map<String,Object> distri){
		distriDao.updateOrderStatus(distri);
	}
	public List<DistriOrderBean> getDistriWorker(Map<String,Object> distri){
		return distriDao.getDistriWorker(distri);
	}
	public Integer getDetailCountByNum(Map<String,Object> distri){
		return distriDao.getDistriDetailCount(distri);
	}
	public List<DistriOrderBean> getSerialByPhone(Map<String, Object> map) {
		return distriDao.getSerialByPhone(map);
	}
	
	public List<DistriOrderBean> getDistriDetailExecel(Map<String, Object> map) {
		return distriDao.getDistriDetailExecel(map);
	}
	//distr_work_list
	public List<Map> distr_work_list(Map param) {
		return distriDao.distr_work_list(param);
	}
	//distr_work_count(param)
	public int distr_work_count(Map param) {
		return distriDao.distr_work_count(param);
	}
	
	public Map<String, Object> get_id(int worker_id) {
		return distriDao.get_id(worker_id);
	}
	
	public void delete(int worker_id) {
		 distriDao.delete(worker_id);
	}
	
	public Integer save_Worker(DistriOrderBean bean) { 
		if(null != bean) {
			if(bean.getDistri_worker_id() == null) {
				return distriDao.insert_Worker(bean);
			}
			String picture = bean.getDistri_worker_img();
			
			if (StringUtils.isNotBlank(picture)) {

				try {
					String new_path = "/upload/product/" + bean.getDistri_community_id() + "/picture/" + picture;

					picture = URLEncoder.encode(picture, "utf-8");
					String url_new_path = URLEncoder.encode(new_path, "utf-8");

					HttpUtil.doGet(SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST") + "/common/rename/", "password=qxitrename&old_path=" + picture + "&new_path=" + url_new_path);

					bean.setDistri_worker_img(SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST") + new_path);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			distriDao.updata_Worker(bean);
		}
		return null;
	}
}
