package it.solving.padelmanagement.dto.message.insert;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

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

	public PlayerInsertMessageDTO(@NotBlank String name, @NotBlank String surname, @NotBlank String dateOfBirth,
			@NotBlank String mailAddress, @NotBlank String username, @NotBlank @Size(min = 6) String password,
			@NotBlank String mobile, @NotBlank String proPicName, @NotNull MultipartFile proPic,
			@Min(1) @Max(7) String level, @Positive String clubId) {
		super(name, surname, dateOfBirth, mailAddress, username, password, mobile, proPicName, proPic);
		this.level = level;
		this.clubId = clubId;
	}

	public PlayerInsertMessageDTO() {
		super();
	}
	
	
}
