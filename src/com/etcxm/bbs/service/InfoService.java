package com.etcxm.bbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etcxm.bbs.dao.InfoDao;
import com.etcxm.bbs.model.Info;

@Service("infoService")
public class InfoService{
	@Autowired
	InfoDao infoDao;
	
	public void update(Info info) {
		infoDao.update(info);
	}
	public Info find() {
		int id=1;
		return infoDao.find(id);
	}
	

}
