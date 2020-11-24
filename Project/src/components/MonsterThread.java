package components;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

import util.Util;

public class MonsterThread extends Thread{
	private int x = 0; 
	private int y = 0;
	private int hp;
	private Image images;
	private boolean status = false; // 생존여부
	private boolean fall = false;
	private boolean jump = false;
	protected boolean flag = false;
	private int field = 400;
	
	protected Player player;
	
	public MonsterThread(int x, int y, int hp, String Image, Player player) {
		setX(x);
		setY(y);
		setHp(hp);
		setStatus(true);
		setImage(Image);
		if(!Image.equals("images/monsters/날개괴물.gif")) {
			fall();
		}
		this.player = player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void setImage(String Image) {
		Image monsterIcon1 = new ImageIcon(Image).getImage();
		this.images = monsterIcon1;
	}
	
	public Image getImage() {
		return images;
	}
	
	public void setX(int x) {
		this.x = x;
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
	
	public int getField() {
		return field;
	}
	
	public void setField(int field) {
		this.field = field;
	}
	
	public void setHp(int hp) {
		this.hp = hp;
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
	
	public void reduceHp(int hp) {
		this.hp += hp;
	}
	
	private void m_move() {
		x-=8;	
	}
	
	public void m_move(int x) {
		flag=true;
		this.x-=x;
		flag=false;
	}
    
    public void run() {
    	while(true) {
			if(flag==false) {
				m_move();
			}
			try {
				m_remove();
			}catch (NullPointerException e) {
				e.printStackTrace();
			}
			if(isStatus()==true) {
				m_hit();
			}
			if(isStatus()==false)
				break;
			try {
				Thread.sleep(30);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}	
    }
    
    //player와 monster의 충돌 
    public void m_hit() {
    	try {
    		// 충돌 했을 때
    		if((player.getY() < getY() + getImage().getHeight(null) && getY() < player.getY() + player.getImage().getHeight(null)) 
					&& (player.getX() < getX() + getImage().getWidth(null) && getX() < player.getX() + player.getImage().getWidth(null))) {
    			if(player.getInvincibility()==255) { 
        			player.damaged(200);
        		}
    		}
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    }
    
    
    // 총알과 player의 충돌
    public void m_remove() {
    	try {
			for(int i = 0; i < player.getShots().size(); i++) {
				Shot shot = player.getShots().get(i);
				int head = getY() - getImage().getHeight(null);
				int foot = getY() + getImage().getHeight(null);
				int shotD = shot.getShot_direction();
				
				//총알이 오른쪽으로 날아갈때 
				if(foot >= shot.getY() && head <= shot.getY() && shotD == 0 && getX() <= shot.getX() && getX() >= player.getX()) {
					player.getShots().remove(i); //맞은 총알 삭제
					reduceHp(-50); 
				}

				//총알이 왼쪽으로 날아갈 때 
				else if(foot >= shot.getY() && head <= shot.getY() && shotD ==180 && getX() + 100>= shot.getX() && getX() <= player.getX()) {
					player.getShots().remove(i);
					reduceHp(-50);
				}
				if(getHp() <= 0) { 
					if(isStatus()==true) {
						player.setScore(player.getScore()+200);
						Sound("music/monsterDieSound.wav", false);
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
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
    }

    //필드로 계속 떨어지도록 하는 스레드 
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
						} // end of while
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
	
	public void Sound(String file, boolean Loop){
		//사운드재생용메소드
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
}