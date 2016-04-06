package com.brilliantreform.sc.task.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.task.dao.TaskDao;
import com.brilliantreform.sc.task.po.PropertyInfo;
import com.brilliantreform.sc.task.po.Service_order;
import com.brilliantreform.sc.task.po.Task;
import com.brilliantreform.sc.task.po.TaskMedia;
import com.brilliantreform.sc.task.po.TaskProperty;
import com.brilliantreform.sc.task.po.TaskQueryBean;

@Service("taskService")
public class TaskService {
	private static Logger logger = Logger.getLogger(TaskService.class);

	@Autowired
	private TaskDao  taskDao;
	
	/**
	 * 获取任务列表（时间顺序）
	 * @param begin
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Task> getTaskByTime(Integer begin,Integer end){
		logger.info("into TaskService getTaskByTime:"+begin+";"+end);
		
		Map map = new HashMap();
		map.put("begin", begin);
		map.put("end", end);
		
		List<Task> list = taskDao.getTaskByTime(map);
			
		logger.info("TaskService getTaskByTime:"+list);
		return list;
	}
	
	/**
	 * 获取任务列表（时间倒序）
	 * @param begin
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Task> getTaskByTimeDesc(Integer begin,Integer end){
		logger.info("into TaskService getTaskByTimeDesc:"+begin+";"+end);
		
		Map map = new HashMap();
		map.put("begin", begin);
		map.put("end", end);
		
		List<Task> list = taskDao.getTaskByTimeDesc(map);
			
		logger.info("TaskService getTaskByTimeDesc:"+list);
		return list;
	}
	
	/**
	 * 获取任务列表（小费金额顺序）
	 * @param begin
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Task> getTaskByTip(Integer begin,Integer end){
		logger.info("into TaskService getTaskByTip:"+begin+";"+end);
		
		Map map = new HashMap();
		map.put("begin", begin);
		map.put("end", end);
		
		List<Task> list = taskDao.getTaskByTip(map);
			
		logger.info("TaskService getTaskByTip:"+list);
		return list;
	}
	
	/**
	 * 获取任务列表（小费金额倒序）
	 * @param begin
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Task> getTaskByTipDesc(Integer begin,Integer end){
		logger.info("into TaskService getTaskByTipDesc:"+begin+";"+end);
		
		Map map = new HashMap();
		map.put("begin", begin);
		map.put("end", end);
		
		List<Task> list = taskDao.getTaskByTipDesc(map);
			
		logger.info("TaskService getTaskByTipDesc:"+list);
		return list;
	}
	/**
	 * 获取用户接受的任务列表（时间倒序）
	 * @param begin
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Task> getTaskByUserReceive(Integer begin,Integer end,Integer user_id){
		logger.info("into TaskService getTaskByUserReceive:"+begin+";"+end+";"+user_id);
		
		Map map = new HashMap();
		map.put("begin", begin);
		map.put("end", end);
		map.put("user_id", user_id);
		
		List<Task> list = taskDao.getTaskByUserReceive(map);
			
		logger.info("TaskService getTaskByUserReceive:"+list);
		return list;
	}
	
	/**
	 * 获取用户发送的任务列表（时间倒序）
	 * @param begin
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Task> getTaskByUserSend(Integer begin,Integer end,Integer user_id){
		logger.info("into TaskService getTaskByTipDesc:"+begin+";"+end+";"+user_id);
		
		Map map = new HashMap();
		map.put("begin", begin);
		map.put("end", end);
		map.put("user_id", user_id);
		
		List<Task> list = taskDao.getTaskByUserSend(map);
			
		logger.info("TaskService getTaskByUserSend:"+list);
		return list;
	}
	
	/**
	 * 获取任务详情
	 * @param task_id
	 * @param Task
	 * @return
	 */
	public Task getTaskById(Integer task_id){
		logger.info("into TaskService getTaskById:"+task_id+";");
		
		Task task = taskDao.getTaskById(task_id);
		
		List<TaskMedia> list = this.getTaskMedia(task.getTask_id());
		if(list!=null && list.size() > 0)
		{
			List<String> photoes = new ArrayList<String>();
			List<String> audioes = new ArrayList<String>();
			for(TaskMedia tm : list)
			{

				if(tm.getType() == 2)
				{
					photoes.add(tm.getMedia_url());
				}else if(tm.getType() == 3)
				{
					audioes.add(tm.getMedia_url());
				}
			}
			
			task.setAudio(audioes);
			task.setPhoto(photoes);
		}

			
		logger.info("TaskService getTaskById:"+task);
		
		return task;
	}
	
	/***
	 *创建任务
	 * @param task
	 */
	public Integer insertTask(Task task){
		logger.info("into TaskService insertTask:"+task+";");
		return taskDao.insertTask(task);
	}
	
	/**
	 * 创建任务相关多媒体文件
	 * @param media
	 */
	public void insertTaskMedia(TaskMedia media){
		logger.info("into TaskService insertTaskMedia:"+media+";");
		taskDao.insertTaskMedia(media);
	}
	
	/**
	 * 更新任务状态
	 * @param media
	 */
	public void updateTask(Task task){
		logger.info("into TaskService updateTask:"+task+";");
		taskDao.updateTask(task);
	}
	
	/**
	 * 创建物业任务
	 * @param media
	 */
	public Integer insertTaskProperty(TaskProperty task){
		logger.info("into TaskService insertTaskProperty:"+task+";");
		return taskDao.insertTaskProperty(task);
	}
	
	/**
	 * 获取物业任务列表（时间倒序）
	 * @param begin
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TaskProperty> getTaskPropertyList(Integer userid,Integer begin,Integer end){
		logger.info("into TaskService getTaskPropertyList:"+begin+";"+end+";");
		
		Map map = new HashMap();
		map.put("begin", begin);
		map.put("end", end);
		map.put("userid", userid);
		
		List<TaskProperty> list = taskDao.getTaskPropertyList(map);
			
		logger.info("TaskService getTaskPropertyList:"+list);
		return list;
	}
	
	/**
	 * 获取物业任务详情
	 * @param task_id
	 * @param Task
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public TaskProperty getTaskProperty(Integer task_id){
		logger.info("into TaskService getTaskProperty:"+task_id+";");
		
		TaskProperty task = taskDao.getTaskProperty(task_id);
		
		Map map = new HashMap();
		map.put("task_id", task_id);
		map.put("task_type", 2);
		
		List<TaskMedia> list = taskDao.getTaskMedia(map);
		
		List<String> pics = new ArrayList<String>();
		List<String> audios = new ArrayList<String>();
 		for(TaskMedia media : list)
		{
			if(media.getType() == 2)
			{
				pics.add(media.getMedia_url());
			}
			
			if(media.getType() == 3)
			{
				audios.add(media.getMedia_url());
			}
		}
 		
 		task.setPhoto(pics);
 		task.setAudio(audios);
			
		logger.info("TaskService getTaskProperty:"+task);
		return task;
	}
	
	/**
	 * 获取任务媒体
	 * @param task_id
	 * @param Task
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TaskMedia> getTaskMedia(Integer task_id){
		logger.info("into TaskService getTaskMedia:"+task_id+";");
		
		
		Map map = new HashMap();
		map.put("task_id", task_id);
		
		List<TaskMedia> list = taskDao.getTaskMedia(map);
			
		logger.info("TaskService getTaskMedia:"+list);
		
		return list;
	}
	
	
	public List<Task> searchTask(TaskQueryBean searchBean){
		return taskDao.searchTask(searchBean);
	}
	
	public Integer countSearchTask(TaskQueryBean searchBean){
		return taskDao.countSearchTask(searchBean);
	}
	
	public List<TaskProperty> searchTaskProperty(TaskQueryBean searchBean){
		return taskDao.searchTaskProperty(searchBean);
	}
	
	public Integer countTaskProperty(TaskQueryBean searchBean){
		return taskDao.countTaskProperty(searchBean);
	}
	
	/**
	 * 更新任务状态
	 * @param media
	 */
	public void updateTaskProperty(TaskProperty task){
		logger.info("into TaskService updateTaskProperty:"+task+";");
		taskDao.updateTaskProperty(task);
	}
	
	/**
	 * 查物业公告信息
	 * @param map
	 * @return
	 */
	public List<PropertyInfo> propertyInfoList(Map map){
		logger.info("into TaskService propertyInfoList:"+map+";");
		return taskDao.propertyInfoList(map);
	}
	
	/**
	 * 查物业公告信息   总记录数
	 * @param community_id
	 * @return
	 */
	public int propertyInfoListCount(Map map){
		logger.info("into TaskService propertyInfoListCount:"+map+";");
		return (Integer)taskDao.propertyInfoListCount(map);
	}
	
	/**
	 * 物业公告录入
	 * @param info
	 */
	public void propertyInfo_ADD(PropertyInfo info){
		logger.info("into TaskService propertyInfo_ADD:"+info+";");
		taskDao.propertyInfo_ADD(info);
	}
	/**
	 * 物业公告信息编辑
	 * @param map
	 */
	public void editPropertyInfo(Map map){
		logger.info("into TaskService propertyInfo_ADD:"+map+";");
		taskDao.editPropertyInfo(map);
	}
	/**
	 * 查询编辑当前的一个PropertyInfo对象 显示在编辑页面
	 * @param propertyInfoListOne
	 * @return
	 */
	public PropertyInfo propertyInfoOne(int propertyInfoOne){
		logger.info("into TaskService propertyInfo_ADD:"+propertyInfoOne+";");
		return taskDao.propertyInfoOne(propertyInfoOne);
	}
	/**
	 * 删除物业公告信息 根据 property_information_id 删除
	 * @param property_information_id
	 */
	public void delPropertyInfo(int property_information_id){
		logger.info("into TaskService propertyInfo_ADD:"+property_information_id+";");
		taskDao.delPropertyInfo(property_information_id);
	}
	
	/**
	 * 预约管理
	 * @param communityId
	 * @return
	 */
	public List<Service_order> getService_order(Map map){
		logger.info("TaskService getService_order"+map+";");
		return taskDao.getService_order(map);
	}
	
	/**
	 * 预约管理分页
	 */
	public Integer getService_order_count(Map map){
		logger.info("TaskService getService_order"+map+";");
		return taskDao.getService_order_count(map);
	}

    public List<Map> searchServiceOrder(Map param) {
        return taskDao.searchServiceOrder(param);
    }

    public int searchServiceOrderCount(Map param) {
        return taskDao.searchServiceOrderCount(param);
    }

    public Map getServiceOrderById(int order_id) {
        return taskDao.getServiceOrderById(order_id);
    }

    public void editServiceOrder(int order_id, Integer order_status, String order_dec) {
        taskDao.editServiceOrder(order_id, order_status, order_dec);
    }

	/**
	 * 删除预约 
	 * @param map
	 * @return
	 */
	public Integer delService_order(Map map){
		logger.info("TaskService getService_order"+map+";");
		return taskDao.delService_order(map);
	}
	
	/**
	 * 编辑页面
	 * @param order_id
	 * @return
	 */
	public Service_order getService_order_edit(int order_id){
		return taskDao.getService_order_edit(order_id);
	}
	
	/**
	 * 提交更新 预约管理
	 * @param map
	 * @return
	 */
	public Integer updataService_order(Map map){
		return taskDao.updataService_order(map);
	}
}
