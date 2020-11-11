package panels;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.DBConnection;

public class ClearPanel extends JPanel{
	private JButton replaybt;
	private JButton rankbt;
	private TextField name;
	private JButton applybt;
	private JLabel scoreLabel;
	private String scoreText;
	
	
	
	public String getScoreText() {
		return scoreText;
	}

	public void setScoreText(String scoreText) {
		this.scoreText = scoreText;
	}

	public ClearPanel(Object o){
		JLabel j1 = new JLabel("클리어화면");
		j1.setBounds(0, 0, 200, 100);
		add(j1);
		
		replaybt = new JButton();
		replaybt.setName("ReplayButton");
		replaybt.setText("다시하기");
		replaybt.setBounds(10, 800, 200, 100);
		replaybt.addMouseListener((MouseListener) o);
		add(replaybt);
		
		scoreLabel = new JLabel(getScoreText());
		scoreLabel.setLocation(500, 50);
		add(scoreLabel);
		
		name = new TextField();
		name.setName("name");
		name.setBounds(600, 600, 150, 150);
		add(name);
		
		applybt = new JButton();
		applybt.setName("applyButton");
		applybt.setText("확인" );
		applybt.setBounds(500, 500, 200, 100);
		applybt.addMouseListener((MouseListener) o);
		applybt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DBConnection db = new DBConnection();
				db.insertDB(name.getText() , "23");
				
			}
		});
		add(applybt);
		
		rankbt = new JButton();
		rankbt.setName("RankingButton");
		rankbt.setText("랭킹");
		rankbt.setBounds(1000, 800, 200, 100);
		rankbt.addMouseListener((MouseListener) o);
		add(rankbt);
		
		
		
		setBackground(Color.YELLOW);
	}

}