package it.solving.padelmanagement.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
	
	private boolean mayBeReserved;

	@OneToMany(mappedBy="court", cascade=CascadeType.REMOVE)
	private Set<PadelMatch> matches = new HashSet<>();
	
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

	public boolean mayBeReserved() {
		return mayBeReserved;
	}

	public void setMayBeReserved(boolean mayBeReserved) {
		this.mayBeReserved = mayBeReserved;
	}

	public Set<PadelMatch> getMatches() {
		return matches;
	}

	public void setMatches(Set<PadelMatch> matches) {
		this.matches = matches;
	}
	
	public void addToMatches(PadelMatch match) {
		this.matches.add(match);
	}
	
	public void removeFromMatches(PadelMatch match) {
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
	
	public boolean areThereMatchesInTheSlots(Set<Slot> slots) {
		Boolean result=false;
		Set<Integer> slotsIds=slots.stream().map(slot->slot.getId()).collect(Collectors.toSet());
		Set<PadelMatch> matches=this.getMatches();
		for (PadelMatch match:matches) {
			result=match.getSlots().stream().map(slot->slot.getId()).noneMatch(id->slotsIds.contains(id));
			if (result) {
				break;
			}
		}
		
		return result;
	}
	
}
