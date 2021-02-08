package sg.edu.iss.jinder.service;

import java.util.List;

import sg.edu.iss.jinder.model.Job_Clicked;

import sg.edu.iss.jinder.model.User;
import sg.edu.iss.jinder.model.User_Preference;
import sg.edu.iss.jinder.model.User_Graph;

public interface UserService {

	public boolean resumeUploaded(int id);

	public boolean saveUser(User user);
	
	public User findUserbyId(Integer id);
	
	public User findUserbyUserName(String username);
	
	public User findUserbyEmailAddress (String emailAddress);
	
	public User login(String username,String password);
	
	public void uploadResume(String resumeUrl, User user);
	
	public void saveUserPref(User_Preference upref);
	
	public Boolean findUserPrefByUserId (int id);
	
	public User_Preference getUserPrefByUserId (int id);
	
	public void deleteUserPreference (User_Preference upref);
	
	public void saveJob_Clicked (Job_Clicked job_Clicked);
	
	public List<User_Graph> findAllGraphs();
	
	public boolean forgetPassword(String emailAddress);
	
	public String generateRandomAlphanumericString();

}
