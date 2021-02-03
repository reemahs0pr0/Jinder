package sg.edu.iss.jinder.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.iss.jinder.message.ResponseFile;
import sg.edu.iss.jinder.message.ResponseMessage;
import sg.edu.iss.jinder.model.User;
import sg.edu.iss.jinder.service.UserService;
import sg.edu.iss.jinder.service.UserServiceImpl;

@Controller
@RequestMapping("/user")
//@CrossOrigin("http://localhost:8081")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private void setUserService(UserServiceImpl userServiceImpl) {
		this.userService=userServiceImpl;
	}

	//....................LOGIN/HOME/LOGOUT....................
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
	
	@RequestMapping(value="logout")
	public String logout(Model model, HttpSession session) {
		session.invalidate();
		
		return "logout";
	}

	//....................CRUD....................
	@RequestMapping(value = "/signup")
	public String addUser(Model model) {
		User user = new User(); 
		user.setRegistrationDate(LocalDate.now());
		model.addAttribute("user", new User());
		
		return "signup";
	}

	//for new user
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") @Valid User user,
			BindingResult bindingResult,  Model model) {   
		if (bindingResult.hasErrors()) {
			
			return "signup";
		}
		if (userService.findUserbyUserName(user.getUserName()) != null) {   
			model.addAttribute("duplicate", "The username already exists");

			return "signup";
		}
		if (userService.findUserbyEmailAddress(user.getEmailAddress()) != null) {
			model.addAttribute("duplicate", "The email already exists");

			return "signup";
		}
		else {
			userService.saveUser(user);

			return "forward:/user/login";
		}
	}

	//for editing
	@RequestMapping(value = "/edit")
	public String editUser(Model model, HttpSession session) {
		User user = (User) session.getAttribute("usession");
		model.addAttribute("user", userService.findUserbyId(user.getId()));

		return "edituser";
	}

	//for saving the edited
	@RequestMapping(value = "/save1", method = RequestMethod.POST)
	public String saveEditedUser(@ModelAttribute("user") @Valid User user,BindingResult bindingResult,  Model model, HttpSession session) {   
		User previousUser = userService.findUserbyId(user.getId());
		User newUser = (User) model.getAttribute("user");
		if (bindingResult.hasErrors()) {

			return "edituser";
		}
		if (!previousUser.getEmailAddress().equalsIgnoreCase(newUser.getEmailAddress())) {
			if (userService.findUserbyEmailAddress(user.getEmailAddress())!=null) {
				model.addAttribute("duplicate", "The email address already exists");

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
				model.addAttribute("duplicate", "The username already exists");

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

	// view user particulars
	@RequestMapping(value="/userdetails")
	public String userDetails(Model model, HttpSession session) {
		User user = (User) session.getAttribute("usession");
		model.addAttribute("user", userService.findUserbyId(user.getId()));

		return "userdetails";
	}

	//....................RESUME UPLOAD....................
	//	@PostMapping("/upload")
	//	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer id) {
	//		String message = "";
	//		try {
	//			userService.store(file, id);
	//
	//			message = "Uploaded the file successfully: " + file.getOriginalFilename();
	//			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	//		} catch (Exception e) {
	//			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	//			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	//		}
	//	}
	//
	//	@GetMapping("/files/{id}")
	//	public ResponseEntity<byte[]> getFile(@PathVariable("id") Integer id) {
	//		User user = userService.findUserById(id); 
	//
	//		return ResponseEntity.ok()
	//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + user.getResumeName() + "\"")
	//				.body(user.getResumeData());
	//	}
}
