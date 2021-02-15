package sg.edu.iss.jinder.model;

import javax.persistence.Entity;

@Entity
public class Admin extends User {
	
	public Admin() {
		super();
	}
	
	public Admin(String fullName, String userName, String password, String emailAddress, String address, String phoneNo) {
		super(fullName, userName, password, emailAddress, address, phoneNo);
	}

}
