import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Classes.Calhelper;
import Classes.Hhelper;
import Classes.grlib;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Patienttable extends JFrame {
	ResultSet rs;
	Connection con = null;
	grlib gr = new grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Calhelper cc = new Calhelper();

	Patienttable() {
		initcomponents();
		hh.iconhere(this);
		dd.patienttable_update(ptable,"");
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
		setSize(1030, 620);
		setLayout(null);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(128, 255, 128));
		lbheader = hh.fflabel("Patient table");
		lbheader.setBounds(420, 10, 350, 35);
		add(lbheader);
		
		searchPanel = new JPanel(null);
		searchPanel.setBounds(200,45,645,45);
		searchPanel.setBorder(hh.linesz);
		searchPanel.setBackground(new Color(128, 255, 128));
		add(searchPanel);

		lbsearch = hh.clabel("Search:");
		lbsearch.setBounds(0, 10, 70, 25);
		searchPanel.add(lbsearch);

		btnGroup = new ButtonGroup();
		rname = new JRadioButton("Name");
		rname.setHorizontalTextPosition(SwingConstants.LEFT);
		rname.setActionCommand("name");
		rname.setBounds(75, 10, 80, 25);
		rname.setFont(new Font("Tahoma", Font.BOLD, 16));
		rname.setBackground(Color.ORANGE);
		btnGroup.add(rname);
		rdob = new JRadioButton("Dob");
		rdob.setHorizontalTextPosition(SwingConstants.LEFT);
		rdob.setActionCommand("dob");
		rdob.setBounds(160, 10, 60, 25);
		rdob.setFont(new Font("Tahoma", Font.BOLD, 16));
		rdob.setBackground(Color.ORANGE);
		btnGroup.add(rdob);

		rtel = new JRadioButton("Phone");
		rtel.setHorizontalTextPosition(SwingConstants.LEFT);
		rtel.setActionCommand("tel");
		rtel.setBounds(225, 10, 80, 25);
		rtel.setFont(new Font("Tahoma", Font.BOLD, 16));
		rtel.setBackground(Color.ORANGE);
		btnGroup.add(rtel);
		searchPanel.add(rname);
		searchPanel.add(rdob);
		searchPanel.add(rtel);

		txsearch = hh.cTextField(25);
		txsearch.setBounds(310, 10, 200, 25);
		searchPanel.add(txsearch);

		btnclear = new JButton();
		btnclear.setFont(new java.awt.Font("Tahoma", 1, 16));
		btnclear.setMargin(new Insets(0, 0, 0, 0));
		btnclear.setBounds(511, 10, 25, 25);
		btnclear.setBorder(hh.borderf);
		btnclear.setText("x");
		searchPanel.add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txsearch.setText("");
				txsearch.requestFocus();
				// table_update("");
			}
		});
		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnsearch.setBounds(542, 10, 90, 27);
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
					String text = ptable.getValueAt(row, 7).toString();
					txanote.setText(text);
				}
			}
		});

		ptable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				DefaultTableModel model = (DefaultTableModel) ptable.getModel();
				try {
					int row = ptable.getSelectedRow();
					if (row > -1) {
						String pid = model.getValueAt(row, 0).toString();
						dd.patientapptimes(pid, aptable);
					}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
			}
		});

		jScrollPane1 = new JScrollPane(ptable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		ptable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] {  "pid", "Name", "Dob", "Phone", "Email", "Insureanceid","Gen", "remarks","Address"}));
	    hh.setJTableColumnsWidth(ptable, 960, 0, 30, 10, 15,20,15,0,0,0);
		jScrollPane1.setViewportView(ptable);
		jScrollPane1.setBounds(30, 100, 960, 230);
		jScrollPane1.setBorder(hh.borderf);
		add(jScrollPane1);

		aptable = hh.ztable();
		aptable.setTableHeader(new JTableHeader(aptable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		lbappointment = hh.clabel("Appointments");
		lbappointment.setBounds(10, 340, 130, 30);
		add(lbappointment);

		lbremarks = hh.clabel("Remarks");
		lbremarks.setBounds(640, 340, 70, 30);
		add(lbremarks);

		hh.madeheader(aptable);
		aptable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				aptable.scrollRectToVisible(aptable.getCellRect(aptable.getRowCount() - 1, 0, true));
			}
		});

		jScrollPane2 = new JScrollPane(aptable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		aptable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "Doctor", "Date", "Time", "Day" }));
		hh.setJTableColumnsWidth(aptable, 600, 50, 20, 8, 22);
		jScrollPane2.setViewportView(aptable);
		jScrollPane2.setBounds(30, 370, 600, 150);
		jScrollPane2.setBorder(hh.borderf);
		add(jScrollPane2);

		txanote = new JTextArea();
		txanote.setBackground(hh.tcolor);
		txanote.setBorder(hh.borderf);
		txanote.setFont(hh.textf5);
		txanote.setEditable(false);
		txanote.setFocusable(false);
		jsp = new JScrollPane(txanote, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setBounds(640, 370, 340, 150);
		jsp.setViewportView(txanote);
		add(jsp);
		setVisible(true);
	}

	private void sqlgyart(int jel) {
		String sql = "";
		String ss = txsearch.getText().trim().toLowerCase();
		if (jel == 1) {
			sql = "lower(pname) LIKE '%" + ss + "%' order by pname  COLLATE NOCASE ASC";
		} else if (jel == 2) {
			sql = "dob LIKE '%" + ss + "%' order by dob COLLATE NOCASE ASC";
		} else {
			sql = "phone LIKE '%" + ss + "%' order by phone COLLATE NOCASE ASC";
		}	
		dd.patienttable_update(ptable,"");
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Patienttable pat = new Patienttable();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader, lbsearch, lbappointment, lbremarks;
	JRadioButton rname, rtel, rdob;
	ButtonGroup btnGroup = new ButtonGroup();
	JTextField txsearch;
	JButton btnclear, btnsearch;
	JTable ptable, aptable;
	JScrollPane jScrollPane1, jScrollPane2, jsp;
	JPanel dataPanel, searchPanel;
	JTextArea txanote;
}
