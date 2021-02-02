package sg.edu.iss.jinder;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.edu.iss.jinder.model.User;
import sg.edu.iss.jinder.repo.UserRepository;

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
			urepo.save(new User(
					"Ash Ketchup", 
					"ilovepikachu", 
					"pikapika", 
					"pkmnmaster@gmail.com", 
					"Pallet Town", 
					12345, 
					LocalDate.now()));
			urepo.save(new User(
					"Prof Oak", 
					"ilovepkmn", 
					"oaky", 
					"oak@gmail.com", 
					"Pallet Town", 
					23456, 
					LocalDate.now()));
			urepo.save(new User(
					"Gary",
					"iloveeevee", 
					"eeeee", 
					"gary@gmail.com", 
					"Pallet Town", 
					34567, 
					LocalDate.now()));
		};
	}

}
