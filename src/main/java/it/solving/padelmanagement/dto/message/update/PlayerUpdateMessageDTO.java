package it.solving.padelmanagement.dto.message.update;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

public class PlayerUpdateMessageDTO extends UserUpdateMessageDTO {
	
	@Min(1)
	@Max(7)
	private String level;
	
	private static final String role="player";
	
	private Set<String> matchesIds=new HashSet<>();

	private Set<String> matchesJoinedIds=new HashSet<>();
	
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

	public static String getRole() {
		return role;
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
		this.matchesIds.add(id);
	}
	
	public void removeFromMatchesJoinedIds(String id) {
		if(this.matchesIds.contains(id)) {
			this.matchesIds.remove(id);
		}
	}

	public PlayerUpdateMessageDTO(@NotBlank String id, @NotBlank String name, @NotBlank String surname,
			@NotBlank @Past String dateOfBirth, @NotBlank String mailAddress, @NotBlank String mobile,
			@Min(1) @Max(7) String level, Set<String> matchesIds) {
		super(id, name, surname, dateOfBirth, mailAddress, mobile);
		this.level = level;
		this.matchesIds = matchesIds;
	}

	public PlayerUpdateMessageDTO() {
		super();
	}
	
	
	
}
