package it.solving.padelmanagement.dto.message.update;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.web.multipart.MultipartFile;

public class UserUpdateMessageDTO {

	@NotBlank
	protected String id;
	
	@NotBlank
	protected String name;
	
	@NotBlank
	protected String surname;
	
	@NotBlank
	@Past
	protected String dateOfBirth;
	
	@NotBlank
	protected String username;
	
	@NotBlank
	@Min(6)
	protected String password;
	
	@NotBlank
	protected String mailAddress;
		
	protected static final String role="guest";
	
	@NotBlank
	protected String mobile;
	
	protected String proPicName;
		
	protected MultipartFile proPic;

	
	private Set<String> newClubProposalsIds=new HashSet<>();
	
	private Set<String> joinProposalsIds=new HashSet<>();

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

	public Set<String> getNewClubProposalsIds() {
		return newClubProposalsIds;
	}

	public void setNewClubProposalsIds(Set<String> newClubProposalsIds) {
		this.newClubProposalsIds = newClubProposalsIds;
	}

	public Set<String> getJoinProposalsIds() {
		return joinProposalsIds;
	}

	public void setJoinProposalsIds(Set<String> joinProposalsIds) {
		this.joinProposalsIds = joinProposalsIds;
	}

	public static String getRole() {
		return role;
	}

	public String getProPicName() {
		return proPicName;
	}

	public void setProPicName(String proPicName) {
		this.proPicName = proPicName;
	}

	public MultipartFile getProPic() {
		return proPic;
	}

	public void setLogo(MultipartFile proPic) {
		this.proPic = proPic;
	}

	public void addToNewClubProposalsIds(String id) {
		this.newClubProposalsIds.add(id);
	}
	
	public void removeFromNewClubProposalsIds(String id) {
		if(this.newClubProposalsIds.contains(id)) {
			this.newClubProposalsIds.remove(id);			
		}
	}
	
	public void addToJoinProposalsIds(String id) {
		this.joinProposalsIds.add(id);
	}
	
	public void removeFromJoinProposalsIds(String id) {
		if(this.joinProposalsIds.contains(id)) {
			this.joinProposalsIds.remove(id);			
		}
	}
	
	public UserUpdateMessageDTO(@NotBlank String id, @NotBlank String name, @NotBlank String surname,
			@NotBlank @Past String dateOfBirth, @NotBlank String username, @NotBlank @Min(6) String password,
			@NotBlank String mailAddress, @NotBlank String mobile, String proPicName, MultipartFile proPic,
			Set<String> newClubProposalsIds, Set<String> joinProposalsIds) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.username = username;
		this.password = password;
		this.mailAddress = mailAddress;
		this.mobile = mobile;
		this.proPicName = proPicName;
		this.proPic = proPic;
		this.newClubProposalsIds = newClubProposalsIds;
		this.joinProposalsIds = joinProposalsIds;
	}

	public UserUpdateMessageDTO() {
		super();
	}
	
	
}
