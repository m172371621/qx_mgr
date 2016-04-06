package com.brilliantreform.sc.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brilliantreform.sc.community.service.CommunityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.service.po.ServiceVo;
import com.brilliantreform.sc.user.dao.UserDao;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.user.mgrpo.Relation;
import com.brilliantreform.sc.user.mgrpo.Right;
import com.brilliantreform.sc.user.mgrpo.Role;
import com.brilliantreform.sc.user.mgrpo.UserSearchBean;
import com.brilliantreform.sc.user.po.LoginInfo;
import com.brilliantreform.sc.user.po.UserFeature;
import com.brilliantreform.sc.user.po.UserInfo;

@Service("userService")
public class UserService {

	private static Logger logger = Logger.getLogger(UserService.class);

	@Autowired
	private UserDao userDao;

    @Autowired
    private CommunityService communityService;

	/**
	 * 用户登陆判断
	 * 
	 * @param username
	 * @param password
	 * @return -1 用户不存在 -2 用户状态错误 -3 密码错误 其他： 用户ID
	 */
	public int login(String username, String password) {
		logger.info("into UserService login :" + username + "|" + password);
		int result = 0;

		MgrUser user = userDao.getLoginInfo(username);
		
		if (user == null) {
			result = -9;

			logger.info("end UserService login result:-9");
			return result;
		}

		if (user.getAuthenticate() == 2) {
			result = -2;

			logger.info("end UserService login result:2");
			return result;
		}

		if (password == null || !password.equals(user.getPassword())) {
			result = -3;

			logger.info("end UserService login result:-3");
			return result;
		} else {
			result = user.getUser_id();

			logger.info("end UserService login result:" + result);
			return result;
		}
	}

	public LoginInfo getUserShowInfo(int userId) {
		logger.info("into UserService getUserShowInfo :" + userId);

		LoginInfo user = userDao.getUserShowInfo(userId);

		logger.info("end UserService getUserShowInfo :" + user);
		return user;
	}

	public UserInfo getUserInfo(int userId) {
		logger.info("into UserService getUserInfo :" + userId);

		UserInfo user = userDao.getUserInfo(userId);

		logger.info("end UserService getUserInfo :" + user);
		return user;
	}

	public boolean checkName(String name) {
		logger.info("into UserService checkName :" + name);
		if (null == userDao.getName(name))
			return true;
		else
			return false;
	}

	public boolean checkPhone(String phone) {
		logger.info("into UserService checkPhone :" + phone);
		if (null == userDao.getPhone(phone))
			return true;
		else
			return false;
	}

	public int registUser(UserInfo userInfo) {
		logger.info("into UserService registUser :" + userInfo);
		
		int userid = userDao.registUser(userInfo);
		logger.info("end UserService registUser :" + userid);
		return userid;
	}

	public void resetPassword(LoginInfo loginInfo) {
		logger.info("into UserService resetPassword :" + loginInfo);
		userDao.resetPassword(loginInfo);
	}

	public void updateUser(UserInfo userInfo) {
		logger.info("into UserService updateUser :" + userInfo);
		userDao.updateUser(userInfo);
	}

	/**
	 * 
	 * @param type
	 *            1 Interest 2 Profession
	 * @return
	 */
	public String getFeatures(int type) {
		logger.info("into UserService getFeature :" + type);

		String features = "";
		StringBuffer sb = new StringBuffer();
		
		if (type == 1) {
			
			// 获取兴趣信息
			List<UserFeature> ilist = userDao.getUserInterest();

			for (UserFeature f : ilist) {
				sb.append(f.getFeatureName());
				sb.append("|");
			}

		} else if (type == 2) {

			// 获取职业信息
			List<UserFeature> plist = userDao.getUserProfession();
			sb = new StringBuffer();
			for (UserFeature f : plist) {
				sb.append(f.getFeatureName());
				sb.append("|");
			}
		}

		features = sb.toString();
		if (features.length() > 0) {
			features = features.substring(0, features.length() - 1);
		}

		logger.info("end UserService getFeature :" + features);
		return features;
	}
	
	@SuppressWarnings("unchecked")
	public void insertToken(String token,long time)
	{
		Map map = new HashMap();
		map.put("token", token);
		map.put("time", time);
		
		userDao.insertToken(map);
	}
	
	@SuppressWarnings("unchecked")
	public Map getToken(String token)
	{
		return userDao.getToken(token);
	}
	
	public void delToken(String token)
	{
		userDao.delToken(token);
	}
	
	public LoginInfo getUserByPhone(String phone)
	{
		return userDao.getUserByPhone(phone);
	}
	
	//mgr============================================================
	
	
	public MgrUser getLoginInfo(String name) {
		return userDao.getLoginInfo(name);
	}
	
	public MgrUser getMgrUserInfo(Integer userid) {
		return userDao.getMgrUserInfo(userid);
	}
	

	public List<Role> getRole() {
		return userDao.getRole();
	}
	

	public List<Right> getRight() {
		return userDao.getRight();
	}
	

	public List<Role> getUserRole(Integer userid) {
		return userDao.getUserRole(userid);
	}

	public List<Community> getUserCommunity(Integer userid) {
		return userDao.getUserCommunity(userid);
	}

    /**
     * 获取用户的所属门店，user_id为null的则代表管理员，查询所有的门店出来
     * */
    public List<Community> findUserCommunity(Integer user_id) {
        List<Community> list = new ArrayList<Community>();
        if(user_id == null) {
            list = communityService.getCommunityList();
        } else {
            list = getUserCommunity(user_id);
        }
        return list;
    }

	public List<ServiceVo> getUserService(Integer userid) {
		return userDao.getUserService(userid);
	}
	

	public List<Right> getUserRight(Integer userid) {
		return userDao.getUserRight(userid);
	}

	public void insertUserRole(Relation relation) {
		userDao.insertUserRole(relation);
	}
	
	public void insertUserService(Relation relation) {
		userDao.insertUserService(relation);
	}
	
	public void insertUserCommunity(Relation relation) {
		userDao.insertUserCommunity(relation);
	}
	
	public void insertRoleRight(Relation relation) {
		userDao.insertRoleRight(relation);
	}
	
	public void deleteUserRole(Integer userid) {
		userDao.deleteUserRole(userid);
	}
	
	public void deleteUserCommunity(Integer userid) {
		userDao.deleteUserCommunity(userid);
	}
	
	public void deleteUserService(Integer userid) {
		userDao.deleteUserService(userid);
	}
	
	public void deleteRoleRight(Integer roleid) {
		userDao.deleteRoleRight(roleid);
	}
	
	public List<MgrUser> listMgrUserA(UserSearchBean searchBean) {
		return userDao.listMgrUserA(searchBean);
	}
	
	public List<MgrUser> listMgrUser(UserSearchBean searchBean) {
		return userDao.listMgrUser(searchBean);
	}
	
	
	public int countMgrUserA(UserSearchBean searchBean) {
		return userDao.countMgrUserA(searchBean);
	}
	
	public int countMgrUser(UserSearchBean searchBean) {
		return userDao.countMgrUser(searchBean);
	}
	
	public int countUserName(String name) {
		return userDao.countUserName(name);
	}

	public Integer insertMgrUser(MgrUser user) {
		return userDao.insertMgrUser(user);
	}
	
	public void updateMgrUser(MgrUser user) {
		userDao.updateMgrUser(user);
	}
	
	public List<MgrUser> listQxUser(UserSearchBean searchBean) {
		return userDao.listQxUser(searchBean);
	}
	
	
	public int countQxUser(UserSearchBean searchBean) {
		return userDao.countQxUser(searchBean);
	}
	
	/**
	 *  账户冻结
	 * @param user_id
	 */
	public void accountDisable(int user_id){
		userDao.accountDisable(user_id);
	}
	
	/**
	 *  账户解冻
	 * @param user_id
	 */
	public void accountEnable(int user_id){
		userDao.accountEnable(user_id);
	}

	public List<Map> listQxUserV4(Map param) {
		return userDao.listQxUserV4(param);
	}


	public int countQxUserV4(Map param) {
		return userDao.countQxUserV4(param);
	}

	public List<MgrUser> listMgrUserAV4(Map param) {
		return userDao.listMgrUserAV4(param);
	}

	public List<MgrUser> listMgrUserV4(Map param) {
		return userDao.listMgrUserV4(param);
	}

	public int countMgrUserAV4(Map param) {
		return userDao.countMgrUserAV4(param);
	}

	public int countMgrUserV4(Map param) {
		return userDao.countMgrUserV4(param);
	}

    public List<Map> findMgrUserRoleByUid(int user_id) {
        return userDao.findMgrUserRoleByUid(user_id);
    }

    public List<Map> findMgrUserCommunityByUid(int user_id) {
        return userDao.findMgrUserCommunityByUid(user_id);
    }

    public void saveMgrUser(MgrUser user) {
        userDao.saveMgrUser(user);
    }

    /**
     * 更改区享用户门店<br>
     * 1. 若改成的门店不是映射门店，则直接将user_baseinfo表中的communityId和sub_cid都改成门店id<br>
     * 2. 若改成的门店是映射门店，则将user_baseinfo表中的communityId改成主门店的id，sub_cid改成门店id
     * */
    public void changeCommunity(int user_id, int cid) {
        Community community = communityService.getCommunityById(cid);
        if(community != null) {
            //判断该门店是否是映射出来的门店
            if(community.getOrg_info_pid() == null || community.getOrg_info_pid().intValue() == 0) {
                //主门店
                userDao.changeCommunity(user_id, cid, cid);
            } else {
                //映射出来的门店
                userDao.changeCommunity(user_id, community.getOrg_info_pid(), cid);
            }
        }
    }

	public List<Map> searchDeliveryer(Map param) {
		return userDao.searchDeliveryer(param);
	}

	public int searchDeliveryerCount(Map param) {
		return userDao.searchDeliveryerCount(param);
	}
}
