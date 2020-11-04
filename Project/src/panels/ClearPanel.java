package panels;

import java.awt.Color;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClearPanel extends JPanel{
	private JButton replaybt;
	private JButton rankbt;
	public ClearPanel(Object o) {
		JLabel j1 = new JLabel("클리어화면");
		j1.setBounds(0, 0, 200, 100);
		add(j1);
		
		replaybt = new JButton();
		replaybt.setName("ReplayButton");
		replaybt.setText("다시하기");
		replaybt.setBounds(10, 800, 200, 100);
		replaybt.addMouseListener((MouseListener) o);
		add(replaybt);
		
		rankbt = new JButton();
		rankbt.setName("RankingButton");
		rankbt.setText("랭킹");
		rankbt.setBounds(1000, 800, 200, 100);
		rankbt.addMouseListener((MouseListener) o);
		add(rankbt);
		
		setBackground(Color.YELLOW);
	}
}