package com.brilliantreform.sc.activity.service;

import com.brilliantreform.sc.activity.dao.CouponDao;
import com.brilliantreform.sc.activity.dao.PrizeDao;
import com.brilliantreform.sc.activity.po.*;
import com.brilliantreform.sc.utils.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CouponService {
    private static Logger LOGGER = Logger.getLogger(CouponService.class);

    @Autowired
    private CouponDao couponDao;

    public CouponInfo getRandomCouponInfo(Integer cid) {
        return couponDao.getRandomCouponInfo(cid);
    }

    public void insertCouponCode(CouponCode couponCode) {
        couponDao.insertCouponCode(couponCode);
    }

    public void updateCouponCode(CouponCode couponCode) {
        couponDao.updateCouponCode(couponCode);
    }

    public List<Map> searchCouponInfo(Map param) {
        return couponDao.searchCouponInfo(param);
    }

    public int searchCouponInfoCount(Map param) {
        return couponDao.searchCouponInfoCount(param);
    }

    public void saveCouponInfo(CouponInfo couponInfo) {
        couponDao.saveCouponInfo(couponInfo);
    }

    public CouponInfo getCouponInfoById(int coupon_id) {
        return couponDao.getCouponInfoById(coupon_id);
    }

    public void removeCouponInfo(int coupon_id) {
        couponDao.removeCouponInfo(coupon_id);
    }

    public List<Map> searchUserCoupon(Map param) {
        return couponDao.searchUserCoupon(param);
    }

    public int searchUserCouponCount(Map param) {
        return couponDao.searchUserCouponCount(param);
    }

    public List<Map> searchCouponGroup(Map param) {
        return couponDao.searchCouponGroup(param);
    }

    public int searchCouponGroupCount(Map param) {
        return couponDao.searchCouponGroupCount(param);
    }

    public CouponGroup getCouponGroupById(String group_id) {
        return couponDao.getCouponGroupById(group_id);
    }

    public List<Map> findCouponUnion(String group_id) {
        return couponDao.findCouponUnion(group_id);
    }

    public void insertCouponGroup(CouponGroup couponGroup) {
        couponDao.insertCouponGroup(couponGroup);
    }

    public void updateCouponGroup(CouponGroup couponGroup) {
        couponDao.updateCouponGroup(couponGroup);
    }

    public void removeCouponUnionByGroup(String group_id) {
        couponDao.removeCouponUnionByGroup(group_id);
    }

    public void insertCouponUnion(CouponUnion couponUnion) {
        couponDao.insertCouponUnion(couponUnion);
    }

    public UserCoupon getUserCouponById(int coupon_id) {
        return couponDao.getUserCouponById(coupon_id);
    }
}
