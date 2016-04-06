package com.brilliantreform.sc.user.controller;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.community.service.CommunityService;
import com.brilliantreform.sc.service.po.ServiceVo;
import com.brilliantreform.sc.service.service.SevService;
import com.brilliantreform.sc.sys.po.MenuTree;
import com.brilliantreform.sc.sys.po.SysMenu;
import com.brilliantreform.sc.sys.service.SysMenuService;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.user.mgrpo.Right;
import com.brilliantreform.sc.user.mgrpo.Role;
import com.brilliantreform.sc.user.service.UserService;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.LogerUtil;
import com.brilliantreform.sc.utils.SettingUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("login.do")
public class LoginCtrl {

	private static Logger logger = Logger.getLogger(LoginCtrl.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private SevService sevService;
	@Autowired
	private SysMenuService sysMenuService;
	
	@RequestMapping(params = "method=login", method = { RequestMethod.POST })
	public String login(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "login");
		
		int user_id = 0;

		try {

			String username = request.getParameter("username");
			String password = request.getParameter("password");

			String msg="";
			
			if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {

				msg = "用户名或密码错误";

			}else{

		
				user_id = userService.login(username, password);
				

				// 判断登陆结果
				if (user_id >= 0) {

					request.getSession().setAttribute("user_info", userService.getMgrUserInfo(user_id));

				} else {

					msg = "用户名或密码错误";
				}
				
				//判断账户是否冻结
				int activation = userService.getMgrUserInfo(user_id).getActivation();
				if(activation == 0){ 
					user_id = 0;
					msg = "账户禁止登陆";
				}
				
			}
			
			if(user_id > 0){
				
				List<Role> list_role = this.userService.getUserRole(user_id);
				List<Right> right_list = this.userService.getUserRight(user_id);
				List<Community> community_list = this.userService.getUserCommunity(user_id);
				List<ServiceVo> service_list = this.userService.getUserService(user_id);
				request.getSession().setAttribute("user_right", right_list);
				request.getSession().setAttribute("user_role", list_role);
				
				int role_id = 0;
				for(Role role : list_role)
				{
					if(role.getRole_id() == 1)
					{
						role_id = 1;
						break;
					}
				}
				
				if(role_id == 1)
				{
					request.getSession().setAttribute("user_isAdmin", true);
					community_list = this.communityService.getCommunityList();					
				}

				int curr_cid = community_list.get(community_list.size()-1).getCommunity_id();
				//user_community 用户所属小区
				request.getSession().setAttribute("user_community", community_list);
				
				
				if(service_list.size() > 0) //有多个
				{
					//user_service 用户所属服务
					request.getSession().setAttribute("user_service", service_list);
				}else //为admin 拥有全部服务
				{
					request.getSession().setAttribute("user_service", this.sevService.getServiceList(3,curr_cid));
				}
				
				//all_service 当前小区全部服务列表
				request.getSession().setAttribute("all_service", this.sevService.getServiceList(null,curr_cid));
				//product_service 当前小区全部商品服务列表
				request.getSession().setAttribute("product_service", this.sevService.getServiceList(1,curr_cid));			
				
				//selectCommunity 当前的小区
				 request.getSession().setAttribute("selectCommunity", community_list.get(community_list.size()-1));

				return "/jsp/common/main";	
				
			}else
			{
				request.setAttribute("msg", msg);
				return "/jsp/login";
			}
			
		} catch (Exception e) {

			logger.error(e.getMessage());
			return "/jsp/error";
		}

	}
	
	@RequestMapping(params = "method=logout", method = { RequestMethod.GET})
	public String logout(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "login");

		try {

			request.getSession().invalidate();

			
			return "/jsp/login";
			
			
		} catch (Exception e) {

			logger.error(e.getMessage());
			return "/jsp/error";
		}

	}

	/**
	 * 用户更改下拉框时更换session
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=selectCommunity", method = {
			RequestMethod.POST, RequestMethod.GET })
	public void  selectCommunity(HttpServletRequest request,
			HttpServletResponse response){

		 int cid=Integer.valueOf(request.getParameter("cid"));
		 
		 List<Community> community_list =  (List<Community>)request.getSession().getAttribute("user_community");
		
		 boolean flag = false;
		 Community curr = null;
		 for(Community c : community_list)
		 {
			 if(c.getCommunity_id() == cid)
			 {
				 flag = true;
				 curr = c;
				 break;
			 }
		 }
			 
		 if(flag)
		 {
			 if(request.getSession().getAttribute("user_isAdmin") != null)
			 {
				 request.getSession().setAttribute("user_service", this.sevService.getServiceList(3,curr.getCommunity_id()));
			 }
			 
			 //all_service 当前小区全部服务列表
			 request.getSession().setAttribute("all_service", this.sevService.getServiceList(null,curr.getCommunity_id()));
			 //product_service 当前小区全部商品服务列表
			 request.getSession().setAttribute("product_service", this.sevService.getServiceList(1,curr.getCommunity_id()));			
				
			 request.getSession().setAttribute("selectCommunity", curr);	 
			 
		 }
	}

	@RequestMapping(params = "method=loginNew", method = { RequestMethod.POST })
	public String loginNew (HttpServletRequest request) {
		LogerUtil.logRequest(request, logger, "loginNew");
		int user_id = 0;
        MgrUser user = null;
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String msg="";
			List<Role> list_role = new ArrayList<Role>();
			if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
				msg = "用户名或密码错误：01";
			}else{
				user_id = userService.login(username, password);
				// 判断登陆结果
				if (user_id >= 0) {
                    //判断账户是否冻结
                    user = userService.getMgrUserInfo(user_id);
                    if(user != null) {
						if(user.getActivation() == 0) {
							user_id = -1;
							msg = "账户禁止登录：02";
						} else {
							list_role = this.userService.getUserRole(user_id);
							String NOLOGIN_ROLE = SettingUtil.getSettingValue("COMMON", "NOLOGIN_ROLE");    //禁止登录的角色

							if (StringUtils.isNotBlank(NOLOGIN_ROLE)) {
								if(list_role != null && list_role.size() == 1) {
									if(("," + NOLOGIN_ROLE + ",").contains(list_role.get(0).getRole_id() + "")) {
										user_id = -1;
										msg = "账户禁止登录：03";
									}
								}
							}
						}
                    }
				} else {
					msg = "用户名或密码错误：04";
				}
			}

			if(user_id > 0) {
                boolean isAdmin = false;
                for(Role role : list_role) {
                    if(role.getRole_id() == 1) {
                        isAdmin = true;
                        break;
                    }
                }


                List<Community> community_list = this.userService.findUserCommunity(isAdmin ? null : user_id);

                request.getSession().setAttribute("user_info", user);
				request.getSession().setAttribute("user_role", list_role);
                request.getSession().setAttribute("user_isAdmin", isAdmin);
				request.getSession().setAttribute("user_community", community_list);

				//selectCommunity 所属门店的第一个  todo 兼容性考虑未删除，待以后系统稳定了之后删除
                List<Map> communityList = communityService.findSecondUserCommunity(user.getUser_id());
                Integer fst_cid = CommonUtil.safeToInteger(((Map)communityList.get(0)).get("community_id"), null);
                request.getSession().setAttribute("selectCommunity", communityService.getCommunity(fst_cid));

				//获取菜单
				List<MenuTree> menuList = sysMenuService.loadSysMenu(isAdmin ? null : user_id);
				request.getSession().setAttribute("user_menu", menuList);

                //获取菜单以及操作等权限列表
                List<SysMenu> rightList = sysMenuService.findUserMenu(isAdmin ? null : user_id, null);
                request.getSession().setAttribute("user_right", rightList);

				return "redirect:/new/jsp/index.jsp";
			} else {
				request.setAttribute("msg", msg);
				return "/new/jsp/login";
			}

		} catch (Exception e) {
			logger.error("login error", e);
			return "/new/jsp/500";
		}

	}

    @RequestMapping(params = "method=logoutV4")
    public String logoutV4(HttpServletRequest request) {
        LogerUtil.logRequest(request, logger, "logoutV4");
        try {
            request.getSession().invalidate();
        } catch (Exception e) {
            logger.error("logoutV4 error", e);
        }
        return "redirect:/new/jsp/login.jsp";
    }
}
