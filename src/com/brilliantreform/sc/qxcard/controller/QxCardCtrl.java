package com.brilliantreform.sc.qxcard.controller;

import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.common.RtnResult;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.confgure.po.Config_community;
import com.brilliantreform.sc.confgure.service.Config_communityService;
import com.brilliantreform.sc.qxcard.enumerate.QxCardCzChannelEnum;
import com.brilliantreform.sc.qxcard.enumerate.QxCardCzPayTypeEnum;
import com.brilliantreform.sc.qxcard.enumerate.QxCardCzStatusEnum;
import com.brilliantreform.sc.qxcard.po.*;
import com.brilliantreform.sc.qxcard.service.QxCardService;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.user.po.LoginInfo;
import com.brilliantreform.sc.user.service.UserService;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.GridUtil;
import com.brilliantreform.sc.utils.LogerUtil;
import com.brilliantreform.sc.utils.MathUtil;
import net.sf.json.JSONArray;
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
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("qxcard.do")
public class QxCardCtrl {

	private static Logger logger = Logger.getLogger(QxCardCtrl.class);

	@Autowired
	private QxCardService qxCardService;
	@Autowired
	private UserService userService;
    @Autowired
    private Config_communityService configCommunityService;

	private static int size = 20;

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=list_reglog", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String list(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "list_reglog");

		try {

			String qxcard_no = CommonUtil.isNull(request
					.getParameter("qxcard_no"));
			String seller_name = CommonUtil.isNull(request
					.getParameter("seller_name"));
			String op_type = CommonUtil.isNull(request.getParameter("op_type"));

			String time_from = CommonUtil.isNull(request
					.getParameter("time_from"));
			String time_to = CommonUtil.isNull(request.getParameter("time_to"));
			String pageIndex = CommonUtil.isNull(request
					.getParameter("pageIndex"));

			Integer i_pageIndex = pageIndex == null ? 1 : Integer
					.parseInt(pageIndex);
			int begin = (i_pageIndex - 1) * size;

			Integer i_op_type = null;
			if (op_type != null) {
				i_op_type = Integer.parseInt(op_type);
			}

			Map searchBean = new HashMap();

			searchBean.put("qxcard_no", qxcard_no);
			searchBean.put("seller_name", seller_name);
			searchBean.put("op_type", op_type);

			searchBean.put("time_from", time_from);
			searchBean.put("time_to", time_to);

			Community c = (Community) request.getSession().getAttribute(
					"selectCommunity");
			int cid = c.getCommunity_id();

			QxcardReglog qxcardReglog = new QxcardReglog();
			qxcardReglog.setQxcard_no(qxcard_no);
			qxcardReglog.setOp_type(i_op_type);
			qxcardReglog.setSeller_name(seller_name);
			qxcardReglog.setCid(cid);

			int count = qxCardService.countQxcardReglog(qxcardReglog,
					time_from, time_to);
			List list = qxCardService.getQxcardReglogList(qxcardReglog, begin,
					size, time_from, time_to);

			request.setAttribute("list", list);
			request.setAttribute("pageCount", count % size == 0 ? count / size
					: count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			request.setAttribute("searchBean", searchBean);

			return "jsp/qxcard/card_reg_list";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=edit_reglog", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String edit(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "edit_reglog");

		try {

			String op_id = request.getParameter("op_id");
			String type = request.getParameter("type");
			int i_op_id = 0;

			// 更新或新增 
			if (StringUtils.isNotBlank(type)) {

				String qxcard_no = CommonUtil.isNull(request
						.getParameter("qxcard_no"));
				String op_type = CommonUtil.isNull(request
						.getParameter("op_type"));

				String custm_name = CommonUtil.isNull(request
						.getParameter("custm_name"));
				String custm_phone = CommonUtil.isNull(request
						.getParameter("custm_phone"));
				String custm_dec = CommonUtil.isNull(request
						.getParameter("custm_dec"));
				String seller_dec = CommonUtil.isNull(request
						.getParameter("seller_dec"));

				Community c = (Community) request.getSession().getAttribute(
						"selectCommunity");
				int cid = c.getCommunity_id();
				String sname = ((MgrUser) request.getSession().getAttribute(
						"user_info")).getLoginname();

				QxcardReglog qxcardReglog = new QxcardReglog();
				qxcardReglog.setCustm_name(custm_name);
				qxcardReglog.setCustm_phone(custm_phone);
				qxcardReglog.setCustm_dec(custm_dec);
				qxcardReglog.setSeller_dec(seller_dec);

				// 新增
				if (StringUtils.isBlank(op_id)) {
					
					QxCard qxCard = new QxCard();
					qxCard.setQxcard_no(qxcard_no);
					qxCard = qxCardService.getQxCard(qxCard);
					if (qxCard != null) {

						int i_op_type = Integer.parseInt(op_type);
						
						if(i_op_type == 1)
						{
							if(qxCard.getStatus() == 1)
							{
								qxcardReglog.setQxcard_no(qxcard_no);
								qxcardReglog.setOp_type(i_op_type);
								qxcardReglog.setCid(cid);
								qxcardReglog.setSeller_name(sname);
								qxcardReglog.setSeller_ip(CommonUtil.getIp(request));
							
								i_op_id = qxCardService
										.insertQxcardReglog(qxcardReglog);
								
								qxCard.setStatus(2);
								Calendar calendar = Calendar.getInstance();
								calendar.add(Calendar.YEAR, 3);
								qxCard.setExpire_time(new Timestamp(calendar.getTimeInMillis()));
								qxCardService.updateQxCard(qxCard);
								// TODO 插入操作记录表
								request.setAttribute("SUCCESS", true);
								
							}else
							{
								request.setAttribute("SUCCESS", false);
							}
						
						}
						else if(i_op_type == 2)
						{
							
								qxcardReglog.setQxcard_no(qxcard_no);
								qxcardReglog.setOp_type(i_op_type);
								qxcardReglog.setCid(cid);
								qxcardReglog.setSeller_name(sname);
								qxcardReglog.setSeller_ip(CommonUtil.getIp(request));

								i_op_id = qxCardService
										.insertQxcardReglog(qxcardReglog);
								
								
								qxCard.setDisabled(2);
								qxCardService.updateQxCard(qxCard);
								
								UserQxCard userQxCard = new UserQxCard();
								userQxCard.setQxcard_no(qxcard_no);
								userQxCard.setDisabled(2);
								
								qxCardService.updateUserQxCard(userQxCard);
								request.setAttribute("SUCCESS", true);
							
						}else if(i_op_type == 3)
						{
							qxcardReglog.setQxcard_no(qxcard_no);
							qxcardReglog.setOp_type(i_op_type);
							qxcardReglog.setCid(cid);
							qxcardReglog.setSeller_name(sname);
							qxcardReglog.setSeller_ip(CommonUtil.getIp(request));

							i_op_id = qxCardService
									.insertQxcardReglog(qxcardReglog);
							
							qxCard.setDisabled(1);
							qxCardService.updateQxCard(qxCard);
							
							UserQxCard userQxCard = new UserQxCard();
							userQxCard.setQxcard_no(qxcard_no);
							userQxCard.setDisabled(1);
							qxCardService.updateUserQxCard(userQxCard);
							request.setAttribute("SUCCESS", true);
						}
						
					}else
					{
						request.setAttribute("SUCCESS", false);
					}
				//更新
				} else {
					qxcardReglog.setId(Integer.parseInt(op_id));
					qxCardService.updateQxcardReglog(qxcardReglog);
					request.setAttribute("SUCCESS", true);
				}
			}

			if (StringUtils.isNotBlank(op_id)) {
				i_op_id = Integer.parseInt(op_id);
			}

			if (i_op_id != 0) {
				QxcardReglog bean = this.qxCardService.getQxcardReglog(i_op_id);

				request.setAttribute("obj", bean);
			}

			return "/jsp/qxcard/card_reg_edit";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "/jsp/error";
		}

	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=userQxCardlist", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String userQxCardlist(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "userQxCardlist");

		try {

			String qxcard_no = CommonUtil.isNull(request
					.getParameter("qxcard_no"));
			String phone = CommonUtil.isNull(request
					.getParameter("phone"));
			String status = CommonUtil.isNull(request.getParameter("status"));

			String pageIndex = CommonUtil.isNull(request
					.getParameter("pageIndex"));
			String time_from = CommonUtil.isNull(request
					.getParameter("time_from"));
			String time_to = CommonUtil.isNull(request
					.getParameter("time_to"));

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if(request.getParameter("time_to")==null) {
				Date currentTime = new Date();
				time_to=formatter.format(currentTime);
				
			}
			if(request.getParameter("time_from")==null) {
				Calendar rightNow = Calendar.getInstance();
				rightNow.add(Calendar.DAY_OF_YEAR, -7);
				Date pre_date=rightNow.getTime();
				time_from=formatter.format(pre_date);
			}
			
			Integer i_pageIndex = pageIndex == null ? 1 : Integer
					.parseInt(pageIndex);
			int begin = (i_pageIndex - 1) * size;

			Integer i_status = null;
			if (status != null) {
				i_status = Integer.parseInt(status);
			}

			Map searchBean = new HashMap();

			searchBean.put("qxcard_no", qxcard_no);
			searchBean.put("phone", phone);
			searchBean.put("status", status==null||"0".equals(status)?null:Integer.parseInt(status));


			Community c = (Community) request.getSession().getAttribute(
					"selectCommunity");
			int cid = c.getCommunity_id();

			searchBean.put("communityId", cid);
			searchBean.put("start", begin);
			searchBean.put("size", 20);
			searchBean.put("time_from", time_from);
			searchBean.put("time_to", time_to);

			int count = qxCardService.getUserQxcardListCount(searchBean);
			List<UserQxCard> list = qxCardService.getUserQxcardList(searchBean);

			searchBean.put("status", status==null||"0".equals(status)?0:Integer.parseInt(status));
			request.setAttribute("list", list);
			request.setAttribute("pageCount", count % size == 0 ? count / size
					: count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			request.setAttribute("searchBean", searchBean);

			return "jsp/qxcard/user_qxcard_list";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=qxcard_optlog_list", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String qxcard_optlog_list(HttpServletRequest request) {

		LogerUtil.logRequest(request, logger, "qxcard_optlog_list");

		try {

			String qxcard_no = CommonUtil.isNull(request
					.getParameter("qxcard_no"));
			String phone = CommonUtil.isNull(request
					.getParameter("phone"));
			String order_serial = CommonUtil.isNull(request
					.getParameter("order_serial"));

			String pageIndex = CommonUtil.isNull(request
					.getParameter("pageIndex"));
			String time_from = CommonUtil.isNull(request
					.getParameter("time_from"));
			String time_to = CommonUtil.isNull(request
					.getParameter("time_to"));

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if(request.getParameter("time_to")==null) {
				Date currentTime = new Date();
				time_to=formatter.format(currentTime);
				
			}
			if(request.getParameter("time_from")==null) {
				Calendar rightNow = Calendar.getInstance();
				rightNow.add(Calendar.DAY_OF_YEAR, -7);
				Date pre_date=rightNow.getTime();
				time_from=formatter.format(pre_date);
			}
			
			Integer i_pageIndex = pageIndex == null ? 1 : Integer
					.parseInt(pageIndex);
			int begin = (i_pageIndex - 1) * size;


			Map searchBean = new HashMap();

			searchBean.put("qxcard_no", qxcard_no);


			Community c = (Community) request.getSession().getAttribute(
					"selectCommunity");
			int cid = c.getCommunity_id();

			searchBean.put("community_id", cid);
			searchBean.put("order_serial", order_serial);
			searchBean.put("phone", phone);
			searchBean.put("start", begin);
			searchBean.put("size", 20);
			searchBean.put("time_from", time_from);
			searchBean.put("time_to", time_to);

			int count = qxCardService.getCardOptCount(searchBean);
			List<QxCardLog> list = qxCardService.getCardOptList(searchBean);

			request.setAttribute("list", list);
			request.setAttribute("pageCount", count % size == 0 ? count / size
					: count / size + 1);
			request.setAttribute("pageIndex", i_pageIndex);
			request.setAttribute("searchBean", searchBean);

			return "jsp/qxcard/qxcard_optlog_list";

		} catch (Exception e) {

			logger.error(e.getMessage());
			return "jsp/error";
		}

	}

	@RequestMapping(params = "method=checkUser")
	public void checkUser(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		boolean flag = false;
		try {
			LogerUtil.logRequest(request, logger, "checkUser");

			String phone = CommonUtil.safeToString(request.getParameter("phone"), null);
			if(StringUtils.isNotBlank(phone)) {
				if(!userService.checkPhone(phone.trim())) {
					flag = true;
				}
			}

		} catch (Exception e) {
			logger.error(e);
		}
		if(flag) {
			map.put("ok", "");
		} else {
			map.put("error", "用户不存在");
		}
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(JSONObject.fromObject(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取区享卡优惠金额
	 * */
	@RequestMapping(params = "method=getRestPrice")
	public void getRestPrice(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, logger, "getRestPrice");

            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
			Integer card_value = CommonUtil.safeToInteger(request.getParameter("card_value"), null);	//卡面值
			Integer amount = CommonUtil.safeToInteger(request.getParameter("amount"), null);	//数量

			if(community_id != null && card_value != null && amount != null) {
				Double restValue = qxCardService.calcQxCardRestValue(community_id, card_value, amount);

				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print(restValue);
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 区享卡充值(线下)
	 * */
	@RequestMapping(params = "method=rechargeQxcard")
	public void rechargeQxcard(HttpServletRequest request, HttpServletResponse response) {
		Map map = null;
		try {
			LogerUtil.logRequest(request, logger, "rechargeQxcard");

            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
			String phone = CommonUtil.safeToString(request.getParameter("phone"), null);	//充值手机号
			Integer facevalue = CommonUtil.safeToInteger(request.getParameter("facevalue"), null);	//面值
			Integer amount = CommonUtil.safeToInteger(request.getParameter("amount"), null);	//数量
			Double pay_price = CommonUtil.safeToDouble(request.getParameter("pay_price"), null);	//应付金额

			if(community_id != null && StringUtils.isNotBlank(phone) && facevalue != null && amount != null && pay_price != null) {
				LoginInfo loginInfo = userService.getUserByPhone(phone);
				if(loginInfo != null) {
					MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("user_info");
					QxCardCzLog czLog = qxCardService.recordQxCardCzLog(loginInfo.getUserId(), mgrUser.getUser_id(), community_id, facevalue, amount, pay_price, QxCardCzChannelEnum.POS.getValue(), QxCardCzPayTypeEnum.CASH.getValue());
					if (czLog != null) {
						QxCard qxCard = qxCardService.rechargeQxcard(czLog);
						if(qxCard != null) {
							map = new HashMap();
							map.put("phone", phone);
							map.put("value", qxCard.getValue());
							map.put("cardno", qxCard.getQxcard_no());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("recharge qxcard error", e);
		}
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(map == null ? "" : JSONObject.fromObject(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取用户某一面值的充值数量
	 * */
	@RequestMapping(params = "method=getCzAmountByCardValue")
	public void getCzAmountByCardValue(HttpServletRequest request, HttpServletResponse response) {
		Integer amount = 0;
		try {
			LogerUtil.logRequest(request, logger, "getCzAmountByCardValue");

			String phone = CommonUtil.safeToString(request.getParameter("phone"), null);	//充值手机号
			Integer facevalue = CommonUtil.safeToInteger(request.getParameter("facevalue"), null);	//面值

			if(StringUtils.isNotBlank(phone) && facevalue != null) {
				LoginInfo loginInfo = userService.getUserByPhone(phone);
				if(loginInfo != null) {
					amount = qxCardService.getCzAmountByCardValue(loginInfo.getUserId(), facevalue);
				}
			}
		} catch (Exception e) {
			logger.error("getCzAmountByCardValue error", e);
		}

		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(amount + "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * 展示区享卡充值页面(店员使用，该页面区享卡直接到账)
     * */
	@RequestMapping(params = "method=showRechargePage")
	public String showRechargePage(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, logger, "showRechargePage");

		} catch (Exception e) {
			logger.error("showRechargePage error", e);
		}
		return "/new/jsp/qxcard/recharge";
	}

    /**
     * 展示区享卡充值页面(电信使用，该页面使用支付宝支付，付款之后区享卡到账)
     * */
    @RequestMapping(params = "method=showRechargeByAlipayPage")
    public String showRechargeByAlipayPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "showRechargeByAlipayPage");

            request.setAttribute("alipay", true);
        } catch (Exception e) {
            logger.error("showRechargeByAlipayPage error", e);
        }
        return "/new/jsp/qxcard/recharge";
    }

    @RequestMapping(params = "method=findRechargeCardValue")
    public void findRechargeCardValue(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "findRechargeCardValue");

            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);

            if(community_id != null) {
                List<QxCardCzRule> ruleList = qxCardService.findRechargeCardValue(community_id);
                CommonUtil.outToWeb(response, JSONArray.fromObject(ruleList).toString());
            }
        } catch (Exception e) {
            logger.error("findRechargeCardValue error", e);
        }
    }

    /**
     * 统一检测区享卡充值限制规则：额度限制。。。
     * */
    @RequestMapping(params = "method=checkRule")
    public void checkRule(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("result", "ok");
        try {
            LogerUtil.logRequest(request, logger, "checkRule");

            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
            Integer card_value = CommonUtil.safeToInteger(request.getParameter("card_value"), null);
            Integer amount = CommonUtil.safeToInteger(request.getParameter("amount"), null);
            if(community_id != null && card_value != null && amount != null) {

                //获取该小区的区享卡充值额度配置
                Map m = new HashMap();
                m.put("config_id", "QXCARD_LIMIT");
                m.put("community_id", community_id);
                Config_community configCommunity = configCommunityService.selConfig_community(m);
                if (configCommunity != null) {
                    Integer qxcardLimit = CommonUtil.safeToInteger(configCommunity.getConfig_value(), null);
                    if(qxcardLimit != null) {
                        int sum_cz_price = qxCardService.getSumCzPriceByCid(community_id);
                        if ((sum_cz_price + MathUtil.mul(card_value, amount)) >= qxcardLimit) {
                            map.put("result", "fail");
                            map.put("msg", "当前额度为：" + qxcardLimit + "，已经充值的总金额为：" + sum_cz_price + "。超过额度限制，暂时无法充值！");
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("checkRule error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(map).toString());
    }

    @RequestMapping(params = "method=showSearchQxcardCzLogPage")
    public String showSearchQxcardCzLogPage(HttpServletRequest request, HttpServletResponse response) {
        return "/new/jsp/qxcard/searchQxcardCzLog";
    }

    @RequestMapping(params = "method=searchQxcardCzLog")
    public void searchQxcardCzLog(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            LogerUtil.logRequest(request, logger, "searchQxcardCzLog");

            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);
            CommonUtil.parseParamCommunityId(request, param);

            //处理结束时间
            Date end_cz_date = CommonUtil.safeToDate(param.get("end_cz_time"), "yyyy-MM-dd");
            if(end_cz_date != null) {
                param.put("end_cz_time", CommonUtil.formatDate(DateUtils.addDays(end_cz_date, 1), "yyyy-MM-dd"));
            }

            List<Map> list = qxCardService.searchQxcardCzLog(param);
            int count = qxCardService.searchQxcardCzLogCount(param);

            pager = GridUtil.setPagerResult(pager, list, count);

        } catch (Exception e) {
            logger.error("searchQxcardCzLog error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    @RequestMapping(params = "method=buildAlipayUrl")
    public void buildAlipayUrl(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "buildAlipayUrl");

            Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);
            if(objid != null) {
                QxCardCzLog czLog = qxCardService.getQxcardCzLogById(objid);
                if(czLog != null) {
                    String url = qxCardService.buildAlipayUrl(czLog);
                    CommonUtil.outToWeb(response, url);
                }
            }

        } catch (Exception e) {
            logger.error("buildAlipayUrl error", e);
        }
    }

    /**
     * 通过支付宝充值区享卡，返回跳转到支付宝页面的url
     * */
    @RequestMapping(params = "method=rechargeQxcardByAlipay")
    public void rechargeQxcardByAlipay(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "showAlipayPage");

            String phone = CommonUtil.safeToString(request.getParameter("phone"), null);	//充值手机号
            Integer facevalue = CommonUtil.safeToInteger(request.getParameter("facevalue"), null);	//面值
            Integer amount = CommonUtil.safeToInteger(request.getParameter("amount"), null);	//数量
            Double pay_price = CommonUtil.safeToDouble(request.getParameter("pay_price"), null);	//应付金额

            Community community = (Community)request.getSession().getAttribute("selectCommunity");

            if(StringUtils.isNotBlank(phone) && facevalue != null && amount != null && pay_price != null) {
                LoginInfo loginInfo = userService.getUserByPhone(phone);
                if(loginInfo != null) {
                    MgrUser mgrUser = (MgrUser) request.getSession().getAttribute("user_info");
                    QxCardCzLog czLog = qxCardService.recordQxCardCzLog(loginInfo.getUserId(), mgrUser.getUser_id(), community.getCommunity_id(), facevalue, amount, pay_price, QxCardCzChannelEnum.CHINANET.getValue(), QxCardCzPayTypeEnum.ALIPAY.getValue());
                    if (czLog != null) {
                        String url = qxCardService.buildAlipayUrl(czLog);

                        Map map = new HashMap();
                        map.put("url", url);
                        map.put("objid", czLog.getObjid());
                        CommonUtil.outToWeb(response, JSONObject.fromObject(map).toString());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("showAlipayPage error");
        }
    }

    @RequestMapping(params = "method=getQxcardCzLogById")
    public void getQxcardCzLogById(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, logger, "getQxcardCzLogById");

            Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);	//面值

            if(objid != null) {
                QxCardCzLog czLog = qxCardService.getQxcardCzLogById(objid);
                CommonUtil.outToWeb(response, JSONObject.fromObject(czLog).toString());
            }
        } catch (Exception e) {
            logger.error("getQxcardCzLogById error");
        }
    }

    @RequestMapping(params = "method=showListRegLogPage")
    public String showListRegLogPage(HttpServletRequest request, HttpServletResponse response) {
        return "/new/jsp/qxcard/listRegLog";
    }

    @RequestMapping(params = "method=searchRegLog")
    public void searchRegLog(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            LogerUtil.logRequest(request, logger, "searchRegLog");

            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);
            CommonUtil.parseParamCommunityId(request, param);
            //处理结束时间
            Date time_to_d = CommonUtil.safeToDate(param.get("time_to"), "yyyy-MM-dd");
            if(time_to_d != null) {
                param.put("time_to", CommonUtil.formatDate(DateUtils.addDays(time_to_d, 1), "yyyy-MM-dd"));
            }

            List<Map> list = qxCardService.searchRegLog(param);
            int count = qxCardService.searchRegLogCount(param);

            pager = GridUtil.setPagerResult(pager, list, count);

        } catch (Exception e) {
            logger.error("searchRegLog error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());

    }

    @RequestMapping(params = "method=showListOptLogPage")
    public String showListOptLogPage(HttpServletRequest request, HttpServletResponse response) {
        return "/new/jsp/qxcard/listOptLog";
    }

    @RequestMapping(params = "method=searchOptLog")
    public void searchOptLog(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            LogerUtil.logRequest(request, logger, "searchOptLog");

            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);
            CommonUtil.parseParamCommunityId(request, param);
            //处理结束时间
            Date time_to_d = CommonUtil.safeToDate(param.get("time_to"), "yyyy-MM-dd");
            if(time_to_d != null) {
                param.put("time_to", CommonUtil.formatDate(DateUtils.addDays(time_to_d, 1), "yyyy-MM-dd"));
            }

            List<Map> list = qxCardService.searchOptLog(param);
            int count = qxCardService.searchOptLogCount(param);

            pager = GridUtil.setPagerResult(pager, list, count);

        } catch (Exception e) {
            logger.error("searchOptLog error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());

    }

    @RequestMapping(params = "method=showListUserQxcardPage")
    public String showListUserQxcardPage(HttpServletRequest request, HttpServletResponse response) {
        return "/new/jsp/qxcard/listUserQxcard";
    }

    @RequestMapping(params = "method=searchUserQxcard")
    public void searchUserQxcard(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            LogerUtil.logRequest(request, logger, "searchUserQxcard");

            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);
            CommonUtil.parseParamCommunityId(request, param);
            //处理结束时间
            Date time_to_d = CommonUtil.safeToDate(param.get("time_to"), "yyyy-MM-dd");
            if(time_to_d != null) {
                param.put("time_to", CommonUtil.formatDate(DateUtils.addDays(time_to_d, 1), "yyyy-MM-dd"));
            }

            List<Map> list = qxCardService.searchUserQxcard(param);
            int count = qxCardService.searchUserQxcardCount(param);

            pager = GridUtil.setPagerResult(pager, list, count);

        } catch (Exception e) {
            logger.error("searchUserQxcard error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());

    }

    @RequestMapping(params = "method=cancelQxcardCz")
    public void cancelQxcardCz(HttpServletRequest request, HttpServletResponse response) {
        String result = "fail";
        try {
            Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);
            if(objid != null) {
                QxCardCzLog czLog = qxCardService.getQxcardCzLogById(objid);
                if(czLog != null) {
                    czLog.setStatus(QxCardCzStatusEnum.CANCEL.getValue());
                    qxCardService.saveQxCardCzLog(czLog);
                    result = "success";
                }
            }
        } catch (Exception e) {
            logger.error("cancelQxcardCz error", e);
        }
        CommonUtil.outToWeb(response, result);
    }

    @RequestMapping(params = "method=saveRegLog")
    public void saveRegLog(HttpServletRequest request, HttpServletResponse response) {
        LogerUtil.logRequest(request, logger, "saveRegLog");
        RtnResult result = new RtnResult(false);
        try {
            Integer cid = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
            String qxcard_no = request.getParameter("qxcard_no");
            Integer op_type = CommonUtil.safeToInteger(request.getParameter("op_type"), null);
            String custm_name = request.getParameter("custm_name");
            String custm_phone = request.getParameter("custm_phone");
            String custm_dec = request.getParameter("custm_dec");
            String seller_dec = request.getParameter("seller_dec");

            if(cid != null && op_type != null && StringUtils.isNotBlank(qxcard_no)) {
                QxCard qxCard = new QxCard();
                qxCard.setQxcard_no(qxcard_no);
                qxCard = qxCardService.getQxCard(qxCard);
                if(qxCard == null) {
                    result.setMsg("操作失败，卡号不存在或已开卡！");
                } else {
                    if(op_type.intValue() == 1 || op_type.intValue() == 2 || op_type.intValue() == 3) {
                        String sname = ((MgrUser) request.getSession().getAttribute("user_info")).getLoginname();

                        QxcardReglog qxcardReglog = new QxcardReglog();
                        qxcardReglog.setCustm_name(custm_name);
                        qxcardReglog.setCustm_phone(custm_phone);
                        qxcardReglog.setCustm_dec(custm_dec);
                        qxcardReglog.setSeller_dec(seller_dec);
                        qxcardReglog.setQxcard_no(qxcard_no);
                        qxcardReglog.setOp_type(op_type);
                        qxcardReglog.setCid(cid);
                        qxcardReglog.setSeller_name(sname);
                        qxcardReglog.setSeller_ip(CommonUtil.getIp(request));
                        qxCardService.insertQxcardReglog(qxcardReglog);

                        if (op_type.intValue() == 1) {
                            //开卡
                            qxCard.setStatus(2);    //开卡
                            Calendar calendar = Calendar.getInstance();
                            calendar.add(Calendar.YEAR, 3);
                            qxCard.setExpire_time(new Timestamp(calendar.getTimeInMillis()));
                            qxCardService.updateQxCard(qxCard);
                        } else if (op_type.intValue() == 2) {
                            //冻结
                            qxCard.setDisabled(2);  //冻结
                            qxCardService.updateQxCard(qxCard);

                            UserQxCard userQxCard = new UserQxCard();
                            userQxCard.setQxcard_no(qxcard_no);
                            userQxCard.setDisabled(2);  //冻结
                            qxCardService.updateUserQxCard(userQxCard);
                        } else if (op_type.intValue() == 3) {
                            //解冻
                            qxCard.setDisabled(1);  //正常
                            qxCardService.updateQxCard(qxCard);

                            UserQxCard userQxCard = new UserQxCard();
                            userQxCard.setQxcard_no(qxcard_no);
                            userQxCard.setDisabled(1);  //正常
                            qxCardService.updateUserQxCard(userQxCard);
                        }
                        result.setResult(true);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    @RequestMapping(params = "method=updateQxcardDisabled")
    public void updateQxcardDisabled(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            String qxcard_no = request.getParameter("qxcard_no");
            Integer disabled = CommonUtil.safeToInteger(request.getParameter("disabled"), null);
            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);

            if(StringUtils.isNotBlank(qxcard_no) && disabled != null && community_id != null) {
                QxCard qxCard = new QxCard();
                qxCard.setQxcard_no(qxcard_no);
                qxCard.setDisabled(disabled);
                qxCardService.updateQxCard(qxCard);

                UserQxCard userQxCard = new UserQxCard();
                userQxCard.setQxcard_no(qxcard_no);
                userQxCard.setDisabled(disabled);
                qxCardService.updateUserQxCard(userQxCard);

                String sname = ((MgrUser) request.getSession().getAttribute("user_info")).getLoginname();

                QxcardReglog qxcardReglog = new QxcardReglog();
                qxcardReglog.setQxcard_no(qxcard_no);
                qxcardReglog.setOp_type(disabled.intValue() == 2 ? 2 : 3);  //2 冻结  3 解冻
                qxcardReglog.setCid(community_id);
                qxcardReglog.setSeller_name(sname);
                qxcardReglog.setSeller_ip(CommonUtil.getIp(request));
                qxCardService.insertQxcardReglog(qxcardReglog);

                result.setResult(true);
            }
        } catch (Exception e) {
            logger.error("updateQxcardDisabled error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }
}
