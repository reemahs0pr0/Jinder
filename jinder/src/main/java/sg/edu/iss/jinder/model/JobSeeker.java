package sg.edu.iss.jinder.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class JobSeeker extends User {
	private String pictureUrl; 
	private LocalDate registrationDate; 
	private String resumeUrl;

	@OneToOne(mappedBy = "user")
	private User_Preference uPreference;
	
    @OneToMany(mappedBy = "user")
	private List<Job_Clicked> job_Clickeds;
	
	
	public JobSeeker() {
		super();
	}
	
	public JobSeeker(String fullName, String userName, String password, String emailAddress, String address, String phoneNo) {
		super(fullName, userName, password, emailAddress, address, phoneNo);
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getResumeUrl() {
		return resumeUrl;
	}

	public void setResumeUrl(String resumeUrl) {
		this.resumeUrl = resumeUrl;
	}
	
	public User_Preference getuPreference() {
		return uPreference;
	}

	public void setuPreference(User_Preference uPreference) {
		this.uPreference = uPreference;
	}
	
	public List<Job_Clicked> getJob_Clickeds() {
		return job_Clickeds;
	}

	public void setJob_Clickeds(List<Job_Clicked> job_Clickeds) {
		this.job_Clickeds = job_Clickeds;
	}
	
}
