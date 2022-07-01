package my.project.acspring.models.entity;

import javax.validation.constraints.NotBlank;

import my.project.acspring.models.validation.PasswordMatch;


@PasswordMatch
public class UserDto {

	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String confirmPassword;
	
	private Role role;
	

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


	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}


	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

}
