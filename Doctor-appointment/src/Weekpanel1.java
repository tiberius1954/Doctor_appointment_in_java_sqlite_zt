import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import Classes.Calhelper;
import Classes.Docvalidation;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Weekpanel1 extends JFrame {
	Hhelper hh = new Hhelper();
	Calhelper cc = new Calhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Docvalidation dvad = new Docvalidation();
	LocalDate actdate;
	JDateChooser dobdate = new JDateChooser(new Date());
	String docdid="";
	JFrame myframe = this;
	String doctorname="";
	
	
	Weekpanel1(){
		setTitle("Doctor appointment times");
		actdate = LocalDate.now();
		initcomponents();
		hh.iconhere(this);
	}
	Weekpanel1(String did, String docname){
		setTitle("Doctor appointment times");
		docdid= did;		
		doctorname = docname;
		actdate = LocalDate.now();
		initcomponents();
		hh.iconhere(this);
		lbheader1.setText(doctorname);
	}
	
	private void initcomponents(){
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		setLayout(null);
		mainPanel = new JPanel(null);
		mainPanel.setBounds(0,0, 1240, 720);
		mainPanel.setBackground(hh.vgreen);
		add(mainPanel);
		
		lbheader = hh.fflabel("Doctor appointment times");
		lbheader.setBounds(40, 5, 350, 35);	
		mainPanel.add(lbheader);
		
		lbavailable = hh.llabel("Available");
		lbavailable.setBounds(40, 45, 80, 25);
		mainPanel.add(lbavailable);		
		lbava= hh.llabel(" ");
		lbava.setBounds(130, 50, 15, 15);
		lbava.setOpaque(true);
		lbava.setBackground(Color.WHITE);
		mainPanel.add(lbava);
		
		lbunavailable = hh.llabel("Unavailable");
		lbunavailable.setBounds(165, 45, 100, 25);
		mainPanel.add(lbunavailable);		
		lbunava= hh.llabel(" ");
		lbunava.setBounds(275, 50, 15, 15);
		lbunava.setOpaque(true);
		lbunava.setBackground(cc.lpink);
		mainPanel.add(lbunava);		
		
		btnnextweek = hh.cbutton("Next week");
		btnnextweek.setBackground(hh.vvkek);
		btnnextweek.setBounds(665, 30, 140, 30);
		mainPanel.add(btnnextweek);
		btnnextweek.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				actdate = actdate.plusWeeks(1);		
				refreshCalendar();
			}		
		});
		btnprevweek = hh.cbutton("Previous week");
		btnprevweek.setBackground(hh.vvkek);
		btnprevweek.setBounds(520, 30, 140, 30);
		mainPanel.add(btnprevweek);
		btnprevweek.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				actdate = actdate.plusWeeks(-1);	 	
				refreshCalendar();
			}
		});
		
		lbheader1 = hh.fflabel("");
		lbheader1.setBounds(830, 80, 360, 35);	
		lbheader1.setHorizontalAlignment(JLabel.CENTER);	
		mainPanel.add(lbheader1);
		
		mPanel = new Calendarpanel(actdate, myframe,docdid);	
		mPanel.setBounds(20, 70, 787, 580);
		mPanel.setBorder(hh.borderf);
		mPanel.setLayout(null);
	    mainPanel.add(mPanel);

		dataPanel = new JPanel(null);
		dataPanel = createdatapanel();
		mainPanel.add(dataPanel);		
		setSize(1240, 720);
		setVisible(true);
		
	}
	private JPanel createdatapanel() {
		JPanel cPanel = new JPanel(null);
		cPanel.setBounds(830, 130, 360, 470);
		cPanel.setBorder(BorderFactory.createLineBorder(SystemColor.BLACK));

		lbappointment = hh.flabel("Make an appointment");
		lbappointment.setBounds(45, 15, 280, 30);
		cPanel.add(lbappointment);

		lbdate = hh.clabel("");
		lbdate.setHorizontalAlignment(JLabel.CENTER);
		lbdate.setBounds(30, 65, 120, 30);
		lbdate.setBorder(hh.line2);
		cPanel.add(lbdate);

		lbdayname = hh.clabel("");
		lbdayname.setHorizontalAlignment(JLabel.CENTER);
		lbdayname.setBounds(160, 65, 120, 30);
		lbdayname.setBorder(hh.line2);
		cPanel.add(lbdayname);

		lbtime = hh.clabel("");
		lbtime.setHorizontalAlignment(JLabel.CENTER);
		lbtime.setBounds(290, 65, 60, 30);
		lbtime.setBorder(hh.line2);
		cPanel.add(lbtime);

		lbpatientdata = hh.clabel("Data of patient");
		lbpatientdata.setBounds(150, 110, 110, 25);
		cPanel.add(lbpatientdata);

		lbpname = hh.clabel("Name");
		lbpname.setBounds(10, 155, 60, 25);
		cPanel.add(lbpname);

		txpname = hh.cTextField(30);
		txpname.setBounds(80, 155, 250, 30);
		cPanel.add(txpname);
		txpname.addKeyListener(hh.MUpper());

		lbphone = hh.clabel("Phone");
		lbphone.setBounds(10, 200, 60, 25);
		cPanel.add(lbphone);

		txphone = hh.cTextField(30);
		txphone.setBounds(80, 200, 250, 30);
		cPanel.add(txphone);
		txphone.addKeyListener(hh.Onlyphone());

		lbemail = hh.clabel("Email");
		lbemail.setBounds(10, 240, 60, 25);
		cPanel.add(lbemail);

		txemail = hh.cTextField(30);
		txemail.setBounds(80, 240, 250, 30);
		cPanel.add(txemail);

		lbdob = hh.clabel("Dob");
		lbdob.setBounds(10, 280, 60, 25);
		cPanel.add(lbdob);

		dobdate.setDateFormatString("yyyy-MM-dd");
		dobdate.setFont(new Font("Arial", Font.BOLD, 16));
		dobdate.setBounds(80, 280, 120, 30);
		dobdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		cPanel.add(dobdate);
		dobdate.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					// countd();
				}
			}
		});

		lbgen = hh.clabel("Gender");
		lbgen.setBounds(10, 320, 60, 25);
		cPanel.add(lbgen);

		cmbgender = hh.cbcombo();
		cmbgender.addItem("M");
		cmbgender.addItem("F");
		cmbgender.setBounds(80, 320, 50, 25);
		cPanel.add(cmbgender);

		btnsave = hh.cbutton("Save");
		btnsave.setBounds(80, 380, 120, 30);
		btnsave.setBackground(hh.lpiros);
		cPanel.add(btnsave);

		btnsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				savebuttrun();
			}
		});
		btncancel = hh.cbutton("Cancel");
		btncancel.setBackground(hh.zold);
		btncancel.setBounds(210, 380, 120, 30);
		cPanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
			}
		});

		return cPanel;
	}
	public void clearFields() {
		lbdate.setText("");
		lbdayname.setText("");
		lbtime.setText("");
		txpname.setText("");
		txphone.setText("");
		txemail.setText("");
		Date date = new Date();
		dobdate.setDate(date);
		cmbgender.setSelectedIndex(-1);
	//	buttonrecancel();
	}
	
	private void savebuttrun() {
		String sql="";
		String text = lbdate.getText();
		if (hh.zempty(text)) {
			return;
		}
		
		String pname = txpname.getText();
		String phone = txphone.getText();
		String email = txemail.getText();
		String gender = (String) cmbgender.getSelectedItem();
		String appdate = lbdate.getText();
		String apptime = lbtime.getText();
		String day = lbdayname.getText();
		String dobdat = ((JTextField) dobdate.getDateEditor().getUiComponent()).getText();
		if (docvalidation(pname, phone, email, dobdat, gender) == false) {
			return;
		}
		try {
		sql = "insert into patient (pname, phone, email, dob, gen) values ('" + pname + "','" + phone
				+ "','" + email + "','" + dobdat + "','" + gender + "')";
			int flag = dh.Insupdel(sql);	
			
			int pid = dd.table_maxid("SELECT MAX(pid) AS max_id from patient");
			sql = "insert into appointments (did, pid, app_date, app_time, day) values" + " ('" + docdid + "'," 
			+ pid + ",'" + appdate + "','" + apptime + "','" + day + "')";
			flag = dh.Insupdel(sql);
			if (flag > 0) {
				hh.ztmessage("Success", "Message");
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "sql error !");
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
		clearFields();
	}
	private Boolean docvalidation(String name, String phone, String email, String docdate, String gender) {
		Boolean ret = true;
		ArrayList<String> err = new ArrayList<String>();

		if (!dvad.namevalid(name)) {
			err.add(dvad.mess);
			ret = false;
		}
		if (!dvad.phonevalid(phone)) {
			err.add(dvad.mess);
			ret = false;
		}
		if (!dvad.emailvalid(email)) {
			err.add(dvad.mess);
			ret = false;
		}
		if (!dvad.gendervalid(gender)) {
			err.add(dvad.mess);
			ret = false;
		}

		if (err.size() > 0) {
			JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
		}
		return ret;
	}
	private void refreshCalendar() {			
		mainPanel.remove(mPanel);
		mainPanel.repaint();
		mainPanel.revalidate();
		mPanel.removeAll();	
		mPanel = new Calendarpanel(actdate, myframe, docdid);	
		mPanel.setLayout(null);
		mPanel.setBounds(20, 70, 787, 580);
		mPanel.setBorder(hh.borderf);	
		mPanel.repaint();
		mPanel.revalidate();
		mainPanel.add(mPanel);
	}
public void writeout(String data1, String data2, String data3){
	lbdate.setText(data1);
	lbdayname.setText(data2);
	lbtime.setText(data3);
}
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Weekpanel1 doc = new Weekpanel1();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	JButton btnsave, btncancel, btnnextweek, btnprevweek; 
	JTextField txpname, txphone, txemail;
	JComboBox cmbgender;
	JLabel lbpname, lbphone, lbemail, lbgen, lbdate, lbdayname, lbtime, lbdob,
	lbpatientdata, lbappointment, lbheader, lbheader1;
	JLabel lbavailable, lbunavailable,lbava, lbunava;
	JPanel mPanel, dataPanel, mainPanel;
}
