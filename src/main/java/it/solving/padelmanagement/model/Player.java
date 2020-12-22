package it.solving.padelmanagement.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Player extends User {
	
	private Integer level;
	
	private static final Role role=Role.ROLE_PLAYER;
	
	@OneToMany(mappedBy="creator")
	private Set<Match> matches=new HashSet<>();
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Club club;

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public static Role getRole() {
		return role;
	}

	public Set<Match> getMatches() {
		return matches;
	}

	public void setMatches(Set<Match> matches) {
		this.matches = matches;
	}

	public void addToMatches(Match match) {
		this.matches.add(match);
	}
	
	public void removeFromMatches(Match match) {
		if(this.matches.contains(match)) {
			this.matches.remove(match);
		}
	}

	public Player() {
		super();
	}

	public Player(Long id, String name, String surname, LocalDate dateOfBirth, String mailAddress, String mobile,
			Set<Byte> proPicFile, Integer level) {
		super(id, name, surname, dateOfBirth, mailAddress, mobile, proPicFile);
		this.level=level;
	}
	

	
	
}
