import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;
import Classes.Calhelper;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Calendarpanel extends JPanel {
	Hhelper hh = new Hhelper();
	Calhelper cc = new Calhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	ResultSet rs;
	Connection con = null;
	LocalDate actdate;
	protected String[] Times = cc.timesint();
	protected String[] workdays = cc.wdaynames();
	protected String[] workdates;
	String[][] qq = new String[44][5];
	JLabel[] h1Label = new JLabel[6];
	JLabel[] h2Label = new JLabel[6];
	ArrayList<String> mytimes = new ArrayList<String>();
	ArrayList<String> timelist = cc.timefill();
	String[] days = cc.daytimes();
	ColoredTButton tbuttons[][];
	JLabel rbuttons[][];
	JScrollPane hscroll;
	private Color sarga = new Color(243, 231, 9);
	JPanel basePanel, headerPanel, columnPanel, bodyPanel, scrPanel;
	public LineBorder border = new LineBorder(Color.BLACK, 1);
	String docdid="";
	private JFrame pframe;

	public Calendarpanel(LocalDate myactdate,JFrame parent, String did) {
		pframe = parent;
		actdate = myactdate;
		docdid=did;
		workdates = cc.weekday(actdate);	
		makedata();
		this.add(createGUI());
	}

	protected JPanel createGUI() {
		basePanel = new JPanel(null);
		basePanel.setBounds(1, 2, 784, 576);	
		basePanel.setBackground(hh.vgreen);
		basePanel.setForeground(Color.BLACK);
		headerPanel = createheader();
		headerPanel.setBounds(15, 0, 765, 70);
		headerPanel.setBackground(hh.vgreen);
	
		basePanel.add(headerPanel);
		columnPanel = createcolumn0();
		columnPanel.setBounds(13, 0, 120, 772);

		basePanel.add(columnPanel);
		bodyPanel = createbody();
		bodyPanel.setBounds(143, 0, 657, 780);
		bodyPanel.setBackground(hh.vgreen);

		scrPanel = new JPanel(null);
		scrPanel.setBackground(hh.vgreen);
		scrPanel.setBounds(0, 0, 780, 780);
		scrPanel.setPreferredSize(new Dimension(780, 780));
		scrPanel.add(columnPanel);
		scrPanel.add(bodyPanel);
		hscroll = new JScrollPane(scrPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    hscroll.setBounds(0, 71, 780, 500);	
	    hscroll.setBackground(hh.vgreen);	
		basePanel.add(hscroll);	
		return basePanel;
	}

	JPanel createheader() {
		String[] lines = { "", "" };
		JPanel cPanel = new JPanel(null);
		cPanel.setBounds(0, 0, 800, 70);	
		cPanel.setBackground(cc.psarga);
		for (int i = 0; i < 6; i++) {
			JPanel iPanel = new JPanel(null);
			iPanel.setBounds(123 *i , 5 , 120, 60);
			iPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			iPanel.setBackground(cc.psarga);
			h1Label[i] = new JLabel(workdates[i], SwingConstants.CENTER);
			h1Label[i].setBounds(5,10,110,20);
			h1Label[i].setBackground(cc.psarga);
			h1Label[i].setFont(hh.textf);
			iPanel.add(h1Label[i]);
			h2Label[i] = new JLabel(workdays[i], SwingConstants.CENTER);
			h2Label[i].setBounds(5,35,110,20);
			h2Label[i].setBackground(cc.psarga);
			h2Label[i].setFont(hh.textf);
			iPanel.add(h2Label[i]);
			cPanel.add(iPanel);
		}
		return cPanel;
	}

	JPanel createcolumn0() {
		JPanel cPanel = new JPanel(null);
		cPanel.setBounds(0, 0, 130, 780);	
		cPanel.setBackground(cc.psarga);
		for (int i = 0; i < Times.length; i++) {
			JPanel iPanel = new JPanel(null);
			iPanel.setBounds(0, 70 * i, 120, 70);
			iPanel.setBackground(cc.psarga);
			iPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			JLabel dLabel = new JLabel(Times[i], SwingConstants.CENTER);
			dLabel.setFont(hh.textf);
			dLabel.setBounds(2,0,110, 60);
			iPanel.add(dLabel);
			cPanel.add(iPanel);
		}
		return cPanel;
	}

	protected JPanel createbody() {
		JPanel cPanel = new JPanel(null);
		cPanel.setBounds(0, 0, 657, 780);	
		tbuttons = new ColoredTButton[44][5];
		rbuttons = new JLabel[44][5];
		int x = 0;
		while (x < 11) {
			getPanel(cPanel, x);
			x++;
		}
		return cPanel;
	}

	private JPanel getPanel(JPanel cPanel, int x) {
		int j = (x + 1) * 4;
		int k = (x + 1) * 4 - 4;
		for (int y = 0; y < 5; y++) {
			JPanel iPanel = new JPanel(null);		
			iPanel.setBounds(y*122, x* 70, 120, 70);
			iPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			int xx = 0;		
			for (int i = k; i < j; i++) {
				String ss = hh.itos(x + 7);
				String stime = hh.padLeftZeros(ss, 2) + ":";
				String sss = stime + hh.padLeftZeros(hh.itos(xx * 15), 2);		
				if (qq[i][y] == "1") {
					rbuttons[i][y] = lbutton();
					rbuttons[i][y].setBounds(0,xx*17, 120,17);
					rbuttons[i][y].setBackground(hh.vgray);
					rbuttons[i][y].setText(sss);
					iPanel.add(rbuttons[i][y]);
				} else if (qq[i][y] == "2") {			
					rbuttons[i][y] = lbutton();
					rbuttons[i][y].setBounds(0,xx*17, 120,17);
					rbuttons[i][y].setBackground(cc.lpink);
					rbuttons[i][y].setText(sss);
					iPanel.add(rbuttons[i][y]);
				} else if (qq[i][y] == "3") {				
					tbuttons[i][y] = (ColoredTButton) gbutton();
					tbuttons[i][y].setBounds(0,xx*17, 120,17);
					tbuttons[i][y].setText(sss);
					String sx = hh.padLeftZeros(hh.itos(i), 2);
					String sy = hh.itos(y);
					tbuttons[i][y].setName(hh.itos(y + 1) + "-" + sss + "-" + sx + "-" + sy);
					iPanel.add(tbuttons[i][y]);
					tbuttons[i][y].addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent evt) {
						buttActionPerformed(evt);
						}
					});
				}
				xx++;
			}
			cPanel.add(iPanel);
		}
		return cPanel;
	}

	private Boolean isoke() {
		Boolean ret = false;
		int size = mytimes.size();
		if (size > 0) {
			ret = true;
		}
		return ret;
	}

	public JToggleButton gbutton() {
		JToggleButton bbutton = new ColoredTButton();
		bbutton.setFont(new Font("Tahoma", Font.BOLD, 12));
		bbutton.setContentAreaFilled(false);
		bbutton.setOpaque(true);
		bbutton.setBackground(Color.orange);
		bbutton.setFocusPainted(false);	
		bbutton.setEnabled(true);
		 bbutton.setBorder(border);
		bbutton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			//	buttActionPerformed(evt);
			}
		});
		return bbutton;
	}

	public JLabel lbutton() {
		JLabel label = new JLabel();
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		label.setOpaque(true);
		label.setHorizontalAlignment(JLabel.CENTER);		
		label.setPreferredSize(new Dimension(120, 17));
		 label.setBorder(border);
		return label;
	}

	private void makedata() {
		arrfill();
		workdates = cc.weekday(actdate);
		datacollect();
		datacollect1();
		datacollect2();
		return;
	}

	private void datacollect() {
		String sql="";
		for (int i = 0; i < 5; i++) {
			String dayname = workdays[i + 1];
			sql = "select starttime, endtime from doctorapptimes  where did ='" + docdid + "' and dayname ='" + dayname
					+ "'";
		
			try {
				rs = dh.GetData(sql);
				while (rs.next()) {
					String stime = rs.getString("starttime").trim();
					String etime = rs.getString("endtime").trim();
					filter(stime, etime, i,"3");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dh.CloseConnection();
			}
		}
	}
	
	private void datacollect1() {
		String  sql ="";
		for (int i = 0; i < 5; i++) {
			String sdate = workdates[i + 1];
			sql = "select app_time from appointments where app_date = date('" + sdate + "')" + " and did ='" + docdid
					+ "'";
			try {
				rs = dh.GetData(sql);
				while (rs.next()) {
					String time = rs.getString("app_time").trim();
					int index = timelist.indexOf(time);
					if (index > -1) {
						qq[index][i] = "2";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dh.CloseConnection();
			}
		}
	}

	private void datacollect2() {		
		String  sql ="";
		for (int i = 0; i < 5; i++) {
			String sdate = workdates[i + 1];
			sql = "select startdate, enddate, starttime, endtime from docappticancel  where startdate <= date('" + sdate + "')" +
			" and enddate >= date('"+sdate +"') and  did ='" + docdid + "'";
			try {
				rs = dh.GetData(sql);
				while (rs.next()) {
					String stime = rs.getString("starttime").trim();
					String etime = rs.getString("endtime").trim();	
					filter(stime, etime,i,"1");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dh.CloseConnection();
			}
		}
	}
		

	private void filter(String stime, String etime, int col, String signal) {
		LocalTime sTime = LocalTime.parse(stime);
		LocalTime eTime = LocalTime.parse(etime);
		for (int j = 0; j < 44; j++) {
			String target = days[j];
			LocalTime Target = LocalTime.parse(target);
			if (Target.compareTo(sTime) >= 0 && Target.compareTo(eTime) < 0) {
				qq[j][col] = signal;
			}
		}
	}

	private void arrfill() {
		for (int x = 0; x < 44; x++) {
			for (int y = 0; y < 5; y++) {
				qq[x][y] = "1";
			}
		}
	}
	
	private void buttActionPerformed(java.awt.event.ActionEvent e) {
		JToggleButton target = (JToggleButton) e.getSource();
		String ezaz = target.getName();
		String mytime = "";
		if (target.isSelected()) {
			int size = mytimes.size();
			if (isoke() == true) {
				target.setSelected(false);
				return;
			}
			int i = hh.stoi(ezaz.substring(0, 1));	
			((Weekpanel1) pframe).writeout(workdates[i], workdays[i],ezaz.substring(2, 7));
			mytime = ezaz.substring(8, 12);
			mytimes.add(mytime);		
		} else {
			mytime = ezaz.substring(8, 12);
			mytimes.remove(mytime);
			int size = mytimes.size();
			((Weekpanel1) pframe).clearFields();
			if (isoke() == false) {
				System.out.println("itt");
			}
		}
	}

	public class ColoredTButton extends JToggleButton {
		@Override
		public void paintComponent(Graphics g) {
			Color bg;
			if (isSelected()) {
				bg = Color.yellow;
				// bg = hh.lpiros;
			} else {
				// bg = hh.neonzold2;
				bg = Color.white;
			}
			setBackground(bg);
			super.paintComponent(g);
		}
	}
	
	
}
