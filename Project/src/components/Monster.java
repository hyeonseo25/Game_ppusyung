package components;

import java.awt.Image;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import components.GunMonster;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import static panels.GamePanel.field;
import util.Util;

public class Monster {
	private JPanel mainPanel;
	Player player;

	private ArrayList<MonsterThread> monsterList = new ArrayList<>(); //Monster °´Ã¼¸¦ ´ã´Â ArrayList
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
	
    public void createMonsters() { //¸Þ¼Òµå È£Ãâ ½Ã Monster °´Ã¼°¡ ¹è¿­¿¡ Ãß°¡
    	monsterList.clear();
		monsterList.add(new MonsterThread(1200, 0, 100, "images/monsters/Áö··ÀÌ±«¹°.gif", player));
	    monsterList.add(new MonsterThread(2600, 600, 130, "images/monsters/¹°°É·¹±«¹°7x.gif", player));
	    monsterList.add(new MonsterThread(3200, 0, 100, "images/monsters/½½¶óÀÓ±«¹°7x.gif", player));
	    monsterList.add(new MonsterThread(4100, 600, 100, "images/monsters/Ã¥±«¹°7x.gif", player));	
	    monsterList.add(new MonsterThread(4500, 600, 100, "images/monsters/³¯°³±«¹°7x.gif", player));		
	    monsterList.add(new MonsterThread(5600, 600, 100, "images/monsters/Áö··ÀÌ±«¹°.gif", player));		
	    monsterList.add(new MonsterThread(7500, 600, 100, "images/monsters/Ã¥±«¹°.gif", player));		
	    monsterList.add(new MonsterThread(9600, 600, 100, "images/monsters/³ë¶õ»ö½½¶óÀÓ±«¹°.gif", player));		
	    monsterList.add(new MonsterThread(10000, 600, 100, "images/monsters/¹°°É·¹±«¹°7x.gif", player));		
	    monsterList.add(new MonsterThread(8000, 600, 100, "images/monsters/Áö··ÀÌ±«¹°.gif", player));		
	    monsterList.add(new MonsterThread(10600, 600, 100, "images/monsters/³ë¶õ»ö½½¶óÀÓ±«¹°.gif", player));		
	    monsterList.add(new MonsterThread(12000, 600, 100, "images/monsters/¹°°É·¹±«¹°7x.gif", player));		
	    monsterList.add(new MonsterThread(13000, 600, 100, "images/monsters/Áö··ÀÌ±«¹°.gif", player));
	    


        for (int i = 0; i < monsterList.size(); i++) {
        	monsterList.get(i).start();
        }

    }
}
