package sg.edu.iss.jinder.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class User_Preference {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate surveyDate;
	
	@ElementCollection
	@Size(min=1,max=2,message = "Maximum 2 selections only")
	private List <String> preferredJobRole;
	
	@ElementCollection
	@Size(min=1,max=2,message = "Maximum 2 selections only")
	private List <String> preferredJobTitle;
	private String preferredTechnologies;
	
	@OneToOne
	private User user;
	
	
	public User_Preference() {
		super();
	}
  

	public User_Preference(LocalDate surveyDate, List<String> preferredJobRole, List<String> preferredJobTitle,
			String preferredTechnologies, User user) {
		super();
		this.surveyDate = surveyDate;
		this.preferredJobRole = preferredJobRole;
		this.preferredJobTitle = preferredJobTitle;
		this.preferredTechnologies = preferredTechnologies;
		this.user = user;
	}

	

	public User_Preference(List<String> preferredJobRole, List<String> preferredJobTitle,
			String preferredTechnologies) {
		super();
		this.preferredJobRole = preferredJobRole;
		this.preferredJobTitle = preferredJobTitle;
		this.preferredTechnologies = preferredTechnologies;
	}
	
	


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public LocalDate getSurveyDate() {
		return surveyDate;
	}


	public void setSurveyDate(LocalDate surveyDate) {
		this.surveyDate = surveyDate;
	}


	public List<String> getPreferredJobRole() {
		return preferredJobRole;
	}


	public void setPreferredJobRole(List<String> preferredJobRole) {
		this.preferredJobRole = preferredJobRole;
	}


	public List<String> getPreferredJobTitle() {
		return preferredJobTitle;
	}


	public void setPreferredJobTitle(List<String> preferredJobTitle) {
		this.preferredJobTitle = preferredJobTitle;
	}


	public String getPreferredTechnologies() {
		return preferredTechnologies;
	}


	public void setPreferredTechnologies(String preferredTechnologies) {
		this.preferredTechnologies = preferredTechnologies;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "User_Preference [surveyDate=" + surveyDate + ", preferredJobRole=" + preferredJobRole
				+ ", preferredJobTitle=" + preferredJobTitle + ", preferredTechnologies=" + preferredTechnologies
				+ ", user=" + user + "]";
	}
	


	

	
	
}

