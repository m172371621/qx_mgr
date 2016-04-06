package com.brilliantreform.sc.circle.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brilliantreform.sc.circle.dao.CircleDao;
import com.brilliantreform.sc.circle.po.CircleFriend;
import com.brilliantreform.sc.circle.po.CircleMedia;
import com.brilliantreform.sc.circle.po.CircleReply;
import com.brilliantreform.sc.circle.po.CircleSaid;

@Service("circleService")
public class CircleService {

	private static Logger logger = Logger.getLogger(CircleService.class);

	@Autowired
	private CircleDao circleDao;


	@SuppressWarnings("unchecked")
	public List<CircleFriend> getNeighbors(Integer cid,String key_word,Integer begin,Integer size) {
		
		int count = this.countNeighbors(cid);
		int random = count - 10 < 0 ? 1 : count - 10;
		random =  RandomUtils.nextInt(random);
		
		Map map = new HashMap();
		if(StringUtils.isBlank(key_word))
		{
			map.put("cid", cid);
			map.put("begin", random);
			map.put("size", size);
		}else
		{
			try {
				key_word=URLDecoder.decode(key_word, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put("key_word", key_word);
			map.put("begin", begin);
			map.put("size", size);
		}
			
		return (List<CircleFriend>) circleDao.getNeighbors(map);
	}
	
	/**
	 * 
	 * @param me_id
	 * @param user_id
	 * @return 0未关注 1 已关注 2 被关注 3 相互关注
	 */
	@SuppressWarnings("unchecked")
	public int getRelations(Integer me_id,Integer user_id) {

		Map map = new HashMap();
		map.put("me_id", me_id);
		map.put("user_id", user_id);
		
		List<Integer> list = circleDao.getRelations(map);
		
		int type = 0;
		
		if(list.get(0) == 0 && list.get(1) == 0)
		{
			type = 0;
		}else if(list.get(0) == 1 && list.get(1) == 0)
		{
			type = 1;
		}else if(list.get(0) == 0 && list.get(1) == 1)
		{
			type = 2;
		}else if(list.get(0) == 1 && list.get(1) == 1)
		{
			type = 3;
		}
		
		return type;
	}

	public Integer countNeighbors(Integer cid) {
		return (Integer) circleDao.countNeighbors(cid);
	}

	public void addFriend(CircleFriend circleFriend) {
		circleDao.addFriend(circleFriend);
	}

	@SuppressWarnings("unchecked")
	public void delFriend(Integer friend_id , Integer me_id) {
		Map map = new HashMap();
		map.put("user_id", friend_id);
		map.put("me_id", me_id);
		
		circleDao.delFriend(map);
	}


	@SuppressWarnings("unchecked")
	public List<CircleFriend> getFriend(Integer user_id,Integer begin,Integer size) {
		
		Map map = new HashMap();
		map.put("user_id", user_id);
		map.put("begin", begin);
		map.put("size", size);
		
		return (List<CircleFriend>) circleDao.getFriend(map);
	}
	
	@SuppressWarnings("unchecked")
	public List<CircleFriend> getWhosFriend(Integer user_id,Integer begin,Integer size)
	{
		Map map = new HashMap();
		map.put("user_id", user_id);
		map.put("begin", begin);
		map.put("size", size);
		
		return (List<CircleFriend>) circleDao.getWhosFriend(map);
	}

	public void createSaid(CircleSaid circleSaid) {
		
		int saidId = circleDao.createSaid(circleSaid);
		
		String spics = circleSaid.getSaid_pics();
		
		if( spics != null && spics.length() > 0)
		{
			String[] pics = spics.split("\\|"); 
			CircleMedia circleMedia = new CircleMedia();
			circleMedia.setMedia_type("JPG");
			circleMedia.setSaid_id(saidId);
			
			for(String url : pics)
			{
				circleMedia.setMedia_url(url);
				this.insertMedia(circleMedia);
			}
		}
		
	}

	public void insertMedia(CircleMedia circleMedia) {
		circleDao.insertMedia(circleMedia);
	}

	public void delSaid(Integer said_id) {
		
		circleDao.delSaid(said_id);
		circleDao.delMedia(said_id);
		circleDao.delReply(said_id);
	}
	
	public void delReply(Integer reply_id) {
		
		circleDao.delReplyById(reply_id);

	}

	public void createReply(CircleReply circleReply) {
		circleDao.createReply(circleReply);
	}


	@SuppressWarnings("unchecked")
	public List<CircleSaid> getUserSaid(Integer user_id,Integer begin,Integer size) {
		
		Map map = new HashMap();
		map.put("user_id", user_id);
		map.put("begin", begin);
		map.put("size", size);
		
		List<CircleSaid> list = (List<CircleSaid>) circleDao.getUserSaid(map);
		
		for(CircleSaid circleSaid : list)
		{
			List<CircleMedia> mlist = this.getSaidMedia(circleSaid.getSaid_id());
			String pics = "";
			for(CircleMedia circleMedia : mlist)
			{
				pics += (circleMedia.getMedia_url() + "|");
			}
			if(pics.length()>0)
			{
				pics = pics.substring(0,pics.length()-1);
				circleSaid.setSaid_pics(pics);
			}
		}
		
		
		return list;
	}


	@SuppressWarnings("unchecked")
	public List<CircleSaid> getFriendSaid(Integer user_id,Integer cid,Integer begin,Integer size) {
		
		Map map = new HashMap();
		map.put("user_id", user_id);
		map.put("cid", cid);
		map.put("begin", begin);
		map.put("size", size);
		
		List<CircleSaid> list = (List<CircleSaid>) circleDao.getFriendSaid(map);
		
		for(CircleSaid circleSaid : list)
		{
			List<CircleMedia> mlist = this.getSaidMedia(circleSaid.getSaid_id());
			String pics = "";
			for(CircleMedia circleMedia : mlist)
			{
				pics += (circleMedia.getMedia_url() + "|");
			}
			if(pics.length()>0)
			{
				pics = pics.substring(0,pics.length()-1);
				circleSaid.setSaid_pics(pics);
			}
		}
		
		return list;
	}


	public List<CircleMedia> getSaidMedia(Integer said_id) {
		return (List<CircleMedia>) circleDao.getSaidMedia(said_id);
	}


	public List<CircleReply> getSaidReply(Integer said_id) {
		return (List<CircleReply>) circleDao.getSaidReply(said_id);
	}


	@SuppressWarnings("unchecked")
	public List<Map> getSaidGood(Integer said_id) {
		return (List<Map>) circleDao.getSaidGood(said_id);
	}

	public Integer countReply(Integer said_id) {
		return (Integer) circleDao.countReply(said_id);
	}
}
