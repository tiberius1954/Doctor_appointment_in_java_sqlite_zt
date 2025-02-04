import java.awt.*;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Classes.Hhelper;
import Classes.grlib;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Classes.Docvalidation;

public class Doctors extends JFrame{
	Hhelper hh = new Hhelper();
	grlib gr = new grlib();
	ResultSet res;
	Connection con = null;	
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Docvalidation hvad = new Docvalidation();

    JFrame doc;
    String rowid="";
    int myrow=0;
    
	Doctors(){
		initcomponents();
		dd.doctable_update(doctable, "");		
	}
	public Doctors(JFrame fff) {
		doc = fff;
		initcomponents();
	}
	public void initagain() {
		dd.doctable_update(doctable, "");
	}

	public JPanel initcomponents() {
		dPanel = new JPanel(null);	
		dPanel.setBounds(0, 0, 1125, 548);
		dPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		dPanel.setBackground(new Color(178, 216, 216));
		
		ePanel = new JPanel(null);
		ePanel.setBounds(10,10,440, 520);
		ePanel.setBackground(new Color(178, 216, 216));
		ePanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
		dPanel.add(ePanel);			
		
		lbname = hh.clabel("Name");
		lbname.setBounds(10,30,110, 25);
		ePanel.add(lbname);	

		txdname = gr.gTextField(30);
		txdname.setBounds(130,30,250, 30);
		ePanel.add(txdname);
		txdname.addKeyListener( hh.MUpper());
		
		lbphone = hh.clabel("Phone");
		lbphone.setBounds(10,70,110, 25);
		ePanel.add(lbphone);	

		txphone = gr.gTextField(30);
		txphone.setBounds(130,70,250, 30);
		ePanel.add(txphone);
		txphone.addKeyListener( hh.Onlyphone());
		
		lbemail = hh.clabel("Email");
		lbemail.setBounds(10,110,110, 25);
		ePanel.add(lbemail);	

		txemail= gr.gTextField(30);
		txemail.setBounds(130,110,250, 30);
		ePanel.add(txemail);
		
		lbqualification = hh.clabel("Qualifications");
		lbqualification.setBounds(10,150,110, 25);
		ePanel.add(lbqualification);	

		txaqualification= gr.gTextArea();
		jsp1 = new JScrollPane(txaqualification, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp1.setBounds(130,150,250, 90);
		jsp1.setViewportView(txaqualification);
		ePanel.add(jsp1);
		
		lbgen = hh.clabel("Gender");
		lbgen.setBounds(10,260,110, 25);
		ePanel.add(lbgen);	
		
		cmbgender= gr.grcombo();
		cmbgender.addItem("M");
		cmbgender.addItem("F");
		cmbgender.setBounds(130,260,50, 25);
		ePanel.add(cmbgender);		
		
		lbssn = hh.clabel("SSN");
		lbssn.setBounds(10,300,110, 25);
		ePanel.add(lbssn);
	  
		txssn= gr.gTextField(30);
		txssn.setBounds(130,300,250, 25);
		ePanel.add(txssn);
		
		buttPanel = new JPanel(null);
		buttPanel.setBounds(200, 340, 200, 150);
		buttPanel.setOpaque(true);
		buttPanel.setBackground(new Color(178, 216, 216));
		ePanel.add(buttPanel);		
		
		btnsave =gr.sbcs("Save");
		btnsave.setBounds(10, 10, 100, 30);
		btnsave.setForeground(Color.black);
		btnsave.setBackground(hh.lpiros);
		buttPanel.add(btnsave);	
		btnsave.addActionListener(listener);
	
		btncancel = gr.sbcs("Cancel");
		btncancel.setBounds(10, 50, 100, 30);
		btncancel.setForeground(Color.black);
		btncancel.setBackground(Color.green);
		buttPanel.add(btncancel);
		btncancel.addActionListener(listener);	
		
		btndelete = gr.sbcs("Delete");	
		btndelete.setBounds(10, 90, 100, 30);
		btndelete.setForeground(Color.black);
		btndelete.setBackground(Color.yellow);
		buttPanel.add(btndelete);		
		btndelete.addActionListener(listener);		

		tPanel = new JPanel(null);
		tPanel.setBounds(460,10,650, 520);
		tPanel.setBackground(new Color(178, 216, 216));
		tPanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
		dPanel.add(tPanel);
		
		doctable = hh.ztable();
		doctable.setTableHeader(new JTableHeader(doctable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(doctable);
		doctable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				doctable.scrollRectToVisible(doctable.getCellRect(doctable.getRowCount() - 1, 0, true));
			}
		});
		doctable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {				
				int row = doctable.getSelectedRow();
				if (row >= 0) {
					txdname.setText(doctable.getValueAt(row, 1).toString());
					txphone.setText(doctable.getValueAt(row, 2).toString());
					txemail.setText(doctable.getValueAt(row, 3).toString());
					txaqualification.setText(doctable.getValueAt(row, 4).toString());
					txssn.setText(doctable.getValueAt(row, 6).toString());					
					String ideg = doctable.getValueAt(row, 5).toString().trim();
					cmbgender.setSelectedItem(ideg);
					cmbgender.updateUI();				
					rowid = doctable.getValueAt(row, 0).toString();		
				     myrow = row;
				}
			}
		});	
		doctable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				DefaultTableModel model = (DefaultTableModel) doctable.getModel();
				try {
					int row = doctable.getSelectedRow();
					if (row > -1) {	
						String ss = model.getValueAt(row, 4).toString();
						txareadqualifi.setText(ss);
					}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
			}
		});

		jScrollPane1 = new JScrollPane(doctable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		doctable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null }, },
				new String[] { "did", "Name", "Phone", "Email","qualifications","qender","ssn"}));	
		hh.setJTableColumnsWidth(doctable, 620, 0, 40,20, 40, 0 ,0,0	);
		jScrollPane1.setViewportView(doctable);
		jScrollPane1.setBounds(15, 10, 620, 350);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);		
		
		 txareadqualifi = gr.gTextArea();	
		txareadqualifi.setEditable(false);				
		jsp2 = new JScrollPane(txareadqualifi , JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp2.setBounds(180, 380, 320, 100);
		jsp2.setViewportView(txareadqualifi );
		tPanel.add(jsp2);		
		return dPanel;
	}
	
	ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();		
			String Text = button.getText();	
				if (Text.equals("Save")) {
					data_save();		
					txdname.requestFocus();
				}else if (Text.equals("Cancel")) {
					clearFields();					
					txdname.requestFocus();					
				} else if (Text.equals("Delete")) {
					data_delete();
				}
		}
	};
	private void data_save() {
		String name  = txdname.getText();
		String phone = txphone.getText();
		String email = txemail.getText();
		String qualifications =  txaqualification.getText();		
		String ssn = txssn.getText();
		String gender = (String) cmbgender.getSelectedItem();
		String sql;
		DefaultTableModel d1 = (DefaultTableModel) doctable.getModel();
		
		 if (docvalidation(name, phone, email, qualifications, ssn) == false) {
				return;
		  	}
		 if (rowid != "") {	
				sql = "update  doctors set dname= '" + name + "', phone= '" + phone + "', email= '"
					 + email + "', qualifications= '" + qualifications +"', gen = '" + gender
					 + "', ssn= '" +ssn + "' where did = "+ rowid;
			} else {
				sql = "insert into doctors (dname, phone, email, qualifications, gen,ssn) values"
						+ " ('" + name + "','"+ phone +"','"+email + "','" + qualifications +"','"+ gender +
						"','" + ssn +"')";
			}
			try {
				int flag = dh.Insupdel(sql);
				if (flag > 0) {
					hh.ztmessage("Success", "Message");
					if (rowid == "") {
						int myid = dd.table_maxid("SELECT MAX(did) AS max_id from doctor");
						d1.insertRow(d1.getRowCount(),
								new Object[] { myid, name, phone, email, qualifications,gender, ssn });
						hh.gotolastrow(doctable);
						if (doctable.getRowCount() > 0) {
							int row = doctable.getRowCount() - 1;
							doctable.setRowSelectionInterval(row, row);
						}
					} else {
						table_rowrefresh(name, phone, email, qualifications, gender, ssn);
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
	private void table_rowrefresh(String name, String phone, String email, String qualifications,
			String gender, String ssn) {
		DefaultTableModel d1 = (DefaultTableModel) doctable.getModel();
		d1.setValueAt(name, myrow, 1);
		d1.setValueAt(phone, myrow, 2);	
		d1.setValueAt(email, myrow, 3);
		d1.setValueAt(qualifications, myrow, 4);
		d1.setValueAt(gender, myrow, 5);
		d1.setValueAt(ssn, myrow, 6);
		txareadqualifi.setText(qualifications);
	}
	
	private int data_delete(){
		String sql = "delete from doctors  where did =";
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) doctable.getModel();
		int sIndex = doctable.getSelectedRow();
		if (sIndex < 0) {
			return flag;
		}
		String did = d1.getValueAt(sIndex, 0).toString();
		if (did.equals("")) {
			return flag;
		}	
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			String vsql = sql + did;
			flag = dh.Insupdel(vsql);
			if (flag > 0) {
				d1.removeRow(sIndex);
				clearFields();
			}
		}
		return flag;
		}
	
	public void clearFields(){
		 txdname.setText("");
		 txemail.setText("");
		 txphone.setText("");
		 txssn.setText("");
		 cmbgender.setSelectedIndex(-1);
		 txaqualification.setText("");	
		 txareadqualifi.setText("");
		  txdname.requestFocus();
			rowid = "";
			myrow = 0;
			
	}
	
	 
	 private Boolean docvalidation(String name, String phone, String email, String qualifications, String ssn) 
	 {
			Boolean ret = true;
			 ArrayList<String> err = new ArrayList<String>();
			
			 if (!hvad.namevalid(name)) {
				 err.add(hvad.mess);			
			    	ret = false;
			}
			 if (!hvad.phonevalid(phone)) {
				 err.add(hvad.mess);			
			    	ret = false;
			}
			 if (!hvad.emailvalid(email)) {				
				 err.add(hvad.mess);			
		    	ret = false;
		}
			 if (!hvad.ssnvalid(ssn)) {				
				 err.add(hvad.mess);			
		    	ret = false;
		}	
			 if (!hvad.qualvalid(qualifications)) {				
				 err.add(hvad.mess);			
		    	ret = false;
		}		
			
			 if (err.size() > 0) {
	             JOptionPane.showMessageDialog(null, err.toArray(),"Error message",1);					               
	         }			
			return ret;
		}

	public static void main(String args[]) {
		 EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {	            
	            		Doctors doc = new Doctors();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	}
	

	JPanel dPanel, ePanel, tPanel, buttPanel;
   JLabel lbname, lbemail, lbphone, lbqualification, lbgen, lbaddress, lbssn;
   JTextField txdname, txemail, txphone,  txssn;
   JTextArea  txaqualification, txaaddress, txareadqualifi;
   JButton btnsave, btncancel, btndelete;
   JComboBox cmbgender;
   JScrollPane  jScrollPane1, jsp1, jsp2;
    JTable doctable;
}
