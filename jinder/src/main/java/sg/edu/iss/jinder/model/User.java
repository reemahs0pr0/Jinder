package sg.edu.iss.jinder.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.MappedSuperclass;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id; 
	@NotEmpty(message = "Please fill in full name")
	@Size(min = 2, message = "Full name must be more than 2 characters")
	@Size(max = 50, message = "Full name cannot be more than 50 characters")
	@Pattern(regexp = "[a-zA-Z0-9 ]+", message = "Full name should not include special characters")
	private String fullName; 
	@NotEmpty(message = "Please fill in user name")
	@Size(min =2, message = "User name must be more than 2 characters")
	@Size(max = 50, message = "User name cannot be more than 50 characters")
	@Pattern(regexp = "[a-zA-Z0-9]+", message = "User name should not include special characters")
	private String userName; 
	@NotEmpty(message = "Please fill in password")
	@Size(min = 2, message = "Password must be more than 2 characters")
	@Size(max = 50, message = "Password cannot be more than 50 characters")
	private String password; 
	@NotEmpty(message = "Please fill in email address")
	@Email(message = "Please enter a valid email")
	private String emailAddress;
	@NotEmpty(message = "Please fill in address")
	@Size(min = 2, message = "Address must be more than 2 characters")
	@Size(max = 50, message = "Address cannot be more than 50 characters")
	private String address; 
	@NotNull(message="Please fill in contact number")
	@Pattern(regexp = "^[0-9]{8}$",  message="Please enter valid phone number")
	private String phoneNo; 

	
	public User() {
		super();
	}

	public User(String fullName, String userName, String password, String emailAddress, String address, String phoneNo) {
		super();
		this.fullName = fullName;
		this.userName = userName;
		this.password = password;
		this.emailAddress = emailAddress;
		this.address = address;
		this.phoneNo = phoneNo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String  phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fullName=" + fullName + ", userName=" + userName + ", password=" + password
				+ ", emailAddress=" + emailAddress + ", address=" + address + ", phoneNo=" + phoneNo + "]";
	} 
	
}
