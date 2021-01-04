package it.solving.padelmanagement.dto.message.callforactions;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class InputUpdateMissingPlayersMessageDTO {
	
	@Positive
	private String matchId;
	
	@PositiveOrZero
	@Max(3)
	private String missingPlayers;

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getMissingPlayers() {
		return missingPlayers;
	}

	public void setMissingPlayers(String missingPlayers) {
		this.missingPlayers = missingPlayers;
	}

	public InputUpdateMissingPlayersMessageDTO(@Positive String matchId,
			@PositiveOrZero @Max(3) String missingPlayers) {
		super();
		this.matchId = matchId;
		this.missingPlayers = missingPlayers;
	}

	public InputUpdateMissingPlayersMessageDTO() {
		super();
	}
	
}
