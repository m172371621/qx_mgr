package com.brilliantreform.sc.consultation.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.comment.service.ComService;
import com.brilliantreform.sc.consultation.dao.ConsultationDao;
import com.brilliantreform.sc.consultation.po.Consultation;

@Service("consultationService")
public class ConsultationService {
	private static Logger logger = Logger.getLogger(ConsultationService.class);

	@Autowired
	private ConsultationDao consultationDao;

	@Autowired
	private ComService comService;

	/**
	 * 获取服务二级列表
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Consultation> getConsultationList(Integer id) {
		
		logger.info("into ConsultationService getConsultationList:" + id);
		
		List list = consultationDao.getConsultationList(id);
		
		for (Object o : list) {
			
			Consultation c = (Consultation) o;
			
			Map comment =  comService.getCommentLevel(c.getConsultation_id(),null);
			
			c.setComment_level((Integer)comment.get("avg"));
			c.setComment_count((Integer)comment.get("count"));

		}
		
		logger.info("ConsultationService getConsultationList:" + list);
		
		return list;
	}
	
	public Integer getCidBySid(Integer sid)
	{
		logger.info("into ConsultationService getCidBySid:" + sid);
		return consultationDao.getCidBySid(sid);
	}
	
	public Consultation getConsultation(Integer cid)
	{
		return consultationDao.getConsultation(cid);
	}
}
