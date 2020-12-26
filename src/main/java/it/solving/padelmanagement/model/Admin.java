package it.solving.padelmanagement.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class Admin extends User {

	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	private Club club;
	
	@Enumerated(EnumType.STRING)
	private static final Role role=Role.ROLE_ADMIN;

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	public Admin() {
		super();
	}	
	
}
