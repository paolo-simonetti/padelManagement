package it.solving.padelmanagement.dto.message.createpadelmatch;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class InputValidateAndInsertInputMessageDTO extends InputVerifyAvailabilityMessageDTO {
	
	@Min(0)
	@Max(3)
	private String missingPlayers;
	
	@Positive
	protected String courtId;

	public String getMissingPlayers() {
		return missingPlayers;
	}

	public void setMissingPlayers(String missingPlayers) {
		this.missingPlayers = missingPlayers;
	}

	public String getCourtId() {
		return courtId;
	}

	public void setCourtId(String courtId) {
		this.courtId = courtId;
	}

	public InputValidateAndInsertInputMessageDTO() {
		super();
	}

	public InputValidateAndInsertInputMessageDTO(@Positive String playerId, @NotBlank String date,
			@Min(8) @Max(20) String hour, @Positive @Max(30) String minute, @Min(1) @Max(14) String durationHour,
			@Positive @Max(30) String durationMinute, @Min(0) @Max(3) String missingPlayers, @Positive String courtId) {
		super(playerId, date, hour, minute, durationHour, durationMinute);
		this.missingPlayers=missingPlayers;
		this.courtId=courtId;
	}

	public InputValidateAndInsertInputMessageDTO(@Positive String playerId, @NotBlank String date,
			@Min(8) @Max(20) String hour, @Positive @Max(30) String minute, @Min(1) @Max(14) String durationHour,
			@Positive @Max(30) String durationMinute, @Positive String courtId) {
		super(playerId, date, hour, minute, durationHour, durationMinute);
		this.courtId = courtId;
	}

	
	
	
}
