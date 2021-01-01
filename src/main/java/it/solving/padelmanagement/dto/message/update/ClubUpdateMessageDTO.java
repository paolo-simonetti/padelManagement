package it.solving.padelmanagement.dto.message.update;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class ClubUpdateMessageDTO {
	
	private String id;
	
	private String name;
	
	private String city;
	
	private String logoName;
		
	private MultipartFile logo;

	
	private Set<String> courtsIds=new HashSet<>();
	
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

	public String getLogoName() {
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}

	public MultipartFile getLogo() {
		return logo;
	}

	public void setLogo(MultipartFile logo) {
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
	
	public ClubUpdateMessageDTO(String id, String name, String city, String logoName, MultipartFile logo,
			Set<String> courtsIds, String adminId, Set<String> noticesIds, Set<String> joinProposalsIds,
			Set<String> playersIds) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.logoName = logoName;
		this.logo = logo;
		this.courtsIds = courtsIds;
		this.adminId = adminId;
		this.noticesIds = noticesIds;
		this.joinProposalsIds = joinProposalsIds;
		this.playersIds = playersIds;
	}

	public ClubUpdateMessageDTO() {
		super();
	}
	
}
