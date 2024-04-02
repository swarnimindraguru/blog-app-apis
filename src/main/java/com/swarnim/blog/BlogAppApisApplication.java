package com.swarnim.blog;

import com.swarnim.blog.config.AppConstants;
import com.swarnim.blog.entities.Role;
import com.swarnim.blog.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(passwordEncoder.encode("xyz"));

		try {
			Role adminRole= new Role();
			adminRole.setId(AppConstants.ADMIN_USER);
			adminRole.setName("ROLE_ADMIN");

			Role normalRole= new Role();
			normalRole.setId(AppConstants.NORMAL_USER);
			normalRole.setName("ROLE_NORMAL");

			List<Role> roles=List.of(adminRole,normalRole);
			List<Role> result=roleRepository.saveAll(roles);

//			result.forEach( r-> {
//				System.out.println(r.getRoleName());
//			});
			System.out.println("--------------------> READY TO ROLL");

		}catch (Exception e){
			e.printStackTrace();
		}
	}


}
