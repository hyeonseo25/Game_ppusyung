package components;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import static panels.GamePanel.field;
import util.Util;

public class Player {
	private JPanel mainPanel;
	private int x;
	private int y;
	private int distance = 200;
	private int hp=1000;
	private int status; // 캐릭터가 바라보는 방향 : 1=오른쪽, 2=왼쪽
	private int invincibility = 255;
	private int score=0;
	private Image image;
	public static ArrayList<Shot> shots = new ArrayList<Shot>();
	private int cnt = 0;
	private boolean fall = false;
	private boolean jump = false;
	private int countJump = 0;
	Monster monster;
	
	ImageIcon backImg = new ImageIcon("images/게임패널배경.png");
	Image back = backImg.getImage();
	Dimension view = Toolkit.getDefaultToolkit().getScreenSize();

	
	int a[] = new int[5];
	private Image images[] = {new ImageIcon("images/Player/Player1.png").getImage()
			,new ImageIcon("images/Player/Player1.png").getImage()
			,new ImageIcon("images/Player/Player1.png").getImage()
			,new ImageIcon("images/Player/Player2.png").getImage()
			,new ImageIcon("images/Player/Player2.png").getImage()
			,new ImageIcon("images/Player/Player2.png").getImage()
			,new ImageIcon("images/Player/Player3.png").getImage()
			,new ImageIcon("images/Player/Player3.png").getImage()
			,new ImageIcon("images/Player/Player3.png").getImage()
			,new ImageIcon("images/Player/Player4.png").getImage()
			,new ImageIcon("images/Player/Player4.png").getImage()
			,new ImageIcon("images/Player/Player4.png").getImage()};
	private Image imagesLeft[] = {new ImageIcon("images/Player/PlayerLeft1.png").getImage()
			,new ImageIcon("images/Player/PlayerLeft1.png").getImage()
			,new ImageIcon("images/Player/PlayerLeft1.png").getImage()
			,new ImageIcon("images/Player/PlayerLeft2.png").getImage()
			,new ImageIcon("images/Player/PlayerLeft2.png").getImage()
			,new ImageIcon("images/Player/PlayerLeft2.png").getImage()
			,new ImageIcon("images/Player/PlayerLeft3.png").getImage()
			,new ImageIcon("images/Player/PlayerLeft3.png").getImage()
			,new ImageIcon("images/Player/PlayerLeft3.png").getImage()
			,new ImageIcon("images/Player/PlayerLeft4.png").getImage()
			,new ImageIcon("images/Player/PlayerLeft4.png").getImage()
			,new ImageIcon("images/Player/PlayerLeft4.png").getImage()};
	
	public boolean isFall() {
		return fall;
	}
	public void setFall(boolean fall) {
		this.fall = fall;
	}
	public boolean isJump() {
		return jump;
	}
	public void setJump(boolean jump) {
		this.jump = jump;
	}
	public int getCountJump() {
		return countJump;
	}
	public void setCountJump(int countJump) {
		this.countJump = countJump;
	}
	public ArrayList<Shot> getShots() {
		return shots;
	}
	public void setShots(ArrayList<Shot> shots) {
		this.shots = shots;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public int getInvincibility() {
		return invincibility;
	}
	public void setInvincibility(int invincibility) {
		this.invincibility = invincibility;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image imageIcon) {
		this.image = imageIcon;
	}
	public void p_moveLeft() {
		setStatus(2);
		if(cnt==imagesLeft.length) {
			cnt=0;
		}
		setImage(imagesLeft[cnt]);
		cnt++;
		if(x>0) {
			x-=15;
			distance-=15;
		}else {
			x=0;
		}
	}
	public void p_moveRight() {
		setStatus(1);
		if(cnt==images.length) {
			cnt=0;
		}
		setImage(images[cnt]);
		cnt++;
		if(distance<back.getWidth(null)-130) {
			x+=15;
			distance+=15;
		}else {
			
		}
		
	}
	public void p_moveRight(int num) {
		setStatus(1);
		if(cnt==images.length) {
			cnt=0;
		}
		setImage(images[cnt]);
		cnt++;
		distance+=10;
	}
	public void stop() {
		cnt=0;
		if (status==1) {
			setImage(images[cnt]);
		}else if (status ==2) {
			setImage(imagesLeft[cnt]);
		}
		
	}
	public void p_hit() {
		shots.add(new Shot(mainPanel, x+50, y+15, status));

	}
	public void damaged(int damage) throws InterruptedException {
		if(invincibility==255) {
			Sound("music/ouch.wav", false);
			invincibility=80;
			this.hp -= damage;
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(500);
						invincibility=254;
						Thread.sleep(500);
						invincibility=80;
						Thread.sleep(500);
						invincibility=254;
						Thread.sleep(500);
						invincibility=80;
						Thread.sleep(500);
						invincibility=255;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();

		}else {
						
		}
		
		
	}
	public void fall() {
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {

					int foot = getY() + image.getHeight(null); // 캐릭터 발 위치 재스캔

					// 발바닥이 발판보다 위에 있으면 작동
					if (foot < field // 공중에 있으며
							&& !isJump() // 점프 중이 아니며
							&& !isFall()) { // 떨어지는 중이 아닐 때

						setFall(true); // 떨어지는 중으로 전환

						long t1 = Util.getTime(); // 현재시간을 가져온다
						long t2;
						int set = 1; // 처음 낙하량 (0~10) 까지 테스트해보자

						while (foot < field) { // 발이 발판에 닿기 전까지 반복

							t2 = Util.getTime() - t1; // 지금 시간에서 t1을 뺀다

							int fallY = set + (int) ((t2) / 40); // 낙하량을 늘린다.

							foot = getY() + image.getHeight(null); // 캐릭터 발 위치 재스캔

							if (foot + fallY >= field) { // 발바닥+낙하량 위치가 발판보다 낮다면 낙하량을 조정한다.
								fallY = field - foot;
							}

							setY(getY() + fallY); // Y좌표에 낙하량을 더한다

							if (isJump()) { // 떨어지다가 점프를 하면 낙하중지
								break;
							}

							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}
						setFall(false);

						if (!isJump()) { // 발이 땅에 닿고 점프 중이 아닐 때 더블점프 카운트를 0으로 변경
							setCountJump(0);
						}
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	public void jump() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				setCountJump(getCountJump() + 1); // 점프 횟수 증가

				int nowJump = getCountJump(); // 이번점프가 점프인지 더블점프인지 저장

				setJump(true); // 점프중으로 변경

				long t1 = Util.getTime(); // 현재시간을 가져온다
				long t2;
				int set = 10; // 점프 계수 설정(0~20) 등으로 바꿔보자
				int jumpY = 1; // 1이상으로만 설정하면 된다.(while문 조건 때문)

				while (jumpY >= 0) { // 상승 높이가 0일때까지 반복

					t2 = Util.getTime() - t1; // 지금 시간에서 t1을 뺀다

					jumpY = set - (int) ((t2) / 40); // jumpY 를 세팅한다.

					setY(getY() - jumpY); // Y값을 변경한다.

					if (nowJump != getCountJump()) { // 점프가 한번 더되면 첫번째 점프는 멈춘다.
						break;
					}

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				if (nowJump == getCountJump()) { // 점프가 진짜 끝났을 때를 확인
					setJump(false); // 점프상태를 false로 변경
				}

			}
		}).start();
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
	public Player(JPanel main){
		this.mainPanel = main;
		setX(200);
		setY(600);
		setScore(0);
		setInvincibility(255);
		setStatus(1);
		shots.clear();
		setImage(new ImageIcon("images/Player/Player1.png").getImage());
	}
}
