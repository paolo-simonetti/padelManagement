package it.solving.padelmanagement.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Player extends User {
	
	private Integer level;
	
	@Enumerated(EnumType.STRING)
	private static final Role role=Role.ROLE_PLAYER;
	
	@OneToMany(mappedBy="creator")
	private Set<PadelMatch> matches=new HashSet<>();
	
	@ManyToMany(mappedBy="otherPlayers")
	private Set<PadelMatch> matchesJoined=new HashSet<>();
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Club club;

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Set<PadelMatch> getMatches() {
		return matches;
	}

	public void setMatches(Set<PadelMatch> matches) {
		this.matches = matches;
	}
	
	public Set<PadelMatch> getMatchesJoined() {
		return matchesJoined;
	}

	public void setMatchesJoined(Set<PadelMatch> matchesJoined) {
		this.matchesJoined = matchesJoined;
	}

	public void addToMatches(PadelMatch match) {
		this.matches.add(match);
	}
	
	public void removeFromMatches(PadelMatch match) {
		if(this.matches.contains(match)) {
			this.matches.remove(match);
		}
	}

	public void addToMatchesJoined(PadelMatch match) {
		this.matchesJoined.add(match);
	}
	
	public void removeFromMatchesJoined(PadelMatch match) {
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
	
}
