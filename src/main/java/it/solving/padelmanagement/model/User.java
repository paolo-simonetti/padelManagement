package it.solving.padelmanagement.model;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	
	protected String name;
	
	protected String surname;
	
	protected LocalDate dateOfBirth;
	
	protected String mailAddress;
	
	protected String username;
	
	protected String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	protected String mobile;
	
	@Lob
	protected Blob proPicFile;
		
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	private Set<NewClubProposal> newClubProposals=new HashSet<>();
	
	
	@OneToMany(mappedBy="applicant", cascade=CascadeType.REMOVE)
	private Set<JoinProposal> joinProposals=new HashSet<>();


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


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getMailAddress() {
		return mailAddress;
	}


	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Blob getProPicFile() {
		return proPicFile;
	}

	public void setProPicFile(Blob proPicFile) {
		this.proPicFile = proPicFile;
	}

	public Set<JoinProposal> getJoinProposals() {
		return joinProposals;
	}


	public void setJoinProposals(Set<JoinProposal> joinProposals) {
		this.joinProposals = joinProposals;
	}

	public Set<NewClubProposal> getNewClubProposals() {
		return newClubProposals;
	}


	public void setNewClubProposals(Set<NewClubProposal> newClubProposals) {
		this.newClubProposals = newClubProposals;
	}

	public void addToNewClubProposals(NewClubProposal newClubProposal) {
		this.newClubProposals.add(newClubProposal);
	}
	
	public void removeFromNewClubProposals(NewClubProposal newClubProposal) {
		if(this.newClubProposals.contains(newClubProposal)) {
			this.newClubProposals.remove(newClubProposal);
		}
	}
	
	public void addToJoinProposals(JoinProposal joinProposal) {
		this.joinProposals.add(joinProposal);
	}
	
	public void removeFromJoinProposals(JoinProposal joinProposal) {
		if(this.joinProposals.contains(joinProposal)) {
			this.joinProposals.remove(joinProposal);
		}
	}

	
	public User(Long id, String name, String surname, LocalDate dateOfBirth, String mailAddress, String mobile,
			Role role, Blob proPicFile) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.mailAddress = mailAddress;
		this.mobile = mobile;
		this.role=role;
		this.proPicFile = proPicFile;
	}


	public User() {
		super();
	}
	
	
}
