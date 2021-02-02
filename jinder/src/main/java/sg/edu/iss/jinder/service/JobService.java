package sg.edu.iss.jinder.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sg.edu.iss.jinder.model.Job;

public interface JobService {
	
	 public Job findJobbyId(Integer id);
	 public List<Job> listAll(String keyword);
	 public Page<Job> findPaginated(List<Job> jobs, Pageable pageable);
}
