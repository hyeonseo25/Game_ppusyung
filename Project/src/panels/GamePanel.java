package panels;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import components.GunMonster;
import components.Monster;
import components.Player;
import components.Shot;
import main.Main;
import util.DBConnection;

public class GamePanel extends JPanel{
	
	boolean keyLeft = false;
	boolean keyRight = false;
	boolean keyEnter = false;
	boolean keySpace = false;
	boolean check=false;
	int cnt=5;
	
	Clip backgroundMusic;
	
	private ImageIcon backImg = new ImageIcon("images/�����гι��.png");
	private Image back = backImg.getImage();
	
	Dimension view = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static final int field = 900;
	
	private int backX=0;
	private int backX2 = back.getWidth(null);
	private String endTime; //���� Ŭ���� �ð�

	
	private int end = back.getWidth(null)-(view.width-1600);
	
	Player player;
	Monster monster;
	util.Timer time;
	
	// �ٸ� Ŭ���� ������
	JFrame frame;
	CardLayout cl;
	Main main;
	ClearPanel clearPanel;
	
	public String getTime() {
		if (Integer.valueOf(time.getSeconds()) <0 ) {
			main.getCl().show(frame.getContentPane(), "gameover");
		}
		return time.getSeconds() + "��";
	}
	public String getScore() {
		return Integer.toString(player.getScore()) + "��";
	}
	
	public String getScore1() {
		return Integer.toString(player.getScore());
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEndTime() {
		return this.endTime;
	}
	public void setBackX(int backX) {
		this.backX = backX;
	}
	
	public int getBackX() {
		return backX;
	}
	
	public int getBackX2() {
		return backX2;
	}

	public void setBackX2(int backx2) {
		backX2 = backx2;
	}
	
	public GamePanel(Object o, JFrame frame, CardLayout cl) {
		this.frame = frame;
		this.cl = cl;
		this.main = (Main)o;
		test(); // ��ġ�� ����
		playGame();
	}
	public GamePanel() {
		
	}
	public void gameStart() {
		time = new util.Timer();
		time.start();
		player = new Player(this);

		player.fall();
		
		monster = new Monster(this, player);
		//monster.createMonsters(monster.getMonsterList());//������ ������ Monster ��ü���� �迭�� �߰�
		monster.createMonsters();//������ ������ Monster ��ü���� �迭�� �߰�
		playMusic();
	}
	// ��ġ�� ����
	private void test() {
		JButton gameoverbt;
		JButton clearbt;
		
		gameoverbt = new JButton();
		gameoverbt.setName("GameoverButton");
		gameoverbt.setText("���ӿ���");
		gameoverbt.setBounds(1690, 10, 100, 50);
		gameoverbt.addMouseListener((MouseListener) main);
		add(gameoverbt);
		
		clearbt = new JButton();
		clearbt.setName("ClearButton");
		clearbt.setText("Ŭ����");
		clearbt.setBounds(1800, 10, 100, 50);
		clearbt.addMouseListener((MouseListener) main);
		add(clearbt);
	}
	private void playGame() {
		
		setFocusable(true);
		repaintThread();
		initListener();
	}
	
	private void playMusic() {
		 try {
			 File file = new File("music/backgroundMusic.wav");
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
	public void Sound(String file, boolean Loop){
		//���������޼ҵ�
		//���� Ŭ������ �߰��� �޼ҵ带 �ϳ� �� ��������ϴ�.
		//�����������޾Ƶ鿩�ش���带�����Ų��.
		Clip clip;
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
			if (Loop) clip.loop(-1);
			//Loop ����true�� ������������ѹݺ���ŵ�ϴ�.
			//false�� �ѹ��������ŵ�ϴ�.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// ������ �߰� �޼���
	private void initListener() {
		addKeyListener(new KeyAdapter() { // Ű ������ �߰�
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				switch(keyCode) {
				case KeyEvent.VK_A: keyLeft = true; break;
				case KeyEvent.VK_D: keyRight = true; break;
				case KeyEvent.VK_ENTER: keyEnter = true; break;
				case KeyEvent.VK_SPACE: keySpace = true;break;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_A: keyLeft = false; player.stop();break;
				case KeyEvent.VK_D: keyRight = false;  player.stop();break;
				case KeyEvent.VK_ENTER: keyEnter = false; break;
				case KeyEvent.VK_SPACE: keySpace = false; check=false; break;
				}
			}
		});
	}
	public void keyCheck() throws InterruptedException {
		if(keyLeft==true) {
			player.p_moveLeft();
			
		}else if(keyRight==true) {
			if(player.getDistance()>end) {
				closeMusic();
				keySpace = false;
				Sound("music/clearMusic.wav", false);
				TimeUnit.SECONDS.sleep(3);
				cl.show(frame.getContentPane(), "clear");
				frame.requestFocus();
				setEndTime(getTime()); //���� Ŭ���� �ð�
			}else if(player.getDistance()>back.getWidth(null)-(view.width-900)) {
				player.p_moveRight();
			}else if(player.getX()>900) {  //�÷��̾ �߰��� ������
				player.p_moveRight(1);//�Ű������� �����ε��� �޼��带 ���� ��Ű�� ����. �� �� �ǹ� ����
				movebg();
			}else {
				player.p_moveRight();
			}
		}
		if(keyEnter==true) {
			if(cnt==5) {
				Sound("music/Gunshot.wav", false);//�ѽ�� �Ҹ�
				player.p_hit();
				cnt=0;
			}
		}
		if(keySpace==true) {
			if (player.isJump() == false && player.isFall() == false && player.getY() + player.getImage().getHeight(null)==field) {
				player.jump();
				Sound("music/jumpMusic.wav", false);
			}
		}
	}
	//���Ͱ� ������
	public void repaintThread() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					
					
					repaint();
					
					try {
						keyCheck();
						if(cnt<5) {
							cnt++; // �Ѿ˿� ������
						}
						Thread.sleep(40);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
	
			}
			
		}).start();
	}
	//�гο� �׸���
		@Override
		public void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			g.drawImage(back, backX, 0, this);
			g.drawImage(back, backX2, 0, this);
			ArrayList<Shot> list = player.getShots();
			ArrayList<Shot> GunMonster_shotlist = GunMonster.shotList;
			//monsterList�� �ִ� monster ��ü���� �׸�
			for (int i = 0; i < monster.getMonsterList().size(); i++) {
				g.drawImage(monster.getMonsterList().get(i).getImage(), monster.getMonsterList().get(i).getX(), monster.getMonsterList().get(i).getY(), this);
			}
			for(int i=0; i<list.size();i++) {
				g.drawImage(list.get(i).getImage(), list.get(i).getX(), list.get(i).getY(), this);
			}
			
			for(int i=0; i<GunMonster_shotlist.size();i++) {
				g.drawImage(GunMonster_shotlist.get(i).getImage(), GunMonster_shotlist.get(i).getX(), GunMonster_shotlist.get(i).getY(), this);
			}
			g.drawImage(player.getImage(), player.getX(), player.getY(), this);
			g.setFont(new Font("����ü", Font.BOLD, 40));  //Ÿ�̸� �۾�ü
			g.drawString(getTime(), 900, 50); // Ÿ�̸� �׸���
			g.drawString(getScore(), 1500, 50); // Ÿ�̸� �׸���

		}
		//�г� ���� ������
	public void movebg() {
		backX -=10; 
		// backX2 -=10;
		for (int i = 0; i < monster.getMonsterList().size(); i++) {
			monster.getMonsterList().get(i).m_move(20);	
		}
		// �̹����� ȭ�� ������ ������ x�� ��ǥ�� ���� ���� ���̷� ��ȯ
//		if (backX < -(back.getWidth(null))) {
//			backX = back.getWidth(null)+5;
//		}
//		if(backX2 < -(back.getWidth(null))) {
//			backX2 = back.getWidth(null)+5;
//		}
	}
}