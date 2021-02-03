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

	@Query("SELECT u FROM User u WHERE u.userName=:userName")
	User findByUserName(@Param("userName")String userName);

	@Query("SELECT u FROM User u WHERE u.emailAddress=:emailAddress")
	User findByEmail(@Param("emailAddress")String emailAddress);

	@Query("SELECT u From User u WHERE u.userName=:userName and u.password=:password")
	User findByUsernameAndPassword(@Param("userName")String userName,@Param("password")String password);
}
