package com.brilliantreform.sc.outgoing.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brilliantreform.sc.incomming.po.IncommingDetailBean;
import com.brilliantreform.sc.incomming.po.IncommingHeaderBean;
import com.brilliantreform.sc.incomming.po.ProductBatchStockBean;
import com.brilliantreform.sc.incomming.po.ProductRealStockBean;
import com.brilliantreform.sc.incomming.po.StockChangeLogBean;
import com.brilliantreform.sc.outgoing.dao.OutgoingOrderDao;
import com.brilliantreform.sc.product.po.ProductDouble;

@Service("outgoingOrderService")
public class OutgoingOrderService {

	@Autowired
	private OutgoingOrderDao outgoingOrderDao;

	/**
	 * 查询进货，调拨入，退货，损耗，调拨出单据汇总
	 * 
	 * @param headerBean
	 * @return
	 */
	public List<IncommingHeaderBean> getIncommingHeader(Map<String, Object> map) {
		return outgoingOrderDao.getIncommingHeader(map);
	}

	/**
	 * 查询进货，调拨入，退货，损耗，调拨出单据汇总总数
	 * 
	 * @param headerBean
	 * @return
	 */
	public Integer getIncommingHeaderCount(Map<String, Object> map) {
		return outgoingOrderDao.getIncommingHeaderCount(map);
	}

	/**
	 * 查询进货，调拨入，退货，损耗，调拨出单据明细
	 * 
	 * @param detailBean
	 * @return
	 */
	public List<IncommingDetailBean> getIncommintDetail(
			IncommingDetailBean detailBean) {
		return (List<IncommingDetailBean>)outgoingOrderDao.getIncommintDetail(detailBean);
	}

	/**
	 * 查询进货，调拨入，退货，损耗，调拨出单据明细总数
	 * 
	 * @param detailBean
	 * @return
	 */
	public Integer getIncommintDetailCount(IncommingDetailBean detailBean) {
		return outgoingOrderDao.getIncommintDetailCount(detailBean);
	}

	/**
	 * 插入进货，调拨入，退货，损耗，调拨出单据汇总
	 * 
	 * @param headerBean
	 * @return
	 */
	public Integer insertIncommingHeader(IncommingHeaderBean headerBean) {
		return outgoingOrderDao.insertIncommingHeader(headerBean);
	}
	
	/**
	 * 修改汇总信息
	 * @param headerBean
	 */
	public void updateHeaderInfo(IncommingHeaderBean headerBean) {
		outgoingOrderDao.updateHeaderInfo(headerBean);
	}

	/**
	 * 插入查询进货，调拨入，退货，损耗，调拨出单据明细
	 * 
	 * @param detailList
	 */
	@Transactional
	public void insertIncommingDetail(List<IncommingDetailBean> detailList) {
		for (IncommingDetailBean incommingDetailBean : detailList) {
			//根据单据类型做变动数量是正还是负1=进货，2=调拨入，3=退货，4=损耗，5=调拨出
			Double changeCount=(incommingDetailBean.getStock_type()>2&&incommingDetailBean.getStock_type()<6)?(-1*incommingDetailBean.getChange_count()):incommingDetailBean.getChange_count();
			Double stock_sum=incommingDetailBean.getChange_count();
			
			//更新商品批次库存 begin
			ProductBatchStockBean batchBean=new ProductBatchStockBean();
//			batchBean.setBatch_serial(incommingDetailBean.getBatch_serial());
			batchBean.setCommunity_id(incommingDetailBean.getCommunity_id());
			batchBean.setProduct_id(incommingDetailBean.getProduct_id());
//			batchBean.setIncommint_price(incommingDetailBean.getUnit_price());
			batchBean.setStock_sum(changeCount);
			batchBean.setOrder_current_sum(incommingDetailBean.getChange_count());
			ProductBatchStockBean retBatchBean=outgoingOrderDao.getProductBatchStockById(batchBean);
			//如果不存在记录，则插入记录
			if(retBatchBean==null){
				outgoingOrderDao.insertProductBatchStock(batchBean);
			}
			//如果存在记录，则更新商品批次库存
			else{
				batchBean.setBatch_serial(retBatchBean.getBatch_serial());
				batchBean.setIncommint_price(retBatchBean.getIncommint_price());
				batchBean.setOrder_current_sum(retBatchBean.getOrder_current_sum()+changeCount);//当前数量
				batchBean.setStock_sum(retBatchBean.getStock_sum()+changeCount);//总数量
				outgoingOrderDao.updateProductBatchStockById(batchBean);
				
				//调拨等出库时，调拨单据
				//批次号为最新入库的商品批次，无需用户输入
				//成本单价为为最新入库的商品批次的成本单价，无需用户输入
				//变更数量为实际调拨数量 （必须小于商品批次库存表.当前数量）
				incommingDetailBean.setBatch_serial(retBatchBean.getBatch_serial());
				incommingDetailBean.setUnit_price(retBatchBean.getIncommint_price());
			}
			//更新商品批次库存 end
			//更新商品实时库存 begin
			//查询是否存在该商品库存记录
			ProductRealStockBean realStockBean=new ProductRealStockBean();
			realStockBean.setCommunity_id(incommingDetailBean.getCommunity_id());
			realStockBean.setProduct_id(incommingDetailBean.getProduct_id());
			realStockBean.setPre_incomming_price(incommingDetailBean.getUnit_price());//如果实时库存表中不存在该商品记录，则当前进货价格为前台输入的价格
			realStockBean.setReal_stock_sum(changeCount);
			ProductRealStockBean retBean=outgoingOrderDao.getProductStockById(realStockBean);
			//如果不存在记录，则插入记录
			if(retBean==null){
				outgoingOrderDao.insertProductStock(realStockBean);
			}
			//如果存在记录，则更新商品库存
			else{
				stock_sum=retBean.getReal_stock_sum()+changeCount;//记录当前该商品实时库存
				realStockBean.setPre_incomming_price(retBean.getPre_incomming_price());
				realStockBean.setReal_stock_sum(changeCount);
				outgoingOrderDao.updateProductStockById(realStockBean);//
			}
			//更新商品实时库存 end
			
			//更新该商品所有批次的总数量为实时库存里面的数量
			ProductBatchStockBean allBean=new ProductBatchStockBean();
			allBean.setProduct_id(incommingDetailBean.getProduct_id());
			allBean.setCommunity_id(incommingDetailBean.getCommunity_id());
			allBean.setStock_sum(stock_sum);
			outgoingOrderDao.updateProductBatchStockById(allBean);
			
			//更新库存变动日志
			StockChangeLogBean changeLogBean=new StockChangeLogBean();//
			changeLogBean.setProduct_id(incommingDetailBean.getProduct_id());//商品ID
			changeLogBean.setCommunity_id(incommingDetailBean.getCommunity_id());//小区ID
			changeLogBean.setCreate_by(incommingDetailBean.getCreate_by());//创建人ID
			changeLogBean.setOrder_current_sum(changeCount);//操作数量
			changeLogBean.setCreate_type(2);//创建人类型（1=用户，2=管理后台）
			changeLogBean.setIp(incommingDetailBean.getIp());//ip
			changeLogBean.setLog_type(incommingDetailBean.getStock_type());
			changeLogBean.setStock_sum(stock_sum);
			changeLogBean.setBatch_serial(incommingDetailBean.getBatch_serial());
			
			outgoingOrderDao.insertChangeLog(changeLogBean);
			
			//
			outgoingOrderDao.insertIncommingDetail(incommingDetailBean);
		}
	}

	/**
	 * 查询需要添加的商品
	 * 
	 * @param map
	 * @return
	 */
	public List<ProductDouble> queryGoods(Map<String, Object> map) {
		return outgoingOrderDao.queryGoods(map);
	}

	/**
	 * 删除进货，调拨入，退货，损耗，调拨出单据汇总
	 * 
	 * @param headerBean
	 *            指定单据汇总
	 */
	public void delIncommintHeader(IncommingHeaderBean headerBean) {
		outgoingOrderDao.delIncommintHeader(headerBean);
	}

	/**
	 * 删除进货，调拨入，退货，损耗，调拨出单据明细
	 * 
	 * @param detailBean
	 */
	public void delIncommintDetail(IncommingDetailBean detailBean) {
		outgoingOrderDao.delIncommintDetail(detailBean);
	}
	
	/**
	 * 获取小区进货最大编号
	 * @param map
	 * @return
	 */
	public String getMaxNo(Map<String,Object> map) {
		return outgoingOrderDao.getMaxNo(map);
	}
	
	
	public ProductRealStockBean getProductStockById(ProductRealStockBean realStockBean) {
		return outgoingOrderDao.getProductStockById(realStockBean);
	}
	
	public void insertProductStock(ProductRealStockBean realStockBean) {
		outgoingOrderDao.insertProductStock(realStockBean);
	}
	
	public void updateProductStockById(ProductRealStockBean realStockBean) {
		outgoingOrderDao.updateProductStockById(realStockBean);
	}
	
	
	public ProductBatchStockBean getProductBatchStockById(ProductBatchStockBean batchBean) {
		return outgoingOrderDao.getProductBatchStockById(batchBean);
	}
	
	public void insertProductBatchStock(ProductBatchStockBean batchStockBean) {
		outgoingOrderDao.insertProductBatchStock(batchStockBean);
	}
	
	public void updateProductBatchStockById(ProductBatchStockBean batchStockBean) {
		outgoingOrderDao.updateProductBatchStockById(batchStockBean);
	}
}
