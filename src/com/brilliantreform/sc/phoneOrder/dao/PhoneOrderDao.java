package com.brilliantreform.sc.phoneOrder.dao;

import com.brilliantreform.sc.phoneOrder.po.DistriOrderBean;
import com.brilliantreform.sc.phoneOrder.po.PhoneOrder;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Lm on 2015/11/30 0030.
 */
@Repository("phoneorderdao")
public class PhoneOrderDao {
    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    public DistriOrderBean getDisworkerInfo(Map<String, Object> map) {
        return (DistriOrderBean) sqlMapClient.queryForObject(
                "phoneOrder.getDisworkerInfo", map);
    }

    @SuppressWarnings("unchecked")
    public List<DistriOrderBean> getDistriHead(Map<String, Object> map) {
        return (List<DistriOrderBean>) sqlMapClient.queryForList(
                "phoneOrder.getDistriHead", map);
    }

    public DistriOrderBean getDistriDetail(Map<String, Object> map) {
        return (DistriOrderBean) sqlMapClient.queryForObject(
                "phoneOrder.getDistriDetail", map);
    }

    @SuppressWarnings("unchecked")
    public List<DistriOrderBean> getDistriProduct(Map<String, Object> map) {
        return (List<DistriOrderBean>) sqlMapClient.queryForList(
                "phoneOrder.getDistriProduct", map);
    }

    public void optDetail(Map<String, Object> map) {
        sqlMapClient.update("phoneOrder.optDetail", map);
    }

    public void optProduct(Map<String, Object> map) {
        sqlMapClient.update("phoneOrder.optProduct", map);
    }

    @SuppressWarnings("unchecked")
    public List<DistriOrderBean> getSerialByPhone(Map<String, Object> map) {
        return (List<DistriOrderBean>) sqlMapClient.queryForList("phoneOrder.getSerialByPhone", map);
    }

    public void updateOrderStatus(Map<String, Object> distri) {
        sqlMapClient.update("phoneOrder.updateOrderStatus", distri);
    }

    public void updateOrderBaseStatus(Map<String, Object> distri) {
        sqlMapClient.update("phoneOrder.updateOrderBaseStatus", distri);
    }

    public List<PhoneOrder> weiXinTs() {
        return (List<PhoneOrder>) sqlMapClient.queryForList("phoneOrder.weiXinTs");
    }

    public List<PhoneOrder> getOrderList(Map<String, Object> distri) {
        return (List<PhoneOrder>) sqlMapClient.queryForList("phoneOrder.getOrderList", distri);
    }

    public List<Map> getNewOrder() {
        return (List<Map>) sqlMapClient.queryForObject("phoneOrder.getNewOrder");
    }


    public Map acceptOrder(Map paramMap) {
        sqlMapClient.queryForObject("phoneOrder.acceptOrder", paramMap);
        return paramMap;
    }

    public void finishOrder(DistriOrderBean distri) {
        sqlMapClient.update("phoneOrder.finishOrder", distri);
    }

    public List<DistriOrderBean> getDeliveryDetail(Map<String, Object> distri) {
        return (List<DistriOrderBean>) sqlMapClient.queryForList("phoneOrder.getDeliveryDetail", distri);
    }

    public Integer getNewOrderCount(Map<String, Object> distri) {
        return (Integer) sqlMapClient.queryForObject("phoneOrder.getNewOrderCount", distri);
    }

    public void updatePreSaleOrder(String order_serial, int type) {
        if (type == 1) {
            sqlMapClient.update("phoneOrder.updatePreSaleOrder", order_serial);
        }
        if (type == 2) {
            sqlMapClient.update("phoneOrder.updatePreSaleOrder2", order_serial);
        }
    }

    public void updatePreSaleOrderProduct(String order_serial, int type) {
        if (type == 1) {
            sqlMapClient.update("phoneOrder.updatePreSaleOrderProduct", order_serial);
        }
        if (type == 2) {
            sqlMapClient.update("phoneOrder.updatePreSaleOrderProduct2", order_serial);
        }
    }

    public List<PhoneOrder> getBaseProduct(Map<String, Object> distri) {
        return (List<PhoneOrder>) sqlMapClient.queryForList("phoneOrder.getBaseProduct", distri);
    }

    public MgrUser getMgrUser(String loginname) {
        return (MgrUser) sqlMapClient.queryForObject("phoneOrder.getMgrUser", loginname);
    }

    public void finishOrderBase(Map map) {
        sqlMapClient.update("phoneOrder.finishOrderBase", map);
    }

    public List<PhoneOrder> getOrderBase(Map param) {
        return (List<PhoneOrder>) sqlMapClient.queryForList("phoneOrder.getOrderBase", param);
    }

    public Map selOpenidMgrUser(String openid) {
        return (Map) sqlMapClient.queryForObject("phoneOrder.selOpenidMgrUser", openid);
    }

    public int updateMgrUserOpenid(MgrUser mgrUser) {
        return sqlMapClient.update("phoneOrder.updateMgrUserOpenid", mgrUser);
    }

    public int insertMgrUserOpenid(MgrUser mgrUser) {
        return (Integer) sqlMapClient.insert("phoneOrder.insertMgrUserOpenid", mgrUser);
    }

    public MgrUser selMgrUserByid(int user_id) {
        return (MgrUser) sqlMapClient.queryForObject("phoneOrder.selMgrUserByid", user_id);
    }

    public void delMgrUserOpenid(MgrUser mgrUser) {
        sqlMapClient.delete("phoneOrder.delMgrUserOpenid", mgrUser);
    }

    public List<Map> getPhoneOpenid(int user_id) {
        return (List<Map>) sqlMapClient.queryForList("phoneOrder.getPhoneOpenid", user_id);
    }

    public List<Map> getUserId(Map param) {
        return (List<Map>) sqlMapClient.queryForList("phoneOrder.getUserId",param);
    }
}
