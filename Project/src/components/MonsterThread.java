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
	private boolean status = false; // 생존여부
	private boolean fall = false;
	private boolean jump = false;
	private boolean flag = false;
	
	Player player;
	Monster monster;
	

	public MonsterThread(int x, int y, int hp, String Image, Player player) {
		setX(x);
		setY(y);
		setHp(hp);
		setStatus(true);
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
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
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
    	player.damaged(200);
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

					for(int i = 0; i < Player.shots.size(); i++) {
						Shot shot = Player.shots.get(i);

						int head = getY() - getImage().getHeight(null);
					
						int foot = getY() + getImage().getHeight(null);
	
						int shotD = shot.getShot_direction();
						
						//총알이 오른쪽으로 날아갈때 
						if(foot >= shot.getY() && head <= shot.getY() && shotD == 0 && getX() <= shot.getX() && getX() >= player.getX()) {
								Player.shots.remove(i); //맞은 총알 삭제

								setHp(-50); 
						}

						//총알이 왼쪽으로 날아갈 때 
						else if(foot >= shot.getY() && head <= shot.getY() && shotD ==180 && getX() + 100>= shot.getX() && getX() <= player.getX()) {
								Player.shots.remove(i);
								setHp(-50); 
								
							}
						if(getHp() <= 0 || getX() <= 0) { 
							if(isStatus()==true) {
								player.setScore(player.getScore()+200);
							}
							setStatus(false);
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
					int foot = getY() + getImage().getHeight(null); // 캐릭터 발 위치 재스캔

					// 발바닥이 발판보다 위에 있으면 작동
					if (foot < field // 공중에 있으며
							&& !isJump() // 점프 중이 아니며
							&& !isFall()) { // 떨어지는 중이 아닐 때

						setFall(true); 

						long t1 = Util.getTime(); // 현재시간을 가져온다
						long t2;
						int set = 1; // 처음 낙하량 (0~10) 까지 테스트해보자

						while (foot < field) { // 발이 발판에 닿기 전까지 반복

							t2 = Util.getTime() - t1; // 지금 시간에서 t1을 뺀다

							int fallY = set + (int) ((t2) / 40); // 낙하량을 늘린다.

							foot = getY() + getImage().getHeight(null); // 캐릭터 발 위치 재스캔

							
							if (foot + fallY >= field) { // 발바닥+낙하량 위치가 발판보다 낮다면 낙하량을 조정한다.
								fallY = field - foot;
							}

							setY(fallY); // Y좌표에 낙하량을 더한다

							
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

