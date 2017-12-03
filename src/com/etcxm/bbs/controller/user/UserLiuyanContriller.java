package com.etcxm.bbs.controller.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etcxm.bbs.model.User;
import com.etcxm.bbs.model.UserLiuyan;
import com.etcxm.bbs.service.UserLiuyanService;
import com.etcxm.bbs.tool.PublicStatic;
import com.etcxm.bbs.tool.Tool;
/*
 * 用户留言页面
 * 
 */
@Controller
@RequestMapping("/user/liuyan")
public class UserLiuyanContriller {
	
	@Autowired
	UserLiuyanService userLiuyanService;
	
	
	//添加
	@ResponseBody
	@RequestMapping("add.do")
	public String add(HttpServletRequest request, Model model,UserLiuyan userLiuyan) {
		User sessionuser=(User)request.getSession().getAttribute(PublicStatic.USER);
		if(sessionuser!=null&&userLiuyan.getContenthtml().length()>0){
			userLiuyan.setCreateuserid(sessionuser.getId());
			userLiuyan.setCreatetime(Tool.getyyyyMMddHHmm());
			userLiuyanService.insert(userLiuyan);
		}
		return "1";
	}
	//删除
	@ResponseBody
	@RequestMapping("del.do")
	public String del(HttpServletRequest request, Model model,UserLiuyan userLiuyan) {
		User sessionuser=(User)request.getSession().getAttribute(PublicStatic.USER);
		if(sessionuser!=null){
			userLiuyan  = userLiuyanService.findbyid(userLiuyan);
			if(userLiuyan!=null){
				if(sessionuser.getGroupid().equals("1")||sessionuser.getGroupid().equals("2")||sessionuser.getId()==userLiuyan.getCreateuserid()||sessionuser.getId()==userLiuyan.getLiuyanuserid()){
					userLiuyanService.delete(userLiuyan.getId());
				}
			}
		}
		return "1";
	}
	
	
	
}
