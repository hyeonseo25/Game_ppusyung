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
		replaybt = new JButton();
		replaybt.setName("ReplayButton");
		replaybt.setText("다시하기");
		replaybt.setBounds(10, 800, 200, 100);
		replaybt.addMouseListener((MouseListener) o);
		add(replaybt);
		
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
			     name.setText("이름을 입력해주세요");			
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
		applybt.setName("applyButton");
		applybt.setBorderPainted(false);
		applybt.setFocusPainted(false);
		applybt.setContentAreaFilled(false);
		applybt.setBounds(1450, 710, 200, 200);
		applybt.addMouseListener((MouseListener) o);
		
		
		applybt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DBConnection db = new DBConnection();
				db.insertDB(name.getText() , "23");
				
			}
		});
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
		
		g.drawImage(ClearMessage, 800, 120, this);
		g.setFont(new Font("돋음", Font.BOLD, 60)); 
		g.drawString(Integer.toString(score), 1100, 520);
	}
	
	

}