package sg.edu.iss.jinder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.jinder.model.JobSeeker;

@Repository
public interface JobSeekerRepository extends JpaRepository<JobSeeker,Integer>{

	@Query("SELECT js.resumeUrl from JobSeeker js WHERE js.id=:id")
	public String getResumeById(@Param("id")int id);

	@Query("SELECT js FROM JobSeeker js WHERE js.userName=:userName")
	JobSeeker findByUserName(@Param("userName")String userName);

	@Query("SELECT js FROM JobSeeker js WHERE js.emailAddress=:emailAddress")
	JobSeeker findByEmail(@Param("emailAddress")String emailAddress);

	@Query("SELECT js From JobSeeker js WHERE js.userName=:userName and js.password=:password")
	JobSeeker findByUsernameAndPassword(@Param("userName")String userName,@Param("password")String password);
	
	@Transactional
	@Modifying
	@Query("UPDATE JobSeeker js SET js.resumeUrl=:resumeUrl WHERE js.id=:id")
	void updateResume(@Param("resumeUrl")String resumeUrl,@Param("id")int id);
	
}
