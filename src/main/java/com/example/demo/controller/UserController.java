package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.controller.Vo.BbsVo;
import com.example.demo.mapper.BbsMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Bbs;
import com.example.demo.model.PageBean;
import com.example.demo.model.User;

@Controller
public class UserController {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private BbsMapper bbsMapper;

	@RequestMapping("/home")
	public ModelAndView home(@RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "3") Integer pageSize, String title, HttpSession session) {
		if ("".equals(title)) {
			title = null;
		}
		ModelAndView mv = new ModelAndView();
		int offset = (pageNum - 1) * pageSize;
		List<Bbs> list = bbsMapper.getByTitle(title, offset, pageSize);
		int totalRecord = bbsMapper.countByTitle(title);
		int totalPage = (int) Math.ceil((double) totalRecord / pageSize);
		List<BbsVo> bbsVoList = new ArrayList<>();
		for (Bbs bbs : list) {
			BbsVo bbsVo = new BbsVo();
			bbsVo.setId(bbs.getId());
			bbsVo.setTitle(bbs.getTitle());
			bbsVo.setContent(bbs.getContent());
			bbsVo.setTime(bbs.getTime());
			User user = userMapper.get(bbs.getId());
			bbsVo.setOwner(user.getUsername());
			bbsVoList.add(bbsVo);
		}
		mv.setViewName("/home");
		PageBean<BbsVo> bbsPage = new PageBean<BbsVo>(pageNum, pageSize, totalRecord);
		bbsPage.setList(bbsVoList);
		bbsPage.setPageNum(pageNum);
		bbsPage.setPageSize(pageSize);
		bbsPage.setTotalPage(totalPage);
		bbsPage.setTotalRecord(totalRecord);
		mv.addObject("bbsPage", bbsPage);
		mv.addObject("title", title);
		// mv.addObject("title", "xxx");

		return mv;
	}

	@RequestMapping("/do_publish")
	public ModelAndView publish(Integer user_id, String title, String content, HttpSession session) {
		// 判断用户登录
		User loginUser = (User) session.getAttribute("loginUser");
		if (null == loginUser) {
			return new ModelAndView("redirect:/login");
		}
		Map<String, Object> model = new HashMap<>();
		model.put("title", title);
		model.put("content", content);
		if (null == title || "".equals(title)) {
			// 标题为空
			model.put("titleMessage", "标题不能为空");
			return new ModelAndView("/publish", model);
		}
		if (null == content || "".equals(content)) {
			// 内容为空
			model.put("contentMessage", "内容不能为空");
			return new ModelAndView("/publish", model);
		}
		Date date1 = new Date();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, 3);
		date1 = c.getTime();
		Bbs bbs = new Bbs();
		bbs.setUser_id(loginUser.getId());
		bbs.setTitle(title);
		bbs.setContent(content);
		bbs.setTime(date1);
		bbsMapper.insert(bbs);
		return new ModelAndView("redirect:/home");
	}

	@RequestMapping("/do_search")
	public ModelAndView doSearch(String title, String content, HttpSession session) {
		// 判断用户登录
		User loginUser = (User) session.getAttribute("loginUser");
		if (null == loginUser) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView mv = new ModelAndView();
		Bbs bbs = new Bbs();
		String keyword = "%keyword%";
		bbs.setTitle(title);
		bbs.setContent(content);
		List<Bbs> lists = bbsMapper.getByKeyWord(keyword);
		List<BbsVo> bbsVoLists = new ArrayList<>();
		for (Bbs bbs1 : lists) {
			BbsVo bbsVo = new BbsVo();
			bbsVo.setId(bbs1.getId());
			bbsVo.setTitle(bbs1.getTitle());
			bbsVo.setContent(bbs1.getContent());
			bbsVo.setTime(bbs1.getTime());

			User user = userMapper.get(bbs1.getId());
			bbsVo.setOwner(user.getUsername());
			bbsVoLists.add(bbsVo);
		}
		mv.setViewName("/search");
		mv.addObject("keyword", keyword);
		return mv;
	}
}
