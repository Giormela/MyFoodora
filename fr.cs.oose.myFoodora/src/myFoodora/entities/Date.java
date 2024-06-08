package myFoodora.entities;

public class Date implements Comparable<Date>{
	private Integer year;
	private Integer month;
	private Integer day;

	/**
	 * Constructs a new Date instance.
	 * @param year The year of the date.
	 * @param month The month of the year (1-12).
	 * @param day The day of the month.
	 */
	public Date(Integer year, Integer month, Integer day) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/**
	 * Returns the current system date.
	 * @return The current date.
	 */
	public static Date now() {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		return new Date(calendar.get(java.util.Calendar.YEAR),
				calendar.get(java.util.Calendar.MONTH) + 1, // Add 1 because Calendar.MONTH is zero-based
				calendar.get(java.util.Calendar.DAY_OF_MONTH));
	}

	/**
	 * Returns the date one month ago from today.
	 * @return Date one month ago.
	 */
	public static Date oneMonthAgo() {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.add(java.util.Calendar.MONTH, -1);  // Subtract one month from the current date
		return new Date(calendar.get(java.util.Calendar.YEAR),
				calendar.get(java.util.Calendar.MONTH) + 1, // Add 1 because Calendar.MONTH is zero-based
				calendar.get(java.util.Calendar.DAY_OF_MONTH));
	}

	/**
	 * Determines if this date is between two other dates.
	 * @param from The starting date.
	 * @param to The ending date.
	 * @return true if this date is between 'from' and 'to', inclusive.
	 */
	public boolean isIncluded(Date from, Date to) {
		return (this.compareTo(from) >= 0 && this.compareTo(to) <= 0);
	}

	@Override
	public int compareTo(Date o) {
		if (!this.year.equals(o.year)) {
			return this.year.compareTo(o.year);
		} else if (!this.month.equals(o.month)) {
			return this.month.compareTo(o.month);
		} else {
			return this.day.compareTo(o.day);
		}
	}

	public static Date from(String string) {
		String[] fields = string.split("/");
		return new Date(Integer.valueOf(fields[2]), Integer.valueOf(fields[1]), Integer.valueOf(fields[0]));
	}
	
	@Override
	public String toString() {
		return day+"/"+month+"/"+year;
	}

	public Integer getYear() {
		return year;
	}

	public Integer getMonth() {
		return month;
	}

	public Integer getDay() {
		return day;
	}
}
