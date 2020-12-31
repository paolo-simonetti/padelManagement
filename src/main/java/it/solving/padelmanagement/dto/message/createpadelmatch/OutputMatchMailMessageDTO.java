package it.solving.padelmanagement.dto.message.createpadelmatch;

import java.util.HashSet;
import java.util.Set;

public class OutputMatchMailMessageDTO {
	
	private String date;
	private String payed;
	private String missingPlayers;
	private String courtName;
	private Set<String> otherPlayersUsername=new HashSet<>();
	private String usernameCreator;
	private String hourStart;
	private String minuteStart;
	private String hourEnd;
	private String minuteEnd;
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getPayed() {
		return payed;
	}
	
	public void setPayed(String payed) {
		this.payed = payed;
	}
	
	public String getMissingPlayers() {
		return missingPlayers;
	}
	
	public void setMissingPlayers(String missingPlayers) {
		this.missingPlayers = missingPlayers;
	}
	
	public String getCourtName() {
		return courtName;
	}
	
	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}
	
	public Set<String> getOtherPlayersUsername() {
		return otherPlayersUsername;
	}
	
	public void setOtherPlayersUsername(Set<String> otherPlayersUsername) {
		this.otherPlayersUsername = otherPlayersUsername;
	}
	
	public String getUsernameCreator() {
		return usernameCreator;
	}
	
	public void setUsernameCreator(String usernameCreator) {
		this.usernameCreator = usernameCreator;
	}
	
	public String getHourStart() {
		return hourStart;
	}
	
	public void setHourStart(String hourStart) {
		this.hourStart = hourStart;
	}
	
	public String getMinuteStart() {
		return minuteStart;
	}
	
	public void setMinuteStart(String minuteStart) {
		this.minuteStart = minuteStart;
	}
	
	public String getHourEnd() {
		return hourEnd;
	}
	
	public void setHourEnd(String hourEnd) {
		this.hourEnd = hourEnd;
	}
	
	public String getMinuteEnd() {
		return minuteEnd;
	}
	
	public void setMinuteEnd(String minuteEnd) {
		this.minuteEnd = minuteEnd;
	}

	@Override
	public String toString() {
		if (otherPlayersUsername!=null && otherPlayersUsername.size()>0 ) {
			return "- date=" + date + "; \n - payed=" + payed + "; \n - missing players=" + missingPlayers
					+ "; \n - name of the court=" + courtName + "; \n - other signed up players' username=" + 
					otherPlayersUsername.stream().reduce((username1,username2)->username1+", " +username2) + 
					"; \n - creator's username= "+ usernameCreator + "; \n start=" + hourStart + "." + minuteStart + 
					", end=" + hourEnd+"." + minuteEnd ;			
		} else {
			return "- date=" + date + "; \n - payed=" + payed + "; \n - missing players=" + missingPlayers
					+ "; \n - name of the court=" + courtName + "; \n - creator's username= "+ usernameCreator + 
					"; \n start=" + hourStart + "." + minuteStart +	", end=" + hourEnd+"." + minuteEnd ;		
		}
	}
	
	
}
