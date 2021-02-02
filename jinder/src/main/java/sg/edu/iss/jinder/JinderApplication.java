package sg.edu.iss.jinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.edu.iss.jinder.model.Job;
import sg.edu.iss.jinder.repo.JobRepository;

@SpringBootApplication
public class JinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(JinderApplication.class, args);
	}
	
	
	@Autowired
	private JobRepository jrepo;
	
	@Bean
	CommandLineRunner runner() {
		return args -> {
			jrepo.save(new Job(
					"Qlikview Developer", 
					"Proficient in SQL based querying of database for data analysis Experience to handle huge volume of datasets and performance improvements of dashboards",
					"A-IT Software Services Pte. Ltd", 
					"Qlikview QMC",
					"https://www.monster.com.sg/seeker/job-details?id=2511623&searchId=2b34467f-fc11-4651-98e3-a742474195f5"));
			jrepo.save(new Job(
					"Robotics Engineer (Hardware)", 
					"Competency in designing interface devices.Experience in prototyping (3D-printing, CAD systems) Knowledge of C++ and Python will be an added advantage (Not Mandatory)", 
					"admin2@gmail.com", 
					"Transcendent Business Services Pte Ltd",
					"https://www.monster.com.sg/seeker/job-details?id=2511607&searchId=2b34467f-fc11-4651-98e3-a742474195f5"));
			jrepo.save(new Job(
					".NET IT Analyst, Senior, Lead", 
					"certified in Amazon Web Services like AWS Certified Cloud Practitioner or better.certified in Microsoft Azure platform.Google Cloud services.container technology like Docker, Docker Swarm or Kubernetes.C# (NET)", 
					"Ethos Search Associates Pte Ltd", 
					"",
					"https://www.monster.com.sg/seeker/job-details?id=2511561&searchId=2b34467f-fc11-4651-98e3-a742474195f5"));
			jrepo.save(new Job(
					"PHP Software Developer", 
					"Develop new applications from scratch.Involved in full software development life cycle (SDLC)Research and work on high-scalability applications with PHP7", 
					"Transcendent Business Services Pte Ltd", 
					"React, Angular, Javascript",
					"https://www.monster.com.sg/seeker/job-details?id=2511585&searchId=2b34467f-fc11-4651-98e3-a742474195f5"));
			jrepo.save(new Job(
					"IT Security Engineer", 
					"This role is open to fresh graduates!Work alongside best in class talent in a successful companyExcellent learning opportunitiesOur client is a digital asset exchange that is transforming the capital market", 
					"SnapHunt Pte Ltd", 
					"Cissp, Cisa, Cism, Agile",
					"https://www.monster.com.sg/seeker/job-details?id=2511561&searchId=2b34467f-fc11-4651-98e3-a742474195f5"));
									
		};
	}

}
