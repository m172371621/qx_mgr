package com.brilliantreform.sc.system.dicti;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Dicti {

	private static Map<Integer,LinkedHashMap<Integer,String>> dicti_map ;
	private static Map<String,Integer> dicti_type ;
	
	protected static void loadDicti(Map<String,Integer> dicti_type,Map<Integer,LinkedHashMap<Integer,String>> dicti_map)
	{

		Dicti.dicti_type = dicti_type;
		Dicti.dicti_map = dicti_map;
		
	}
	
	@SuppressWarnings("unchecked")
	public static Map get(Integer type_id)
	{
		Map map = new TreeMap();
		if(dicti_map == null || dicti_map.isEmpty())
		{
			return map;
		}
		if(type_id == null)
		{
			return map;
		}
		if(dicti_map.get(type_id) == null)
		{
			return map;
		}
		
		return (Map)dicti_map.get(type_id);
	}
	
	public static Integer get(String name)
	{
		if(dicti_type == null )
		{
			return null;
		}
		if(dicti_type.get(name) == null )
		{
			return null;
		}
		return (Integer)dicti_type.get(name);
	}
	
	public static String getValue(String name,Integer value_id)
	{
		Map map = Dicti.get(Dicti.get(name));
		
		if(map == null )
		{
			return null;
		}
		return (String)map.get(value_id);
	}
	
	public static String getValue(Integer type_id,Integer value_id)
	{
		Map map = Dicti.get(type_id);
		
		if(map == null )
		{
			return null;
		}
		return (String)map.get(value_id);
	}
}
