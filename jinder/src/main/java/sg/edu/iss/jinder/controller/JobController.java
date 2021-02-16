package sg.edu.iss.jinder.controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
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
import sg.edu.iss.jinder.model.Job_Clicked;

import sg.edu.iss.jinder.model.JobSeeker;
import sg.edu.iss.jinder.service.JobService;
import sg.edu.iss.jinder.service.JobServiceImpl;
import sg.edu.iss.jinder.service.JobSeekerService;
import sg.edu.iss.jinder.service.JobSeekerServiceImpl;

@Controller
@RequestMapping("/job")
public class JobController {
	
	@Autowired
	private JobService jobService;
    
	@Autowired
	private JobSeekerService jsService;
	
	@Autowired
	private void setJobService(JobServiceImpl jobServiceImpl) {
		this.jobService=jobServiceImpl;
	}
	
	@Autowired
	private void setJobSeekerService(JobSeekerServiceImpl jobSeekerServiceImpl) {
		this.jsService=jobSeekerServiceImpl;
	}
	
//....................JOB LISTING PAGE....................
	@RequestMapping(value="/list")
	public String jobListings(Model model,@Param("keyword")String keyword, 
			@RequestParam("progLang") Optional<String> progLang, 
			@RequestParam("page") Optional<Integer> page, 
			@RequestParam("size") Optional<Integer> size, HttpSession session) {
		List<Job> jobs;
		JobSeeker user = (JobSeeker) session.getAttribute("usession");
		int id = user.getId();
		if(jsService.resumeUploaded(id)) {
			jobs = jobService.listResult(keyword, id);
		}
		else {
			jobs = jobService.listAll(keyword);
		}
		
		if(progLang.isPresent()) {
			ListIterator<Job> iter = jobs.listIterator();
			while(iter.hasNext()){
			    if(!iter.next().toString().contains(progLang.get())){
			        iter.remove();
			    }
			}
			model.addAttribute("lastSelected", progLang.get());
		}
		else {
			model.addAttribute("lastSelected", "");
		}
		
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
	
		Page<Job> jobPage=jobService.findPaginated(jobs, PageRequest.of(currentPage-1, pageSize));
	
		int totalPages= jobPage.getTotalPages();
		if(totalPages>0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                .boxed()
	                .collect(Collectors.toList());
	            model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("jobs", jobPage);
		model.addAttribute("keyword", keyword);
	
		return "jobs";
	}
	
	@RequestMapping(value = "/adminlist")
	public String adminJobListing(Model model,@Param("keyword")String keyword, 
			@RequestParam("progLang") Optional<String> progLang, @RequestParam("page") Optional<Integer> page, 
			@RequestParam("size") Optional<Integer> size, HttpSession session) {
		List<Job> jobList; 
		jobList = jobService.listAll(keyword);
		if(progLang.isPresent()) {
			ListIterator<Job> iter = jobList.listIterator();
			while(iter.hasNext()){
			    if(!iter.next().toString().contains(progLang.get())){
			        iter.remove();
			    }
			}
			model.addAttribute("lastSelected", progLang.get());
		}
		else {
			model.addAttribute("lastSelected", "");
		}
		
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
	
		Page<Job> jobPage=jobService.findPaginated(jobList, PageRequest.of(currentPage-1, pageSize));
	
		int totalPages= jobPage.getTotalPages();
		if(totalPages>0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                .boxed()
	                .collect(Collectors.toList());
	            model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("jobs", jobPage);
		model.addAttribute("keyword", keyword);
		
		return "adminjobs";
	}

//....................VIEW JOB DETAILS PAGE....................
	@RequestMapping(value = "/detail/{id}")
	public String showJob(@PathVariable("id") Integer id, Model model,HttpSession session) {
		JobSeeker user = (JobSeeker) session.getAttribute("usession");
		//---- to add user click history -------------------------
		Job_Clicked job_ClickedToSave= new Job_Clicked();
		job_ClickedToSave.setUser(user);
		job_ClickedToSave.setJob(jobService.findJobById(id));
		jsService.saveJob_Clicked(job_ClickedToSave);
		
		model.addAttribute("job", jobService.findJobById(id));
    
		return "jobdetail";	
	}
	
	@RequestMapping(value = "/admindetail/{id}")
	public String adminShowJob(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("job", jobService.findJobById(id));
		
		return "jobdetail";	
	}
	
//....................VIEW RECOMMENDATION PAGE....................
	@RequestMapping(value = "/recommendedjobs")
	public String recommendationJobs (Model model, HttpSession session) {
		JobSeeker user = (JobSeeker) session.getAttribute("usession");
		//----- list jobs based on User Preference Survey -----
		if (jsService.findUserPrefById(user.getId())) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String lastSurveySubmittedDate = jsService.getUserPrefById(user.getId()).getSurveyDate().format(formatter);
			
			model.addAttribute("recommendBySurveyText", "Based on your Job Preference Survey submitted on " + lastSurveySubmittedDate + ", below are recommended jobs:");
			model.addAttribute("recommendedJobsBySurvey", jobService.listRecommendedJobsBySurvey(user));
			for (Job j:jobService.listRecommendedJobsBySurvey(user)) {
				System.out.println(j.getJobTitle());
			}
		}
		else {
			List<Job> emptyJobs= new ArrayList<Job>();
			System.out.println("Empty:" + emptyJobs.isEmpty());
			
			model.addAttribute("recommendBySurveyText", "No Job Preference Survey submitted");
			model.addAttribute("recommendedJobsBySurvey", emptyJobs);
			model.addAttribute("takesurvey","Click here to take survey");
		}
		//----- list jobs based on click history -----
		if( !jobService.findJob_ClickedsbyUserId(user.getId()).isEmpty()) {
			List<Job_Clicked> jobs_clickedByUser = jobService.findJob_ClickedsbyJobId(user.getId());
			
			String jobid_1 = String.valueOf(jobs_clickedByUser.get(jobs_clickedByUser.size()-1).getJob().getId()).replace(" ", "+");
			String lastJobClicked = jobs_clickedByUser.get(jobs_clickedByUser.size()-1).getJob().getJobTitle();
			model.addAttribute("recommendByClickHistoryText", "Since you last clicked on " + lastJobClicked + ", below are some recommended jobs:");
			model.addAttribute("recommendedJobsByClickHistory", jobService.listRecommendedJobsByClickHistory(jobid_1));
			
			if (jobs_clickedByUser.size()-2 >= 0) {
				String jobid_2 = String.valueOf(jobs_clickedByUser.get(jobs_clickedByUser.size()-2).getJob().getId()).replace(" ", "+");
				String lastSecondJobClicked = jobs_clickedByUser.get(jobs_clickedByUser.size() - 2).getJob().getJobTitle();
				model.addAttribute("recommendByClickHistoryText2", "Since you last clicked on " + lastSecondJobClicked + ", below are some recommended jobs:");
				model.addAttribute("recommendedJobsByClickHistory2", jobService.listRecommendedJobsByClickHistory(jobid_2));
			}
		}
		else {
			List<Job> emptyJobs= new ArrayList<Job>();
			System.out.println("Empty:" + emptyJobs.isEmpty());
			
			model.addAttribute("recommendByClickHistoryText", "No click history present");
			model.addAttribute("message","Please check out jobs in the listing page for us to recommend to you");
			model.addAttribute("recommendedJobsByClickHistory", emptyJobs);
		}
		
		return "recommendation";
	}
	
}
