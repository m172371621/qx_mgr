package com.brilliantreform.sc.community.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.community.dao.CommunityDao;
import com.brilliantreform.sc.community.dao.CommunityRegisterDao;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.community.po.Community_register;

@Service
public class ComunityRegisterService {

	@Autowired
	private CommunityRegisterDao communityRegisterDao;
	
	@Autowired
	private CommunityDao communityDao;
	
	/**
	 * 添加申请
	 * @param register
	 * @return
	 */
	public Integer register(Community_register register){
		return communityRegisterDao.register(register);
	}
	
	/**
	 * 查询申请列表
	 * @param map
	 * @return
	 */
	public List<Community_register> queryRegisterList(Map<String,Object> map){
		return communityRegisterDao.queryRegisterList(map);
	}
	
	/**
	 * 查询申请总数
	 * @param map
	 * @return
	 */
	public Integer queryRegisterCount(Map<String,Object> map){
		return communityRegisterDao.queryRegisterCount(map);
	}
	
	/**
	 * 查询明细
	 * @param map
	 * @return
	 */
	public Community_register queryRegisterDetail(Map<String,Object> map){
		return communityRegisterDao.queryRegisterDetail(map);
	}
	
	/**
	 * 审核申请
	 * @param map
	 * @return
	 */
	public Integer auditRegister(Community_register com){
		communityRegisterDao.auditRegister(com);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("community_register_id", com.getCommunity_register_id());
		Community_register register=communityRegisterDao.queryRegisterDetail(map);
		Community community=new Community();
		community.setCommunity_name(register.getCommunity_name());
		community.setCommunity_addr(register.getCommunity_addr());
		community.setOrg_info_pid(1);
		community.setOrg_info_type(3);
		community.setCommunity_Dec(register.getCommunity_name());
		community.setOrg_info_person(register.getCommunity_person_name());
		community.setOrg_info_phone(register.getCommunity_person_phone());
		communityDao.insertCommunity(community);
		return 0;
	}
}
