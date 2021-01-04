package it.solving.padelmanagement.dto.message.createpadelmatch;

import javax.validation.constraints.Positive;

public class InputDeleteMessageDTO {
	
	@Positive
	private String matchId;

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public InputDeleteMessageDTO(@Positive String matchId) {
		super();
		this.matchId = matchId;
	}

	public InputDeleteMessageDTO() {
		super();
	}

}
