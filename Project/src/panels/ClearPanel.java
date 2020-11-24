package panels;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.DBConnection;

public class ClearPanel extends JPanel{

	private JTextField name;
	private JButton applybt;
	private JLabel scoreLabel;
	private String scoreText;
	private int score; // 플레이어 점수
	
	private ImageIcon ClearMessageImg = new ImageIcon("images/게임클리어.gif");
	private Image ClearMessage = ClearMessageImg.getImage();
	
	private ImageIcon backImg = new ImageIcon("images/클리어패널배경.png");
	private Image back = backImg.getImage();
	
	public String getName() {
		return name.getText();
	}
	public void setName(String n) {
		this.name.setText(n);
	}

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
		applybt.setName("ReplayButton3");
		applybt.setBorderPainted(false);
		applybt.setFocusPainted(false);
		applybt.setContentAreaFilled(false);
		applybt.setBounds(1450, 710, 200, 200);
		
		applybt.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(!(getName().equals("")||getName().equals("이름을 입력해주세요"))) {
					DBConnection db = new DBConnection();
					db.insertDB(name.getText() , Integer.toString(score));	
				}
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
	}

	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub	
		super.paintComponent(g);
		
		
		g.drawImage(back, 0, 0, this); // 뒷배경
		
		g.drawImage(ClearMessage, 800, 180, this); //게임 클리어 gif
		g.setFont(new Font("돋음", Font.BOLD, 60)); 
		g.drawString(Integer.toString(score), 1100, 520); //점수
	}
	
	

}