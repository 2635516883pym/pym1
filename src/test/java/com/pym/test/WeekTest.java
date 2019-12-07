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
			
			//������ĺ���
			String randomChinese = StringUtils.getRandomChinese(3);
			user.setName(randomChinese);
			
			//������Ա�
			Random random = new Random();
			String sex=random.nextBoolean()?"��":"Ů";
			user.setSex(sex);
			
			//������ֻ���
			String phone="13"+StringUtils.getRandomNumber(9);
			user.setPhone(phone);
			
			//���������
			int random2 = (int)(Math.random()*20);
			int len=random2<3?random2+3:random2;
			String randomStr = StringUtils.getRandomStr(len);
			String randomEmailSuffex = StringUtils.getRandomEmailSuffex();
			user.setEmail(randomStr+randomEmailSuffex);
			
			//��������� 18-70
			String randomBirthday = StringUtils.randomBirthday();
			user.setBirthday(randomBirthday);
			userList.add(user);
			
		}
		//jdk�����л���ʽ
/*		System.out.println("jdk�����л���ʽ");
		long  start= System.currentTimeMillis();
		BoundListOperations<String,Object> boundListOps = redisTemplate.boundListOps("jdk");
		boundListOps.leftPush(userList);
		long end = System.currentTimeMillis();
		System.out.println("��ʱ:"+(end-start)+"����");
		
		System.out.println("json�����л���ʽ");
		long start2 = System.currentTimeMillis();
		BoundListOperations<String, Object> boundListOps2 = redisTemplate.boundListOps("json");
		boundListOps2.leftPush(userList);
		long end2=System.currentTimeMillis();
		System.out.println("��ʱ:"+(end2-start2)+"����");*/
		
System.out.println("hash�����л���ʽ");
		long start3 = System.currentTimeMillis();
		BoundListOperations<String,Object> boundListOps3 = redisTemplate.boundListOps("hash");
		boundListOps3.leftPush(userList);
		long end3=System.currentTimeMillis();
		System.out.println("��ʱ:"+(end3-start3));
	}
}
