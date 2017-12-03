package com.etcxm.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etcxm.bbs.dao.JifenDao;
import com.etcxm.bbs.dao.UserDao;
import com.etcxm.bbs.model.Jifen;
import com.etcxm.bbs.model.PageBean;
import com.etcxm.bbs.tool.Tool;

@Service("jifenService")
public class JifenService{
	@Autowired
	JifenDao JifenDao;
	@Autowired
	UserDao userDao;
	
	public PageBean<Jifen> findpage(Jifen Jifen,PageBean<Jifen> page) {
		page.setBean(Jifen);
		int count = JifenDao.findpagecount(page);
		page.setTotalRecords(count);
		List<Jifen> list = JifenDao.findpage(page);
		page.setList(list);
		return page ;
	}
	
	public void insert(Jifen Jifen) {
		Jifen.setCreatetime(Tool.getyyyyMMddHHmmss());
		JifenDao.insert(Jifen);
		userDao.updatejifen(Jifen.getUserid());
	}

	public String delete(Integer id) {
		String result="1";
		JifenDao.delete(id);
		return result;
	}
	public void update(Jifen Jifen) {
		JifenDao.update(Jifen);
	}

	public List<Jifen> find(Jifen Jifen) {
		return JifenDao.find(Jifen);
	}
	//查询当天积分次数，只需要传入type
	public int findnowcount(Jifen Jifen) {
		return JifenDao.findnowcount(Jifen);
	}
	

}
