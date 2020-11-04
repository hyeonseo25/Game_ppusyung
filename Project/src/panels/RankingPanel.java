package panels;

import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import util.DBConnection;

public class RankingPanel extends JPanel{
	private JButton replaybt;

	public RankingPanel(Object o) {
		JLabel j1 = new JLabel("랭킹화면");
		j1.setBounds(0, 0, 200, 100);
		add(j1);
		

		//랭킹 테이블 
		String clname[] = {"점수 ", "이름"};
		
		DefaultTableModel model = new DefaultTableModel(clname, 0) { //모델 생성
			public boolean isCellEditable(int i, int c) { //셀 선택 안되게
				return false;
			}
		};
		
		model.addRow(clname); //첫 열 이름 
		
		JTable RankingTable = new JTable(model); //테이블 생성하고 model 구조 사용
		RankingTable.setBounds(800, 200, 200, 200); 
		RankingTable.setFocusable(false); //포커스 안잡히게
		RankingTable.setRowSelectionAllowed(false); //열 선택 안되게 
		
		JScrollPane scroll = new JScrollPane(RankingTable); //길어지면 스크롤 되게
		

		DBConnection db = new DBConnection(); //디비 연결
		String sql = "select * from user order by score DESC"; //score 내림차순으로 정렬
		
		try {
			db.rs = db.stmt.executeQuery(sql);
			while(db.rs.next()) {
				Vector<String> record = new Vector();
				record.add(db.rs.getString("score"));
				record.add(db.rs.getString("name"));
				model.addRow(record); //model에 행 추가
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		add(RankingTable);
		
		replaybt = new JButton();
		replaybt.setName("ReplayButton");
		replaybt.setText("처음으로");
		replaybt.setBounds(10, 800, 200, 100);
		replaybt.addMouseListener((MouseListener) o);
		add(replaybt);
		
		//setBackground(Color.GRAY);
	}
}