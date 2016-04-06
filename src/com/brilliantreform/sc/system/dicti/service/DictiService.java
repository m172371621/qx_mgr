package com.brilliantreform.sc.system.dicti.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.system.dicti.dao.DictiDao;
import com.brilliantreform.sc.system.dicti.po.DictiVo;

@Service("dictiService")
public class DictiService {

	private static Logger logger = Logger.getLogger(DictiService.class);

	@Autowired
	private DictiDao dictiDao;
	
	public List<DictiVo> getDictiType()
	{
		return dictiDao.getDictiType();
	}
	
	public List<DictiVo> getDictiValue(int id)
	{
		return dictiDao.getDictiValue(id);
	}
}
