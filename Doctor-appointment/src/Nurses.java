import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Classes.Hhelper;
import Classes.Docvalidation;
import Classes.grlib;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Nurses extends JFrame{
	Hhelper hh = new Hhelper();
	grlib gr = new grlib();
	ResultSet res;
	Connection con = null;	
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Docvalidation hvad = new Docvalidation();

    JFrame nur;
    String rowid="";
    int myrow=0;    
	
	Nurses(){
		initcomponents();
		dd.doctable_update(nurtable, "");
		
	}
	public Nurses(JFrame fff) {
		nur = fff;
		initcomponents();
	}
	public void initagain() {
		dd.nurtable_update(nurtable, "");
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

		txnname = gr.gTextField(30);
		txnname.setBounds(130,30,250, 30);
		ePanel.add(txnname);
		txnname.addKeyListener( hh.MUpper());
		
		lbposition = hh.clabel("Position");
		lbposition.setBounds(10,70,110, 25);
		ePanel.add(lbposition);	

		txposition = gr.gTextField(30);
		txposition.setBounds(130,70,250, 30);
		ePanel.add(txposition);
		txposition.addKeyListener( hh.MUpper());
		
		lbphone = hh.clabel("Phone");
		lbphone.setBounds(10,110,110, 25);
		ePanel.add(lbphone);	

		txphone = gr.gTextField(30);
		txphone.setBounds(130,110,250, 30);
		ePanel.add(txphone);
		txphone.addKeyListener( hh.Onlyphone());
		
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
		
		lbnote = hh.clabel("Note");
		lbnote.setBounds(10,340,110, 25);
		ePanel.add(lbnote);
	  
		txanote= gr.gTextArea();	
		jsp2 = new JScrollPane(txanote, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp2.setBounds(130,340,250, 110);
		jsp2.setViewportView(txanote);
		ePanel.add(jsp2);
		buttPanel = new JPanel(null);
		buttPanel.setBounds(90, 470, 340, 35);
		buttPanel.setOpaque(true);
		buttPanel.setBackground(new Color(178, 216, 216));
		ePanel.add(buttPanel);

		btnsave = gr.sbcs("Save");
		btnsave.setBounds(10, 5, 100, 30);
		btnsave.setForeground(Color.black);
		btnsave.setBackground(hh.lpiros);
		buttPanel.add(btnsave);
		btnsave.addActionListener(listener);

		btncancel = gr.sbcs("Cancel");
		btncancel.setBounds(120, 5, 100, 30);
		btncancel.setForeground(Color.black);
		btncancel.setBackground(Color.green);
		buttPanel.add(btncancel);
		btncancel.addActionListener(listener);

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(230, 5, 100, 30);
		btndelete.setForeground(Color.black);
		btndelete.setBackground(Color.yellow);
		buttPanel.add(btndelete);
		btndelete.addActionListener(listener);		
		dPanel.add(ePanel);
		tPanel = new JPanel(null);
		tPanel.setBounds(460,10,650, 520);
		tPanel.setBackground(new Color(178, 216, 216));
		tPanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
		dPanel.add(tPanel);
		
		nurtable = hh.ztable();
		nurtable.setTableHeader(new JTableHeader(nurtable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(nurtable);
		nurtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				nurtable.scrollRectToVisible(nurtable.getCellRect(nurtable.getRowCount() - 1, 0, true));
			}
		});
		nurtable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {				
				int row = nurtable.getSelectedRow();
				if (row >= 0) {
					txnname.setText(nurtable.getValueAt(row, 1).toString());
					txposition.setText(nurtable.getValueAt(row, 2).toString());
					txphone.setText(nurtable.getValueAt(row, 3).toString());				
					txaqualification.setText(nurtable.getValueAt(row, 4).toString());
					String ideg = nurtable.getValueAt(row, 5).toString();
					cmbgender.setSelectedItem(ideg);
					cmbgender.updateUI();	
					txssn.setText(nurtable.getValueAt(row, 6).toString());
					txanote.setText(nurtable.getValueAt(row, 7).toString());
					rowid = nurtable.getValueAt(row, 0).toString();		
				     myrow = row;
				}
			}
		});	
		nurtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				DefaultTableModel model = (DefaultTableModel) nurtable.getModel();
				try {
					int row = nurtable.getSelectedRow();
					if (row > -1) {	
						String ss = model.getValueAt(row, 4).toString();
				//		txareadqualifi.setText(ss);
					}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
			}
		});

		jScrollPane1 = new JScrollPane(nurtable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		nurtable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null }, },
				new String[] { "nid", "Name", "Position","Phone","qualification","gen","ssn","note"}));
		hh.setJTableColumnsWidth(nurtable, 480, 0, 40, 20, 15, 0, 0,15,0);
		jScrollPane1.setViewportView(nurtable);
		jScrollPane1.setBounds(15, 10, 620, 480);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);		
		return dPanel;
	}
	
	ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();		
			String Text = button.getText();	
				if (Text.equals("Save")) {
					data_save();		
					txnname.requestFocus();
				}else if (Text.equals("Cancel")) {
					clearFields();					
					txnname.requestFocus();					
				} else if (Text.equals("Delete")) {
					data_delete();
				}
		}
	};
	
	private void data_save() {
		String name  = txnname.getText();
		String position= txposition.getText();
		String phone = txphone.getText();	
		String qualifications =  txaqualification.getText();		
		String gender = (String) cmbgender.getSelectedItem();
		String ssn = txssn.getText();
		String note = txanote.getText();	
		String sql;
		DefaultTableModel d1 = (DefaultTableModel) nurtable.getModel();
		
		 if (nurvalidation(name, phone, position, qualifications, ssn) == false) {
				return;
		  	}
		 if (rowid != "") {	
				sql = "update  nurse set nname= '" + name + "', phone= '" + phone + "', position= '"
					 + position + "', qualifications= '" + qualifications +"', gen = '" + gender
					 + "', ssn= '" +ssn + "', note= '" + note +"' where nid = "+ rowid;
			} else {
				sql = "insert into nurse (nname, phone, position, qualifications, gen,ssn, note) values"
						+ " ('" + name + "','"+ phone +"','"+position + "','" + qualifications +"','"+ gender +
						"','" + ssn +"','" + note+"')";
			}
			try {
				int flag = dh.Insupdel(sql);
				if (flag > 0) {
					hh.ztmessage("Success", "Message");
					if (rowid == "") {
						int myid = dd.table_maxid("SELECT MAX(nid) AS max_id from nurse");
						d1.insertRow(d1.getRowCount(),
								new Object[] { myid, name, position,phone,  qualifications,gender, ssn, note });
					//	"nid", "Name", "Position","Phone","qualification","gen","ssn","note"
						hh.gotolastrow(nurtable);
						if (nurtable.getRowCount() > 0) {
							int row = nurtable.getRowCount() - 1;
							nurtable.setRowSelectionInterval(row, row);
						}
					} else {
						table_rowrefresh(name, phone, position, qualifications, gender, ssn, note);
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
	private void table_rowrefresh(String name, String phone, String position, String qualifications, 
			String gender, String ssn, String  note) {
		DefaultTableModel d1 = (DefaultTableModel) nurtable.getModel();
		d1.setValueAt(name, myrow, 1);
		d1.setValueAt(position, myrow, 2);
		d1.setValueAt(phone, myrow, 3);		
		d1.setValueAt(qualifications, myrow, 4);
		d1.setValueAt(gender, myrow, 5);
		d1.setValueAt(ssn, myrow, 6);
		d1.setValueAt(note, myrow, 7);
	}
	private int data_delete(){
		String sql = "delete from nurse  where nid =";
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) nurtable.getModel();
		int sIndex = nurtable.getSelectedRow();
		if (sIndex < 0) {
			return flag;
		}
		String nid = d1.getValueAt(sIndex, 0).toString();
		if (nid.equals("")) {
			return flag;
		}	
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			String vsql = sql + nid;
			flag = dh.Insupdel(vsql);
			if (flag > 0) {
				d1.removeRow(sIndex);
				clearFields();
			}
		}
		return flag;
		}

	 private Boolean nurvalidation(String name, String phone, String position, String qualifications, String ssn) 
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
			 if (!hvad.positionvalid(position)) {				
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

	
	
	void clearFields() {
		 txnname.setText("");
		 txposition.setText("");
		 txphone.setText("");
		 txssn.setText("");
		 cmbgender.setSelectedIndex(-1);
		 txaqualification.setText("");	
		 txanote.setText("");
		  txnname.requestFocus();
		  rowid = "";
		  myrow = 0;
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
   JLabel lbname, lbphone, lbqualification, lbgen, lbssn, lbposition, lbnote;
   JTextField txnname,  txphone,  txssn,txposition;
   JTextArea  txaqualification, txanote;
   JButton btnsave, btncancel, btndelete;
   JComboBox cmbgender;
   JScrollPane  jScrollPane1, jsp1, jsp2;
    JTable nurtable;

}
