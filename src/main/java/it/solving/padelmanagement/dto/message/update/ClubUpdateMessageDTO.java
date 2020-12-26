package it.solving.padelmanagement.dto.message.update;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

public class ClubUpdateMessageDTO {
	
	@NotBlank
	private String id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String city;
	
	private Blob logo;
	
	private Set<String> courtsIds=new HashSet<>();
	
	@NotBlank
	private String adminId;
		
	private Set<String> noticesIds=new HashSet<>();
	
	private Set<String> joinProposalsIds=new HashSet<>();

	private Set<String> playersIds=new HashSet<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public Set<String> getCourtsIds() {
		return courtsIds;
	}

	public void setCourtsIds(Set<String> courtsIds) {
		this.courtsIds = courtsIds;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public Set<String> getNoticesIds() {
		return noticesIds;
	}

	public void setNoticesIds(Set<String> noticesIds) {
		this.noticesIds = noticesIds;
	}

	public Set<String> getJoinProposalsIds() {
		return joinProposalsIds;
	}

	public void setJoinProposalsIds(Set<String> joinProposalsIds) {
		this.joinProposalsIds = joinProposalsIds;
	}

	public Set<String> getPlayersIds() {
		return playersIds;
	}

	public void setPlayersIds(Set<String> playersIds) {
		this.playersIds = playersIds;
	}

	public ClubUpdateMessageDTO(@NotBlank String id, @NotBlank String name, @NotBlank String city, Blob logo) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.logo = logo;
	}

	public ClubUpdateMessageDTO() {
		super();
	}
	
}
