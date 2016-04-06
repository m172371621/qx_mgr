package com.brilliantreform.sc.activity.dao;

import com.brilliantreform.sc.activity.po.*;
import com.brilliantreform.sc.utils.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import javax.print.attribute.standard.MediaSize;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CouponDao {

    private static Logger LOGGER = Logger.getLogger(CouponDao.class);

    private static final String NAMESPACE = "coupon.";

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    public CouponInfo getRandomCouponInfo(Integer cid) {
        return (CouponInfo) sqlMapClient.queryForObject(NAMESPACE + "getRandomCouponInfo", cid);
    }

    public void insertCouponCode(CouponCode couponCode) {
        sqlMapClient.insert(NAMESPACE + "insertCouponCode", couponCode);
    }

    public void updateCouponCode(CouponCode couponCode) {
        sqlMapClient.update(NAMESPACE + "updateCouponCode", couponCode);
    }

    public List<Map> searchCouponInfo(Map param) {
        return sqlMapClient.queryForList(NAMESPACE + "searchCouponInfo", param);
    }

    public int searchCouponInfoCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject(NAMESPACE + "searchCouponInfoCount", param), 0);
    }

    public void insertCouponInfo(CouponInfo couponInfo) {
        sqlMapClient.insert(NAMESPACE + "insertCouponInfo", couponInfo);
    }

    public void updateCouponInfo(CouponInfo couponInfo) {
        sqlMapClient.update(NAMESPACE + "updateCouponInfo", couponInfo);
    }

    public void saveCouponInfo(CouponInfo couponInfo) {
        if(couponInfo != null) {
            if(couponInfo.getCoupon_id() == null) {
                couponInfo.setCreatetime(new Date());
                couponInfo.setRemovetag(0);
                insertCouponInfo(couponInfo);
            } else {
                updateCouponInfo(couponInfo);
            }
        }
    }

    public CouponInfo getCouponInfoById(int coupon_id) {
        return (CouponInfo) sqlMapClient.queryForObject(NAMESPACE + "getCouponInfoById", coupon_id);
    }

    public void removeCouponInfo(int coupon_id) {
        sqlMapClient.update(NAMESPACE + "removeCouponInfo", coupon_id);
    }

    public List<Map> searchUserCoupon(Map param) {
        return sqlMapClient.queryForList(NAMESPACE + "searchUserCoupon", param);
    }

    public int searchUserCouponCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject(NAMESPACE + "searchUserCouponCount", param), 0);
    }

    public void updateUserCouponStatus(int coupon_id, int status) {
        Map param = new HashMap();
        param.put("coupon_id", coupon_id);
        param.put("status", status);
        sqlMapClient.update(NAMESPACE + "updateUserCouponStatus", param);
    }

    public List<Map> searchCouponGroup(Map param) {
        return sqlMapClient.queryForList(NAMESPACE + "searchCouponGroup", param);
    }

    public int searchCouponGroupCount(Map param) {
        return CommonUtil.safeToInteger(sqlMapClient.queryForObject(NAMESPACE + "searchCouponGroupCount", param), 0);
    }

    public CouponGroup getCouponGroupById(String group_id) {
        return (CouponGroup) sqlMapClient.queryForObject(NAMESPACE + "getCouponGroupById", group_id);
    }

    public List<Map> findCouponUnion(String group_id) {
        return sqlMapClient.queryForList(NAMESPACE + "findCouponUnion", group_id);
    }

    public void insertCouponGroup(CouponGroup couponGroup) {
        sqlMapClient.insert(NAMESPACE + "insertCouponGroup", couponGroup);
    }

    public void updateCouponGroup(CouponGroup couponGroup) {
        sqlMapClient.update(NAMESPACE + "updateCouponGroup", couponGroup);
    }

    public void removeCouponUnionByGroup(String group_id) {
        sqlMapClient.delete(NAMESPACE + "removeCouponUnionByGroup", group_id);
    }

    public void insertCouponUnion(CouponUnion couponUnion) {
        sqlMapClient.insert(NAMESPACE + "insertCouponUnion", couponUnion);
    }

    public UserCoupon getUserCouponById(int coupon_id) {
        return (UserCoupon) sqlMapClient.queryForObject(NAMESPACE + "getUserCouponById", coupon_id);
    }
}