import java.sql.Connection;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import Classes.Calhelper;
import Classes.Doctor;
import Classes.Hhelper;
import Classes.grlib;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Docapptimecancel extends JFrame {

	ResultSet res;
	Connection con = null;
	grlib gr = new grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Calhelper cc = new Calhelper();
	private String rowid = "";
	private int myrow = 0;
	JDateChooser startdate = new JDateChooser(new Date());
	JDateChooser enddate = new JDateChooser(new Date());

	Docapptimecancel() {
		initcomponents();
		hh.iconhere(this);
		dd.doctorscombofill(cmbdoctors);
		dd.cntable_update(cntable, "");
	}

	private void initcomponents() {
		UIManager.put("ComboBox.selectionBackground", hh.piros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		UIManager.put("ComboBox.disabledForeground", Color.magenta);
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});

		setSize(980, 530);
		setLayout(null);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(128, 255, 128));
		lbheader = hh.fflabel("Doctor appointment times cancel");
		lbheader.setBounds(20, 10, 350, 35);
		add(lbheader);

		bPanel = new JPanel(null);
		bPanel.setBounds(10, 50, 970, 430);
		bPanel.setBackground(new Color(128, 255, 128));
		// bPanel.setBorder(hh.line);
		add(bPanel);

		ePanel = new JPanel(null);
		ePanel.setBounds(10, 10, 340, 385);
		ePanel.setBackground(new Color(128, 255, 128));
		ePanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
		bPanel.add(ePanel);

		tPanel = new JPanel(null);
		tPanel.setBounds(355, 10, 580, 385);
		tPanel.setBackground(new Color(128, 255, 128));
		tPanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
		bPanel.add(tPanel);

		lbdoctor = hh.clabel("Doctors");
		lbdoctor.setBounds(10, 40, 80, 25);
		ePanel.add(lbdoctor);

		cmbdoctors = gr.grcombo();
		cmbdoctors.setFocusable(true);
		cmbdoctors.setName("doctors");
		cmbdoctors.setBounds(120, 40, 200, 30);
		ePanel.add(cmbdoctors);

		lbstartdate = hh.clabel(" Start date");
		lbstartdate.setBounds(10, 80, 80, 25);
		ePanel.add(lbstartdate);

		startdate.setDateFormatString("yyyy-MM-dd");
		startdate.setFont(new Font("Arial", Font.BOLD, 16));
		startdate.setBounds(120, 80, 120, 30);
		startdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		ePanel.add(startdate);
		startdate.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					// countd();
				}
			}
		});

		lbenddate = hh.clabel("End  date");
		lbenddate.setBounds(10, 120, 80, 25);
		ePanel.add(lbenddate);

		enddate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		enddate.setDateFormatString("yyyy-MM-dd");
		enddate.setFont(new Font("Arial", Font.BOLD, 16));
		enddate.setBounds(120, 120, 120, 25);
		ePanel.add(enddate);
		enddate.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					// countd();
				}
			}
		});

		lbstart = hh.clabel("Start time");
		lbstart.setBounds(10, 160, 80, 25);
		ePanel.add(lbstart);

		String rtime = "07:00";
		startmodel = new SpinnerDateModel();
		starttime = hh.cspinner(startmodel);
		starttime.setBounds(120, 160, 70, 25);
		starttime.setBorder(hh.myRaisedBorder);
		hh.madexxx(starttime, "T");
		starttime.setName("starttime");
		ePanel.add(starttime);
		starttime.setValue(hh.stringtotime(rtime));
		((JSpinner.DefaultEditor) starttime.getEditor()).getTextField().addFocusListener(sFocusListener);
		starttime.addChangeListener((ChangeListener) new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				starttime.requestFocus();
			}
		});

		lbend = hh.clabel("End time");
		lbend.setBounds(10, 200, 80, 25);
		ePanel.add(lbend);

		endmodel = new SpinnerDateModel();
		endtime = hh.cspinner(endmodel);
		endtime.setBounds(120, 200, 70, 25);
		endtime.setBorder(hh.myRaisedBorder);
		hh.madexxx(endtime, "T");
		endtime.setName("endtime");
		ePanel.add(endtime);
		rtime = "18:00";
		endtime.setValue(hh.stringtotime(rtime));
		((JSpinner.DefaultEditor) endtime.getEditor()).getTextField().addFocusListener(sFocusListener);

		btnsave = gr.sbcs("Save");
		btnsave.setBounds(120, 245, 130, 30);
		btnsave.setBackground(hh.lpiros);
		ePanel.add(btnsave);

		btnsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				savebuttrun();
			}
		});

		btncancel = gr.sbcs("Cancel");
		btncancel.setBackground(Color.green);
		btncancel.setBounds(120, 285, 130, 30);
		ePanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
			}
		});

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(120, 325, 130, 30);
		btndelete.setBackground(Color.yellow);
		ePanel.add(btndelete);
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_delete();
			}
		});

		cntable = hh.ztable();
		cntable.setTableHeader(new JTableHeader(cntable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(cntable);
		cntable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				cntable.scrollRectToVisible(cntable.getCellRect(cntable.getRowCount() - 1, 0, true));
			}
		});
		cntable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {

				int row = cntable.getSelectedRow();
				if (row >= 0) {
					rowid = cntable.getValueAt(row, 0).toString();
					int number = 0;
					String cnum = cntable.getValueAt(row, 1).toString();
					if (!hh.zempty(cnum)) {
						number = Integer.parseInt(cnum);
					}
					hh.setSelectedValue(cmbdoctors, number);
					cmbdoctors.updateUI();
					Date date;
					Date date1;
					try {
						String dd = cntable.getValueAt(row, 3).toString();
						date = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
						startdate.setDate(date);
						dd = cntable.getValueAt(row, 4).toString();
						date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
						enddate.setDate(date1);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					String starttime = cntable.getValueAt(row, 5).toString();
					startmodel.setValue(hh.stringtotime(starttime));
					String endtime = cntable.getValueAt(row, 6).toString();
					endmodel.setValue(hh.stringtotime(endtime));
					myrow = row;
				}
			}
		});

		jScrollPane1 = new JScrollPane(cntable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		cntable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "cid", "did", "Doctor Name", "Start date", "End date", "Start time", "End time" }));
		hh.setJTableColumnsWidth(cntable, 555, 0, 0, 40, 17, 17, 13, 13);
		jScrollPane1.setViewportView(cntable);
		jScrollPane1.setBounds(10, 25, 555, 340);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);
		setVisible(true);
	}

	private void savebuttrun() {
		String sql = "";
		int did = 0;
		String dname = "";
		DefaultTableModel d1 = (DefaultTableModel) cntable.getModel();
		if (cmbdoctors.getSelectedItem() != null) {
			Doctor doc = (Doctor) cmbdoctors.getSelectedItem();
			did = doc.getDid();
			dname = doc.getDname();
		}
		String startdat = ((JTextField) startdate.getDateEditor().getUiComponent()).getText();
		String enddat = ((JTextField) enddate.getDateEditor().getUiComponent()).getText();
		JTextField intf = ((JSpinner.DefaultEditor) starttime.getEditor()).getTextField();
		String starttime = intf.getText();
		intf = ((JSpinner.DefaultEditor) endtime.getEditor()).getTextField();
		String endtime = intf.getText();

		if (hh.zempty(dname) || hh.zempty(startdat) || hh.zempty(enddat)) {
			JOptionPane.showMessageDialog(null, "Please fill name, startdate, enddate  fields");
			return;
		}
		if (hh.twodate(startdat, enddat) == false) {
			return;
		}
		if (cc.dateisworkday(startdat) == false || cc.dateisworkday(enddat) == false) {
			JOptionPane.showMessageDialog(null, "Dates are not workdays");
			return;
		}

		if (hh.twotime(starttime, endtime) == false) {
			return;
		}

		if (rowid != "") {
			sql = "update docappticancel set  did=" + did + ", startdate='" + startdat + "', enddate='" + enddat
					+ "', starttime='" + starttime + "', endtime='" + endtime + "' where cid = " + rowid;
		} else {
			sql = "insert into docappticancel  (did, startdate, enddate, starttime, endtime) " + "values" + " (" + did
					+ ", '" + startdat + "','" + enddat + "','" + starttime + "','" + endtime + "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag > 0) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(cid) AS max_id from docappticancel");
					d1.insertRow(d1.getRowCount(),
							new Object[] { myid, did, dname, startdat, enddat, starttime, endtime });
					hh.gotolastrow(cntable);
					if (cntable.getRowCount() > 0) {
						int row = cntable.getRowCount() - 1;
						cntable.setRowSelectionInterval(row, row);
					}
				} else {
					table_rowrefresh(did, dname, startdat, enddat, starttime, endtime);
				}
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

	private void table_rowrefresh(int did, String dname, String startdate, String enddate, String starttime,
			String endtime) {
		DefaultTableModel d1 = (DefaultTableModel) cntable.getModel();
		d1.setValueAt(did, myrow, 1);
		d1.setValueAt(dname, myrow, 2);
		d1.setValueAt(startdate, myrow, 3);
		d1.setValueAt(enddate, myrow, 4);
		d1.setValueAt(starttime, myrow, 5);
		d1.setValueAt(endtime, myrow, 6);
	}

	private int data_delete() {
		String sql = "delete from docappticancel  where cid =";
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) cntable.getModel();
		int sIndex = cntable.getSelectedRow();
		if (sIndex < 0) {
			return flag;
		}
		String iid = d1.getValueAt(sIndex, 0).toString();
		if (iid.equals("")) {
			return flag;
		}
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			String vsql = sql + iid;
			flag = dh.Insupdel(vsql);
			if (flag > 0) {
				d1.removeRow(sIndex);
				clearFields();
			}
		}
		return flag;
	}

	private void clearFields() {
		cmbdoctors.setSelectedIndex(-1);
		Date date = new Date();
		startdate.setDate(date);
		enddate.setDate(date);
		String rtime = "07:00";
		starttime.setValue(hh.stringtotime(rtime));
		rtime = "18:00";
		endtime.setValue(hh.stringtotime(rtime));
		myrow = 0;
		rowid = "";

	}

	private final FocusListener sFocusListener = new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {
			JComponent c = (JComponent) e.getSource();
		}

		@Override
		public void focusLost(FocusEvent e) {
			JComponent b = (JComponent) e.getSource();
			if (b.getParent().getParent().getName() == "starttime") {
				JTextField intf = ((JSpinner.DefaultEditor) starttime.getEditor()).getTextField();
				String intime = intf.getText();
				if (hh.correcttime(intime) == true) {
					JOptionPane.showMessageDialog(null, "Correct time is 07:00 - 18:00 !");
				}
			} else {
				JTextField outtf = ((JSpinner.DefaultEditor) endtime.getEditor()).getTextField();
				String outtime = outtf.getText();
				if (hh.correcttime(outtime) == true) {
					JOptionPane.showMessageDialog(null, "Correct time is 07:00 - 18:00 !");
				}
			}
		}
	};

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Docapptimecancel in = new Docapptimecancel();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader, lbdoctor, lbenddate, lbstartdate, lbstart, lbend;
	JComboBox cmbdoctors;
	JPanel ePanel, tPanel, bPanel;
	SpinnerDateModel startmodel, endmodel;
	JSpinner starttime, endtime;
	JButton btnsave, btncancel, btndelete;
	JTable cntable;
	JScrollPane jScrollPane1;

}
