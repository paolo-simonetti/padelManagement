package it.solving.padelmanagement.dto;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

public class UserDTO {

	protected String id;
	
	protected String name;
	
	protected String surname;
	
	protected String dateOfBirth;
	
	protected String mailAddress;
		
	private String role;
	
	protected String mobile;
	
	protected Blob proPicFile;
	
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

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Blob getProPicFile() {
		return proPicFile;
	}

	public void setProPicFile(Blob proPicFile) {
		this.proPicFile = proPicFile;
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

	public UserDTO(String id, String name, String surname, String dateOfBirth, String mailAddress, String role,
			String mobile) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.mailAddress = mailAddress;
		this.role=role;
		this.mobile = mobile;
	}

	public UserDTO() {
		super();
	}
	
	
	
}
