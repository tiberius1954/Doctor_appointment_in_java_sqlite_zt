import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import Classes.Hhelper;
import Classes.grlib;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Doc_nursezt extends JFrame {
	ResultSet res;
	Connection con = null;
	grlib gr = new grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	private String rowid = "";
	private int myrow = 0;
	Doctors doctors;
	Nurses nurses;

	Doc_nursezt() {
		initcomponents();
		hh.iconhere(this);
	}

	private void initcomponents() {
		setSize(1220, 690);
		setLayout(null);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(0, 128, 128));
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		UIManager.put("ComboBox.selectionBackground", hh.piros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		UIManager.put("ComboBox.disabledForeground", Color.magenta);	
		UIManager.put("TabbedPane.selected", hh.lpiros);
	
		addWindowListener((WindowListener) new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBackground(Color.yellow);
		tabbedPane.setForeground(Color.black);
		tabbedPane.setBounds(50, 50, 1130, 580);
		Font font = new Font("Arial", Font.CENTER_BASELINE, 20);
		tabbedPane.setFocusable(false);
		tabbedPane.setFont(font);
	    tabbedPane.addTab("Doctors   ", createdoctors());	
	    tabbedPane.addTab("Nurses      ", createnurses());
		add(tabbedPane);
		setVisible(true);
	}

	JPanel createdoctors() {
		JPanel docpanel = new JPanel(null);
		docpanel.setBounds(20, 70, 325, 330);
		doctors = new Doctors(this);		
		docpanel.add(doctors.initcomponents());
		doctors.initagain();	
		return docpanel;
	}	

	JPanel createnurses() {
		JPanel nurpanel = new JPanel(null);
		nurpanel.setBounds(20, 70, 325, 330);
		nurses = new Nurses(this);		
		nurpanel.add(nurses.initcomponents());
		nurses.initagain();	
		return nurpanel;		
	}
	

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Doc_nursezt frame = new Doc_nursezt();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JPanel exitPanel;
}
