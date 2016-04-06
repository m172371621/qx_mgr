package com.brilliantreform.sc.incomming.service;

import java.util.*;

import com.brilliantreform.sc.user.mgrpo.MgrUser;
import com.brilliantreform.sc.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brilliantreform.sc.incomming.dao.IncommingOrderDao;
import com.brilliantreform.sc.incomming.po.IncommingDetailBean;
import com.brilliantreform.sc.incomming.po.IncommingHeaderBean;
import com.brilliantreform.sc.incomming.po.ProductBatchStockBean;
import com.brilliantreform.sc.incomming.po.ProductRealStockBean;
import com.brilliantreform.sc.incomming.po.StockChangeLogBean;
import com.brilliantreform.sc.product.po.Product;

@Service("incommingOrderService")
public class IncommingOrderService {

	@Autowired
	private IncommingOrderDao incommingOrderDao;

	/**
	 * 查询进货，调拨入，退货，损耗，调拨出单据汇总
	 * 
	 * @param headerBean
	 * @return
	 */
	public List<IncommingHeaderBean> getIncommingHeader(Map<String, Object> map) {
		return incommingOrderDao.getIncommingHeader(map);
	}

	/**
	 * 查询进货，调拨入，退货，损耗，调拨出单据汇总总数
	 * 
	 * @param headerBean
	 * @return
	 */
	public Integer getIncommingHeaderCount(Map<String, Object> map) {
		return incommingOrderDao.getIncommingHeaderCount(map);
	}

	/**
	 * 查询进货，调拨入，退货，损耗，调拨出单据明细
	 * 
	 * @param detailBean
	 * @return
	 */
	public List<IncommingDetailBean> getIncommintDetail(
			IncommingDetailBean detailBean) {
		return incommingOrderDao.getIncommintDetail(detailBean);
	}

	/**
	 * 查询进货，调拨入，退货，损耗，调拨出单据明细总数
	 * 
	 * @param detailBean
	 * @return
	 */
	public Integer getIncommintDetailCount(IncommingDetailBean detailBean) {
		return incommingOrderDao.getIncommintDetailCount(detailBean);
	}

	/**
	 * 插入进货，调拨入，退货，损耗，调拨出单据汇总
	 * 
	 * @param headerBean
	 * @return
	 */
	public Integer insertIncommingHeader(IncommingHeaderBean headerBean) {
		return incommingOrderDao.insertIncommingHeader(headerBean);
	}
	
	/**
	 * 修改汇总信息
	 * @param headerBean
	 */
	public void updateHeaderInfo(IncommingHeaderBean headerBean) {
		incommingOrderDao.updateHeaderInfo(headerBean);
	}

	/**
	 * 插入查询进货，调拨入，退货，损耗，调拨出单据明细
	 * 
	 * @param detailList
	 */
	@Transactional
	public void insertIncommingDetail(List<IncommingDetailBean> detailList) {
		for (IncommingDetailBean incommingDetailBean : detailList) {
			incommingOrderDao.insertIncommingDetail(incommingDetailBean);
			//根据单据类型做变动数量是正还是负1=进货，2=调拨入，3=退货，4=损耗，5=调拨出
			Double changeCount=(incommingDetailBean.getStock_type()>2&&incommingDetailBean.getStock_type()<6)?(-1*incommingDetailBean.getChange_count()):incommingDetailBean.getChange_count();
			Double stock_sum=incommingDetailBean.getChange_count();
			//更新商品实时库存 begin
			//查询是否存在该商品库存记录
			ProductRealStockBean realStockBean=new ProductRealStockBean();
			realStockBean.setCommunity_id(incommingDetailBean.getCommunity_id());
			realStockBean.setProduct_id(incommingDetailBean.getProduct_id());
			realStockBean.setPre_incomming_price(incommingDetailBean.getUnit_price());//如果实时库存表中不存在该商品记录，则当前进货价格为前台输入的价格
			realStockBean.setReal_stock_sum(changeCount);
			ProductRealStockBean retBean=incommingOrderDao.getProductStockById(realStockBean);
			//如果不存在记录，则插入记录
			if(retBean==null){
				incommingOrderDao.insertProductStock(realStockBean);
			}
			//如果存在记录，则更新商品库存
			else{
				stock_sum=retBean.getReal_stock_sum()+changeCount;//记录当前该商品实时库存
				realStockBean.setPre_incomming_price(retBean.getPre_incomming_price());
				incommingOrderDao.updateProductStockById(realStockBean);//
			}
			//更新商品实时库存 end
			
			//更新商品批次库存 begin
			ProductBatchStockBean batchBean=new ProductBatchStockBean();
			batchBean.setBatch_serial(incommingDetailBean.getBatch_serial());
			batchBean.setCommunity_id(incommingDetailBean.getCommunity_id());
			batchBean.setProduct_id(incommingDetailBean.getProduct_id());
			batchBean.setIncommint_price(incommingDetailBean.getUnit_price());
			batchBean.setStock_sum(changeCount);
			batchBean.setOrder_current_sum(incommingDetailBean.getChange_count());
			ProductBatchStockBean retBatchBean=incommingOrderDao.getProductBatchStockById(batchBean);
			//如果不存在记录，则插入记录
			if(retBatchBean==null){
				incommingOrderDao.insertProductBatchStock(batchBean);
			}
			//如果存在记录，则更新商品批次库存
			else{
				batchBean.setIncommint_price(retBatchBean.getIncommint_price());
				batchBean.setOrder_current_sum(changeCount);//当前数量
				batchBean.setStock_sum(stock_sum);//总数量
				incommingOrderDao.updateProductBatchStockById(batchBean);
				
			}
			//更新该商品所有批次的总数量为实时库存里面的数量
			ProductBatchStockBean allBean=new ProductBatchStockBean();
			allBean.setProduct_id(incommingDetailBean.getProduct_id());
			allBean.setCommunity_id(incommingDetailBean.getCommunity_id());
			allBean.setStock_sum(stock_sum);
			incommingOrderDao.updateProductBatchStockById(allBean);
			
			//更新商品批次库存 end
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
			
			incommingOrderDao.insertChangeLog(changeLogBean);
		}
	}

	public void insertIncommingDetail(int stock_type, int community_id, MgrUser user, String ip, int product_id, double change_count, double unit_price) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("community_id", community_id);
		String maxNo = getMaxNo(map);
		maxNo = maxNo == null ? "000000" : maxNo;
		IncommingHeaderBean headerBean = new IncommingHeaderBean();
		headerBean.setCommunity_id(community_id);
		String str_cid = community_id < 10 ? ("00000" + community_id)
				: community_id < 100 ? ("0000" + community_id) : community_id < 1000 ? ("000" + community_id)
				: community_id < 10000 ? ("00" + community_id)
				: community_id < 10000 ? ("0" + community_id) : String
				.valueOf(community_id);
		int int_no = (Integer.parseInt(maxNo) + 1);
		String str_no = int_no < 10 ? ("00000" + int_no)
				: int_no < 100 ? ("0000" + int_no)
				: int_no < 1000 ? ("000" + int_no)
				: int_no < 10000 ? ("00" + int_no)
				: community_id < 10000 ? ("0" + int_no)
				: String.valueOf(int_no);
		String preHead = stock_type == 1 ? "JH" : stock_type == 2 ? "DR":"";
		String stockchange_header_no = preHead + str_cid + str_no;
		headerBean.setStockchange_header_no(stockchange_header_no);
		headerBean.setStock_type(stock_type);// 3=退货，4=损耗，5=调拨出
		headerBean.setState(1);// 1=未确认
		headerBean.setCreate_by(user.getLoginname());

		// 添加汇总数据后获取汇总ID，使用此ID和明细关联
		Integer stockchange_header_id = insertIncommingHeader(headerBean);
		String batch_serial = String.valueOf((new Date()).getTime());

		IncommingDetailBean detailBean = new IncommingDetailBean();
		detailBean.setProduct_id(product_id);
		detailBean.setChange_count(change_count);
		detailBean.setUnit_price(unit_price);
		detailBean.setStockchange_header_id(stockchange_header_id);
		detailBean.setBatch_serial(batch_serial);
		detailBean.setStock_type(stock_type);// 进货=1
		detailBean.setCreate_by(user.getLoginname());
		detailBean.setCommunity_id(community_id);
		detailBean.setCreate_by(user.getLoginname());
		detailBean.setIp(ip);

		List<IncommingDetailBean> detailList = new ArrayList<IncommingDetailBean>();
		detailList.add(detailBean);
		insertIncommingDetail(detailList);
	}

	/**
	 * 查询需要添加的商品
	 * 
	 * @param map
	 * @return
	 */
	public List<Product> queryGoods(Map<String, Object> map) {
		return incommingOrderDao.queryGoods(map);
	}
	
	public List<Product> queryGoods_zj(Map<String, Object> map) {
		return incommingOrderDao.queryGoods_zj(map);
	}

	/**
	 * 删除进货，调拨入，退货，损耗，调拨出单据汇总
	 * 
	 * @param headerBean
	 *            指定单据汇总
	 */
	public void delIncommintHeader(IncommingHeaderBean headerBean) {
		incommingOrderDao.delIncommintHeader(headerBean);
	}

	/**
	 * 删除进货，调拨入，退货，损耗，调拨出单据明细
	 * 
	 * @param detailBean
	 */
	public void delIncommintDetail(IncommingDetailBean detailBean) {
		incommingOrderDao.delIncommintDetail(detailBean);
	}
	
	/**
	 * 获取小区进货最大编号
	 * @param map
	 * @return
	 */
	public String getMaxNo(Map<String,Object> map) {
		return incommingOrderDao.getMaxNo(map);
	}
	
	
	public ProductRealStockBean getProductStockById(ProductRealStockBean realStockBean) {
		return incommingOrderDao.getProductStockById(realStockBean);
	}
	
	public void insertProductStock(ProductRealStockBean realStockBean) {
		incommingOrderDao.insertProductStock(realStockBean);
	}
	
	public void updateProductStockById(ProductRealStockBean realStockBean) {
		incommingOrderDao.updateProductStockById(realStockBean);
	}
	
	
	public ProductBatchStockBean getProductBatchStockById(ProductBatchStockBean batchBean) {
		return incommingOrderDao.getProductBatchStockById(batchBean);
	}
	
	public void insertProductBatchStock(ProductBatchStockBean batchStockBean) {
		incommingOrderDao.insertProductBatchStock(batchStockBean);
	}
	
	public void updateProductBatchStockById(ProductBatchStockBean batchStockBean) {
		incommingOrderDao.updateProductBatchStockById(batchStockBean);
	}

    public List<Map> searchIncommingHeader(Map param) {
        return incommingOrderDao.searchIncommingHeader(param);
    }

    public int searchIncommingHeaderCount(Map param) {
        return incommingOrderDao.searchIncommingHeaderCount(param);
    }

    public List<Map> searchInCommingDetail(Map param) {
        return incommingOrderDao.searchInCommingDetail(param);
    }

    public int searchInCommingDetailCount(Map param) {
        return incommingOrderDao.searchInCommingDetailCount(param);
    }
}
