package sg.edu.iss.jinder.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import sg.edu.iss.jinder.model.Admin;
import sg.edu.iss.jinder.repo.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	AdminRepository arepo; 
	
	@Autowired
	private JavaMailSender emailSender;
	
	@Override
	public void saveAdmin(Admin admin) {
		arepo.save(admin);
	}
	
	@Override
	public Admin findAdminById(Integer id) {
		
		return arepo.findById(id).get();
	}
	
	@Override
	public Admin findAdminByUsername(String userName) {
		
		return arepo.findByUserName(userName);
	}
	
	@Override 
	public Admin findAdminByEmailAddress(String emailAddress) {
		
		return arepo.findByEmail(emailAddress);
	}
	
	@Override
	public Admin findAdminByUsernameAndPassword(String userName, String password) {
		
		return arepo.findByUsernameAndPassword(userName, password);
	}

	@Override
	public boolean forgetPassword(String emailAddress) {
		boolean isValidReq = false;
		List<Admin> adminList = arepo.findAll();
		for(Admin admin: adminList) {
			if(admin.getEmailAddress().equalsIgnoreCase(emailAddress)) {
				// generating a random alphanumeric password 
				String newPassword = generateRandomAlphanumericString();
				
				System.out.println(newPassword); // for debugging
				
				// retrieving user object and setting generated password to it
				Admin forgetfulAdmin = findAdminByEmailAddress(emailAddress);
				forgetfulAdmin.setPassword(newPassword);
				arepo.flush();
				// sending email to user 
				SimpleMailMessage message = new SimpleMailMessage(); 
				message.setTo(emailAddress);
				message.setSubject("Jinder - Forget Password");
				message.setText(" Dear " + forgetfulAdmin.getFullName() + 
								",\n\n Your password reset request for Jinder account " + forgetfulAdmin.getUserName() + " has been approved." +
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
	public void postJobs() {
		try {
			URL url = new URL("http://127.0.0.1:5000/postjobs/");
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
			
			conn.setRequestMethod("POST");
		    conn.connect();
		    BufferedReader rd  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    StringBuilder sb = new StringBuilder();
		    String line = null;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line + '\n');
	        }
	        rd.close();
	        conn.disconnect();
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
