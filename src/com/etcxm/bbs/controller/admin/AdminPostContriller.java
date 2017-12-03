package com.etcxm.bbs.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etcxm.bbs.model.PageBean;
import com.etcxm.bbs.model.Tiezi;
import com.etcxm.bbs.model.User;
import com.etcxm.bbs.service.GroupService;
import com.etcxm.bbs.service.HuifuService;
import com.etcxm.bbs.service.TieziService;
import com.etcxm.bbs.service.UserService;
import com.etcxm.bbs.tool.PublicStatic;
import com.etcxm.bbs.tool.Tool;
/*
 * 后台用户管理页面
 * 
 */
@Controller
@RequestMapping("/admin/post")
public class AdminPostContriller {
	
	@Autowired
	UserService userService;
	
	@Autowired
	GroupService groupService;
	
	@Autowired
	TieziService tieziService;
	
	@Autowired
	HuifuService huifuService;
	
	//跳转首页
	@RequestMapping("index.do")
	public String add(HttpServletRequest request, Model model,Tiezi tiezi,PageBean<Tiezi> page) {
		if("1".equals(tiezi.getIsuse())){
			tiezi.setOrderby("5");
		}else{
			tiezi.setOrderby("4");
		}
		page = tieziService.findpagebybankuanid(tiezi, page);
		model.addAttribute("page", page);
		model.addAttribute("qtiezi", tiezi);
		return "jsp/admin/post/index";
	}
	
	
	
	//审核通过
	@RequestMapping("updatepost.do")
	public String updatepost(HttpServletRequest request, Model model,Tiezi tiezi) {
		tieziService.updatepost(tiezi);
		return "redirect:index.do?isuse=0";
	}
	
	
	
	//删除帖子
	@RequestMapping("delpost.do")
	public String delpost(HttpServletRequest request, Model model,int id) {
		tieziService.delete(id);
		huifuService.deletebytiezi(id);
		return "redirect:index.do?isuse=1";
	}
}
