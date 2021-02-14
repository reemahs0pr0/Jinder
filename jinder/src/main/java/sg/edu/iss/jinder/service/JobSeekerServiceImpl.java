package sg.edu.iss.jinder.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import sg.edu.iss.jinder.model.Job_Clicked;
import sg.edu.iss.jinder.model.JobSeeker;
import sg.edu.iss.jinder.model.User_Graph;
import sg.edu.iss.jinder.model.User_Preference;
import sg.edu.iss.jinder.repo.Job_ClickedRepository;
import sg.edu.iss.jinder.repo.UserGraphRepository;
import sg.edu.iss.jinder.repo.UserPreferenceRepository;
import sg.edu.iss.jinder.repo.JobSeekerRepository;

@Service
public class JobSeekerServiceImpl implements JobSeekerService {
	
	@Autowired
	JobSeekerRepository jsrepo;
	
	@Autowired
	UserPreferenceRepository uprefrepo;
	
	@Autowired
	Job_ClickedRepository job_clickedrepo;
	
	@Autowired
	UserGraphRepository ugraphrepo;
	
	@Autowired
	private JavaMailSender emailSender;
	
	@Override
	public boolean saveJobSeeker(JobSeeker jobSeeker) {
		if(jsrepo.save(jobSeeker)!=null) 
			return true; 
		else {
			return false;	
		}
	}
	
	@Override
	public JobSeeker findJobSeekerById(Integer id) {
		return jsrepo.findById(id).get();
	}
	
	@Override
	public JobSeeker findJobSeekerByUserName(String userName) {
		return jsrepo.findByUserName(userName);
	}
	
	@Override
	public JobSeeker findJobSeekerByEmailAddress(String emailAddress) {
		return jsrepo.findByEmail(emailAddress);
	}
	
	@Override
	public JobSeeker login(String userName, String password) {
		JobSeeker jobSeeker = jsrepo.findByUsernameAndPassword(userName,password);
		return jobSeeker;
	}

	@Override
	public boolean resumeUploaded(int id) {
		if(jsrepo.getResumeById(id) != null) {
			return true;
		}
		
		return false;
	}

	@Override
	public void uploadResume(String resumeUrl, JobSeeker jobSeeker) {
		jsrepo.updateResume(resumeUrl, jobSeeker.getId());
	}

	@Override
	public void saveUserPref(User_Preference upref) {
		uprefrepo.save(upref);
		
	}

	@Override
	public Boolean findUserPrefById(int id) {
		if(uprefrepo.finduserprefByUserId(id)==null )
		{return false;}
		else {
			return true;
		}
	}

	@Override
	public User_Preference getUserPrefById(int id) {
		return uprefrepo.finduserprefByUserId(id);
	}

	@Override
	public void deleteUserPreference(User_Preference upref) {
		uprefrepo.delete(upref);
		
	}

	@Override
	public void saveJob_Clicked(Job_Clicked job_Clicked) {
		job_clickedrepo.save(job_Clicked);
		
	}

	@Override
	public List<User_Graph> findAllGraphs() {
		return ugraphrepo.findAll();
	}
	
	@Override
	public boolean forgetPassword(String emailAddress) {
		boolean isValidReq = false;
		List<JobSeeker> jobSeekerList = jsrepo.findAll();
		for(JobSeeker jobSeeker: jobSeekerList) {
			if(jobSeeker.getEmailAddress().equalsIgnoreCase(emailAddress)) {
				// generating a random alphanumeric password 
				String newPassword = generateRandomAlphanumericString();
				
				System.out.println(newPassword); // for debugging
				
				// retrieving user object and setting generated password to it
				JobSeeker forgetfulJobSeeker = findJobSeekerByEmailAddress(emailAddress);
				forgetfulJobSeeker.setPassword(newPassword);
				jsrepo.flush();
				// sending email to user 
				SimpleMailMessage message = new SimpleMailMessage(); 
				message.setTo(emailAddress);
				message.setSubject("Jinder - Forget Password");
				message.setText(" Dear " + forgetfulJobSeeker.getFullName() + 
								",\n\n Your password reset request for Jinder account " + forgetfulJobSeeker.getUserName() + " has been approved." +
								"\n Your new temporary password is: " + 
								"\n " + newPassword + 
								"\n Please login to Jinder immediately and change your password. " + 
								"\n\n Thank you " + 
								"\n\n Cheers, " + 
								"\n The Jinder Team");
				emailSender.send(message);
				
				System.out.println("Email sent"); // for debugging
				
				isValidReq = true;
				break;
			}
		}
		
		return isValidReq;
	}
	
	@Override
	public String generateRandomAlphanumericString() {
		int leftLimit = 48; //numeric '0' 
		int rightLimit = 122; // alphabet 'z' 
		int strLength = 6; 
		Random random = new Random(); 
		
		String generatedStr = random.ints(leftLimit, rightLimit + 1)
								.filter(i -> (i <= 57 || i>= 65) && (i <= 90 || i >= 97))
								.limit(strLength).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
								.toString();
		
		return generatedStr;
	}
	
	@Override
	public void saveEditedJobSeeker(JobSeeker previousJobSeeker, JobSeeker editedJobSeeker) {
		previousJobSeeker.setFullName(editedJobSeeker.getUserName());
		previousJobSeeker.setUserName(editedJobSeeker.getUserName());
		previousJobSeeker.setPassword(editedJobSeeker.getPassword());
		previousJobSeeker.setEmailAddress(editedJobSeeker.getEmailAddress());
		previousJobSeeker.setAddress(editedJobSeeker.getAddress());
		previousJobSeeker.setPhoneNo(editedJobSeeker.getPhoneNo());
		jsrepo.flush();
	}
}
