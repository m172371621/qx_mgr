package com.brilliantreform.sc.card.controller;

import com.brilliantreform.sc.activity.po.CardNumConfig;
import com.brilliantreform.sc.card.service.CardService;
import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.community.service.CommunityService;
import com.brilliantreform.sc.order.po.WinningInfo;
import com.brilliantreform.sc.order.service.OrderService;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.GridUtil;
import com.brilliantreform.sc.utils.LogerUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("card.do")
public class CardCtrl {

	private static Logger logger = Logger.getLogger(CardCtrl.class);

	@Autowired
	private CardService cardService;
	@Autowired
	private CommunityService communityService;
	@Autowired
	private OrderService orderService;

	@RequestMapping(params = "method=cardManager")
	public String cardManager(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, logger, "cardManager");

			Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
			if (community_id == null) {
				Community c = (Community) request.getSession().getAttribute("selectCommunity");
				if (c != null) {
					community_id = c.getCommunity_id();
				}
			}
			if (community_id != null) {
				request.setAttribute("community_id", community_id);
				// 获取该小区的卡牌设置
				List<Map> list = cardService.findCommunityCardInfo(community_id);
				if (list != null && list.size() > 0) {
					// 处理中奖率
					int last_probability = 0;
					for (Map map : list) {
						Integer probability = CommonUtil.safeToInteger(map.get("probability"), null);
						if (probability != null) {
							double rate = (probability - last_probability) * 100.0 / 1000000;
							map.put("rate", rate);
							last_probability = probability;
						}
					}
					request.setAttribute("list", list);
				}
			}
			request.setAttribute("community_list", communityService.getCommunityList());
		} catch (Exception e) {
			logger.error("cardManager error", e);
			return "/jsp/error";
		}
		return "jsp/card/cardManager";
	}

	@RequestMapping(params = "method=saveRate")
	public String saveRate(HttpServletRequest request, HttpServletResponse response) {
		Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
		try {
			LogerUtil.logRequest(request, logger, "saveRate");

			String[] level_id_array = request.getParameterValues("level_id");
			String[] rate_array = request.getParameterValues("rate");
			String[] amount_array = request.getParameterValues("amount");
			String[] award_array = request.getParameterValues("award");

			if (community_id != null && level_id_array != null && level_id_array.length > 0 && rate_array != null && rate_array.length > 0
					&& amount_array != null && amount_array.length > 0 && award_array != null && award_array.length > 0) {
				// 开始计算probability，已经按照probability从小到大排序了，故理应是一等奖在最前面
				int last_probability = 0;
				for (int i = 0; i < level_id_array.length; i++) {
					Integer level_id = CommonUtil.safeToInteger(level_id_array[i], null);
					Double rate = CommonUtil.safeToDouble(rate_array[i], 0d);
					Integer amount = CommonUtil.safeToInteger(amount_array[i], 0);
					String award = award_array[i];
					if (level_id != null) {
						int probability = (int) (rate * 1000000 / 100) + last_probability;
						cardService.updateCardLevel(level_id, probability);
						cardService.updateAmount(community_id, level_id, amount);
						cardService.updateAward(community_id, level_id, award);
						last_probability = probability;
					}
				}
			}
		} catch (Exception e) {
			logger.error("saveRate error", e);
			return "/jsp/error";
		}
		return "redirect:/card.do?method=cardManager&community_id=" + community_id;
	}

	/**
	 * 根据订单总价按比例计算卡牌数量
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "method=cardNum")
	public String cardNum(HttpServletRequest request, HttpServletResponse response) {
		try {
			LogerUtil.logRequest(request, logger, "cardNum");
			Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
			if (community_id == null) {
				Community c = (Community) request.getSession().getAttribute("selectCommunity");
				if (c != null) {
					community_id = c.getCommunity_id();
				}
			}
			List<Map> list = null;
			if (community_id != null) {
				request.setAttribute("community_id", community_id);
				list = cardService.findCardNum(community_id);

			}
			request.setAttribute("list", list);
			request.setAttribute("community_list", communityService.getCommunityList());
		} catch (Exception e) {
			logger.error("saveRate error", e);
			return "/jsp/error";
		}
		return "jsp/card/card_num";
	}

	@RequestMapping(params = "method=delCardNum")
	public String delCardNum(HttpServletRequest request, HttpServletResponse response) {
		LogerUtil.logRequest(request, logger, "delCardNum");
		try {
			int objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);
			int num = cardService.delCardNum(objid);
			response.getWriter().print(num != 0 ? "ok" : "error");
		} catch (Exception e) {
			logger.error("saveRate error", e);
			return "/jsp/error";
		}
		return null;
	}

	// saveCardNum

	@RequestMapping(params = "method=saveCardNum")
	public String saveCardNum(HttpServletRequest request, HttpServletResponse response) {
		LogerUtil.logRequest(request, logger, "saveCardNum");
		try {
			String[] order_price = request.getParameterValues("order_price"); // 订单总价
			String[] card_num = request.getParameterValues("card_num"); // 卡牌数量
			String[] objid = request.getParameterValues("objid"); // card_num_config
																	// -> objid

			int count = 0;
			if(null != objid) {
				for (int i = 0; i < objid.length; i++) {
					count++;
					int oid = Integer.parseInt(objid[i]);
					int cn = Integer.parseInt(card_num[i]);
					double op = Double.parseDouble(order_price[i]);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("objid", oid);
					map.put("card_num", cn);
					map.put("order_price", op);
					cardService.updateCardNum(map); // 更新操作
				}
			}
			Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
			
			for (int i = count; i < order_price.length; i++) {
				int cn = Integer.parseInt(card_num[i]);
				double op = Double.parseDouble(order_price[i]);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("card_num", cn);
				map.put("order_price", op);
				map.put("community_id", community_id);
				cardService.insertCardNum(map); // 插入操作
			}

		} catch (Exception e) {
			logger.error("saveRate error", e);
			return "/jsp/error";
		}
		return "redirect:/card.do?method=cardNum";
	}

	@RequestMapping(params = "method=showListAwardLog")
	public String showListAwardLog(HttpServletRequest request, HttpServletResponse response) {
		return "/new/jsp/card/listAwardLog";
	}

	@RequestMapping(params = "method=listAwardLog", method = { RequestMethod.POST, RequestMethod.GET })
	public void listAwardLog(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
		Pager pager = new Pager();
		try {
			LogerUtil.logRequest(request, logger, "listAwardLog");

			pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

			Map param = GridUtil.parseGridPager(pager);
            CommonUtil.parseParamCommunityId(request, param);

			Date end_time = CommonUtil.safeToDate(param.get("end_time"), "yyyy-MM-dd");
			if(end_time != null) {
				param.put("end_time", CommonUtil.formatDate(DateUtils.addDays(end_time, 1), "yyyy-MM-dd"));
			}

			List<WinningInfo> list  = (List<WinningInfo>) orderService.getWinning(param);
			int count = orderService.getWinningCount(param);

			pager = GridUtil.setPagerResult(pager, list, count);

		} catch (Exception e) {
			logger.error("listAwardLog error", e);
			pager.setIsSuccess(false);
		}
		CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
	}

    @RequestMapping(params = "method=searchAwardInfo")
    public void searchAwardInfo(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            LogerUtil.logRequest(request, logger, "searchAwardInfo");

            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);
            CommonUtil.parseParamCommunityId(request, param);

            List<Map> list = cardService.findCommunityCardInfoV4(param);
            if (list != null && list.size() > 0) {
                // 处理中奖率
                int last_probability = 0;
                Iterator<Map> iter = list.iterator();
                while(iter.hasNext()) {
                    Map map = iter.next();
                    Integer probability = CommonUtil.safeToInteger(map.get("probability"), null);
                    if (probability != null) {
                        if(probability.intValue() == 1000000) {
                            iter.remove();
                        } else {
                            double rate = (probability - last_probability) * 100.0 / 1000000;
                            map.put("rate", rate);
                            last_probability = probability;
                        }
                    }
                }
            }

            pager = GridUtil.setPagerResult(pager, list, list.size());

        } catch (Exception e) {
            logger.error("searchAwardInfo error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());

    }

    @RequestMapping(params = "method=showSettingPage")
    public String showSettingPage(HttpServletRequest request, HttpServletResponse response) {
        return "/new/jsp/card/setting";
    }

    @RequestMapping(params = "method=updateCardSetting")
    public void updateCardSetting(HttpServletRequest request, HttpServletResponse response) {
        String result = "fail";
        try {
            LogerUtil.logRequest(request, logger, "updateCardSetting");

            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
            Integer level_id = CommonUtil.safeToInteger(request.getParameter("level_id"), null);
            Double rate = CommonUtil.safeToDouble(request.getParameter("rate"), null);
            String award = request.getParameter("award");
            Integer amount = CommonUtil.safeToInteger(request.getParameter("amount"), null);

            if(community_id != null && level_id != null && rate != null && award != null && amount != null) {
                Map param = new HashMap();
                param.put("community_id", community_id);
                CommonUtil.parseParamCommunityId(request, param);
                List<Map> list = cardService.findCommunityCardInfoV4(param);

                if (list != null && list.size() > 0) {
                    // 处理中奖率
                    int last_probability = 0;
                    Iterator<Map> iter = list.iterator();
                    while(iter.hasNext()) {
                        Map map = iter.next();
                        Integer probability = CommonUtil.safeToInteger(map.get("probability"), null);
                        if (probability != null) {
                            if(probability.intValue() == 1000000) {
                                iter.remove();
                            } else {
                                double _rate = (probability - last_probability) * 100.0 / 1000000;
                                map.put("rate", _rate);
                                last_probability = probability;
                            }
                        }
                    }
                }

                // 开始计算probability，已经按照probability从小到大排序了，故理应是一等奖在最前面
                int last_probability = 0;
                for(Map map : list) {
                    Integer _level_id = CommonUtil.safeToInteger(map.get("level_id"), null);
                    Double _rate = CommonUtil.safeToDouble(map.get("rate"), 0d);
                    //Integer _amount = CommonUtil.safeToInteger(map.get("amount"), 0);
                    //String _award = CommonUtil.safeToString(map.get("award"), "");
                    if (_level_id != null) {
                        int probability = (int) (((level_id.intValue() == _level_id.intValue()) ? rate : _rate) * 1000000 / 100) + last_probability;
                        cardService.updateCardLevel(_level_id, probability);

                        if(level_id.intValue() == _level_id.intValue()) {
                            cardService.updateAmount(community_id, _level_id, amount);
                            cardService.updateAward(community_id, _level_id, award);
                        }

                        last_probability = probability;
                    }
                }
                result = "success";
            }

        } catch (Exception e) {
            logger.error("updateCardSetting error", e);
        }
        CommonUtil.outToWeb(response, result);
    }

    @RequestMapping(params = "method=searchCardNumConfig")
    public void searchCardNumConfig(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            LogerUtil.logRequest(request, logger, "searchCardNumConfig");

            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);
            CommonUtil.parseParamCommunityId(request, param);

            List<Map> list = cardService.searchCardNumConfig(param);

            pager = GridUtil.setPagerResult(pager, list, list.size());
        } catch (Exception e) {
            logger.error("searchCardNumConfig error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    @RequestMapping(params = "method=saveCardNumConfig")
    public void saveCardNumConfig(HttpServletRequest request, HttpServletResponse response) {
        String result = "fail";
        try {
            LogerUtil.logRequest(request, logger, "searchCardNumConfig");

            Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);
            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
            Double order_price = CommonUtil.safeToDouble(request.getParameter("order_price"), null);
            Integer card_num = CommonUtil.safeToInteger(request.getParameter("card_num"), null);

            if(community_id != null && order_price != null && card_num != null) {
                CardNumConfig cardNumConfig = null;
                if(objid != null) {
                    cardNumConfig = cardService.getCardNumConfigById(objid);
                } else {
                    cardNumConfig = new CardNumConfig();
                }

                if(cardNumConfig != null) {
                    cardNumConfig.setCommunity_id(community_id);
                    cardNumConfig.setOrder_price(order_price);
                    cardNumConfig.setCard_num(card_num);
                    cardService.saveCardNumConfig(cardNumConfig);
                    result = "success";
                }
            }

        } catch (Exception e) {
            logger.error("searchCardNumConfig error", e);
        }
        CommonUtil.outToWeb(response, result);
    }

}
