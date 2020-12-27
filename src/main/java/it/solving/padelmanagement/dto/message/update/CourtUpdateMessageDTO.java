package it.solving.padelmanagement.dto.message.update;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class CourtUpdateMessageDTO {

	@NotBlank
	@Positive
	private String id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	@Positive
	private String clubId;
	
	@NotBlank
	private String mayBeReserved;
	
	private Set<String> matchesIds=new HashSet<>();

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

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}
	
	public String getMayBeReserved() {
		return mayBeReserved;
	}

	public void setMayBeReserved(String mayBeReserved) {
		this.mayBeReserved = mayBeReserved;
	}

	public void addToMatchesIds(String matchId) {
		this.matchesIds.add(matchId);
	}
	
	public void removeFromMatches(String matchId) {
		if (this.matchesIds.contains(matchId)) {
			this.matchesIds.remove(matchId);
		}
	}
	
	public Set<String> getMatchesIds() {
		return matchesIds;
	}

	public void setMatchesIds(Set<String> matchesIds) {
		this.matchesIds = matchesIds;
	}

	public CourtUpdateMessageDTO(@NotBlank @Positive String id, @NotBlank String name,
			@NotBlank @Positive String clubId) {
		super();
		this.id = id;
		this.name = name;
		this.clubId = clubId;
	}

	public CourtUpdateMessageDTO() {
		super();
	}
	
	
}
