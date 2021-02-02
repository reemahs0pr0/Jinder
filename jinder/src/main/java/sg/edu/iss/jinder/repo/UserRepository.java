package sg.edu.iss.jinder.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.jinder.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
