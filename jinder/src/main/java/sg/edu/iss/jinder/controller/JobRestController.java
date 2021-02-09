package sg.edu.iss.jinder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.iss.jinder.model.Job;
import sg.edu.iss.jinder.service.JobService;
import sg.edu.iss.jinder.service.JobServiceImpl;

@RestController
@RequestMapping(path = "/api/jobs/") 
class JobRestController {
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private void setJobService(JobServiceImpl jobServiceImpl) {
		this.jobService=jobServiceImpl;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Job> getJobs() { 
		return jobService.listAll(null);
	}

	@RequestMapping(path = "{query}", method = RequestMethod.GET)
	public List<Job> searchJobs(@PathVariable(name = "query") String query) {
		return jobService.listAll(query);
	}
	
	@RequestMapping(path = "sorted/{id}", method = RequestMethod.GET)
	public List<Job> getSortedJobs(@PathVariable(name = "id") String id) { 
		return jobService.listResult(null, Integer.valueOf(id));
	}

	@RequestMapping(path = "sorted/{id}/{query}", method = RequestMethod.GET)
	public List<Job> searchSortedJobs(@PathVariable(name = "query") String query, 
			@PathVariable(name = "id") String id) {
		return jobService.listResult(query, Integer.valueOf(id));
	}
	
}
