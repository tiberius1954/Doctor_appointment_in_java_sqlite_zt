import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class Doctorchoose extends JFrame {
	ResultSet rs;
	Connection con = null;
	grlib gr = new grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Calhelper cc = new Calhelper();
	String docid="";
	String docname="";
	Doctorchoose() {
		initcomponents();
		hh.iconhere(this);
		dctable_update(dctable);
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
		setSize(940, 400);
		setLayout(null);
		setLocationRelativeTo(null);
		getContentPane().setBackground(hh.vgreen);
		lbheader = hh.fflabel("Doctor choose"); 
		lbheader.setBounds(20, 10, 350, 35);
		add(lbheader);		
		
		dctable = hh.ztable();
		dctable.setTableHeader(new JTableHeader(dctable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(dctable);
		dctable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				dctable.scrollRectToVisible(dctable.getCellRect(dctable.getRowCount() - 1, 0, true));
			}
		});
		dctable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = dctable.getSelectedRow();
				if (row >= 0) {				
					docid = dctable.getValueAt(row, 0).toString();					
				}
			}
		});
		dctable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				DefaultTableModel model = (DefaultTableModel) dctable.getModel();
				try {
					int row = dctable.getSelectedRow();
					if (row > -1) {	
						 docid = model.getValueAt(row, 0).toString();
                            String sss = dd.doctorapptimes(docid);
                            txaorder.setText(sss);
                            docname =model.getValueAt(row, 1).toString();
					}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
			}
		});

		jScrollPane1 = new JScrollPane(dctable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		dctable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "did", "Doctor Name", "Qualifications" }));
		hh.setJTableColumnsWidth(dctable, 600, 0, 30, 70);
		jScrollPane1.setViewportView(dctable);
		jScrollPane1.setBounds(20, 60, 600, 200);
		jScrollPane1.setBorder(hh.borderf);
		add(jScrollPane1);
		
		lbheader1 = hh.fflabel("Ordering time"); 
		lbheader1.setBounds(690, 10, 180, 35);
		add(lbheader1);	
		
		txaorder= new JTextArea();
		txaorder.setBorder(hh.borderf);
		txaorder.setBackground(hh.tcolor);
		txaorder.setForeground(Color.BLACK);
		txaorder.setFont(hh.textf3);
		txaorder.setEditable(false);
		txaorder.setFocusable(false);
		jsp = new JScrollPane();
		jsp.setBounds(640,60, 260, 200);
		jsp.setViewportView(txaorder);
		add(jsp);
		
		btnnext =hh.cbutton("Next");
		btnnext.setBounds(280, 290, 100, 30);
		btnnext.setForeground(Color.black);
		btnnext.setBackground(hh.lpiros);
		add(btnnext);	
		btnnext.addActionListener(listener);
		setVisible(true);
	}
	public void dctable_update(JTable dtable) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);
		String Sql = "select  did, dname, qualifications from doctors order by dname";
	
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String did= rs.getString("did");
				String dname = rs.getString("dname");			
				String  qualifications = rs.getString("qualifications");				
				m1.addRow(new Object[] { did, dname, qualifications});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();			
		}	
		String[] fej = { "did", "Name", "Qualifications"};				
		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(fej);	
		hh.setJTableColumnsWidth(dctable, 600, 0, 30, 70);
		dtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				dtable.scrollRectToVisible(dtable.getCellRect(dtable.getRowCount() - 1, 0, true));
			}
		});
		if (dtable.getRowCount() > 0) {
			int row = dtable.getRowCount() - 1;
			dtable.setRowSelectionInterval(row, row);
		}
	}
	ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			//JButton button = (JButton) e.getSource();	
			if (!hh.zempty(docid)) {
		      new Weekpanel1(docid, docname);
		      dispose();
			}
		}
	};
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Doctorchoose in = new Doctorchoose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	JLabel lbheader, lbheader1;
	JTable dctable;
    JScrollPane 	jScrollPane1, jsp;
    JTextArea  txaorder;
    JButton btnnext;
}
