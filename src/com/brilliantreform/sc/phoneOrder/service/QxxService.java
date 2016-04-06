package com.brilliantreform.sc.phoneOrder.service;

import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.community.service.CommunityService;
import com.brilliantreform.sc.order.enumerate.DeliveryStatusEnum;
import com.brilliantreform.sc.phoneOrder.dao.QxxDao;
import com.brilliantreform.sc.phoneOrder.po.DistriOrderBean;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.user.mgrpo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lm on 2016/1/18.
 */
@Service("qxxService")
public class QxxService {

    @Autowired
    private QxxDao qxxDao;
    @Autowired
    private CommunityService communityService;

    public Map selOpenidMgrUser(String openid) {
        return qxxDao.selOpenidMgrUser(openid);
    }

    public MgrUser selMgrUserByid(int user_id) {
        return qxxDao.selMgrUserByid(user_id);
    }

    public MgrUser getMgrUser(String loginname) {
        return qxxDao.getMgrUser(loginname);
    }

    public int insertMgrUserOpenid(MgrUser mgrUser) {
        return qxxDao.insertMgrUserOpenid(mgrUser);
    }

    public List<Role> getUserRole(Integer userid) {
        return qxxDao.getUserRole(userid);
    }

    /**
     * 获取用户的所属门店，user_id为null的则代表管理员，查询所有的门店出来
     */
    public List<Community> findUserCommunity(Integer user_id) {
        List<Community> list = new ArrayList<Community>();
        if (user_id == null) {
            list = communityService.getCommunityList();
        } else {
            list = getUserCommunity(user_id);
        }
        return list;
    }

    public List<Community> getUserCommunity(Integer userid) {
        return qxxDao.getUserCommunity(userid);
    }

    /**
     * 工作台
     *
     * @return
     */
    public Map getWorkbench(Map params) {
        Map map = new HashMap();
        //订单
        Map mapCount = new HashMap();

        params.put("user_id", params.get("user_id"));// 配送员ID

        mapCount.put("notOrder", qxxDao.getNotOrderCount(params));// 未接单

        params.put("distri_staus", DeliveryStatusEnum.ING.getValue());// 配送中  status 2
        mapCount.put("deliveryOrder", qxxDao.getOrderDeliveryCount(params));

        mapCount.put("finishOrder", qxxDao.getOrderFinishCount(params));// 已完成

        //取货
        params.put("distri_staus", DeliveryStatusEnum.WAIT.getValue());// 取货  status 0

        mapCount.put("quHuoOrder", qxxDao.getQuHuoOrderCount(params));

        //称重
        mapCount.put("notWeigh", qxxDao.getNotWeighingCount(params));// 未称重  status 1

        mapCount.put("weighing", qxxDao.getWeighingCount(params));// 称重中  status 2

        mapCount.put("weighFinish", qxxDao.getFinishWeighCount(params));//称重完成  status 3

        //服务
        map.putAll(mapCount);
        return map;
    }

    /**
     * 取消 称重商品 状态
     *
     * @param user_cartMap
     */
    public void weighOk(Map user_cartMap) {
        qxxDao.weighOk(user_cartMap);
    }

    /**
     * 取消 称重商品 状态
     *
     * @param user_cartMap
     */
    public void weighCancel(Map user_cartMap) {
        qxxDao.weighCancel(user_cartMap);
    }

    /**
     * 接单，根据返回map中的p_result_code判断是否接单成功，0表示成功
     */
    public Map acceptOrder(int distri_worker_id, int community_id, String order_serial) {
        Map param = new HashMap();
        param.put("p_distri_worker_id", distri_worker_id);
        param.put("p_distri_community_id", community_id);
        param.put("p_order_serial", order_serial);
        param.put("p_result_code", 1);
        qxxDao.acceptOrder(param);
        return param;
    }

    public void updateOrderStatus(String order_serial) {
        qxxDao.updatehOrder(order_serial);
        qxxDao.updateOrderProduct(order_serial);
        qxxDao.updatehDelivery_detail(order_serial);
    }

    public List<Map> getNotOrderList(Map params) {
        return qxxDao.getNotOrderList(params);
    }

    public List<Map> orderXq(Map params) {
        return qxxDao.orderXq(params);
    }

    public List<Map> getDeliveryOrderList(Map params) {
        return qxxDao.getDeliveryOrderList(params);
    }

    public List<Map> getOrderFinishList(Map params) {
        return qxxDao.getOrderFinishList(params);
    }

    public List<Map> getNotWeighList(Map params) {
        return qxxDao.getNotWeighList(params);
    }

    public List<Map> getWeighingList(Map params) {
        return qxxDao.getWeighingList(params);
    }

    public List<Map> getFinishWeighList(Map params) {
        return qxxDao.getFinishWeighList(params);
    }

    public List<Map> getQuHuoOrderList(Map params) {
        return qxxDao.getQuHuoOrderList(params);
    }

    public void finishOrder(DistriOrderBean distri) {
        qxxDao.finishOrder(distri);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("distri_detail_id", distri.getDistri_detail_id());
        map.put("order_status", 3);
        qxxDao.updateOrderStatus(map);
        qxxDao.finishOrderBase(map);
    }

    public Map selorder_base(Map map) {
        return qxxDao.selorder_base(map);
    }

    public Boolean insertDelivery_address(String lng, String lat, String address, int user_id) {
        Map map = new HashMap();
        String lnglatXY = lng + "," + lat;
        map.put("lnglatXY", lnglatXY);
        map.put("address", address);
        map.put("user_id", user_id);
        return qxxDao.insertDelivery_address(map) == null ? false : true;
    }

    public void quHuoOK(String order_serial) {
        qxxDao.quHuoOK(order_serial);
    }

    public List<Map> selAMap(Map map) {
        return qxxDao.selAMap(map);
    }
}
