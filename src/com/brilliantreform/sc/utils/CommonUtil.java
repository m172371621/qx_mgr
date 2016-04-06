package com.brilliantreform.sc.utils;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.product.po.Product;
import com.brilliantreform.sc.product.po.ProductTags;
import com.brilliantreform.sc.qxcard.po.QxCard;
import com.brilliantreform.sc.qxcard.service.QxCardService;
import com.brilliantreform.sc.sys.utils.CacheUtil;
import com.brilliantreform.sc.system.SpringContextHolder;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtil {
	
	private static Logger logger = Logger.getLogger(CommonUtil.class);

	public static String createOrderSerial(String product_id)
	{
		StringBuilder sb = new StringBuilder();

		sb.append(10);

		if(product_id.length()>4)
			sb.append(product_id.substring(product_id.length()-4));
		else
		{
			for(int i = 0;i< 4 - product_id.length();i++)
			{
				sb.append(0);
			}
			sb.append(product_id);
		}

		String time = "" + System.currentTimeMillis();
		sb.append(time.substring(3));
		sb.append(RandomStringUtils.randomNumeric(4));
		return sb.toString();
	}

    /**
     * 区享卡充值订单号
     * */
    public static String createQxcardCzSerial(int community_id, int user_id, int card_value, int amount) {
        StringBuilder sb = new StringBuilder(20);
        String str1 = formatDate(new Date(), "yyMMdd");
        String str2 = String.format("%0" + 3 + "d", community_id);
        String str3 = String.format("%0" + 5 + "d", user_id);
        String str4 = String.format("%0" + 4 + "d", card_value * amount);
        //String str5 = String.format("%0" + 3 + "d", amount);
        String str5 = RandomStringUtils.randomNumeric(4);
        return sb.append(str1).append(str2).append(str3).append(str4).append(str5).toString();
    }

	public static String formatTimestamp(Timestamp ts,int type)
	{
		String format = "yyyy-MM-dd HH:mm:ss";

		if(type == 2)
		{
			format = "yyyy-MM-dd";
		}

		DateFormat sf = new SimpleDateFormat(format);

		if(ts!=null)
			return sf.format(ts.getTime());
		else
			return "";
	}

	public static String formatTime(long time,int type)
	{
		String format = "yyyy-MM-dd HH:mm:ss";

		if(type == 2)
		{
			format = "yyyy-MM-dd";
		}

		DateFormat sf = new SimpleDateFormat(format);

		if(time!=0)
			return sf.format(time);
		else
			return "";
	}

	public static Timestamp getTimestamp(String time,int type)
	{
		String format = "yyyy-MM-dd HH:mm:ss";

		if(type == 2)
		{
			format = "yyyy-MM-dd";
		}

		DateFormat sf = new SimpleDateFormat(format);

		try {

			Date date = sf.parse(time);
			return new Timestamp(date.getTime());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static String isNull(String str)
	{
		if(StringUtils.isBlank(str))
			return null;
		else if("0".equals(str))
			return null;
		else
			return str;
	}

	public static String getIp(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;

	}

	public static float floatmul(Float f,Integer i)
	{
		BigDecimal b1 = new BigDecimal(f.toString());
		BigDecimal b2 = new BigDecimal(i.toString());
		b1 = b1.multiply(b2);
		String result = b1.toString();
		return Float.parseFloat(result);
	}

	public static int getPageCount(int count, int size) {
		return count % size == 0 ? count / size : count / size + 1;
	}

	/**
	 * 获取订单状态名称
	 * */
	public static String parseOrderStatus(Integer status) {
		String name = "未知";
		if(status != null) {
			if(status.intValue() == 5) {
				name = "未到货";
			} else if(status.intValue() == 21) {
				name = "待支付";
			} else if(status.intValue() == 1 || status.intValue() == 2 || status.intValue() == 22) {
				name = "待收货";
			} else if(status.intValue() == 3) {
				name = "已提货";
			} else if(status.intValue() == 4 || status.intValue() == 23) {
				name = "已取消";
			}
		}
		return name;
	}

	public static Integer safeToInteger(Object obj, Integer defaultValue) {
		Integer r = defaultValue;
		if (obj != null) {
			try {
				r = Integer.valueOf(String.valueOf(obj));
			} catch (Exception ex) {
			}
		}
		return r;
	}

	public static String safeToString(Object obj, String defaultValue) {
		String r = defaultValue;
		if (obj != null) {
			try {
				r = String.valueOf(obj);
			} catch (Exception ex) {}
		}
		return r;
	}

	public static Double safeToDouble(Object obj, Double defaultValue) {
		Double r = defaultValue;
		if (obj != null) {
			try {
				r = Double.valueOf(String.valueOf(obj));
			} catch (Exception ex) {
			}
		}
		return r;
	}

	public static Date safeToDate(Object obj, String format) {
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(safeToString(obj, null));
		} catch (Exception e) {}
		return date;
	}

	public static String formatDate(Date date, String format) {
		String str = null;
		try {
			str = new SimpleDateFormat(format).format(date);
		} catch (Exception e) {}
		return str;
	}

	/**
	 * 获取一定长度的随机字符串
	 * @param length 指定字符串长度
	 * @return 一定长度的字符串
	 */
	public static String getRandomStringByLength(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 生成退款订单号
	 * */
	public static String buildRefundNo(int order_id) {
		String ymd = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String refundNo = ymd + order_id + System.currentTimeMillis();
		if(refundNo.length() > 32) {
			refundNo = refundNo.substring(0, 32);
		}
		return refundNo;
	}

	/**
	 * 生成退款订单号
	 * */
	public static String buildRefundNo(String order_serial) {
		String ymd = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String refundNo = ymd + order_serial + System.currentTimeMillis();
		if(refundNo.length() > 32) {
			refundNo = refundNo.substring(0, 32);
		}
		return refundNo;
	}

	public static Map getParameterMap(HttpServletRequest request) {
		// 参数Map
		Map properties = request.getParameterMap();
		// 返回值Map
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if(null == valueObj){
				value = "";
			}else if(valueObj instanceof String[]){
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){
					value = values[i] + ",";
				}
				if(value.endsWith(",")) {
					value = value.substring(0, value.length() - 1);
				}
			}else{
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
	
	public static double round(String d,int scale) {
		BigDecimal b = new BigDecimal(d);
		b = b.setScale(scale, RoundingMode.HALF_UP);
		String result = b.toString();
		return Double.parseDouble(result);
	}
	
	public static double round(String d) {
		return round(d, 2);
	}
	
	public static double doublemul(Double f, Double i,int scale) {
		BigDecimal b1 = new BigDecimal(f.toString());
		BigDecimal b2 = new BigDecimal(i.toString()); 
		b1 = b1.multiply(b2);
		b1 = b1.setScale(scale, RoundingMode.DOWN);
		String result = b1.toString();
		return Double.parseDouble(result);
	}
	
	public static double doublemul(Double f, Double i) {
		return doublemul(f, i, 2);
	}
	
	public static double doublemul(String f, Double i) {
		Double df = Double.parseDouble(f);
		return doublemul(df,i,2);
	}

	public static double doublediv(Double f, Double i, int scale) {
		BigDecimal b1 = new BigDecimal(f.toString());
		BigDecimal b2 = new BigDecimal(i.toString());
		b1 = b1.divide(b2, scale, RoundingMode.DOWN);
		String result = b1.toString();
		return Double.parseDouble(result);
	}
	
	public static double doublediv(Double f, Double i) {
		
		return doublediv(f, i, 2);
	}


	// 计算满减的价格
	public static double get_payoff(Product p, double amount) {
		logger.info("into getpay_off:" + p + "&" + amount);
		double pay_off = 0d;
		// 如果为满减商品 则添加满减价格
		if (ProductTags.checkTag(p.getTags(), ProductTags.WHOLESALE)) {

			try {

				String[] strs = p.getWholesale_price().split("\\|");
				double limit_price = Double.parseDouble(strs[0]);
				double total_price = CommonUtil.doublemul(p.getPrice(), amount);
				pay_off = Double.parseDouble(strs[1]);

				pay_off = CommonUtil.doublemul(pay_off, CommonUtil.doublediv(
						total_price, limit_price, 0));

			} catch (Exception e) {
				logger.info(" get_payoff error:" + p.getWholesale_price()
						+ e.getMessage());
			}
		}

		return pay_off;
	}

	/**
	 * 生成区享卡卡号
	 * */
	public synchronized static String buildQxcardNo() {
		StringBuffer cardNo = new StringBuffer("30");
		cardNo.append(RandomStringUtils.randomNumeric(8));
		//判断生成的卡号是否已经存在
		QxCardService qxCardService = SpringContextHolder.getBean("qxCardService");
		QxCard qxCard = new QxCard();
		qxCard.setQxcard_no(cardNo.toString());
		if(qxCardService.getQxCard(qxCard) != null) {
			return buildQxcardNo();
		} else {
			return cardNo.toString();
		}
	}

	public static void outToWeb(HttpServletResponse response, String str) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 分割List
	 *
	 * @param list 待分割的list
	 * @param pageSize    每段list的大小
	 * @return List<<List<T>>
	 */
	public static <T> List<List<T>> splitList(List<T> list, int pageSize) {
		int listSize = list.size(); // list的大小
		int page = (listSize + (pageSize - 1)) / pageSize;// 页数
		List<List<T>> listArray = new ArrayList<List<T>>();// 创建list数组,用来保存分割后的list
		for (int i = 0; i < page; i++) { // 按照数组大小遍历
			List<T> subList = new ArrayList<T>(); // 数组每一位放入一个分割后的list
			for (int j = 0; j < listSize; j++) {// 遍历待分割的list
				int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize;// 当前记录的页码(第几页)
				if (pageIndex == (i + 1)) {// 当前记录的页码等于要放入的页码时
					subList.add(list.get(j)); // 放入list中的元素到分割后的list(subList)
				}
				if ((j + 1) == ((j + 1) * pageSize)) {// 当放满一页时退出当前循环
					break;
				}
			}
			listArray.add(subList);// 将分割后的list放入对应的数组的位中
		}
		return listArray;
	}

    public static void parseParamCommunityId(HttpServletRequest request, Map param) {
        Integer community_id = CommonUtil.safeToInteger(param.get("community_id"), null);
        Set<Integer> communityIdSet = new HashSet<Integer>();
        if(community_id != null) {
            communityIdSet.add(community_id);
        } else {
            List<Community> list = (List<Community>) request.getSession().getAttribute("user_community");
            for(Community community : list) {
                communityIdSet.add(community.getCommunity_id());
            }
        }

        List<Community> list = new ArrayList<Community>();
        for(Integer communityId : communityIdSet) {
            List<Community> _list = CacheUtil.findChildrenCommunity(communityId);
            if(_list != null && _list.size() > 0) {
                list.addAll(_list);
            }
        }
        for(Community c : list) {
            communityIdSet.add(c.getCommunity_id());
        }
        param.put("community_list", communityIdSet.toArray());
        param.remove("community_id");  //清空community_id
    }

    public static Set<Integer> findAllUserCommunity(List<Community> ulist) {
        Set<Integer> communityIdSet = new HashSet<Integer>();
        for(Community community : ulist) {
            communityIdSet.add(community.getCommunity_id());
        }
        List<Community> list = new ArrayList<Community>();
        for(Integer communityId : communityIdSet) {
            List<Community> _list = CacheUtil.findChildrenCommunity(communityId);
            if(_list != null && _list.size() > 0) {
                list.addAll(_list);
            }
        }
        for(Community c : list) {
            communityIdSet.add(c.getCommunity_id());
        }
        return communityIdSet;
    }

    public static String CreateNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}


    public static Community getContainsCommunity(List<Community> list, int community_id) {
        for(Community community : list) {
            int cid = community.getCommunity_id();
            if(community_id == cid) {
                return community;
            }
        }
        return null;
    }

}
