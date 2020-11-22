package components;

import java.awt.Image;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import components.GunMonster;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import util.Util;

public class Monster {
	private JPanel mainPanel;
	Player player;

	private ArrayList<MonsterThread> monsterList = new ArrayList<>(); //Monster 객체를 담는 ArrayList
	private ArrayList<Shot> shotList;
	
	private int monstercnt=0;
	
	public int getMonsterCnt() {
		return monstercnt;
	}
	Monster(){ 
		
	}
	
	
	public Monster(JPanel main, Player player) {
		this.mainPanel = main;
		this.player = player;
	}

	public ArrayList<MonsterThread> getMonsterList() {
		return monsterList;
	}
	
    public void createMonsters() { //메소드 호출 시 Monster 객체가 배열에 추가
    	monsterList.clear();
		monsterList.add(new MonsterThread(1200, 450, 100, "images/monsters/지렁이괴물.gif", player));
	    //monsterList.add(new GunMonster(1600, 400, 130, "images/monsters/물걸레괴물7x.gif", player));
	    monsterList.add(new MonsterThread(1200, 450, 100, "images/monsters/슬라임괴물.gif", player));
	    /*monsterList.add(new MonsterThread(1300, 400, 100, "images/monsters/책괴물.gif", player));	
	    monsterList.add(new MonsterThread(4500, 400, 100, "images/monsters/날개괴물.gif", player));		
	    monsterList.add(new MonsterThread(5600, 400, 100, "images/monsters/슬라임괴물.gif", player));		
	    monsterList.add(new MonsterThread(7500, 400, 100, "images/monsters/책괴물.gif", player));		
	    monsterList.add(new MonsterThread(9600, 400, 100, "images/monsters/노란색슬라임괴물.gif", player));		
	    monsterList.add(new MonsterThread(10000, 400, 100, "images/monsters/물걸레괴물.gif", player));		
	    monsterList.add(new MonsterThread(8000, 400, 100, "images/monsters/지렁이괴물.gif", player));		
	    monsterList.add(new MonsterThread(10600, 400, 100, "images/monsters/노란색슬라임괴물.gif", player));		
	    monsterList.add(new MonsterThread(12000, 400, 100, "images/monsters/물걸레괴물.gif", player));		
	    monsterList.add(new MonsterThread(13000, 400, 100, "images/monsters/지렁이괴물.gif", player));*/
        for (int i = 0; i < monsterList.size(); i++) {
        	monsterList.get(i).start();
        }
    }
    MonsterThread[] monster = {
    		new MonsterThread(2250, 400, 100, "images/monsters/날개괴물.gif", player),
    		new MonsterThread(2500, 400, 100, "images/monsters/슬라임괴물.gif", player),
    		new MonsterThread(4000, 400, 100, "images/monsters/슬라임괴물.gif", player)
    		};
    public void addMonster(int x) {
    	System.out.println("실행");
    	System.out.println(x);
    	monster[monstercnt].setX(x);
    	monsterList.add(monster[monstercnt]);
    	System.out.println(monsterList.size());
    	System.out.println(monsterList.get(monsterList.size()-1).getX()+""+monsterList.get(monsterList.size()-1).getImage());
    	monsterList.get(monsterList.size()-1).start();
    	if(monstercnt< monster.length-1) {
    		monstercnt++;
    	}
    }
}
