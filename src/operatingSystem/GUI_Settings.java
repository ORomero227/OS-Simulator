package operatingSystem;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JSlider;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class GUI_Settings extends JFrame {

	private JPanel contentPane;
	private JSlider sliderProbability;
	private JSlider sliderCPUsQty;
	private JSlider sliderQuantumTime;
	private JSpinner spinnerMaxProcessSize;
	private JSpinner spinnerBlockTime;
	private JLabel lblprobabilityPercent;
	private JRadioButton btnRandom;
	private JRadioButton btnConstant;
	private OperatingSystem os;
	private ButtonGroup btnGroup;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Settings frame = new GUI_Settings();
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

	public GUI_Settings(OperatingSystem os) {
		this.setOperatingSystem(os);
	}

	public void hideFrame() {
		this.setVisible(false);
	}

	public OperatingSystem getOs() {
		return os;
	}

	public void setOperatingSystem(OperatingSystem os) {
		this.os = os;
	}

	public void applyChangesToCPUs() {

		if (os == null) {
			return;
		}

		int cpuQty = (int) sliderCPUsQty.getValue();

		os.getSettings().setCpusQty(cpuQty);
		os.applyChangesToCPUs();
	}

	public void quantumTimeHandler() {
		if (os == null) {
			return;
		}

		int quantumTimeQty = (int) sliderQuantumTime.getValue();
		os.getSettings().setQuantumTimeQty(quantumTimeQty);
		os.changeCPUsQuantumTime();
	}

	public void constantButtonAction() {
		if (os == null) {
			return;
		}

		os.getSettings().setIsRandomSelected(false);
		os.getSettings().setIsConstantSelected(true);
		int probability = (int) sliderProbability.getValue();
		os.getSettings().setConstantProbability(probability);
		lblprobabilityPercent.setText("Probability %: " + sliderProbability.getValue());
	}

	public void randomButtonAction() {
		if (os == null) {
			return;
		}

		os.getSettings().setIsConstantSelected(false);
		os.getSettings().setIsRandomSelected(true);
	}

	public void probabilityHandler() {
		if (os == null) {
			return;
		}

		int probability = (int) sliderProbability.getValue();
		os.getSettings().setConstantProbability(probability);
		lblprobabilityPercent.setText("Probability %: " + sliderProbability.getValue());
	}

	public void maxProcessSizeHandler() {
		if (os == null) {
			return;
		}

		os.getSettings().setMaxSize((int) spinnerMaxProcessSize.getValue());
		os.changeMaxProcessSize();
	}

	public void blockTimeHandler() {
		if (os == null) {
			return;
		}

		os.getSettings().setBlockTime((int) spinnerBlockTime.getValue());
		os.changeBlockTime();
	}

	public GUI_Settings() {

		btnGroup = new ButtonGroup();
		setTitle("Settings");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 488, 287);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// ==================== Close Button =============================

		JButton btnClose = new JButton("Close");
		btnClose.setBounds(315, 212, 149, 25);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideFrame();
			}
		});
		btnClose.setFont(new Font("Arial", Font.PLAIN, 14));
		btnClose.setFocusable(false);
		contentPane.setLayout(null);
		contentPane.add(btnClose);

		// ===================== CPUS =======================================

		JLabel lblCPUsQty = new JLabel("CPUs Qty:");
		lblCPUsQty.setBounds(38, 10, 89, 25);
		lblCPUsQty.setHorizontalAlignment(SwingConstants.CENTER);
		lblCPUsQty.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(lblCPUsQty);

		sliderCPUsQty = new JSlider();
		sliderCPUsQty.setPaintLabels(true);
		sliderCPUsQty.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				applyChangesToCPUs();
			}
		});
		sliderCPUsQty.setMajorTickSpacing(1);
		sliderCPUsQty.setValue(1);
		sliderCPUsQty.setMinorTickSpacing(1);
		sliderCPUsQty.setMinimum(1);
		sliderCPUsQty.setMaximum(4);
		sliderCPUsQty.setBounds(38, 41, 89, 35);
		contentPane.add(sliderCPUsQty);

		// ==================== QuantumTime ===================================

		JLabel lblQuantumTime = new JLabel("QuantumTime Qty");
		lblQuantumTime.setBounds(172, 12, 122, 21);
		lblQuantumTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuantumTime.setFont(new Font("Arial", Font.PLAIN, 14));

		sliderQuantumTime = new JSlider();
		sliderQuantumTime.setPaintLabels(true);
		sliderQuantumTime.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				quantumTimeHandler();
			}
		});
		sliderQuantumTime.setValue(3);
		sliderQuantumTime.setMinorTickSpacing(1);
		sliderQuantumTime.setMinimum(1);
		sliderQuantumTime.setMaximum(10);
		sliderQuantumTime.setMajorTickSpacing(1);
		sliderQuantumTime.setBounds(172, 38, 140, 42);
		contentPane.add(sliderQuantumTime);
		contentPane.add(lblQuantumTime);

		// ================== Max Process Size ====================================

		JLabel lblProcessSizes = new JLabel("Max Process Size");
		lblProcessSizes.setHorizontalAlignment(SwingConstants.CENTER);
		lblProcessSizes.setFont(new Font("Arial", Font.PLAIN, 14));
		lblProcessSizes.setBounds(344, 12, 114, 21);
		contentPane.add(lblProcessSizes);

		spinnerMaxProcessSize = new JSpinner();
		spinnerMaxProcessSize
				.setModel(new SpinnerNumberModel(Integer.valueOf(10), Integer.valueOf(1), null, Integer.valueOf(1)));
		spinnerMaxProcessSize.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				maxProcessSizeHandler();
			}
		});
		spinnerMaxProcessSize.setBounds(370, 41, 59, 25);
		contentPane.add(spinnerMaxProcessSize);

		// ===================== Block Time ======================================

		JLabel lblBlockTime = new JLabel("Block Time");
		lblBlockTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblBlockTime.setFont(new Font("Arial", Font.PLAIN, 14));
		lblBlockTime.setBounds(344, 103, 114, 21);
		contentPane.add(lblBlockTime);

		spinnerBlockTime = new JSpinner();
		spinnerBlockTime.setModel(new SpinnerNumberModel(20, 1, 20, 1));
		spinnerBlockTime.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				blockTimeHandler();
			}
		});
		spinnerBlockTime.setBounds(370, 131, 59, 25);
		contentPane.add(spinnerBlockTime);

		// ================== Probability =========================================

		JLabel lblGenerationProbability = new JLabel("Generation Probability");
		lblGenerationProbability.setHorizontalAlignment(SwingConstants.CENTER);
		lblGenerationProbability.setBounds(38, 106, 256, 21);
		lblGenerationProbability.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPane.add(lblGenerationProbability);

		// =========== Random Button =========================

		btnRandom = new JRadioButton("Random");
		btnRandom.setHorizontalAlignment(SwingConstants.CENTER);
		btnRandom.setSelected(true);
		btnRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sliderProbability.setVisible(false);
				lblprobabilityPercent.setVisible(false);
				randomButtonAction();
			}
		});
		btnRandom.setBounds(63, 133, 74, 21);
		contentPane.add(btnRandom);

		// =========== Constant Button =======================

		btnConstant = new JRadioButton("Constant");
		btnConstant.setHorizontalAlignment(SwingConstants.CENTER);
		btnConstant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sliderProbability.setVisible(true);
				lblprobabilityPercent.setVisible(true);
				constantButtonAction();
			}
		});
		btnConstant.setBounds(204, 133, 80, 21);
		contentPane.add(btnConstant);

		btnGroup.add(btnRandom);
		btnGroup.add(btnConstant);

		sliderProbability = new JSlider();
		sliderProbability.setVisible(false);
		sliderProbability.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				probabilityHandler();
			}
		});
		sliderProbability.setBounds(63, 160, 221, 49);
		sliderProbability.setMinimum(10);
		sliderProbability.setPaintLabels(true);
		sliderProbability.setMajorTickSpacing(10);
		sliderProbability.setMinorTickSpacing(1);
		contentPane.add(sliderProbability);

		lblprobabilityPercent = new JLabel();
		lblprobabilityPercent.setBounds(89, 212, 169, 21);
		contentPane.add(lblprobabilityPercent);
	}
}
