package sg.edu.iss.jinder.controller;

import java.io.File; 
import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.iss.jinder.model.JobSeeker;
import sg.edu.iss.jinder.service.JobSeekerService;
import sg.edu.iss.jinder.service.JobSeekerServiceImpl;

@RestController
@RequestMapping(path = "/api/user/")
public class JobSeekerRestController {
	
	@Autowired
	private JobSeekerService jsservice;
	
	@Autowired
	public void setUserService(JobSeekerServiceImpl jsserviceimpl) {
		this.jsservice = jsserviceimpl;
	}
	private String localDirectory = "DIRECTORY ";
	
	
	@RequestMapping(path = "{username}", method = RequestMethod.GET)
	public JobSeeker login(@PathVariable("username") String username, Model model) { 
		return jsservice.findJobSeekerByUserName(username);
	}
	@RequestMapping(path = "/save/{userName}&{password}&{fullName}&{emailAddress}&{address}&{phoneNo}", method = RequestMethod.POST)
	public List<ObjectError> saveUser(@ModelAttribute("user") @Valid JobSeeker user,
			BindingResult bindingResult,  Model model) { 
		if (bindingResult.hasErrors()) {
			return bindingResult.getAllErrors();
		}
		else {
			jsservice.saveJobSeeker(user);
			return null;
		}
	}
	@RequestMapping(path = "/edit/{userName}&{password}&{fullName}&{emailAddress}&{address}&{phoneNo}&{id}", method = RequestMethod.POST)
	public List<ObjectError> editUser(@ModelAttribute("user") @Valid JobSeeker user,
			BindingResult bindingResult,  Model model){ 
		if (bindingResult.hasErrors()) {
			return bindingResult.getAllErrors();
		}
		else {
			jsservice.saveJobSeeker(user);
			return null;
		}
		
	}
	@RequestMapping(path = "{userName}/uploadResume", method = RequestMethod.POST)
    public JobSeeker springUpload(@RequestParam("file") MultipartFile file, 
    		@PathVariable("userName") String username) throws IllegalStateException, IOException {
		
		JobSeeker user = jsservice.findJobSeekerByUserName(username);
        
		if(file != null) {
            String path=localDirectory + file.getOriginalFilename();
            file.transferTo(new File(path));
            jsservice.uploadResume(path, user);
        }
		
		return user;
    }

}
