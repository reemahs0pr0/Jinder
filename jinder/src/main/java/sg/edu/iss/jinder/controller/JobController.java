package sg.edu.iss.jinder.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.iss.jinder.model.Job;
import sg.edu.iss.jinder.model.User;
import sg.edu.iss.jinder.service.JobService;
import sg.edu.iss.jinder.service.JobServiceImpl;
import sg.edu.iss.jinder.service.UserService;
import sg.edu.iss.jinder.service.UserServiceImpl;

@Controller
@RequestMapping("/job")
public class JobController {
	
	@Autowired
	private JobService jobService;
    
	@Autowired
	private UserService userService;
	
	@Autowired
	private void setJobService(JobServiceImpl jobServiceImpl) {
		this.jobService=jobServiceImpl;
	}
	
	@Autowired
	private void setUserService(UserServiceImpl userServiceImpl) {
		this.userService=userServiceImpl;
	}
	
//....................JOB LISTING PAGE....................
	@RequestMapping(value="/list")
	public String jobListings(Model model,@Param("keyword")String keyword, @RequestParam("page") Optional<Integer> page, 
			@RequestParam("size") Optional<Integer> size, HttpSession session)
	{
		List<Job> jobs;
		User user = (User) session.getAttribute("usession");
		int id = user.getId();
		if(userService.resumeUploaded(id)) {
			jobs= jobService.listResult(keyword, id);
		}
		else {
			jobs= jobService.listAll(keyword);
		}
		int currentPage= page.orElse(1);
		int pageSize=size.orElse(10);
	
		Page<Job> jobPage=jobService.findPaginated(jobs, PageRequest.of(currentPage-1, pageSize));
	
		int totalPages= jobPage.getTotalPages();
		if(totalPages>0)
		{
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                .boxed()
	                .collect(Collectors.toList());
	            model.addAttribute("pageNumbers", pageNumbers);
			
		}
		model.addAttribute("jobs", jobPage);
		model.addAttribute("keyword", keyword);
		
		return "jobs";
	}

//....................VIEW JOB DETAILS PAGE....................
	@RequestMapping(value = "/detail/{id}")
	public String showJob(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("job", jobService.findJobById(id));
    
		return "jobdetail";	
	}
}
