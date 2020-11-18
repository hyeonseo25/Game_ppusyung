package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import util.DBConnection;

public class ClearPanel extends JPanel{
	private JButton replaybt;
	private JButton rankbt;
	private JTextField name;
	private JButton applybt;
	private JLabel scoreLabel;
	private String scoreText;
	private int score; // 플레이어 점수
	private ImageIcon ClearMessageImg = new ImageIcon("images/게임클리어.gif");
	private Image ClearMessage = ClearMessageImg.getImage();
	

	
	private ImageIcon backImg = new ImageIcon("images/클리어패널배경.png");
	private Image back = backImg.getImage();
	



	
	
	
	public String getScoreText() {
		return scoreText;
	}

	public void setScoreText(String scoreText) {
		this.scoreText = scoreText;
	}
	
	public void setScore(int score) {
		this.score = score;
	}

	public ClearPanel(Object o){
		
		scoreLabel = new JLabel(Integer.toString(score));
		scoreLabel.setLocation(500, 50);
		add(scoreLabel);
		
		
		
		Font font=new Font("돋음", Font.BOLD, 35);
		name = new JTextField("이름을 입력해주세요");
		name.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		name.setHorizontalAlignment(JTextField.CENTER);
		
		// hint 리스너
		name.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
			     	
			}
			@Override
			public void focusGained(FocusEvent e) {
		        	name.setText("");
			}
		});
		name.setName("name");
		name.setFont(font);
		name.setLocation(800, 790);
		name.setSize(600, 50);
		add(name);
		
		
		applybt = new JButton(new ImageIcon("images/button/clearPanelBtn.png"));
		applybt.setName("ReplayButton");
	

		applybt.setBorderPainted(false);
		applybt.setFocusPainted(false);
		applybt.setContentAreaFilled(false);
		applybt.setBounds(1450, 710, 200, 200);
		
		//TODO 디비가 먼저 들어가게 수정 / 그 뒤에 화면 전환
		//TODO GunMoster 총알 수정 
		
		
//		applybt.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				DBConnection db = new DBConnection();
//				db.insertDB(name.getText() , Integer.toString(score));
//				System.out.println("DB");
//				
//				
//			}
//		});
		
		applybt.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				DBConnection db = new DBConnection();
				db.insertDB(name.getText() , Integer.toString(score));
			}
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
		
		applybt.addMouseListener((MouseListener) o);


		add(applybt);
		
		rankbt = new JButton();
		rankbt.setName("RankingButton");
		rankbt.setText("랭킹");
		rankbt.setBounds(100, 100, 200, 100);
		rankbt.addMouseListener((MouseListener) o);
		add(rankbt);
		
	}

	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		
		g.drawImage(back, 0, 0, this);
		
		g.drawImage(ClearMessage, 800, 180, this);
		g.setFont(new Font("돋음", Font.BOLD, 60)); 
		g.drawString(Integer.toString(score), 1100, 520);
	}
	
	

}