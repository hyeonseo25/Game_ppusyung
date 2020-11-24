package components;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import components.GunMonster;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import util.Util;

public class Monster {
	private JPanel mainPanel;
	Player player;
	
	Dimension view = Toolkit.getDefaultToolkit().getScreenSize();
	
	private ArrayList<MonsterThread> monsterList = new ArrayList<>(); //Monster °´Ã¼¸¦ ´ã´Â ArrayList
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
	
    public void createMonsters() { //¸Þ¼Òµå È£Ãâ ½Ã Monster °´Ã¼°¡ ¹è¿­¿¡ Ãß°¡
    	monsterList.clear();
		monsterList.add(new MonsterThread(1200, 450, 100, "images/monsters/Áö··ÀÌ±«¹°.gif", player));
		monsterList.add(new MonsterThread(1600, 450, 130, "images/monsters/¹°°É·¹±«¹°.gif", player));

        for (int i = 0; i < monsterList.size(); i++) {
        	monsterList.get(i).start();
        }
    }
    MonsterThread[] monster = {
    		new MonsterThread(2250, 500, 300, "images/monsters/³ë¶õ»ö½½¶óÀÓ±«¹°.gif", player),
    		new MonsterThread(2500, 450, 100, "images/monsters/½½¶óÀÓ±«¹°.gif", player),
    		new MonsterThread(3350, 400, 400, "images/monsters/³¯°³±«¹°.gif", player),
    		new MonsterThread(4000, 450, 100, "images/monsters/¹°°É·¹±«¹°.gif", player),
    		new MonsterThread(4190, 450, 100, "images/monsters/Ã¥±«¹°.gif", player),
    		new GunMonster(4520, 500, 100, "images/monsters/¹°°É·¹±«¹°.gif", player),
    		new MonsterThread(5570, 450, 100, "images/monsters/Áö··ÀÌ±«¹°.gif", player),
    		new MonsterThread(5990, 400, 100, "images/monsters/³¯°³±«¹°.gif", player),
    		new MonsterThread(6080, 450, 100, "images/monsters/Áö··ÀÌ±«¹°.gif", player),
    		new MonsterThread(6560, 450, 100, "images/monsters/½½¶óÀÓ±«¹°.gif", player),
    		new MonsterThread(8210, 400, 400, "images/monsters/³¯°³±«¹°.gif", player),
    		new MonsterThread(8240, 450, 300, "images/monsters/³ë¶õ»ö½½¶óÀÓ±«¹°.gif", player),
    		new MonsterThread(9460, 300, 1000, "images/monsters/½½¶óÀÓ±«¹°º¸½º.gif", player),
    		new MonsterThread(10520, 450, 100, "images/monsters/Ã¥±«¹°.gif", player),
    		new MonsterThread(10760, 450, 100, "images/monsters/Áö··ÀÌ±«¹°.gif", player),
    		new MonsterThread(11130, 450, 100, "images/monsters/¹°°É·¹±«¹°.gif", player),
    		new GunMonster(11430, 400, 400, "images/monsters/³¯°³±«¹°.gif", player)
    		};
    public void addMonster() {
    	monster[monstercnt].setPlayer(player);
    	monster[monstercnt].setX((int) view.getWidth()+50);
    	monsterList.add(monster[monstercnt]);
    	monsterList.get(monsterList.size()-1).start();
    	if(monstercnt< monster.length-1) {
    		monstercnt++;
    	}
    }
}