package components;

import java.util.ArrayList;

import javax.imageio.stream.ImageInputStream;
import javax.swing.JPanel;

import panels.GamePanel;


// 총 쏘는 몬스터
public class GunMonster extends MonsterThread{ //기존의 몬스터를 상속
	public static ArrayList<Shot> GunShotList = new ArrayList<Shot>();
	private GamePanel mainPanel;
	private String Image;

	public GunMonster(int x, int y, int hp, String Image, Player player) {
		super(x, y, hp, Image, player);
		this.Image = Image;
		shot();
		
	}

	public ArrayList<Shot> getShotList() {
		return GunShotList;
	}

	public void setShotList(ArrayList<Shot> GunShotList) {
		this.GunShotList = GunShotList;
	}

	@Override
	public void run() {
		super.run();
		
	}
	
	public void shot() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while(isStatus() == true) {
					GunShotList.add(new Shot(getX()+50, getY()+15, 2));
					try {
						
						Thread.sleep(2000); //2초에 한번 쏘도록 설정
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
			}
			
		}).start();
	}	
	
}
