import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import com.toedter.calendar.JDateChooser;
import Classes.Calhelper;
import Classes.Doctor;
import Classes.Hhelper;
import Classes.grlib;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import java.util.Date;
import java.awt.event.ActionListener;

public class Appointmenttable extends JFrame {
	ResultSet rs;
	Connection con = null;
	grlib gr = new grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Calhelper cc = new Calhelper();
	JDateChooser sdate = new JDateChooser(new Date());
	JDateChooser vdate = new JDateChooser(new Date());

	Appointmenttable() {
		initcomponents();
		hh.iconhere(this);
		dd.doctorscombofill(cmbdoctors);
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
		lbheader = hh.fflabel("Appointment table");
		lbheader.setBounds(420, 10, 350, 35);
		add(lbheader);
		
		searchPanel = new JPanel(null);
		searchPanel.setBounds(160,55,692,45);
		searchPanel.setBorder(hh.linesz);
		searchPanel.setBackground(new Color(128, 255, 128));
		add(searchPanel);

		lbdoctor = hh.clabel("Doctors");
		lbdoctor.setBounds(0, 10, 70, 25);
		searchPanel.add(lbdoctor);

		cmbdoctors = gr.grcombo();
		cmbdoctors.setFocusable(true);
		cmbdoctors.setName("doctors");
		cmbdoctors.setBounds(75, 10, 200, 28);
		searchPanel.add(cmbdoctors);

		lbidoszak = hh.clabel("Term");
		lbidoszak.setBounds(280, 10, 40, 25);
		searchPanel.add(lbidoszak);
		sdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		sdate.setDateFormatString("yyyy-MM-dd");
		sdate.setFont(new Font("Arial", Font.BOLD, 16));
		sdate.setBounds(325, 10, 120, 25);
		searchPanel.add(sdate);
		lbjel = hh.clabel(" - ");
		lbjel.setBounds(443, 10, 20, 25);
		searchPanel.add(lbjel);

		vdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		vdate.setDateFormatString("yyyy-MM-dd");
		vdate.setFont(new Font("Arial", Font.BOLD, 16));
		vdate.setBounds(465, 10, 120, 25);
		searchPanel.add(vdate);

		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(hh.skek);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBounds(590, 10, 90, 25);
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		searchPanel.add(btnsearch);
		btnsearch.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqlgyart();
			}
		});

		aptable = hh.ztable();
		aptable.setTableHeader(new JTableHeader(aptable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(aptable);
		aptable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				aptable.scrollRectToVisible(aptable.getCellRect(aptable.getRowCount() - 1, 0, true));
			}
		});

		jScrollPane1 = new JScrollPane(aptable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		aptable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "aid", "Date", "Day", "Time", "Patient", "Phone", "Email" }));
		hh.setJTableColumnsWidth(aptable, 950, 0, 10, 10, 6, 30, 18, 26);	
		jScrollPane1.setViewportView(aptable);
		jScrollPane1.setBounds(30, 120, 950, 340);
		jScrollPane1.setBorder(hh.borderf);
		add(jScrollPane1);

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(450, 500, 130, 30);
		btndelete.setBackground(Color.yellow);
		add(btndelete);
		
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				String sql= " delete from appointments where aid= ";
				dd.data_delete(aptable, sql);
			}
		});
		setVisible(true);
	}
	
	private void sqlgyart() {
		int did = 0;
		if (cmbdoctors.getSelectedItem() != null) {
			Doctor doc = (Doctor) cmbdoctors.getSelectedItem();
			did = doc.getDid();
		} else {
			return;
		}
		String sdid = hh.itos(did);
		String ssdate = ((JTextField) sdate.getDateEditor().getUiComponent()).getText();
		String vvdate = ((JTextField) vdate.getDateEditor().getUiComponent()).getText();
		String swhere = " did= '" + sdid + "' and app_date >= date('" + ssdate + "') and app_date <=" + " date('"
				+ vvdate + "')";
		table_update(swhere);
		return;
	}

	private void table_update(String swhere) {
		DefaultTableModel m1 = (DefaultTableModel) aptable.getModel();
		m1.setRowCount(0);
		String Sql = "select a.aid,  a.app_date,a.day, a.app_time,  p.pname, p.phone, p.email "
				+ "  from appointments a  left join patient p  on a.pid = p.pid  where " + swhere
				+ " order by p.pname, a.app_date, a.app_time ";
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String aid = rs.getString("aid");
				String appdate = rs.getString("app_date");
				String day = rs.getString("day");
				String apptime = rs.getString("app_time");
				String pname = rs.getString("pname");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				m1.addRow(new Object[] { aid, appdate, day, apptime, pname, phone, email });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = { "aid", "Date", "Day", "Time", "Patient", "Phone", "Email" };
		
		((DefaultTableModel) aptable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(aptable, 950, 0, 10, 10, 6, 30, 18, 26);		
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Appointmenttable app = new Appointmenttable();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader, lbdoctor, lbidoszak, lbjel;;
	JComboBox cmbdoctors;
	JTable aptable;
	JScrollPane jScrollPane1;
	JPanel searchPanel;
	JButton btnsearch, btndelete;
}
