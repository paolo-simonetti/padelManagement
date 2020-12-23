package it.solving.padelmanagement.dto.message.insert;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

public class PlayerInsertMessageDTO extends UserInsertMessageDTO {
	
	@Min(1)
	@Max(7)
	private String level;
	
	private static final String role="player";
	
	@Positive
	private String clubId;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}

	public static String getRole() {
		return role;
	}

	public PlayerInsertMessageDTO(String name, String surname, String dateOfBirth, String mailAddress, String mobile,
			@Min(1) @Max(7) String level, @Positive String clubId) {
		super(name, surname, dateOfBirth, mailAddress, mobile);
		this.level = level;
		this.clubId = clubId;
	}

	public PlayerInsertMessageDTO() {
		super();
	}
	
	
}
