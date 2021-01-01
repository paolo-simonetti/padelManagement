package it.solving.padelmanagement.dto.message.member;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.web.multipart.MultipartFile;

public class InputUpdateMemberMessageDTO {

	@NotBlank
	private String id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String surname;
	
	@NotBlank
	@Past
	private String dateOfBirth;
	
	@NotBlank
	private String username;
	
	@NotBlank
	@Min(6)
	private String password;
	
	@NotBlank
	private String mailAddress;
		
	private static final String role="guest";
	
	@NotBlank
	private String mobile;
	
	@Min(1)
	@Max(7)
	private String userLevel;

	@NotBlank
	private String proPicName;
	
	@NotNull
	private MultipartFile proPic;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
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

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
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
	
	public InputUpdateMemberMessageDTO(@NotBlank String id, @NotBlank String name, @NotBlank String surname,
			@NotBlank @Past String dateOfBirth, @NotBlank String username, @NotBlank @Min(6) String password,
			@NotBlank String mailAddress, @NotBlank String mobile, @Min(1) @Max(7) String userLevel,
			@NotBlank String proPicName, @NotNull MultipartFile proPic) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.username = username;
		this.password = password;
		this.mailAddress = mailAddress;
		this.mobile = mobile;
		this.userLevel = userLevel;
		this.proPicName = proPicName;
		this.proPic = proPic;
	}

	public InputUpdateMemberMessageDTO() {
		super();
	}
	
}
