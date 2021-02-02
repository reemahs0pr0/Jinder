package sg.edu.iss.jinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.jinder.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository urepo;

	@Override
	public boolean resumeUploaded(int id) {
		if(urepo.getResumeById(id) != null) {
			return true;
		}
		return false;
	}

}
