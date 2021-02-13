package sg.edu.iss.jinder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.edu.iss.jinder.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
	
	@Query("SELECT a FROM Admin a WHERE a.userName=:userName")
	Admin findByUserName(@Param("userName")String userName);

	@Query("SELECT a FROM Admin a WHERE a.emailAddress=:emailAddress")
	Admin findByEmail(@Param("emailAddress")String emailAddress);

	@Query("SELECT a From Admin a WHERE a.userName=:userName and a.password=:password")
	Admin findByUsernameAndPassword(@Param("userName")String userName,@Param("password")String password);
	
}
