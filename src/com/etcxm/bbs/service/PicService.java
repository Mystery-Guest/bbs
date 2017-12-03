package com.etcxm.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etcxm.bbs.dao.PicDao;
import com.etcxm.bbs.dao.UserDao;
import com.etcxm.bbs.model.Pic;

@Service
public class PicService{
	@Autowired
	PicDao PicDao;
	@Autowired
	UserDao userDao;
	
	
	public void insert(Pic Pic) {
		PicDao.insert(Pic);
	}

	public String delete(Integer id) {
		String result="1";
		PicDao.delete(id);
		return result;
	}
	
	public String deletebytieziid(Integer tieziid) {
		String result="1";
		PicDao.deletebytieziid(tieziid);
		return result;
	}
	
	
	
	public void update(Pic Pic) {
		PicDao.update(Pic);
	}

	public List<Pic> selectbyindex(Pic Pic) {
		return PicDao.selectbyindex(Pic);
	}
	

}
