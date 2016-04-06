package com.brilliantreform.sc.user.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brilliantreform.sc.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.service.po.ServiceVo;
import com.brilliantreform.sc.system.xmpp.XmppUtil;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.user.mgrpo.Relation;
import com.brilliantreform.sc.user.mgrpo.Right;
import com.brilliantreform.sc.user.mgrpo.Role;
import com.brilliantreform.sc.user.mgrpo.UserSearchBean;
import com.brilliantreform.sc.user.po.LoginInfo;
import com.brilliantreform.sc.user.po.UserFeature;
import com.brilliantreform.sc.user.po.UserInfo;

@Repository("userDao")
public class UserDao {

	private static Logger logger = Logger.getLogger(UserDao.class);

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	public UserInfo getUserInfo(int userid) {
		return (UserInfo) sqlMapClient.queryForObject("user.getUserInfo",
				userid);
	}
	
	public LoginInfo getUserByPhone(String phone) {
		return (LoginInfo) sqlMapClient.queryForObject("user.getUserByPhone",
				phone);
	}
	
	
	public LoginInfo getUserShowInfo(int userId)
	{
		return (LoginInfo) sqlMapClient.queryForObject("user.getUserShowInfo",
				userId);
	}
	
	public void resetPassword(LoginInfo loginInfo)
	{
		sqlMapClient.update("user.resetPassword",
				loginInfo);
	}
	

	@SuppressWarnings("unchecked")
	public List<UserFeature> getUserInterest() {
		return (List<UserFeature>) sqlMapClient.queryForList(
				"user.getInterest");
	}

	@SuppressWarnings("unchecked")
	public List<UserFeature> getUserProfession() {
		return (List<UserFeature>) sqlMapClient.queryForList(
				"user.getProfession");
	}

	public String getName(String name) {
		return (String) sqlMapClient.queryForObject("user.getName", name);
	}

	public String getPhone(String phone) {
		return (String) sqlMapClient.queryForObject("user.getPhone", phone);
	}

	public int registUser(UserInfo userInfo) {

		int id = -1;
		try {
			
			if(!XmppUtil.getInstance().xmppRegist(userInfo.getLoginName(), userInfo.getPassword()))
			{
				logger.error("UserDao registUser: xmpp regist error!");
				return -1;
			}
			
			sqlMapClient.getSqlMapClient().startTransaction();

			id = (Integer) sqlMapClient.insert("user.insertLoginInfo",
					userInfo);
			userInfo.setUserId(id);

			sqlMapClient.insert("user.insertBaseInfo", userInfo);

			sqlMapClient.getSqlMapClient().commitTransaction();
		} catch (Exception e) {
			logger.error("UserDao registUser:" + e.getMessage());
			id = -1;
		} finally {
			try {
				sqlMapClient.getSqlMapClient().endTransaction();
			} catch (SQLException e) {
				logger.error("UserDao registUser:" + e.getMessage());
			} // 事务结束
		}

		return id;

	}
	
	public void updateUser(UserInfo userInfo) {

		try {
			sqlMapClient.getSqlMapClient().startTransaction();

			sqlMapClient.update("user.updateLoginInfo",
					userInfo);

			sqlMapClient.update("user.updateUserInfo",
					userInfo);

			sqlMapClient.getSqlMapClient().commitTransaction();
		} catch (SQLException e) {
			logger.error("UserDao updateUser:" + e.getMessage());
		} finally {
			try {
				sqlMapClient.getSqlMapClient().endTransaction();
			} catch (SQLException e) {
				logger.error("UserDao updateUser:" + e.getMessage());
			} // 事务结束
		}


	}
	
	@SuppressWarnings("unchecked")
	public void insertToken(Map map)
	{
		sqlMapClient.insert("user.insertToken", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map getToken(String token)
	{
		return (Map)sqlMapClient.queryForObject("user.getToken", token);
	}
	
	public void delToken(String token)
	{
		sqlMapClient.delete("user.deleteToken", token);
	}
	
	// mgr--------------------------------------------------------
	public MgrUser getLoginInfo(String name) {
		return (MgrUser)sqlMapClient.queryForObject(
				"user.getLoginInfo",name);
	}
	
	public MgrUser getMgrUserInfo(Integer userid) {
		return (MgrUser)sqlMapClient.queryForObject(
				"user.getMgrUserInfo",userid);
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> getRole() {
		return (List<Role>) sqlMapClient.queryForList(
				"user.getRole");
	}
	
	@SuppressWarnings("unchecked")
	public List<Right> getRight() {
		return (List<Right>) sqlMapClient.queryForList(
				"user.getRight");
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> getUserRole(Integer userid) {
		return (List<Role>) sqlMapClient.queryForList(
				"user.getUserRole", userid);
	}
	
	@SuppressWarnings("unchecked")
	public List<Community> getUserCommunity(Integer userid) {
		return (List<Community>) sqlMapClient.queryForList(
				"user.getUserCommunity", userid);
	}

	@SuppressWarnings("unchecked")
	public List<ServiceVo> getUserService(Integer userid) {
		return (List<ServiceVo>) sqlMapClient.queryForList(
				"user.getUserService",userid);
	}
	
	@SuppressWarnings("unchecked")
	public List<Right> getUserRight(Integer userid) {
		return (List<Right>) sqlMapClient.queryForList(
				"user.getUserRight",userid);
	}

	public void insertUserRole(Relation relation) {
		sqlMapClient.insert("user.insertUserRole",relation);
	}
	
	public void insertUserService(Relation relation) {
		sqlMapClient.insert("user.insertUserService",relation);
	}
	
	public void insertUserCommunity(Relation relation) {
		sqlMapClient.insert("user.insertUserCommunity",relation);
	}
	
	public void insertRoleRight(Relation relation) {
		sqlMapClient.insert("user.insertRoleRight",relation);
	}
	
	public void deleteUserRole(Integer userid) {
		sqlMapClient.delete("user.deleteUserRole", userid);
	}
	
	public void deleteUserCommunity(Integer userid) {
		sqlMapClient.delete("user.deleteUserCommunity", userid);
	}
	
	public void deleteUserService(Integer userid) {
		sqlMapClient.delete("user.deleteUserService", userid);
	}
	
	public void deleteRoleRight(Integer roleid) {
		sqlMapClient.delete("user.deleteRoleRight", roleid);
	}
	
	@SuppressWarnings("unchecked")
	public List<MgrUser> listMgrUserA(UserSearchBean searchBean) {
		return (List<MgrUser>) sqlMapClient.queryForList(
				"user.listMgrUserA",searchBean);
	}
	
	@SuppressWarnings("unchecked")
	public List<MgrUser> listMgrUser(UserSearchBean searchBean) {
		return (List<MgrUser>) sqlMapClient.queryForList(
				"user.listMgrUser",searchBean);
	}

	public int countMgrUserA(UserSearchBean searchBean) {
		return (Integer) sqlMapClient.queryForObject(
				"user.countMgrUserA",searchBean);
	}
	
	public int countMgrUser(UserSearchBean searchBean) {
		return (Integer) sqlMapClient.queryForObject(
				"user.countMgrUser", searchBean);
	}
	
	public int countUserName(String name) {
		return (Integer) sqlMapClient.queryForObject(
				"user.countUserName", name);
	}

	public Integer insertMgrUser(MgrUser user) {
		return (Integer) sqlMapClient.insert(
				"user.insertMgrUser", user);
	}
	
	public void updateMgrUser(MgrUser user) {
		sqlMapClient.update("user.updateMgrUser",user);
	}
	
	@SuppressWarnings("unchecked")
	public List<MgrUser> listQxUser(UserSearchBean searchBean) {
		return (List<MgrUser>) sqlMapClient.queryForList(
				"user.listQxUser", searchBean);
	}

	public int countQxUser(UserSearchBean searchBean) {
		return (Integer) sqlMapClient.queryForObject(
				"user.countQxUser",searchBean);
	}
	
	/**
	 * 账户冻结
	 * @param user_id
	 */
	public void accountDisable(int user_id){
		sqlMapClient.update("user.accountDisable", user_id);
	}
	
	/**
	 * 账户解冻
	 * @param user_id
	 */
	public void accountEnable(int user_id){
		sqlMapClient.update("user.accountEnable", user_id);
	}

	public List<Map> listQxUserV4(Map param) {
		return (List<Map>) sqlMapClient.queryForList("user.listQxUserV4", param);
	}

	public int countQxUserV4(Map param) {
		return (Integer) sqlMapClient.queryForObject("user.countQxUserV4", param);
	}

	public List<MgrUser> listMgrUserAV4(Map param) {
		return (List<MgrUser>) sqlMapClient.queryForList("user.listMgrUserAV4",param);
	}

	public List<MgrUser> listMgrUserV4(Map param) {
		return (List<MgrUser>) sqlMapClient.queryForList("user.listMgrUserV4",param);
	}

	public int countMgrUserAV4(Map param) {
		return (Integer) sqlMapClient.queryForObject("user.countMgrUserAV4", param);
	}

	public int countMgrUserV4(Map param) {
		return (Integer) sqlMapClient.queryForObject("user.countMgrUserV4", param);
	}

    public List<Map> findMgrUserRoleByUid(int user_id) {
        return sqlMapClient.queryForList("user.findMgrUserRoleByUid", user_id);
    }

    public List<Map> findMgrUserCommunityByUid(int user_id) {
        return sqlMapClient.queryForList("user.findMgrUserCommunityByUid", user_id);
    }

    public void insertMgrUserInfo(MgrUser user) {
        sqlMapClient.insert("user.insertMgrUserInfo", user);
    }

    public void updateMgrUserInfo(MgrUser user) {
        sqlMapClient.update("user.updateMgrUserInfo", user);
    }

    public void saveMgrUser(MgrUser user) {
        if(user != null) {
            if(user.getUser_id() == null) {
                user.setCreateTime(new Date());
                user.setUpdateTime(new Date());
                insertMgrUserInfo(user);
            } else {
                user.setUpdateTime(new Date());
                updateMgrUserInfo(user);
            }
        }
    }

    public void changeCommunity(int user_id, int cid, int sub_cid) {
        Map param = new HashMap();
        param.put("user_id", user_id);
        param.put("cid", cid);
        param.put("sub_cid", sub_cid);
        sqlMapClient.update("user.changeCommunity", param);
    }

	public List<Map> searchDeliveryer(Map param) {
		return sqlMapClient.queryForList("user.searchDeliveryer", param);
	}

	public int searchDeliveryerCount(Map param) {
		return CommonUtil.safeToInteger(sqlMapClient.queryForObject("user.searchDeliveryerCount", param), 0);
	}
}
