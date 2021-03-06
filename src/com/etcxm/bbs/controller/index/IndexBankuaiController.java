package com.etcxm.bbs.controller.index;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.etcxm.bbs.model.Bankuai;
import com.etcxm.bbs.model.Link;
import com.etcxm.bbs.model.PageBean;
import com.etcxm.bbs.model.Tiezi;
import com.etcxm.bbs.model.User;
import com.etcxm.bbs.model.Zhuti;
import com.etcxm.bbs.service.BankuaiService;
import com.etcxm.bbs.service.LinkService;
import com.etcxm.bbs.service.TieziService;
import com.etcxm.bbs.service.UserService;
import com.etcxm.bbs.service.ZhutiService;
import com.etcxm.bbs.tool.PublicStatic;
import com.etcxm.bbs.tool.Tool;
/*
 * 板块的查询
 * 
 */
@Controller
@RequestMapping("/bk")
public class IndexBankuaiController {
	@Autowired
	BankuaiService bankuaiService;
	
	@Autowired
	TieziService tieziService;
	
	@Autowired
	ZhutiService zhutiService;
	
	@Autowired
	LinkService linkService;
	
	@Autowired
	UserService userService;

	private static Logger log = Logger.getLogger(IndexBankuaiController.class);

	//首页查询
	@RequestMapping("index.do")
	public String findAllWhere(HttpServletRequest request, Model model,String bankuaiid,String zhutiid,PageBean<Tiezi> page,String orderby) {
		
		
		
//		long currentTimeMillis = System.currentTimeMillis();
		//查询当前板块
		Bankuai bankuai= bankuaiService.findbyid(bankuaiid);
		
		//判断访问权限
		User user=(User)request.getSession().getAttribute(PublicStatic.USER);
		boolean flag=userService.isfind(bankuai.getFindgroup(),user);
		if(!flag){
			return "redirect:/topage/quanxian.do";
		}
		
		
		//查询主题列表
		Zhuti zhuti=new Zhuti();
		zhuti.setBankuaiId(Integer.parseInt(bankuaiid));
		List<Zhuti> zhutilist = zhutiService.find(zhuti);
		
		//查询板块的帖子
		Tiezi tiezi=new Tiezi();
		tiezi.setBankuaiId(Integer.parseInt(bankuaiid));
		tiezi.setOrderby(orderby);
		if(zhutiid!=null&&!zhutiid.equals("0")){
			tiezi.setZhutiid(zhutiid);
		}else{
			zhutiid="0";
		}
		//查询列表
		tiezi.setIsuse("1");
		page = tieziService.findpagebybankuanid(tiezi, page);
		
//		long currentTimeMillis = System.currentTimeMillis();
		//今日的帖子总数
		int nowcount=tieziService.findnowcount(bankuaiid);
		//查询置顶
		List<Tiezi> zhidinglist= tieziService.findzhiding(bankuaiid);
		
		//查询友情链接
		Link link=new Link();
		link.setIsshow("1");
		link.setBankuaiid(Integer.parseInt(bankuaiid));
		List<Link> linklist = linkService.find(link);
		model.addAttribute("linklist", linklist);
		
		model.addAttribute("page", page);
		model.addAttribute("bankuaiid", bankuaiid);
		model.addAttribute("zhutiid", zhutiid);
		model.addAttribute("orderby", orderby);
		model.addAttribute("bankuai", bankuai);
		model.addAttribute("zhutilist", zhutilist);
		model.addAttribute("nowcount", nowcount);
		model.addAttribute("zhidinglist", zhidinglist);
		
		if(Tool.ismobile(request)){
			return "jsp/mobile/bankuai";
		}else{
			return "jsp/index/bankuai";
		}
	}
	
}
