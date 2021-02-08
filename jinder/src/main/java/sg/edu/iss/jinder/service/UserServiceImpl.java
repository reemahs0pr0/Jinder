package sg.edu.iss.jinder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.jinder.model.Job_Clicked;
import sg.edu.iss.jinder.model.User;
import sg.edu.iss.jinder.model.User_Graph;
import sg.edu.iss.jinder.model.User_Preference;
import sg.edu.iss.jinder.repo.Job_ClickedRepository;
import sg.edu.iss.jinder.repo.UserGraphRepository;
import sg.edu.iss.jinder.repo.UserPreferenceRepository;
import sg.edu.iss.jinder.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository urepo;
	
	@Autowired
	UserPreferenceRepository uprefrepo;
	
	@Autowired
	Job_ClickedRepository job_clickedrepo;
	
	@Autowired
	UserGraphRepository ugraphrepo;
	
	@Override
	public boolean saveUser(User user) {
		if(urepo.save(user)!=null) 
			return true; 
		else {
			return false;	
		}
	}
	
	@Override
	public User findUserbyId(Integer id) {
		return urepo.findById(id).get();
	}
	
	@Override
	public User findUserbyUserName(String username) {
		return urepo.findByUserName(username);
	}
	
	@Override
	public User findUserbyEmailAddress(String emailAddress) {
		return urepo.findByEmail(emailAddress);
	}
	
	@Override
	public User login(String username, String password) {
		User user = urepo.findByUsernameAndPassword(username,password);
		return user;
	}

	@Override
	public boolean resumeUploaded(int id) {
		if(urepo.getResumeById(id) != null) {
			return true;
		}
		
		return false;
	}

	@Override
	public void uploadResume(String resumeUrl, User user) {
		urepo.updateUserResume(resumeUrl, user.getId());
	}

	@Override
	public void saveUserPref(User_Preference upref) {
		uprefrepo.save(upref);
		
	}

	@Override
	public Boolean findUserPrefByUserId(int id) {
		if(uprefrepo.finduserprefByUserId(id)==null )
		{return false;}
		else {
			return true;
		}
	}

	@Override
	public User_Preference getUserPrefByUserId(int id) {
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
}
