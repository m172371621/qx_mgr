package com.brilliantreform.sc.comment.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.comment.dao.CommentDao;
import com.brilliantreform.sc.comment.po.CommentServcie;
import com.brilliantreform.sc.comment.po.CommentTask;

@Service("comService")
public class ComService {
	private static Logger logger = Logger.getLogger(ComService.class);

	@Autowired
	private CommentDao commentDao;

	/**
	 * 新增用户评论
	 * 
	 * @param task_id
	 * @param user_id
	 * @return
	 */
	public void userComment(CommentTask commentTask) {
		logger.info("into ComService userComment:" + commentTask);

		commentDao.insertCommentTask(commentTask);
	}

	/**
	 * 获取评论等级
	 * 
	 * @param task_id
	 * @param user_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map getCommentLevel(Integer consultation_id, Integer product_id) {
		logger.info("into ComService getCommentLevel:" + consultation_id + ";"
				+ product_id);

		Map map = new HashMap();
		map.put("consultation_id", consultation_id);
		map.put("product_id", product_id);

		Map comment = commentDao.getCommentLevel(map);

		logger.info("ComService getCommentLevel level:" + comment);
		if (comment == null) {
			comment = new HashMap();
			comment.put("count", 0);
			comment.put("avg", 0);
		} else {
			Long count = (Long) comment.get("count");
			BigDecimal avg = (BigDecimal) comment.get("avg");

			if (count != null)
				comment.put("count", count.intValue());
			else
				comment.put("count", 0);

			if (avg != null)
			{
				Long l_avg = Math.round(avg.doubleValue());
				comment.put("avg", l_avg.intValue());
			}
			else
				comment.put("avg", 0);
		}

		logger.info("end ComService getCommentLevel :" + comment);
		return comment;
	}

	/**
	 * 获取商品/服务评论类别
	 * 
	 * @param task_id
	 * @param user_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CommentServcie> getCommentList(Integer consultation_id,
			Integer product_id, int begin, int size) {
		logger.info("into ComService getCommentList:" + consultation_id + ";"
				+ product_id + ";" + begin + ";" + size);

		Map map = new HashMap();
		map.put("consultation_id", consultation_id);
		map.put("product_id", product_id);
		map.put("begin", begin);
		map.put("size", size);

		List<CommentServcie> list = commentDao.getCommentList(map);

		logger.info("ComService getCommentLevel getCommentList:" + list);
		return list;
	}

	/**
	 * 获取任务的评论
	 * 
	 * @param task_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CommentTask> getTaskCommentByTask(Integer task_id) {
		logger.info("into ComService getTaskCommentByTask:" + task_id);

		Map map = new HashMap();
		map.put("task_id", task_id);

		List<CommentTask> list = commentDao.getTaskComment(map);

		logger.info("ComService getTaskCommentByTask list:" + list);

		List<CommentTask> rlist = new ArrayList<CommentTask>();

		// 只取一条任务发送者 和 接受者的评论
		if (list != null && list.size() >= 1) {
			int i = 0;
			int j = 0;
			for (CommentTask c : list) {
				if (i == 0 && c.getComment_type() != null
						&& c.getComment_type() == 1) {
					rlist.add(c);
					i++;
				}

				if (j == 0 && c.getComment_type() != null
						&& c.getComment_type() == 2) {
					rlist.add(c);
					j++;
				}
			}


			return rlist;
		} 
		
		return rlist;

	}

	public void insertCommentService(CommentServcie commentServcie) {
		this.commentDao.insertCommentService(commentServcie);
	}
}
