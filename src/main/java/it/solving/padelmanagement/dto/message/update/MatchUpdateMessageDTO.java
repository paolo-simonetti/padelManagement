package it.solving.padelmanagement.dto.message.update;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class MatchUpdateMessageDTO {

	@Positive
	private String id;
	
	@FutureOrPresent
	private String date;
	
	private String payed;
	
	@Min(0)
	@Max(3)
	private String missingPlayers;
	
	//TODO: scrivere una validazione apposita per size+missingPlayers==4
	@Min(0)
	@Max(3)
	private Set<String> otherPlayersIds=new HashSet<>();

	@Positive
	private String creatorId;

	@Size(min=3, max=29)
	private Set<String> slotsIds=new HashSet<>();

	@Positive
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

	public MatchUpdateMessageDTO(@Positive String id, @FutureOrPresent String date, String payed,
			@Min(0) @Max(3) String missingPlayers, @Min(0) @Max(3) Set<String> otherPlayersIds,
			@Positive String creatorId, @Size(min = 3, max = 28) Set<String> slotsIds, @Positive String courtId) {
		super();
		this.id = id;
		this.date = date;
		this.payed = payed;
		this.missingPlayers = missingPlayers;
		this.otherPlayersIds = otherPlayersIds;
		this.creatorId = creatorId;
		this.slotsIds = slotsIds;
		this.courtId = courtId;
	}

	public MatchUpdateMessageDTO() {
		super();
	}
	
	
}
