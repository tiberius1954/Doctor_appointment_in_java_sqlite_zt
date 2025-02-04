import java.awt.*;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Classes.Calhelper;
import Classes.Doctor;
import Classes.Hhelper;
import Classes.grlib;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;



public class Docapptimes  extends JFrame{
	ResultSet res;
	Connection con = null;
	grlib gr = new grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Calhelper cc = new Calhelper();
	private String rowid = "";
	private int myrow = 0;
	String[] workdays = cc.wdaynames();
	
	Docapptimes(){
		initcomponents();
		hh.iconhere(this);
		dd.doctorscombofill(cmbdoctors);
	  dd.intable_update(intable,"");
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
	
		setSize(930, 530);
		setLayout(null);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(128, 255, 128));
		lbheader = hh.fflabel("Doctor appointment times");
		lbheader.setBounds(20, 10, 350, 35);
		add(lbheader);

		bPanel = new JPanel(null);
		bPanel.setBounds(10,50,905, 430);
		bPanel.setBackground(new Color(128, 255, 128));	
		add(bPanel);
		
		ePanel = new JPanel(null);
		ePanel.setBounds(10,10,340, 385);
		ePanel.setBackground(new Color(128, 255, 128));
		ePanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
		bPanel.add(ePanel);	
		
		tPanel = new JPanel(null);
		tPanel.setBounds(355,10,530, 385);
		tPanel.setBackground(new Color(128, 255, 128));
		tPanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
		bPanel.add(tPanel);
		
		lbdoctor = hh.clabel("Doctors");
		lbdoctor.setBounds(10,40,80, 25);
		ePanel.add(lbdoctor);	

		cmbdoctors = gr.grcombo();
		cmbdoctors.setFocusable(true);
		cmbdoctors.setName("doctors");
		cmbdoctors.setBounds(120, 40, 200, 30);
		ePanel.add(cmbdoctors);	
		
		lbday = hh.clabel("Day");
		lbday.setBounds(10,90,80, 25);
		ePanel.add(lbday);			

		cmbwdays = gr.grcombo();
		cmbwdays.setFocusable(true);
		cmbwdays.setName("wdays");
		cmbwdays.setBounds(120, 90, 200, 30);
		ePanel.add(cmbwdays);		
		for (int i = 1; i < 6; i++) {		
			 cmbwdays.addItem(workdays[i]);	
			}
		
		lbstart = hh.clabel("Start time");
		lbstart.setBounds(10,140,80, 25);
		ePanel.add(lbstart);	
		
		String rtime = "07:00";
		startmodel = new SpinnerDateModel();
		starttime = hh.cspinner(startmodel);
		starttime.setBounds(120, 140, 70, 25);
		starttime.setBorder(hh. myRaisedBorder);
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
		lbend.setBounds(10, 190, 80, 25);
		ePanel.add(lbend);
		
		endmodel = new SpinnerDateModel();
		endtime = hh.cspinner(endmodel);
		endtime.setBounds(120, 190, 70, 25);
		endtime.setBorder(hh. myRaisedBorder);
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
		
		intable = hh.ztable();
		intable.setTableHeader(new JTableHeader(intable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(intable);
		intable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				intable.scrollRectToVisible(intable.getCellRect(intable.getRowCount() - 1, 0, true));
			}
		});
		intable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				
				int row = intable.getSelectedRow();
				if (row >= 0) {				
					rowid = intable.getValueAt(row, 0).toString();
					int number = 0;
					String cnum = intable.getValueAt(row, 1).toString();
					if (!hh.zempty(cnum)) {
						number = Integer.parseInt(cnum);
					}
					hh.setSelectedValue(cmbdoctors, number);
					cmbdoctors.updateUI();
				    cmbwdays.setSelectedItem(intable.getValueAt(row, 3).toString());				    
				    String starttime = intable.getValueAt(row, 4).toString();
					startmodel.setValue(hh.stringtotime(starttime));
					String endtime = intable.getValueAt(row, 5).toString();
					endmodel.setValue(hh.stringtotime(endtime));
					myrow = row;
				}
			}
		});

		jScrollPane1 = new JScrollPane(intable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		intable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { },
				new String[] { "tid","did", "Doctor Name", "Day", "Start time", "End time" }));	
		hh.setJTableColumnsWidth(intable, 510, 0, 0, 45, 25,15,15);
		jScrollPane1.setViewportView(intable);
		jScrollPane1.setBounds(10, 25, 510, 340);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);

		setVisible(true);
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
	private void savebuttrun(){
		String sql = "";	
		int did = 0;
		String dname ="";
		DefaultTableModel d1 = (DefaultTableModel) intable.getModel();	
		if (cmbdoctors.getSelectedItem() != null) {		
			Doctor  doc = (Doctor) cmbdoctors.getSelectedItem();
			did = doc.getDid();
			 dname = doc.getDname();			
		}
		String dayname = String.valueOf(cmbwdays.getSelectedItem());
		JTextField intf = ((JSpinner.DefaultEditor) starttime.getEditor()).getTextField();
		String starttime = intf.getText();
		intf = ((JSpinner.DefaultEditor) endtime.getEditor()).getTextField();
		String endtime = intf.getText();
		if (hh.zempty(dname) || hh.zempty(dayname)) {
			JOptionPane.showMessageDialog(null, "Please fill name, dayname  fields");
			return;
		}
		if (hh.twotime(starttime, endtime) == false) {		
			   return;
			}		
		
		if (rowid != "") {	
			sql = "update doctorapptimes set dayname= '" + dayname + "',"
					+ " did="+ did +", starttime='" + starttime +"', endtime='" +endtime + "' where tid = "
					+ rowid;
		} else {
			sql = "insert into doctorapptimes  (dayname, did, starttime, endtime) " + "values"
					+ " ('" + dayname+"',"+ did +", '" +starttime+"','"+endtime+ "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag > 0) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(tid) AS max_id from doctorapptimes");
					d1.insertRow(d1.getRowCount(),
							new Object[] { myid, did, dname, dayname, dayname, starttime, endtime });					 
					hh.gotolastrow(intable);
					if (intable.getRowCount() > 0) {
						int row = intable.getRowCount() - 1;
						intable.setRowSelectionInterval(row, row);
					}
				} else {
					table_rowrefresh( did, dname,dayname, starttime,endtime);
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
	private void table_rowrefresh(int did, String dname,String dayname, String starttime, String endtime) {
		DefaultTableModel d1 = (DefaultTableModel) intable.getModel();
		d1.setValueAt(did, myrow, 1);
		d1.setValueAt(dname, myrow, 2);
		d1.setValueAt(dayname, myrow, 3);	
		d1.setValueAt(starttime, myrow, 4);
		d1.setValueAt(endtime, myrow, 5);
	}
	private int data_delete(){
		String sql = "delete from doctorapptimes  where tid =";
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) intable.getModel();
		int sIndex = intable.getSelectedRow();
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
	
	
	private void clearFields(){
		cmbdoctors.setSelectedIndex(-1);
		cmbwdays.setSelectedItem("Monday");
		String rtime = "07:00";
		starttime.setValue(hh.stringtotime(rtime));
		rtime = "18:00";
		endtime.setValue(hh.stringtotime(rtime));
		myrow = 0;
		rowid = "";		
	}
	
	public static void main(String args[]) {		
		 EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {	            
	                	Docapptimes in  = new Docapptimes();	
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	}
JLabel lbheader, lbday, lbdoctor, lbstart,lbend;
JPanel bPanel, ePanel, tPanel;
JComboBox cmbdoctors, cmbwdays;
SpinnerDateModel startmodel, endmodel;
JSpinner starttime, endtime;
JButton btnsave, btncancel, btndelete;
JTable intable;
JScrollPane jScrollPane1;
}
