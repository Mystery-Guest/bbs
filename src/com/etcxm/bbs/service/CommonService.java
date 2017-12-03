package com.etcxm.bbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etcxm.bbs.dao.CommonDao;
import com.etcxm.bbs.dao.InnerLinkDao;
import com.etcxm.bbs.dao.StopIpDao;
import com.etcxm.bbs.dao.TieziDao;
import com.etcxm.bbs.model.InnerLink;

@Service("commonService")
public class CommonService{
	@Autowired
	CommonDao commonDao;
	
	@Autowired
	TieziDao tieziDao;
	
	@Autowired
	InnerLinkDao innerLinkDao;
	
	@Autowired
	StopIpDao stopIpDao;
	
	//查询最新帖子,查询后放入缓存
	public void ztiezi() {
		//获取站内链接
		innerLinkDao.selectmap();
		
		//查询禁止的ip
		stopIpDao.staticstopip();
		
		//首页统计
		commonDao.indextongji();
		
		//首页最新帖子列表
		commonDao.ztiezi();
		
		//更新最后发帖的回复id
		tieziDao.updatetiezilasthuifuid();
	}
	

}
