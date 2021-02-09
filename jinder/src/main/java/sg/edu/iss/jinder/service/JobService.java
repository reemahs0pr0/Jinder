package sg.edu.iss.jinder.service;

import java.util.List; 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sg.edu.iss.jinder.model.Job;
import sg.edu.iss.jinder.model.Job_Clicked;
import sg.edu.iss.jinder.model.User;

public interface JobService {
	
	public List<Job> listAll(String keyword);
	
	public List<Job> listResult(String keyword, int id);

	public Job findJobById(Integer id);
  
	public Page<Job> findPaginated(List<Job> jobs, Pageable pageable);

	public List<Job_Clicked> findJob_ClickedsbyUserId(int id);
	
	public List<Job_Clicked> findJob_ClickedsbyJobId(int id);
	
	public List<Job> listRecommendedJobsByClickHistory(User user);
	
	public List<Job> listRecommendedJobsBySurvey(User user);

}
