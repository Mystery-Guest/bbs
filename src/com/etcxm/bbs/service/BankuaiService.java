package com.etcxm.bbs.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etcxm.bbs.dao.BankuaiDao;
import com.etcxm.bbs.dao.TieziDao;
import com.etcxm.bbs.dao.ZhutiDao;
import com.etcxm.bbs.model.Bankuai;
import com.etcxm.bbs.model.PageBean;
import com.etcxm.bbs.model.Tiezi;
import com.etcxm.bbs.tool.PublicStatic;
import com.etcxm.bbs.tool.Tool;

@Service("bankuaiService")
public class BankuaiService{
	@Autowired
	BankuaiDao bankuaiDao;
	
	@Autowired
	TieziDao tieziDao;
	
	@Autowired
	ZhutiDao zhutiDao;
	
	public PageBean<Bankuai> findpage(Bankuai bankuai,PageBean<Bankuai> page) {
		page.setBean(bankuai);
		int count = bankuaiDao.findpagecount(page);
		page.setTotalRecords(count);
		List<Bankuai> list = bankuaiDao.findpage(page);
		page.setList(list);
		return page ;
	}
	
	public List<Bankuai> select(Bankuai bankuai) {
		return bankuaiDao.select(bankuai);
	}
	

	public void insert(Bankuai bankuai) {
		if(bankuai.getOrderBy()==null){
			int maxorderby= bankuaiDao.findmaxorderby(bankuai.getParentId());
			bankuai.setOrderBy(maxorderby+1);
		}
		bankuai.setCreateTime(Tool.getyyyyMMddHHmmss());
		bankuaiDao.insert(bankuai);
	}

	public String delete(Integer id) {
		String result="1";
		Bankuai bankuai=new Bankuai();
		bankuai.setParentId(id);
		List<Bankuai> list = bankuaiDao.select(bankuai);
		if(list.size()>0){
			result="0";
		}else{
			Tiezi tiezi=new Tiezi();
			tiezi.setBankuaiId(id);
			List<Tiezi> tiezilist = tieziDao.select(tiezi);
			if(tiezilist.size()>0){
				result="2";
			}else{
				zhutiDao.deletebyzhuti(id);
				bankuaiDao.delete(id);
			}
		}
		return result;
	}
	public void update(Bankuai bankuai) {
		if(bankuai.getOrderBy()==null){
			int maxorderby= bankuaiDao.findmaxorderby(0);
			bankuai.setOrderBy(maxorderby+1);
		}
		bankuaiDao.update(bankuai);
		
	}
	public Bankuai findbyid(Bankuai bankuai) {
		List<Bankuai> list= bankuaiDao.find(bankuai);
		if(list.size()>0){
			bankuai=list.get(0);
		}
		return bankuai;
	}
	
	public List<Bankuai> findbyparentId(Bankuai bankuai) {
		bankuai.setParentId(0);
		List<Bankuai> list= bankuaiDao.find(bankuai);
		return list;
	}


	public List<Bankuai> find(Bankuai bankuai) {
		// TODO Auto-generated method stub
		return bankuaiDao.find(bankuai);
	}


	public Bankuai findbyid(String bankuaiid) {
		List<Bankuai> list=new ArrayList<Bankuai>();
//		Bankuai bankuai=new Bankuai();
//		bankuai.setId(Integer.parseInt(bankuaiid));
//		List<Bankuai> find = bankuaiDao.find(bankuai);
//		if(find.size()>0){
//			list.add(find.get(0));
//			Integer parentId = find.get(0).getParentId();
//			if(0!=parentId){
//				bankuai=new Bankuai();
//				bankuai.setId(parentId);
//				find = bankuaiDao.find(bankuai);
//				if(find.size()>0){
//					list.add(0,find.get(0));
//				}
//			}
//		}
		
		Bankuai bankuai=bankuaiDao.findfbankuaibyid(Integer.parseInt(bankuaiid));
		return bankuai;
	}
	
	public void getallbankuai(){
		List<Bankuai> list = bankuaiDao.select(null);
		Map<String, Integer> map=new HashMap<String, Integer>();
		for (Bankuai bankuai2 : list) {
			String yuming = bankuai2.getYuming();
			Integer id = bankuai2.getId();
			map.put(yuming, id);
		}
		PublicStatic.bankuai=map;
	}

}
