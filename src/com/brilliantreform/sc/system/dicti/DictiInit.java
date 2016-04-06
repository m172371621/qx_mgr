package com.brilliantreform.sc.system.dicti;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.brilliantreform.sc.system.dicti.po.DictiVo;
import com.brilliantreform.sc.system.dicti.service.DictiService;

public class DictiInit {

	public void InitDicti(DictiService dictiService) {
		
		Map<Integer, LinkedHashMap<Integer, String>> dicti_map = new HashMap<Integer, LinkedHashMap<Integer, String>>();

		Map<String, Integer> dicti_type = new HashMap<String, Integer>();

		List<DictiVo> tlist = dictiService.getDictiType();

		for (DictiVo t : tlist) {
			
			dicti_type.put(t.getName(), t.getId());
			
			List<DictiVo> vlist = dictiService.getDictiValue(t.getId());
			
			LinkedHashMap<Integer, String> vMap = new LinkedHashMap<Integer, String>();
			
			for (DictiVo v : vlist) {
				vMap.put(v.getValue(), v.getName());
			}
			
			dicti_map.put(t.getId(), vMap);
		}

		Dicti.loadDicti(dicti_type, dicti_map);
	}
}
