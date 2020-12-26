package it.solving.padelmanagement.dto.message.insert;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class MatchInsertMessageDTO {

	@FutureOrPresent
	private String date;
	
	@AssertFalse
	private String payed;
	
	@Min(0)
	@Max(3)
	private String missingPlayers;
	
	@Positive
	private String creatorId;

	@Size(min=3, max=29)
	private Set<String> slotsIds=new HashSet<>();

	@Positive
	private String courtId;

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

	public void addToSlotsIds(String id) {
		this.slotsIds.add(id);
	}
	
	public void removeFromSlotsIds(String id) {
		this.slotsIds.remove(id);
	}

	public MatchInsertMessageDTO(@Future String date, @AssertFalse String payed, @Min(0) @Max(3) String missingPlayers,
			@Positive String creatorId, @Size(min = 3, max = 28) Set<String> slotsIds, @Positive String courtId) {
		super();
		this.date = date;
		this.payed = payed;
		this.missingPlayers = missingPlayers;
		this.creatorId = creatorId;
		this.slotsIds = slotsIds;
		this.courtId = courtId;
	}

	public MatchInsertMessageDTO() {
		super();
	}
		
}
