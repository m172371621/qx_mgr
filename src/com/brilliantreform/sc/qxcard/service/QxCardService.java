package com.brilliantreform.sc.qxcard.service;

import com.brilliantreform.sc.alipay.config.AlipayConfig;
import com.brilliantreform.sc.alipay.util.AlipayUtils;
import com.brilliantreform.sc.qxcard.dao.QxCardDao;
import com.brilliantreform.sc.qxcard.enumerate.QxCardCzChannelEnum;
import com.brilliantreform.sc.qxcard.enumerate.QxCardCzPayStatusEnum;
import com.brilliantreform.sc.qxcard.enumerate.QxCardCzStatusEnum;
import com.brilliantreform.sc.qxcard.po.*;
import com.brilliantreform.sc.user.dao.UserDao;
import com.brilliantreform.sc.user.po.UserInfo;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.SettingUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service("qxCardService")
public class QxCardService {

	private static Logger logger = Logger.getLogger(QxCardService.class);

	@Autowired
	private QxCardDao qxCardDao;
	@Autowired
	private UserDao userDao;
	
	public QxCard getQxCard(QxCard qxCard)
	{
		return qxCardDao.getQxCard(qxCard);
	}
	
	public void updateQxCard(QxCard qxCard)
	{
		qxCardDao.updateQxCard(qxCard);
	}
	
	public void insertQxCardLog(QxCardLog qxCardLog)
	{
		qxCardDao.insertQxCardLog(qxCardLog);
	}
		
	public void updateUserQxCard(UserQxCard userQxCard)
	{
		qxCardDao.updateUserQxCard(userQxCard);
	}
	
	public Integer insertQxcardReglog(QxcardReglog qxcardReglog)
	{
		return (Integer)qxCardDao.insertQxcardReglog(qxcardReglog);
	}
	
	public void updateQxcardReglog(QxcardReglog qxcardReglog)
	{
		qxCardDao.updateQxcardReglog(qxcardReglog);
	}
	
	public QxcardReglog getQxcardReglog(Integer id)
	{
		return (QxcardReglog)qxCardDao.getQxcardReglog(id);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<QxcardReglog> getQxcardReglogList(QxcardReglog qxcardReglog,Integer begin,Integer size,String time_from,String time_to)
	{
		Map paramMap = new HashMap();
		paramMap.put("qxcard_no", qxcardReglog.getQxcard_no());
		paramMap.put("seller_name", qxcardReglog.getSeller_name());
		paramMap.put("op_type", qxcardReglog.getOp_type());		
		paramMap.put("cid", qxcardReglog.getCid());		
		paramMap.put("time_from", time_from);
		paramMap.put("time_to", time_to);
		paramMap.put("begin", begin);
		paramMap.put("size", size);
		
		return (List<QxcardReglog>)qxCardDao.getQxcardReglogList(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public Integer countQxcardReglog(QxcardReglog qxcardReglog,String time_from,String time_to)
	{
		Map paramMap = new HashMap();
		paramMap.put("qxcard_no", qxcardReglog.getQxcard_no());
		paramMap.put("seller_name", qxcardReglog.getSeller_name());
		paramMap.put("op_type", qxcardReglog.getOp_type());	
		paramMap.put("cid", qxcardReglog.getCid());		
		paramMap.put("time_from", time_from);
		paramMap.put("time_to", time_to);

		return (Integer)qxCardDao.countQxcardReglog(paramMap);  
	 
	}

    public List<Map> searchRegLog(Map param) {
        return qxCardDao.searchRegLog(param);
    }

    public int searchRegLogCount(Map param) {
        return qxCardDao.searchRegLogCount(param);
    }

    public List<Map> searchUserQxcard(Map param) {
        return qxCardDao.searchUserQxcard(param);
    }

    public int searchUserQxcardCount(Map param) {
        return qxCardDao.searchUserQxcardCount(param);
    }
	
	public List<UserQxCard> getUserQxcardList(Map<String,Object> paramMap){
		return qxCardDao.getUserQxcardList(paramMap);
	}
	
	public Integer getUserQxcardListCount(Map<String,Object> paramMap)
	{
		return qxCardDao.getUserQxcardListCount(paramMap);
	 
	}
	
	public List<QxCardLog> getCardOptList(Map<String,Object> paramMap){
		return qxCardDao.getCardOptList(paramMap);
	}
	
	public Integer getCardOptCount(Map<String,Object> paramMap)
	{
		return qxCardDao.getCardOptCount(paramMap);
	 
	}
	
	public List<QxCardPayLog> getQxCardByOrder(String order_serial) {
		return qxCardDao.getQxCardByOrder(order_serial);
	}
	
	public void updateQxCardPayLog(QxCardPayLog qxCardPayLog) {
		qxCardDao.updateQxCardPayLog(qxCardPayLog);
	}
	
	/**
	 * 删除用户订单卡牌
	 */
	public void deleteCard(String order_serial){
		qxCardDao.deleteCard(order_serial);
	}

	public List<Map> findQxcardRefund(String order_serial) {
		return qxCardDao.findQxcardRefund(order_serial);
	}

	public void insertQxcardPayLog(QxCardPayLog payLog) {
		qxCardDao.insertQxcardPayLog(payLog);
	}

	public UserQxCard getUserQxcardByNo(String qxcard_no) {
		return qxCardDao.getUserQxcardByNo(qxcard_no);
	}

	public void insertQxcardBase(QxCard qxCard) {
		qxCardDao.insertQxcardBase(qxCard);
	}

	public List<QxCardCzRule> findQxCardCzRule(Integer card_value) {
		return qxCardDao.findQxCardCzRule(card_value);
	}

	public List<QxCardCzRule> findRechargeCardValue(int community_id) {
		return qxCardDao.findRechargeCardValue(community_id);
	}

	/**
	 * 计算区享卡充值优惠金额(按总金额计算)
	 * */
	@Deprecated
 	public Double calcQxCardRestValue(Integer value) {
		double restValue = 0d;
		List<QxCardCzRule> ruleList = findQxCardCzRule(value);
		Integer calc_value = value;
		for(QxCardCzRule rule : ruleList) {

			Integer card_value = rule.getCard_value();
			Double rest_value = rule.getRest_value();
			int num = (int)Math.floor(calc_value / card_value);
			if(num > 0) {
				restValue += num * rest_value;
				calc_value = calc_value - num * card_value;
			}

		}
		return restValue;
	}

	/**
	 * 计算区享卡充值优惠金额(按卡面值计算)
	 * */
	public Double calcQxCardRestValue(int community_id, int card_value, int amount) {
		double restValue = 0d;
        QxCardCzRule rule = qxCardDao.getQxCardCzRule(community_id, card_value);
        if(rule != null) {
            restValue = amount * rule.getRest_value();
        }
		return restValue;
	}

	/**
	 * 创建区享卡(用于区享卡充值)
	 * */
	public QxCard enabledCzQxCard(int cardValue) {
		//创建新卡并激活
		String cardNo = CommonUtil.buildQxcardNo();	//卡号
		String cdkey = RandomStringUtils.randomNumeric(16);	//cdkey
		QxCard qxCard = new QxCard();
		qxCard.setQxcard_no(cardNo);
		qxCard.setQxcard_cdkey(cdkey);
		qxCard.setStatus(3);	//直接设置成激活状态
		qxCard.setValue(cardValue);
		qxCard.setDisabled(1);
		Date now = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
		qxCard.setCreatetime(new Timestamp(now.getTime()));
		qxCard.setExpire_time(new Timestamp(DateUtils.addYears(now, 2).getTime()));	//过期时间为2年后
		qxCardDao.insertQxcardBase(qxCard);
		return qxCard;
	}

	/**
	 * 记录区享卡充值记录(此时未开卡)
	 * @param user_id 充值用户id
	 * @param phone 充值手机号
	 * @param card_value 卡面值
	 * @param amount 数量
	 * @param pay_price 应付金额
	 * @param channel 充值渠道
	 * @param pay_type 支付方式
	 * */
	public QxCardCzLog recordQxCardCzLog(int user_id, int workerid, int community_id, int card_value, int amount, double pay_price, int channel, int pay_type) {
		UserInfo userInfo = userDao.getUserInfo(user_id);
		if(userInfo == null) {
			return null;
		}
		//保存充值记录 等开卡之后设置卡号
		QxCardCzLog czLog = new QxCardCzLog();
		czLog.setUser_id(user_id);
		czLog.setPhone(userInfo.getPhone());
		czLog.setCard_value(card_value);
		czLog.setAmount(amount);
		czLog.setCz_price(card_value * amount);
		czLog.setPay_price(pay_price);
		czLog.setChannel(channel);
		czLog.setPay_type(pay_type);
        czLog.setCz_serial(CommonUtil.createQxcardCzSerial(community_id, user_id, card_value, amount));
		czLog.setCommunity_id(community_id);
		czLog.setCreate_time(new Date());
		if(channel == QxCardCzChannelEnum.POS.getValue()) {
			czLog.setPay_status(QxCardCzPayStatusEnum.YES.getValue());
			czLog.setStatus(QxCardCzStatusEnum.FINISH.getValue());
			czLog.setPay_time(new Date());
			czLog.setCz_time(new Date());
		} else {
			czLog.setPay_status(QxCardCzPayStatusEnum.NO.getValue());
			czLog.setStatus(QxCardCzStatusEnum.ING.getValue());
		}
		czLog.setWorkerid(workerid);
		czLog.setRemovetag(0);
		qxCardDao.insertQxCardCzLog(czLog);
		return czLog;
	}

	/**
	 * 区享卡充值
	 * */
	public QxCard rechargeQxcard(QxCardCzLog czLog) {
		try {
			//开卡并激活
			String cardNo = CommonUtil.buildQxcardNo();    //卡号
			String cdkey = RandomStringUtils.randomNumeric(16);    //cdkey
			QxCard qxCard = new QxCard();
			qxCard.setQxcard_no(cardNo);
			qxCard.setQxcard_cdkey(cdkey);
			qxCard.setStatus(3);    //直接设置成激活状态
			qxCard.setValue(czLog.getCz_price());
			qxCard.setDisabled(1);
			Date now = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
			qxCard.setCreatetime(new Timestamp(now.getTime()));
			qxCard.setExpire_time(new Timestamp(DateUtils.addYears(now, 2).getTime()));    //过期时间为2年后
			qxCardDao.insertQxcardBase(qxCard);

			//记录激活日志
			QxCardLog qxCardLog = new QxCardLog();
			qxCardLog.setQxcard_no(cardNo);
			qxCardLog.setOp_type(1002);
			qxCardLog.setOp_dec("区享卡状态：" + qxCard.getStatus());
			qxCardLog.setOp_result(0);
			qxCardLog.setOp_result_dec("激活成功！");
			qxCardLog.setUser_type(3);	//系统自动
			qxCardLog.setUser_id(czLog.getUser_id());
            qxCardLog.setOp_price(Double.parseDouble(czLog.getCz_price() + ""));
			qxCardDao.insertQxCardLog(qxCardLog);

			//设置卡号保存
			qxCardDao.updateQxcardCzCardNo(czLog.getObjid(), cardNo);

			//将卡绑定与用户绑定
			UserQxCard userQxCard = new UserQxCard();
			userQxCard.setUser_id(czLog.getUser_id());
			userQxCard.setQxcard_no(cardNo);
			userQxCard.setQxcard_balance(Double.parseDouble(czLog.getCz_price() + ""));
			userQxCard.setQxcard_value(czLog.getCz_price());
			userQxCard.setQxcard_status(3);        //正常
			userQxCard.setDisabled(1);
			userQxCard.setExpire_time(qxCard.getExpire_time());
			userQxCard.setUpdatetime(new Timestamp(System.currentTimeMillis()));
			userQxCard.setCreatetime(new Timestamp(System.currentTimeMillis()));
			qxCardDao.insertUserQxcard(userQxCard);
			return qxCard;
		} catch (Exception e) {
			logger.error("qxcard recharge error", e);
		}
		return null;
	}

	public int getCzAmountByCardValue(int user_id, int card_value) {
		return qxCardDao.getCzAmountByCardValue(user_id, card_value);
	}

	public int getSumCzPriceByCid(int community_id) {
		return qxCardDao.getSumCzPriceByCid(community_id);
	}

    public List<Map> searchQxcardCzLog(Map param) {
        return qxCardDao.searchQxcardCzLog(param);
    }

    public int searchQxcardCzLogCount(Map param) {
        return qxCardDao.searchQxcardCzLogCount(param);
    }

    public QxCardCzLog getCzLogByCzSerial(String cz_serial) {
        return qxCardDao.getCzLogByCzSerial(cz_serial);
    }

    public QxCardCzLog saveQxCardCzLog(QxCardCzLog czLog) {
        return qxCardDao.saveQxCardCzLog(czLog);
    }

    public void insertQxcardCzPayLog(QxCardCzPayLog czPayLog) {
        qxCardDao.insertQxcardCzPayLog(czPayLog);
    }

    public QxCardCzLog getQxcardCzLogById(int objid) {
        return qxCardDao.getQxcardCzLogById(objid);
    }

    /**
     * 构建区享卡充值支付宝付款链接
     * */
    public String buildAlipayUrl(QxCardCzLog czLog) {
        //开始构建支付宝即时到账接口所需参数
        String service = "create_direct_pay_by_user";   //接口名称
        String payment_type = "1";  //支付类型
        String notify_url = SettingUtil.getSettingValue("ALIPAY", "QXCARD_CZ_NOTIFY_URL"); //服务器异步通知页面路径
        String return_url = ""; //同步通知页面路径
        String out_trade_no = czLog.getCz_serial();   //商户订单号
        String subject = "区享卡充值订单";    //订单名称
        String total_fee = czLog.getPay_price() + "";  //付款金额
        String body = "";   //订单描述
        String show_url = "";   //商品展示地址
        String anti_phishing_key = "";  //防钓鱼时间戳
        String exter_invoke_ip = "";    //客户端的IP地址

        //把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", service);
        sParaTemp.put("partner", AlipayConfig.DEFAULT_PARTNER);
        sParaTemp.put("seller_email", AlipayConfig.DEFAULT_SELLER);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("payment_type", payment_type);
        sParaTemp.put("notify_url", notify_url);
        sParaTemp.put("return_url", return_url);
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", subject);
        sParaTemp.put("total_fee", total_fee);
        sParaTemp.put("body", body);
        sParaTemp.put("show_url", show_url);
        sParaTemp.put("anti_phishing_key", anti_phishing_key);
        sParaTemp.put("exter_invoke_ip", exter_invoke_ip);

        return AlipayUtils.buildRequest(sParaTemp, 1);
    }

    public List<Map> searchOptLog(Map param) {
        return qxCardDao.searchOptLog(param);
    }

    public int searchOptLogCount(Map param) {
        return qxCardDao.searchOptLogCount(param);
    }
}
