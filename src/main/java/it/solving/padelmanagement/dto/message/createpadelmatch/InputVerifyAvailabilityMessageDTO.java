package it.solving.padelmanagement.dto.message.createpadelmatch;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class InputVerifyAvailabilityMessageDTO {
	
	@Positive
	private String playerId;
	
	@NotBlank
	private String date;
	
	@Min(8)
	@Max(20)
	private String hour;
	
	@Positive
	@Max(30)
	private String minute;
	
	@Min(1)
	@Max(14)
	private String durationHour;

	@Positive
	@Max(30)
	private String durationMinute;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getDurationHour() {
		return durationHour;
	}

	public void setDurationHour(String durationHour) {
		this.durationHour = durationHour;
	}

	public String getDurationMinute() {
		return durationMinute;
	}

	public void setDurationMinute(String durationMinute) {
		this.durationMinute = durationMinute;
	}

	public InputVerifyAvailabilityMessageDTO(@Positive String playerId, @NotBlank String date,
			@Min(8) @Max(20) String hour, @Positive @Max(30) String minute, @Min(1) @Max(14) String durationHour,
			@Positive @Max(30) String durationMinute) {
		super();
		this.playerId = playerId;
		this.date = date;
		this.hour = hour;
		this.minute = minute;
		this.durationHour = durationHour;
		this.durationMinute = durationMinute;
	}

	public InputVerifyAvailabilityMessageDTO() {
		super();
	}
	
	
	
}
