package it.solving.padelmanagement.dto.message.createpadelmatch;

import javax.validation.constraints.Positive;

public class InputDeleteMessageDTO {
	
	@Positive
	private String creatorId;
	
	@Positive
	private String matchId;

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public InputDeleteMessageDTO(@Positive String creatorId, @Positive String matchId) {
		super();
		this.creatorId = creatorId;
		this.matchId = matchId;
	}

	public InputDeleteMessageDTO() {
		super();
	}

}
