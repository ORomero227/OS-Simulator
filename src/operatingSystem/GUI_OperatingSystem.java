package operatingSystem;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Window.Type;

public class GUI_OperatingSystem {

	private JFrame frmMultiprogrammedBatchSystem;
	private OperatingSystem os;
	private GUI_ProcessControlBlock pcbGui;
	private GUI_Settings settingsGui;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_OperatingSystem window = new GUI_OperatingSystem();
					window.frmMultiprogrammedBatchSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */

	public void updateDispatcher(JList<Process> dispatcherList) {
		dispatcherList.setModel(os.getDispatcher());
	}

	public void updateFinishedList(JList<Process> finishedList) {
		finishedList.setModel(os.getFinishedList());
	}

	public void updateBlockedList(JList<Process> blockedList) {
		blockedList.setModel(os.getBlockedList());
	}

	public void updateCPUsList(JList<CPU> cpusList) {
		cpusList.setModel(os.getCPUsList());
	}

	public void updatePCBTableModel() {
		pcbGui.setPcbTableModel(os.getPcbTableModel());
	}

	public GUI_OperatingSystem() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {

		os = new OperatingSystem();

		settingsGui = new GUI_Settings();

		pcbGui = new GUI_ProcessControlBlock();

		frmMultiprogrammedBatchSystem = new JFrame();
		frmMultiprogrammedBatchSystem.setType(Type.POPUP);
		frmMultiprogrammedBatchSystem.getContentPane().setBackground(new Color(255, 255, 255));
		frmMultiprogrammedBatchSystem.setForeground(new Color(255, 255, 255));
		frmMultiprogrammedBatchSystem.setTitle("Operating System Simulator");
		frmMultiprogrammedBatchSystem.setBounds(100, 100, 660, 435);
		frmMultiprogrammedBatchSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMultiprogrammedBatchSystem.getContentPane().setLayout(null);

		JScrollPane hddPanel = new JScrollPane();
		hddPanel.setViewportBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Hard Drive Disk / NEW", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		hddPanel.setBounds(10, 10, 186, 216);
		frmMultiprogrammedBatchSystem.getContentPane().add(hddPanel);

		JList<Process> hddList = new JList<Process>();
		hddList.setModel(os.getHardDriveDisk());
		hddPanel.setViewportView(hddList);

		JScrollPane dispatcherPanel = new JScrollPane();
		dispatcherPanel.setViewportBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Dispatcher / READY", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		dispatcherPanel.setBounds(206, 10, 138, 216);
		frmMultiprogrammedBatchSystem.getContentPane().add(dispatcherPanel);

		JList<Process> dispatcherList = new JList<Process>();
		dispatcherList.setModel(os.getDispatcher());
		dispatcherPanel.setViewportView(dispatcherList);

		JScrollPane cpuPanel = new JScrollPane();
		cpuPanel.setViewportBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"CPU / RUNNING", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		cpuPanel.setBounds(354, 10, 279, 137);
		frmMultiprogrammedBatchSystem.getContentPane().add(cpuPanel);

		JList<CPU> cpusList = new JList<CPU>();
		cpusList.setModel(os.getCPUsList());
		cpuPanel.setViewportView(cpusList);

		JScrollPane blockedPanel = new JScrollPane();
		blockedPanel.setViewportBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Blocked",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		blockedPanel.setBounds(354, 157, 91, 211);
		frmMultiprogrammedBatchSystem.getContentPane().add(blockedPanel);

		JList<Process> blockedList = new JList<Process>();
		blockedPanel.setViewportView(blockedList);

		JScrollPane finishedPanel = new JScrollPane();
		finishedPanel.setViewportBorder(
				new TitledBorder(null, "Finished", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		finishedPanel.setBounds(455, 157, 178, 211);
		frmMultiprogrammedBatchSystem.getContentPane().add(finishedPanel);

		JList<Process> finishedList = new JList<Process>();
		finishedPanel.setViewportView(finishedList);

		JButton btnClockTick = new JButton("Clock Tick");
		btnClockTick.setForeground(new Color(0, 0, 0));
		btnClockTick.setFont(new Font("Arial", Font.BOLD, 14));
		btnClockTick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				os.clockTick();

				updateDispatcher(dispatcherList);

				updateCPUsList(cpusList);

				cpusList.repaint();

				updateBlockedList(blockedList);

				updateFinishedList(finishedList);

				updatePCBTableModel();
			}
		});
		btnClockTick.setBounds(10, 247, 334, 51);
		btnClockTick.setFocusable(false);
		frmMultiprogrammedBatchSystem.getContentPane().add(btnClockTick);

		JButton btnViewPCBFrame = new JButton("PCB");
		btnViewPCBFrame.setForeground(new Color(0, 0, 0));
		btnViewPCBFrame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pcbGui.setVisible(true);
			}
		});
		btnViewPCBFrame.setFont(new Font("Arial", Font.BOLD, 14));
		btnViewPCBFrame.setBounds(184, 308, 160, 38);
		btnViewPCBFrame.setFocusable(false);
		frmMultiprogrammedBatchSystem.getContentPane().add(btnViewPCBFrame);

		JButton btnSettings = new JButton("Settings");
		btnSettings.setForeground(new Color(0, 0, 0));
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settingsGui.setOperatingSystem(os);
				settingsGui.setVisible(true);
			}
		});
		btnSettings.setFont(new Font("Arial", Font.BOLD, 14));
		btnSettings.setFocusable(false);
		btnSettings.setBounds(10, 308, 164, 38);
		frmMultiprogrammedBatchSystem.getContentPane().add(btnSettings);
	}

}
