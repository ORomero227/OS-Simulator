package operatingSystem;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

@SuppressWarnings("serial")
public class GUI_ProcessControlBlock extends JFrame {

	private JPanel contentPane;
	private OperatingSystem os;
	private JTable pcbTable;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_ProcessControlBlock frame = new GUI_ProcessControlBlock();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	
	public GUI_ProcessControlBlock() {
		setTitle("Process Control Block Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 770, 505);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("Arial Black", Font.PLAIN, 14));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideFrame();
			}
		});
		btnClose.setBounds(270, 407, 197, 47);
		btnClose.setFocusable(false);
		contentPane.add(btnClose);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 10, 698, 387);
		contentPane.add(scrollPane);
						
		pcbTable = new JTable();
		scrollPane.setViewportView(pcbTable);
	}
	
	public void hideFrame() {
		this.setVisible(false);
	}
	
	public void setPcbTableModel(DefaultTableModel model) {
		pcbTable.setModel(model);
	}
	
	
	public OperatingSystem getOs() {
		return os;
	}
	
	public void setOs(OperatingSystem os) {
		this.os = os;
	}
	
}
