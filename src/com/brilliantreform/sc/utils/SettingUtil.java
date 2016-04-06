package com.brilliantreform.sc.utils;

import com.brilliantreform.sc.sys.po.SysSetting;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SettingUtil {
    public static final String cacheName = "settingCache";

    public static List<SysSetting> findSettingListByModule(String module) {
        if (StringUtils.isNotBlank(module)) {
            Cache cache = CacheManager.create().getCache(cacheName);
            Element element = cache.get(module);
            if (element != null) {
                return (List<SysSetting>)element.getObjectValue();
            }
        }
        return new ArrayList<SysSetting>();
    }

    public static Map<String, String> findSettingByModule(String module) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        List<SysSetting> list = findSettingListByModule(module);
        for (SysSetting setting : list) {
            String name = setting.getName();
            String value = setting.getValue();
            map.put(name, value);
        }
        if(map != null && map.size() > 0) {
            return map;
        } else {
            return null;
        }
    }

    public static String getSettingValue(String module, String name) {
        if (StringUtils.isNotBlank(name)) {
            Map<String, String> map = findSettingByModule(module);
            if (map != null && map.size() > 0) {
                for (String k : map.keySet()) {
                    if (CommonUtil.safeToString(k, null).equals(name)) {
                        return map.get(k);
                    }
                }
            }
        }
        return null;
    }

}
