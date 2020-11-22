package panels;

import java.awt.AlphaComposite;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import components.GunMonster;
import components.Monster;
import components.MonsterThread;
import components.Player;
import components.Shot;
import components.Field;
import components.Tacle;
import components.Jelly;
import main.Main;
import util.DBConnection;
import util.Util;

public class GamePanel extends JPanel{
	
	boolean keyLeft = false;
	boolean keyRight = false;
	boolean keyEnter = false;
	boolean keySpace = false;
	boolean check=false;
	int cnt=5;
	
	private Clip backgroundMusic;
	
	private ImageIcon backImg = new ImageIcon("images/�����гι��.png");
	private Image back = backImg.getImage();
	
	private ImageIcon hpImg = new ImageIcon("images/HP.png");
	private Image hp = hpImg.getImage();
	
	// ���� �̹��� �����ܵ�
	private ImageIcon field1Ic = new ImageIcon("images/map/����.png"); // ����
	private ImageIcon field2Ic = new ImageIcon("images/map/fieldIc2.png"); // ���߹���

	// ��ֹ� �̹��� �����ܵ�
	private ImageIcon tacle10Ic = new ImageIcon("images/map/tacle2.png"); // 1ĭ ��ֹ�
	
	// ���� �̹��� �����ܵ�
	private ImageIcon jelly1Ic = new ImageIcon("images/map/�ӽ�Ĺ�帵ũ.png");;
	private ImageIcon jelly2Ic = new ImageIcon("images/map/�𸸵�.png");;
	private ImageIcon jelly3Ic = new ImageIcon("images/map/�𸸵�.png");;
		
	private int[] monsterSpawnpoint = {2250,2500,4000}; //���� ���� ��ġ
	private int nowMonster=0; // ���ݱ��� ������ ������ ��
	
	Dimension view = Toolkit.getDefaultToolkit().getScreenSize();
	
	private int field = 800;
	
	private int backX=0;
	private int backX2 = back.getWidth(null);
	private String endTime; //���� Ŭ���� �ð�
	
	// ����Ʈ ����
	private List<Jelly> jellyList; // ���� ����Ʈ
	private List<Field> fieldList; // ���� ����Ʈ
	private List<Tacle> tacleList; // ��ֹ� ����Ʈ
	
	// �̹��� ���Ϸ� �� ���� �����´�.
	private int[] sizeArr; // �̹����� ���̿� ���̸� �������� 1���� �迭
	private int[][] colorArr; // �̹����� x y ��ǥ�� �ȼ� ������ �����ϴ� 2�����迭
		
	private int end = back.getWidth(null)-(view.width-1600);
	
	Player player = new Player(this);
	Monster monster;
	util.Timer time;
	
	// �ٸ� Ŭ���� ������
	JFrame frame;
	CardLayout cl;
	Main main;
	ClearPanel clearPanel;
	
	public String getTime() {
		if (Integer.valueOf(time.getSeconds()) <0 ) {
			//gameOver();
		}
		return time.getSeconds() + "��";
	}
	
	public String getScore1() {
		return Integer.toString(player.getScore());
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public int getField() {
		return field;
	}

	public void setField(int field) {
		this.field = field;
	}

	public String getEndTime() {
		return this.endTime;
	}
	public String getScore() {
		return Integer.toString(player.getScore()) + "��";
	}

	public int getHp() {
		return player.getHp();
	}
	public void setBackX(int backX) {
		this.backX = backX;
	}
	
	public int getBackX() {
		return backX;
	}
	
	public int getBackX2() {
		return backX2;
	}

	public void setBackX2(int backx2) {
		backX2 = backx2;
	}
	
	public GamePanel(Object o, JFrame frame, CardLayout cl) {
		this.frame = frame;
		this.cl = cl;
		this.main = (Main)o;
		test(); // ��ġ�� ����
		playGame();
	}
	public GamePanel() {
		
	}
	public void gameStart() {
		time = new util.Timer();
		time.start();
		player = new Player(this);

		player.fall(); // field ���� �÷��̾ ������ ��������
		player.deleteShot(); // ȭ�� ������ ���� �Ѿ��� ���ִ� �޼���
		monster = new Monster(this, player);
		//monster.createMonsters(monster.getMonsterList());//������ ������ Monster ��ü���� �迭�� �߰�
		monster.createMonsters();//������ ������ Monster ��ü���� �迭�� �߰�
		setCpField();
		repaintThread();
		playMusic();
	}
	// ��ġ�� ����
	private void test() {
		JButton gameoverbt;
		JButton clearbt;
		
		gameoverbt = new JButton();
		gameoverbt.setName("GameoverButton");
		gameoverbt.setText("���ӿ���");
		gameoverbt.setBounds(1690, 10, 100, 50);
		gameoverbt.addMouseListener((MouseListener) main);
		add(gameoverbt);
		
		clearbt = new JButton();
		clearbt.setName("ClearButton");
		clearbt.setText("Ŭ����");
		clearbt.setBounds(1800, 10, 100, 50);
		clearbt.addMouseListener((MouseListener) main);
		add(clearbt);
	}
	private void playGame() {
		setFocusable(true);
		initListener();
		initObject();
		initMap(2);
	}
	private void initObject() {
		jellyList = new ArrayList<>(); // ���� ����Ʈ

		fieldList = new ArrayList<>(); // ���� ����Ʈ

		tacleList = new ArrayList<>(); // ��ֹ� ����Ʈ
	}
	// ���� ������ �׸��� �̹����� �޾Ƽ� ����
		private void initMap(int num) {

			String tempMap = null;
			int tempMapLength = 0;

			if (num == 1) {
				tempMap = "images/map/map1.png";
			} else if (num == 2) {
				tempMap = "images/map/�ʹ�ġ2.png";
			}

			// �� ���� �ҷ�����
			try {
				sizeArr = Util.getSize(tempMap); // �� ����� �迭�� ����
				colorArr = Util.getPic(tempMap); // �� �ȼ����� �迭�� ����
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			int maxX = sizeArr[0]; // ���� ����
			int maxY = sizeArr[1]; // ���� ����

			for (int i = 0; i < maxX; i += 1) { // ������ 1ĭ�� �����ϱ� ������ 1,1������� �ݺ����� ������.
				for (int j = 0; j < maxY; j += 1) {
					if (colorArr[i][j] == 16756425) { // ������ 16776960�� ��� �⺻���� ����
						// ��ǥ�� 40�� ���ϰ�, ���̿� ���̴� 30���� �Ѵ�.
						jellyList.add(new Jelly(jelly1Ic.getImage(), i * 40, j * 40, 70, 70, 255, 1234));

					} else if (colorArr[i][j] == 16776444) { // ������ 13158400�� ��� ������� ����
						// ��ǥ�� 40�� ���ϰ�, ���̿� ���̴� 30���� �Ѵ�.
						jellyList.add(new Jelly(jelly2Ic.getImage(), i * 40, j * 40, 70, 70, 255, 2345));

					} else if (colorArr[i][j] == 9868800) { // ������ 9868800�� ��� ������� ����
						// ��ǥ�� 40�� ���ϰ�, ���̿� ���̴� 30���� �Ѵ�.
						jellyList.add(new Jelly(jelly3Ic.getImage(), i * 40, j * 40, 70, 70, 255, 3456));

					}
//						else if (colorArr[i][j] == 16737280) { // ������ 16737280�� ��� �� ���� ����
//						// ��ǥ�� 40�� ���ϰ�, ���̿� ���̴� 30���� �Ѵ�.
//						jellyList.add(new Jelly(jellyHPIc.getImage(), i * 40, j * 40, 30, 30, 255, 4567));
//					}
				}
			}
			for (int i = 0; i < maxX; i += 2) { // ������ 4ĭ�� �����ϴ� �����̱� ������ 2,2������� �ݺ����� ������.
				for (int j = 0; j < maxY; j += 2) {
					if (colorArr[i][j] == 0) { // ������ 0 �ϰ�� (������)
						// ��ǥ�� 40�� ���ϰ�, ���̿� ���̴� 80���� �Ѵ�.
						fieldList.add(new Field(field1Ic.getImage(), i * 40 , j * 40, 80, 80));

					} else if (colorArr[i][j] == /*6579300*/12829635) { // ������ 12829635 �ϰ�� (ȸ��)
						// ��ǥ�� 40�� ���ϰ�, ���̿� ���̴� 80���� �Ѵ�.
						fieldList.add(new Field(field2Ic.getImage(), i*40 , j * 40, 80, 80));
					}
				}
			}

			for (int i = 0; i < maxX; i += 2) { // ��ֹ��� 4ĭ �̻��� �����Ѵ�. ���� ����
				for (int j = 0; j < maxY; j += 2) {
					if (colorArr[i][j]==15539236) { // ������ 16776958�� ��� (������) 1ĭ
						// ��ǥ�� 40�� ���ϰ�, ���̿� ���̴� 80���� �Ѵ�.
						tacleList.add(new Tacle(tacle10Ic.getImage(), i * 40 , j * 40, 80, 80, 0));
					} else if(colorArr[i][j]!=16777215&&colorArr[i][j]!=12829635&&colorArr[i][j]!=0){
						System.out.println(colorArr[i][j]);
					}
						//else if (colorArr[i][j] == 16711830) { // ������ 16711830�� ��� (��ȫ) 2ĭ
//						// ��ǥ�� 40�� ���ϰ�, ���̿� ���̴� 160���� �Ѵ�.
//						tacleList.add(new Tacle(tacle20Ic.getImage(), i * 40 , j * 40, 80, 160, 0));
//
//					} else if (colorArr[i][j] == 16711935) { // ������ 16711830�� ��� (����ũ) 3ĭ
//						// ��ǥ�� 40�� ���ϰ�, ���̿� ���̴� 240���� �Ѵ�.
//						tacleList.add(new Tacle(tacle30Ic.getImage(), i * 40 , j * 40, 80, 240, 0));
//					}
				}
			}
			
		}
		
	private void playMusic() {
		 try {
			 File file = new File("music/backgroundMusic.wav");
			 AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			 backgroundMusic = AudioSystem.getClip();
			 backgroundMusic.open(stream);
			 backgroundMusic.start();
			 backgroundMusic.loop(-1);
	     } catch(Exception e) {
	    	 e.printStackTrace();
	     }	
	}
	public void closeMusic() {
		backgroundMusic.close();
	}
	public void Sound(String file, boolean Loop){
		//���������޼ҵ�
		//���� Ŭ������ �߰��� �޼ҵ带 �ϳ� �� ��������ϴ�.
		//�����������޾Ƶ鿩�ش���带�����Ų��.
		Clip clip;
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
			if (Loop) clip.loop(-1);
			//Loop ����true�� ������������ѹݺ���ŵ�ϴ�.
			//false�� �ѹ��������ŵ�ϴ�.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// ������ �߰� �޼���
	private void initListener() {
		addKeyListener(new KeyAdapter() { // Ű ������ �߰�
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				switch(keyCode) {
				case KeyEvent.VK_A: keyLeft = true; break;
				case KeyEvent.VK_D: keyRight = true; break;
				case KeyEvent.VK_ENTER: keyEnter = true; break;
				case KeyEvent.VK_SPACE: 
					if(player.getCountJump() < 2) {
						player.jump();
						Sound("music/jumpMusic.wav", false);
					}
					keySpace = true; break;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_A: keyLeft = false; player.stop();break;
				case KeyEvent.VK_D: keyRight = false;  player.stop();break;
				case KeyEvent.VK_ENTER: keyEnter = false; break;
				case KeyEvent.VK_SPACE: keySpace = false; check=false; break;
				}
			}
		});
	}
	public void keyCheck() throws InterruptedException {
		if(keyLeft==true) {
			if(player.getDistance()+1200==monsterSpawnpoint[monster.getMonsterCnt()]&&nowMonster==monster.getMonsterCnt()) {
				monsterSpawn();
			}				
			player.p_moveLeft();
			System.out.println(player.getDistance());
		}else if(keyRight==true) {
			if(player.getDistance()+1200==monsterSpawnpoint[monster.getMonsterCnt()]&&nowMonster==monster.getMonsterCnt()) {
				monsterSpawn();
			}
			System.out.println(player.getDistance() );
			if(player.getDistance()>end) {
				closeMusic();
				time.interrupt();
				keySpace = false;
				Sound("music/clearMusic.wav", false);
				TimeUnit.SECONDS.sleep(3);
				//for (int i = 0; i < monster.getMonsterList().size(); i++) {
				//	monster.getMonsterList().get(i).setPlayer(null);
				//}
				player.setScore(player.getScore()+Integer.valueOf(time.getSeconds())*10);
				main.getClearPanel().setScore(player.getScore());
				cl.show(frame.getContentPane(), "clear");
				//frame.getContentPane().remove(this);
				frame.requestFocus();
				setEndTime(getTime()); //���� Ŭ���� �ð�
			}else if(player.getDistance()>back.getWidth(null)-(view.width-700)) {
				player.p_moveRight();
			}else if(player.getX()>700) {  //�÷��̾ �߰��� ������
				player.p_moveRight(1);//�Ű������� �����ε��� �޼��带 ���� ��Ű�� ����. �� �� �ǹ� ����
				movebg();
			}else {
				player.p_moveRight();
			}
		}
		if(keyEnter==true) {
			if(cnt==5) {
				Sound("music/Gunshot.wav", false);//�ѽ�� �Ҹ�
				player.p_hit();
				cnt=0;
			}
		}
		if(keySpace==true) {
			
		}
	}
	//���Ͱ� ������
	public void repaintThread() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					
					
					repaint();
					
					try {
						keyCheck();
						setObject();
						if(cnt<5) {
							cnt++; // �Ѿ˿� ������
						}
						
						if(player.getY() - player.getImage().getHeight(null)>1100) {
							player.setHp(0);
						}
						if(player.getHp()<=0) {
							gameOver();	
							break;
						}
						Thread.sleep(40);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
	
			}
			
		}).start();
	}
	//�гο� �׸���
		public void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			g.drawImage(back, backX, 0, this);
			ArrayList<Shot> list = player.getShots();
			ArrayList<Shot> GunMonster_shotlist = GunMonster.GunShotList;
			//monsterList�� �ִ� monster ��ü���� �׸�
			for (int i = 0; i < monster.getMonsterList().size(); i++) {
				g.drawImage(monster.getMonsterList().get(i).getImage(), monster.getMonsterList().get(i).getX(), monster.getMonsterList().get(i).getY(), this);
			}
			for(int i=0; i<list.size();i++) {
				g.drawImage(list.get(i).getImage(), list.get(i).getX(), list.get(i).getY(), this);
			}
			
			Graphics2D g2 = (Graphics2D)g;
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) player.getInvincibility()/255));
			g.drawImage(player.getImage(), player.getX(), player.getY(), this);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255));
			for(int i=0; i<GunMonster_shotlist.size();i++) {
				g.drawImage(GunMonster_shotlist.get(i).getImage(), GunMonster_shotlist.get(i).getX(), GunMonster_shotlist.get(i).getY(), this);
			}
			for(int i=0; i<player.getHp()/200; i++) {
				g.drawImage(hp, 10+i*70, 10, this);
			}
			for (int i = 0; i < fieldList.size(); i++) {

				Field tempFoot = fieldList.get(i);

				// ����� �� ��Ƹ԰� �ϱ����� ��ġ
				if (tempFoot.getX() > -90 && tempFoot.getX() < view.getWidth()) { // x���� -90~810�� ��ü�鸸 �׸���.

					g.drawImage(tempFoot.getImage(), tempFoot.getX(), tempFoot.getY(), tempFoot.getWidth(),
							tempFoot.getHeight(), null);
				}

			}
			for (int i = 0; i < jellyList.size(); i++) {

				Jelly tempJelly = jellyList.get(i);

				if (tempJelly.getX() > -90 && tempJelly.getX() < view.getWidth()) {

					g.drawImage(tempJelly.getImage(), tempJelly.getX(), tempJelly.getY(), tempJelly.getWidth(),
							tempJelly.getHeight(), null);

				}
			}
			// ��ֹ��� �׸���
			for (int i = 0; i < tacleList.size(); i++) {

				Tacle tempTacle = tacleList.get(i);

				if (tempTacle.getX() > -90 && tempTacle.getX() < view.getWidth()) {

					g.drawImage(tempTacle.getImage(), tempTacle.getX(), tempTacle.getY(), tempTacle.getWidth(),
							tempTacle.getHeight(), null);
				}
			}
			g.setFont(new Font("����ü", Font.BOLD, 40));  //Ÿ�̸� �۾�ü
			g.drawString(getTime(), 900, 50); // Ÿ�̸� �׸���
			g.drawString(getScore(), 1500, 50); // ���� �׸���
			
		}
		public void setObject() {
			int face = player.getX() + player.getImage().getWidth(null); // ĳ���� ���� ��ġ �罺ĵ
			int foot = player.getY() + player.getImage().getHeight(null); // ĳ���� �� ��ġ �罺ĵ
			for (int i = 0; i < tacleList.size(); i++) {
				Tacle tempTacle = tacleList.get(i); // �ӽ� ������ ����Ʈ �ȿ� �ִ� ���� ��ֹ��� �ҷ�����
				if ( // �������°� �ƴϰ� �����̵� ���� �ƴϸ� ĳ������ ���� �ȿ� ��ֹ��� ������ �ε�����
						player.getInvincibility()==255
							&& tempTacle.getX() + tempTacle.getWidth() / 2 >= player.getX()
							&& tempTacle.getX() + tempTacle.getWidth() / 2 <= face
							&& tempTacle.getY() + tempTacle.getHeight() / 2 >= player.getY()
							&& tempTacle.getY() + tempTacle.getHeight() / 2 <= foot) {
						try {
							player.damaged(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} // �ǰ� + ���� ������ �޼���
	
					} else if ( // �����̵� �ƴҽ� ������ֹ�
						player.getInvincibility()==255
							&& tempTacle.getX() + tempTacle.getWidth() / 2 >= player.getX()
							&& tempTacle.getX() + tempTacle.getWidth() / 2 <= face
							&& tempTacle.getY() <= player.getY()
							&& tempTacle.getY() + tempTacle.getHeight() * 95 / 100 > player.getY()) {
						try {
							player.damaged(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} // �ǰ� + ���� ������ �޼���
	
					}
			}
			for (int i = 0; i < jellyList.size(); i++) {

				Jelly tempJelly = jellyList.get(i); // �ӽ� ������ ����Ʈ �ȿ� �ִ� ���� ������ �ҷ�����
				if ( // ĳ������ ���� �ȿ� ������ ������ �������� �Դ´�.
					tempJelly.getX() + tempJelly.getWidth() * 20 / 100 >= player.getX()
							&& tempJelly.getX() + tempJelly.getWidth() * 80 / 100 <= face
							&& tempJelly.getY() + tempJelly.getWidth() * 20 / 100 >= player.getY()
							&& tempJelly.getY() + tempJelly.getWidth() * 80 / 100 <= foot
							) {

//						if (tempJelly.getImage() == jellyHPIc.getImage()) {
//							if ((c1.getHealth() + 100) > 1000) {
//								c1.setHealth(1000);
//							} else {
//								c1.setHealth(c1.getHealth() + 100);
//							}
//						}
						tempJelly.setImage(null); // ������ �̹����� ����Ʈ�� �ٲ۴�
						player.setScore(player.getScore()+10); // �������� ���� ������ ���Ѵ�

					}
			}
			setCpField(1); // �÷��̾� �ʵ� ����
			setCpField(2); // ���� �ʵ� ����
			
		}
		//�г� ���� ������
	public void movebg() {
		backX -=10; 
		// backX2 -=10;
		for (int i = 0; i < monster.getMonsterList().size(); i++) {
			monster.getMonsterList().get(i).m_move(20);	
		}
		// ������ġ�� -4 �� ���ش�.
		for (int i = 0; i < jellyList.size(); i++) {

			Jelly tempJelly = jellyList.get(i); // �ӽ� ������ ����Ʈ �ȿ� �ִ� ���� ������ �ҷ�����

			if (tempJelly.getX() < -90) { // ������ x ��ǥ�� -90 �̸��̸� �ش� ������ �����Ѵ�.(����ȭ)

				fieldList.remove(tempJelly);

			} else {

				tempJelly.setX(tempJelly.getX() - 10); // �� ���ǿ� �ش��� �ȵǸ� x��ǥ�� ������
				
			}
		}
		// ������ġ�� -3 �� ���ش�. (�������� �帣�� ȿ��)
		for (int i = 0; i < fieldList.size(); i++) {

			Field tempField = fieldList.get(i); // �ӽ� ������ ����Ʈ �ȿ� �ִ� ���� ������ �ҷ�����

			if (tempField.getX() < -90) { // ������ x��ǥ�� -90 �̸��̸� �ش� ������ �����Ѵ�.(����ȭ)

				fieldList.remove(tempField);

			} else {

				tempField.setX(tempField.getX() - 10); // �� ���ǿ� �ش��� �ȵǸ� x��ǥ�� ������

			}
		}
		// ��ֹ���ġ�� - 4 �� ���ش�.
		for (int i = 0; i < tacleList.size(); i++) {
			Tacle tempTacle = tacleList.get(i); // �ӽ� ������ ����Ʈ �ȿ� �ִ� ���� ��ֹ��� �ҷ�����
			if (tempTacle.getX() < -90) {
				fieldList.remove(tempTacle); // ��ֹ��� x ��ǥ�� -90 �̸��̸� �ش� ������ �����Ѵ�.(����ȭ)
			} else {
				tempTacle.setX(tempTacle.getX() - 10); // �� ���ǿ� �ش��� �ȵǸ� x��ǥ�� ������
			}
		}
	}
	public void gameOver() {
		closeMusic();
		time.interrupt();
		keySpace = false;
		Sound("music/die.wav", false);
		for (int i = 0; i < monster.getMonsterList().size(); i++) {
			monster.getMonsterList().get(i).setPlayer(null);
		}
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//frame.getContentPane().remove(this); // ��� �ߴ� ���� �г��� �����ӿ��� ��
		cl.show(frame.getContentPane(), "gameover");
		main.getGameOverPanel().playMusic();
		frame.requestFocus();
	}
	public void setCpField() {
		player.setField(this.field);
		for (int i = 0; i < monster.getMonsterList().size(); i++) {
			monster.getMonsterList().get(i).setField(this.field);
		}
	}
	public void setCpField(int cp) {
		if(cp==1) {
			int face = player.getX() + player.getImage().getWidth(null); // ĳ���� ���� ��ġ �罺ĵ
			int foot = player.getY() + player.getImage().getHeight(null); // ĳ���� �� ��ġ �罺ĵ
			// ��Ű�� ���� ������ ����ϴ� �ڵ�
			int tempField; // ������ġ�� ��� ��ĵ�ϴ� ��������
			int tempNowField=2000; // ĳ���Ϳ� ������ ���̿� ���� ����Ǵ� ��������, ����� nowField�� �����Ѵ�

			for (int i = 0; i < fieldList.size(); i++) { // ������ ������ŭ �ݺ�

				int tempX = fieldList.get(i).getX(); // ������ x��

				if (tempX > player.getX() - 60 && tempX <= face) { // ������ ĳ�� ���� ���̶��

					tempField = fieldList.get(i).getY(); // ������ y���� tempField�� �����Ѵ�

					foot = player.getY() + player.getImage().getHeight(null); // ĳ���� �� ��ġ �罺ĵ

					// ������ġ�� tempNowField���� ����, �߹ٴ� ���� �Ʒ� �ִٸ�
					// ��, ĳ���� �� �Ʒ��� ���� ���� �ִ� �����̶�� tempNowField�� �����Ѵ�.
					if (tempField < tempNowField && tempField >= foot) {

						tempNowField = tempField;

					}
				}
			}

			field = tempNowField; // ����� nowField�� ������Ʈ �Ѵ�.
			player.setField(this.field);
		}else if(cp==2) {
			for (int j = 0; j < monster.getMonsterList().size(); j++) {
				MonsterThread m = monster.getMonsterList().get(j);
				int face = m.getX() + m.getImage().getWidth(null); // ĳ���� ���� ��ġ �罺ĵ
				int foot = m.getY() + m.getImage().getHeight(null); // ĳ���� �� ��ġ �罺ĵ
				// ��Ű�� ���� ������ ����ϴ� �ڵ�
				int tempField; // ������ġ�� ��� ��ĵ�ϴ� ��������
				int tempNowField=2000; // ĳ���Ϳ� ������ ���̿� ���� ����Ǵ� ��������, ����� nowField�� �����Ѵ�

				for (int i = 0; i < fieldList.size(); i++) { // ������ ������ŭ �ݺ�

					int tempX = fieldList.get(i).getX(); // ������ x��

					if (tempX > m.getX() - 60 && tempX <= face) { // ������ ĳ�� ���� ���̶��

						tempField = fieldList.get(i).getY(); // ������ y���� tempField�� �����Ѵ�

						foot = m.getY() + m.getImage().getHeight(null); // ĳ���� �� ��ġ �罺ĵ

						// ������ġ�� tempNowField���� ����, �߹ٴ� ���� �Ʒ� �ִٸ�
						// ��, ĳ���� �� �Ʒ��� ���� ���� �ִ� �����̶�� tempNowField�� �����Ѵ�.
						if (tempField < tempNowField && tempField >= foot) {

							tempNowField = tempField;

						}
					}
				}
				m.setField(tempNowField);
			}
		}
		
	}
	public void monsterSpawn() {
		monster.addMonster(player.getDistance()+1200);
		nowMonster++;
	}
}