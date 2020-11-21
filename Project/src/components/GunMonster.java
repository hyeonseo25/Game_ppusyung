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
		//gm_hit();
		
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
					try {
						Thread.sleep(1000); //2초에 한번 쏘도록 설정
						GunShotList.add(new Shot(getX()+50, getY()+15, 2));
						//gm_hit();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
			
		}).start();
	}	
	
	public void gm_hit() {
		//총알이 하나일때 계속 실행되어서 
		new Thread(new Runnable() {

			@Override
			public void run() {
				
				System.out.println("gm_hit");
				while(isStatus() == true) {
			
		
				for(int i = 0; i < GunMonster.GunShotList.size(); i++) {
					Shot shot = GunMonster.GunShotList.get(i);
					//System.out.println(i);
		
		//			int head = player.getY() - player.getImage().getHeight(null);
		//		
		//			int foot = player.getY() + player.getImage().getHeight(null);
		
					int shotD = shot.getShot_direction();
					
		//			//총알이 왼쪽으로 날아갈 때 
		//			if(player.getY >= shot.getY() && head <= shot.getY() && shotD ==180 && player.getX() >= shot.getX()) {
		//				if(player.getInvincibility()==255) {
		//					GunMonster.GunShotList.remove(i);
		//        			player.damaged(200);
		//        			System.out.println("player.damaged " + Integer.toString(i));
		//        		}
		//					
		//			}
					
					if(player.getX() + player.getImage().getWidth(null) > shot.getX() && player.getX() - player.getImage().getWidth(null) < shot.getX()
							&& player.getY() + player.getImage().getHeight(null) > shot.getY() && player.getY() - player.getImage().getHeight(null) < shot.getY()) {
		    			try {
							GunMonster.GunShotList.remove(i);
							player.damaged(200);
			      			System.out.println("player.damaged " + shot.toString());

						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
				}
			}
		}).start();
	}
}
		