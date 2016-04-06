package com.brilliantreform.sc.phoneOrder.service;

import com.brilliantreform.sc.phoneOrder.dao.PhoneOrderDao;
import com.brilliantreform.sc.phoneOrder.po.DistriOrderBean;
import com.brilliantreform.sc.phoneOrder.po.PhoneOrder;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lm on 2015/11/30 0030.
 */

@Service("phoneorderservice")
public class PhoneOrderService {
    @Autowired
    private PhoneOrderDao phoneorderdao;

    public List<DistriOrderBean> getDistriHead(Map<String, Object> map) {
        return phoneorderdao.getDistriHead(map);
    }

    public DistriOrderBean getDistriDetail(Map<String, Object> map) {
        return phoneorderdao.getDistriDetail(map);
    }

    public List<DistriOrderBean> getDistriProduct(Map<String, Object> map) {
        return phoneorderdao.getDistriProduct(map);
    }

    public void optDetail(Map<String, Object> map) {
        phoneorderdao.optDetail(map);
    }

    public void optProduct(Map<String, Object> map) {
        phoneorderdao.optProduct(map);
    }

    public List<DistriOrderBean> getSerialByPhone(Map<String, Object> map) {
        return phoneorderdao.getSerialByPhone(map);
    }

    @Transactional
    public void updateOrderStatus(Map<String, Object> distri) {
        phoneorderdao.updateOrderStatus(distri);
        phoneorderdao.updateOrderBaseStatus(distri);

    }

    public List<PhoneOrder> getOrderList(Map<String, Object> distri) {
        return phoneorderdao.getOrderList(distri);
    }
    public List<PhoneOrder> weiXinTs() {
        return phoneorderdao.weiXinTs();
    }

    public List<Map> getNewOrder() {
        return phoneorderdao.getNewOrder();
    }


    /**
     * 接单，根据返回map中的p_result_code判断是否接单成功，0表示成功
     * */
    public Map acceptOrder(int distri_worker_id, int community_id, String order_serial) {
        Map param = new HashMap();
        param.put("p_distri_worker_id", distri_worker_id);
        param.put("p_distri_community_id", community_id);
        param.put("p_order_serial", order_serial);
        param.put("p_result_code", 1);
        phoneorderdao.acceptOrder(param);
        return param;
    }

    @Transactional
    public void finishOrder(DistriOrderBean distri) {
        phoneorderdao.finishOrder(distri);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("distri_detail_id", distri.getDistri_detail_id());
        map.put("order_status", 3);
        phoneorderdao.updateOrderStatus(map);
        phoneorderdao.finishOrderBase(map);
    }


    public List<DistriOrderBean> getDeliveryDetail(Map<String, Object> distri) {
        return phoneorderdao.getDeliveryDetail(distri);
    }

    public Integer getNewOrderCount(Map<String, Object> distri) {
        return phoneorderdao.getNewOrderCount(distri);
    }

    public void updatePreSaleOrder(String order_serial, int type) {
        phoneorderdao.updatePreSaleOrder(order_serial, type);
        phoneorderdao.updatePreSaleOrderProduct(order_serial, type);

    }

    public List<PhoneOrder> getBaseProduct(Map<String, Object> distri) {
        return phoneorderdao.getBaseProduct(distri);
    }

    public MgrUser getMgrUser(String loginname) {
        return phoneorderdao.getMgrUser(loginname);
    }

    public List<PhoneOrder> getOrderBase(Map param) {
        return phoneorderdao.getOrderBase(param);
    }

    public Map selOpenidMgrUser(String openid) {
        return phoneorderdao.selOpenidMgrUser(openid);
    }

    public int updateMgrUserOpenid(MgrUser mgrUser) {
        return phoneorderdao.updateMgrUserOpenid(mgrUser);
    }

    public int insertMgrUserOpenid(MgrUser mgrUser) {
        return phoneorderdao.insertMgrUserOpenid(mgrUser);
    }

    public MgrUser selMgrUserByid(int user_id) {
        return phoneorderdao.selMgrUserByid(user_id);
    }

    public void delMgrUserOpenid(MgrUser mgrUser) {
        phoneorderdao.delMgrUserOpenid(mgrUser);
    }

    public List<Map> getPhoneOpenid(int user_id) {
        return phoneorderdao.getPhoneOpenid(user_id);
    }

    public List<Map> getUserId(Map param) {
        return phoneorderdao.getUserId(param);
    }
}

