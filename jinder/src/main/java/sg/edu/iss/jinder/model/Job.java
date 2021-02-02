package sg.edu.iss.jinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="jobs")
public class Job {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;	
	private String jobTitle;
	private String companyName;
	@Column(columnDefinition = "TEXT")
	private String jobDesc;
	@Column(columnDefinition = "TEXT")
	private String jobSkills;
	private String jobAppUrl;
	
	public Job() {
		super();
	}

	public Job(String jobTitle, String companyName, String jobDesc, String jobSkills, String jobAppUrl) {
		super();
		this.jobTitle = jobTitle;
		this.companyName = companyName;
		this.jobDesc = jobDesc;
		this.jobSkills = jobSkills;
		this.jobAppUrl = jobAppUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public String getJobSkills() {
		return jobSkills;
	}

	public void setJobSkills(String jobSkills) {
		this.jobSkills = jobSkills;
	}

	public String getJobAppUrl() {
		return jobAppUrl;
	}

	public void setJobAppUrl(String jobAppUrl) {
		this.jobAppUrl = jobAppUrl;
	}

	@Override
	public String toString() {
		return "Job [id=" + id + ", jobTitle=" + jobTitle + ", companyName=" + companyName + ", jobDesc=" + jobDesc
				+ ", jobSkills=" + jobSkills + ", jobAppUrl=" + jobAppUrl + "]";
	}
}
