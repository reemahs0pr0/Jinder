package sg.edu.iss.jinder.service;

import sg.edu.iss.jinder.model.User;

public interface UserService {

	public boolean resumeUploaded(int id);

	public boolean saveUser(User user);
	
//	public ArrayList<User> findAllUsers();
//	
//	public void deleteUser(User user);
	
	public User findUserbyId(Integer id);
	
	public User findUserbyUserName(String username);
	
	public User findUserbyEmailAddress (String emailAddress);
	
	public User login(String username,String password);
	
	public void uploadResume(String resumeUrl, User user);

}
