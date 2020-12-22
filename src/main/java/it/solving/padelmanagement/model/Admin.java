package it.solving.padelmanagement.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class Admin extends User {

	@OneToOne(fetch=FetchType.LAZY)
	private Club club;
	
	private static final Role role=Role.ROLE_ADMIN;

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	public static Role getRole() {
		return role;
	}

	public Admin() {
		super();
	}

	public Admin(Long id, String name, String surname, LocalDate dateOfBirth, String mailAddress, String mobile,
			Set<Byte> proPicFile) {
		super(id, name, surname, dateOfBirth, mailAddress, mobile, proPicFile);
	}

	
	
	
}
