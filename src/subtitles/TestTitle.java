package subtitles;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestTitle {
	
	private Date start;
	private Date end;
	private String text;
	private DateFormat df;
	long currentTime = System.currentTimeMillis();
	
	public TestTitle(Date start, Date end, String text, DateFormat df){
		this.start = start;
		this.end = end;
		this.text = text;
		this.df = df;
	}
	
	public long getStart(){
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(start);
		return calendarStart.getTimeInMillis() - 18000000;
	
	}
	
	public long getEnd(){
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(end);
		return calendarEnd.getTimeInMillis() - 18000000;
	}
	
	public String getText(){
		return text;
	}
	
	@Override
	public String toString(){
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(start);
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(end);
		return String.format("\nStart: %d:%d:%d:%d\nEnd: %d:%d:%d:%d\nText: %s\n", calendarStart.get(Calendar.HOUR),calendarStart.get(Calendar.MINUTE), calendarStart.get(Calendar.SECOND), calendarStart.get(Calendar.MILLISECOND), calendarEnd.get(Calendar.HOUR), calendarEnd.get(Calendar.MINUTE), calendarEnd.get(Calendar.SECOND), calendarEnd.get(Calendar.MILLISECOND), text);
	}
}