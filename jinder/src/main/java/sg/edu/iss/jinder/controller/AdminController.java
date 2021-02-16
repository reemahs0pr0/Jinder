package sg.edu.iss.jinder.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sg.edu.iss.jinder.model.Admin;
import sg.edu.iss.jinder.service.AdminService;
import sg.edu.iss.jinder.service.AdminServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService aService; 
	
	@Autowired 
	private void setAdminService(AdminServiceImpl adminServiceImpl) {
		this.aService = adminServiceImpl;
	}

//....................LOGIN PAGE....................
	@RequestMapping(value="/login")
	public String login(Model model) {
		model.addAttribute("admin", new Admin());
		
		return "adminlogin";
	}
	
	@RequestMapping(value="/authenticate",method = RequestMethod.POST)
	public String authenticate(@ModelAttribute("admin") Admin admin, Model model, HttpSession session) {		
		if (aService.findAdminByUsernameAndPassword(admin.getUserName(), admin.getPassword()) != null) {
			Admin authAdmin = aService.findAdminByUsernameAndPassword(admin.getUserName(), admin.getPassword());
			String au_username = authAdmin.getUserName();
			String au_password= authAdmin.getPassword();
			if (au_username.equals(admin.getUserName()) && au_password.equals(admin.getPassword())) {
				session.setAttribute("asession", authAdmin);

				return "forward:/job/adminlist";
			}
			else {
				model.addAttribute("error","Please enter correct username and password");

				return "forward:/admin/login";
			}

		}
		else {
			model.addAttribute("error","Please enter correct username and password");

			return "forward:/admin/login";
		}
	}

//....................LOGOUT PAGE....................	
	@RequestMapping(value="logout")
	public String logout(Model model, HttpSession session) {
		session.invalidate();
		
		return "logout";
	}


//....................EDIT USER PAGE....................
	@RequestMapping(value = "/edit")
	public String editAdmin(Model model, HttpSession session) {
		Admin admin = (Admin) session.getAttribute("asession");
		model.addAttribute("admin", aService.findAdminById(admin.getId()));

		return "adminedit";
	}

	@RequestMapping(value = "/save1", method = RequestMethod.POST)
	public String saveEditedAdmin(@ModelAttribute("admin") @Valid Admin admin, BindingResult bindingResult,  Model model, HttpSession session) {   
		Admin previousUser = aService.findAdminById(admin.getId());
		Admin newUser = (Admin) model.getAttribute("admin");
		if (bindingResult.hasErrors()) {

			return "adminedit";
		}
		if (!previousUser.getEmailAddress().equalsIgnoreCase(newUser.getEmailAddress())) {
			if (aService.findAdminByEmailAddress(admin.getEmailAddress())!=null) {
				model.addAttribute("duplicateEmailAddress", "The email address already exists");

				return "adminedit";
			}
			else {
				aService.saveEditedAdmin(previousUser, newUser);
				session.setAttribute("asession", admin);

				return "forward:/admin/userdetails";
			}
		}
		if (!previousUser.getUserName().equalsIgnoreCase(newUser.getUserName())) {
			if (aService.findAdminByUsername(admin.getUserName())!=null) {
				model.addAttribute("duplicateUserName", "The username already exists");

				return "adminedit";
			}
			else {	
				aService.saveEditedAdmin(previousUser, newUser);
				session.setAttribute("asession", admin);

				return "forward:/admin/userdetails";
			}
		}
		else {
			aService.saveEditedAdmin(previousUser, newUser);
			session.setAttribute("asession", admin);

			return "forward:/admin/userdetails";
		}
	}

//....................USER DETAILS PAGE....................
	@RequestMapping(value="/userdetails")
	public String userDetails(Model model, HttpSession session) {
		Admin admin = (Admin) session.getAttribute("asession");
		model.addAttribute("admin", aService.findAdminById(admin.getId()));

		return "admindetails";
	}

//....................PASSWORD RESET PAGE....................
	@RequestMapping(value = "/forgetpassword")
	public String forgetPassword() {
			
		return "adminpasswordreset";
	}
		
	@RequestMapping(value = "/resetpassword")
	public String passwordReset(@Param("emailAddress") String emailAddress, Model model) {
		if (aService.forgetPassword(emailAddress)) {
			model.addAttribute("emailaddress", emailAddress);
				
			return "approvedresetpw"; 
		}
		else {
			model.addAttribute("invalidemail", "The entered email address " + emailAddress + " is invalid. Please enter a correct email address.");
				
			return "adminpasswordreset";
		}
	}
	
//.................... POST JOBS ....................
	@RequestMapping(value = "/postjobs")
	public String postJobs() {
		aService.postJobs();
		
		return "forward:/job/adminlist";
	}
	
}
