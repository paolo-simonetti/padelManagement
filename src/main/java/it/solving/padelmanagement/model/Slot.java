package it.solving.padelmanagement.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Slot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private Integer hour;
	
	private Integer minute;
	
	@ManyToMany(mappedBy="slots")
	private Set<PadelMatch> matches=new HashSet<>();

	public Slot(Integer id, Integer hour, Integer minute) {
		super();
		this.id = id;
		this.hour = hour;
		this.minute = minute;
	}

	public Slot() {
		super();
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
	
	public Set<PadelMatch> getMatches() {
		return matches;
	}

	public void addToMatches(PadelMatch match) {
		this.matches.add(match);
	}
	
	public void removeFromMatches(PadelMatch match) {
		if(this.matches.contains(match)) {
			this.matches.remove(match);
		}
	}
	
	private static final Slot OTTO=new Slot(1,8,0);
	private static final Slot OTTO_E_TRENTA=new Slot(2,8,30);
	private static final Slot NOVE=new Slot(3,9,0);
	private static final Slot NOVE_E_TRENTA=new Slot(4,9,30);
	private static final Slot DIECI=new Slot(5,10,0);
	private static final Slot DIECI_E_TRENTA=new Slot(6,10,30);
	private static final Slot UNDICI=new Slot(7,11,0);
	private static final Slot UNDICI_E_TRENTA=new Slot(8,11,30);
	private static final Slot DODICI=new Slot(9,12,0);
	private static final Slot DODICI_E_TRENTA=new Slot(10,12,30);
	private static final Slot TREDICI=new Slot(11,13,0);
	private static final Slot TREDICI_E_TRENTA=new Slot(12,13,30);
	private static final Slot QUATTORDICI= new Slot(13,14,0);
	private static final Slot QUATTORDICI_E_TRENTA=new Slot(14,14,30);
	private static final Slot QUINDICI=new Slot(15,15,0);
	private static final Slot QUINDICI_E_TRENTA=new Slot(16,15,30);
	private static final Slot SEDICI=new Slot(17,16,0);
	private static final Slot SEDICI_E_TRENTA=new Slot(18,16,30);
	private static final Slot DICIASSETTE=new Slot(19,17,0);
	private static final Slot DICIASSETTE_E_TRENTA=new Slot(20,17,30);
	private static final Slot DICIOTTO=new Slot(21,18,0);
	private static final Slot DICIOTTO_E_TRENTA=new Slot(22,18,30);
	private static final Slot DICIANNOVE=new Slot(23,19,0);
	private static final Slot DICIANNOVE_E_TRENTA=new Slot(24,19,30);
	private static final Slot VENTI=new Slot(25,20,0);
	private static final Slot VENTI_E_TRENTA=new Slot(26,20,30);
	private static final Slot VENTUNO=new Slot(27,21,0);
	private static final Slot VENTUNO_E_TRENTA=new Slot(28,21,30);
	private static final Slot VENTIDUE=new Slot(29,22,0);

	public static Map<Integer, Slot> idToSlotConversion = new HashMap<>();
	
	static {
		idToSlotConversion.put(1,Slot.OTTO);
		idToSlotConversion.put(2,OTTO_E_TRENTA);
		idToSlotConversion.put(3,NOVE);
		idToSlotConversion.put(4,NOVE_E_TRENTA);
		idToSlotConversion.put(5,DIECI);
		idToSlotConversion.put(6,DIECI_E_TRENTA);
		idToSlotConversion.put(7,UNDICI);
		idToSlotConversion.put(8,UNDICI_E_TRENTA);
		idToSlotConversion.put(9,DODICI);
		idToSlotConversion.put(10,DODICI_E_TRENTA);
		idToSlotConversion.put(11,TREDICI);
		idToSlotConversion.put(12,TREDICI_E_TRENTA);
		idToSlotConversion.put(13,QUATTORDICI);
		idToSlotConversion.put(14,QUATTORDICI_E_TRENTA);
		idToSlotConversion.put(15,QUINDICI);
		idToSlotConversion.put(16,QUINDICI_E_TRENTA);
		idToSlotConversion.put(17,SEDICI);
		idToSlotConversion.put(18,SEDICI_E_TRENTA);
		idToSlotConversion.put(19,DICIASSETTE);
		idToSlotConversion.put(20,DICIASSETTE_E_TRENTA);
		idToSlotConversion.put(21,DICIOTTO);
		idToSlotConversion.put(22,DICIOTTO_E_TRENTA);
		idToSlotConversion.put(23,DICIANNOVE);
		idToSlotConversion.put(24,DICIANNOVE_E_TRENTA);
		idToSlotConversion.put(25,VENTI);
		idToSlotConversion.put(26,VENTI_E_TRENTA);
		idToSlotConversion.put(27,VENTUNO);
		idToSlotConversion.put(28,VENTUNO_E_TRENTA);
		idToSlotConversion.put(29,VENTIDUE);
	}
	
	public static Slot convertIdToSlot(Integer id) {
		return idToSlotConversion.get(id);
	}
	
	public static Slot convertHourAndMinuteToSlot (Integer hour,Integer minute) {
		switch (hour) {
			case 8: 
				if(minute==0) {
					return OTTO;
				} else if(minute==30) {
					return OTTO_E_TRENTA;
				} else {
					return null;
				}
				
			case 9:
				if(minute==0) {
					return NOVE;
				} else if(minute==30) {
					return NOVE_E_TRENTA;
				} else {
					return null;
				}
				
			case 10:
				if(minute==0) {
					return DIECI;
				} else if(minute==30) {
					return DIECI_E_TRENTA;
				} else {
					return null;
				}
			
			case 11:
				if(minute==0) {
					return UNDICI;
				} else if(minute==30) {
					return UNDICI_E_TRENTA;
				} else {
					return null;
				}
				
			case 12:
				if(minute==0) {
					return DODICI;
				} else if(minute==30) {
					return DODICI_E_TRENTA;
				} else {
					return null;
				}
				
			case 13:
				if(minute==0) {
					return TREDICI;
				} else if(minute==30) {
					return TREDICI_E_TRENTA;
				} else {
					return null;
				}
				
			case 14:
				if(minute==0) {
					return QUATTORDICI;
				} else if(minute==30) {
					return QUATTORDICI_E_TRENTA;
				} else {
					return null;
				}
				
			case 15:
				if(minute==0) {
					return QUINDICI;
				} else if(minute==30) {
					return QUINDICI_E_TRENTA;
				} else {
					return null;
				}
				
			case 16:
				if(minute==0) {
					return SEDICI;
				} else if(minute==30) {
					return SEDICI_E_TRENTA;
				} else {
					return null;
				}
				
			case 17:
				if(minute==0) {
					return DICIASSETTE;
				} else if(minute==30) {
					return DICIASSETTE_E_TRENTA;
				} else {
					return null;
				}
				
			case 18:
				if(minute==0) {
					return DICIOTTO;
				} else if(minute==30) {
					return DICIOTTO_E_TRENTA;
				} else {
					return null;
				}
				
			case 19:
				if(minute==0) {
					return DICIANNOVE;
				} else if(minute==30) {
					return DICIANNOVE_E_TRENTA;
				} else {
					return null;
				}
				
			case 20:
				if(minute==0) {
					return VENTI;
				} else if(minute==30) {
					return VENTI_E_TRENTA;
				} else {
					return null;
				}
				
			case 21:
				if(minute==0) {
					return VENTUNO;
				} else if(minute==30) {
					return VENTUNO_E_TRENTA;
				} else {
					return null;
				}
				
			case 22:
				if(minute==0) {
					return VENTIDUE;
				} else {
					return null;
				}
			
			default: 
				return null;
		} 
	}
	
	public boolean equals(Slot slot) {
		return this.id==slot.getId();
	}
}
