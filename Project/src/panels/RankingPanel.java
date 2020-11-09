package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import util.DBConnection;

public class RankingPanel extends JPanel{
	private JButton replaybt;
	
	private ImageIcon backImg = new ImageIcon("images/랭킹패널배경.png");
	private Image back = backImg.getImage();


	public RankingPanel(Object o) {



		
		replaybt = new JButton();
		replaybt.setName("ReplayButton");
		replaybt.setText("처음으로");
		replaybt.setBounds(10, 800, 200, 100);
		replaybt.addMouseListener((MouseListener) o);
		add(replaybt);
		
		//setBackground(Color.GRAY);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(back, 0, 0, this);
		
		g.setFont(new Font("나눔바른고딕", Font.BOLD, 40));
		
		
		DBConnection db = new DBConnection(); //디비 연결
		String sql = "select * from user order by score DESC"; //score 내림차순으로 정렬
		
		try {
			db.rs = db.stmt.executeQuery(sql);

			int i = 0;
			while(db.rs.next() && i < 10) {
				g.drawString(db.rs.getString("name"), 700, 240 + i * 72);
				g.drawString(db.rs.getString("score"), 1100, 240 + i * 72);				
				i++;
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	
	}
}