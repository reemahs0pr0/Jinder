package sg.edu.iss.jinder.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.edu.iss.jinder.model.Job_Clicked;
import sg.edu.iss.jinder.model.User;
import sg.edu.iss.jinder.model.User_Preference;

@Repository
public interface Job_ClickedRepository extends JpaRepository<Job_Clicked,Integer>{
	
	
	@Query("SELECT jc FROM Job_Clicked jc WHERE jc.user.id=:userid")
	public List<Job_Clicked> findJob_ClickedsbyUserId(@Param("userid") int user_id);
	
	@Query("SELECT jc FROM Job_Clicked jc WHERE jc.job.id=:jobid")
	public List<Job_Clicked> findJob_ClickedsbyJobId(@Param("jobid") int user_id);

}
