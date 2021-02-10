package sg.edu.iss.jinder.controller;

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
import org.springframework.web.bind.annotation.RestController;

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

}
