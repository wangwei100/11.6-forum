package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;

@Controller
public class RegisterController {
	@Autowired
	private UserMapper userMapper;

	@RequestMapping("/register")
	public ModelAndView register() {
		ModelAndView mv = new ModelAndView("/register");
		return mv;
	}

	@RequestMapping("/do_register")
	public ModelAndView doRegister(String username, String password, String password_confirm) {
		Map<String, Object> model = new HashMap<>();
		model.put("username", username);
		model.put("password", password);
		if (null == username || "".equals(username)) {
			// 用户名为空
			model.put("usernameMessage", "用户名不能为空");
			return new ModelAndView("/register", model);
		}
		User userDb = userMapper.getByUsername(username);
		if (null != userDb) {
			// 用户名被注册
			model.put("usernameMessage", "用户名被注册");
			return new ModelAndView("/register", model);
		}
		String s1 = new String("password");
		String s2 = new String("password_confirm");
		if (s1 == s2) {
			// 两次密码不同
			model.put("passwordMessage", "两次密码不同");
			return new ModelAndView("/user/register", model);
		}
		if (null == password || "".equals(password)) {
			// 密码不能为空
			model.put("passwordMessage", "密码不能为空");
			return new ModelAndView("/register", model);
		}
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setPassword(password_confirm);
		userMapper.insert(user);
		return new ModelAndView("redirect:/login");
	}
}
