package sg.edu.iss.jinder.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "job_clicked")
public class Job_Clicked {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;	
	
	@ManyToOne
	private JobSeeker user;
	
	@ManyToOne
	private Job job;
	
	public Job_Clicked() {
		super();
	}

	public Job_Clicked(int id, JobSeeker user, Job job) {
		super();
		this.id = id;
		this.user = user;
		this.job = job;
	}



	public Job_Clicked(JobSeeker user, Job job) {
		super();
		this.user = user;
		this.job = job;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public JobSeeker getUser() {
		return user;
	}

	public void setUser(JobSeeker user) {
		this.user = user;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	@Override
	public String toString() {
		return "Job_Clicked [id=" + id + ", user=" + user + ", job=" + job + "]";
	}
	
	
	
	


}
