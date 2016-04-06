package com.brilliantreform.sc.utils;

import com.brilliantreform.sc.sys.po.SysDict;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DictUtil {

    public static final String cacheName = "dictCache";

    public static List<SysDict> findDictListByModuleAndType(String module, String type) {
        if (StringUtils.isNotBlank(module) && StringUtils.isNotBlank(type)) {
            Cache cache = CacheManager.create().getCache(cacheName);
            Element element = cache.get(module);
            if (element != null) {
                Map<String, List<SysDict>> map = (Map<String, List<SysDict>>) element.getObjectValue();
                return map.get(type);
            }
        }
        return new ArrayList<SysDict>();
    }

    public static Map<String, String> findDictByModuleAndType(String module, String type) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        List<SysDict> list = findDictListByModuleAndType(module, type);
        for (SysDict dict : list) {
            String name = dict.getName();
            Integer value = dict.getValue();
            String strvalue = dict.getStrvalue();
            map.put(name, value != null ? CommonUtil.safeToString(value, null) : strvalue);
        }
        if(map != null && map.size() > 0) {
            return map;
        } else {
            return null;
        }
    }

    public static String getDictName(String module, String type, Integer value) {
        if (value != null) {
            Map<String, String> map = findDictByModuleAndType(module, type);
            if (map != null && map.size() > 0) {
                for (String name : map.keySet()) {
                    String v = map.get(name);
                    if (CommonUtil.safeToString(value, null).equals(v)) {
                        return name;
                    }
                }
            }
        }
        return null;
    }
}
