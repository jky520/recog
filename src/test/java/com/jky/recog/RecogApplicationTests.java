package com.jky.recog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecogApplicationTests {

	@Value("${test.msg}")
	private String msg;

	@Test
	public void contextLoads() {
		System.out.println(msg);
	}

	@Autowired
	private Environment env;

	@Test
	public void testConfig() {
		System.out.println(env.getProperty("test.msg"));
	}
}
