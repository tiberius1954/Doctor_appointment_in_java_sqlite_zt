package Databaseop;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import Classes.Doctor;
import Classes.Hhelper;


public class Databaseop {
	Connection con;
	Statement stmt;
	PreparedStatement pst;
	ResultSet rs;	
	DatabaseHelper dh = new DatabaseHelper();
	Hhelper hh = new Hhelper();

	public int data_delete(JTable dtable, String sql) {
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) dtable.getModel();
		int sIndex = dtable.getSelectedRow();
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
			}
		}
		return flag;
	}	

	public void rtable_delete(JTable dtable, String sql) {
		DefaultTableModel d1 = (DefaultTableModel) dtable.getModel();
		int flag = dh.Insupdel(sql);
		if (flag > 0) {
			d1.setRowCount(0);
		}
	}	
	
	public int tdata_delete(JTable dtable, String sql, int row) {
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) dtable.getModel();	
		flag = dh.Insupdel(sql);
		if (flag == 1) {
			d1.removeRow(row);
		}
		return flag;
	}


	public void intable_update(JTable dtable, String what) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);
		String Sql = "";
		if (what == "") {
			Sql = "select  a.tid, a.did, d.dname,  a.dayname, a.starttime, a.endtime from doctorapptimes a"
					+ " join doctors  d  on a.did = d.did  order by d.dname, a.dayname, a.starttime";
		} else {
			Sql = "select  a.tid, a.did, d.dname,  a.dayname, a.starttime, a.endtime from doctorapptimes a "
					+"  join doctors  d  on a.did = a.did  where " + what + " order by d.dname, a.dayname, a.starttime";
		}
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String tid= rs.getString("tid");
				String did= rs.getString("did");
				String dname = rs.getString("dname");
				String dayname= rs.getString("dayname");
				String starttime = rs.getString("starttime");			
				String  endtime = rs.getString("endtime");			
				m1.addRow(new Object[] { tid, did, dname, dayname,  starttime, endtime });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();			
		}	
		String[] fej = { "tid","did", "Doctor Name", "Day", "Start time", "End time"};		
		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(fej);	
		hh.setJTableColumnsWidth(dtable, 510, 0, 0, 45, 25,15,15);
//		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) dtable.getDefaultRenderer(Object.class);
	//	renderer.setHorizontalAlignment(SwingConstants.LEFT);	
	
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
	
	public void cntable_update(JTable dtable, String what) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);
		String Sql = "";
		if (what == "") {
			Sql = "select  a.cid, a.did, d.dname,  a.startdate, a.enddate, a.starttime, a.endtime from" 
			+ " docappticancel a  join doctors d  on a.did = d.did order by d.dname, a.startdate, a.starttime";
			
		} else {
			Sql = "select  a.cid, a.did, d.dname,  a.startdate, a.enddate, a.starttime, a.endtime from" 
					+ " docappticancel a  join doctors d  on a.did = d.did where " + what +" order by d.dname, "
							+ " a.startdate, a.starttime";				
		}
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String cid= rs.getString("cid");
				String did= rs.getString("did");
				String dname = rs.getString("dname");
				String startdate= rs.getString("startdate");
				String enddate= rs.getString("enddate");
				String starttime = rs.getString("starttime");			
				String  endtime = rs.getString("endtime");			
				m1.addRow(new Object[] { cid, did, dname, startdate, enddate, starttime, endtime });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();			
		}	
		String[] fej = { "cid","did", "Doctor Name", "Startdate","Enddate", "Start time", "End time"};		
		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(fej);	
		hh.setJTableColumnsWidth(dtable, 555, 0, 0, 40, 17,17,13, 13);
//		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) dtable.getDefaultRenderer(Object.class);
	//	renderer.setHorizontalAlignment(SwingConstants.LEFT);	
	
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
	public void patienttable_update(JTable dtable, String swhere) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);
		String Sql = "";
		if (hh.zempty(swhere)) {
			Sql = "select pid,  pname, dob,  phone,  email, gen, insuranceid, remarks, address  from patient"
					+ " order by pname";
		} else {
			Sql = "select pid,  pname, dob,  phone,  email, gen,insuranceid, remarks, address "
					+ " from patient   where " + swhere +" order by pname";
		}
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String pid = rs.getString("pid");
				String pname = rs.getString("pname");
				String dob = rs.getString("dob");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				String gen = rs.getString("gen");
				String insuranceid =  rs.getString("insuranceid");
				String remarks = rs.getString("remarks");
				String address = rs.getString("address");
				m1.addRow(new Object[] { pid, pname, dob, phone, email,  insuranceid, gen, remarks, address });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		String[] fej = { "pid", "Name", "Dob", "Phone", "Email", "Insureanceid","Gen", "remarks","Address" };
		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(fej);	
		hh.setJTableColumnsWidth(dtable, 735, 0, 30, 20, 15,20,15,0,0,0);
		
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

	
	public void doctable_update(JTable dtable, String what) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);
		String Sql = "";
		if (what == "") {
			Sql = "select  did, dname,  phone, email, qualifications, gen, ssn from doctors order by dname";
		} else {
			Sql = "select  did, dname,  phone, email, qualifications, gen, ssn from doctors "
					+ " where " + what + "  order by dname";
		}
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String did= rs.getString("did");
				String dname = rs.getString("dname");
				String phone= rs.getString("phone");
				String email = rs.getString("email");			
				String  qualifications = rs.getString("qualifications");
				String ssn = rs.getString("ssn");		
				String gen = rs.getString("gen");
				m1.addRow(new Object[] { did, dname, phone,  email, qualifications,gen,ssn });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();			
		}	
		String[] fej = { "did", "Name", "Phone", "Email", "Qualifications","Gender","Ssn"};		
		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(fej);	
		hh.setJTableColumnsWidth(dtable, 620, 0, 40,20, 40, 0, 0, 0	);
//		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) dtable.getDefaultRenderer(Object.class);
	//	renderer.setHorizontalAlignment(SwingConstants.LEFT);	
	
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
	
	public void patientapptimes(String pid, JTable dtable){
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);
		String Sql = "select a.did, d.dname, a.app_date,  a.app_time, a.day from appointments a "
				+ " left join  doctors d  on a.did = d.did  where  pid ='" +pid +"'";	
		String text="";
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String dname =  rs.getString("dname");
				String appdate= rs.getString("app_date");
				String apptime = rs.getString("app_time");
				String day = rs.getString("day");			
	            m1.addRow(new Object[] { dname, appdate, apptime, day});
		}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();			
		}			
		return;		
	}
	public String doctorapptimes(String docid){
		String ret="";	
		String Sql = "select  dayname,  starttime, endtime from doctorapptimes where did ='" +docid +"'";	
		String text="";
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String dayname= rs.getString("dayname");
				String starttime = rs.getString("starttime");
				String endtime = rs.getString("endtime");
				text = text + " " + hh.padr(dayname, 12) +"   " + starttime + "   " +endtime +"\n";					
			}
			ret = text;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();			
		}			
		return ret;		
	}
	

	public void nurtable_update(JTable dtable, String what) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);
		String Sql = "";
		if (what == "") {
			Sql = "select  nid, nname, position, phone, qualifications, gen, ssn, note from nurse order by nname";
		} else { 
			Sql = "select  nid, nname, position, phone, qualifications, gen,  ssn, note from nurse "
					+ "  where nid= " + what + " order by nname";
		}
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String nid= rs.getString("nid");
				String name= rs.getString("nname");
				String position= rs.getString("position");
				String phone= rs.getString("phone");
				String qualification=  rs.getString("qualifications");
				String gender= rs.getString("gen");			
				String ssn= rs.getString("ssn");			
				String note= rs.getString("note");					
				m1.addRow(new Object[] { nid, name, position,phone,qualification, gender, ssn, note });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();			
		}	
		String[] fej = { "nid", "Name", "Position","Phone","qualification","gen","ssn","note"};		
		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(dtable, 480, 0, 40, 20, 15, 0, 0,15,0);		
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) dtable.getDefaultRenderer(Object.class);
			
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

	public int table_maxid(String sql) {
		int myid = 0;
		try {
			con = dh.getConnection();
			rs = dh.GetData(sql);
			if (!rs.next()) {
				System.out.println("Error.");
			} else {
				myid = rs.getInt("max_id");
			}
			dh.CloseConnection();
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}
		return myid;
	}

	public Boolean cannotdelete(String sql) {
		Boolean found = false;
		rs = dh.GetData(sql);
		try {
			if (rs.next()) {
				found = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dh.CloseConnection();
		return found;
	}
	
	public void doctorscombofill(JComboBox ccombo) {
		ccombo.removeAllItems();
		Doctor A = new Doctor(0, " ");
		ccombo.addItem(A);
		String sql = "select did, dname  from doctors order by upper(dname)";
		try {
			ResultSet rs = dh.GetData(sql);
			while (rs.next()) {
				A = new Doctor(rs.getInt("did"), rs.getString("dname"));
				ccombo.addItem(A);
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
