package components;

import java.util.ArrayList;
import panels.GamePanel;

// 총 쏘는 몬스터
public class GunMonster extends MonsterThread{ //기존의 몬스터를 상속
	public ArrayList<Shot> GunShotList = new ArrayList<Shot>();
	private GamePanel mainPanel;
	private String Image;

	public GunMonster(int x, int y, int hp, String Image, Player player) {
		super(x, y, hp, Image, player);
		this.Image = Image;	
	}

	public ArrayList<Shot> getShotList() {
		return GunShotList;
	}

	public void setShotList(ArrayList<Shot> GunShotList) {
		this.GunShotList = GunShotList;
	}

	@Override
	public void run() {
		shot();
		gm_hit();
		super.run();
	}
	
	public void shot() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(isStatus() == true) {
					try {
						GunShotList.add(new Shot(getX()+50, getY()+15, 2));
						Thread.sleep(1000); //1초에 한번 쏘도록 설정
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				GunShotList.clear();
			}
		}).start();
	}	
	
	public void gm_hit() {
		//총알이 하나일때 계속 실행되어서 
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(isStatus()) {
					try {
						for(int i = 0; i < GunShotList.size(); i++) {
							Shot shot = GunShotList.get(i);
							
							if(player.getX() + player.getImage().getWidth(null) > shot.getX() && player.getX() - player.getImage().getWidth(null) < shot.getX()
									&& player.getY() + player.getImage().getHeight(null) > shot.getY() && player.getY() - player.getImage().getHeight(null) < shot.getY()) {
				    			try {
				    				if(player.getInvincibility()==255) {
				    					GunShotList.remove(i);
				            			player.damaged(200);
				    				}
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}	
						}
					}catch (NullPointerException e) {
						continue;
					}
				}
			}
		}).start();
	}
}