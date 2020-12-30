package it.solving.padelmanagement.dto.message.callforactions;

import javax.validation.constraints.Positive;

public class InputCancelParticipationMessageDTO {

	@Positive
	private String matchId;
	
	@Positive
	private String playerId;

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public InputCancelParticipationMessageDTO(@Positive String matchId, @Positive String playerId) {
		super();
		this.matchId = matchId;
		this.playerId = playerId;
	}

	public InputCancelParticipationMessageDTO() {
		super();
	}
	
}
