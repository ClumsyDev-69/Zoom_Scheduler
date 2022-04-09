package zoom;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Calendar;
import java.util.LinkedHashMap;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Zoom_Panel extends JPanel {

	public JTextField meetingID;
	public JTextField pass;
	static String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
	public LinkedHashMap<String, Integer> daysToInt= new LinkedHashMap<>();
	static Integer[] hours = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
	static Integer[] minutes = new Integer[60];
	static String[] tod = { "AM", "PM" }; // time of day
	JComboBox<?> day;
	JComboBox<?> hour;
	JComboBox<?> minute;
	JComboBox<?> ampm;

	public Zoom_Panel() {
		for (int i = 0; i <= 59; i++) {
			minutes[i] = i;
		}
		initialize();
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
		this.add(new JLabel("Meeting ID: "));
		this.add(meetingID);
		meetingID.setPreferredSize(new Dimension(100, 30));
		this.add(new JLabel("Password: "));
		this.add(pass);
		pass.setPreferredSize(new Dimension(100, 30));
		this.add(new JLabel("Day: "));
		this.add(day);
		this.add(new JLabel("Hour: "));
		this.add(hour);
		this.add(new JLabel("Minute: "));
		this.add(minute);
		this.add(new JLabel("AM / PM: "));
		this.add(ampm);
	}
	
	private void initialize() {
		meetingID = new JTextField();
		pass = new JTextField();
		day = new JComboBox<Object>(days);
		hour = new JComboBox<Object>(hours);
		minute = new JComboBox<Object>(minutes);
		ampm = new JComboBox<Object>(tod);
		daysToInt.put(days[0], Calendar.MONDAY);
		daysToInt.put(days[1], Calendar.TUESDAY);
		daysToInt.put(days[2], Calendar.WEDNESDAY);
		daysToInt.put(days[3], Calendar.THURSDAY);
		daysToInt.put(days[4], Calendar.FRIDAY);
		daysToInt.put(days[5], Calendar.SATURDAY);
		daysToInt.put(days[6], Calendar.SUNDAY);
		Calendar curr_time = Calendar.getInstance();
		int weekday = curr_time.get(Calendar.DAY_OF_WEEK) - 2;
		weekday = (weekday == -1)? 6 : weekday;
		day.setSelectedIndex(weekday);
		int sethour = curr_time.get(Calendar.HOUR);
		hour.setSelectedItem(sethour);
		int setminute = curr_time.get(Calendar.MINUTE);
		minute.setSelectedItem(setminute);
		ampm.setSelectedIndex((curr_time.get(Calendar.HOUR_OF_DAY) > 12)? 1 : 0);
	}

}
