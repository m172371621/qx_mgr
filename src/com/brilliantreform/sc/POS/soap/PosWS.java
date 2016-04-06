package com.brilliantreform.sc.POS.soap;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface PosWS {
	
	@WebResult(name="result")
	public   String getProduct( @WebParam(name = "authentication") String authentication,
			 @WebParam(name = "params") String params);
	
	@WebResult(name="result")
	public   String posLogin( @WebParam(name = "authentication") String authentication,
			 @WebParam(name = "params") String params);
	
	@WebResult(name="result")
	public   String posOrder( @WebParam(name = "authentication") String authentication,
			 @WebParam(name = "params") String params);
	
	@WebResult(name="result")
	public   String searchProduct( @WebParam(name = "authentication") String authentication,
			 @WebParam(name = "params") String params);
	
	/**
	 * 生成POS数据暂存，为扫码支付提供数据
	 * @param authentication
	 * @param params
	 * @return
	 */
	@WebResult(name="result")
	public   String addTempData( @WebParam(name = "authentication") String authentication,
			 @WebParam(name = "params") String params);
}
