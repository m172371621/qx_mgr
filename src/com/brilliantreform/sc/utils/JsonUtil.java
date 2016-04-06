package com.brilliantreform.sc.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;

import com.brilliantreform.sc.order.controller.OrderCtrl;

public class JsonUtil {
	
	private static Logger logger = Logger.getLogger(OrderCtrl.class);
	
	public static  Map<String, Object> getJson(HttpServletRequest request)
	{
		 String line;
         String result = "";
		 BufferedReader in;
		 Map<String, Object> map = new HashMap<String, Object>();
		 try {
			in = new BufferedReader(new InputStreamReader(
					 request.getInputStream(), "utf-8"));
			
			while ((line = in.readLine()) != null) {
				    result += line;
			}
			logger.info(result);
			
			map = JsonUtil.json2Map(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		
		logger.info(map);
		return map;
	}

	/**
	 * 将接口返回结果转为map
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> json2Map(String json) {
	
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String, Object> map = new HashMap<String, Object>();

		for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			String value = jsonObject.get(key).toString();
			Object obj = value;

			if (value.startsWith("{")) {
				obj = json2Map(value);
			}else if(value.startsWith("["))
			{
				JSONArray jsonArray = JSONArray.fromObject(value);
				List<Map> list = JSONArray.toList(jsonArray, new HashMap(),
						new JsonConfig());
				obj = list;
			}

			map.put(key, obj);
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static String result2Json(int resultCode,String resultDec, Object data) {
		
		Map map = new HashMap();
		map.put("result_code", resultCode);
		map.put("result_dec", resultDec);
		if(data != null )
			map.put("data", data);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.putAll(map);
		
		return jsonObject.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static String result2Json(boolean result,Object data) {
		
		Map map = new HashMap();
		if(result)
		{
			map.put("result_code", 0);
			map.put("result_dec", "OK");
		}
		else
		{
			map.put("result_code", 500);
			map.put("result_dec", "ERROR");
		}
		if(data != null)
			map.put("data", data);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.putAll(map);
		
		return jsonObject.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static String result2Json(int result,Object data) {
		
		Map map = new HashMap();
		if(result == 0)
		{
			map.put("result_code", 0);
			map.put("result_dec", "OK");
		}
		else
		{
			map.put("result_code", result);
			map.put("result_dec", "ERROR");
		}
		if(data != null)
			map.put("data", data);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.putAll(map);
		
		return jsonObject.toString();
	}
	
    /**
     * 将javaBean转换成Map(将时间转为字符串，将null置为“”)
     *
     * @param javaBean javaBean
     * @return Map对象
     */
    public static Map<String, String> been2Map(Object javaBean)
    {
        Map<String, String> result = new HashMap<String, String>();
        Method[] methods = javaBean.getClass().getDeclaredMethods();

        for (Method method : methods)
        {
            try
            {
                if (method.getName().startsWith("get"))
                {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);

                    Object value = method.invoke(javaBean, (Object[])null);
                    
                    if(value!=null)
                    {
                    	if("java.sql.Timestamp".equals(value.getClass().getName()))
                    	{
                    		value = CommonUtil.formatTimestamp((Timestamp)value, 1);
                    	}
                    }
                    
                    result.put(field, obj2Str(value));
                }
            }
            catch (Exception e)
            {
            }
        }

        return result;
    }
	
    
    /**
     * 格式化返回的list (将时间转为字符串，将null置为“”)
     *
     * @param javaBean javaBean
     * @return Map对象
     */
    public static List<Map<String, String>> formatList(List<Object> list)
    {
       List<Map<String, String>> rlist = new ArrayList<Map<String, String>>();
       
       for(Object o : list)
       {
    	   Map<String, String> map = been2Map(o);
    	   rlist.add(map);
       }
       
       return rlist;
    }
    
    public static String obj2Str(Object obj)
    {
       if(obj == null)
    	   return "";
       else
    	   return obj.toString();
    }

	public static String result2Json(int resultCode, String resultDec,
									 Object data, int count) {

		Map map = new HashMap();
		map.put("result_code", resultCode);
		map.put("result_dec", resultDec);
		if (data != null)
			map.put("data", data);
		map.put("count", count);

		JSONObject jsonObject = new JSONObject();
		jsonObject.putAll(map);

		return jsonObject.toString();
	}

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		System.out.println(sdf.format(new Date(1510000000000l)));
	}
}
