package it.solving.padelmanagement.dto.message.update;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

public class SlotUpdateMessageDTO {
	
	@Positive
	private String id;
	
	@Min(8)
	@Max(29)
	private String hour;
	
	@Positive
	@Max(30)
	private String minute;
	
	private Set<String> matchesIds=new HashSet<>();
	
	// TODO: scrivere un validator per trovare solo istanze dell'enum

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

	public SlotUpdateMessageDTO(String id, String hour, String minute, Set<String> matchesIds) {
		super();
		this.id = id;
		this.hour = hour;
		this.minute = minute;
		this.matchesIds = matchesIds;
	}

	public SlotUpdateMessageDTO() {
		super();
	}
	
}
