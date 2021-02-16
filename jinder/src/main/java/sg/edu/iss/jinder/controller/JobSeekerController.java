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

import sg.edu.iss.jinder.model.JobSeeker;
import sg.edu.iss.jinder.model.User_Graph;
import sg.edu.iss.jinder.model.User_Preference;
import sg.edu.iss.jinder.service.JobSeekerService;
import sg.edu.iss.jinder.service.JobSeekerServiceImpl;

@Controller
@RequestMapping("/jobseeker")
public class JobSeekerController {

	@Autowired
	private JobSeekerService jsService;

	@Autowired
	private void setJobSeekerService(JobSeekerServiceImpl jobSeekerServiceImpl) {
		this.jsService=jobSeekerServiceImpl;
	}
	
	private String localDirectory = "D:\\GDipSA\\SA4106 Application Development Project ";

//....................LOGIN PAGE....................
	@RequestMapping(value="/login")
	public String login(Model model) {
		model.addAttribute("jobseeker",new JobSeeker());

		return "login";
	}

	@RequestMapping(value="/authenticate",method = RequestMethod.POST)
	public String authenticate(@ModelAttribute("jobseeker") JobSeeker jobSeeker, Model model, HttpSession session) {		
		if (jsService.login(jobSeeker.getUserName(), jobSeeker.getPassword()) != null) {
			JobSeeker authJobSeeker = jsService.login(jobSeeker.getUserName(), jobSeeker.getPassword());
			String au_username = authJobSeeker.getUserName();
			String au_password= authJobSeeker.getPassword();
			if (au_username.equals(jobSeeker.getUserName()) && au_password.equals(jobSeeker.getPassword())) {
				session.setAttribute("usession", authJobSeeker);

				return "forward:/job/recommendedjobs";
			}
			else {
				model.addAttribute("error","Please enter correct username and password");

				return "forward:/jobseeker/login";
			}

		}
		else {
			model.addAttribute("error","Please enter correct username and password");

			return "forward:/jobseeker/login";
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
	public String addJobSeeker(Model model) {
		model.addAttribute("jobseeker", new JobSeeker());
		
		return "signup";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String savejobSeeker(@ModelAttribute("jobseeker") @Valid JobSeeker jobSeeker,
			BindingResult bindingResult,  Model model) {   
		if (bindingResult.hasErrors()) {
			
			return "signup";
		}
		if (jsService.findJobSeekerByUserName(jobSeeker.getUserName()) != null) {   
			model.addAttribute("duplicateUserName", "The username already exists");

			return "signup";
		}
		if (jsService.findJobSeekerByEmailAddress(jobSeeker.getEmailAddress()) != null) {
			model.addAttribute("duplicateEmailAddress", "The email already exists");

			return "signup";
		}
		else {
			jsService.saveJobSeeker(jobSeeker);

			return "forward:/jobseeker/login";
		}
	}

//....................EDIT USER PAGE....................
	@RequestMapping(value = "/edit")
	public String editJobSeeker(Model model, HttpSession session) {
		JobSeeker jobSeeker = (JobSeeker) session.getAttribute("usession");
		model.addAttribute("jobseeker", jsService.findJobSeekerById(jobSeeker.getId()));

		return "edituser";
	}

	@RequestMapping(value = "/save1", method = RequestMethod.POST)
	public String saveEditedJobSeeker(@ModelAttribute("jobseeker") @Valid JobSeeker jobSeeker, BindingResult bindingResult,  Model model, HttpSession session) {   
		JobSeeker previousUser = jsService.findJobSeekerById(jobSeeker.getId());
		JobSeeker newUser = (JobSeeker) model.getAttribute("jobseeker");
		if (bindingResult.hasErrors()) {

			return "edituser";
		}
		if (!previousUser.getEmailAddress().equalsIgnoreCase(newUser.getEmailAddress())) {
			if (jsService.findJobSeekerByEmailAddress(jobSeeker.getEmailAddress())!=null) {
				model.addAttribute("duplicateEmailAddress", "The email address already exists");

				return "edituser";
			}
			else {
				jsService.saveEditedJobSeeker(previousUser, newUser);
				session.setAttribute("usession", jobSeeker);

				return "forward:/jobseeker/userdetails";
			}
		}
		if (!previousUser.getUserName().equalsIgnoreCase(newUser.getUserName())) {
			if (jsService.findJobSeekerByUserName(jobSeeker.getUserName())!=null) {
				model.addAttribute("duplicateUserName", "The username already exists");

				return "edituser";
			}
			else {
				jsService.saveEditedJobSeeker(previousUser, newUser);
				session.setAttribute("usession", jobSeeker);

				return "forward:/jobseeker/userdetails";
			}
		}
		else {
			jsService.saveEditedJobSeeker(previousUser, newUser);
			session.setAttribute("usession", jobSeeker);

			return "forward:/jobseeker/userdetails";
		}
	}

//....................USER DETAILS PAGE....................
	@RequestMapping(value="/userdetails")
	public String userDetails(Model model, HttpSession session) {
		JobSeeker jobSeeker = (JobSeeker) session.getAttribute("usession");
		model.addAttribute("jobseeker", jsService.findJobSeekerById(jobSeeker.getId()));

		return "userdetails";
	}

//....................UPLOAD RESUME PAGE....................
	@RequestMapping(value="/uploadfile")
	public String uploadFile(Model model, HttpSession session) {
		JobSeeker jobSeeker = (JobSeeker) session.getAttribute("usession");
		model.addAttribute("jobseeker", jsService.findJobSeekerById(jobSeeker.getId()));
		
		return "uploadresume";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/springUpload", method = RequestMethod.POST)
    public String springUpload(HttpServletRequest request, HttpSession session) throws IllegalStateException, IOException {
		JobSeeker jobSeeker = (JobSeeker) session.getAttribute("usession");
		
        long startTime=System.currentTimeMillis(); //for debugging
        
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
                    jsService.uploadResume(path, jobSeeker);
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
		   JobSeeker jobSeeker = (JobSeeker) session.getAttribute("usession");
		   System.out.print(jsService.findUserPrefById(jobSeeker.getId()));
		   if(jsService.findUserPrefById(jobSeeker.getId())==true)
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
				
				JobSeeker jobSeeker = (JobSeeker) session.getAttribute("usession");
				userPrefToSave.setUser(jobSeeker);
				
				jsService.saveUserPref(userPrefToSave);

				return "forward:/jobseeker/userdetails";
			}
			
		}
		
		
		//for old survey
		@RequestMapping(value="/surveyagain")
		public String saveagain(Model model,HttpSession session,@RequestParam("yes_no") String yes_or_no)
				
		{     
			   JobSeeker jobSeeker = (JobSeeker) session.getAttribute("usession");
			   
			   if(jsService.findUserPrefById(jobSeeker.getId())==true)
			   {
				  if (yes_or_no.contains("Yes"))
				  {
                        User_Preference uPreference=jsService.getUserPrefById(jobSeeker.getId());
                        jsService.deleteUserPreference(uPreference);
						
                        
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
			 
				   return "forward:/jobseeker/userdetails";	

		}

//....................DASHBOARD PAGE....................
	@RequestMapping(value = "/data")
	public String viewDashboard(Model model) {
		List<User_Graph> glist = jsService.findAllGraphs();
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
		if (jsService.forgetPassword(emailAddress)) {
			model.addAttribute("emailaddress", emailAddress);
			
			return "approvedresetpw"; 
		}
		else {
			model.addAttribute("invalidemail", "The entered email address " + emailAddress + " is invalid. Please enter a correct email address.");
			
			return "passwordreset";
		}
	}
	
}
