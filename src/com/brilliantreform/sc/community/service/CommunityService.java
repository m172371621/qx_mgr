package com.brilliantreform.sc.community.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.community.dao.CommunityDao;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.community.po.TreeNode;

@Service("communityService")
public class CommunityService {

	
	@Autowired
	private CommunityDao communityDao;
	
	public Community getCommunity(int id){
		return communityDao.getCommunityInfo(id);
	}
	
	public List<Community> getCommunityList(){
		return communityDao.getCommunityList();
	}
	
	public List<TreeNode> getOrgList(Map<String,Object> map){
		return communityDao.getOrgList(map);
	}

    public List<TreeNode> getAdminOrgList() {
        return communityDao.getAdminOrgList();
    }
	
	public Community getOrgDetail(Map<String,Object> map){
		return communityDao.getOrgDetail(map);
	}
	
	public Integer insertCommunity(Community com){
		return communityDao.insertCommunity(com);
	}
	public Integer updateCommunity(Community com){
		return communityDao.updateCommunity(com);
	}

    public List<Community> findCommunityByParent(Integer pid) {
        return communityDao.findCommunityByParent(pid);
    }

    public Community getCommunityById(int community_id){
        return communityDao.getCommunityById(community_id);
    }

    public List<Map> findCommunityTree() {
        return communityDao.findCommunityTree();
    }

    public List<Map> findSecondUserCommunity(int user_id) {
        return communityDao.findSecondUserCommunity(user_id);
    }

    public List<Map> findSecondAdminCommunity() {
        return communityDao.findSecondAdminCommunity();
    }

    public Community getTotalCommunity() {
        return communityDao.getTotalCommunity();
    }
}
