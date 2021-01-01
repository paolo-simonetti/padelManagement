package it.solving.padelmanagement.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ClubDTO {
	
	@JsonIgnore
	private String id;
	
	private String name;
	
	private String city;
	
	private MultipartFile logo;
	
	private String logoName;
	
	private Set<String> courtsIds=new HashSet<>();
	
	@JsonIgnore
	private String adminId;
	
	@JsonIgnore
	private Set<String> noticesIds=new HashSet<>();
	
	@JsonIgnore
	private Set<String> joinProposalsIds=new HashSet<>();
	
	@JsonIgnore
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
	
	public MultipartFile getLogo() {
		return logo;
	}

	public void setLogo(MultipartFile logo) {
		this.logo = logo;
	}

	public String getLogoName() {
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
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

	public void addToCourtsIds(String courtId) {
		this.courtsIds.add(courtId);
	}
	
	public void removeFromCourtsIds(String courtId) {
		if(this.courtsIds.contains(courtId)) {
			this.courtsIds.remove(courtId);			
		}
	}
	
	public void addToNoticesIds(String noticeId) {
		this.noticesIds.add(noticeId);
	}
	
	public void removeFromNoticesIds(String noticeId) {
		if(this.noticesIds.contains(noticeId)) {
			this.noticesIds.remove(noticeId);
		}
	}
	
	public void addToJoinProposalsIds(String joinProposalId) {
		this.joinProposalsIds.add(joinProposalId);
	}
	
	public void removeFromJoinProposalsIds(String joinProposalId) {
		if(this.joinProposalsIds.contains(joinProposalId)) {
			this.joinProposalsIds.remove(joinProposalId);
		}
	}
	
	public void addToPlayersIds(String playerId) {
		this.playersIds.add(playerId);
	}
	
	public void removeFromPlayersIds(String playerId) {
		if(this.playersIds.contains(playerId)) {
			this.playersIds.remove(playerId);
		}
	}

	public ClubDTO(String id, String name, String city, MultipartFile logo, String logoName, Set<String> courtsIds,
			String adminId, Set<String> noticesIds, Set<String> joinProposalsIds, Set<String> playersIds) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.logo = logo;
		this.logoName = logoName;
		this.courtsIds = courtsIds;
		this.adminId = adminId;
		this.noticesIds = noticesIds;
		this.joinProposalsIds = joinProposalsIds;
		this.playersIds = playersIds;
	}

	public ClubDTO() {
		super();
	}
	
	
}
