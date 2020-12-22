package it.solving.padelmanagement.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Match {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private LocalDate date;
	
	private Boolean payed;
	
	private Integer missingPlayers;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="match_players")
	private Set<Player> otherPlayers=new HashSet<>();
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Player creator;
	
	@OneToMany(mappedBy="match")
	private Set<Slot> slots=new HashSet<>();
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Club club;

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

	public Boolean getPayed() {
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

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
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
		if(this.slots.contains(slot)) {
			this.slots.remove(slot);			
		}
	}

	public Match(Long id, LocalDate date, Boolean payed, Integer missingPlayers) {
		super();
		this.id = id;
		this.date = date;
		this.payed = payed;
		this.missingPlayers = missingPlayers;
	}

	public Match() {
		super();
	}
	
	
}
