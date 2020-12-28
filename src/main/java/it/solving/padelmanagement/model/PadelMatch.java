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
	
}
