package com.brilliantreform.sc.distri.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.distri.po.DistriOrderBean;

@Repository("distriDao")
public class DistriDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@SuppressWarnings("unchecked")
	public List<DistriOrderBean> getDistriList(Map<String, Object> map) {
		return (List<DistriOrderBean>) sqlMapClient.queryForList("distri.getDistriList", map);
	}
	public Integer getDistriCount(Map<String, Object> map){
		return (Integer) sqlMapClient.queryForObject("distri.getDistriCount", map);
	}
	@SuppressWarnings("unchecked")
	public List<DistriOrderBean> getDistriDetail(Map<String, Object> map) {
		return (List<DistriOrderBean>) sqlMapClient.queryForList("distri.getDistriDetail", map);
	}
	public Integer getDistriDetailCount(Map<String, Object> map){
		return (Integer) sqlMapClient.queryForObject("distri.getDistriDetailCount", map);
	}
	public Integer insertDistri(DistriOrderBean distri){
		return (Integer) sqlMapClient.insert("distri.insertDistri", distri);
	}
	public Integer insertDistriDetail(Map<String,Object> distri){
		return (Integer) sqlMapClient.insert("distri.insertDistriDetail", distri);
	}
	public void insertDistriProduct(Map<String,Object> distri){
		sqlMapClient.insert("distri.insertDistriProduct", distri);
	}
	
	public void insertDetailDelTemp(Map<String,Object> distri){
		sqlMapClient.insert("distri.insertDetailDelTemp", distri);
	}
	public void insertProductDelTemp(Map<String,Object> distri){
		sqlMapClient.insert("distri.insertProductDelTemp", distri);
	}
	public Integer insertDistriWorker(DistriOrderBean distri){
		return (Integer) sqlMapClient.insert("distri.insertDistriWorker", distri);
	}
	public void optDistriDetail(DistriOrderBean distri){
		sqlMapClient.update("distri.optDistriDetail", distri);
	}
	public void optDistri(DistriOrderBean distri){
		sqlMapClient.update("distri.optDistri", distri);
	}
	public void updateOrderStatus(Map<String,Object> distri){
		sqlMapClient.update("distri.updateOrderStatus", distri);
	}
	
	public Integer getDetailCountByNum(Map<String,Object> distri){
		return (Integer) sqlMapClient.queryForObject("distri.getDetailCountByNum", distri);
	}
	@SuppressWarnings("unchecked")
	public List<DistriOrderBean> getDistriWorker(Map<String,Object> distri){
		return (List<DistriOrderBean>) sqlMapClient.queryForList("distri.getDistriWorker", distri); 
	}
	
	@SuppressWarnings("unchecked")
	public List<DistriOrderBean> getSerialByPhone(Map<String, Object> map) {
		return (List<DistriOrderBean>) sqlMapClient.queryForList("distri.getSerialByPhone", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<DistriOrderBean> getDistriDetailExecel(Map<String, Object> map) {
		return (List<DistriOrderBean>) sqlMapClient.queryForList("distri.getDistriDetailExecel", map);
	}
	
	public List<Map> distr_work_list(Map param) {
		return sqlMapClient.queryForList("distri.distr_work_list", param);
	}
	
	public int distr_work_count(Map param) {
		return (Integer)sqlMapClient.queryForObject("distri.distr_work_count", param);
	}
	
	public Map<String, Object> get_id(int worker_id) {
		return(Map<String, Object>)sqlMapClient.queryForObject("distri.get_id", worker_id);
	}
	
	public void delete(int worker_id ) {
		sqlMapClient.delete("distri.delete", worker_id);
	}
	
	public void updata_Worker(DistriOrderBean bean) {
		 sqlMapClient.update("distri.updata_Worker", bean);
	}
	
	public Integer insert_Worker(DistriOrderBean bean) {
		return (Integer) sqlMapClient.insert("distri.insert_Worker", bean);
	}
}
