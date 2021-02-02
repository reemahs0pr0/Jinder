package sg.edu.iss.jinder.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sg.edu.iss.jinder.model.Job;
import sg.edu.iss.jinder.repo.JobRepository;

@Service
public class JobServiceImpl implements JobService {
	
	@Autowired
	JobRepository jrepo;

	@Override
	public Job findJobById(Integer id) {
		return jrepo.findById(id).get();
	}

	@Override
	public List<Job> listAll(String keyword) {
		if (keyword != null) {
			return (List<Job>)jrepo.search(keyword);
		}		
		return jrepo.findAll();
	}
	
	@Override
	public Page<Job> findPaginated(List<Job> jobs, Pageable pageable) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Job> list;
        
        if (jobs.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, jobs.size());
            list = jobs.subList(startItem, toIndex);
        }
        
        Page<Job> jobPage = new PageImpl<Job>(list, PageRequest.of(currentPage, pageSize), jobs.size());

        return jobPage;
	}
}
