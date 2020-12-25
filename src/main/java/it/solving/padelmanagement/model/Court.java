package it.solving.padelmanagement.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Court {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Club club;

	@OneToMany(mappedBy="court", cascade=CascadeType.REMOVE)
	private Set<Match> matches = new HashSet<>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
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
		if (this.matches.contains(match)) {
			this.matches.remove(match);
		}
	}

	public Court(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Court() {
		super();
	}
	
	
	
}
