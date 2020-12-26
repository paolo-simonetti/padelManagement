package it.solving.padelmanagement.dto;

import java.util.HashSet;
import java.util.Set;

public class MatchDTO {

	private String id;
	
	private String date;
	
	private String payed;
	
	private String missingPlayers;
	
	private Set<String> otherPlayersIds=new HashSet<>();

	private String creatorId;

	private Set<String> slotsIds=new HashSet<>();

	private String courtId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPayed() {
		return payed;
	}

	public void setPayed(String payed) {
		this.payed = payed;
	}

	public String getMissingPlayers() {
		return missingPlayers;
	}

	public void setMissingPlayers(String missingPlayers) {
		this.missingPlayers = missingPlayers;
	}

	public Set<String> getOtherPlayersIds() {
		return otherPlayersIds;
	}

	public void setOtherPlayersIds(Set<String> otherPlayersIds) {
		this.otherPlayersIds = otherPlayersIds;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Set<String> getSlotsIds() {
		return slotsIds;
	}

	public void setSlotsIds(Set<String> slotsIds) {
		this.slotsIds = slotsIds;
	}

	public String getCourtId() {
		return courtId;
	}

	public void setCourtId(String courtId) {
		this.courtId = courtId;
	}

	public void addToOtherPlayersIds(String id) {
		this.otherPlayersIds.add(id);
	}
	
	public void removeFromOtherPlayerIds(String id) {
		if(this.otherPlayersIds.contains(id)) {
			this.otherPlayersIds.remove(id);			
		}
	}
	
	public void addToSlotsIds(String id) {
		this.slotsIds.add(id);
	}
	
	public void removeFromSlotsIds(String id) {
		this.slotsIds.remove(id);
	}

	public MatchDTO(String id, String date, String payed, String missingPlayers, 
			String creatorId, Set<String> slotsIds, String courtId) {
		super();
		this.id = id;
		this.date = date;
		this.payed = payed;
		this.missingPlayers = missingPlayers;
		this.creatorId = creatorId;
		this.slotsIds = slotsIds;
		this.courtId = courtId;
	}

	public MatchDTO() {
		super();
	}
	
	
	
}
