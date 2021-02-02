package sg.edu.iss.jinder.model;
 
import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jobs")
public class Job {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)

	private int id;	
	private String jobTitle;
	private String companyName;
	@Column(columnDefinition = "TEXT")
	private String jobDescription;
	@Column(columnDefinition = "TEXT")
	private String skills;
	private String jobAppUrl;

	public Job() {
		super();
	}

	public Job(String jobTitle, String companyName, String jobDescription, String skills, String jobAppUrl) {
		super();
		this.jobTitle = jobTitle;
		this.companyName = companyName;
		this.jobDescription = jobDescription;
		this.skills = skills;
		this.jobAppUrl = jobAppUrl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getJobDescription() {
		return jobDescription;
  }
  
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
  
	public String getSkills() {
		return skills;
	}
  
	public void setSkills(String skills) {
		this.skills = skills;
	}
  
	public String getJobAppUrl() {
		return jobAppUrl;
	}
  
	public void setJobAppUrl(String jobAppUrl) {
		this.jobAppUrl = jobAppUrl;
	}
  
	@Override
	public String toString() {
		return "Job [id=" + id + ", jobTitle=" + jobTitle + ", companyName=" + companyName + ", jobDescription=" + jobDescription
				+ ", skills=" + skills + ", jobAppUrl=" + jobAppUrl + "]";
	}

}
