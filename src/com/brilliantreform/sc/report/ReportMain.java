package com.brilliantreform.sc.report;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brilliantreform.sc.utils.LogerUtil;

@Controller
@RequestMapping("reportmain.do")
public class ReportMain {
	private static Logger logger = Logger.getLogger(ReportMain.class);

	/**
	 * 跳转商品利润表
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=saleprofit", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String saleprofit(HttpServletRequest request) {
        //获得前台传过来的开始时间和结束时间
		String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
		String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
		LogerUtil.logRequest(request, logger, "saleprofit");

		request.setAttribute("time_to", time_to);
		request.setAttribute("time_from", time_from);
		return "jsp/report/saleprofit";
	}
	
	/**
	 * 跳转新增用户统计表
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=newadd_sumeric", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String newadd_sumeric(HttpServletRequest request) {

		String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
		String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
		LogerUtil.logRequest(request, logger, "saleprofit");

		request.setAttribute("time_to", time_to);
		request.setAttribute("time_from", time_from);
		return "jsp/report/newuser_sumeric";
	}
	
	
	/**
	 * 跳转区享卡开卡统计
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=qxcard_sumeric", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String qxcard_sumeric(HttpServletRequest request) {

		String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
		String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
		LogerUtil.logRequest(request, logger, "qxcard_sumeric");

		request.setAttribute("time_to", time_to);
		request.setAttribute("time_from", time_from);
		return "jsp/report/qxcard_sumeric";
	}
	
	/**
	 * 跳转销售日报表
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=date_sale_report", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String date_sale_report(HttpServletRequest request) {

		String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
		String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
		LogerUtil.logRequest(request, logger, "qxcard_sumeric");

		request.setAttribute("time_to", time_to);
		request.setAttribute("time_from", time_from);
		return "jsp/report/date_sale_report";
	}
	
	/**
	 * 跳转商品分类销售日报表
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=service_sale_report", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String service_sale_report(HttpServletRequest request) {

		String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
		String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
		LogerUtil.logRequest(request, logger, "qxcard_sumeric");

		request.setAttribute("time_to", time_to);
		request.setAttribute("time_from", time_from);
		return "jsp/report/service_sale_report";
	}
	
	/**
	 * 跳转支付日报表
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=fee_sumery", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String fee_sumery(HttpServletRequest request) {

		String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
		String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
		LogerUtil.logRequest(request, logger, "fee_sumery");

		request.setAttribute("time_to", time_to);
		request.setAttribute("time_from", time_from);
		return "jsp/report/fee_sumery";
	}
	
	/**
	 * 跳转订单配送统计表
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=orderps_sumery", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String orderps_sumery(HttpServletRequest request) {

		String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
		String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
		LogerUtil.logRequest(request, logger, "fee_sumery");

		request.setAttribute("time_to", time_to);
		request.setAttribute("time_from", time_from);
		return "jsp/report/orderps_sumery";
	}
	
	/**
	 * 跳转订单配送统计表，不分小区
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=orderps_sumery_sum", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String orderps_sumery_sum(HttpServletRequest request) {

		String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
		String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
		LogerUtil.logRequest(request, logger, "fee_sumery");

		request.setAttribute("time_to", time_to);
		request.setAttribute("time_from", time_from);
		return "jsp/report/orderps_sumery_sum";
	}
	
	/**
	 * 跳转商品利润表
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=saleprofit_low", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String saleprofit_low(HttpServletRequest request) {
        //获得前台传过来的开始时间和结束时间
		String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
		String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
		LogerUtil.logRequest(request, logger, "saleprofit");

		request.setAttribute("time_to", time_to);
		request.setAttribute("time_from", time_from);
		return "jsp/report/saleprofit_low";
	}
	
	/**
	 * 跳转支付总表
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=fee_sumery_sum", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String fee_sumery_sum(HttpServletRequest request) {
        //获得前台传过来的开始时间和结束时间
		String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
		String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
		LogerUtil.logRequest(request, logger, "saleprofit");

		request.setAttribute("time_to", time_to);
		request.setAttribute("time_from", time_from);
		return "jsp/report/fee_sumery_sum";
	}
	
	/**
	 * 跳转支出总表
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=feerefund_sumery_sum", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String feerefund_sumery_sum(HttpServletRequest request) {
        //获得前台传过来的开始时间和结束时间
		String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
		String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
		LogerUtil.logRequest(request, logger, "saleprofit");

		request.setAttribute("time_to", time_to);
		request.setAttribute("time_from", time_from);
		return "jsp/report/feerefund_sumery_sum";
	}
	
	/**
	 * 跳转支出总表
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=outgoing_detail", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String outgoing_detail(HttpServletRequest request) {
        //获得前台传过来的开始时间和结束时间
		String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
		String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
				(new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
		String community_id=request.getParameter("community_id") ;
		String stock_type=request.getParameter("stock_type");
		LogerUtil.logRequest(request, logger, "outgoing_detail");

		request.setAttribute("time_to", time_to);
		request.setAttribute("time_from", time_from);
		request.setAttribute("community_id", community_id);
		request.setAttribute("stock_type", stock_type);
		return "jsp/report/outgoing_detail";
	}


    @RequestMapping(params = "method=showSaleReportPage")
    public String showSaleReportPage(HttpServletRequest request) {
        String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
        String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
        request.setAttribute("time_to", time_to);
        request.setAttribute("time_from", time_from);
        return "/new/jsp/report/saleReport";
    }

    @RequestMapping(params = "method=showTotalRefundReportPage")
    public String showTotalRefundReportPage(HttpServletRequest request) {
        //获得前台传过来的开始时间和结束时间
        String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
        String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
        LogerUtil.logRequest(request, logger, "saleprofit");

        request.setAttribute("time_to", time_to);
        request.setAttribute("time_from", time_from);
        return "/new/jsp/report/totalRefundReport";
    }

    @RequestMapping(params = "method=showTotalPayReportPage")
    public String showTotalPayReportPage(HttpServletRequest request) {
        //获得前台传过来的开始时间和结束时间
        String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
        String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
        LogerUtil.logRequest(request, logger, "saleprofit");

        request.setAttribute("time_to", time_to);
        request.setAttribute("time_from", time_from);
        return "/new/jsp/report/totalPayReport";
    }

    @RequestMapping(params = "method=showTotalOrderDeliveryReportPage")
    public String showTotalOrderDeliveryReportPage(HttpServletRequest request) {
        String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
        String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
        LogerUtil.logRequest(request, logger, "fee_sumery");

        request.setAttribute("time_to", time_to);
        request.setAttribute("time_from", time_from);
        return "/new/jsp/report/totalOrderDeliveryReport";
    }

    @RequestMapping(params = "method=showProductServiceSaleReportPage")
    public String showProductServiceSaleReportPage(HttpServletRequest request) {

        String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
        String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
        LogerUtil.logRequest(request, logger, "qxcard_sumeric");

        request.setAttribute("time_to", time_to);
        request.setAttribute("time_from", time_from);
        return "/new/jsp/report/productServiceSaleReport";
    }

    @RequestMapping(params = "method=showOrderDeliveryReportPage")
    public String showOrderDeliveryReportPage(HttpServletRequest request) {
        String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
        String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
        LogerUtil.logRequest(request, logger, "fee_sumery");

        request.setAttribute("time_to", time_to);
        request.setAttribute("time_from", time_from);
        return "/new/jsp/report/orderDeliveryReport";
    }

    @RequestMapping(params = "method=showSaleProfitReportPage")
    public String showSaleProfitReportPage(HttpServletRequest request) {
        //获得前台传过来的开始时间和结束时间
        String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
        String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
        LogerUtil.logRequest(request, logger, "saleprofit");

        request.setAttribute("time_to", time_to);
        request.setAttribute("time_from", time_from);
        return "/new/jsp/report/saleProfitReport";
    }

    @RequestMapping(params = "method=showNewUserReportPage")
    public String showNewUserReportPage(HttpServletRequest request) {

        String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
        String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
        LogerUtil.logRequest(request, logger, "saleprofit");

        request.setAttribute("time_to", time_to);
        request.setAttribute("time_from", time_from);
        return "/new/jsp/report/newUserReport";
    }

    @RequestMapping(params = "method=showQxcardSaleReportPage")
    public String showQxcardSaleReportPage(HttpServletRequest request) {

        String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
        String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
        LogerUtil.logRequest(request, logger, "qxcard_sumeric");

        request.setAttribute("time_to", time_to);
        request.setAttribute("time_from", time_from);
        return "/new/jsp/report/qxcardSaleReport";
    }

    @RequestMapping(params = "method=showPayReportPage")
    public String showPayReportPage(HttpServletRequest request) {

        String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
        String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
        LogerUtil.logRequest(request, logger, "fee_sumery");

        request.setAttribute("time_to", time_to);
        request.setAttribute("time_from", time_from);
        return "/new/jsp/report/payReport";
    }

    @RequestMapping(params = "method=showRefundDetailReportPage")
    public String showRefundDetailReportPage(HttpServletRequest request) {

        String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
        String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
        LogerUtil.logRequest(request, logger, "fee_sumery");

        request.setAttribute("time_to", time_to);
        request.setAttribute("time_from", time_from);
        return "/new/jsp/report/refundDetailReport";
    }
    
    @RequestMapping(params = "method=delivery_report")
    public String showDeliveryReport(HttpServletRequest request) {

        String time_to = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() + 1 * 24 * 60 * 60 * 1000));
        String time_from = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
                (new Date()).getTime() - 7 * 24 * 60 * 60 * 1000));
        LogerUtil.logRequest(request, logger, "delivery_report");

        request.setAttribute("time_to", time_to);
        request.setAttribute("time_from", time_from);
        return "/new/jsp/report/delivery_report";
    }
}
