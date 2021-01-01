package it.solving.padelmanagement.dto.message.insert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class UserInsertMessageDTO {
	
	@NotBlank
	protected String name;
	
	@NotBlank
	protected String surname;
	
	@NotBlank
	protected String dateOfBirth;
	
	@NotBlank
	protected String mailAddress;
	
	@NotBlank
	protected String username;
	
	@NotBlank
	@Size(min=6)
	protected String password;
	
	protected static final String role="guest";
	
	@NotBlank
	protected String mobile;

	@NotBlank
	protected String proPicName;
	
	@NotNull
	protected MultipartFile proPic;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public static String getRole() {
		return role;
	}

	public MultipartFile getProPic() {
		return proPic;
	}

	public void setProPic(MultipartFile proPic) {
		this.proPic = proPic;
	}

	public String getProPicName() {
		return proPicName;
	}

	public void setProPicName(String proPicName) {
		this.proPicName = proPicName;
	}
	
	public UserInsertMessageDTO(@NotBlank String name, @NotBlank String surname, @NotBlank String dateOfBirth,
			@NotBlank String mailAddress, @NotBlank String username, @NotBlank @Size(min = 6) String password,
			@NotBlank String mobile, @NotBlank String proPicName, @NotNull MultipartFile proPic) {
		super();
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.mailAddress = mailAddress;
		this.username = username;
		this.password = password;
		this.mobile = mobile;
		this.proPicName = proPicName;
		this.proPic = proPic;
	}

	public UserInsertMessageDTO() {
		super();
	}
	
	
}
