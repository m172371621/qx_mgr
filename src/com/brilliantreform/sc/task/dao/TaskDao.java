package com.brilliantreform.sc.task.dao;

import com.brilliantreform.sc.task.po.*;
import com.brilliantreform.sc.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("taskDao")
public class TaskDao {
	private static Logger logger = Logger.getLogger(TaskDao.class);

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	@SuppressWarnings("unchecked")
	public List<Task> getTaskByTime(Map map){
		return (List<Task>)sqlMapClient.queryForList("task.getTaskByTime",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Task> getTaskByTimeDesc(Map map){
		return (List<Task>)sqlMapClient.queryForList("task.getTaskByTimeDesc",map);
	}

	@SuppressWarnings("unchecked")
	public List<Task> getTaskByTip(Map map){
		return (List<Task>)sqlMapClient.queryForList("task.getTaskByTip",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Task> getTaskByTipDesc(Map map){
		return (List<Task>)sqlMapClient.queryForList("task.getTaskByTipDesc",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Task> getTaskByUserSend(Map map){
		return (List<Task>)sqlMapClient.queryForList("task.getTaskByUserSend",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Task> getTaskByUserReceive(Map map){
		return (List<Task>)sqlMapClient.queryForList("task.getTaskByUserReceive",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<TaskMedia> getTaskMedia(Map map){
		return (List<TaskMedia>)sqlMapClient.queryForList("task.getTaskMediaList",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<TaskProperty> getTaskPropertyList(Map map){
		return (List<TaskProperty>)sqlMapClient.queryForList("task.getTaskPropertyList",map);
	}
	
	public TaskProperty getTaskProperty(Integer task_id){
		return (TaskProperty)sqlMapClient.queryForObject("task.getTaskProperty",task_id);
	}
	
	public Task getTaskById(Integer task_id){
		return (Task)sqlMapClient.queryForObject("task.getTask",task_id);
	}
	
	public Integer insertTask(Task task){
		return (Integer)sqlMapClient.insert("task.insertTask",task);
	}
	
	public void insertTaskMedia(TaskMedia media){
		sqlMapClient.insert("task.insertTaskMedia",media);
	}
	
	public Integer insertTaskProperty(TaskProperty task){
		return (Integer)sqlMapClient.insert("task.insertTaskProperty",task);
	}
	
	public void updateTask(Task task){
		sqlMapClient.update("task.updateTask",task);
	}
	
	//---------------------mgr------------------
	
	@SuppressWarnings("unchecked")
	public List<Task> searchTask(TaskQueryBean searchBean){
		return (List<Task>)sqlMapClient.queryForList("task.searchTask",searchBean);
	}
	

	public Integer countSearchTask(TaskQueryBean searchBean){
		return (Integer)sqlMapClient.queryForObject("task.countSearchTask",searchBean);
	}
	
	@SuppressWarnings("unchecked")
	public List<TaskProperty> searchTaskProperty(TaskQueryBean searchBean){
		return (List<TaskProperty>)sqlMapClient.queryForList("task.searchTaskProperty",searchBean);
	}
	

	public Integer countTaskProperty(TaskQueryBean searchBean){
		return (Integer)sqlMapClient.queryForObject("task.countTaskProperty",searchBean);
	}
	
	public void updateTaskProperty(TaskProperty task){
		sqlMapClient.update("task.updateTaskProperty",task);
	}
	/**
	 * 查物业公告信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PropertyInfo> propertyInfoList(Map map){
		return (List<PropertyInfo>)sqlMapClient.queryForList("task.propertyInfoList", map);
	}
	
	
	public int propertyInfoListCount(Map map){
		return (Integer)sqlMapClient.queryForObject("task.propertyInfoListCount", map);
	}
	/**
	 * 物业公告录入
	 * @param info
	 */
	public void propertyInfo_ADD(PropertyInfo info){
		sqlMapClient.insert("task.propertyInfo_ADD", info);
	}
	
	/**
	 * 物业公告信息 编辑
	 * @param map
	 */
	public void editPropertyInfo(Map map){
		sqlMapClient.insert("task.editPropertyInfo", map);
	}
	/**
	 * 查询编辑当前的一个PropertyInfo对象 显示在编辑页面
	 * @param propertyInfoListOne
	 * @return
	 */
	public PropertyInfo propertyInfoOne(int propertyInfoOne){
		return (PropertyInfo) sqlMapClient.queryForObject("task.propertyInfoOne", propertyInfoOne);
	}
	/**
	 * 删除物业公告信息 根据 property_information_id 删除
	 * @param property_information_id
	 */
	public void delPropertyInfo(int property_information_id){
		sqlMapClient.delete("task.delPropertyInfo", property_information_id);
	}
	/**
	 * 预约管理
	 * @param communityId
	 * @return
	 */
	public List<Service_order> getService_order(Map map){
		return (List<Service_order>)sqlMapClient.queryForList("task.getService_order",map);
	}
	
	public Integer getService_order_count(Map map){
		return (Integer) sqlMapClient.queryForObject("task.getService_order_count",map);
	}

    public List<Map> searchServiceOrder(Map param) {
        return sqlMapClient.queryForList("task.searchServiceOrder", param);
    }

    public int searchServiceOrderCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject("task.searchServiceOrderCount", param), 0);
    }

    public Map getServiceOrderById(int order_id) {
        return (Map)sqlMapClient.queryForObject("task.getServiceOrderById", order_id);
    }

    public void editServiceOrder(int order_id, Integer order_status, String order_dec) {
        Map param = new HashMap();
        param.put("order_id", order_id);
        param.put("order_status", order_status);
        param.put("order_dec", order_dec);
        sqlMapClient.update("task.editServiceOrder", param);
    }

	/**
	 * 删除预约
	 * @param map
	 * @return
	 */
	public Integer delService_order(Map map){
		return (Integer)sqlMapClient.delete("task.delService_order", map);
	}
	
	/**
	 * 编辑页面
	 * @param order_id
	 * @return
	 */
	public Service_order getService_order_edit(int order_id){
		return (Service_order) sqlMapClient.queryForObject("task.getService_order_edit", order_id);
	}
	
	/**
	 * 提交更新 预约管理
	 * @param map
	 * @return
	 */
	public Integer updataService_order(Map map){
		return sqlMapClient.update("task.updataService_order", map);
	}
}
