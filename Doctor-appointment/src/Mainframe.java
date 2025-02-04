import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.border.Border;
import Classes.Calhelper;
import Classes.Hhelper;
import Classes.grlib;

public class Mainframe extends JFrame {
	grlib gr = new grlib();
	Calhelper cc = new Calhelper();
	Border borderb = BorderFactory.createLineBorder(Color.BLACK, 1);
	Border borderr = BorderFactory.createLineBorder(Color.RED, 1);
	Hhelper hh = new Hhelper();
	MyPanel panel = new MyPanel(500, 40);
	Color color = Color.white;
	Color sszin = new Color(255, 255, 51);
	Font font1 = new java.awt.Font("Lucida Handwriting", 1, 50);
	Mainframe() {
		initComponents();
	}

	private void initComponents() {
	
		addWindowListener((WindowListener) new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				closing();
			}
		});

		 this.setUndecorated(true);	
		this.setSize(1100, 600);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		cont = getContentPane();		
		cont.setBackground(sszin);	
		panel.setBounds(0, 40, 1100, 200);
		panel.setBackground(sszin);
		this.add(panel);
		exitPanel = gr.makeexitpanel(this);
		exitPanel.setBounds(1000, 10, 100, 35);
		exitPanel.setOpaque(true);
		exitPanel.setBackground(new Color(128, 255, 128));
		exitPanel.setBackground(sszin);
		add(exitPanel);
		
		
		btnappo= gr.sbcs("Appointments");
		btnappo.setBounds(290, 260, 225, 35);
		btnappo.setBackground(hh.lpiros);
		add(btnappo);

		btnappo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Doctorchoose dc = new Doctorchoose();				
			}
		});
		
		btnapptab= gr.sbcs("Appointment table");
		btnapptab.setBounds(290, 310, 225, 35);
		btnapptab.setBackground(hh.lpiros);
		add(btnapptab);

		btnapptab.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Appointmenttable ap = new Appointmenttable();
			}
		});
		
		btndocatim= gr.sbcs("Doctors app. times");
		btndocatim.setBounds(290, 360, 225, 35);
		btndocatim.setBackground(hh.lpiros);
		add(btndocatim);

		btndocatim.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Docapptimes dc = new Docapptimes();
			}
		});
		
		btndoccan= gr.sbcs("Doctors app. time cancel");
		btndoccan.setBounds(290, 410, 225, 35);
		btndoccan.setBackground(hh.lpiros);
		add(btndoccan);
		btndoccan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Docapptimecancel dc =new  Docapptimecancel();
			}
		});
		
		
	
		btndocnur= gr.sbcs("Doctors, nurses");
		btndocnur.setBounds(530, 260, 225, 35);
		btndocnur.setBackground(cc.lblue);
		add(btndocnur);

		btndocnur.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Doc_nursezt dc = new Doc_nursezt();
			}
		});
		
		btnpati= gr.sbcs("Patients");
		btnpati.setBounds(530, 310, 225, 35);
		btnpati.setBackground(cc.lblue);
		add(btnpati);

		btnpati.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Patients pt = new Patients();
			}
		});
		
		btnptable = gr.sbcs("Patient table");
		btnptable.setBounds(530, 360, 225, 35);
		btnptable.setBackground(cc.lblue);
		add(btnptable);

		btnptable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Patienttable pt = new Patienttable();
			}
		});
		
		btnexit = gr.sbcs("Exit");
		btnexit.setBounds(530, 410, 225, 35);
		btnexit.setBackground(cc.lblue);
		add(btnexit);

		btnexit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				closing();
			}
		});
		
		this.setVisible(true);
	}
	private void closing() {
	int x, y, d;
	x = 1000;
	y = 600;
	d = 10;
	while (x > 0 && y > 0) {
		setSize(x, y);
		x = x - 2 * d;
		y = y - d;
		setVisible(true);
		try {
			Thread.sleep(10);
		} catch (Exception e) {
			System.out.println("Error:" + e);
		}
	}
	dispose();
	}
	public MyPanel getPanel() {
		return panel;
	}

	public class MyPanel extends JPanel {
		private int x;
		private int y;

		public MyPanel(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			x++;
			// y++;
			g.setColor(Color.black);
			g.setFont(font1);
			g.drawString("DOCTOR APPOINTMENT", 200, 120);
			g.setColor(sszin);
			g.fillRect(x, y, 700, 100);
		}
	}

	public static void main(String args[]) {
		Mainframe frame = new Mainframe();
		int i = 0;
		while (i < 650) {
			frame.getPanel().repaint();
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
	}

	JPanel exitPanel, hidpanel, basepanel;
	JLabel lbheader;
	Container cont;
	JButton btnappo, btndocnur, btnpati, btnapptab, btndocatim, btndoccan, btnptable, btnexit;
}
