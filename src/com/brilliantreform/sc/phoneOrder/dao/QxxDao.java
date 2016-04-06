package com.brilliantreform.sc.phoneOrder.dao;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.phoneOrder.po.DistriOrderBean;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.user.mgrpo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Lm on 2016/1/18.
 */
@Repository
public class QxxDao {
    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    private static final String NAMESPACE = "qxx.";

    /* 订单 未接单 数量 */
    public Integer getNotOrderCount(Map params) {
        return (Integer) sqlMapClient.queryForObject(NAMESPACE + "getNotOrderCount", params);
    }

    /* 订单  未接单 列表 */
    public List<Map> getNotOrderList(Map params) {
        return (List<Map>) sqlMapClient.queryForList(NAMESPACE + "getNotOrderList", params);
    }

    /* 订单  完成 详情 */
    public List<Map> orderXq(Map params) {
        return (List<Map>) sqlMapClient.queryForList(NAMESPACE + "orderXq", params);

    }

    /* 订单  配送中 数量 */
    public Integer getOrderDeliveryCount(Map params) {
        return (Integer) sqlMapClient.queryForObject(NAMESPACE + "getOrderDeliveryCount", params);
    }

    /* 订单  配送中 列表*/
    public List<Map> getDeliveryOrderList(Map params) {
        return (List<Map>) sqlMapClient.queryForList(NAMESPACE + "getDeliveryOrderList", params);
    }

    /* 订单  已完成 数量 */
    public Integer getOrderFinishCount(Map params) {
        return (Integer) sqlMapClient.queryForObject(NAMESPACE + "getOrderFinishCount", params);
    }

    /* 订单  已完成 列表 */
    public List<Map> getOrderFinishList(Map params) {
        return (List<Map>) sqlMapClient.queryForList(NAMESPACE + "getOrderFinishList", params);
    }

    /* 未称重 数量 */
    public Integer getNotWeighingCount(Map params) {
        return (Integer) sqlMapClient.queryForObject(NAMESPACE + "getNotWeighingCount", params);
    }

    /* 未称重 列表 */
    public List<Map> getNotWeighList(Map params) {
        return (List<Map>) sqlMapClient.queryForList(NAMESPACE + "getNotWeighList", params);
    }

    /* 称重中 数量 */
    public Integer getWeighingCount(Map params) {
        return (Integer) sqlMapClient.queryForObject(NAMESPACE + "getWeighingCount", params);
    }

    /* 称重中 列表 */
    public List<Map> getWeighingList(Map params) {
        return (List<Map>) sqlMapClient.queryForList(NAMESPACE + "getWeighingList", params);
    }

    /* 称重完成 列表 */
    public List<Map> getFinishWeighCount(Map params) {
        return (List<Map>) sqlMapClient.queryForList(NAMESPACE + "getFinishWeighCount", params);
    }

    /* 称重完成 列表 */
    public List<Map> getFinishWeighList(Map params) {
        return (List<Map>) sqlMapClient.queryForList(NAMESPACE + "getFinishWeighList", params);
    }

    public Integer getWorkbench() {
        return (Integer) sqlMapClient.queryForObject(NAMESPACE + "getNotOrder");
    }

    public Map selOpenidMgrUser(String openid) {
        return (Map) sqlMapClient.queryForObject(NAMESPACE + "selOpenidMgrUser", openid);
    }

    public MgrUser selMgrUserByid(int user_id) {
        return (MgrUser) sqlMapClient.queryForObject(NAMESPACE + "selMgrUserByid", user_id);
    }

    /* 获取后台管理员信息 */
    public MgrUser getMgrUser(String loginname) {
        return (MgrUser) sqlMapClient.queryForObject(NAMESPACE + "getMgrUser", loginname);
    }

    public List<Community> getUserCommunity(Integer userid) {
        return (List<Community>) sqlMapClient.queryForList(
                "user.getUserCommunity", userid);
    }

    public List<Role> getUserRole(Integer userid) {
        return (List<Role>) sqlMapClient.queryForList(
                "user.getUserRole", userid);
    }

    /* 插入微信的openid */
    public int insertMgrUserOpenid(MgrUser mgrUser) {
        return (Integer) sqlMapClient.insert(NAMESPACE + "insertMgrUserOpenid", mgrUser);
    }

    /**
     * 更新 称重商品 状态
     *
     * @param params
     */
    public void weighOk(Map params) {
        sqlMapClient.update(NAMESPACE + "weighOk", params);
    }

    /**
     * 取消 称重商品 状态
     *
     * @param params
     */
    public void weighCancel(Map params) {
        sqlMapClient.update(NAMESPACE + "weighCancel", params);
    }

    public void updatehOrder(String order_serial) {
        sqlMapClient.update(NAMESPACE + "updatehOrder", order_serial);
    }

    public void updateOrderProduct(String order_serial) {
        sqlMapClient.update(NAMESPACE + "updateOrderProduct", order_serial);
    }

    public void updatehDelivery_detail(String order_serial) {
        sqlMapClient.update(NAMESPACE + "updatehDelivery_detail", order_serial);
    }

    public Map acceptOrder(Map paramMap) {
        sqlMapClient.queryForObject(NAMESPACE + "acceptOrder", paramMap);
        return paramMap;
    }

    public void finishOrder(DistriOrderBean distri) {
        sqlMapClient.update("phoneOrder.finishOrder", distri);
    }

    public void updateOrderStatus(Map<String, Object> distri) {
        sqlMapClient.update(NAMESPACE + "updateOrderStatus", distri);
    }

    public void updateOrderStatus(String order_serial) {
        sqlMapClient.update("phoneOrder.updateOrderStatus", order_serial);
    }

    public void finishOrderBase(Map map) {
        sqlMapClient.update("phoneOrder.finishOrderBase", map);
    }

    public Map selorder_base(Map map) {
        return (Map) sqlMapClient.queryForObject(NAMESPACE + "selorder_base", map);
    }

    public Integer insertDelivery_address(Map map) {
        return (Integer) sqlMapClient.insert(NAMESPACE + "insertDelivery_address", map);
    }

    public Integer getQuHuoOrderCount(Map map) {
        return (Integer) sqlMapClient.queryForObject(NAMESPACE + "getQuHuoOrderCount", map);
    }

    public List<Map> getQuHuoOrderList(Map map) {
        return (List<Map>) sqlMapClient.queryForList(NAMESPACE + "getQuHuoOrderList", map);
    }

    public void quHuoOK(String params) {
        sqlMapClient.update(NAMESPACE + "quHuoOK", params);
    }

    public List<Map> selAMap(Map map) {
        return (List<Map>) sqlMapClient.queryForList(NAMESPACE + "selAMap", map);
    }
}
