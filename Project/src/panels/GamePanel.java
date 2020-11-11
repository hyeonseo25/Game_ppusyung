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
	
	private ImageIcon backImg = new ImageIcon("images/게임패널배경.png");
	private Image back = backImg.getImage();
	
	Dimension view = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static final int field = 900;
	
	private int backX=0;
	private int backX2 = back.getWidth(null);
	private String endTime; //게임 클리어 시간

	
	private int end = back.getWidth(null)-(view.width-1600);
	
	Player player;
	Monster monster;
	util.Timer time;
	
	// 다른 클래스 변수들
	JFrame frame;
	CardLayout cl;
	Main main;
	ClearPanel clearPanel;
	
	public String getTime() {
		if (Integer.valueOf(time.getSeconds()) <0 ) {
			main.getCl().show(frame.getContentPane(), "gameover");
		}
		return time.getSeconds() + "초";
	}
	public String getScore() {
		return Integer.toString(player.getScore()) + "점";
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
		test(); // 고치면 삭제
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
		//monster.createMonsters(monster.getMonsterList());//프레임 생성시 Monster 객체들을 배열에 추가
		monster.createMonsters();//프레임 생성시 Monster 객체들을 배열에 추가
		playMusic();
	}
	// 고치면 삭제
	private void test() {
		JButton gameoverbt;
		JButton clearbt;
		
		gameoverbt = new JButton();
		gameoverbt.setName("GameoverButton");
		gameoverbt.setText("게임오버");
		gameoverbt.setBounds(1690, 10, 100, 50);
		gameoverbt.addMouseListener((MouseListener) main);
		add(gameoverbt);
		
		clearbt = new JButton();
		clearbt.setName("ClearButton");
		clearbt.setText("클리어");
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
		//사운드재생용메소드
		//메인 클래스에 추가로 메소드를 하나 더 만들었습니다.
		//사운드파일을받아들여해당사운드를재생시킨다.
		Clip clip;
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
			if (Loop) clip.loop(-1);
			//Loop 값이true면 사운드재생을무한반복시킵니다.
			//false면 한번만재생시킵니다.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 리스너 추가 메서드
	private void initListener() {
		addKeyListener(new KeyAdapter() { // 키 리스너 추가
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
				setEndTime(getTime()); //게임 클리어 시간
			}else if(player.getDistance()>back.getWidth(null)-(view.width-900)) {
				player.p_moveRight();
			}else if(player.getX()>900) {  //플레이어가 중간을 넘으면
				player.p_moveRight(1);//매개변수는 오버로딩된 메서드를 실행 시키기 위함. 그 외 의미 없음
				movebg();
			}else {
				player.p_moveRight();
			}
		}
		if(keyEnter==true) {
			if(cnt==5) {
				Sound("music/Gunshot.wav", false);//총쏘는 소리
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
	//몬스터가 움직임
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
							cnt++; // 총알에 딜레이
						}
						Thread.sleep(40);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
	
			}
			
		}).start();
	}
	//패널에 그리기
		@Override
		public void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			g.drawImage(back, backX, 0, this);
			g.drawImage(back, backX2, 0, this);
			ArrayList<Shot> list = player.getShots();
			ArrayList<Shot> GunMonster_shotlist = GunMonster.shotList;
			//monsterList에 있는 monster 객체들을 그림
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
			g.setFont(new Font("굴림체", Font.BOLD, 40));  //타이머 글씨체
			g.drawString(getTime(), 900, 50); // 타이머 그리기
			g.drawString(getScore(), 1500, 50); // 타이머 그리기

		}
		//패널 전용 스레드
	public void movebg() {
		backX -=10; 
		// backX2 -=10;
		for (int i = 0; i < monster.getMonsterList().size(); i++) {
			monster.getMonsterList().get(i).m_move(20);	
		}
		// 이미지가 화면 밖으로 나가면 x축 좌표를 사진 가로 길이로 변환
//		if (backX < -(back.getWidth(null))) {
//			backX = back.getWidth(null)+5;
//		}
//		if(backX2 < -(back.getWidth(null))) {
//			backX2 = back.getWidth(null)+5;
//		}
	}
}