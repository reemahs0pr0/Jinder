package sg.edu.iss.jinder.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.jinder.model.User;
import sg.edu.iss.jinder.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository urepo;
	
	@Override
	public boolean saveUser(User user) {
		if(urepo.save(user)!=null) 
			return true; 
		else {
			return false;	
		}
	}
	
	// may not need this
	@Override
	public ArrayList<User> findAllUsers() {
		return (ArrayList<User>) urepo.findAll();
	}
	
	// may not need
	@Transactional
	@Override
	public void deleteUser(User user) {
		urepo.delete(user);
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
}
