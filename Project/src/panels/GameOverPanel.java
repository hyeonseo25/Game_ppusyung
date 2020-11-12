package panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOverPanel extends JPanel{
	private JButton replaybt;
	private JButton rankbt;
	
	private ImageIcon backImg = new ImageIcon("images/게임오버패널배경.png");
	private Image back = backImg.getImage();
	
	Image replaybtn = new ImageIcon("images/button/restartBtn.png").getImage();
	
	Dimension view = Toolkit.getDefaultToolkit().getScreenSize();

	public GameOverPanel(Object o) {
		
		replaybt = new JButton(new ImageIcon("images/button/restartBtn.png"));
		replaybt.setName("ReplayButton");
		replaybt.setBorderPainted(false);
		replaybt.setFocusPainted(false);
		replaybt.setContentAreaFilled(false);
		replaybt.setBounds((view.width/2 - replaybtn.getWidth(null)/2), 700, replaybtn.getWidth(null), replaybtn.getHeight(null));	
		replaybt.addMouseListener((MouseListener) o);
		add(replaybt);
		
		
	}

	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(back, 0, 0, this);
	}
	
	
}