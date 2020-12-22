package it.solving.padelmanagement.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Club {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String city;
	
	@Lob
	private Set<Byte> logo=new HashSet<>();
	
	@OneToMany(mappedBy="club")
	private Set<Court> courts=new HashSet<>();
	
	@OneToOne(fetch=FetchType.LAZY)
	private Admin admin;
	
	@OneToMany(mappedBy="club")
	private Set<Promotion> promotions=new HashSet<>();
	
	@OneToMany(mappedBy="club")
	private Set<JoinProposal> joinProposals=new HashSet<>();

	@OneToMany(mappedBy="club") 
	private Set<Player> players=new HashSet<>();
	
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Set<Byte> getLogo() {
		return logo;
	}

	public void setLogo(Set<Byte> logo) {
		this.logo = logo;
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
	}

	public Set<Court> getCourts() {
		return courts;
	}

	public void setCourts(Set<Court> courts) {
		this.courts = courts;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Set<Promotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}

	public Set<JoinProposal> getJoinProposals() {
		return joinProposals;
	}

	public void setJoinProposals(Set<JoinProposal> joinProposals) {
		this.joinProposals = joinProposals;
	}
	
	public void addToCourts(Court court) {
		this.courts.add(court);
	}
	
	public void removeFromCourts(Court court) {
		if (this.courts.contains(court)) {
			this.courts.remove(court);
		}
	}
	
	public void addToPromotions(Promotion promotion) {
		this.promotions.add(promotion);
	}
	
	public void removeFromPromotions(Promotion promotion) {
		if (this.promotions.contains(promotion)) {
			this.promotions.remove(promotion);
		}
	}

	public void addToPlayers(Player player) {
		this.players.add(player);
	}
	
	public void removeFromPlayers(Player player) {
		if (this.players.contains(player)) {
			this.players.remove(player);
		}
	}
	
	public void addToJoinProposals(JoinProposal joinProposal) {
		this.joinProposals.add(joinProposal);
	}
	
	public void removeFromJoinProposals(JoinProposal joinProposal) {
		if (this.joinProposals.contains(joinProposal)) {
			this.joinProposals.remove(joinProposal);
		}
	}
	
	public Club(Long id, String name, String city, Set<Byte> logo, Admin admin) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.logo = logo;
		this.admin = admin;
	}

	public Club() {
		super();
	}
	
	
	
}