package sg.edu.iss.jinder.service;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import sg.edu.iss.jinder.model.User;

public interface UserService {

	public boolean resumeUploaded(int id);

	public boolean saveUser(User user);
	
	public ArrayList<User> findAllUsers();
	
	public void deleteUser(User user);
	
	public User findUserbyId(Integer id);
	
	public User findUserbyUserName(String username);
	
	public User findUserbyEmailAddress (String emailAddress);
	
	public User login(String username,String password);

	//	public User store(MultipartFile file, Integer id);
	//	
	//	public User findUserById(Integer id);

}
