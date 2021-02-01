package sg.edu.iss.jinder.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class User {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String fullname;
	private String username;
	private String password;
	private String emailAddress;
	private String address;
	private int phoneNo;
	private String pictureUrl;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate registrationDate;
	private String resumeUrl;
	
	
	public User() {
		super();
	}

	public User(String fullname, String username, String password, String emailAddress, String address, int phoneNo,
			LocalDate registrationDate) {
		super();
		this.fullname = fullname;
		this.username = username;
		this.password = password;
		this.emailAddress = emailAddress;
		this.address = address;
		this.phoneNo = phoneNo;
		this.registrationDate = registrationDate;
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
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(int phoneNo) {
		this.phoneNo = phoneNo;
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
	
	@Override
	public String toString() {
		return "User [id=" + id + ", fullname=" + fullname + ", username=" + username + ", password=" + password
				+ ", emailAddress=" + emailAddress + ", address=" + address + ", phoneNo=" + phoneNo + ", pictureUrl="
				+ pictureUrl + ", registrationDate=" + registrationDate + ", resumeUrl=" + resumeUrl + "]";
	}
	
}
