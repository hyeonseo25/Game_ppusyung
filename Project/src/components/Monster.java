package components;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Monster {
	private Player player;
	private int monstercnt=0;
	private Dimension view = Toolkit.getDefaultToolkit().getScreenSize();
	private ArrayList<MonsterThread> monsterList = new ArrayList<>(); // Monster 객체를 담는 ArrayList
	
	MonsterThread[] monster = { // 스폰 될 몬스터들의 정보를 담은 배열
    		new MonsterThread(2250, 500, 300, "images/monsters/노란색슬라임괴물.gif", player),
    		new MonsterThread(2500, 450, 100, "images/monsters/슬라임괴물.gif", player),
    		new MonsterThread(3350, 400, 400, "images/monsters/날개괴물.gif", player),
    		new MonsterThread(4000, 450, 100, "images/monsters/물걸레괴물.gif", player),
    		new MonsterThread(4190, 450, 100, "images/monsters/책괴물.gif", player),
    		new GunMonster(4520, 500, 100, "images/monsters/물걸레괴물.gif", player),
    		new MonsterThread(5570, 450, 100, "images/monsters/지렁이괴물.gif", player),
    		new MonsterThread(5990, 400, 100, "images/monsters/날개괴물.gif", player),
    		new MonsterThread(6080, 450, 100, "images/monsters/지렁이괴물.gif", player),
    		new MonsterThread(6560, 450, 100, "images/monsters/슬라임괴물.gif", player),
    		new MonsterThread(8210, 400, 400, "images/monsters/날개괴물.gif", player),
    		new MonsterThread(8240, 450, 300, "images/monsters/노란색슬라임괴물.gif", player),
    		new MonsterThread(9460, 300, 1000, "images/monsters/슬라임괴물보스.gif", player),
    		new MonsterThread(10520, 450, 100, "images/monsters/책괴물.gif", player),
    		new MonsterThread(10760, 450, 100, "images/monsters/지렁이괴물.gif", player),
    		new MonsterThread(11130, 450, 100, "images/monsters/물걸레괴물.gif", player),
    		new GunMonster(11430, 400, 400, "images/monsters/날개괴물.gif", player)
    		};
	
	public int getMonsterCnt() {
		return monstercnt;
	}
	
	public Monster(Player player) {
		this.player = player;
	}

	public ArrayList<MonsterThread> getMonsterList() {
		return monsterList;
	}
	
    public void createMonsters() { //메소드 호출 시 Monster 객체가 배열에 추가
    	monsterList.clear();
		monsterList.add(new MonsterThread(1200, 450, 100, "images/monsters/지렁이괴물.gif", player));
		monsterList.add(new MonsterThread(1600, 450, 130, "images/monsters/물걸레괴물.gif", player));
        for (int i = 0; i < monsterList.size(); i++) {
        	monsterList.get(i).start();
        }
    }
    
    public void addMonster() {
    	monster[monstercnt].setPlayer(player); // 플레이어 객체 정보 전달
    	monster[monstercnt].setX((int) view.getWidth()+50); // 화면 끝에서 생성
    	monsterList.add(monster[monstercnt]);
    	monsterList.get(monsterList.size()-1).start(); // 가장 최근에 리스트의 추가된 몬스터 객체 스레드 실행
    	if(monstercnt< monster.length-1) {
    		monstercnt++;
    	}
    }
}