package sg.edu.iss.jinder.service;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
	@Transactional
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

//	@Override
//	public User store(MultipartFile file, Integer id) {
//		User user = findUserById(id);
//		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//		user.setResumeName(fileName);
//		user.setResumeType(file.getContentType());
//		try {
//			user.setResumeData(file.getBytes());
//		} catch (IOException e) {
//			e.printStackTrace();
//			// TODO see what can be done if there is an exception caught
//		}
//
//	    return urepo.save(user);
//	}
//
//	@Override
//	public User findUserById(Integer id) {
//
//		return urepo.findById(id).get();
//	}

}
