package com.brilliantreform.sc.product.service;

import com.brilliantreform.sc.community.dao.CommunityDao;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.incomming.dao.IncommingOrderDao;
import com.brilliantreform.sc.incomming.po.*;
import com.brilliantreform.sc.incomming.service.IncommingOrderService;
import com.brilliantreform.sc.product.dao.WarehouseDao;
import com.brilliantreform.sc.product.enumerate.StockTypeEnum;
import com.brilliantreform.sc.product.po.Product;
import com.brilliantreform.sc.product.po.ProductDBDetail;
import com.brilliantreform.sc.product.po.ProductDBHeader;
import com.brilliantreform.sc.product.po.ProductDBTemp;
import com.brilliantreform.sc.stock.po.TotalProduct;
import com.brilliantreform.sc.stock.po.TotalService;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class WarehouseService {
	private static Logger LOGGER = Logger.getLogger(WarehouseService.class);

	@Autowired
	private WarehouseDao warehouseDao;
    @Autowired
    private CommunityDao communityDao;
    @Autowired
    private IncommingOrderDao incommingOrderDao;

    /**
     * 总库分类商品tree
     * @param community_id 总库的cid
     * */
    public List<Map> getTotalServiceProductTree(int community_id) {
        List<Map> serviceList = warehouseDao.getTotalServiceTree();
        List<Map> productList = warehouseDao.getTotalProductTree(community_id);

        List<Map> list = new ArrayList<Map>();
        list.addAll(serviceList);
        list.addAll(productList);
        return list;
    }

    /**
     * 分库分类商品tree
     * */
    public List<Map> getServiceProductTree(Map param) {
        List<Map> serviceList = warehouseDao.getServiceTree(param);
        List<Map> productList = warehouseDao.getProductTree(param);

        List<Map> list = new ArrayList<Map>();
        list.addAll(serviceList);
        list.addAll(productList);
        return list;
    }

    public Map getTotalProductById(int community_id, int product_id) {
        return warehouseDao.getTotalProductById(community_id, product_id);
    }

    public Map getProductById(int product_id) {
        return warehouseDao.getProductById(product_id);
    }

    public void saveProductDBTemp(ProductDBTemp productDBTemp) {
        if(productDBTemp != null) {
            if(productDBTemp.getObjid() == null) {
                productDBTemp.setCreate_time(new Date());
                warehouseDao.insertProductDBTemp(productDBTemp);
            } else {
                warehouseDao.updateProductDBTemp(productDBTemp);
            }
        }
    }

    public ProductDBTemp getProductDBTempInfo(int community_id, int user_id, int product_id) {
        return warehouseDao.getProductDBTempInfo(community_id, user_id, product_id);
    }

    public List<Map> searchTotalProductByKeyword(int community_id, String keyword) {
        return warehouseDao.searchTotalProductByKeyword(community_id, keyword);
    }

    public List<ProductDBTemp> findUserProductDBTemp(int user_id) {
        return warehouseDao.findUserProductDBTemp(user_id);
    }

    public void deleteUserProductDBTemp(int user_id) {
        warehouseDao.deleteUserProductDBTemp(user_id);
    }

    public List<ProductBatchStockBean> findProductBatchStock(int community_id, int product_id) {
        return warehouseDao.findProductBatchStock(community_id, product_id);
    }

    public TotalService getTotalServiceById(int service_id) {
        return warehouseDao.getTotalServiceById(service_id);
    }

    public void saveProductDBHeader(ProductDBHeader header) {
        if(header != null) {
            if(header.getObjid() == null) {
                warehouseDao.insertProductDBHeader(header);
            } else {
                warehouseDao.updateProductDBHeader(header);
            }
        }
    }

    public void saveProductDBDetail(ProductDBDetail detail) {
        if(detail != null) {
            if(detail.getObjid() == null) {
                warehouseDao.insertProductDBDetail(detail);
            } else {
                warehouseDao.updateProductDBDetail(detail);
            }
        }
    }

    public List<Integer> findDBTempCommunity(int user_id) {
        return warehouseDao.findDBTempCommunity(user_id);
    }

    public void deleteProductDBTemp(int objid) {
        warehouseDao.deleteProductDBTemp(objid);
    }

    public ProductRealStockBean getProductRealStock(int community_id, int product_id) {
        return warehouseDao.getProductRealStock(community_id, product_id);
    }

    public double getProductCountInDBTemp(int community_id, int product_id) {
        return warehouseDao.getProductCountInDBTemp(community_id, product_id);
    }

    public List<Map> searchDBHeader(Map param) {
        return warehouseDao.searchDBHeader(param);
    }

    public int searchDBHeaderCount(Map param) {
        return warehouseDao.searchDBHeaderCount(param);
    }

    public List<Map> searchDBDetail(Map param) {
        return warehouseDao.searchDBDetail(param);
    }

    public int searchDBDetailCount(Map param) {
        return warehouseDao.searchDBDetailCount(param);
    }

    public List<Map> searchTotalDBDetail(Map param) {
        return warehouseDao.searchTotalDBDetail(param);
    }

    public int searchTotalDBDetailCount(Map param) {
        return warehouseDao.searchTotalDBDetailCount(param);
    }

    public ProductDBHeader getDBHeaderById(int objid) {
        return warehouseDao.getDBHeaderById(objid);
    }

    /**
     * 总库商品入库统一调用方法
     * @param detailList 入库商品的集合封装，只需要包含product_id、change_count、unit_price、remark、deadline、supplier_id、puhuo_flag
     * */
    @Transactional
    public void intoWarehouse(List<IncommingDetailBean> detailList, MgrUser user, String ip) {
        String ymdhms = CommonUtil.formatDate(new Date(), "yyyyMMddHHmmss");
        String header_no = "RK" + ymdhms;   //入库单号：RK + 年月日时分秒
        String batch_serial = ymdhms;       //批次号：年月日时分秒
        //总库id
        Community community = communityDao.getTotalCommunity();
        String loginName = user.getLoginname();

        //生成入库单
        IncommingHeaderBean header = new IncommingHeaderBean();
        header.setCommunity_id(community.getCommunity_id());
        header.setStockchange_header_no(header_no);
        header.setStock_type(StockTypeEnum.RK.getValue());
        header.setCreate_by(loginName);
        header.setCreate_time(new Date());
        header.setState(1);     //未确认
        incommingOrderDao.insertIncommingHeader(header);

        for(IncommingDetailBean detail : detailList) {
            //获取商品的实时库存信息
            ProductRealStockBean realStock = warehouseDao.getProductRealStock(community.getCommunity_id(), detail.getProduct_id());
            if(realStock != null) {
                //更新商品库存信息
                realStock.setReal_stock_sum(realStock.getReal_stock_sum() + detail.getChange_count());
                realStock.setPre_incomming_price(detail.getUnit_price());
                warehouseDao.updateProductRealStock(realStock);

                detail.setPre_stock_count(realStock.getReal_stock_sum());
            } else {
                //插入实时库存信息
                realStock = new ProductRealStockBean();
                realStock.setProduct_id(detail.getProduct_id());
                realStock.setCommunity_id(community.getCommunity_id());
                realStock.setReal_stock_sum(detail.getChange_count());
                realStock.setPre_incomming_price(detail.getUnit_price());
                realStock.setCreate_time(new Date());
                incommingOrderDao.insertProductStock(realStock);
            }

            detail.setCommunity_id(community.getCommunity_id());
            detail.setStockchange_header_id(header.getStockchange_header_id());
            detail.setBatch_serial(batch_serial);
            detail.setStock_type(StockTypeEnum.RK.getValue());
            detail.setCreate_by(loginName);
            detail.setCreate_time(new Date());
            incommingOrderDao.insertIncommingDetail(detail);

            //插入商品批次库存(入库的时候肯定不会存在该批次记录，故无需更新)
            ProductBatchStockBean productBatchStock = new ProductBatchStockBean();
            productBatchStock.setCommunity_id(community.getCommunity_id());
            productBatchStock.setBatch_serial(batch_serial);
            productBatchStock.setProduct_id(detail.getProduct_id());
            productBatchStock.setIncommint_price(detail.getUnit_price());
            productBatchStock.setStock_sum(realStock.getReal_stock_sum());      //该批次的总库存
            productBatchStock.setOrder_current_sum(detail.getChange_count());   //该批次的实时库存
            productBatchStock.setCreate_time(new Date());
            incommingOrderDao.insertProductBatchStock(productBatchStock);

            //记录库存变更日志
            StockChangeLogBean changeLogBean = new StockChangeLogBean();
            changeLogBean.setProduct_id(detail.getProduct_id());
            changeLogBean.setCommunity_id(detail.getCommunity_id());
            changeLogBean.setCreate_by(loginName);
            changeLogBean.setOrder_current_sum(detail.getChange_count());
            changeLogBean.setCreate_type(1);    //创建人类型（1=用户，2=管理后台）
            changeLogBean.setIp(ip);
            changeLogBean.setLog_type(StockTypeEnum.RK.getValue());
            changeLogBean.setStock_sum(realStock.getReal_stock_sum());
            changeLogBean.setBatch_serial(batch_serial);
            incommingOrderDao.insertChangeLog(changeLogBean);

        }

    }
}
