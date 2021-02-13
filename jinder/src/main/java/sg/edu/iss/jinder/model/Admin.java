package sg.edu.iss.jinder.model;

import java.time.LocalDate;

import javax.persistence.Entity;

@Entity
public class Admin extends User {
	private String pictureUrl; 
	private LocalDate employmentDate;
	
	public Admin() {
		super();
	}
	
	public Admin(String fullName, String userName, String password, String emailAddress, String address, String phoneNo) {
		super(fullName, userName, password, emailAddress, address, phoneNo);
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public LocalDate getEmploymentDate() {
		return employmentDate;
	}

	public void setEmploymentDate(LocalDate employmentDate) {
		this.employmentDate = employmentDate;
	} 

}
