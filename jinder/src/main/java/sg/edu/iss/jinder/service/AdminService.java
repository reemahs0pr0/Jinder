package sg.edu.iss.jinder.service;

import sg.edu.iss.jinder.model.Admin;

public interface AdminService {
	
	public void saveAdmin(Admin admin);

	public Admin findAdminById(Integer id);
	
	public Admin findAdminByUsername(String userName);
	
	public Admin findAdminByEmailAddress(String emailAddress);
	
	public Admin findAdminByUsernameAndPassword(String userName, String password);
	
	public boolean forgetPassword(String emailAddress); 
	
	public String generateRandomAlphanumericString();
	
	public void postJobs();
	
	public void saveEditedAdmin(Admin previousAdmin, Admin editedAdmin);
	
}
