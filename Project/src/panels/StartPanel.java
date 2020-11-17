package panels;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;

public class StartPanel extends JPanel{
	private JButton startbt;
	private JButton exitbt;
	private JButton rankbt;
	
	private ImageIcon backImg = new ImageIcon("images/시작패널배경.png");
	private Image back = backImg.getImage();
	
	private Clip backgroundMusic;
	
	Dimension view = Toolkit.getDefaultToolkit().getScreenSize();
	public StartPanel(Object o) {
		Image startButton = new ImageIcon("images/button/StartButton.png").getImage();
		Image rankButton = new ImageIcon("images/button/RankButton.png").getImage();
		Image exitButton = new ImageIcon("images/button/ExitButton.png").getImage();
		
		exitbt = new JButton(new ImageIcon("images/button/ExitButton.png"));
		exitbt.setName("ExitButton");
		exitbt.setBorderPainted(false);
		exitbt.setFocusPainted(false);
		exitbt.setContentAreaFilled(false);
		exitbt.setBounds(20, 20, exitButton.getWidth(null), exitButton.getHeight(null));		
		exitbt.addMouseListener((MouseListener) o);
		add(exitbt);
		
		startbt = new JButton(new ImageIcon("images/button/StartButton.png"));
		startbt.setName("StartButton");
		startbt.setBorderPainted(false);
		startbt.setFocusPainted(false);
		startbt.setContentAreaFilled(false);
		startbt.setBounds((view.width/2 - startButton.getWidth(null)/2), 800, startButton.getWidth(null), startButton.getHeight(null));		
		startbt.addMouseListener((MouseListener) o);
		add(startbt);
		
		rankbt = new JButton(new ImageIcon("images/button/RankButton.png"));
		rankbt.setName("RankingButton");
		rankbt.setBorderPainted(false);
		rankbt.setFocusPainted(false);
		rankbt.setContentAreaFilled(false);
		rankbt.setBounds(1750, 20, rankButton.getWidth(null), rankButton.getHeight(null));
		rankbt.addMouseListener((MouseListener) o);
		add(rankbt);
		
		playMusic();
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(back, 0, 0, this);
	}
	public void playMusic() {
		 try {
			 File file = new File("music/StartMusic.wav");
			 AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			 backgroundMusic = AudioSystem.getClip();
			 backgroundMusic.open(stream);
			 backgroundMusic.start();
			 backgroundMusic.loop(-1);
	     } catch(Exception e) {
	    	 e.printStackTrace();
	     }	
	}
	public void closeMusic() {
		backgroundMusic.close();
	}
}