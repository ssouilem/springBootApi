package com.services.direct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DirectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DirectApplication.class, args);
	}
	
	
	// new token 
	@Bean
	  public ModelMapper modelMapper() {
	    return new ModelMapper();
	  }
	 @Override
	  public void run(String... params) throws Exception {
	    User admin = new User();
	    admin.setUsername("admin");
	    admin.setPassword("admin");
	    admin.setEmail("admin@email.com");
	    admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));

	    userService.signup(admin);

	    User client = new User();
	    client.setUsername("client");
	    client.setPassword("client");
	    client.setEmail("client@email.com");
	    client.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));

	    userService.signup(client);
	  }

}
