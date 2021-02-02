package sg.edu.iss.jinder.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.iss.jinder.service.JobService;
import sg.edu.iss.jinder.service.JobServiceImpl;

@Controller
@RequestMapping("/job")
public class JobController {
	@Autowired
	private JobService jobservice;
    
	@Autowired
	public void setJobService(JobServiceImpl jobServiceImpl) {
		this.jobservice = jobServiceImpl;
	}
	
	@RequestMapping(value = "/list") 
	public String catalog(Model model, @Param("keyword") String keyword, @RequestParam("page") Optional<Integer> page, 
			@RequestParam("size") Optional<Integer> size) {
		
		List<sg.edu.iss.jinder.model.Job> jobs = jobservice.listAll(keyword);
		
		int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<sg.edu.iss.jinder.model.Job> jobPage = jobservice.findPaginated(jobs, PageRequest.of(currentPage - 1, pageSize));

        int totalPages = jobPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
		model.addAttribute("jobs", jobPage);
		model.addAttribute("keyword", keyword);
		
		return "jobs";
	}

	@RequestMapping(value = "/detail/{id}")
	public String showJob(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("job", jobservice.findJobbyId(id));
		return "jobdetail";	
	}
}
