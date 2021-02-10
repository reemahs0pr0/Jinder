package sg.edu.iss.jinder.service;

import java.util.List;

import sg.edu.iss.jinder.model.Job_Clicked;
import sg.edu.iss.jinder.model.JobSeeker;
import sg.edu.iss.jinder.model.User_Preference;
import sg.edu.iss.jinder.model.User_Graph;

public interface JobSeekerService {

	public boolean resumeUploaded(int id);

	public boolean saveJobSeeker(JobSeeker jobSeeker);
	
	public JobSeeker findJobSeekerById(Integer id);
	
	public JobSeeker findJobSeekerByUserName(String userName);
	
	public JobSeeker findJobSeekerByEmailAddress (String emailAddress);
	
	public JobSeeker login(String userName,String password);
	
	public void uploadResume(String resumeUrl, JobSeeker jobSeeker);
	
	public void saveUserPref(User_Preference upref);
	
	public Boolean findUserPrefById (int id);
	
	public User_Preference getUserPrefById (int id);
	
	public void deleteUserPreference (User_Preference upref);
	
	public void saveJob_Clicked (Job_Clicked job_Clicked);
	
	public List<User_Graph> findAllGraphs();
	
	public boolean forgetPassword(String emailAddress);
	
	public String generateRandomAlphanumericString();

}
