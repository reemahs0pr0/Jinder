package sg.edu.iss.jinder.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sg.edu.iss.jinder.model.User;
import sg.edu.iss.jinder.model.User_Preference;

@Repository
public interface UserPreferenceRepository extends JpaRepository<User_Preference,Integer>{

	@Query("SELECT up FROM User_Preference up WHERE up.user.id=:id")
	public User_Preference finduserprefByUserId(@Param("id") int user_id);
}
