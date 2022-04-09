package zoom;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Executor {

	static StringBuffer VBSText = null;
	static String ConfNo = null, pwd = null;
	static Scanner sc = null;
	static Runtime rt;
	static Process pr;
	static String path;
	
	/*
	 * ChangeVBS default text:
	 * Set sh = CreateObject("WScript.Shell")
	 * Set shortcut = sh.CreateShortcut("")
	 * shortcut.TargetPath = ""
	 * shortcut.Arguments = "--url=zoommtg://zoom.us/join?action=join&confno=&pwd="
	 * shortcut.Save
	 */

	public static void launchMeet(String ID, String pass) throws Exception {
		initializer(ID, pass);
		ConfNo = ConfNo.replaceAll("\\D", "");
		System.out.println("Joining Meeting ID: " + ConfNo);
		VBSText = new StringBuffer(readVBS());
		modifyVBS();
		executeVBS();
		executeShortcut();
		rewriteVBS();
	}

	private static void initializer(String ID, String pass) throws Exception {
		rt = null;
		pr = null;
		path = getZoomPath();
		ConfNo = ID;
		pwd = pass;
		sc = new Scanner(new File("prereq\\ChangeTarget.vbs"));
	}

	private static String readVBS() {
		String text = "";
		while (sc.hasNextLine())
			text = text.concat(sc.nextLine()).concat("\n");
		return text;
	}

	private static String modifyVBS() throws Exception {
		VBSText.insert(VBSText.indexOf("CreateShortcut") + 16, System.getProperty("user.home").concat("\\Zoom.lnk"));
		VBSText.insert(VBSText.indexOf("TargetPath") + 14, path);
		VBSText.insert(VBSText.indexOf("pwd=") + 4, pwd);
		VBSText.insert(VBSText.indexOf("confno=") + 7, ConfNo);
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("prereq\\ChangeTarget.vbs"), false));
		bw.write(VBSText.toString());
		bw.close();
		return VBSText.toString();
	}

	private static void executeVBS() throws Exception {
		rt = Runtime.getRuntime();
		pr = null;
		pr = rt.exec(new String[] { "cmd", "/c", "cd " + "prereq\\" });
		pr = rt.exec(new String[] { "cmd", "/c", "WScript \"" + "prereq\\ChangeTarget.vbs" + "\"" });
		pr.waitFor();
		Thread.sleep(1000);
	}

	private static void executeShortcut() throws Exception {
		rt = Runtime.getRuntime();
		pr = null;
		Thread.sleep(1000);
		pr = rt.exec(new String[] { "cmd", "/c", System.getProperty("user.home").concat("\\Zoom.lnk") });
		pr.waitFor();
	}

	private static void rewriteVBS() throws Exception {
		BufferedWriter bw3 = new BufferedWriter(new FileWriter(new File("prereq\\ChangeTarget.vbs")));
		String Retext = "Set sh = CreateObject(\"WScript.Shell\")\r\n" + "Set shortcut = sh.CreateShortcut(\"\")\r\n"
				+ "shortcut.TargetPath = \"\"\r\n"
				+ "shortcut.Arguments = \"--url=zoommtg://zoom.us/join?action=join&confno=&pwd=\"\r\n"
				+ "shortcut.Save";
		bw3.write(Retext);
		bw3.close();
	}

	private static String getZoomPath() throws Exception{
		Scanner sc = new Scanner(new File("prereq\\Zoom_loc.txt"));
		String text = "";
		while(sc.hasNextLine())
			text = text.concat(sc.nextLine());
		return text;
	}

	public static void execute(String ID, String pass, Date time) {
		Timer t = new Timer();
		TimerTask tmt = new TimerTask() {
			@Override
			public void run() {
				try {
					launchMeet(ID, pass);
				} catch (Exception e) {
					GUI_Exception.showException(e);;
				}
			}
		};
		t.schedule(tmt, time);
		System.out.println("Timer scheduled");
	}

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		execute("98616417655", "12345", cal.getTime());
	}
}