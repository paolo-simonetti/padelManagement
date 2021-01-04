package it.solving.padelmanagement.dto.message.createpadelmatch;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class InputVerifyAvailabilityMessageDTO {
	
	@NotBlank
	protected String date;
	
	@Min(8)
	@Max(20)
	protected String hour;
	
	@PositiveOrZero
	@Max(30)
	protected String minute;
	
	@Min(1)
	@Max(14)
	protected String durationHour;

	@PositiveOrZero
	@Max(30)
	protected String durationMinute;

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

	public InputVerifyAvailabilityMessageDTO(@NotBlank String date,
			@Min(8) @Max(20) String hour, @Positive @Max(30) String minute, @Min(1) @Max(14) String durationHour,
			@Positive @Max(30) String durationMinute) {
		super();
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
