import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment implements Comparable<Appointment> {
	private String date;
	private String time;
	private String patient;
	private String reason;
	
	/* Getter that returns the date of the appointment converted from string */
	public Date getDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
		try {
			
			return sdf.parse(date + " " + time);
		}
		catch(ParseException pex) {
			/* Have you seen this? Does this look wrong to you?
			 * Let me know your thoughts at the end of the test!
			 */
			pex.printStackTrace();
		}
		return null;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPatient() {
		return patient;
	}
	public void setPatient(String patient) {
		this.patient = patient;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String toString() {
		return String.format("[name: %s, reason: %s, date: %s]",
				this.getPatient(),
				this.getReason(),
				this.getDateTime());
	}
	
	// override the compareTo method to sort the dates
	public int compareTo(Appointment a) {
		return (this.date).compareTo(a.date);
	}
	
}

