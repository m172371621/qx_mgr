package com.brilliantreform.sc.weixin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.order.po.Order;
import com.brilliantreform.sc.product.po.ProductDouble;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.weixin.dao.WeixinQxxDao;
import com.brilliantreform.sc.weixin.po.WeixinQxx;
import com.brilliantreform.sc.weixin.util.WeixinPayUtil;

@Service
public class WeixinQxxService {

	private final Integer limit_amount=30;
	private final Integer userid=3;
	
	private static Logger logger = Logger.getLogger(WeixinQxxService.class);
	@Autowired
	private WeixinQxxDao weixinQxxDao;
	
	//查询是否存在该用户对应的信息 参数openid，返回手机、我的推荐码、他人推荐码等信息
	public WeixinQxx getPersonalInfo(Map<String,Object> map){
		return weixinQxxDao.getPersonalInfo(map);
	}
	
	//更新已经购买数量，参数openid，修改buy_count
	public void updatePersonalInfo(WeixinQxx qxx){
		weixinQxxDao.updatePersonalInfo(qxx);
	}
	//填写相关个人信息生成个人信息记录，返回我的个人推荐码，参数openid，姓名、地址等，返回个人推荐码
	public WeixinQxx insertPersonalInfo(WeixinQxx qxx){
		Map<String,Object> parammap=new HashMap<String, Object>();
		parammap.put("openid", qxx.getOpenid());
		WeixinQxx ret_qxx=getPersonalInfo(parammap);
		if(ret_qxx==null){
			weixinQxxDao.insertPersonalInfo(qxx);
		}else if(ret_qxx.getOther_recommend_code().length()<8){
			ret_qxx.setOther_recommend_code(qxx.getOther_recommend_code());
			weixinQxxDao.updatePersonalInfo(ret_qxx);
		}
		WeixinQxx ret_t_qxx=getPersonalInfo(parammap);
		return ret_t_qxx;
	}
	//查询指定小区的促销商品信息 返回商品信息
	public ProductDouble getProductInfo(Map<String,Object> map){
		return weixinQxxDao.getProductInfo(map);
	}
	/**
	 * 提交抢购信息 
	 * 判断填写的个人推荐码是否已经存在，如果存在，判断openid是否相同，如果不同，则提示该验证码已经使用过
	 * 判断当前openid的已推荐数量是否大于等于相应数量限制，如果是，则再判断是否已经有相应该商品的订单，否则生成订单，有则只增加推荐人数，
	 * 如果否，则只是生成个人信息，推荐数量为1
	 */
	public Map<String,Object> sumitOrder(Map<String,Object> paramMap){
		Map<String,Object> ret_map=new HashMap<String, Object>();
		Map<String,Object> product_param_map=new HashMap<String, Object>();
		ProductDouble product=getProductInfo(product_param_map);
		//判断填写的个人推荐码是否已经存在
		WeixinQxx qxx=getPersonalInfo(paramMap);//mark
		if(qxx!=null){
			//如果存在，判断openid是否相同
			if(paramMap.get("openid").toString().equals(qxx.getOpenid())){
				//相同则判断推荐数量
				Integer count=qxx.getRecommend_amount();
				if(count>=limit_amount){
					//达到资格，判断此用户是否已经购买过此商品
					Integer buy_count=qxx.getBuy_count();
					if(buy_count>0){
						ret_map.put("result_code", -2);
						ret_map.put("result_dec", "您已购买过此商品,限购次商品一次！");
					}else{
						//下订单 mark
						String order_serial = CommonUtil.createOrderSerial("" + 3);
						Order order=new Order();
						order.setOrder_serial(order_serial);
						order.setUser_id(userid);
						order.setProduct_id(product.getProduct_id());
						order.setProduct_name(product.getName());
						order.setProduct_amount(1D);
						order.setProduct_price(Double.parseDouble(product.getPrice()) );
						order.setOrder_price(Double.parseDouble(product.getPrice()) );
						order.setOrder_phone(qxx.getPhone());
						order.setDelivery_addr(qxx.getAddr());
						order.setDelivery_type(2);
						order.setOrder_status(21);
						order.setChannel("3");
						order.setCommunity_id(3);
						order.setPay_type(2);
						order.setPay_type_ext(23);
						weixinQxxDao.insertOrderBase(order);
						weixinQxxDao.insertOrderProduct(order);
						String pay_str=WeixinPayUtil.getWeixinpayUrl(qxx.getOpenid(), paramMap.get("ip").toString(), order_serial, "区享商城订单", "区享服务，订单号：" + order_serial
								,String.valueOf((int) Math.rint(Double.parseDouble(product.getMarket_price()) * 100)));
						logger.info("pay_str:"+pay_str);
						ret_map.put("pay_str", pay_str);
						ret_map.put("result_code", 0);
						ret_map.put("result_dec", "购买成功！");
					}
				}else{
					ret_map.put("result_code", -4);
					ret_map.put("result_dec", "推荐人数不够，无法购买！");
				}
			}else{
				//不同则
				ret_map.put("result_code", -1);
				ret_map.put("result_dec", "该验证码已经使用，不允许重复多人使用！");
			}
		}else{
			//不存在则提示异常
			ret_map.put("result_code", 500);
			ret_map.put("result_dec", "请联系技术人员处理！");
//			if(!paramMap.containsKey("phone") || paramMap.get("phone").toString().equals("")){
//				ret_map.put("result_code", -3);
//				ret_map.put("result_dec", "请填写手机号！");
//			}else if(!paramMap.containsKey("name") || paramMap.get("name").toString().equals("")){
//				ret_map.put("result_code", -3);
//				ret_map.put("result_dec", "请填写姓名！");
//			}else if(!paramMap.containsKey("addr") || paramMap.get("addr").toString().equals("")){
//				ret_map.put("result_code", -3);
//				ret_map.put("result_dec", "请填写地址！");
//			}else{
//				WeixinQxx qxx_new=new WeixinQxx();
//				qxx_new.setOpenid(paramMap.get("openid").toString());
//				qxx_new.setPhone(paramMap.get("phone").toString());
//				qxx_new.setName(paramMap.get("name").toString());
//				qxx_new.setAddr(paramMap.get("addr").toString());
//				qxx_new.setMy_recommend_code(myRecommendCode)
//			}
		}
		return ret_map;
	}
	public List<WeixinQxx> getRecommendList(Map<String, Object> paramMap){
		return weixinQxxDao.getRecommendList(paramMap);
	}   
}
