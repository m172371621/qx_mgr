package com.brilliantreform.sc.community.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.community.po.TreeNode;

@Repository("communityDao")
public class CommunityDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	public Community getCommunityInfo(int cid){
		return (Community)sqlMapClient.queryForObject("community.getCommunityInfo",cid);
	}
	
	@SuppressWarnings("unchecked")
	public List<Community> getCommunityList(){
		return (List<Community>)sqlMapClient.queryForList("community.getCommunityList");
	}
	
	@SuppressWarnings("unchecked")
	public List<TreeNode> getOrgList(Map<String,Object> map){
		return (List<TreeNode>)sqlMapClient.queryForList("community.getOrgList",map);
	}

    public List<TreeNode> getAdminOrgList() {
        return (List<TreeNode>)sqlMapClient.queryForList("community.getAdminOrgList");
    }
	
	public Community getOrgDetail(Map<String,Object> map){
		return (Community) sqlMapClient.queryForObject("community.getOrgDetail", map);
	}
	
	public Integer insertCommunity(Community com){
		return (Integer) sqlMapClient.insert("community.insertCommunity", com);
	}
	
	public Integer updateCommunity(Community com){
		return (Integer) sqlMapClient.insert("community.updateCommunity", com);
	}

    public List<Community> findCommunityByParent(Integer pid) {
        return sqlMapClient.queryForList("community.findCommunityByParent", pid);
    }

    public Community getCommunityById(int community_id){
        return (Community)sqlMapClient.queryForObject("community.getCommunityById", community_id);
    }

    public List<Map> findCommunityTree() {
        return sqlMapClient.queryForList("community.findCommunityTree");
    }

    public List<Map> findSecondUserCommunity(int user_id) {
        return sqlMapClient.queryForList("community.findSecondUserCommunity", user_id);
    }

    public List<Map> findSecondAdminCommunity() {
        return sqlMapClient.queryForList("community.findSecondAdminCommunity");
    }

    public Community getTotalCommunity() {
        return (Community) sqlMapClient.queryForObject("community.getTotalCommunity");
    }
}
