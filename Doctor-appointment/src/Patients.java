import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import Classes.Calhelper;
import Classes.Doctor;
import Classes.Hhelper;
import Classes.grlib;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Patients extends JFrame {
	ResultSet rs;
	Connection con = null;
	grlib gr = new grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Calhelper cc = new Calhelper();
	JDateChooser dobdate = new JDateChooser(new Date());
	private String rowid = "";
	private int myrow = 0;

	Patients() {
		initcomponents();
		hh.iconhere(this);
		dd.patienttable_update(ptable, "");
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
		setSize(1280, 700);
		setLayout(null);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(0, 128, 128));
		lbheader = hh.fflabel("Patients");
		lbheader.setBounds(20, 10, 350, 35);
		lbheader.setForeground(new Color(255, 255, 255));
		add(lbheader);
		
		bPanel = new JPanel(null);
		bPanel.setBounds(5,50,1250, 600);
		bPanel.setBackground(new Color(178, 216, 216));
		add(bPanel);
		
		ePanel = new JPanel(null);
		ePanel.setBounds(5,10,475, 580);
		ePanel.setBackground(new Color(178, 216, 216));
		ePanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
		bPanel.add(ePanel);	
		
		tPanel = new JPanel(null);
		tPanel.setBounds(485,10,764, 580);
		tPanel.setBackground(new Color(178, 216, 216));
		tPanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
		bPanel.add(tPanel);		
		
		lbname = hh.clabel("Name");
		lbname.setBounds(10, 20, 90, 25);
		ePanel.add(lbname);

		txname = hh.cTextField(30);
		txname.setBounds(110, 20, 250, 30);
		ePanel.add(txname);
		txname.addKeyListener(hh.MUpper());

		lbphone = hh.clabel("Phone");
		lbphone.setBounds(10, 60, 90, 25);
		ePanel.add(lbphone);

		txphone = hh.cTextField(30);
		txphone.setBounds(110, 60, 250, 30);
		ePanel.add(txphone);
		txphone.addKeyListener(hh.Onlyphone());

		lbemail = hh.clabel("Email");
		lbemail.setBounds(10, 100, 90, 25);
		ePanel.add(lbemail);

		txemail = hh.cTextField(30);
		txemail.setBounds(110, 100, 250, 30);
		ePanel.add(txemail);
		
		lbaddress = hh.clabel("Address");
		lbaddress.setBounds(10, 140, 90, 25);
		ePanel.add(lbaddress);

		txaaddress = hh.cTextarea();
		txaaddress.setBounds(110, 140, 350, 60);
		ePanel.add(txaaddress);		

		lbdob = hh.clabel("Dob");
		lbdob.setBounds(10, 210, 90, 25);
		ePanel.add(lbdob);
		
		dobdate.setDateFormatString("yyyy-MM-dd");
		dobdate.setFont(new Font("Arial", Font.BOLD, 16));
		dobdate.setBounds(110, 210, 120, 30);
		dobdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		ePanel.add(dobdate);
		dobdate.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					// countd();
				}
			}
		});
		
		lbgen = hh.clabel("Gender");
		lbgen.setBounds(10, 250, 90, 25);
		ePanel.add(lbgen);

		cmbgender = hh.cbcombo();
		cmbgender.addItem("M");
		cmbgender.addItem("F");
		cmbgender.setBounds(110, 250, 50, 30);
		ePanel.add(cmbgender);
		
		lbinsuranceid = hh.clabel("Insuranceid");
		lbinsuranceid.setBounds(10, 290, 90, 25);
		ePanel.add(lbinsuranceid);
		
		txinsuranceid = hh.cTextField(30);
		txinsuranceid.setBounds(110, 290, 250, 30);
		ePanel.add(txinsuranceid);
		
		lbremarks = hh.clabel("Remarks");
		lbremarks.setBounds(10, 330, 90, 25);
		ePanel.add(lbremarks);

		txaremarks = hh.cTextarea();
		txaremarks.setBounds(110, 330, 350, 130);
		ePanel.add(txaremarks);		
		
		btnsave = gr.sbcs("Save");
		btnsave.setBounds(70, 500, 120, 30);
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
		btncancel.setBounds(200, 500, 120, 30);
		ePanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
			}
		});

	   btndelete = gr.sbcs("Delete");
		btndelete.setBounds(330, 500, 120, 30);
		btndelete.setBackground(Color.yellow);
		ePanel.add(btndelete);
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				String sql = "delete from patient  where pid =";
				dd.data_delete(ptable,sql);
				clearFields();
			}
		});	
		
		ptable = hh.ztable();
		ptable.setTableHeader(new JTableHeader(ptable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(ptable);
		ptable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				ptable.scrollRectToVisible(ptable.getCellRect(ptable.getRowCount() - 1, 0, true));
			}
		});
			
		ptable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {				
				int row = ptable.getSelectedRow();
				if (row >= 0) {				
					txname.setText(ptable.getValueAt(row, 1).toString());
					try {
					Date date;
					String dd = ptable.getValueAt(row, 2).toString();
					date = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
					dobdate.setDate(date);		
					} catch (ParseException e) {
						e.printStackTrace();
					}
					txphone.setText(ptable.getValueAt(row, 3).toString());
					txemail.setText(ptable.getValueAt(row, 4).toString());
					 txinsuranceid.setText(ptable.getValueAt(row,5).toString());
					 String ideg = ptable.getValueAt(row, 6).toString();
					 cmbgender.setSelectedItem(ideg);
					 cmbgender.updateUI();		
				   	txaremarks.setText(ptable.getValueAt(row, 7).toString());
					txaaddress.setText(ptable.getValueAt(row, 8).toString());										
					rowid = ptable.getValueAt(row, 0).toString();						
				     myrow = row;
				}
			}
		});

		jScrollPane1 = new JScrollPane(ptable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		ptable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { },	
		new String[] { "pid", "Name", "Dob", "Phone", "Email", "Insureanceid","Gen", "remarks","Address" }));
		hh.setJTableColumnsWidth(ptable, 735, 0, 30, 20, 15,20,15,0,0,0);
		jScrollPane1.setViewportView(ptable);
		jScrollPane1.setBounds(15, 25, 735, 430);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);
		
		searchPanel = new JPanel(null);
		searchPanel.setBounds(55,495,665,35);
		searchPanel.setBorder(hh.borderf);
		searchPanel.setBackground(new Color(0, 128, 128));
		tPanel.add(searchPanel);
		
		lbsearch = hh.clabel("Search:");
		lbsearch.setBounds(10, 5, 70, 25);
		searchPanel.add(lbsearch);

		btnGroup = new ButtonGroup();
		rname = new JRadioButton("Name");
		rname.setHorizontalTextPosition(SwingConstants.LEFT);
		rname.setActionCommand("name");
		rname.setBounds(85, 5, 80, 25);
		rname.setFont(new Font("Tahoma", Font.BOLD, 16));
		rname.setBackground(Color.ORANGE);
		btnGroup.add(rname);
		rdob = new JRadioButton("Dob");
		rdob.setHorizontalTextPosition(SwingConstants.LEFT);
		rdob.setActionCommand("dob");
		rdob.setBounds(170, 5, 60, 25);
		rdob.setFont(new Font("Tahoma", Font.BOLD, 16));
		rdob.setBackground(Color.ORANGE);
		btnGroup.add(rdob);

		rtel = new JRadioButton("Phone");
		rtel.setHorizontalTextPosition(SwingConstants.LEFT);
		rtel.setActionCommand("tel");
		rtel.setBounds(235, 5, 80, 25);
		rtel.setFont(new Font("Tahoma", Font.BOLD, 16));
		rtel.setBackground(Color.ORANGE);
		btnGroup.add(rtel);
		searchPanel.add(rname);
		searchPanel.add(rdob);
		searchPanel.add(rtel);

		txsearch = hh.cTextField(25);
		txsearch.setBounds(320, 5, 200, 25);
		searchPanel.add(txsearch);

		btnclear = new JButton();
		btnclear.setFont(new java.awt.Font("Tahoma", 1, 16));
		btnclear.setMargin(new Insets(0, 0, 0, 0));
		btnclear.setBounds(518, 5, 25, 25);
		btnclear.setBorder(hh.borderf);
		btnclear.setText("x");
		searchPanel.add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txsearch.setText("");
				txsearch.requestFocus();
				 dd.patienttable_update(ptable,"");
			}
		});
		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnsearch.setBounds(551, 5, 90, 27);
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		searchPanel.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int jelem = 0;
				if (rname.isSelected()) {
					jelem = 1;
				} else if (rdob.isSelected()) {
					jelem = 2;
				} else if (rtel.isSelected()) {
					jelem = 3;
				} else {
					String qual = "Did not choose !";
					JOptionPane.showMessageDialog(null, qual);
				}
				if (jelem < 1 || hh.zempty(txsearch.getText())) {
					return;
				}
				sqlgyart(jelem);
			}
		});	
		rname.setSelected(true);
		setVisible(true);	
}
	
	private void savebuttrun() {
		DefaultTableModel d1 = (DefaultTableModel) ptable.getModel();	
		String sql = "";		
		String name = txname.getText();	  
		String phone = txphone.getText();
		String email = txemail.getText();
		String dob = ((JTextField) dobdate.getDateEditor().getUiComponent()).getText();		
		String gender = (String) cmbgender.getSelectedItem();
		String remarks = txaremarks.getText();
		String insuranceid = txinsuranceid.getText();
		String address = txaaddress.getText();
	
		if (hh.zempty(name) || hh.zempty(phone) || hh.zempty(dob)) {
			JOptionPane.showMessageDialog(null, "Please fill name, phone, dob  fields");
			return;
		}		
		if (rowid != "") {	
		sql = "update patient set pname= '" + name + "',"
				+ " dob='"+ dob +"', phone='" + phone +"', email='" +email + "',gen='" +gender+"',"
						+ "remarks='" + remarks +"', insuranceid='"+insuranceid+ "', address='" +address 
				+ "' where pid = " + rowid;
		} else {
			sql = "insert into patient  (pname, phone, email, dob, gen, remarks, insuranceid, address) "
		        + "values ('" + name+"','"+ phone +"', '" + email +"','"+ dob + "','"+ gender +"','"
		        + remarks + "','"+ insuranceid +"','"+ address  + "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag > 0) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(pid) AS max_id from patient");
					d1.insertRow(d1.getRowCount(),
							new Object[] { myid, name, dob, phone, email, gender,insuranceid, remarks, address });					 
					hh.gotolastrow(ptable);
					if (ptable.getRowCount() > 0) {
						int row = ptable.getRowCount() - 1;
						ptable.setRowSelectionInterval(row, row);
					}
				} else {
					table_rowrefresh(name,dob, phone,email, gender, insuranceid, remarks, address );
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
	
	private void table_rowrefresh(String name,String dob, String phone, String email, String gender, 
		String insuranceid, String remarks, String address ){	
		DefaultTableModel d1 = (DefaultTableModel) ptable.getModel();
		d1.setValueAt(name, myrow, 1);
		d1.setValueAt(dob, myrow, 2);
		d1.setValueAt(phone, myrow, 3);	
		d1.setValueAt(email, myrow, 4);
		d1.setValueAt(insuranceid, myrow, 5);
		d1.setValueAt(gender, myrow, 6);
		d1.setValueAt(remarks, myrow, 7);
		d1.setValueAt(address, myrow, 8);
	}
	
	private void clearFields(){
		txname.setText("");	  
		txphone.setText("");
		txemail.setText("");		
		Date date = new Date();
		dobdate.setDate(date);
		cmbgender.setSelectedIndex(-1);
		txaremarks.setText("");
		txinsuranceid.setText("");
		txaaddress.setText("");		
		myrow = 0;
		rowid = "";
	}

	private void sqlgyart(int jel) {
		String swhere = "";
		String ss = txsearch.getText().trim().toLowerCase();
		if (jel == 1) {
			swhere = "lower(pname) LIKE '%" + ss + "%' order by pname  COLLATE NOCASE ASC";
		} else if (jel == 2) {
			swhere = "dob LIKE '%" + ss + "%' order by dob COLLATE NOCASE ASC";
		} else {
			swhere = "phone LIKE '%" + ss + "%' order by phone COLLATE NOCASE ASC";
		}
		dd.patienttable_update(ptable, swhere);
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Patients pat = new Patients();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader, lbname, lbphone, lbemail, lbdob, lbgen, lbaddress, lbinsuranceid, lbremarks, lbsearch;
	JTextField txname, txphone, txemail, txinsuranceid;
	JTextArea txaremarks, txaaddress;
	JPanel bPanel, ePanel, tPanel, searchPanel;
	JComboBox cmbgender;
	JButton btnsave, btncancel, btndelete;
	JTable ptable;
	JScrollPane jScrollPane1;
	JRadioButton rname, rtel, rdob;
	ButtonGroup btnGroup = new ButtonGroup();
	JTextField txsearch;
	JButton btnclear, btnsearch;
}
