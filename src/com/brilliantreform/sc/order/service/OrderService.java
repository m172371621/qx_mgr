package com.brilliantreform.sc.order.service;

import com.brilliantreform.sc.activity.po.CardNumConfig;
import com.brilliantreform.sc.activity.po.CouponCode;
import com.brilliantreform.sc.activity.po.CouponInfo;
import com.brilliantreform.sc.activity.po.PrizeUser;
import com.brilliantreform.sc.activity.service.CouponService;
import com.brilliantreform.sc.card.dao.CardDao;
import com.brilliantreform.sc.order.dao.MsgInfoDao;
import com.brilliantreform.sc.order.dao.OrderDao;
import com.brilliantreform.sc.order.po.*;
import com.brilliantreform.sc.product.dao.ProductDao;
import com.brilliantreform.sc.product.po.Product;
import com.brilliantreform.sc.product.po.ProductTags;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.SettingUtil;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("orderService")
public class OrderService {
	private static Logger logger = Logger.getLogger(OrderService.class);

	@Autowired
	private OrderDao  orderDao;
	@Autowired
	private ProductDao  productDao;
    @Autowired
    private CardDao cardDao;
    @Autowired
    private CouponService couponService;
    @Autowired
    private MsgInfoDao msgInfoDao;
	
	public void createOrder(Order order){
		
		logger.info("into OrderService createOrder:" + order + ";");
		
		orderDao.createOrder(order);
	}
	
	@SuppressWarnings("unchecked")
	public List<Order> getOrderList(Integer user_id,String month){
		logger.info("into OrderService getOrderList:"+user_id+";"+month);
		
		Map map = new HashMap();
		map.put("user_id", user_id);
		map.put("month", month);
		
		List<Order> list = orderDao.getOrderList(map);
			
		logger.info("OrderService getOrderList:" + list);
		return list;
	}
	
	public List<Order> searchOrderList(OrderSearch searchBean){
		logger.info("into OrderService searchOrderList:" + searchBean);
		
		List<Order> list = orderDao.searchOrderList(searchBean);
			
		//logger.info("OrderService searchOrderList:"+list);
		return list;
	}
	
	public int countOrderList(OrderSearch searchBean){
		logger.info("into OrderService countOrderList:" + searchBean);
		
		int result = orderDao.countOrderList(searchBean);
			
		logger.info("OrderService countOrderList:"+result);
		return result;
	}
	
	public Order getOrderById(Integer order_id){
		logger.info("into OrderService getOrderById:"+order_id);
		
		
		Order order = orderDao.getOrderById(order_id);
			
		logger.info("OrderService getOrderById:"+order);
		return order;
	}
	
	
	public Order getOrderBySerial(String serial_id){
		logger.info("into OrderService getOrderBySerial:"+serial_id);
		
		
		Order order = orderDao.getOrderBySerial(serial_id);
			
		logger.info("OrderService getOrderBySerial:"+order);
		
		return order;
	}
	
	public List<Order> getOrderBySerialList(String serial_id){
		logger.info("into OrderService getOrderBySerialList:"+serial_id);
		
		
		List<Order> order = orderDao.getOrderBySerialList(serial_id);
			
		logger.info("OrderService getOrderBySerialList:"+order);
		
		return order;
	}
	
	public List<Order> getOrderBySerialList2(Order orders){
		logger.info("into OrderService getOrderBySerialList2:"+orders);
		
		
		List<Order> order = orderDao.getOrderBySerialList2(orders);
			
		logger.info("OrderService getOrderBySerialList2:"+order);
		
		return order;
	}
	
	
	public void updateOrder(Order order){
		orderDao.updateOrder(order);
		if(order.getOrder_status()!=null && 3 == order.getOrder_status())
		{
			orderDao.updateOrderCard(order.getOrder_id());
		}
	}
	
	public int countOrderByProduct(Integer productId){
		return orderDao.countOrderByProduct(productId);
	}
	
	@SuppressWarnings("unchecked")
	public int countProduct(Integer product_id,Integer service_id,String product_name,String service_name,Integer cid ){
		Map map = new HashMap();
		map.put("product_id", product_id);
		map.put("service_id", service_id);
		map.put("product_name", product_name);
		map.put("service_name", service_name);
		map.put("cid", cid);
		return orderDao.countProduct(map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map> searchProduct(Integer begin,Integer size,Integer product_id,Integer service_id,String product_name,String service_name,Integer cid ){
		Map map = new HashMap();
		map.put("product_id", product_id);
		map.put("service_id", service_id);
		map.put("product_name", product_name);
		map.put("service_name", service_name);
		map.put("begin", begin);
		map.put("size", size);
		map.put("cid", cid);
		return orderDao.searchProduct(map);
	}
	
	public void updateOrderByProduct(Integer productId){
		orderDao.updateOrderByProduct(productId);
	}
	
	public void deleteOrder(Integer id){
		orderDao.deleteOrder(id);
	}
	
	/**
	 * 查询新订单
	 * @return 订单数量 
	 */
	public int countNewOrder(int cid) {
		return orderDao.countNewOrder(cid);
	}
	

	public List<CountOrder>  countOrder(Map<String,String> map){
		return orderDao.countOrder(map);
	}
	
	public void  modify(){
		OrderSearch searchBean = new OrderSearch();
		searchBean.setBegin(0);
		searchBean.setSize(10000);
		
		List<Order> list = this.searchOrderList(searchBean);
		for(Order order : list)
		{
			
			searchBean.setOrder_serial(order.getOrder_serial());
			List<Order> slist = this.searchOrderList(searchBean);
			double price = 0d;
			boolean dtype = false;
			for(Order sorder : slist)
			{
				price = price + sorder.getProduct_amount() * sorder.getProduct_price();
				if(sorder.getDelivery_type() == 2)
				{
					dtype = true;
				}
			}
			
			if(dtype) price += 2;
				
			Order temp = new Order();
			temp.setOrder_price(price);
			
			for(Order sorder : slist)
			{
				temp.setOrder_id(sorder.getOrder_id());
				this.updateOrder(temp);
			}
			
		}
	}
	
	/**
	 * 
	 * @param list
	 *            订单列表

	 * @return 下订单结果 (result_code result_code  order_price);
	 * 
	 */
	@SuppressWarnings("unchecked")
	public synchronized Map createOrder(List<Order> list) {

		logger.info("into OrderService createOrder:" + list + ";");

		Map result = new HashMap();
		int result_code = 0;
		String result_dec = "成功";
		String err_pname = "";
		
		
		String products = "";
		String amounts = "";
		
		Float order_price = 0.0f;
		// 验证商品信息
		for (Order order : list) {
			products = products + "|" +order.getProduct_id();
			amounts = amounts + "|" + order.getProduct_amount();
			
			Map param = new HashMap();
			param.put("product_id", order.getProduct_id());
			param.put("community_id", order.getCommunity_id());

			Product p = this.productDao.getProduct(order.getProduct_id());
			err_pname = p.getName();

			// 判断商品状态
			if (p == null || p.getStatus() != 1) {
				result_code = 3;
				result_dec = "对不起，" + err_pname + "已下架";
			}

			if (result_code != 0)
				break;
		}

		// 下订单
		if (result_code == 0) {
			Order temp_order = list.get(0);
			Map paramMap = new HashMap();
			paramMap.put("p_order_serial", temp_order.getOrder_serial());
			paramMap.put("p_product_id", products.substring(1));
			paramMap.put("p_product_amount", amounts.substring(1));
			paramMap.put("p_user_id", temp_order.getUser_id());
			paramMap.put("p_pay_type", temp_order.getPay_type());
			paramMap.put("p_pay_type_ext", 11);
			paramMap.put("p_order_status", temp_order.getOrder_status());
			paramMap.put("p_cid", temp_order.getCommunity_id());
			paramMap.put("p_channel", Integer.parseInt(temp_order.getChannel()));
			paramMap.put("p_delivery_type", temp_order.getDelivery_type());
			paramMap.put("p_delivery_price", temp_order.getDelivery_price());
			paramMap.put("p_delivery_time", "");
			paramMap.put("p_delivery_addr", temp_order.getDelivery_addr());
			paramMap.put("p_delivery_phone", temp_order.getOrder_phone());
			paramMap.put("p_user_age", temp_order.getUser_age());
			paramMap.put("p_user_sex", temp_order.getUser_sex());
			paramMap.put("p_ip", temp_order.getIp());
			paramMap.put("p_salesman", temp_order.getSalesman());
			paramMap.put("p_off_price", temp_order.getPayoff_price());
			paramMap.put("p_result_code", 0);
			logger.info("p_orderCreate paramMap:" + paramMap.toString());
			result_code = this.p_orderCreate(paramMap);
			logger.info("p_orderCreate result_code:" + result_code);
			if(result_code!=0){
				result_dec = "下单失败";
			}
			if(result_code==203){
				result_dec = "商品数量不足";
			}
		}

		result.put("result_code", result_code);
		result.put("result_dec", result_dec);
		result.put("order_price", order_price);

		logger.info("end OrderService createOrder:" + result + ";");
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public int p_orderCreate(Map paramMap)
	{
		
		Map rMap = orderDao.p_orderCreate(paramMap);  
	 
		int result = -1;
		if(rMap != null && rMap.get("p_result_code")!=null)
		{
			result = (Integer)rMap.get("p_result_code");
		}
		return result;  
	}
	
	@SuppressWarnings("unchecked")
	public List<Map> select_Stock_change_log(String param){
		return (List<Map> )orderDao.select_Stock_change_log(param);
	}
	
	 @SuppressWarnings("unchecked")
		public void update_product_real_stock(Map paramBean){
	    	orderDao.update_product_real_stock(paramBean);
		}
	
	@SuppressWarnings("unchecked")
	public void update_product_batch_stock(Map paramBean){
    	orderDao.update_product_batch_stock(paramBean);
	}
	
	 @SuppressWarnings("unchecked")
		public void insert_stock_change_log(Map paramBean){
	    	orderDao.insert_stock_change_log(paramBean);
		}
	 
	 public void updateOrderBySerial(Order order) {
			orderDao.updateOrderBySerial(order);
		}
	
	
	@SuppressWarnings("unchecked")
	public void channel_order(String order_serial,String ip){
    	int pid = 0;
		List<Map> loglist = this.select_Stock_change_log(order_serial);
		for(Map logmap : loglist)
		{
			pid = (Integer)logmap.get("product_id");
			int change_count = (Integer)logmap.get("order_current_sum");
			Map paramBean = new HashMap();
			paramBean.put("batch_serial", logmap.get("batch_serial"));
			paramBean.put("product_id", pid);
			paramBean.put("order_current_sum", change_count);
			paramBean.put("real_stock_sum", change_count);
			this.update_product_batch_stock(paramBean);
			this.update_product_real_stock(paramBean);
			logmap.put("stock_sum", 0);
			logmap.put("order_current_sum", change_count);
			logmap.put("log_type", 7);
			logmap.put("IP", ip);
			this.insert_stock_change_log(logmap);
		}
	}
	
	/**
	 * 插入录入信息
	 * @param list
	 * @return
	 */
	public int addOrder(AddInfo addInfo){
		logger.info("into OrderService addOrder:" + addInfo);
		return orderDao.addOrder(addInfo);
	}
	
	/**
	 * 查询queryBatch 集合
	 * @param service_name
	 * @return
	 */
	
	public List<QueryBatch> queryBatchList(QueryBatch batch){
		return orderDao.queryBatchList(batch);
	}
	
	/**
	 * 查询queryBatch 数量
	 * @param batch
	 * @return
	 */
	
	public int queryBatchCount (QueryBatch batch){
		 return orderDao.queryBatchCount(batch);
	}
	/**
	 * 中奖查询
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WinningInfo> getWinning(Map map){
		return (List<WinningInfo>)orderDao.getWinning(map);
	}
	
	/**
	 * 中间记录数量 （进行分页）
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getWinningCount(Map map){
		return (Integer)orderDao.getWinningCount(map);
	}
	
	/**
	 * 更新领奖状态
	 */
	
	@SuppressWarnings("unchecked")
	public void awardUpdate(Map orderID){
		orderDao.awardUpdate(orderID);
	}

	public double getOrderAmount(String order_serial) {
		return orderDao.getOrderAmount(order_serial);
	}

	public void updateOrderStatusById(int order_id, int order_status) {
		orderDao.updateOrderStatusById(order_id, order_status);
	}

	public void updateOrderBaseStatus(String order_serial, int order_status) {
		orderDao.updateOrderBaseStatus(order_serial, order_status);
	}

	public List<Map> searchOrderBase(Map param) {
		return orderDao.searchOrderBase(param);
	}

	public int searchOrderBaseCount(Map param) {
		return orderDao.searchOrderBaseCount(param);
	}

	public List<Order> findOrderProduct(String order_serial) {
		return orderDao.findOrderProduct(order_serial);
	}

	public List<Map> findOrderProductTemp(String order_serial) {
		return orderDao.findOrderProductTemp(order_serial);
	}

	public Map getOrderBaseById(String order_serial) {
		return orderDao.getOrderBaseById(order_serial);
	}

	public List<String> findOrderSerial(Map param) {
		return orderDao.findOrderSerial(param);
	}

	public void updateOrderBaseStatus(int order_status, String order_serial, String[] order_serial_array) {
		orderDao.updateOrderBaseStatus(order_status, order_serial, order_serial_array);
	}

	public List<Map> findUnPayOrder(int time) {
		return orderDao.findUnPayOrder(time);
	}

	/**
	 * 订单提货
	 * */
	public void pickUpOrder(String order_serial) {
		orderDao.pickUpOrder(order_serial);
		orderDao.pickUpOrderProduct(order_serial);
		orderDao.updateUserCardIdBySerial(order_serial, 6);
        //配送中的订单改成配送完成
        orderDao.finishDelivery(order_serial);
        //根据订单金额新增抽奖次数
        Map order = orderDao.getOrderBySerial3(order_serial);
        if(order != null) {
            Integer user_id = CommonUtil.safeToInteger(order.get("user_id"), null);
            Integer community_id = CommonUtil.safeToInteger(order.get("community_id"), null);
            Double order_price = CommonUtil.safeToDouble(order.get("order_price"), null);
            if(user_id != null && community_id != null && order_price != null) {
                CardNumConfig cardNumConfig = cardDao.getCardNumByPrice(community_id, order_price);
                if(cardNumConfig != null) {
                    PrizeUser prizeUser = cardDao.getPrizeUserByUserId(user_id);
                    if(prizeUser == null) {
                        prizeUser = new PrizeUser();
                        prizeUser.setUser_id(user_id);
                        prizeUser.setRemainder(0);
                        prizeUser.setTotal(0);
                        prizeUser.setUpdatetime(new Date());
                        cardDao.insertPrizeUser(prizeUser);
                    }
                    cardDao.addUserPrizeCount(user_id, cardNumConfig.getCard_num());

                    //新增抽奖次数推送信息
                    MsgInfo msginfo = new MsgInfo();
                    msginfo.setMsg_info_phone(user_id + "");
                    msginfo.setMsg_template_code("0000000008");
                    msginfo.setMsg_info_detail("");
                    msgInfoDao.addMsgInfo(msginfo);
                }
            }
        }

        //新增红包
        CouponInfo couponInfo = couponService.getRandomCouponInfo(null);    //不分门店，随机选取一个红包
        if(couponInfo != null) {
            CouponCode couponCode = new CouponCode();
            couponCode.setRandomcode(order_serial);
            couponCode.setCoupon_id(couponInfo.getCoupon_id());
            couponCode.setExpiretime(DateUtils.addDays(new Date(), 1));
            couponCode.setStatus(0);
            couponCode.setAmount(30);
            try {
                couponService.insertCouponCode(couponCode);
            } catch (Exception e) {
                logger.error("error", e);
            }
        }

		//送货上门的订单需要推送信息
		if(CommonUtil.safeToInteger(order.get("delivery_type"), -1) == 2) {
			MsgInfo msginfo = new MsgInfo();
			msginfo.setMsg_info_phone(order.get("user_id") + "");
			msginfo.setMsg_template_code("0000000013");
			msginfo.setMsg_info_detail("");
			msgInfoDao.addMsgInfo(msginfo);
		}

	}

	/**
	 * 订单到货
	 * */
	public void updatePreSaleOrder(String order_serial) {
		orderDao.updatePreSaleOrder(order_serial);
		orderDao.updatePreSaleOrderProduct(order_serial);
	}

	public Map getOrderBySerial3(String order_serial) {
		return orderDao.getOrderBySerial3(order_serial);
	}

	public Map getOrderProductMapById(int order_id) {
		return orderDao.getOrderProductMapById(order_id);
	}
	
	/**
	 * 
	 * 下订单： 3.0新方法
	 * 
	 * @param list
	 *            订单列表
	 * 
	 * @return 下订单结果 (result_code result_code order_price);
	 * 
	 */
	@SuppressWarnings("unchecked")
	public synchronized Map createOrder3(List<Order> list) {

		logger.info("into OrderService createOrde3r:" + list + ";");

		Map result = new HashMap();
		int result_code = 0;
		String result_dec = "成功";

		String products = "";
		String amounts = "";
		String prices = "";
		String product_name = "";

		double order_price = 0.0d;
		double pay_off_product = 0.0d;
		String pay_off_all = "";

		Order temp_order = list.get(0);


		// 验证商品信息
		for (Order order : list) {

			products = products + "|" + order.getProduct_id();

			Product p = this.productDao.getProduct(order.getProduct_id());
			product_name = p.getName();
			
			// 判断商品状态
			if (p == null || p.getStatus() != 1) {
				result_code = 3;
				result_dec = "对不起，" + p.getName() + "已下架";
			} else if (ProductTags.checkTag(p.getTags(), ProductTags.PRESALE)) {
				result_code = 5;
				result_dec = "对不起，" + p.getName() + "为预售商品";
			}

//			// 判断商品营销规则
//			if (result_code == 0) {
//				Map param = new HashMap();
//				param.put("product_id", order.getProduct_id());
//				param.put("community_id", temp_order.getCommunity_id());
//
//				ProductRule rule = this.productDao.getProductRule(param);
//				logger.info("rule:" + rule);
//
//				if (rule != null) {
//
//					if (rule.getRule_id() != null) {
//						if (rule.getRule_type() != null && rule.getRule_type() == 1) {
//							Integer count = this.countOrderByUser(order);
//							count = count == null ? 0 : count;
//							logger.info("count:" + count);
//							if (count >= rule.getUser_limit() || order.getProduct_amount() > rule.getUser_limit()) {
//								result_code = 2;
//								result_dec = "对不起，" + p.getName() + "此限量商品您已经达到购买上限";
//							}
//						}
//					}
//				}
//			}

			if (result_code != 0)
				break;

			// 如果为称重商品
			if (ProductTags.checkTag(p.getTags(), ProductTags.WEIGHT)) {
				amounts = amounts + "|" + order.getProduct_amount();
			} else {
				// 舍弃小数点
				amounts = amounts + "|" + (int) (order.getProduct_amount() + 0);
			}

			prices = prices + "|" + p.getPrice();

			double product_price = CommonUtil.doublemul(p.getPrice(), order.getProduct_amount());
			order_price = order_price + product_price;

			// 如果为满减商品 则添加满减价格
			double pay_off = CommonUtil.get_payoff(p, order.getProduct_amount());

			if (pay_off != 0) {
				prices = prices + "-" + pay_off;
				order_price = order_price - pay_off;
				pay_off_product = pay_off_product + pay_off;
			}

		}
		
		pay_off_all = "|" + pay_off_product + "|0|0";
		
		// 下订单
		if (result_code == 0) {

			Map paramMap = new HashMap();
			paramMap.put("p_order_serial", temp_order.getOrder_serial());
			paramMap.put("p_product_id", products.substring(1));
			paramMap.put("p_product_amount", amounts.substring(1));
			paramMap.put("p_product_price", prices.substring(1));
			paramMap.put("p_order_price", order_price);
			paramMap.put("p_user_id", temp_order.getUser_id());
			paramMap.put("p_pay_type", temp_order.getPay_type());
			paramMap.put("p_pay_type_ext", temp_order.getPay_type_ext());
			paramMap.put("p_order_status", temp_order.getOrder_status());
			paramMap.put("p_order_type", 1);
			paramMap.put("p_cid", temp_order.getCommunity_id());
			paramMap.put("p_sub_cid", temp_order.getCommunity_id());
            paramMap.put("p_coupon_id", null);
			paramMap.put("p_channel", Integer.parseInt(temp_order.getChannel()));
			paramMap.put("p_delivery_type", temp_order.getDelivery_type());
			paramMap.put("p_delivery_price", temp_order.getDelivery_price());
			paramMap.put("p_delivery_time", temp_order.getDelivery_time());
			paramMap.put("p_delivery_addr", temp_order.getDelivery_addr());
			paramMap.put("p_delivery_phone", temp_order.getOrder_phone());
			paramMap.put("p_user_age", temp_order.getUser_age());
			paramMap.put("p_user_sex", temp_order.getUser_sex());
			paramMap.put("p_ip", temp_order.getIp());
			paramMap.put("p_salesman", temp_order.getSalesman());
			paramMap.put("p_off_price", temp_order.getPayoff_price());
			paramMap.put("p_result_code", 0);
			paramMap.put("p_result_msg", "");
			
			paramMap.put("p_delivery_user", "");
			paramMap.put("p_delivery_dec", "");
			paramMap.put("p_pay_off_all", pay_off_all.substring(1));
			paramMap.put("p_sub_cid", temp_order.getCommunity_id());
			
			logger.info("p_orderCreate paramMap:" + paramMap.toString());
			Map result_map = this.p_torderCreate(paramMap);
			logger.info("p_orderCreate result_code:" + result_map);
			if (result_map != null && result_map.get("p_result_code") != null) {
				result_code = (Integer) result_map.get("p_result_code");
			} else
				result_code = -1;

			if (result_code != 0) {
				result_dec = "下单失败";
			}
			if (result_code == 203) {
				result_code = 1; // 为了兼容原来错误码
				result_dec = "对不起，" + result_map.get("p_result_msg") + "商品库存不足";
			}
		}		

		result.put("result_code", result_code);
		result.put("result_dec", result_dec);
		result.put("order_price", order_price);

		logger.info("end OrderService createOrder3:" + result + ";");
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Map p_torderCreate(Map paramMap) {

		Map rMap = orderDao.p_torderCreate(paramMap);

		return rMap;
	}
	
	public Map<String,Object> checkProductOrder(Integer product_id,Double product_amount,Double order_price){

		Map<String, Object> result = new HashMap<String, Object>();
		int result_code = 0;
		String result_dec = "成功";

		String amounts = "";
		String prices = "";
		double product_price=0.0d;

		Product p = this.productDao.getProduct(product_id);

		// 判断商品状态
		if (p == null || p.getStatus() != 1) {
			result_code = 3;
			result_dec = "对不起，所购买的商品中有已下架商品";
		} else if (ProductTags.checkTag(p.getTags(), ProductTags.PRESALE)) {
			result_code = 5;
			result_dec = "对不起，所购买的商品中有预售商品";
		}

		if (result_code == 0) {

			// 如果为称重商品
//			if (ProductTags.checkTag(p.getTags(), ProductTags.WEIGHT)) {
//				amounts = amounts + "|" + product_amount;
//			} else {
//				// 舍弃小数点
//				amounts = amounts + "|" + (int) (product_amount + 0);
//			}

//			prices = prices + "|" + p.getPrice();

			product_price = CommonUtil.doublemul(p.getPrice(),
					product_amount);
			order_price = order_price + product_price;

			// 如果为满减商品 则添加满减价格
			double pay_off = CommonUtil.get_payoff(p, product_amount);

			if (pay_off != 0) {
//				prices = prices + "-" + pay_off;
				order_price = order_price - pay_off;
				product_price-=pay_off;
			}
		}
		result.put("result_code", result_code);
		result.put("result_dec", result_dec);
		result.put("order_price", order_price);
		result.put("product_price", product_price);

		return result;
	}

	public int selOrderProductUserid(String order_serial) {
		return orderDao.selOrderProductUserid(order_serial);
	}
	
	public Integer getNewOrderCount(Map<String, Object> param){
		return orderDao.getNewOrderCount(param);
	}

    public List<Map> searchProductBatch(Map param) {
        return orderDao.searchProductBatch(param);
    }

    public int searchProductBatchCount(Map param) {
        return orderDao.searchProductBatchCount(param);
    }

	/**
	 * 获取该门店下的所有配送员
	 * */
	public List<Map> findDeliveryer(Integer community_id) {
		//获取门店下的所有子门店
		//List<Community> communityList = CacheUtil.findChildrenCommunity(community_id);
		List<Integer> community_list = new ArrayList<Integer>();
		if(community_id != null) {
			community_list.add(community_id);
		}
		Map map = new HashMap();
		map.put("community_list", community_list);
		map.put("role_id", SettingUtil.getSettingValue("COMMON", "DELIVERY_ROLE"));
		return orderDao.findDeliveryer(map);
	}

	public int getDeliveryCount(int distri_worker_id, int except_distri_staus) {
		Map map = new HashMap();
		map.put("distri_worker_id", distri_worker_id);
		map.put("distri_staus", except_distri_staus);
		return orderDao.getDeliveryCount(map);
	}

	public DeliveryAddress getDeliveryAddress(int user_id) {
		return orderDao.getDeliveryAddress(user_id);
	}

	public void updateDeliveryStatus(String order_serial, int delivery_status) {
		orderDao.updateDeliveryStatus(order_serial, delivery_status);
	}
}
