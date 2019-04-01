package com.cwj.springbootTest.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("userInfo")
public class UserInfoController {
	@RequestMapping("userList")
	public String userList(HttpServletRequest request){
		System.out.println("userList");
		Session session = SecurityUtils.getSubject().getSession();
		Object principal = SecurityUtils.getSubject().getPrincipal();
		request.setAttribute("userInfo", principal);
		return "userInfo";
	}
	@RequestMapping("userAdd")
	public String userAdd(){
		System.out.println("userAdd");
		return "userInfoAdd";
	}
	@RequiresPermissions(value = { "userInfo:del" })
	@RequestMapping("userDel")
	public String userDel(){
		System.out.println("userDel");
		return "userInfoDel";
	}
}
