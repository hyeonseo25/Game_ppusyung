package panels;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.MouseListener;

public class StartPanel extends JPanel{
	private JButton startbt;
	private JButton rankbt;
	public StartPanel(Object o) {
		JLabel j1 = new JLabel("게임화면");
		j1.setBounds(0, 0, 200, 100);
		add(j1);
		startbt = new JButton();
		startbt.setName("StartButton");
		startbt.setText("게임시작");
		startbt.setBounds(10, 800, 200, 100);
		startbt.addMouseListener((MouseListener) o);
		add(startbt);
		
		rankbt = new JButton();
		rankbt.setName("RankingButton");
		rankbt.setText("랭킹");
		rankbt.setBounds(1000, 800, 200, 100);
		rankbt.addMouseListener((MouseListener) o);
		add(rankbt);
		setBackground(Color.CYAN);
	}
}