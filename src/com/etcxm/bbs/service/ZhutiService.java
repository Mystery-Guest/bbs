package com.etcxm.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etcxm.bbs.dao.ZhutiDao;
import com.etcxm.bbs.model.Zhuti;
import com.etcxm.bbs.tool.Tool;

@Service
public class ZhutiService{
	@Autowired
	ZhutiDao zhutiDao;
	
	public void insert(Zhuti zhuti) {
		zhuti.setCreatetime(Tool.getyyyyMMddHHmmss());
		if(zhuti.getOrderby()==null){
			int maxorderby= zhutiDao.findmaxorderby(zhuti.getBankuaiId());
			zhuti.setOrderby(maxorderby+1);
		}
		zhutiDao.insert(zhuti);
	}

	public String delete(Integer id) {
		String result="1";
		zhutiDao.delete(id);
		return result;
	}
	public void update(Zhuti zhuti) {
		zhuti.setCreatetime(Tool.getyyyyMMddHHmmss());
		if(zhuti.getOrderby()==null){
			int maxorderby= zhutiDao.findmaxorderby(0);
			zhuti.setOrderby(maxorderby+1);
		}
		zhutiDao.update(zhuti);
	}
	public Zhuti findbyid(Zhuti zhuti) {
		List<Zhuti> list= zhutiDao.find(zhuti);
		if(list.size()>0){
			zhuti=list.get(0);
		}
		return zhuti;
	}
	
	public List<Zhuti> find(Zhuti zhuti) {
		return zhutiDao.find(zhuti);
	}


}
