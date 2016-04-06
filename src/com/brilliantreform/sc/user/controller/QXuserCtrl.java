package com.brilliantreform.sc.user.controller;

import com.brilliantreform.sc.circle.po.CircleFriend;
import com.brilliantreform.sc.circle.service.CircleService;
import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.community.service.CommunityService;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.user.mgrpo.UserSearchBean;
import com.brilliantreform.sc.user.po.UserInfo;
import com.brilliantreform.sc.user.service.UserService;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.GridUtil;
import com.brilliantreform.sc.utils.LogerUtil;
import com.brilliantreform.sc.utils.ValidateUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("qxuser.do")
public class QXuserCtrl {

	private static Logger logger = Logger.getLogger(UserCtrl.class);
	
	private static int size = 20;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private CircleService circleService;
	
	@RequestMapping(params = "method=list", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String login(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "list");

		try {
			HttpSession session = request.getSession();

			String cid = CommonUtil.isNull(request.getParameter("cid"));
			String phone = CommonUtil.isNull(request.getParameter("phone"));
			String loginname = CommonUtil.isNull(request
					.getParameter("loginname"));

			String pageIndex = CommonUtil.isNull(request
					.getParameter("pageIndex"));

			Integer i_pageIndex = pageIndex == null ? 1 : Integer
					.parseInt(pageIndex);
			int begin = (i_pageIndex - 1) * size;

			UserSearchBean searchBean = new UserSearchBean();
			searchBean.setBegin(begin);
			searchBean.setSize(size);
			searchBean.setAuthenticate(1);
			if (cid != null && !"0".equals(cid))
				searchBean.setCid(Integer.parseInt(cid));
			if (loginname != null)
				searchBean.setLoginname(loginname);
			if (phone != null)
				searchBean.setPhone(phone);

			int count = 0;
			List<MgrUser> list = null;
		
				count = this.userService.countQxUser(searchBean);
				list = this.userService.listQxUser(searchBean);
			

			request.setAttribute("list", list);
			// request.setAttribute("clist", clist);
			request.setAttribute("pageCount", count % size == 0 ? count / size
					: count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);

			request.setAttribute("searchBean", searchBean);

			return "/jsp/qxuser/qxUser_list";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "/jsp/error";
		}

	}
	
	@RequestMapping(params = "method=createUser", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String createUser(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "addUser");

		String username = CommonUtil.isNull(request.getParameter("username"));
		String password = CommonUtil.isNull(request.getParameter("password"));
		String phone = CommonUtil.isNull(request.getParameter("phone"));
		String name = CommonUtil.isNull(request.getParameter("name"));
		String type = CommonUtil.isNull(request.getParameter("type"));
		String nick = CommonUtil.isNull(request.getParameter("nick"));
		String interest = CommonUtil.isNull(request.getParameter("interest"));
		String profession = CommonUtil.isNull(request.getParameter("profession"));
		String avatar = CommonUtil.isNull(request.getParameter("avatar"));
		String email = CommonUtil.isNull(request.getParameter("email"));
		String floor = CommonUtil.isNull(request.getParameter("floor"));
		String room = CommonUtil.isNull(request.getParameter("room"));
		String community_id = CommonUtil.isNull(request.getParameter("cid"));

		int result_code = 0;
		String result_dec = "OK";
		
		phone = username;
		name = username;
		nick = username;
		password = "123456";
		type = "1";
		
		try {

			// 检查参数
			if (StringUtils.isBlank(username) || username.length() > 32) {
				request.setAttribute("is_new", 1);
				result_code = 1;
				result_dec = "用户名不合法";
			} else if (!userService.checkName(username)) {
				result_code = 2;
				result_dec = "用户名已存在";
			} else if (StringUtils.isBlank(password) || password.length() > 32) {
				result_code = 1;
				result_dec = "密码不合法";
			} else if (!ValidateUtil.checkMobile(phone)) {
				result_code = 1;
				result_dec = "手机号不合法";
			} else if (!userService.checkPhone(phone)) {
				result_code = 3;
				result_dec = "手机号已注册";
			} else if (StringUtils.isBlank(name) || name.length() > 32) {
				result_code = 1;
				result_dec = "参数错误";
			} else if (!ValidateUtil.checkUserType(type)) {
				result_code = 1;
				result_dec = "参数错误";
			} else if (StringUtils.isNotBlank(nick) && nick.length() > 32) {
				result_code = 1;
				result_dec = "参数错误";
			} else if (StringUtils.isNotBlank(interest)
					&& interest.length() > 128) {
				result_code = 1;
				result_dec = "参数错误";
			} else if (StringUtils.isNotBlank(avatar) && avatar.length() > 255) {
				result_code = 1;
				result_dec = "参数错误";
			} else if (StringUtils.isNotBlank(profession)
					&& profession.length() > 128) {
				result_code = 1;
				result_dec = "参数错误";
			} else if (StringUtils.isNotBlank(email) && email.length() > 63) {
				result_code = 1;
				result_dec = "邮件不合法";
			} else if (StringUtils.isNotBlank(floor) && floor.length() > 32) {
				result_code = 1;
				result_dec = "楼栋号不合法";
			} else if (StringUtils.isNotBlank(room) && room.length() > 32) {
				result_code = 1;
				result_dec = "房间号不合法";
			} else if (!(StringUtils.isNotBlank(community_id)
					&& StringUtils.isNumeric(community_id) && community_id
					.length() < 10)) {
				result_code = 1;
				result_dec = "参数错误";
			} else if (communityService.getCommunity(Integer
					.parseInt(community_id)) == null) {
				result_code = 4;
				result_dec = "参数错误";
			}

			logger.info("UserCtrl createAccount: result_dec " + result_dec);

			// 注册用户
			if (result_code == 0) {
				// TODO 头像上传
				UserInfo user = new UserInfo();
				user.setLoginName(username);
				user.setPassword(password);
				user.setPhone(phone);
				user.setUsername(name);
				user.setNick(nick);
				user.setType(Integer.parseInt(type));
				user.setEmail(email);
				user.setInterest(interest);
				user.setProfession(profession);
				user.setAvatar(avatar);
				user.setFloor(floor);
				user.setRoom(room);
				user.setCommunityId(Integer.parseInt(community_id));

				int userId = userService.registUser(user);
				
				if(userId > 0)
				{
					CircleFriend circleFriend = new CircleFriend();
					circleFriend.setMe_id(userId);
					circleFriend.setUser_id(userId);
					circleService.addFriend(circleFriend);
				}
				

				if (userId <= 0) {
					result_code = 5;
					result_dec = "注册失败";
				}
				
				
			}
			
			request.setAttribute("result_code", result_code);
			request.setAttribute("result_dec", result_dec);
					return "/jsp/qxuser/createUser";
				
		} catch (Exception e) {

			logger.error(e.getMessage());
			return "/jsp/error";
		}

	}
	
	@RequestMapping(params = "method=updateUser", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String updateUser(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "updateUser");

		try{
			
			String userId = request.getParameter("userId");
			String cid = request.getParameter("cid");
			
			if(cid != null)
			{
				UserInfo temp = new UserInfo();
				temp.setUserId(Integer.parseInt(userId));
				temp.setCommunityId(Integer.parseInt(cid));
				
				userService.updateUser(temp);
				request.setAttribute("isEdit", true);
			}
			UserInfo u = userService.getUserInfo(Integer.parseInt(userId));
			request.setAttribute("user", u);
			return "/jsp/qxuser/updateUser";
				
		} catch (Exception e) {

			logger.error(e.getMessage());
			return "/jsp/error";
		}

	}

	@RequestMapping(params = "method=showListQxUserPage")
	public String showListQxUserPage(HttpServletRequest request, HttpServletResponse response) {
		return "/new/jsp/qxuser/listQxUser";
	}

	@RequestMapping(params = "method=listV4")
	public void listV4(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
		Pager pager = new Pager();
		try {
			LogerUtil.logRequest(request, logger, "listV4");

			pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

			Map param = GridUtil.parseGridPager(pager);
			param.put("authenticate", 1);
            CommonUtil.parseParamCommunityId(request, param);

			List<Map> list = this.userService.listQxUserV4(param);
			int count = this.userService.countQxUserV4(param);

			pager = GridUtil.setPagerResult(pager, list, count, true);

		} catch (Exception e) {
			logger.error("listV4 error", e);
			pager.setIsSuccess(false);
		}
		CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
	}

    /**
     * 更改区享用户门店<br>
     * 1. 若改成的门店不是映射门店，则直接将user_baseinfo表中的communityId和sub_cid都改成门店id<br>
     * 2. 若改成的门店是映射门店，则将user_baseinfo表中的communityId改成主门店的id，sub_cid改成门店id
     * */
	@RequestMapping(params = "method=changeCommunity")
	public void editUser(HttpServletRequest request, HttpServletResponse response) {
		String result = "fail";
		LogerUtil.logRequest(request, logger, "changeCommunity");
		try{
			Integer user_id = CommonUtil.safeToInteger(request.getParameter("user_id"), null);
			Integer cid = CommonUtil.safeToInteger(request.getParameter("cid"), null);
			if(user_id != null && cid != null) {
				userService.changeCommunity(user_id, cid);
				result = "ok";
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		CommonUtil.outToWeb(response, result);
	}

	@RequestMapping(params = "method=createUserV4")
	public void createUserV4(HttpServletRequest request, HttpServletResponse response) {
		LogerUtil.logRequest(request, logger, "createUserV4");
		Map param = new HashMap();

		String username = CommonUtil.isNull(request.getParameter("username"));
		String password = CommonUtil.isNull(request.getParameter("password"));
		String phone = CommonUtil.isNull(request.getParameter("phone"));
		String name = CommonUtil.isNull(request.getParameter("name"));
		String type = CommonUtil.isNull(request.getParameter("type"));
		String nick = CommonUtil.isNull(request.getParameter("nick"));
		String interest = CommonUtil.isNull(request.getParameter("interest"));
		String profession = CommonUtil.isNull(request.getParameter("profession"));
		String avatar = CommonUtil.isNull(request.getParameter("avatar"));
		String email = CommonUtil.isNull(request.getParameter("email"));
		String floor = CommonUtil.isNull(request.getParameter("floor"));
		String room = CommonUtil.isNull(request.getParameter("room"));
		String community_id = CommonUtil.isNull(request.getParameter("cid"));

		int result_code = 0;
		String result_dec = "OK";

		phone = username;
		name = username;
		nick = username;
		password = "123456";
		type = "1";
		try {
			// 检查参数
			if (StringUtils.isBlank(username) || username.length() > 32) {
				request.setAttribute("is_new", 1);
				result_code = 1;
				result_dec = "用户名不合法";
			} else if (!userService.checkName(username)) {
				result_code = 2;
				result_dec = "用户名已存在";
			} else if (StringUtils.isBlank(password) || password.length() > 32) {
				result_code = 1;
				result_dec = "密码不合法";
			} else if (!ValidateUtil.checkMobile(phone)) {
				result_code = 1;
				result_dec = "手机号不合法";
			} else if (!userService.checkPhone(phone)) {
				result_code = 3;
				result_dec = "手机号已注册";
			} else if (StringUtils.isBlank(name) || name.length() > 32) {
				result_code = 1;
				result_dec = "参数错误";
			} else if (!ValidateUtil.checkUserType(type)) {
				result_code = 1;
				result_dec = "参数错误";
			} else if (StringUtils.isNotBlank(nick) && nick.length() > 32) {
				result_code = 1;
				result_dec = "参数错误";
			} else if (StringUtils.isNotBlank(interest)
					&& interest.length() > 128) {
				result_code = 1;
				result_dec = "参数错误";
			} else if (StringUtils.isNotBlank(avatar) && avatar.length() > 255) {
				result_code = 1;
				result_dec = "参数错误";
			} else if (StringUtils.isNotBlank(profession)
					&& profession.length() > 128) {
				result_code = 1;
				result_dec = "参数错误";
			} else if (StringUtils.isNotBlank(email) && email.length() > 63) {
				result_code = 1;
				result_dec = "邮件不合法";
			} else if (StringUtils.isNotBlank(floor) && floor.length() > 32) {
				result_code = 1;
				result_dec = "楼栋号不合法";
			} else if (StringUtils.isNotBlank(room) && room.length() > 32) {
				result_code = 1;
				result_dec = "房间号不合法";
			} else if (!(StringUtils.isNotBlank(community_id)
					&& StringUtils.isNumeric(community_id) && community_id
					.length() < 10)) {
				result_code = 1;
				result_dec = "参数错误";
			} else if (communityService.getCommunity(Integer
					.parseInt(community_id)) == null) {
				result_code = 4;
				result_dec = "参数错误";
			}
			logger.info("UserCtrl createAccount: result_dec " + result_dec);
			// 注册用户
			if (result_code == 0) {
				// TODO 头像上传
				UserInfo user = new UserInfo();
				user.setLoginName(username);
				user.setPassword(password);
				user.setPhone(phone);
				user.setUsername(name);
				user.setNick(nick);
				user.setType(Integer.parseInt(type));
				user.setEmail(email);
				user.setInterest(interest);
				user.setProfession(profession);
				user.setAvatar(avatar);
				user.setFloor(floor);
				user.setRoom(room);
				user.setCommunityId(Integer.parseInt(community_id));
				int userId = userService.registUser(user);
				if(userId > 0) {
					CircleFriend circleFriend = new CircleFriend();
					circleFriend.setMe_id(userId);
					circleFriend.setUser_id(userId);
					circleService.addFriend(circleFriend);
				}
				if (userId <= 0) {
					result_code = 5;
					result_dec = "注册失败";
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		param.put("result_code", result_code);
		param.put("result_dec", result_dec);
		CommonUtil.outToWeb(response, JSONObject.fromObject(param).toString());
	}
}
