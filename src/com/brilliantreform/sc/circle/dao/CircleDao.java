package com.brilliantreform.sc.circle.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.circle.po.CircleFriend;
import com.brilliantreform.sc.circle.po.CircleMedia;
import com.brilliantreform.sc.circle.po.CircleReply;
import com.brilliantreform.sc.circle.po.CircleSaid;

@Repository("circleDao")
public class CircleDao {
	
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	@SuppressWarnings("unchecked")
	public List<CircleFriend> getNeighbors(Map map)
	{
		return (List<CircleFriend>)sqlMapClient.queryForList("circle.getNeighbors", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getRelations(Map map)
	{
		return (List<Integer>)sqlMapClient.queryForList("circle.getRelations", map);
	}
	
	public Integer countNeighbors(Integer cid)
	{
		return (Integer)sqlMapClient.queryForObject("circle.countNeighbors", cid);
	}
	
	public void addFriend(CircleFriend circleFriend)
	{
		sqlMapClient.insert("circle.addFriend", circleFriend);
	}
	
	@SuppressWarnings("unchecked")
	public void delFriend(Map map)
	{
		sqlMapClient.delete("circle.delFriend", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<CircleFriend> getFriend(Map map)
	{
		return (List<CircleFriend>)sqlMapClient.queryForList("circle.getFriend", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<CircleFriend> getWhosFriend(Map map)
	{
		return (List<CircleFriend>)sqlMapClient.queryForList("circle.getWhosFriend", map);
	}
	
	public Integer createSaid(CircleSaid circleSaid)
	{
		return (Integer)sqlMapClient.insert("circle.createSaid", circleSaid);
	}
	
	public void insertMedia(CircleMedia circleMedia)
	{
		sqlMapClient.insert("circle.insertMedia", circleMedia);
	}
	
	public void delSaid(Integer said_id)
	{
		sqlMapClient.delete("circle.delSaid", said_id);
	}
	
	public void delReply(Integer said_id)
	{
		sqlMapClient.delete("circle.delReply", said_id);
	}
	
	public void delReplyById(Integer reply_id)
	{
		sqlMapClient.delete("circle.delReplyById", reply_id);
	}
	
	public void delMedia(Integer said_id)
	{
		sqlMapClient.delete("circle.delMedia", said_id);
	}
	
	public void createReply(CircleReply circleReply)
	{
		sqlMapClient.insert("circle.createReply", circleReply);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CircleSaid> getUserSaid(Map map)
	{
		return (List<CircleSaid>)sqlMapClient.queryForList("circle.getUserSaid", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<CircleSaid> getFriendSaid(Map map)
	{
		return (List<CircleSaid>)sqlMapClient.queryForList("circle.getFriendSaid", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<CircleMedia> getSaidMedia(Integer said_id)
	{
		return (List<CircleMedia>)sqlMapClient.queryForList("circle.getSaidMedia", said_id);
	}
	
	@SuppressWarnings("unchecked")
	public List<CircleReply> getSaidReply(Integer said_id)
	{
		return (List<CircleReply>)sqlMapClient.queryForList("circle.getSaidReply", said_id);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map> getSaidGood(Integer said_id)
	{
		return (List<Map>)sqlMapClient.queryForList("circle.getSaidGood", said_id);
	}
	
	public Integer countReply(Integer said_id)
	{
		return (Integer)sqlMapClient.queryForObject("circle.countReply", said_id);
	}
	
}
