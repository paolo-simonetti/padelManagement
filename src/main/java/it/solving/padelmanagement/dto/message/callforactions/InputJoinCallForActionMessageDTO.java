package it.solving.padelmanagement.dto.message.callforactions;

import javax.validation.constraints.Positive;

public class InputJoinCallForActionMessageDTO {
	
	@Positive
	private String playerId;
	
	@Positive
	private String matchId;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public InputJoinCallForAction(@Positive String playerId, @Positive String matchId) {
		super();
		this.playerId = playerId;
		this.matchId = matchId;
	}

	public InputJoinCallForAction() {
		super();
	}
	
}
