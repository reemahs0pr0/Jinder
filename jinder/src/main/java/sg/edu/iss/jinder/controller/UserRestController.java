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

import sg.edu.iss.jinder.model.User;
import sg.edu.iss.jinder.service.UserService;
import sg.edu.iss.jinder.service.UserServiceImpl;

@RestController
@RequestMapping(path = "/api/user/")
public class UserRestController {
	
	@Autowired
	private UserService uservice;
	
	@Autowired
	public void setUserService(UserServiceImpl userviceimpl) {
		this.uservice = userviceimpl;
	}
	@RequestMapping(path = "{username}", method = RequestMethod.GET)
	public User login(@PathVariable("username") String username, Model model) { 
		return uservice.findUserbyUserName(username);
	}
	@RequestMapping(path = "/save/{userName}&{password}&{fullName}&{emailAddress}&{address}&{phoneNo}", method = RequestMethod.POST)
	public List<ObjectError> saveUser(@ModelAttribute("user") @Valid User user,
			BindingResult bindingResult,  Model model) { 
		if (bindingResult.hasErrors()) {
			return bindingResult.getAllErrors();
		}
		else {
			uservice.saveUser(user);
			return null;
		}
	}

}
