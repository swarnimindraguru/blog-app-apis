package com.swarnim.demo;

import com.swarnim.blog.repositories.PostRepo;
import com.swarnim.blog.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogAppApisApplicationTests {

@Autowired
//private PostRepo postRepo;
	private UserRepo userRepo;
//	@Autowired
//	public BlogAppApisApplicationTests(UserRepo userRepo) {
//		this.userRepo = userRepo;
//	}

	@Test
	void contextLoads() {

	}

	@Test
	public void repoTest() {
		String className = this.userRepo.getClass().getName();
		String packageName = this.userRepo.getClass().getPackageName();
		System.out.println(className);
		System.out.println(packageName);
	}

}
