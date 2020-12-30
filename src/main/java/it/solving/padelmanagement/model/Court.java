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
		Set<PadelMatch> matches=this.getMatches();
		for (PadelMatch match:matches) {
			Slot slotIntersection=match.getSlots().stream().filter(s->slots.contains(s)).findFirst().orElse(null);
			if (slotIntersection!=null) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Court other = (Court) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
