package sg.edu.iss.jinder;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.edu.iss.jinder.repo.UserRepository;
import sg.edu.iss.jinder.model.User;

@SpringBootApplication
public class JinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(JinderApplication.class, args);
	}
	
	@Autowired
	private UserRepository urepo;
	
	@Bean
	CommandLineRunner runner() {
		return args -> {
			urepo.save(new User("John Tan", "john123", "password", "john123@u.nus.edu"));
			urepo.save(new User("Jane Tan", "jane123", "password", "jane123@u.nus.edu"));
		};
	}

}
