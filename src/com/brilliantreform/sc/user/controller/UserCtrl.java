package com.brilliantreform.sc.user.controller;

import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.common.RtnResult;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.community.service.CommunityService;
import com.brilliantreform.sc.service.service.SevService;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.user.mgrpo.Relation;
import com.brilliantreform.sc.user.mgrpo.Role;
import com.brilliantreform.sc.user.mgrpo.UserSearchBean;
import com.brilliantreform.sc.user.service.UserService;
import com.brilliantreform.sc.utils.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user.do")
public class UserCtrl {

	private static Logger logger = Logger.getLogger(UserCtrl.class);

	private static int size = 20;

	@Autowired
	private UserService userService;

	@Autowired
	private CommunityService communityService;

	@Autowired
	private SevService sevService;

	@RequestMapping(params = "method=list", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String login(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "list");

		try {
			HttpSession session = request.getSession();

			String userId = CommonUtil.isNull(request.getParameter("user_id"));
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
			if (userId != null)
				searchBean.setUser_id(Integer.parseInt(userId));
			if (loginname != null)
				searchBean.setLoginname(loginname);

			int count = 0;
			List<MgrUser> list = null;
			if (session.getAttribute("user_isAdmin") != null) {
				count = this.userService.countMgrUserA(searchBean);
				list = this.userService.listMgrUserA(searchBean);
			} else {
				count = this.userService.countMgrUser(searchBean);
				list = this.userService.listMgrUser(searchBean);
			}

			request.setAttribute("list", list);
			// request.setAttribute("clist", clist);
			request.setAttribute("pageCount", count % size == 0 ? count / size
					: count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);

			request.setAttribute("searchBean", searchBean);

			return "/jsp/user/mgrUser_list";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "/jsp/error";
		}

	}

	@RequestMapping(params = "method=add", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String addUser(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "addUser");

		try {
			HttpSession session = request.getSession();

			String type = CommonUtil.isNull(request.getParameter("type"));

			if (type != null) {

				List rlist = this.userService.getRole();
				List clist = this.communityService.getCommunityList();
				List slist = this.sevService.getServiceList(3,null);
				request.setAttribute("rlist", rlist);
				request.setAttribute("clist", clist);
				//request.setAttribute("slist", slist);

				return "/jsp/user/mgrUser_add";

			} else {
				String loginname = CommonUtil.isNull(request
						.getParameter("loginname"));
				String password = CommonUtil.isNull(request
						.getParameter("password"));
				String password1 = CommonUtil.isNull(request
						.getParameter("password1"));
				String phone = CommonUtil.isNull(request.getParameter("phone"));
				String email = CommonUtil.isNull(request.getParameter("email"));
				String role_id = CommonUtil.isNull(request
						.getParameter("role_id"));
				String personName = CommonUtil.isNull(request.getParameter("personName"));

				String cids = request.getParameter("cids");
				String[] sids = request.getParameterValues("sids");

				MgrUser mUser = new MgrUser();
				mUser.setLoginname(loginname);
				mUser.setPassword(password);
				mUser.setEmail(email);
				mUser.setPhone(phone);
				mUser.setPersonName(personName);

				int user_id = 0;
				if (this.userService.countUserName(loginname) == 0) {
					user_id = this.userService.insertMgrUser(mUser);

				}

				if (user_id > 0) {
					Relation relation = new Relation();
					relation.setRole_id(Integer.parseInt(role_id));
					relation.setUser_id(user_id);

					this.userService.insertUserRole(relation);
					
					relation.setCommunity_id(Integer.parseInt(cids));

					this.userService.insertUserCommunity(relation);
						

					if (sids != null) {
						for (String sid : sids) {
							relation.setService_id(Integer.parseInt(sid));

							this.userService.insertUserService(relation);
						}
					}
				} else {
					return "/jsp/error";
				}
				
				request.setAttribute("type", "1");
				request.setAttribute("user_id", "" + user_id);
				return this.editUser(request);
			}
		} catch (Exception e) {

			logger.error(e.getMessage());
			return "/jsp/error";
		}

	}

	@RequestMapping(params = "method=edit", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String editUser(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "editUser");

		try {
			HttpSession session = request.getSession();

			String type = CommonUtil.isNull(request.getParameter("type"));
			if (type == null) {
				type = (String) request.getAttribute("type");
			}

			if (type == null) {
				String loginname = CommonUtil.isNull(request
						.getParameter("loginname"));
				String password = CommonUtil.isNull(request
						.getParameter("password"));
				String password1 = CommonUtil.isNull(request
						.getParameter("password1"));
				String phone = CommonUtil.isNull(request.getParameter("phone"));
				String email = CommonUtil.isNull(request.getParameter("email"));
				String role_id = CommonUtil.isNull(request
						.getParameter("role_id"));
				String str_user_id = CommonUtil.isNull(request
						.getParameter("user_id"));
				Integer user_id = Integer.parseInt(str_user_id);

				String cids = request.getParameter("cids");
				String[] sids = request.getParameterValues("sids");

				MgrUser mUser = new MgrUser();
				mUser.setLoginname(loginname);
				mUser.setPassword(password);
				mUser.setEmail(email);
				mUser.setPhone(phone);
				mUser.setUser_id(user_id);
				this.userService.updateMgrUser(mUser);

				Relation relation = new Relation();
				relation.setUser_id(user_id);
				if (StringUtils.isNotBlank(role_id)) {
					this.userService.deleteUserRole(user_id);

					relation.setRole_id(Integer.parseInt(role_id));
					this.userService.insertUserRole(relation);
				}

				if (cids != null) {

					this.userService.deleteUserCommunity(user_id);
					
						relation.setCommunity_id(Integer.parseInt(cids));

						this.userService.insertUserCommunity(relation);
					
				}

				if (sids != null) {

					this.userService.deleteUserService(user_id);
					for (String sid : sids) {
						relation.setService_id(Integer.parseInt(sid));

						this.userService.insertUserService(relation);
					}
				}

				request.setAttribute("isEdit", true);
			}

			List rlist = this.userService.getRole();
			List clist = this.communityService.getCommunityList();
			//List slist = this.sevService.getServiceList(3,null);
			request.setAttribute("rlist", rlist);
			request.setAttribute("clist", clist);
			//request.setAttribute("slist", slist);

			String user_id = CommonUtil.isNull(request.getParameter("user_id"));
			if (user_id == null) {
				user_id = (String) request.getAttribute("user_id");
			}

			int i_user_id = Integer.parseInt(user_id);
			MgrUser user = this.userService.getMgrUserInfo(i_user_id);
			
			List<Role> list_role = this.userService.getUserRole(i_user_id);
			request.setAttribute("user_list_role", list_role);	
			if(list_role.get(0).getRole_id() != 1)
			{
				List<Community> community_list = this.userService.getUserCommunity(i_user_id);
				request.setAttribute("user_community_list", community_list);		
			}
			
//			if(list_role.get(0).getRole_id() == 4)
//			{
//				List<ServiceVo> service_list = this.userService.getUserService(i_user_id);
//				request.setAttribute("user_service_list", service_list);
//			}
			
			request.setAttribute("user", user);

			return "/jsp/user/mgrUser_edit";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "/jsp/error";
		}

	}

	/**
	 * 账户冻结
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=accountDisable" , method={ RequestMethod.POST})
	public void accountDisable(HttpServletRequest request, HttpServletResponse response){
		LogerUtil.logRequest(request, logger, "accountDisable");
		
		try {
			response.setCharacterEncoding("utf-8");
			int result_code = 0;
			String result_dec = "冻结成功！";
			String user_id = request.getParameter("user_id");
			userService.accountDisable(Integer.parseInt(user_id));
			response.getWriter().print(JsonUtil.result2Json(result_code, result_dec, null));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	 
	 /**
		 * 账户解冻
		 * @param request
		 * @return
		 */
		@RequestMapping(params = "method=accountEnable" , method={ RequestMethod.POST})
		public void accountEnable(HttpServletRequest request, HttpServletResponse response){
			LogerUtil.logRequest(request, logger, "accountEnable");
			
			try {
				response.setCharacterEncoding("utf-8");
				int result_code = 0;
				String result_dec = "解冻成功！";
				String user_id = request.getParameter("user_id");
				userService.accountEnable(Integer.parseInt(user_id));
				response.getWriter().print(JsonUtil.result2Json(result_code, result_dec, null));
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

	@RequestMapping(params = "method=showListMgrUserPage")
	public String showListMgrUserPage(HttpServletRequest request) {
        List roleList = userService.getRole();
        request.setAttribute("roleList", roleList);

        MgrUser mgrUser = (MgrUser)request.getSession().getAttribute("user_info");
        Boolean user_isAdmin = (Boolean)request.getSession().getAttribute("user_isAdmin");
        List<Map> communityList = new ArrayList<Map>();
        if(user_isAdmin != null && user_isAdmin) {
            communityList = communityService.findSecondAdminCommunity();
        } else {
            communityList = communityService.findSecondUserCommunity(mgrUser.getUser_id());
        }

        request.setAttribute("communityList", communityList);

		Integer role_id = CommonUtil.safeToInteger(SettingUtil.getSettingValue("COMMON", "DELIVERY_ROLE"), -1);	//配送员角色id
		request.setAttribute("delivery_role_id", role_id);
		return "/new/jsp/user/listMgrUser";
	}

	@RequestMapping(params = "method=listV4")
	public void listV4(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
		Pager pager = new Pager();
		try {
			LogerUtil.logRequest(request, logger, "listV4");

			pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

			HttpSession session = request.getSession();

			Map param = GridUtil.parseGridPager(pager);
			//param.put("community_id", ((Community)request.getSession().getAttribute("selectCommunity")).getCommunity_id());

			List<MgrUser> list = new ArrayList<MgrUser>();
			int count = 0;

			boolean isAdmin = false;
			try {
				isAdmin = Boolean.parseBoolean(session.getAttribute("user_isAdmin") + "");
			} catch (Exception e) {}

			if (isAdmin) {
				count = this.userService.countMgrUserAV4(param);
				list = this.userService.listMgrUserAV4(param);
			} else {
				MgrUser mgrUser = (MgrUser)request.getSession().getAttribute("user_info");
				param.put("my_user_id", mgrUser.getUser_id());
				count = this.userService.countMgrUserV4(param);
				list = this.userService.listMgrUserV4(param);
			}

			pager = GridUtil.setPagerResult(pager, list, count, true);

		} catch (Exception e) {
			logger.error("listSysJob error", e);
			pager.setIsSuccess(false);
		}
		CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
	}

	@RequestMapping(params = "method=editV4")
	public String editUserV4(HttpServletRequest request, HttpServletResponse response) {
		LogerUtil.logRequest(request, logger, "editUserV4");
		try {
			String type = CommonUtil.isNull(request.getParameter("type"));
			if (type == null) {
				type = (String) request.getAttribute("type");
			}

			if (type == null) {
				String loginname = CommonUtil.isNull(request
						.getParameter("loginname"));
				String password = CommonUtil.isNull(request
						.getParameter("password"));
				String personName = CommonUtil.safeToString(request.getParameter("personName"), null);
				String phone = CommonUtil.isNull(request.getParameter("phone"));
				String email = CommonUtil.isNull(request.getParameter("email"));
				String role_id = CommonUtil.isNull(request
						.getParameter("role_id"));
				String str_user_id = CommonUtil.isNull(request
						.getParameter("user_id"));
				Integer user_id = Integer.parseInt(str_user_id);

				String cid = request.getParameter("cid");

				MgrUser mUser = new MgrUser();
				mUser.setLoginname(loginname);
				mUser.setPassword(password);
				mUser.setEmail(email);
				mUser.setPhone(phone);
				mUser.setUser_id(user_id);
				mUser.setPersonName(personName);
				this.userService.updateMgrUser(mUser);

				Relation relation = new Relation();
				relation.setUser_id(user_id);
				if (StringUtils.isNotBlank(role_id)) {
					this.userService.deleteUserRole(user_id);

					relation.setRole_id(Integer.parseInt(role_id));
					this.userService.insertUserRole(relation);
				}

				if (cid != null) {

					this.userService.deleteUserCommunity(user_id);

					relation.setCommunity_id(Integer.parseInt(cid));

					this.userService.insertUserCommunity(relation);

				}

				CommonUtil.outToWeb(response, "success");
				return null;
			}

			List rlist = this.userService.getRole();
			List clist = this.communityService.getCommunityList();
			//List slist = this.sevService.getServiceList(3,null);
			request.setAttribute("rlist", rlist);
			request.setAttribute("clist", clist);
			//request.setAttribute("slist", slist);

			String user_id = CommonUtil.isNull(request.getParameter("user_id"));
			if (user_id == null) {
				user_id = (String) request.getAttribute("user_id");
			}

			int i_user_id = Integer.parseInt(user_id);
			MgrUser user = this.userService.getMgrUserInfo(i_user_id);

			List<Role> list_role = this.userService.getUserRole(i_user_id);
			request.setAttribute("user_list_role", list_role);
			if(list_role.get(0).getRole_id() != 1)
			{
				List<Community> community_list = this.userService.getUserCommunity(i_user_id);
				request.setAttribute("user_community_list", community_list);
			}

//			if(list_role.get(0).getRole_id() == 4)
//			{
//				List<ServiceVo> service_list = this.userService.getUserService(i_user_id);
//				request.setAttribute("user_service_list", service_list);
//			}

			request.setAttribute("user", user);

			return "/new/jsp/user/editMgrUser";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "/jsp/error";
		}

	}

    @RequestMapping(params = "method=getMgrUserById")
    public void getMgrUserById(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "getMgrUserById");

            Integer user_id = CommonUtil.safeToInteger(request.getParameter("user_id"), null);

            if(user_id != null) {
                MgrUser mgrUser = userService.getMgrUserInfo(user_id);
                if(mgrUser != null) {
                    List<Map> roleList = userService.findMgrUserRoleByUid(user_id);
                    List<Map> communityList = userService.findMgrUserCommunityByUid(user_id);

                    Map map = new HashMap();
                    map.put("mgrUser", mgrUser);
                    map.put("roleList", roleList);
                    map.put("communityList", communityList);

                    CommonUtil.outToWeb(response, JSONObject.fromObject(map).toString());
                }
            }

        } catch (Exception e) {
            logger.error("getMgrUserById error", e);
        }
    }

    @RequestMapping(params = "method=saveMgrUser")
    public void saveMgrUser(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            LogerUtil.logRequest(request, logger, "saveMgrUser");

            Integer user_id = CommonUtil.safeToInteger(request.getParameter("user_id"), null);
            String loginname = request.getParameter("loginname");
            String password = request.getParameter("password");
            String personname = request.getParameter("personname");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String[] role_id_array = request.getParameterValues("role_id");
            String[] community_id_array = request.getParameterValues("community_id");

            if(StringUtils.isNotBlank(loginname) && StringUtils.isNotBlank(password)
                    && ArrayUtils.isNotEmpty(role_id_array) && ArrayUtils.isNotEmpty(community_id_array)) {
                MgrUser user = null;
                if(user_id != null) {
                    user = userService.getMgrUserInfo(user_id);
                } else {
                    user = new MgrUser();
                    if(userService.countUserName(loginname) > 0) {
                        user = null;    //登录名重复，不允许注册
                        result.setMsg("登录名重复");
                    }
                }

                if(user != null) {
                    user.setPassword(password);
                    user.setPersonName(personname);
                    user.setPhone(phone);
					user.setDistri_worker_name(personname);
					user.setDistri_worker_phone(phone);
                    user.setEmail(email);
                    if(user_id == null) {
                        user.setLoginname(loginname);
                        user.setActivation(1);
                        user.setAuthenticate(1);    //已认证
                        user.setType(1);
                    }
                    userService.saveMgrUser(user);

                    userService.deleteUserRole(user.getUser_id());
                    for(String role_id_str : role_id_array) {
                        Integer role_id = CommonUtil.safeToInteger(role_id_str, null);
                        if(role_id != null) {
                            Relation relation = new Relation();
                            relation.setRole_id(role_id);
                            relation.setUser_id(user.getUser_id());
                            userService.insertUserRole(relation);
                        }
                    }

                    userService.deleteUserCommunity(user.getUser_id());
                    for(String community_id_str : community_id_array) {
                        Integer community_id = CommonUtil.safeToInteger(community_id_str, null);
                        if(community_id != null) {
                            Relation relation = new Relation();
                            relation.setCommunity_id(community_id);
                            relation.setUser_id(user.getUser_id());
                            userService.insertUserCommunity(relation);
                        }
                    }

                    result.setResult(true);
                }

            }
        } catch (Exception e) {
            logger.error("saveMgrUser error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

	@RequestMapping(params = "method=showListDeliveryerPage")
	public String showListDeliveryerPage(HttpServletRequest request) {
		MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("user_info");
		Boolean user_isAdmin = (Boolean) request.getSession().getAttribute("user_isAdmin");
		List<Map> communityList = new ArrayList<Map>();
		if(user_isAdmin != null && user_isAdmin) {
			communityList = communityService.findSecondAdminCommunity();
		} else {
			communityList = communityService.findSecondUserCommunity(mgrUser.getUser_id());
		}

		request.setAttribute("communityList", communityList);
		return "/new/jsp/user/listDeliveryer";
	}

	@RequestMapping(params = "method=searchDeliveryer")
	public void searchDeliveryer(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
		Pager pager = new Pager();
		try {
			pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

			Map param = GridUtil.parseGridPager(pager);
			CommonUtil.parseParamCommunityId(request, param);

			Integer role_id = CommonUtil.safeToInteger(SettingUtil.getSettingValue("COMMON", "DELIVERY_ROLE"), -1);	//配送员角色id
			param.put("role_id", role_id);

			List<Map> list = userService.searchDeliveryer(param);
			int count = userService.searchDeliveryerCount(param);

			pager = GridUtil.setPagerResult(pager, list, count, true);
		} catch (Exception e) {
			logger.error("searchDeliveryer error", e);
			pager.setIsSuccess(false);
		}
		CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
	}

	@RequestMapping(params = "method=saveDeliveryer")
	public void saveDeliveryer(HttpServletRequest request, HttpServletResponse response) {
		RtnResult result = new RtnResult(false);
		try {
			Integer user_id = CommonUtil.safeToInteger(request.getParameter("user_id"), null);
			String loginname = request.getParameter("loginname");
			String password = request.getParameter("password");
			String[] community_id_array = request.getParameterValues("community_id");
			String distri_worker_name = request.getParameter("distri_worker_name");		//配送员姓名
			String distri_worker_phone = request.getParameter("distri_worker_phone");	//配送员手机

			if(StringUtils.isNotBlank(loginname) && StringUtils.isNotBlank(password) && ArrayUtils.isNotEmpty(community_id_array) &&
					StringUtils.isNotBlank(distri_worker_name) && StringUtils.isNotBlank(distri_worker_phone)) {
				MgrUser mgrUser = null;
				if(user_id != null) {
					mgrUser = userService.getMgrUserInfo(user_id);
				} else {
					mgrUser = new MgrUser();
					if(userService.countUserName(loginname) > 0) {
						mgrUser = null;    //登录名重复，不允许注册
						result.setMsg("登录名重复");
					} else {
						mgrUser.setLoginname(loginname);
						mgrUser.setActivation(1);
						mgrUser.setAuthenticate(1);    //已认证
						mgrUser.setType(1);
					}
				}

				if(mgrUser != null) {
					mgrUser.setPassword(password);
					mgrUser.setPersonName(distri_worker_name);
					mgrUser.setDistri_worker_name(distri_worker_name);
					mgrUser.setPhone(distri_worker_phone);
					mgrUser.setDistri_worker_phone(distri_worker_phone);
					userService.saveMgrUser(mgrUser);

					Integer role_id = CommonUtil.safeToInteger(SettingUtil.getSettingValue("COMMON", "DELIVERY_ROLE"), -1);	//配送员角色id
					userService.deleteUserRole(mgrUser.getUser_id());
					Relation relation = new Relation();
					relation.setRole_id(role_id);
					relation.setUser_id(mgrUser.getUser_id());
					userService.insertUserRole(relation);

					userService.deleteUserCommunity(mgrUser.getUser_id());
					for(String community_id_str : community_id_array) {
						Integer community_id = CommonUtil.safeToInteger(community_id_str, null);
						if(community_id != null) {
							Relation _relation = new Relation();
							_relation.setCommunity_id(community_id);
							_relation.setUser_id(mgrUser.getUser_id());
							userService.insertUserCommunity(_relation);
						}
					}

					result.setResult(true);
				}

			}
		} catch (Exception e) {
			logger.error("saveDeliveryer error", e);
		}
		CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
	}
}
