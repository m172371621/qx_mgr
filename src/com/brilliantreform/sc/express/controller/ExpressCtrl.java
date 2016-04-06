package com.brilliantreform.sc.express.controller;

import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.common.RtnResult;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.express.po.Express;
import com.brilliantreform.sc.express.po.ExpressSend;
import com.brilliantreform.sc.express.service.ExpressService;
import com.brilliantreform.sc.order.po.MsgInfo;
import com.brilliantreform.sc.order.service.MsgInfoService;
import com.brilliantreform.sc.system.dicti.Dicti;
import com.brilliantreform.sc.user.service.UserService;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.GridUtil;
import com.brilliantreform.sc.utils.LogerUtil;
import com.brilliantreform.sc.utils.SettingUtil;
import com.brilliantreform.sc.weixin.dao.UserBindDao;
import com.brilliantreform.sc.weixin.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("express.do")
public class ExpressCtrl {

	private static Logger logger = Logger.getLogger(ExpressCtrl.class);

	@Autowired
    private UserBindDao userbindDao;
	
	@Autowired
	private ExpressService expressService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private MsgInfoService msgInfoService;
	
	private static int size = 20;


	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=list", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String list(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "list");

		try {

			String isSet = CommonUtil.isNull(request
					.getParameter("isSet"));
			
			if (isSet != null && "1".equals(isSet)) {
				String[] ids = request.getParameterValues("ids");
				Express express = new Express();
				express.setStatus(2);
				for(String id : ids)
				{
					express.setExpress_id(Integer.parseInt(id));
					this.expressService.updateExpress(express);
				}
				request.setAttribute("SUCCESS", 1);
			}

			String express_com = CommonUtil.isNull(request
					.getParameter("express_com"));
			String express_no = CommonUtil.isNull(request
					.getParameter("express_no"));
			String status = CommonUtil.isNull(request
					.getParameter("status"));
			String user_phone = CommonUtil.isNull(request
					.getParameter("user_phone"));
			String user_type = CommonUtil.isNull(request
					.getParameter("user_type"));
			String express_info = CommonUtil.isNull(request
					.getParameter("express_info"));
			String time_from = CommonUtil.isNull(request
					.getParameter("time_from"));
			String time_to = CommonUtil.isNull(request
					.getParameter("time_to"));
			String pageIndex = CommonUtil.isNull(request
					.getParameter("pageIndex"));
			
			
			Integer i_pageIndex = pageIndex == null ? 1 : Integer
					.parseInt(pageIndex);
			int begin = (i_pageIndex - 1) * size;

			Integer i_status = null;
			if (status != null) {
				i_status = Integer.parseInt(status);
			}
			Integer i_user_type = null;
			if (user_type != null) {
				i_user_type = Integer.parseInt(user_type);
			}
			
			Map searchBean = new HashMap();
			
				searchBean.put("express_com", express_com);
			
			searchBean.put("express_no", express_no);
			searchBean.put("status", status);
			searchBean.put("user_phone", user_phone);
			searchBean.put("user_type", user_type);
			searchBean.put("express_info", express_info);
			searchBean.put("time_from", time_from);
			searchBean.put("time_to", time_to);
					
			Community c =(Community)request.getSession().getAttribute("selectCommunity");
			int cid = c.getCommunity_id();
			
			Express express = new Express();
			express.setUser_phone(user_phone);
			express.setExpress_com(express_com);			
			express.setExpress_no(express_no);
			express.setCommunity_id(cid);
			express.setUser_type(i_user_type);
			express.setStatus(i_status);
			express.setExpress_info(express_info);
			
			int count = expressService.countExpress(express,time_from,time_to);
			List list = expressService.getExpressList(express, begin, size,time_from,time_to);

			request.setAttribute("list", list);
			request.setAttribute("pageCount", count % size == 0 ? count / size
					: count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			request.setAttribute("searchBean", searchBean);			
			
			return "jsp/express/express_list";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}
	

	@RequestMapping(params = "method=view", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String view(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "list");

		try {

			String id = CommonUtil.isNull(request
					.getParameter("eid"));
			
			boolean falg = false;
			if(id == null)
			{
				falg = true;
				id = (String)request.getAttribute("id");
			}
				
			Express express = this.expressService.getExpress(Integer.parseInt(id));
			request.setAttribute("express", express);	
			
			if(falg || request.getParameter("type")!=null)
			{
				return "jsp/express/express_edit";
			}else
			{
				return "jsp/express/express_view";
			}
		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}
	
	@RequestMapping(params = "method=del", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String del(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "list");

		try {

			String id = CommonUtil.isNull(request
					.getParameter("id"));
				
			Express express = new Express();
			express.setExpress_id(Integer.parseInt(id));
			express.setStatus(9);
			this.expressService.updateExpress(express);
			
			return this.list(request);

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}
	
	@RequestMapping(params = "method=edit", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String edit(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "list");

		try {

			String id = CommonUtil.isNull(request
					.getParameter("express_id"));
			String express_no = request.getParameter("express_no");
			String express_com = request.getParameter("express_com");
			String express_info = request.getParameter("express_info");
			String user_phone = request.getParameter("user_phone");
			String status = request.getParameter("status");

			Express express = new Express();
			express.setExpress_no(express_no.trim());
			express.setExpress_com(express_com);
			express.setExpress_info(express_info);
			express.setUser_phone(user_phone);
			express.setExpress_id(Integer.parseInt(id));
			express.setStatus(Integer.parseInt(status));
			
			this.expressService.updateExpress(express);
			
			request.setAttribute("id", id);
			request.setAttribute("SUCCESS", true);
			return this.view(request);

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}
	
	@RequestMapping(params = "method=add", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String add(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "add");

		try {

			String express_com = request.getParameter("express_com");
			// String type = request.getParameter("type");

			if (StringUtils.isNotBlank(express_com)) {

				String express_no = request.getParameter("express_no");
				String express_info = request.getParameter("express_info");
				String user_phone = request.getParameter("user_phone");

				Community c =(Community)request.getSession().getAttribute("selectCommunity");
				int cid = c.getCommunity_id();
				
				int user_type = 1;
				if(this.userService.getUserByPhone(user_phone.trim())!=null)
				{
					user_type = 2;
				}
				
				Express express = new Express();
				express.setExpress_no(express_no.trim());
				express.setExpress_info(express_info);
				express.setExpress_com(express_com);
				express.setCommunity_id(cid);
				express.setUser_type(user_type);
				express.setUser_phone(user_phone);
				express.setArrival_time(new Date());
				express.setStatus(1);
				
				this.expressService.insertExpress(express);

//				String pushMsg = "尊敬的用户您好，您快递单号为"
//									+express_no.trim()
//									+"的"
//									+Dicti.getValue("快递公司",Integer.parseInt(express_com.trim()))
//									+"包裹已送达区享便民服务中心，请尽快至服务站领取！";

				Map<String,Object> pmap = new HashMap<String,Object>();
				pmap.put("phone",user_phone.trim());

				Map<String, Object> msgmap = new HashMap<String, Object>();

				msgmap.put("URL",
                        SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST") + "/deliver/msgNotSign");
				msgmap.put("FIRST", "尊敬的用户您好，您有快递包裹已送达区享便民服务中心，请尽快至服务站领取！");
				msgmap.put("KEYWORD1", Dicti.getValue("快递公司",Integer.parseInt(express_com.trim())));
				msgmap.put("KEYWORD2", express_no.trim());
				msgmap.put("REMARK", "感谢使用区享平台。");
				
				WeixinUtil wutil = new WeixinUtil(userbindDao);
//				wutil.sendMsgToUser(pushMsg,pmap);
				wutil.sendMsgExpressToUser(msgmap, pmap);
				//新快递
				if(user_phone!=null || user_phone.trim()!="") {
					Integer user_id = expressService.selUserlogin(user_phone);
					if(user_id >=1) {
						MsgInfo msginfo = new MsgInfo();
						msginfo.setMsg_info_phone(user_id+"");
						msginfo.setMsg_template_code("0000000004");
						msginfo.setMsg_info_detail("");
						msgInfoService.addMsgInfo(msginfo);
					}
				}
				
				
				request.setAttribute("SUCCESS", true);

			
				return "/jsp/express/express_add";
			}

			return "/jsp/express/express_add";
			
		} catch (Exception e) {

			logger.error(e.getMessage());
			return "/jsp/error";
		}

	}

    @RequestMapping(params = "method=showListExpressPage")
    public String showListExpressPage(HttpServletRequest request, HttpServletResponse response) {
        Map comMap =  Dicti.get(Dicti.get("快递公司"));
        request.setAttribute("comMap", comMap);
        return "/new/jsp/express/listExpress";
    }

    @RequestMapping(params = "method=searchExpress")
    public void searchExpress(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            LogerUtil.logRequest(request, logger, "searchExpress");

            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);
            CommonUtil.parseParamCommunityId(request, param);

            //处理开始结束时间
            Date time_to = CommonUtil.safeToDate(param.get("time_to"), "yyyy-MM-dd");
            if(time_to != null) {
                param.put("time_to", CommonUtil.formatDate(DateUtils.addDays(time_to, 1), "yyyy-MM-dd"));
            }

            List<Map> list = expressService.searchExpress(param);
            int count = expressService.searchExpressCount(param);

            //处理快递公司
            Map comMap =  Dicti.get(Dicti.get("快递公司"));
            for(Map map : list) {
                Integer express_com = CommonUtil.safeToInteger(map.get("express_com"), null);
                if(express_com != null) {
                    String express_com_name = comMap.get(express_com) + "";
                    map.put("express_com_name", express_com_name);
                }
            }

            pager = GridUtil.setPagerResult(pager, list, count);

        } catch (Exception e) {
            logger.error("searchExpress error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    @RequestMapping(params = "method=getExpressById")
    public void getExpressById(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "getExpressById");

            Integer express_id = CommonUtil.safeToInteger(request.getParameter("express_id"), null);

            if(express_id != null) {
                Express express = expressService.getExpress(express_id);
                if(express != null) {
                    CommonUtil.outToWeb(response, JSONObject.fromObject(express).toString());
                }
            }
        } catch (Exception e) {
            logger.error("getExpressById error", e);
        }
    }

    @RequestMapping(params = "method=saveExpress")
    public void saveExpress(HttpServletRequest request, HttpServletResponse response) {
        String result = "fail";
        try {
            LogerUtil.logRequest(request, logger, "saveExpress");

            Integer express_id = CommonUtil.safeToInteger(request.getParameter("express_id"), null);
            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
            String express_com = request.getParameter("express_com");
            String express_no = request.getParameter("express_no");
            String user_phone = request.getParameter("user_phone");
            String express_info = request.getParameter("express_info");
            Integer status = CommonUtil.safeToInteger(request.getParameter("status"), null);

            Express express = null;
            if(express_id != null) {
                express = expressService.getExpress(express_id);
            } else {
                express = new Express();
            }

            if(express != null) {
                express.setExpress_com(express_com);
                express.setUser_phone(user_phone);
                express.setExpress_no(express_no);
                express.setExpress_info(express_info);
                int user_type = 1;  //1. 非app用户 2. 是app用户
                if(userService.getUserByPhone(express.getUser_phone().trim())!=null) {
                    user_type = 2;
                }
                express.setUser_type(user_type);
                express.setCommunity_id(community_id);

                if(express_id != null) {
                    express.setStatus(status);
                    if (status != null && status.intValue() == 2) {
                        express.setSign_time(new Date());
                    }
                } else {
                    //快递录入
                    express.setStatus(1);   //未签收
                    express.setArrival_time(new Date());
                }

                expressService.saveExpress(express);

                if(express_id == null) {
                    //快递录入时添加提醒
                    Map<String,Object> pmap = new HashMap<String,Object>();
                    pmap.put("phone",user_phone.trim());

                    Map<String, Object> msgmap = new HashMap<String, Object>();

                    msgmap.put("URL",
                            SettingUtil.getSettingValue("COMMON", "QX_SERVER_HOST") + "/deliver/msgNotSign");
                    msgmap.put("FIRST", "尊敬的用户您好，您有快递包裹已送达区享便民服务中心，请尽快至服务站领取！");
                    msgmap.put("KEYWORD1", Dicti.getValue("快递公司",Integer.parseInt(express_com.trim())));
                    msgmap.put("KEYWORD2", express_no.trim());
                    msgmap.put("REMARK", "感谢使用区享平台。");

                    WeixinUtil wutil = new WeixinUtil(userbindDao);
                    wutil.sendMsgExpressToUser(msgmap, pmap);
                    //新快递
                    if(user_phone!=null || user_phone.trim()!="") {
                        Integer user_id = expressService.selUserlogin(user_phone);
                        if(user_id != null && user_id >=1) {
                            MsgInfo msginfo = new MsgInfo();
                            msginfo.setMsg_info_phone(user_id+"");
                            msginfo.setMsg_template_code("0000000004");
                            msginfo.setMsg_info_detail("");
                            msgInfoService.addMsgInfo(msginfo);
                        }
                    }
                }

                result = "success";
            }
        } catch (Exception e) {
            logger.error("saveExpress error", e);
        }
        CommonUtil.outToWeb(response, result);
    }

    @RequestMapping(params = "method=signExpress")
    public void signExpress(HttpServletRequest request, HttpServletResponse response) {
        String result = "fail";
        try {
            LogerUtil.logRequest(request, logger, "signExpress");

            String ids = request.getParameter("ids");

            if(StringUtils.isNotBlank(ids)) {
                String[] id_array = ids.split(",");
                for(String id : id_array) {
                    Integer express_id = CommonUtil.safeToInteger(id, null);
                    if(express_id != null) {
                        Express express = expressService.getExpress(express_id);
                        if(express != null && express.getStatus().intValue() != 2) {
                            express.setStatus(2);
                            express.setSign_time(new Date());
                            expressService.saveExpress(express);
                        }
                    }
                }
                result = "success";
            }
        } catch (Exception e) {
            logger.error("signExpress error", e);
        }
        CommonUtil.outToWeb(response, result);
    }

    @RequestMapping(params = "method=removeExpress")
    public void removeExpress(HttpServletRequest request, HttpServletResponse response) {
        String result = "fail";
        try {
            LogerUtil.logRequest(request, logger, "removeExpress");

            Integer express_id = CommonUtil.safeToInteger(request.getParameter("express_id"), null);

            if(express_id != null) {
                Express express = expressService.getExpress(express_id);
                if(express != null) {
                    express.setStatus(9);
                    expressService.saveExpress(express);
                    result = "success";
                }
            }
        } catch (Exception e) {
            logger.error("removeExpress error", e);
        }
        CommonUtil.outToWeb(response, result);
    }

    @RequestMapping(params = "method=showListExpressSendPage")
    public String showListExpressSendPage(HttpServletRequest request, HttpServletResponse response) {
        Map comMap =  Dicti.get(Dicti.get("快递公司"));
        request.setAttribute("comMap", comMap);
        return "/new/jsp/express/listExpressSend";
    }

    @RequestMapping(params = "method=searchExpressSend")
    public void searchExpressSend(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            LogerUtil.logRequest(request, logger, "searchExpressSend");

            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);
            CommonUtil.parseParamCommunityId(request, param);

            Date time_to = CommonUtil.safeToDate(param.get("time_to"), "yyyy-MM-dd");
            if(time_to != null) {
                param.put("time_to", CommonUtil.formatDate(DateUtils.addDays(time_to, 1), "yyyy-MM-dd"));
            }

            List<Map> list = expressService.searchExpressSend(param);
            int count = expressService.searchExpressSendCount(param);

            //处理快递公司
            Map comMap =  Dicti.get(Dicti.get("快递公司"));
            for(Map map : list) {
                Integer express_com = CommonUtil.safeToInteger(map.get("express_com"), null);
                if(express_com != null) {
                    String express_com_name = comMap.get(express_com) + "";
                    map.put("express_com_name", express_com_name);
                }
            }

            pager = GridUtil.setPagerResult(pager, list, count);

        } catch (Exception e) {
            logger.error("searchExpressSend error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    @RequestMapping(params = "method=getExpressSendById")
    public void getExpressSendById(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "getExpressSendById");

            Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);

            if(objid != null) {
                ExpressSend expressSend = expressService.getExpressSendById(objid);
                if(expressSend != null) {
                    CommonUtil.outToWeb(response, JSONObject.fromObject(expressSend).toString());
                }
            }
        } catch (Exception e) {
            logger.error("getExpressSendById error", e);
        }
    }

    @RequestMapping(params = "method=removeExpressSend")
    public void removeExpressSend(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            LogerUtil.logRequest(request, logger, "removeExpressSend");

            Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);

            if(objid != null) {
                expressService.removeExpressSend(objid);
                result.setResult(true);
            }
        } catch (Exception e) {
            logger.error("removeExpressSend error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    @RequestMapping(params = "method=saveExpressSend")
    public void saveExpressSend(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            LogerUtil.logRequest(request, logger, "saveExpressSend");

            Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);
            String express_no = request.getParameter("express_no");
            Integer express_com = CommonUtil.safeToInteger(request.getParameter("express_com"), null);
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            Double express_price = CommonUtil.safeToDouble(request.getParameter("express_price"), null);
            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
            String remark = request.getParameter("remark");

            ExpressSend expressSend = null;
            if(objid != null) {
                expressSend = expressService.getExpressSendById(objid);
            } else {
                expressSend = new ExpressSend();
            }

            if(expressSend != null) {
                expressSend.setExpress_no(express_no);
                expressSend.setExpress_com(express_com);
                expressSend.setName(name);
                expressSend.setPhone(phone);
                expressSend.setExpress_price(express_price);
                expressSend.setCommunity_id(community_id);
                expressSend.setRemark(remark);

                expressService.saveExpressSend(expressSend);
                result.setResult(true);
            }
        } catch (Exception e) {
            logger.error("saveExpressSend error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }
}
