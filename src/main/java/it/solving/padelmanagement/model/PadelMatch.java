package it.solving.padelmanagement.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import it.solving.padelmanagement.exception.NonAdmissibleActionOnMatchNow;

@Entity
public class PadelMatch {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private LocalDate date;
	
	private Boolean payed;
	
	private Integer missingPlayers;
	
	@ManyToMany
	private Set<Player> otherPlayers=new HashSet<>();
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Player creator;
	
	@ManyToMany
	private Set<Slot> slots= new HashSet<>();
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Court court;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Boolean isPayed() {
		return payed;
	}

	public void setPayed(Boolean payed) {
		this.payed = payed;
	}

	public Integer getMissingPlayers() {
		return missingPlayers;
	}

	public void setMissingPlayers(Integer missingPlayers) {
		this.missingPlayers = missingPlayers;
	}

	public Set<Player> getOtherPlayers() {
		return otherPlayers;
	}

	public void setOtherPlayers(Set<Player> otherPlayers) {
		this.otherPlayers = otherPlayers;
	}

	public Player getCreator() {
		return creator;
	}

	public void setCreator(Player creator) {
		this.creator = creator;
	}
	
	public Set<Slot> getSlots() {
		return slots;
	}

	public void setSlots(Set<Slot> slots) {
		this.slots = slots;
	}

	public Court getCourt() {
		return court;
	}

	public void setCourt(Court court) {
		this.court = court;
	}

	public void addToOtherPlayers(Player player) {
		this.otherPlayers.add(player);
	}
	
	public void removeFromOtherPlayers(Player player) {
		if(this.otherPlayers.contains(player)) {
			this.otherPlayers.remove(player);			
		}
	}
	
	public void addToSlots(Slot slot) {
		this.slots.add(slot);
	}

	public void removeFromSlots(Slot slot) {
		if (this.slots.contains(slot)) {
			this.slots.remove(slot);
		}
	}
	
	public void decrementMissingPlayers() {
		this.missingPlayers--;
	}
	
	public void incrementMissingPlayers() throws NonAdmissibleActionOnMatchNow {
		if(missingPlayers==3) {
			throw new NonAdmissibleActionOnMatchNow("The number of missing players cannot exceed 3");
		} else {
			this.missingPlayers++;
		}
	}

	public PadelMatch(Long id, LocalDate date, Boolean payed, Integer missingPlayers) {
		super();
		this.id = id;
		this.date = date;
		this.payed = payed;
		this.missingPlayers = missingPlayers;
	}

	public PadelMatch() {
		super();
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
		PadelMatch other = (PadelMatch) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
