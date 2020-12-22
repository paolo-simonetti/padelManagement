package it.solving.padelmanagement.dto;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {

	protected String id;
	
	protected String name;
	
	protected String surname;
	
	protected String dateOfBirth;
	
	protected String mailAddress;
		
	protected static final String role="guest";
	
	protected String mobile;
	
	protected Set<Byte> proPicFile = new HashSet<>();
	
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Set<Byte> getProPicFile() {
		return proPicFile;
	}

	public void setProPicFile(Set<Byte> proPicFile) {
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

	public static String getRole() {
		return role;
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
	
}
