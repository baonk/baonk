package com.nv.baonk.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nv.baonk.common.CommonUtil;
import com.nv.baonk.vo.User;

@Controller
public class ChatController {
	@Autowired
	private CommonUtil commonUtil;	
	@Autowired
	private BCryptPasswordEncoder BCryptPass;
	
	private final Logger logger = LoggerFactory.getLogger(ChatController.class);
	
	@RequestMapping("/chatBoard")
	public String chatBoard(@CookieValue("loginCookie")String loginCookie, Model model, HttpServletRequest request) {
		User user = commonUtil.getUserInfo(loginCookie);
		return "/chat/chatBoard";
	}
}
