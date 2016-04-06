package com.brilliantreform.sc.core.soap;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface SCBaseWS {
	
	@WebResult(name="result")
	public   String getSCInfo( @WebParam(name = "authentication") String authentication,
			 @WebParam(name = "serviceId") String serviceId,
			 @WebParam(name = "params") String params);
}
