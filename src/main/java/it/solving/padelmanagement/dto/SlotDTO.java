package it.solving.padelmanagement.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SlotDTO {
	
	@JsonIgnore
	private String id;
	
	private String hour;
	
	private String minute;
	
	@JsonIgnore
	private Set<String> matchesIds=new HashSet<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}
	
	public Set<String> getMatchesIds() {
		return matchesIds;
	}

	public void setMatchesIds(Set<String> matchesIds) {
		this.matchesIds = matchesIds;
	}

	public void addToMatchesIds(String id) {
		this.matchesIds.add(id);
	}
	
	public void removeFromMatchesIds(String id) {
		if(this.matchesIds.contains(id)) {
			this.matchesIds.remove(id);
		}
	}
	
	public SlotDTO(@Min(1) @Max(29) String id, @Min(8) @Max(22) String hour, @Min(0) @Max(30) String minute) {
		super();
		this.id = id;
		this.hour = hour;
		this.minute = minute;
	}

	public SlotDTO() {
		super();
	}
	
	
	
	
}
