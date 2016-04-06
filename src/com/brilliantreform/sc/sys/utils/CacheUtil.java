package com.brilliantreform.sc.sys.utils;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.utils.CommonUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheUtil {

    public static final String productServiceCacheName = "productServiceCache";

    public static final String commonCacheName = "commonCache";

    public static List<Map> findAllCommunityTree() {
        Cache cache = CacheManager.create().getCache(commonCacheName);
        Element element = cache.get("communityTree");
        return (List<Map>)element.getObjectValue();
    }

    public static List<Map> findAllProductServiceTree() {
        Cache cache = CacheManager.create().getCache(commonCacheName);
        Element element = cache.get("productServiceTree");
        return (List<Map>)element.getObjectValue();
    }

    public static List<Map> findAllCommunity() {
        Cache cache = CacheManager.create().getCache(commonCacheName);
        Element element = cache.get("community");
        return (List<Map>)element.getObjectValue();
    }

    public static List<Map> findCommunityByParam(List<Community> communityList) {
        List<Map> list = new ArrayList<Map>();
        List<Map> allCommunityList = findAllCommunity();
        for(Map map : allCommunityList) {
            Community parent = (Community) map.get("parent");
            List<Community> children = (List<Community>) map.get("children");

            if(CommonUtil.getContainsCommunity(communityList, parent.getCommunity_id()) != null) {
                list.add(map);
            } else {
                List<Community> _children = new ArrayList<Community>();
                for(Community child : children) {
                    if(CommonUtil.getContainsCommunity(communityList, child.getCommunity_id()) != null) {
                        _children.add(child);
                    }
                }
                if(_children.size() > 0) {
                    Map m = new HashMap();
                    m.put("parent", parent);
                    m.put("children", _children);
                    list.add(m);
                }
            }
        }
        return list;
    }

    /**
     * 获取某门店下面所有的子门店，包括本身
     * */
    public static List<Community> findChildrenCommunity(Integer pid) {
        List<Community> result = new ArrayList<Community>();
        List<Map> list = new ArrayList<Map>();
        if(pid == null) {
            list = findAllCommunity();
        } else {
            Community parent = new Community();
            parent.setCommunity_id(pid);
            List<Community> clist = new ArrayList<Community>();
            clist.add(parent);
            list = findCommunityByParam(clist);
        }
        if(CollectionUtils.isNotEmpty(list)) {
            for(Map map : list) {
                Community p = (Community) map.get("parent");
                List<Community> c = (List<Community>) map.get("children");

                if(p.getCommunity_id().intValue() == pid) {
                    result.add(p);
                }
                for(Community _c : c) {
                    result.add(_c);
                }
            }
        }
        return result;
    }

    /**
     * 返回门店商品类别map，key为community，value为serviceList
     * */
    public static Map getCommunityProduct(int community_id) {
        Map map = null;
        Cache cache = CacheManager.create().getCache(productServiceCacheName);
        Element element = cache.get(community_id);
        if (element != null) {
            map = (Map) element.getObjectValue();
        }
        return map;
    }

    public static List<Map> findCommunityProduct(List<Community> communityList) {
        List<Map> list = new ArrayList<Map>();
        for(Community community : communityList) {
            Map map = getCommunityProduct(community.getCommunity_id());
            if(map != null && map.size() > 0) {
                list.add(map);
            }
        }
        return list;
    }
}
