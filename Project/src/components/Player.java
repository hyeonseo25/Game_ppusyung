package components;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
	private int status; // ĳ���Ͱ� �ٶ󺸴� ���� : 1=������, 2=����
	private int invincibility = 255;
	private int score=0;
	private Image image;
	public static ArrayList<Shot> shots = new ArrayList<Shot>();
	private int cnt = 0;
	private boolean fall = false;
	private boolean jump = false;
	Monster monster;
	
	ImageIcon backImg = new ImageIcon("images/�����гι��.png");
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
		if(invincibility==80) {
			
		}else {
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
			
		}
		
		
	}
	public void fall() {
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {

					int foot = getY() + image.getHeight(null); // ĳ���� �� ��ġ �罺ĵ

					// �߹ٴ��� ���Ǻ��� ���� ������ �۵�
					if (foot < field // ���߿� ������
							&& !isJump() // ���� ���� �ƴϸ�
							&& !isFall()) { // �������� ���� �ƴ� ��

						setFall(true); // �������� ������ ��ȯ


						long t1 = Util.getTime(); // ����ð��� �����´�
						long t2;
						int set = 1; // ó�� ���Ϸ� (0~10) ���� �׽�Ʈ�غ���

						while (foot < field) { // ���� ���ǿ� ��� ������ �ݺ�

							t2 = Util.getTime() - t1; // ���� �ð����� t1�� ����

							int fallY = set + (int) ((t2) / 40); // ���Ϸ��� �ø���.

							foot = getY() + image.getHeight(null); // ĳ���� �� ��ġ �罺ĵ

							if (foot + fallY >= field) { // �߹ٴ�+���Ϸ� ��ġ�� ���Ǻ��� ���ٸ� ���Ϸ��� �����Ѵ�.
								fallY = field - foot;
							}

							setY(getY() + fallY); // Y��ǥ�� ���Ϸ��� ���Ѵ�

							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}
						setFall(false);

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
				
				int foot = getY() + image.getHeight(null); // ĳ���� �� ��ġ �罺ĵ
				
				if(fall == false && jump == false && foot==field) {
					setJump(true); // ���������� ����
					long t1 = Util.getTime(); // ����ð��� �����´�
					long t2;
					int set = 12; // ���� ��� ����(0~20) ������ �ٲ㺸��
					int jumpY = 1; // 1�̻����θ� �����ϸ� �ȴ�.(while�� ���� ����)

					while (jumpY >= 0) { // ��� ���̰� 0�϶����� �ݺ�

						t2 = Util.getTime() - t1; // ���� �ð����� t1�� ����

						jumpY = set - (int) ((t2) / 40); // jumpY �� �����Ѵ�.

						setY(getY() - jumpY); // Y���� �����Ѵ�.

						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				jump = false;

			}
		}).start();
	}
	public Player(JPanel main){
		this.mainPanel = main;
		setX(200);
		setY(600);
		setScore(0);
		setInvincibility(255);
		setStatus(1);
		setImage(new ImageIcon("images/Player/Player1.png").getImage());
	}
}
