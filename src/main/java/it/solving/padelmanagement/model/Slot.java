package it.solving.padelmanagement.model;

import java.util.HashMap;
import java.util.Map;

public enum Slot {
	OTTO(1,8,0),
	OTTO_E_TRENTA(2,8,30);
	
	private Integer id;
	private Integer hour;
	private Integer minute;
	
	Slot(Integer id, Integer hour, Integer minute) {
		this.id=id;
		this.hour=hour;
		this.minute=minute;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}
	
	public static Map<Integer, Slot> slotConversion = new HashMap<>();
	
	static {
		slotConversion.put(1,OTTO);
	}
}
