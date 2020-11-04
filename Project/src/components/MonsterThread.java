package components;

import static panels.GamePanel.field;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import util.Util;

public class MonsterThread extends Thread{
	private int x = 0; 
	private int y = 0;
	private double angle = 180;
	private double speed = 4;
	private int hp;
	private Image images;
	private boolean fall = false;
	private boolean jump = false;
	private boolean flag = false;
	
	Player player;
	Monster monster;
	

	public MonsterThread(int x, int y, int hp, String Image, Player player) {
		setX(x);
		setY(y);
		setHp(hp);
		setImage(Image);
		fall();
		this.player = player;

	}
	
	public void setImage(String Image) {
		Image monsterIcon1 = new ImageIcon(Image).getImage();
		this.images = monsterIcon1;
	}
	public Image getImage() {
		return images;
	}
	public void setX(double x) {
		this.x += x;
	}
	public int getX() {
		return x;
	}
	public void setY(double y) {
		this.y += y;
	}
	public int getY() {
		return y;
	}
	public void setHp(int hp) {
		this.hp += hp;
	}
	public int getHp() {
		return hp;
	}

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
	
	void m_move() {
		x-=8;
	}
	public void m_move(int x) {
		flag=true;
		this.x-=x;
		flag=false;
//		setX(Math.cos(Math.toRadians(angle)) * speed);
//		setY(Math.sin(Math.toRadians(angle)) * speed);
	}
	
	

    public void m_hit() {
    	
    }
    
    
    public void run() {
				while(true) {
					if(flag==false) {
						m_remove();
						m_move();
					}
					try {
						Thread.sleep(30);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}	
				
    }
    
    public void m_remove() {
				try {

					//System.out.println("m_remove " + Thread.currentThread().getName() + " " + monsterListIdx);
					for(int i = 0; i < Player.shots.size(); i++) {
						Shot shot = Player.shots.get(i);

						int head = getY() - getImage().getHeight(null);
					
						int foot = getY() + getImage().getHeight(null);
	
						//System.out.println(monsterListIdx + " "+ head + " " + foot);
						int shotD = shot.getShot_direction();
						if(foot >= shot.getY() && head <= shot.getY() && shotD == 0 && getX() <= shot.getX() && getX() >= player.getX()) {
								Player.shots.remove(i); //���� �Ѿ� ����

								setHp(-50); //������ ü���� 50����, �ι� ������ hp�� 0
						}

						else if(foot >= shot.getY() && head <= shot.getY() && shotD ==180 && getX() >= shot.getX() && getX() <= player.getX()) {
								Player.shots.remove(i); //���� �Ѿ� ��
								setHp(-50); //������ ü���� 50����, �ι� ������ hp�� 0
								
							}
						if(getHp() <= 0 || getX() <= 0) { //hp�� 0�� �ǰų� ȭ�� ���� ������ ������
							setImage(null);
							break;
							
						}
						Thread.sleep(30);

						}
				
					} catch(IndexOutOfBoundsException e) {
						e.printStackTrace();
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
			
    }

    	

	
	public void fall() {
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					int foot = getY() + getImage().getHeight(null); // ĳ���� �� ��ġ �罺ĵ

					// �߹ٴ��� ���Ǻ��� ���� ������ �۵�
					if (foot < field // ���߿� ������
							&& !isJump() // ���� ���� �ƴϸ�
							&& !isFall()) { // �������� ���� �ƴ� ��

						setFall(true); 

						long t1 = Util.getTime(); // ����ð��� �����´�
						long t2;
						int set = 1; // ó�� ���Ϸ� (0~10) ���� �׽�Ʈ�غ���

						while (foot < field) { // ���� ���ǿ� ��� ������ �ݺ�

							t2 = Util.getTime() - t1; // ���� �ð����� t1�� ����

							int fallY = set + (int) ((t2) / 40); // ���Ϸ��� �ø���.

							foot = getY() + getImage().getHeight(null); // ĳ���� �� ��ġ �罺ĵ

							
							if (foot + fallY >= field) { // �߹ٴ�+���Ϸ� ��ġ�� ���Ǻ��� ���ٸ� ���Ϸ��� �����Ѵ�.
								fallY = field - foot;
							}

							setY(fallY); // Y��ǥ�� ���Ϸ��� ���Ѵ�

							
							try {
								Thread.sleep(40);
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
	

}

