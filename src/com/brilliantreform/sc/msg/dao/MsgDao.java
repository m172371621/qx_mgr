package com.brilliantreform.sc.msg.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.community.po.Community;

@Repository("msgDao")
public class MsgDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	public Community getCommunityInfo(int cid){
		return (Community)sqlMapClient.queryForObject("community.getCommunityInfo",cid);
	}
	
	@SuppressWarnings("unchecked")
	public List<Community> getCommunityList(){
		return (List<Community>)sqlMapClient.queryForList("community.getCommunityList");
	}
}
