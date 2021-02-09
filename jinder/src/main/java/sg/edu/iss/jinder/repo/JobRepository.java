package sg.edu.iss.jinder.repo;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.iss.jinder.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
	
	public List<Job> findAll();
	
	public Job findById(int id);

}
