package sg.edu.iss.jinder.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import sg.edu.iss.jinder.model.User;
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

				return "forward:/job/list";
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
		User user = new User(); 
		user.setRegistrationDate(LocalDate.now());
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
                    String path="DIRECTORY" + file.getOriginalFilename();
                    file.transferTo(new File(path));
                    userService.uploadResume(path, user);
                }
            }
        }
        long  endTime=System.currentTimeMillis(); //for debugging
        System.out.println("Upload duration "+String.valueOf(endTime-startTime)+"ms"); //for debugging
        
    return "uploadsuccess"; 
    }
}
