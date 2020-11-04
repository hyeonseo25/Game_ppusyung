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
		JLabel j1 = new JLabel("��ŷȭ��");
		j1.setBounds(0, 0, 200, 100);
		add(j1);
		

		//��ŷ ���̺� 
		String clname[] = {"���� ", "�̸�"};
		
		DefaultTableModel model = new DefaultTableModel(clname, 0) { //�� ����
			public boolean isCellEditable(int i, int c) { //�� ���� �ȵǰ�
				return false;
			}
		};
		
		model.addRow(clname); //ù �� �̸� 
		
		JTable RankingTable = new JTable(model); //���̺� �����ϰ� model ���� ���
		RankingTable.setBounds(800, 200, 200, 200); 
		RankingTable.setFocusable(false); //��Ŀ�� ��������
		RankingTable.setRowSelectionAllowed(false); //�� ���� �ȵǰ� 
		
		JScrollPane scroll = new JScrollPane(RankingTable); //������� ��ũ�� �ǰ�
		

		DBConnection db = new DBConnection(); //��� ����
		String sql = "select * from user order by score DESC"; //score ������������ ����
		
		try {
			db.rs = db.stmt.executeQuery(sql);
			while(db.rs.next()) {
				Vector<String> record = new Vector();
				record.add(db.rs.getString("score"));
				record.add(db.rs.getString("name"));
				model.addRow(record); //model�� �� �߰�
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		add(RankingTable);
		
		replaybt = new JButton();
		replaybt.setName("ReplayButton");
		replaybt.setText("ó������");
		replaybt.setBounds(10, 800, 200, 100);
		replaybt.addMouseListener((MouseListener) o);
		add(replaybt);
		
		//setBackground(Color.GRAY);
	}
}