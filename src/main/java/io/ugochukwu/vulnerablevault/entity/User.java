package io.ugochukwu.vulnerablevault.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserID")
	protected int id;
	
	@Column(name = "email")
//	@NotNull(message = "is required")
//	@Pattern(regexp ="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", message="Please enter a valid email address (e.g., username@example.com)")
	protected String email;

	@Column(name = "PasswordHash")
	protected String password;
	
	transient protected String confirmPassword;

	@Column(name = "FullName")
//	@NotNull(message = "is required")
//	@Size(min = 3, message="is required")
//	@Pattern(regexp ="[a-zA-Z]+", message="incorrect format")
	protected String fullname;

	@Column(name = "RoleID")
	protected int roleID = 0;

	public User() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String password) {
		this.confirmPassword = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String name) {
		this.fullname = name;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	@Override
	public String toString() {
		return "user = [id: " + getId() + ", email: " + getEmail() + ", name: " + getFullname() + ", pass: " + getPassword() + ", pass2: " + getConfirmPassword() + ", role: " + getRoleID() + "]";
	}
}
