package com.brilliantreform.sc.comment.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.comment.po.CommentServcie;
import com.brilliantreform.sc.comment.po.CommentTask;

@Repository("commentDao")
public class CommentDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	public void insertCommentService(CommentServcie commentServcie)
	{
		sqlMapClient.insert("comment.insertCommentservcie", commentServcie);
	}
	
	public void insertCommentTask(CommentTask commentTask)
	{
		sqlMapClient.insert("comment.insertCommenttask", commentTask);
	}
	
	@SuppressWarnings("unchecked")
	public Map getCommentLevel(Map map)
	{
		return (Map)sqlMapClient.queryForObject("comment.getCommentLevel", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<CommentServcie> getCommentList(Map map)
	{
		return (List<CommentServcie>)sqlMapClient.queryForList("comment.getCommentList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<CommentTask> getTaskComment(Map map)
	{
		return (List<CommentTask>)sqlMapClient.queryForList("comment.getCommentTask", map);
	}
	
}
