package it.solving.padelmanagement.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Player extends User {
	
	private Integer level;
	
	private static final Role role=Role.ROLE_PLAYER;
	
	@OneToMany(mappedBy="creator")
	private Set<Match> matches=new HashSet<>();
	
	@ManyToMany(mappedBy="otherPlayers")
	private Set<Match> matchesJoined=new HashSet<>();
	
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
	
	public Set<Match> getMatchesJoined() {
		return matchesJoined;
	}

	public void setMatchesJoined(Set<Match> matchesJoined) {
		this.matchesJoined = matchesJoined;
	}

	public void addToMatches(Match match) {
		this.matches.add(match);
	}
	
	public void removeFromMatches(Match match) {
		if(this.matches.contains(match)) {
			this.matches.remove(match);
		}
	}

	public void addToMatchesJoined(Match match) {
		this.matchesJoined.add(match);
	}
	
	public void removeFromMatchesJoined(Match match) {
		if(this.matchesJoined.contains(match)) {
			this.matchesJoined.remove(match);
		}
	}

	
	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
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
