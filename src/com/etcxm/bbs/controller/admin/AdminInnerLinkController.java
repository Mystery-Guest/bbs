package com.etcxm.bbs.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etcxm.bbs.model.Bankuai;
import com.etcxm.bbs.model.InnerLink;
import com.etcxm.bbs.model.JifenGroup;
import com.etcxm.bbs.model.PageBean;
import com.etcxm.bbs.model.User;
import com.etcxm.bbs.service.BankuaiService;
import com.etcxm.bbs.service.InnerLinkService;
import com.etcxm.bbs.service.JifenGroupService;
import com.etcxm.bbs.tool.Pinyin4j;
import com.etcxm.bbs.tool.PublicStatic;
import com.etcxm.bbs.tool.Tool;
import com.etcxm.bbs.tool.thread.IndexThread;
/*
 * 后台内部链接
 * 
 */
@Controller
@RequestMapping("/admin/innerlink")
public class AdminInnerLinkController {
	@Autowired
	BankuaiService bankuaiService;
	
	@Autowired
	JifenGroupService jifenGroupService;
	
	@Autowired
	InnerLinkService innerLinkService;

	private static Logger log = Logger.getLogger(AdminInnerLinkController.class);

	//后台页面查询
	@RequestMapping("index.do")
	public String findAllWhere(HttpServletRequest request, Model model,InnerLink innerLink,PageBean<InnerLink> page) {
		page = innerLinkService.findpage(innerLink,page);
		model.addAttribute("page", page);
		return "jsp/admin/innerlink/index";
	}
	
	//跳转到修改页面
	@RequestMapping("toupdateoradd.do")
	public String toupdateoradd(Integer id, Model model) {
		if(id!=null){
			InnerLink innerLink=innerLinkService.findbyid(id);
			model.addAttribute("innerLink", innerLink);
		}
		return "jsp/admin/innerlink/updateoradd";
	}
	//修改或者新增
	@RequestMapping("updateoradd.do")
	public String updateoradd(HttpServletRequest request,InnerLink innerLink) {
		User user=(User)request.getSession().getAttribute(PublicStatic.USER);
		innerLink.setCreateuserid(user.getId());
		innerLink.setIsuse("0");
		innerLink.setType("0");
		innerLink.setCreatetime(Tool.getyyyyMMddHHmmss());
		String innerlinkhtml = innerLink.getInnerlink();
		if(innerlinkhtml!=null&&innerlinkhtml.length()>0){
			if(!innerlinkhtml.startsWith("http://")||!innerlinkhtml.startsWith("http://")){
				innerlinkhtml="http://"+innerlinkhtml;
				innerLink.setInnerlink(innerlinkhtml);
			}
		}
		
		if(innerLink.getId()!=null){
			innerLinkService.update(innerLink);
		}else{
			innerLinkService.insert(innerLink);
		}
		return "redirect:index.do ";
	}
	//删除
	@ResponseBody
	@RequestMapping("del.do")
	public String del(int id) {
		String result=innerLinkService.delete(id);
		return result;
	}
	//删除
	@ResponseBody
	@RequestMapping("updateuse.do")
	public String updateuse(int id,String use) {
		InnerLink innerLink=new InnerLink();
		innerLink.setId(id);
		innerLink.setIsuse(use);
		innerLink.setCreatetime(Tool.getyyyyMMddHHmmss());
		innerLinkService.update(innerLink);
		new IndexThread().start();
		return "1";
	}
	
}