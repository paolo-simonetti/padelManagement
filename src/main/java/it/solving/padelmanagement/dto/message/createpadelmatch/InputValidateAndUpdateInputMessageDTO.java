package it.solving.padelmanagement.dto.message.createpadelmatch;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class InputValidateAndUpdateInputMessageDTO {
	
	@Positive
	private String matchId;
	
	@NotNull
	private InputValidateAndInsertInputMessageDTO inputValidateAndInsertInputMessageDTO;

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public InputValidateAndInsertInputMessageDTO getInputValidateAndInsertInputMessageDTO() {
		return inputValidateAndInsertInputMessageDTO;
	}

	public void setInputValidateAndInsertInputMessageDTO(
			InputValidateAndInsertInputMessageDTO inputValidateAndInsertInputMessageDTO) {
		this.inputValidateAndInsertInputMessageDTO = inputValidateAndInsertInputMessageDTO;
	}

	public InputValidateAndUpdateInputMessageDTO(@Positive String matchId,
			@NotNull InputValidateAndInsertInputMessageDTO inputValidateAndInsertInputMessageDTO) {
		super();
		this.matchId = matchId;
		this.inputValidateAndInsertInputMessageDTO = inputValidateAndInsertInputMessageDTO;
	}

	public InputValidateAndUpdateInputMessageDTO() {
		super();
	}
	
	
}
