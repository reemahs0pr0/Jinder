package sg.edu.iss.jinder.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import sg.edu.iss.jinder.model.User;
import sg.edu.iss.jinder.model.User_Graph;
import sg.edu.iss.jinder.model.User_Preference;
import sg.edu.iss.jinder.service.UserService;
import sg.edu.iss.jinder.service.UserServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private void setUserService(UserServiceImpl userServiceImpl) {
		this.userService=userServiceImpl;
	}
	
	private String localDirectory = "C:\\Gdipsa\\AD Project\\Discussion\\(Submitted)Sprint2_Deliverables";

//....................LOGIN PAGE....................
	@RequestMapping(value="/login")
	public String login(Model model) {
		model.addAttribute("user",new User());

		return "login";
	}

	@RequestMapping(value="/authenticate",method = RequestMethod.POST)
	public String authenticate(@ModelAttribute("user") User user, Model model, HttpSession session) {		
		if (userService.login(user.getUserName(), user.getPassword()) != null) {
			User authUser = userService.login(user.getUserName(), user.getPassword());
			String au_username = authUser.getUserName();
			String au_password= authUser.getPassword();
			if (au_username.equals(user.getUserName()) && au_password.equals(user.getPassword())) {
				session.setAttribute("usession", authUser);

				return "forward:/job/recommendedjobs";
			}
			else {
				model.addAttribute("error","Please enter correct username and password");

				return "forward:/user/login";
			}

		}
		else {
			model.addAttribute("error","Please enter correct username and password");

			return "forward:/user/login";
		}
	}

//....................LOGOUT PAGE....................	
	@RequestMapping(value="logout")
	public String logout(Model model, HttpSession session) {
		session.invalidate();
		
		return "logout";
	}

//....................SIGN UP PAGE....................
	@RequestMapping(value = "/signup")
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		
		return "signup";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") @Valid User user,
			BindingResult bindingResult,  Model model) {   
		if (bindingResult.hasErrors()) {
			
			return "signup";
		}
		if (userService.findUserbyUserName(user.getUserName()) != null) {   
			model.addAttribute("duplicateUserName", "The username already exists");

			return "signup";
		}
		if (userService.findUserbyEmailAddress(user.getEmailAddress()) != null) {
			model.addAttribute("duplicateEmailAddress", "The email already exists");

			return "signup";
		}
		else {
			user.setRegistrationDate(LocalDate.now());
			userService.saveUser(user);

			return "forward:/user/login";
		}
	}

//....................EDIT USER PAGE....................
	@RequestMapping(value = "/edit")
	public String editUser(Model model, HttpSession session) {
		User user = (User) session.getAttribute("usession");
		model.addAttribute("user", userService.findUserbyId(user.getId()));

		return "edituser";
	}

	@RequestMapping(value = "/save1", method = RequestMethod.POST)
	public String saveEditedUser(@ModelAttribute("user") @Valid User user,BindingResult bindingResult,  Model model, HttpSession session) {   
		User previousUser = userService.findUserbyId(user.getId());
		User newUser = (User) model.getAttribute("user");
		if (bindingResult.hasErrors()) {

			return "edituser";
		}
		if (!previousUser.getEmailAddress().equalsIgnoreCase(newUser.getEmailAddress())) {
			if (userService.findUserbyEmailAddress(user.getEmailAddress())!=null) {
				model.addAttribute("duplicateEmailAddress", "The email address already exists");

				return "edituser";
			}
			else {
				userService.saveUser(user);
				session.setAttribute("usession", user);

				return "forward:/user/userdetails";
			}
		}
		if (!previousUser.getUserName().equalsIgnoreCase(newUser.getUserName())) {
			if (userService.findUserbyUserName(user.getUserName())!=null) {
				model.addAttribute("duplicateUserName", "The username already exists");

				return "edituser";
			}
			else {	
				userService.saveUser(user);
				session.setAttribute("usession", user);

				return "forward:/user/userdetails";
			}
		}
		else {
			userService.saveUser(user);
			session.setAttribute("usession", user);

			return "forward:/user/userdetails";
		}
	}

//....................USER DETAILS PAGE....................
	@RequestMapping(value="/userdetails")
	public String userDetails(Model model, HttpSession session) {
		User user = (User) session.getAttribute("usession");
		model.addAttribute("user", userService.findUserbyId(user.getId()));

		return "userdetails";
	}

//....................UPLOAD RESUME PAGE....................
	@RequestMapping(value="/uploadfile")
	public String uploadFile(Model model, HttpSession session) {
		User user = (User) session.getAttribute("usession");
		model.addAttribute("user", userService.findUserbyId(user.getId()));
		
		return "uploadresume";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/springUpload", method = RequestMethod.POST)
    public String springUpload(HttpServletRequest request, HttpSession session) throws IllegalStateException, IOException {
		User user = (User) session.getAttribute("usession");
		
        long  startTime=System.currentTimeMillis(); //for debugging
        
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
      
        if(multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            Iterator iter=multiRequest.getFileNames();
             
            while(iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                if(file != null) {
                    String path=localDirectory + file.getOriginalFilename();
                    file.transferTo(new File(path));
                    userService.uploadResume(path, user);
                }
            }
        }
        long  endTime=System.currentTimeMillis(); //for debugging
        System.out.println("Upload duration "+String.valueOf(endTime-startTime)+"ms"); //for debugging
        
    return "uploadsuccess"; 
    }
	

	
//-----USER PREFRENCE SURVEY----
	//for new surveys
	@RequestMapping(value="/survey")
	public String save(Model model,HttpSession session)
			
	{     
		   User user = (User) session.getAttribute("usession");
		   System.out.print(userService.findUserPrefByUserId(user.getId()));
		   if(userService.findUserPrefByUserId(user.getId())==true)
		   {
			  
			   return "surveyconfirmation";
		   }
		
		   else {
			
				User_Preference upref1= new User_Preference();
				List<String> selectableRoles =new ArrayList<String>();
			    selectableRoles.add("Developer");
			    selectableRoles.add("Analyst");
			    selectableRoles.add("Project Manager");
			    selectableRoles.add("Project Owner/Manager");
			    selectableRoles.add("Sales");
			    selectableRoles.add("Consultant");
			    selectableRoles.add("Architect");
			    selectableRoles.add("Data Scientist");
			    selectableRoles.add("Designer");
			    selectableRoles.add("Database Admin");
			    
			    List<String>selectableTitles = new ArrayList<String>();
			    selectableTitles.add("Intern");
			    selectableTitles.add("Junior");
			    selectableTitles.add("Executive");
			    selectableTitles.add("Lead, Senior and Manager");
			    
			    model.addAttribute("selectableRoles",selectableRoles);
			    model.addAttribute("selectableTitles",selectableTitles);
				model.addAttribute("upref", upref1);
			    
				model.addAttribute("error","");
			    return "survey";
		   }
				

	}
	
	    //for saving  survey
		@RequestMapping(value="/savesurvey",method=RequestMethod.POST)
		public String savePref(@ModelAttribute("upref") @Valid User_Preference upref,BindingResult result,Model model,HttpSession session)
		{
			if (result.hasErrors()) 
			
			{	
				User_Preference upref1= new User_Preference();
			   
				List<String> selectableRoles =new ArrayList<String>();
			    selectableRoles.add("Developer");
			    selectableRoles.add("Analyst");
			    selectableRoles.add("Project Manager");
			    selectableRoles.add("Project Owner/Manager");
			    selectableRoles.add("Sales");
			    selectableRoles.add("Consultant");
			    selectableRoles.add("Architect");
			    selectableRoles.add("Data Scientist");
			    selectableRoles.add("Designer");
			    selectableRoles.add("Database Admin");
			    
			    List<String>selectableTitles = new ArrayList<String>();
			    selectableTitles.add("Intern");
			    selectableTitles.add("Junior");
			    selectableTitles.add("Executive");
			    selectableTitles.add("Lead, Senior and Manager");
			    
			    model.addAttribute("selectableRoles",selectableRoles);
			    model.addAttribute("selectableTitles",selectableTitles);
				model.addAttribute("upref",upref1);

				model.addAttribute("error","Minium must be 1 and Maximum must be 2 only");
				
				return "survey";
			}
			else {
				
				User_Preference userPrefToSave= new User_Preference(upref.getPreferredJobRole(), upref.getPreferredJobTitle(), upref.getPreferredTechnologies());
				 
				LocalDate now=LocalDate.now();
				userPrefToSave.setSurveyDate(now);  
				
				User user = (User) session.getAttribute("usession");
				userPrefToSave.setUser(user);
				
				userService.saveUserPref(userPrefToSave);

				return "forward:/user/userdetails";
			}
			
		}
		
		
		//for old survey
		@RequestMapping(value="/surveyagain")
		public String saveagain(Model model,HttpSession session,@RequestParam("yes_no") String yes_or_no)
				
		{     
			   User user = (User) session.getAttribute("usession");
			   
			   if(userService.findUserPrefByUserId(user.getId())==true)
			   {
				  if (yes_or_no.contains("Yes"))
				  {
                        User_Preference uPreference=userService.getUserPrefByUserId(user.getId());
                        userService.deleteUserPreference(uPreference);
						
                        
                        User_Preference upref1= new User_Preference();
						List<String> selectableRoles =new ArrayList<String>();
					    selectableRoles.add("Developer");
					    selectableRoles.add("Analyst");
					    selectableRoles.add("Project Manager");
					    selectableRoles.add("Project Owner/Manager");
					    selectableRoles.add("Sales");
					    selectableRoles.add("Consultant");
					    selectableRoles.add("Architect");
					    selectableRoles.add("Data Scientist");
					    selectableRoles.add("Designer");
					    selectableRoles.add("Database Admin");
					    
					    List<String>selectableTitles = new ArrayList<String>();
					    selectableTitles.add("Intern");
					    selectableTitles.add("Junior");
					    selectableTitles.add("Executive");
					    selectableTitles.add("Lead, Senior and Manager");
					    
					    model.addAttribute("selectableRoles",selectableRoles);
					    model.addAttribute("selectableTitles",selectableTitles);
						model.addAttribute("upref", upref1);
						model.addAttribute("error","");
					    
					    return "survey";
					  
				  }
			   }
			 
				   return "forward:/user/userdetails";	

		}

//....................DASHBOARD PAGE....................
	@RequestMapping(value = "/data")
	public String viewDashboard(Model model) {
		List<User_Graph> glist = userService.findAllGraphs();
		model.addAttribute("glist", glist);
		
		return "dashboard";
	}	
	
//....................PASSWORD RESET PAGE....................
	@RequestMapping(value = "/forgetpassword")
	public String forgetPassword() {
		
		return "passwordreset";
	}
	
	@RequestMapping(value = "/resetpassword")
	public String passwordReset(@Param("emailAddress") String emailAddress, Model model) {
		if (userService.forgetPassword(emailAddress)) {
			model.addAttribute("emailaddress", emailAddress);
			
			return "approvedresetpw"; 
		}
		else {
			model.addAttribute("invalidemail", "The entered email address " + emailAddress + " is invalid. Please enter a correct email address.");
			
			return "passwordreset";
		}
	}
	
}
