package it.solving.padelmanagement.dto;

import java.util.HashSet;
import java.util.Set;

public class PlayerDTO extends UserDTO {

	private String level;
	
	private static final String role="player";
	
	private Set<String> matchesIds=new HashSet<>();
	
	private String clubId;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Set<String> getMatchesIds() {
		return matchesIds;
	}

	public void setMatchesIds(Set<String> matchesIds) {
		this.matchesIds = matchesIds;
	}

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}

	public static String getRole() {
		return role;
	}
	
	public void addToMatchesIds(String id) {
		this.matchesIds.add(id);
	}
	
	public void removeFromMatchesIds(String id) {
		if(this.matchesIds.contains(id)) {
			this.matchesIds.remove(id);
		}
	}

	public PlayerDTO(String id, String name, String surname, String dateOfBirth, String mailAddress, String mobile,
			String level, Set<String> matchesIds, String clubId) {
		super(id, name, surname, dateOfBirth, mailAddress, mobile);
		this.level = level;
		this.matchesIds = matchesIds;
		this.clubId = clubId;
	}

	public PlayerDTO(String id, String name, String surname, String dateOfBirth, String mailAddress, String mobile) {
		super(id, name, surname, dateOfBirth, mailAddress, mobile);
	}

	public PlayerDTO() {
		super();
	}

	
}
