package com.brilliantreform.sc.core.soap;

import javax.jws.WebService;




@WebService(endpointInterface = "com.brilliantreform.sc.core.soap.SCBaseWS", serviceName = "scBaseInt")
public class SCBaseWSImpl implements SCBaseWS {
	

	@Override
	public String getSCInfo(String authentication,String serviceId,String params){

		return "{id:'1000000000',user_name:'admin', password:'123456', user_type:'0', create_time: '2013-08-12 16:25:24',status: '1',nick_name: 'admin',email:'824346400@qq.com', phone: '18900001111',wy_authentication: '1', photo: ''}";
	}

}
