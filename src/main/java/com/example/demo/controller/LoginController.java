package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;

@Controller
public class LoginController {
	@Autowired
	private UserMapper userMapper;

	@RequestMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("/login");
		return mv;
	}

	@RequestMapping("/do_login")
	public ModelAndView doLogin(String username, String password, HttpSession session) {
		Map<String, Object> model = new HashMap<>();
		model.put("username", username);
		model.put("password", password);
		boolean validateSuccess = true;
		if (null == username || "".equals(username)) {
			// 用户名为空
			model.put("usernameMessage", "用户名不能为空");
			validateSuccess = false;
		}
		if (null == password || "".equals(password)) {
			// 密码为空
			model.put("passwordMessage", "密码不能为空");
			validateSuccess = false;
		}
		if (!validateSuccess) {
			return new ModelAndView("/login", model);
		}
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		User hasUser = userMapper.getByUsernameAndPassword(username, password);
		if (null == hasUser) {
			model.put("usernameMessage", "用户名或密码错误");
			return new ModelAndView("/login", model);
		}
		session.setAttribute("loginUser", hasUser);
		return new ModelAndView("redirect:/home");
	}
}
