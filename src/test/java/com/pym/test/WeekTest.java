package com.pym.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pym.bean.User;
import com.pym.utils.StringUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:applicationContext-redis.xml")
public class WeekTest {
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Test
	public void testDate() {
		
		List<User> userList=new ArrayList<>();
		for(int i=0;i<100000;i++) {
			User user = new User();
			user.setId(i+1);
			
			//随机中文汉字
			String randomChinese = StringUtils.getRandomChinese(3);
			user.setName(randomChinese);
			
			//随机的性别
			Random random = new Random();
			String sex=random.nextBoolean()?"男":"女";
			user.setSex(sex);
			
			//随机的手机号
			String phone="13"+StringUtils.getRandomNumber(9);
			user.setPhone(phone);
			
			//随机的邮箱
			int random2 = (int)(Math.random()*20);
			int len=random2<3?random2+3:random2;
			String randomStr = StringUtils.getRandomStr(len);
			String randomEmailSuffex = StringUtils.getRandomEmailSuffex();
			user.setEmail(randomStr+randomEmailSuffex);
			
			//随机的生日 18-70
			String randomBirthday = StringUtils.randomBirthday();
			user.setBirthday(randomBirthday);
			userList.add(user);
			
		}
		//jdk的序列化方式
/*		System.out.println("jdk的序列化方式");
		long  start= System.currentTimeMillis();
		BoundListOperations<String,Object> boundListOps = redisTemplate.boundListOps("jdk");
		boundListOps.leftPush(userList);
		long end = System.currentTimeMillis();
		System.out.println("耗时:"+(end-start)+"毫秒");
		
		System.out.println("json的序列化方式");
		long start2 = System.currentTimeMillis();
		BoundListOperations<String, Object> boundListOps2 = redisTemplate.boundListOps("json");
		boundListOps2.leftPush(userList);
		long end2=System.currentTimeMillis();
		System.out.println("耗时:"+(end2-start2)+"毫秒");*/
		
System.out.println("hash的序列化方式");
		long start3 = System.currentTimeMillis();
		BoundListOperations<String,Object> boundListOps3 = redisTemplate.boundListOps("hash");
		boundListOps3.leftPush(userList);
		long end3=System.currentTimeMillis();
		System.out.println("耗时:"+(end3-start3));
	}
}
