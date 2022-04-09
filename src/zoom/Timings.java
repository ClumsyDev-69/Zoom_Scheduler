package zoom;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

public class Timings {

	LinkedList<Entry<Entry<String, String>, Calendar>> id_pass_time;
	LinkedHashMap<String, Calendar> link_time;
	Calendar buffertime;
	Calendar curr_time;
	Timer timer;
	TimerTask task;

	public Timings(LinkedList<Entry<Entry<String, String>, Calendar>> m1) {
		for (Entry<Entry<String, String>, Calendar> entry : m1) {
			Entry<String, String> ent = entry.getKey();
			System.out.println("Username: " + ent.getKey() + ", Password: " + ent.getValue() + ", Time: "
					+ entry.getValue().getTime().toString());
		}
		id_pass_time = m1;
		curr_time = Calendar.getInstance();
		timer = new Timer();
	}

	public void timings() throws Exception {
		for (Entry<Entry<String, String>, Calendar> entry : id_pass_time) {
			Entry<String, String> ent = entry.getKey();
			long time = entry.getValue().getTimeInMillis();
			long bufferTime = curr_time.getTimeInMillis() - 600000;
			long currTime = curr_time.getTimeInMillis();
			if (time > bufferTime && time < currTime) {
				Executor.launchMeet(ent.getKey(), ent.getValue());
			} else {
				String id = ent.getKey(), pass = ent.getValue();
				Date time1 = entry.getValue().getTime();
				Executor.execute(id, pass, time1);
			}
		}
	}
}