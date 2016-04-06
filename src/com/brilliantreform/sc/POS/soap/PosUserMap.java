package com.brilliantreform.sc.POS.soap;

import java.util.HashMap;
import java.util.Map;

public class PosUserMap {

	private static Map<Integer,Integer> posuser_community = new HashMap<Integer,Integer>();
	
	public static void setUserCommunity(Integer user_id,Integer cid)
	{
		posuser_community.put(user_id, cid);
	}
	
	public static Integer getUserCommunity(Integer user_id)
	{
		return posuser_community.get(user_id);
	}
}
