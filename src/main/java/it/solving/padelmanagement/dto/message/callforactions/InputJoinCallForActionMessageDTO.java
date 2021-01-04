package it.solving.padelmanagement.dto.message.callforactions;

import javax.validation.constraints.Positive;

public class InputJoinCallForActionMessageDTO {
	
	@Positive
	private String matchId;

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public InputJoinCallForActionMessageDTO(@Positive String matchId) {
		super();
		this.matchId = matchId;
	}

	public InputJoinCallForActionMessageDTO() {
		super();
	}
	
}
