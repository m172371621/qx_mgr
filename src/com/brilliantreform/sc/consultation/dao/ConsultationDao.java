package com.brilliantreform.sc.consultation.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.brilliantreform.sc.comment.po.CommentTask;
import com.brilliantreform.sc.consultation.po.Consultation;

@Repository("consultationDao")
public class ConsultationDao {
	private static Logger logger = Logger.getLogger(ConsultationDao.class);

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	@SuppressWarnings("unchecked")
	public List<Consultation> getConsultationList(Integer id)
	{
		return (List<Consultation>)sqlMapClient.queryForList("consultation.getConsultationList", id);
	}
	
	public Integer getCidBySid(Integer sid)
	{
		return (Integer)sqlMapClient.queryForObject("consultation.getCidBySid", sid);
	}
	
	public Consultation getConsultation(Integer cid)
	{
		return (Consultation)sqlMapClient.queryForObject("consultation.getConsultation", cid);
	}
}
