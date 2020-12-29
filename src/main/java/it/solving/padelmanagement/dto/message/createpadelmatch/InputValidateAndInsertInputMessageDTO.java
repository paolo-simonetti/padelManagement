package it.solving.padelmanagement.dto.message.createpadelmatch;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class InputValidateAndInsertInputMessageDTO {
	
	@Min(0)
	@Max(3)
	private String missingPlayers;
	
	@Positive
	private String courtId;
	
	@NotNull
	private InputVerifyAvailabilityMessageDTO inputVerifyAvailabilityMessageDTO;

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

	public InputVerifyAvailabilityMessageDTO getInputVerifyAvailabilityMessageDTO() {
		return inputVerifyAvailabilityMessageDTO;
	}

	public void setInputVerifyAvailabilityMessageDTO(InputVerifyAvailabilityMessageDTO inputVerifyAvailabilityMessageDTO) {
		this.inputVerifyAvailabilityMessageDTO = inputVerifyAvailabilityMessageDTO;
	}

	public InputValidateAndInsertInputMessageDTO(@Min(0) @Max(3) String missingPlayers, @Positive String courtId,
			@NotNull InputVerifyAvailabilityMessageDTO inputVerifyAvailabilityMessageDTO) {
		super();
		this.missingPlayers = missingPlayers;
		this.courtId = courtId;
		this.inputVerifyAvailabilityMessageDTO = inputVerifyAvailabilityMessageDTO;
	}

	public InputValidateAndInsertInputMessageDTO() {
		super();
	}
	
	
	
}
