package com.brilliantreform.sc.activity.controller;

import com.brilliantreform.sc.activity.po.PrizeInfo;
import com.brilliantreform.sc.activity.service.PrizeService;
import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.common.RtnResult;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.ExportUtils;
import com.brilliantreform.sc.utils.GridUtil;
import com.brilliantreform.sc.utils.MathUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("prize.do")
public class PrizeCtrl {
    private static Logger LOGGER = Logger.getLogger(PrizeCtrl.class);

    @Autowired
    private PrizeService prizeService;

    @RequestMapping(params = "method=showListPrizeLogPage")
    public String showListPrizeLogPage(HttpServletRequest request, HttpServletResponse response) {
        return "/new/jsp/activity/prize/listPrizeLog";
    }

    @RequestMapping(params = "method=searchPrizeLog")
    public void searchPrizeLog(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);
            CommonUtil.parseParamCommunityId(request, param);

            Date end_time_d = CommonUtil.safeToDate(param.get("end_time"), "yyyy-MM-dd");
            if(end_time_d != null) {
                param.put("end_time", CommonUtil.formatDate(DateUtils.addDays(end_time_d, 1), "yyyy-MM-dd"));
            }

            List<Map> list = prizeService.searchPrizeLog(param);
            int count = prizeService.searchPrizeLogCount(param);

            pager = GridUtil.setPagerResult(pager, list, count);
        } catch (Exception e) {
            LOGGER.error("searchPrizeLog error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    @RequestMapping(params = "method=acceptPrize")
    public void acceptPrize(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            Integer log_id = CommonUtil.safeToInteger(request.getParameter("log_id"), null);

            if(log_id != null) {
                MgrUser user = (MgrUser) request.getSession().getAttribute("user_info");
                prizeService.acceptPrize(log_id, user.getLoginname());
                result.setResult(true);
            }
        } catch (Exception e) {
            LOGGER.error("acceptPrize error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    @RequestMapping(params = "method=showSettingPage")
    public String showSettingPage(HttpServletRequest request, HttpServletResponse response) {
        return "/new/jsp/activity/prize/setting";
    }

    @RequestMapping(params = "method=searchPrizeInfo")
    public void searchPrizeInfo(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);

            List<Map> list = prizeService.searchPrizeInfo(param);
            int count = prizeService.searchPrizeInfoCount(param);

            //解析中奖率
            for(Map prize : list) {
                Integer probability = CommonUtil.safeToInteger(prize.get("probability"), null);
                if(probability != null) {
                    double rate = MathUtil.div(probability, 10000, 2);
                    prize.put("rate", rate);
                }
            }

            pager = GridUtil.setPagerResult(pager, list, count);
        } catch (Exception e) {
            LOGGER.error("searchPrizeInfo error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    @RequestMapping(params = "method=savePrize")
    public void savePrize(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            Integer prize_id = CommonUtil.safeToInteger(request.getParameter("prize_id"), null);
            Integer cid = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
            Double rate = CommonUtil.safeToDouble(request.getParameter("rate"), null);
            Integer count = CommonUtil.safeToInteger(request.getParameter("count"), null);
            String prize_level = request.getParameter("prize_level");
            String prize_name = request.getParameter("prize_name");
            String prize_img = request.getParameter("prize_img");
            String prize_dec = request.getParameter("prize_dec");

            if(rate != null && count != null && cid != null && StringUtils.isNotBlank(prize_level) && StringUtils.isNotBlank(prize_name)) {
                if(rate.doubleValue() < 0d || rate.doubleValue() > 100d) {
                    result.setMsg("中奖概率设置不正确！");
                } else {

                    PrizeInfo prize = null;
                    if(prize_id != null) {
                        prize = prizeService.getPrizeInfoById(prize_id);
                    } else {
                        prize = new PrizeInfo();
                    }

                    if(prize != null) {
                        //校验该门店当前奖项是否存在
                        PrizeInfo _prize = prizeService.getPrizeInfo(cid, prize_level, prize_id);
                        if(_prize != null) {
                            result.setMsg(prize_level + "已经存在！");
                        } else {
                            int probability = (int)(rate * 1000000 / 100);

                            prize.setProbability(probability);
                            prize.setCount(count);
                            prize.setCid(cid);
                            prize.setPrize_level(prize_level);
                            prize.setPrize_name(prize_name);
                            prize.setPrize_img(prize_img);
                            prize.setPrize_dec(prize_dec);
                            prize.setUpdatetime(new Date());
                            prizeService.savePrizeInfo(prize);
                            result.setResult(true);
                        }
                    }
                }
            } else {
                result.setMsg("表单参数填写不完整！");
            }

        } catch (Exception e) {
            LOGGER.error("savePrize error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    @RequestMapping(params = "method=removePrize")
    public void removePrize(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            Integer prize_id = CommonUtil.safeToInteger(request.getParameter("prize_id"), null);

            if(prize_id != null) {
                prizeService.deletePrizeInfo(prize_id);
                result.setResult(true);
            }
        } catch (Exception e) {
            LOGGER.error("removePrize error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }
}
