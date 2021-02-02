package sg.edu.iss.jinder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.edu.iss.jinder.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	
	@Query("SELECT u.resumeUrl from User u WHERE u.id=:uid")
	public String getResumeById(@Param("uid")int id);

}
