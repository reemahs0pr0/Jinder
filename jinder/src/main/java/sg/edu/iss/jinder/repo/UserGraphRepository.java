package sg.edu.iss.jinder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sg.edu.iss.jinder.model.User_Graph;

@Repository
public interface UserGraphRepository extends JpaRepository<User_Graph, Integer> {
}