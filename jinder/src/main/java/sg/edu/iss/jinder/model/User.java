package sg.edu.iss.jinder.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String fullname;
	private String username;
	private String password;
	private String email;
	private String resumeUrl;
	
	
	public User() {
		super();
	}

	public User(String fullname, String username, String password, String email) {
		super();
		this.fullname = fullname;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getResumeUrl() {
		return resumeUrl;
	}

	public void setResumeUrl(String resumeUrl) {
		this.resumeUrl = resumeUrl;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fullname=" + fullname + ", username=" + username + ", password=" + password
				+ ", resumeUrl=" + resumeUrl + "]";
	}
	
}
