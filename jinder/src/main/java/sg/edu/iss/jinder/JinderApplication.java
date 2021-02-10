package sg.edu.iss.jinder;

import org.springframework.beans.factory.annotation.Autowired; 

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.edu.iss.jinder.repo.AdminRepository;
import sg.edu.iss.jinder.repo.JobSeekerRepository;
import sg.edu.iss.jinder.model.Admin;
import sg.edu.iss.jinder.model.JobSeeker;

@SpringBootApplication
public class JinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(JinderApplication.class, args);
	}
	
	@Autowired
	private JobSeekerRepository jsrepo;
	
	@Autowired
	private AdminRepository arepo; 
	
	@Bean
	CommandLineRunner runner() {
		return args -> {
			jsrepo.save(new JobSeeker("John Tan", "john123", "password", "john123@u.nus.edu", "Tampines West", 123456));
			jsrepo.save(new JobSeeker("Jane Tan", "jane123", "password", "jane123@u.nus.edu", "Tampines East", 234567));
			arepo.save(new Admin("Benji Lee", "benji123", "password", "benji123@u.nus.edu", "Jurong West", 345678)); 
			arepo.save(new Admin("Benjamin Lee", "benben123", "password", "benjamin123@u.nus.edu", "Jurong East", 456789));
		};
	}
}
