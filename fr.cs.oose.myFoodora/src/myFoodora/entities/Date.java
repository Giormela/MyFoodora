package myFoodora.entities;

import java.util.Calendar;

public class Date implements Comparable<Date>{
	private Integer year;
	private Integer month;
	private Integer day;
	
	public Date(Integer year, Integer month, Integer day) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public static Date now() {
		return new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
	}
	
	public static Date oneMonthAgo() {
		return new Date(Calendar.YEAR, (Calendar.MONTH - 1) % 12, Calendar.DAY_OF_MONTH);
	}
	
	public boolean isIncluded(Date from, Date to) {
		return (this.compareTo(from) >= 0 && this.compareTo(to) <= 0);
	}

	@Override
	public int compareTo(Date o) {
		if (this.year == o.year) {
			if(this.month == o.month) {
				return this.day.compareTo(o.day);
			}
			return this.month.compareTo(o.month);
		}
		return this.year.compareTo(o.year);
	}

	public static Date from(String string) {
		String[] fields = string.split("/");
		return new Date(Integer.valueOf(fields[2]), Integer.valueOf(fields[1]), Integer.valueOf(fields[0]));
	}
	
	@Override
	public String toString() {
		return day+"/"+month+"/"+year;
	}
}
