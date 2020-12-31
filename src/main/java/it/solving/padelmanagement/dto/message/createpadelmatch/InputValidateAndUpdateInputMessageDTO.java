package it.solving.padelmanagement.dto.message.createpadelmatch;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class InputValidateAndUpdateInputMessageDTO extends InputValidateAndInsertInputMessageDTO {
	
	@Positive
	private String matchId;

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public InputValidateAndUpdateInputMessageDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InputValidateAndUpdateInputMessageDTO(@Positive String playerId, @NotBlank String date,
			@Min(8) @Max(20) String hour, @Positive @Max(30) String minute, @Min(1) @Max(14) String durationHour,
			@Positive @Max(30) String durationMinute, @Positive String courtId,
			@Positive String matchId) {
		super(playerId, date, hour, minute, durationHour, durationMinute, courtId);
		this.matchId = matchId;
	}

	

	
	
}
