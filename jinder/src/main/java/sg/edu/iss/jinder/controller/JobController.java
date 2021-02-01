package sg.edu.iss.jinder.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.iss.jinder.model.Job;
import sg.edu.iss.jinder.service.JobService;
import sg.edu.iss.jinder.service.JobServiceImpl;


@Controller
@RequestMapping("/job")
public class JobController

{
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private void setJobService(JobServiceImpl jobServiceImpl)
	{
		this.jobService=jobServiceImpl;
	}
	
	@RequestMapping(value="/list")
	public String jobListings(Model model,@Param("keyword")String keyword, @RequestParam("page") Optional<Integer> page, 
			@RequestParam("size") Optional<Integer> size)
	{
		List<Job> jobs= jobService.listall(keyword);
		int currentPage= page.orElse(1);
		int pageSize=size.orElse(12);
	
		Page<Job> jobPage=jobService.findPaginated(jobs, PageRequest.of(currentPage-1, pageSize));
	
		int totalPages= jobPage.getTotalPages();
		System.out.print("Total page:"+totalPages);
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

}
