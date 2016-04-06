package com.brilliantreform.sc.product.controller;

import com.brilliantreform.sc.common.Pager;
import com.brilliantreform.sc.common.RtnResult;
import com.brilliantreform.sc.community.po.Community;
import com.brilliantreform.sc.community.service.CommunityService;
import com.brilliantreform.sc.incomming.po.IncommingDetailBean;
import com.brilliantreform.sc.incomming.po.ProductBatchStockBean;
import com.brilliantreform.sc.incomming.po.ProductRealStockBean;
import com.brilliantreform.sc.product.enumerate.ProductDBStatusEnum;
import com.brilliantreform.sc.product.po.ProductDBDetail;
import com.brilliantreform.sc.product.po.ProductDBHeader;
import com.brilliantreform.sc.product.po.ProductDBTemp;
import com.brilliantreform.sc.product.service.ProductService;
import com.brilliantreform.sc.product.service.WarehouseService;
import com.brilliantreform.sc.purchase.po.SupplierInfo;
import com.brilliantreform.sc.purchase.service.PurchaseService;
import com.brilliantreform.sc.service.po.ServiceVo;
import com.brilliantreform.sc.service.service.SevService;
import com.brilliantreform.sc.stock.po.TotalService;
import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.utils.CommonUtil;
import com.brilliantreform.sc.utils.GridUtil;
import com.brilliantreform.sc.utils.LogerUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.*;

@Controller
@RequestMapping("warehouse.do")
public class WarehouseCtrl {

	private static Logger LOGGER = Logger.getLogger(WarehouseCtrl.class);

	@Autowired
	private ProductService productService;
	@Autowired
	private SevService sevService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private CommunityService communityService;
    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping(params = "method=showWarehousePage")
    public String showWarehousePage(HttpServletRequest request) {
        MgrUser mgrUser = (MgrUser)request.getSession().getAttribute("user_info");
        Boolean user_isAdmin = (Boolean)request.getSession().getAttribute("user_isAdmin");
        List<Map> communityList = new ArrayList<Map>();
        if(user_isAdmin != null && user_isAdmin) {
            communityList = communityService.findSecondAdminCommunity();
        } else {
            communityList = communityService.findSecondUserCommunity(mgrUser.getUser_id());
        }
        request.setAttribute("communityList", communityList);
        return "/new/jsp/product/warehouse";
    }

    /**
     * 获取仓库商品tree
     * */
    @RequestMapping(params = "method=loadWarehouseProduct")
    public void loadWarehouseProduct(HttpServletRequest request, HttpServletResponse response) {
        try {
            LogerUtil.logRequest(request, LOGGER, "loadWarehouseProduct");

            Integer is_total = CommonUtil.safeToInteger(request.getParameter("is_total"), null);
            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);

            List<Map> tree = new ArrayList<Map>();
            if(is_total != null && community_id != null) {
                if(is_total.intValue() == 1) {
                    Community totalCommunity = communityService.getTotalCommunity();
                    if(totalCommunity != null) {
                        tree = warehouseService.getTotalServiceProductTree(totalCommunity.getCommunity_id());
                    }
                } else {
                    Map param = new HashMap();
                    param.put("community_id", community_id);
                    tree = warehouseService.getServiceProductTree(param);
                }
            }

            //处理节点名称
            for(Map map : tree) {
                String id = CommonUtil.safeToString(map.get("id"), null);
                if(StringUtils.isNotBlank(id) && id.startsWith("p_")) {
                    String name = CommonUtil.safeToString(map.get("name"), "");
                    //todo 此处库存应减去product_db_temp表中的数量 ??
                    Double real_stock_sum = CommonUtil.safeToDouble(map.get("real_stock_sum"), 0d);

                    if(real_stock_sum < 5) {
                        map.put("name", "<font color='red'>" + name + "\t" + new DecimalFormat("#.##").format(real_stock_sum) + "</font>");
                    } else {
                        map.put("name", name + "\t" + new DecimalFormat("#.##").format(real_stock_sum));
                    }
                }
            }

            CommonUtil.outToWeb(response, JSONArray.fromObject(tree).toString());
        } catch (Exception e) {
            LOGGER.error("loadWarehouseProduct error", e);
        }
    }

    @RequestMapping(params = "method=getProductById")
    public void getProductById(HttpServletRequest request, HttpServletResponse response) {
        try {
            Integer product_id = CommonUtil.safeToInteger(request.getParameter("product_id"), null);
            Integer is_total = CommonUtil.safeToInteger(request.getParameter("is_total"), null);

            if(product_id != null && is_total != null) {
                String community_id = "";
                String product_name = "";   //商品名
                String picture = "";    //缩略图
                String spec = "";   //规格
                Double in_price = 0d;   //进价
                Double price = 0d;      //参考售价
                String product_service = "";    //商品分类
                Double stock_num = 0d;  //库存数量
                if(is_total.intValue() == 1) {
                    //总库商品
                    Community community = communityService.getTotalCommunity();
                    community_id = community.getCommunity_id() + "";
                    Map product = warehouseService.getTotalProductById(community.getCommunity_id(), product_id);
                    product_name = product.get("name") + "";
                    picture = product.get("thumbnail") + "";
                    spec = product.get("unit") + "";
                    in_price = CommonUtil.safeToDouble(product.get("pre_incomming_price"), 0d);
                    stock_num = CommonUtil.safeToDouble(product.get("real_stock_sum"), 0d);
                    price = CommonUtil.safeToDouble(product.get("price"), 0d);
                } else {
                    //分库商品
                    Map product = warehouseService.getProductById(product_id);
                    community_id = product.get("community_id") + "";
                    product_name = product.get("name") + "";
                    picture = product.get("thumbnail") + "";
                    spec = product.get("unit") + "";
                    in_price = CommonUtil.safeToDouble(product.get("pre_incomming_price"), 0d);
                    stock_num = CommonUtil.safeToDouble(product.get("real_stock_sum"), 0d);
                    price = CommonUtil.safeToDouble(product.get("price"), 0d);
                }

                double tempCount = warehouseService.getProductCountInDBTemp(CommonUtil.safeToInteger(community_id, -1), product_id);

                Map map = new HashMap();
                map.put("product_id", product_id);
                map.put("community_id", community_id);
                map.put("is_total", is_total);
                map.put("product_name", product_name);
                map.put("picture", picture);
                map.put("spec", spec);
                map.put("in_price", in_price);
                map.put("price", price);
                map.put("product_service", product_service);
                map.put("stock_num", stock_num - tempCount);
                CommonUtil.outToWeb(response, JSONObject.fromObject(map).toString());
            }
        } catch (Exception e) {
            LOGGER.error("getProductById error", e);
        }
    }

    /**
     * 加入调拨清单，此处不直接减实时库存，计算实时库存的时候要关联查询product_db_temp表
     * */
    @RequestMapping(params = "method=addDBTemp")
    public void addDBTemp(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            Integer product_id = CommonUtil.safeToInteger(request.getParameter("product_id"), null);
            Integer community_id = CommonUtil.safeToInteger(request.getParameter("community_id"), null);
            Integer is_total = CommonUtil.safeToInteger(request.getParameter("is_total"), null);
            Double amount = CommonUtil.safeToDouble(request.getParameter("amount"), null);

            if(product_id != null && community_id != null && is_total != null && amount != null) {
                boolean flag = true;
                MgrUser user = (MgrUser) request.getSession().getAttribute("user_info");
                //调拨清单中只允许有一个门店的商品
                List<Integer> cids = warehouseService.findDBTempCommunity(user.getUser_id());
                if (cids != null && cids.size() > 0) {
                    Integer cid = cids.get(0);
                    if (community_id.intValue() != cid.intValue()) {
                        result.setMsg("调拨清单中还存在不同门店的商品，请在调拨清单处理！");
                        flag = false;
                    }
                }

                if (flag) {
                    ProductDBTemp temp = warehouseService.getProductDBTempInfo(community_id, user.getUser_id(), product_id);
                    if (temp != null) {
                        //如果存在则新增数量
                        temp.setAmount(temp.getAmount() + amount);
                    } else {
                        temp = new ProductDBTemp();
                        temp.setCommunity_id(community_id);
                        temp.setUser_id(user.getUser_id());
                        temp.setProduct_id(product_id);
                        temp.setIs_total(is_total);
                        temp.setAmount(amount);
                    }
                    warehouseService.saveProductDBTemp(temp);
                    //返回该商品的库存数量：实际库存-调拨清单中的数量
                    Map rtnMap = new HashMap();
                    ProductRealStockBean realStock = warehouseService.getProductRealStock(community_id, product_id);
                    if(realStock != null) {
                        double tempCount = warehouseService.getProductCountInDBTemp(community_id, product_id);
                        rtnMap.put("realStock", realStock.getReal_stock_sum());
                        rtnMap.put("tempCount", tempCount);
                    }
                    result.setData(rtnMap);
                    result.setResult(true);
                }
            }

        } catch (Exception e) {
            LOGGER.error("addDBTemp error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    @RequestMapping(params = "method=showIntoWarehousePage")
    public String showIntoWarehousePage() {
        return "/new/jsp/product/intoWarehouse";
    }

    /**
     * 根据关键字检索总库商品，bootstrap-suggest插件数据源
     * */
    @RequestMapping(params = "method=searchTotalProductByKeyword")
    public void searchTotalProductByKeyword(HttpServletRequest request, HttpServletResponse response) {
        try {
            String keyword = CommonUtil.safeToString(request.getParameter("keyword"), null);

            if(StringUtils.isNotBlank(keyword)) {
                keyword = new String(keyword.getBytes("iso8859-1"),"utf-8");
                Community totalCommunity = communityService.getTotalCommunity();
                if(totalCommunity != null) {
                    List<Map> list = warehouseService.searchTotalProductByKeyword(totalCommunity.getCommunity_id(), keyword);

                    Map map = new HashMap();
                    map.put("value", list);
                    CommonUtil.outToWeb(response, JSONObject.fromObject(map).toString());
                }
            }

        } catch (Exception e) {
            LOGGER.error("searchTotalProductByKeyword error", e);
        }
    }

    /**
     * 获取总库商品的供应商信息
     * */
    @RequestMapping(params = "method=findSupplier")
    public void findSupplier(HttpServletRequest request, HttpServletResponse response) {
        try {
            Integer product_id = CommonUtil.safeToInteger(request.getParameter("product_id"), null);

            if(product_id != null) {
                List<SupplierInfo> supplierList = purchaseService.findSupplierByProductId(product_id);
                if(supplierList != null && supplierList.size() > 0) {
                    CommonUtil.outToWeb(response, JSONArray.fromObject(supplierList).toString());
                }
            }
        } catch (Exception e) {
            LOGGER.error("findSupplier error", e);
        }
    }

    /**
     * 总库商品入库
     * */
    @RequestMapping(params = "method=intoWarehouse")
    public void intoWarehouse(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            String[] product_id_array = request.getParameterValues("product_id");
            String[] unit_price_array = request.getParameterValues("unit_price");
            String[] change_count_array = request.getParameterValues("change_count");
            String[] deadline_array = request.getParameterValues("deadline");
            String[] supplier_id_array = request.getParameterValues("supplier_id");
            String[] puhuo_flag_array = request.getParameterValues("puhuo_flag");
            String[] remark_array = request.getParameterValues("remark");

            if(ArrayUtils.isNotEmpty(product_id_array) && ArrayUtils.isNotEmpty(unit_price_array) && ArrayUtils.isNotEmpty(change_count_array)) {
                List<IncommingDetailBean> detailList = new ArrayList<IncommingDetailBean>();
                for(int i = 0; i < product_id_array.length; i ++) {
                    Integer product_id = CommonUtil.safeToInteger(product_id_array[i], null);
                    Double unit_price = CommonUtil.safeToDouble(unit_price_array[i], null);
                    Double change_count = CommonUtil.safeToDouble(change_count_array[i], null);
                    Date deadline = CommonUtil.safeToDate(deadline_array[i], "yyyy-MM-dd");
                    Integer supplier_id = CommonUtil.safeToInteger(supplier_id_array[i], null);
                    Integer puhuo_flag = CommonUtil.safeToInteger(puhuo_flag_array[i], 0);
                    String remark = remark_array[i];

                    IncommingDetailBean detail = new IncommingDetailBean();
                    detail.setProduct_id(product_id);
                    detail.setUnit_price(unit_price);
                    detail.setChange_count(change_count);
                    detail.setDeadline(deadline);
                    detail.setSupplier_id(supplier_id);
                    detail.setPuhuo_flag(puhuo_flag);
                    detail.setRemark(remark);

                    detailList.add(detail);
                }
                MgrUser user = (MgrUser)request.getSession().getAttribute("user_info");
                warehouseService.intoWarehouse(detailList, user, CommonUtil.getIp(request));

                result.setResult(true);
            }

        } catch (Exception e) {
            LOGGER.error("findSupplier error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    @RequestMapping(params = "method=showListDBTempPage")
    public String showListDBTempPage(HttpServletRequest request) {
        List<Map> list = new ArrayList<Map>();
        try {
            MgrUser user = (MgrUser) request.getSession().getAttribute("user_info");
            List<ProductDBTemp> tempList = warehouseService.findUserProductDBTemp(user.getUser_id());
            Community totalCommunity = communityService.getTotalCommunity();
            for (ProductDBTemp temp : tempList) {
                Map product = new HashMap();
                String service_name = "";   //商品分类
                if (totalCommunity.getCommunity_id().intValue() == temp.getCommunity_id()) {
                    product = warehouseService.getTotalProductById(totalCommunity.getCommunity_id(), temp.getProduct_id());

                    TotalService service_1 = warehouseService.getTotalServiceById(CommonUtil.safeToInteger(product.get("service_id"), -1));
                    if(service_1 != null) {
                        service_name = service_1.getService_name();
                        if(service_1.getParent_id().intValue() != 0) {
                            TotalService service_2 = warehouseService.getTotalServiceById(service_1.getParent_id());
                            if(service_2 != null) {
                                service_name = service_2.getService_name() + " - " + service_1.getService_name();
                            }
                        }
                    }
                } else {
                    product = warehouseService.getProductById(temp.getProduct_id());

                    ServiceVo service_1 = sevService.getServiceById(CommonUtil.safeToInteger(product.get("service_id"), -1));
                    if(service_1 != null) {
                        service_name = service_1.getService_name();
                        if(service_1.getParent_id().intValue() != 0) {
                            ServiceVo service_2 = sevService.getServiceById(service_1.getParent_id());
                            if(service_2 != null) {
                                service_name = service_2.getService_name() + " - " + service_1.getService_name();
                            }
                        }
                    }
                }

                product.put("service_name", service_name);

                Map map = new HashMap();
                map.put("product", product);
                map.put("temp", temp);

                //获取该商品所有的库存大于0的批次
                List<ProductBatchStockBean> batchStockList = warehouseService.findProductBatchStock(temp.getCommunity_id(), temp.getProduct_id());
                if (batchStockList != null && batchStockList.size() > 0) {
                    map.put("batchStock", batchStockList);
                }
                list.add(map);
            }
        } catch (Exception e) {
            LOGGER.error("showListDBTempPage error", e);
        }
        request.setAttribute("list", list);
        return "/new/jsp/product/listDBTemp";
    }

    /**
     * 提交调拨单
     * */
    @RequestMapping(params = "method=submitDBTemp")
    public void submitDBTemp(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        result.setMsg("操作失败！");
        try {
            Integer to_cid = CommonUtil.safeToInteger(request.getParameter("to_cid"), null);
            String[] community_id_array = request.getParameterValues("community_id");
            String[] batch_serial_array = request.getParameterValues("batch_serial");
            String[] product_id_array = request.getParameterValues("product_id");
            String[] is_total_array = request.getParameterValues("is_total");
            String[] unit_price_array = request.getParameterValues("unit_price");
            String[] change_count_array = request.getParameterValues("change_count");
            String[] pre_stock_count_array = request.getParameterValues("pre_stock_count");

            boolean flag = true;
            int from_cid = -1;
            if(to_cid != null && ArrayUtils.isNotEmpty(community_id_array) && ArrayUtils.isNotEmpty(batch_serial_array)
                    && ArrayUtils.isNotEmpty(product_id_array) && ArrayUtils.isNotEmpty(is_total_array) && ArrayUtils.isNotEmpty(unit_price_array)
                    && ArrayUtils.isNotEmpty(change_count_array) && ArrayUtils.isNotEmpty(pre_stock_count_array)) {
                //校验商品是否是同一门店
                Set set = new HashSet();
                set.addAll(Arrays.asList(community_id_array));
                if(set.size() != 1) {
                    flag = false;
                    result.setMsg("调拨商品来自于不同的门店，无法调拨！");
                } else {
                    //校验来源门店和目的地门店是否是同一门店
                    from_cid = CommonUtil.safeToInteger(set.toArray()[0], -1);
                    if(to_cid.intValue() == from_cid) {
                        flag = false;
                        result.setMsg("只能调拨至其他门店！");
                    }
                }
            } else {
                flag = false;
            }

            if(flag) {
                MgrUser user = (MgrUser) request.getSession().getAttribute("user_info");
                //生成调拨单
                ProductDBHeader header = new ProductDBHeader();
                header.setFrom_cid(from_cid);
                header.setTo_cid(to_cid);
                header.setHeader_no("DB" + CommonUtil.formatDate(new Date(), "yyyyMMddHHmmss"));
                header.setUser_id(user.getUser_id());
                header.setCreate_time(new Date());
                header.setIs_total(CommonUtil.safeToInteger(is_total_array[0], null));
                header.setStatus(ProductDBStatusEnum.INIT.getValue());
                header.setRemovetag(0);
                warehouseService.saveProductDBHeader(header);

                for(int i = 0; i < batch_serial_array.length; i++) {
                    String batch_serial = batch_serial_array[i];
                    Integer product_id = CommonUtil.safeToInteger(product_id_array[i], null);
                    Double unit_price = CommonUtil.safeToDouble(unit_price_array[i], null);
                    Double change_count = CommonUtil.safeToDouble(change_count_array[i], null);
                    Double pre_stock_count = CommonUtil.safeToDouble(pre_stock_count_array[i], null);

                    ProductDBDetail detail = new ProductDBDetail();
                    detail.setHeader_id(header.getObjid());
                    detail.setBatch_serial(batch_serial);
                    detail.setProduct_id(product_id);
                    detail.setIs_total(CommonUtil.safeToInteger(is_total_array[0], null));
                    detail.setUnit_price(unit_price);
                    detail.setChange_count(change_count);
                    detail.setPre_stock_count(pre_stock_count);
                    detail.setRemovetag(0);
                    warehouseService.saveProductDBDetail(detail);
                }
                //清空dbTemp表数据
                warehouseService.deleteUserProductDBTemp(user.getUser_id());
                result.setResult(true);
            }

        } catch (Exception e) {
            LOGGER.error("submitDBHeader error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    @RequestMapping(params = "method=removeDBTemp")
    public void removeDBTemp(HttpServletRequest request, HttpServletResponse response) {
        RtnResult result = new RtnResult(false);
        try {
            Integer objid = CommonUtil.safeToInteger(request.getParameter("objid"), null);

            if(objid != null) {
                warehouseService.deleteProductDBTemp(objid);
                result.setResult(true);
            }
        } catch (Exception e) {
            LOGGER.error("removeDBTemp error", e);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(result).toString());
    }

    @RequestMapping(params = "method=showListDBHeaderPage")
    public String showListDBHeaderPage(HttpServletRequest request) {
        return "/new/jsp/product/listDBHeader";
    }

    @RequestMapping(params = "method=searchDBHeader")
    public void searchDBHeader(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);

            List<Map> list = warehouseService.searchDBHeader(param);
            int count = warehouseService.searchDBHeaderCount(param);

            pager = GridUtil.setPagerResult(pager, list, count);
        } catch (Exception e) {
            LOGGER.error("searchDBHeader error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }

    @RequestMapping(params = "method=searchDBDetail")
    public void searchDBDetail(String dtGridPager, HttpServletRequest request, HttpServletResponse response) {
        Pager pager = new Pager();
        try {
            pager = GridUtil.toPager(JSONObject.fromObject(dtGridPager));

            Map param = GridUtil.parseGridPager(pager);

            Integer header_id = CommonUtil.safeToInteger(param.get("header_id"), null);
            List<Map> list = new ArrayList<Map>();
            int count = 0;
            if(header_id != null) {
                ProductDBHeader dbHeader = warehouseService.getDBHeaderById(header_id);
                if(dbHeader != null) {
                    //判断来源门店是否是总库
                    Community totalCommunity = communityService.getTotalCommunity();
                    if(totalCommunity != null) {
                        if(dbHeader.getFrom_cid().intValue() == totalCommunity.getCommunity_id().intValue()) {
                            list = warehouseService.searchTotalDBDetail(param);
                            count = warehouseService.searchTotalDBDetailCount(param);
                        } else {
                            list = warehouseService.searchDBDetail(param);
                            count = warehouseService.searchDBDetailCount(param);
                        }
                    }
                }

            }

            pager = GridUtil.setPagerResult(pager, list, count);
        } catch (Exception e) {
            LOGGER.error("searchDBDetail error", e);
            pager.setIsSuccess(false);
        }
        CommonUtil.outToWeb(response, JSONObject.fromObject(pager).toString());
    }
}
