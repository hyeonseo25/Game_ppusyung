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
		monsterList.add(new MonsterThread(1200, 0, 100, "images/monsters/지렁이괴물.gif", player));
	    monsterList.add(new GunMonster(2600, 600, 130, "images/monsters/물걸레괴물7x.gif", player));
	    monsterList.add(new MonsterThread(3200, 0, 100, "images/monsters/슬라임괴물1.gif", player));
	    monsterList.add(new MonsterThread(4100, 600, 100, "images/monsters/책괴물7x.gif", player));	
	    monsterList.add(new MonsterThread(4500, 600, 100, "images/monsters/날개괴물7x.gif", player));		
	    monsterList.add(new MonsterThread(5600, 600, 100, "images/monsters/슬라임괴물1x4.gif", player));		
	    monsterList.add(new MonsterThread(7500, 600, 100, "images/monsters/책괴물.gif", player));		
	    monsterList.add(new MonsterThread(9600, 600, 100, "images/monsters/노란색슬라임괴물1x4.gif", player));		
	    monsterList.add(new MonsterThread(10000, 600, 100, "images/monsters/물걸레괴물7x.gif", player));		
	    monsterList.add(new MonsterThread(8000, 600, 100, "images/monsters/지렁이괴물.gif", player));		
	    monsterList.add(new MonsterThread(10600, 600, 100, "images/monsters/노란색슬라임괴물.gif", player));		
	    monsterList.add(new MonsterThread(12000, 600, 100, "images/monsters/물걸레괴물7x.gif", player));		
	    monsterList.add(new MonsterThread(13000, 600, 100, "images/monsters/지렁이괴물.gif", player));
        for (int i = 0; i < monsterList.size(); i++) {
        	monsterList.get(i).start();
        }

    }
}
