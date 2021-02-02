package sg.edu.iss.jinder.service;


import java.util.List; 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sg.edu.iss.jinder.model.Job;

public interface JobService {
	
	public List<Job> listall(String keyword);
	
	public List<Job> listresult(String keyword, int id);
	
	public Page<Job> findPaginated(List<Job> jobs,Pageable pageable);

}
