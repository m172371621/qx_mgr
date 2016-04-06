package com.brilliantreform.sc.weixin.service;

import com.brilliantreform.sc.weixin.dao.WeixinTuiJianDao;
import com.brilliantreform.sc.weixin.po.WeixinTuiJian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Lm on 2015/12/14 0014.
 */
@Service
public class WeixinTuiJianService {

    @Autowired
    private WeixinTuiJianDao weixintuijiandao;

    public List<WeixinTuiJian> selWeixinqxxList(Map param) {
        return weixintuijiandao.selWeixinqxxList(param);
    }

    public int selWeixinqxxListcount(Map param) {
        return weixintuijiandao.selWeixinqxxListcount(param);
    }
    public void updateWeixinqxxTuiJian(Map param) {
        weixintuijiandao.updateWeixinqxxTuiJian(param);
    }

    public WeixinTuiJian selWeixinqxx(Map param) {
       return weixintuijiandao.selWeixinqxx(param);
    }

}
