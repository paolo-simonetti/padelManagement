package it.solving.padelmanagement.dto;

import java.util.HashSet;
import java.util.Set;

public class PlayerDTO extends UserDTO {

	private String level;
	
	private static final String role="player";
	
	private Set<String> matchesIds=new HashSet<>();
	
	private Set<String> matchesJoinedIds=new HashSet<>();
	
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

	
	public Set<String> getMatchesJoinedIds() {
		return matchesJoinedIds;
	}

	public void setMatchesJoinedIds(Set<String> matchesJoinedIds) {
		this.matchesJoinedIds = matchesJoinedIds;
	}

	public void addToMatchesIds(String id) {
		this.matchesIds.add(id);
	}
	
	public void removeFromMatchesIds(String id) {
		if(this.matchesIds.contains(id)) {
			this.matchesIds.remove(id);
		}
	}

	public void addToMatchesJoinedIds(String id) {
		this.matchesJoinedIds.add(id);
	}
	
	public void removeFromMatchesJoinedIds(String id) {
		if(this.matchesJoinedIds.contains(id)) {
			this.matchesJoinedIds.remove(id);
		}
	}

	public PlayerDTO() {
		super();
	}

	
}
