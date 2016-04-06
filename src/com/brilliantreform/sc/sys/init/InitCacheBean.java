package com.brilliantreform.sc.sys.init;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.community.service.CommunityService;
import com.brilliantreform.sc.service.service.SevService;
import com.brilliantreform.sc.sys.po.SysDict;
import com.brilliantreform.sc.sys.po.SysSetting;
import com.brilliantreform.sc.sys.service.SysService;
import com.brilliantreform.sc.sys.utils.CacheUtil;
import com.brilliantreform.sc.utils.DictUtil;
import com.brilliantreform.sc.utils.SettingUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将系统参数以及数据字典缓存到内存中
 * Created by shangwq on 2015/10/16.
 */
@Component
public class InitCacheBean implements InitializingBean {

    @Autowired
    private SysService sysService;
    @Autowired
    private CommunityService communityService;
    @Autowired
    private SevService sevService;


    public synchronized void reloadSettingCache(Cache settingCache) {
        settingCache.removeAll();
        settingCache.putAll(loadSettingCache());
    }

    public synchronized void reloadDictCache(Cache dictCache) {
        dictCache.removeAll();
        dictCache.putAll(loadDictCache());
    }

    public synchronized void reloadCommunity(Cache commonCache) {
        commonCache.remove("community");
        commonCache.put(loadCommunity());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        CacheManager cacheManager = CacheManager.getInstance();
        Cache commonCache = cacheManager.getCache(CacheUtil.commonCacheName);
        Cache dictCache = cacheManager.getCache(DictUtil.cacheName);
        Cache settingCache = cacheManager.getCache(SettingUtil.cacheName);
        //Cache productServiceCache = cacheManager.getCache(CacheUtil.productServiceCacheName);

        reloadSettingCache(settingCache);
        reloadDictCache(dictCache);
        reloadCommunity(commonCache);
    }

    private List<Element> loadDictCache() {
        List<Element> elementList = new ArrayList<Element>();
        Map<String, List<SysDict>> moduleDictMap = new HashMap<String, List<SysDict>>();
        Map<String, Map<String, List<SysDict>>> map = new HashMap<String, Map<String, List<SysDict>>>();

        List<SysDict> dictList = sysService.findAllDict();
        
        for(SysDict dict : dictList) {
            String module = dict.getModule();
            if(moduleDictMap.containsKey(module)) {
                moduleDictMap.get(module).add(dict);
            } else {
                List<SysDict> _list = new ArrayList<SysDict>();
                _list.add(dict);
                moduleDictMap.put(module, _list);
            }
        }
        
        for(String module : moduleDictMap.keySet()) {
            List<SysDict> _dictList = moduleDictMap.get(module);

            Map<String, List<SysDict>> typeDictMap = new HashMap<String, List<SysDict>>();
            for(SysDict dict : _dictList) {
                String type = dict.getType();
                if(typeDictMap.containsKey(type)) {
                    typeDictMap.get(type).add(dict);
                } else {
                    List<SysDict> _list = new ArrayList<SysDict>();
                    _list.add(dict);
                    typeDictMap.put(type, _list);
                }
            }
            map.put(module, typeDictMap);
        }

        for(String module : map.keySet()) {
            Element element = new Element(module, map.get(module));
            elementList.add(element);
        }
        return elementList;
    }

    private List<Element> loadSettingCache() {
        List<Element> elementList = new ArrayList<Element>();
        Map<String, List<SysSetting>> map = new HashMap<String, List<SysSetting>>();

        List<SysSetting> settingList = sysService.findAllSetting();

        for(SysSetting setting : settingList) {
            String module = setting.getModule();
            if(map.containsKey(module)) {
                map.get(module).add(setting);
            } else {
                List<SysSetting> _list = new ArrayList<SysSetting>();
                _list.add(setting);
                map.put(module, _list);
            }
        }

        for(String module : map.keySet()) {
            Element element = new Element(module, map.get(module));
            elementList.add(element);
        }
        return elementList;
    }

    /**
     * 载入门店
     * */
    private Element loadCommunity() {
        List<Map> list = new ArrayList<Map>();
        //顶级门店
        List<Community> fstCommunityList = communityService.findCommunityByParent(0);
        //获取下面的子级
        for(Community parent : fstCommunityList) {
            List<Community> children = communityService.findCommunityByParent(parent.getCommunity_id());
            if(CollectionUtils.isNotEmpty(children)) {
                Map map = new HashMap();
                map.put("parent", parent);
                map.put("children", children);
                list.add(map);
            }
        }

        Element element = new Element("community", list);
        return element;
    }

    private Element loadCommunityTree() {
        List<Map> communityTree = communityService.findCommunityTree();

        List<Map> list = new ArrayList<Map>();
        list.addAll(communityTree);
        Element element = new Element("communityTree", list);
        return element;
    }

    private Element loadProductServiceTree() {
        List<Map> productServiceTree = sevService.findProductServiceTree();

        List<Map> list = new ArrayList<Map>();
        list.addAll(productServiceTree);
        Element element = new Element("productServiceTree", list);
        return element;
    }
}
