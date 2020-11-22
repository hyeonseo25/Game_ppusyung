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

	private ArrayList<MonsterThread> monsterList = new ArrayList<>(); //Monster ��ü�� ��� ArrayList
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
	
    public void createMonsters() { //�޼ҵ� ȣ�� �� Monster ��ü�� �迭�� �߰�
    	monsterList.clear();
		monsterList.add(new MonsterThread(1200, 450, 100, "images/monsters/�����̱���.gif", player));
	    //monsterList.add(new GunMonster(1600, 400, 130, "images/monsters/���ɷ�����7x.gif", player));
	    monsterList.add(new MonsterThread(1200, 450, 100, "images/monsters/�����ӱ���1.gif", player));
	    monsterList.add(new MonsterThread(1300, 400, 100, "images/monsters/å����7x.gif", player));	
	    monsterList.add(new MonsterThread(4500, 400, 100, "images/monsters/��������7x.gif", player));		
	    monsterList.add(new MonsterThread(5600, 400, 100, "images/monsters/�����ӱ���1x4.gif", player));		
	    monsterList.add(new MonsterThread(7500, 400, 100, "images/monsters/å����.gif", player));		
	    monsterList.add(new MonsterThread(9600, 400, 100, "images/monsters/����������ӱ���1x4.gif", player));		
	    monsterList.add(new MonsterThread(10000, 400, 100, "images/monsters/���ɷ�����7x.gif", player));		
	    monsterList.add(new MonsterThread(8000, 400, 100, "images/monsters/�����̱���.gif", player));		
	    monsterList.add(new MonsterThread(10600, 400, 100, "images/monsters/����������ӱ���.gif", player));		
	    monsterList.add(new MonsterThread(12000, 400, 100, "images/monsters/���ɷ�����7x.gif", player));		
	    monsterList.add(new MonsterThread(13000, 400, 100, "images/monsters/�����̱���.gif", player));
        for (int i = 0; i < monsterList.size(); i++) {
        	monsterList.get(i).start();
        }

    }
}