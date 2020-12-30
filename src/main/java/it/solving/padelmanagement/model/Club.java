package it.solving.padelmanagement.model;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
	private Blob logo;
	
	@OneToMany(mappedBy="club", cascade=CascadeType.REMOVE)
	private Set<Court> courts=new HashSet<>();
	
	@OneToOne(fetch=FetchType.LAZY, orphanRemoval=true)
	private Admin admin;
	
	@OneToMany(mappedBy="club", cascade=CascadeType.REMOVE)
	private Set<Notice> notices=new HashSet<>();
	
	@OneToMany(mappedBy="club", cascade=CascadeType.REMOVE)
	private Set<JoinProposal> joinProposals=new HashSet<>();

	@OneToMany(mappedBy="club", cascade=CascadeType.REMOVE) 
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

	public Blob getLogo() {
		return logo;
	}

	public void setLogo(Blob logo) {
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

	public Set<Notice> getNotices() {
		return notices;
	}

	public void setNotices(Set<Notice> notices) {
		this.notices = notices;
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
	
	public void addToNotices(Notice notice) {
		this.notices.add(notice);
	}
	
	public void removeFromNotices(Notice notice) {
		if (this.notices.contains(notice)) {
			this.notices.remove(notice);
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
	
	public Club(Long id, String name, String city, Blob logo, Admin admin) {
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
		Club other = (Club) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
