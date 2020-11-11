package components;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Shot {
	private JPanel mainPanel;
	private int x;
	private int y;
	private int shot_direction;
	private int speed=50;
	private Image image;
	public Shot(JPanel main, int x, int y, int status) { //status 1:오른쪽으로, 2:왼쪽으로 총알 이동
		this.mainPanel = main;
		this.x = x+20;
		this.y = y+70;
		setShot_direction(status);
		setImage(new ImageIcon("images/총알.png").getImage());
		s_move();
	}
	
	//몬스터 shot
	public Shot(int x, int y, int status) { //status 1:오른쪽으로, 2:왼쪽으로 총알 이동
		this.x = x+20;
		this.y = y+70;
		setShot_direction(status);
		setImage(new ImageIcon("images/총알.png").getImage());
		s_move();
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
	public int getShot_direction() {
		return shot_direction;
	}
	public void setShot_direction(int status) {
		if(status==1) {
			shot_direction=0;
		}else if(status==2) {
			shot_direction=180;
		}
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public void s_move() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					
					try {
						x += Math.cos(Math.toRadians(shot_direction)) * speed;
						y += Math.sin(Math.toRadians(shot_direction)) * speed;
						
						Thread.sleep(50);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
	
			}
			
		}).start();
	}
}