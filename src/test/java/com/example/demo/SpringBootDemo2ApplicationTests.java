package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.example.demo.mapper")
public class SpringBootDemo2ApplicationTests {
	@Autowired
	private UserMapper userMapper;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGet() {
		Integer id = 3;
		User user = userMapper.selectByPrimaryKey(id);
		System.out.println("----------------------");
		System.out.println(user.getUsername());
		System.out.println("-----------------------");
	}
}
