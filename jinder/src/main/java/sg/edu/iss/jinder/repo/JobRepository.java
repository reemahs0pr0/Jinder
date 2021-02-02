package sg.edu.iss.jinder.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.iss.jinder.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
	
	@Query("SELECT j FROM Job j WHERE CONCAT(j.jobTitle, ' ',j.jobDesc, ' ', "
			+ "j.companyName, ' ', j.jobSkills, ' ', j.jobAppUrl) LIKE %?1%")
	public List<Job> search(String keyword);
}
