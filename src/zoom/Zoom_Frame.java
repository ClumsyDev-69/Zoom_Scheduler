package zoom;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Zoom_Frame extends JFrame implements ActionListener {

	Zoom_Panel panel1 = new Zoom_Panel();
	Zoom_Panel panel2 = new Zoom_Panel();
	Zoom_Panel panel3 = new Zoom_Panel();
	Zoom_Panel panel4 = new Zoom_Panel();
	Zoom_Panel panel5 = new Zoom_Panel();
	Zoom_Panel panel6 = new Zoom_Panel();
	Zoom_Panel panel7 = new Zoom_Panel();
	Zoom_Panel panel8 = new Zoom_Panel();
	Zoom_Panel panel9 = new Zoom_Panel();
	Zoom_Panel panel10 = new Zoom_Panel();
	JPanel lastPanel = new JPanel();
	JButton done = new JButton("Done");
	LinkedList<Calendar> times = new LinkedList<>();
	LinkedList<Entry<String, String>> id_pass = new LinkedList<>();
	LinkedList<Entry<Entry<String, String>, Calendar>> id_pass_time = new LinkedList<>();
	LinkedList<String> links = new LinkedList<>();
	LinkedHashMap<String, Calendar> link_time = new LinkedHashMap<>();
	LinkedList<Zoom_Panel> panels = new LinkedList<>(
			Arrays.asList(panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9, panel10));

	public Zoom_Frame() {
		this.setSize(1000, 600);
		this.setTitle("Zoom");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(11, 1));
		this.add(panel1);
		this.add(panel2);
		this.add(panel3);
		this.add(panel4);
		this.add(panel5);
		this.add(panel6);
		this.add(panel7);
		this.add(panel8);
		this.add(panel9);
		this.add(panel10);
		this.add(lastPanel);
		this.setResizable(false);
		lastPanel.add(done);
		done.addActionListener(this);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(done)) {
			done.setEnabled(false);
			for (Zoom_Panel panel : panels) {
				iteratePanels(panel);
			}
			create_id_pass_time();
			Timings timings = new Timings(id_pass_time);
			try {
				timings.timings();
			} catch (Exception e1) {
				GUI_Exception.showException(e1);
			}
		}
	}

	private void iteratePanels(Zoom_Panel panel) {
		Map.Entry<String, String> e = new AbstractMap.SimpleEntry<String, String>(panel.meetingID.getText(),
				panel.pass.getText());
		id_pass.add(e);
		Calendar buffer = Calendar.getInstance();
		buffer.set(Calendar.DAY_OF_WEEK, panel.daysToInt.get(panel.day.getSelectedItem()));
		buffer.set(Calendar.HOUR_OF_DAY, panel.ampm.getSelectedItem().equals("AM") ? (int) panel.hour.getSelectedItem()
				: ((int) panel.hour.getSelectedItem() + 12));
		buffer.set(Calendar.MINUTE, (int) panel.minute.getSelectedItem());
		buffer.set(Calendar.SECOND, 0);
		times.add(buffer);
	}

	private void create_id_pass_time() {
		ArrayList<Entry<Entry<String, String>, Calendar>> removeset = new ArrayList<>();
		int i = 0;
		for (Entry<String, String> entry : id_pass) {
			if (times.get(i).getTimeInMillis() < Calendar.getInstance().getTimeInMillis() - 3600000)
				times.get(i).add(Calendar.DAY_OF_YEAR, 7);
			Entry<Entry<String, String>, Calendar> e = new AbstractMap.SimpleEntry<Entry<String, String>, Calendar>(entry, times.get(i));
			id_pass_time.add(e);
			i++;
		}
		for (Entry<Entry<String, String>, Calendar> entry : id_pass_time) {
			Entry<String, String> ent = entry.getKey();
			if (ent.getKey().equals("") || ent.getValue().equals("")) {
				removeset.add(entry);
			}
		}
		for (Entry<Entry<String, String>, Calendar> ent : removeset) {
			id_pass_time.remove(ent);
		}
	}

	public static void main(String[] args) {
		try {
			new Zoom_Frame();
		} catch(Exception e) {
			GUI_Exception.showException(e);
		}
	}

}
