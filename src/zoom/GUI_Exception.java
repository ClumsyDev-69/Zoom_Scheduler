package zoom;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;

public class GUI_Exception {

	public static void showException(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String sStackTrace = sw.toString(); // stack trace as a string

		String[] options = new String[] { "OK", "More details..." };
		int i = JOptionPane.showOptionDialog(null, "An internal error occurred, please restart the app!", "Error",
				JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, 1);
		if (i < 1) {
			System.exit(0);
		} else {
			String[] options2 = new String[] { "Print", "Exit" };
			int i1 = JOptionPane.showOptionDialog(null,
					"<html><body><p style='width: 400px;'>" + sStackTrace + "</p></body></html>", "Details",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, options2, 1);
			if (i1 == -1 || i1 == 1) {
				System.exit(0);
			} else if (i1 == 0) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
}
