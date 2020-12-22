package it.solving.padelmanagement.dto.message.insert;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserInsertMessageDTO {
	
	@NotBlank
	protected String name;
	
	@NotBlank
	protected String surname;
	
	@NotBlank
	protected String dateOfBirth;
	
	@NotBlank
	@Email
	protected String mailAddress;
		
	protected static final String role="guest";
	
	@NotBlank
	protected String mobile;

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public static String getRole() {
		return role;
	}

	public UserInsertMessageDTO(String name, String surname, String dateOfBirth, String mailAddress, String mobile) {
		super();
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.mailAddress = mailAddress;
		this.mobile = mobile;
	}

	public UserInsertMessageDTO() {
		super();
	}
	
	
	
}