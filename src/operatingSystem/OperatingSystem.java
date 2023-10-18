package operatingSystem;

//Oscar Romero

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

public class OperatingSystem {

	// Atributos
	private DefaultListModel<Process> hdd;
	private DefaultListModel<Process> finishedList;
	private DefaultListModel<Process> blockedList;
	private ArrayList<Process> dispatcher;
	private DefaultListModel<ProcessControlBlock> pcbList;
	private DefaultListModel<CPU> cpuList;
	private DefaultTableModel pcbTableModel;
	private RandomProcessGenerator rpg;
	private Settings settings;
	private int blockTime;

	// Constructor
	public OperatingSystem() {
		this.hdd = new DefaultListModel<Process>();
		this.finishedList = new DefaultListModel<Process>();
		this.blockedList = new DefaultListModel<Process>();
		this.pcbList = new DefaultListModel<ProcessControlBlock>();
		this.dispatcher = new ArrayList<Process>();
		this.cpuList = new DefaultListModel<CPU>();
		this.rpg = new RandomProcessGenerator();
		this.settings = new Settings();

		this.blockTime = settings.getBlockTime();
		this.rpg.setMaxProcessSize(settings.getMaxSize());

		// Generar los cpus
		for (int i = 0; i < settings.getCpusQty(); i++) {
			int id = i + 1;
			CPU cpu = new CPU(id, settings.getQuantumTimeQty());
			this.cpuList.addElement(cpu);
		}

		this.applyChangesToCPUs();
	}
//--------------------Getters and setters-------------------------------------

	public DefaultListModel<Process> getHardDriveDisk() {
		return this.hdd;
	}

	public DefaultListModel<Process> getDispatcher() {
		DefaultListModel<Process> dispatcherModel = new DefaultListModel<Process>();

		for (Process p : this.dispatcher) {
			dispatcherModel.addElement(p);
		}

		return dispatcherModel;
	}

	public DefaultListModel<Process> getFinishedList() {
		return this.finishedList;
	}

	public DefaultListModel<Process> getBlockedList() {
		return this.blockedList;
	}

	public DefaultListModel<CPU> getCPUsList() {
		return this.cpuList;
	}

	public DefaultTableModel getPcbTableModel() {
		String[] columnNames = { "PID", "TimeNew", "TimeReady", "TimeBlock", "TimeTotal", "PC", "Size", "State" };
		pcbTableModel = new DefaultTableModel(columnNames, 0);

		for (int i = 0; i < this.pcbList.getSize(); i++) {
			ProcessControlBlock p = this.pcbList.getElementAt(i);
			Object[] data = { p.getPid(), p.getNewTime(), p.getReadyTime(), p.getBlockTime(), p.getTotalTime(),
					p.getPc(), p.getSize(), p.getStatesInString() };
			pcbTableModel.addRow(data);
		}

		return this.pcbTableModel;
	}

	public RandomProcessGenerator getRandomProcessGenerator() {
		return this.rpg;
	}

	public Settings getSettings() {
		return this.settings;
	}

	public int getBlockTime() {
		return this.blockTime;
	}
//-----------------------------Metodos---------------------------------------------------------

//===================== Configuraciones / Cambios  ============================================

	public void changeBlockTime() {
		this.blockTime = settings.getBlockTime();
	}

	public void changeMaxProcessSize() {
		this.rpg.setMaxProcessSize(settings.getMaxSize());
	}

	public void changeCPUsQuantumTime() {
		for (int i = 0; i < this.cpuList.getSize(); i++) {
			this.cpuList.elementAt(i).setQuantumTime(settings.getQuantumTimeQty());
		}
	}

	public void configureProbability() {
		if (settings.isRandomSelected()) {
			this.rpg.setProbability(settings.getRandomProbability());
		} else if (settings.isConstantSelected()) {

			this.rpg.setProbability(settings.getConstantProbability());
		}
	}

//===================== Metodo para generar los processos =====================================

	public void processGenerator() {

		// Se obtiene el proceso generado
		Process process = this.rpg.getRandomProcess();

		// Si el proceso no es null se anade al discoduro y la info del pcb del proceso
		// a la tabla
		if (process != null) {
			this.hdd.addElement(process);
			ProcessControlBlock pcb = process.getPCB();
			this.pcbList.addElement(pcb);
		}

	}

// ===================== Metodo para mover los procesos al dispatcher ==========================

	public void moveToDispatcher() {

		// Verificacion del disco duro
		if (this.hdd.isEmpty()) {
			return;
		}

		if (this.dispatcher.size() <= 10) {
			Process process = this.hdd.remove(0);
			process.getPCB().setState(2);
			this.dispatcher.add(process);
		}
	}

// ===================== Metodo para ajustar los CPUs  ====================================

	public void applyChangesToCPUs() { 

		int currentCPUsQty = this.cpuList.getSize();

		if (currentCPUsQty == this.settings.getCpusQty()) {
			return;
		}

		// Aumentar los CPUs
		if (currentCPUsQty < this.settings.getCpusQty()) {
			for (int i = currentCPUsQty; i < this.settings.getCpusQty(); i++) {
				CPU newCPU = new CPU(i + 1, this.settings.getQuantumTimeQty());
				this.cpuList.addElement(newCPU);
			}
		} // Eliminar los CPUs
		else {

			if (currentCPUsQty == 0) {
				currentCPUsQty = 1;
			}

			for (int i = this.settings.getCpusQty(); i < currentCPUsQty; i++) {
				if (!this.cpuList.elementAt(i).isAvailable()) {
					Process process = this.cpuList.elementAt(i).getExecutingProcess();
					process.getPCB().setState(2);
					this.dispatcher.add(process);
					this.cpuList.remove(i);
				} else {
					this.cpuList.removeElementAt(i);
				}
			}
		}
	}

//============== Metodo para mover el proceso del dispatcher al cpu ======================

	public void moveToCPU() {

		for (int i = 0; i < this.cpuList.getSize(); i++) {
			if (this.cpuList.elementAt(i).isAvailable() && !this.dispatcher.isEmpty()) {
				Process p = this.dispatcher.remove(0);
				this.cpuList.elementAt(i).setExcecutingProcess(p);
			}
		}

	}

//=========== Metodos para implementar el bloqueo a un proceso ======================================

	public boolean isBlocked() {

		Random rnd = new Random();

		int probability = 15;

		int n = rnd.nextInt(100);

		if (n <= probability) {
			return true;
		}
		
		return false;
	}
	
	
	public void blockedImplementation() {
		
		for(int i = 0; i < this.cpuList.getSize(); i++) {
			
			//Si el cpu no tiene un proceso
			if(this.cpuList.elementAt(i).isAvailable()) {
				continue;
			}
			
			//Si tiene, verifica si se va a bloquear
			if(this.isBlocked()) {
				
				Random rnd = new Random();
				
				//El tiempo que va a estar bloqueado
				int n = rnd.nextInt(this.settings.getBlockTime());
				
				Process process = this.cpuList.elementAt(i).getExecutingProcess();
				process.getPCB().setBlockCounter(n);
				process.getPCB().setState(4);
				this.blockedList.addElement(process);
				this.cpuList.elementAt(i).clear();
			}
		}
	}

	public void manageBlockedList() {

		if (this.blockedList.isEmpty()) {
			return;
		}

		for (int i = 0; i < this.blockedList.getSize(); i++) {
			Process process = this.blockedList.elementAt(i);
			process.getPCB().decreaseBlockCounter();

			if (process.getPCB().getBlockCounter() == 0) {
				this.dispatcher.add(process);
				process.getPCB().setState(2);
				this.blockedList.remove(i);
			}
		}

	}

//=========== Metodo devolver al dispatcher si no termino dentro del quantumtime ==========

	private void returnToDispatcher() {

		for (int i = 0; i < this.cpuList.getSize(); i++) {
			Process p = this.cpuList.elementAt(i).getExecutingProcess();

			if (p != null) {
				this.dispatcher.add(p);
				p.getPCB().setState(2);
				this.cpuList.elementAt(i).clear();
			}
		}
	}

//========== Implementa el quantumTime en la ejecucion de los procesos ======================

	private void timeSharingImplementation() {

		for (int i = 0; i < this.cpuList.getSize(); i++) {
			if (this.cpuList.elementAt(i).isTimeSharingFinished()) {
				this.cpuList.getElementAt(i).resetQuantumTimeWatcher();
				this.returnToDispatcher();
				this.moveToCPU();
			}
		}
	}

//============= Metodo que ejecuta el proceso que esta en cpu ======================

	public void execute() {
		for (int i = 0; i < this.cpuList.getSize(); i++) {
			this.cpuList.elementAt(i).execute();
		}
	}

//============== Metodo que verifica si la ejecucion termino =======================

	public void executionFinished() {
		for (int i = 0; i < this.cpuList.getSize(); i++) {
			if (this.cpuList.elementAt(i).executionIsFinished()
					&& this.cpuList.getElementAt(i).getExecutingProcess() != null) {

				Process p = this.cpuList.elementAt(i).getExecutingProcess();

				p.getPCB().setState(5);

				this.finishedList.addElement(p);

				this.cpuList.elementAt(i).clear();
			}
		}
	}

//=================== Metodos para manejar los tiempos =============================

	public void updateNewTime() {
		if (this.hdd.isEmpty()) {
			return;
		}

		for (int i = 0; i < this.hdd.getSize(); i++) {
			ProcessControlBlock p = this.hdd.elementAt(i).getPCB();
			p.incrementNewTime();
		}
	}

	public void updateReadyTime() {
		if (this.dispatcher.isEmpty()) {
			return;
		}

		for (int i = 0; i < this.dispatcher.size(); i++) {
			ProcessControlBlock p = this.dispatcher.get(i).getPCB();
			p.incrementReadyTime();
		}

	}

	public void updateTotalTime() {
		if (this.finishedList.isEmpty()) {
			return;
		}

		for (int i = 0; i < this.finishedList.getSize(); i++) {
			ProcessControlBlock p = this.finishedList.elementAt(i).getPCB();
			int sumOfTimes = p.getNewTime() + p.getReadyTime() + p.getBlockTime();

			p.setTotalTime(sumOfTimes);
		}
	}

	public void updateBlockTime() {
		if (this.blockedList.isEmpty()) {
			return;
		}

		for (int i = 0; i < this.blockedList.getSize(); i++) {
			ProcessControlBlock p = this.blockedList.elementAt(i).getPCB();
			p.incrementBlockTime();
		}
	}

	public void updateTimes() {
		this.updateNewTime();
		this.updateReadyTime();
		this.updateBlockTime();
		this.updateTotalTime();
	}

//==================== Main =====================================================

	public void clockTick() {

		// Configurar la probabilidad para generar los procesos
		this.configureProbability();

		// Genera procesos y los guarda en el discoduro
		this.processGenerator();

		// Mueve los procesos al dispatcher
		this.moveToDispatcher();

		// Organiza el dispatcher por orden de prioridad
		Collections.sort(this.dispatcher);

		// Mueve los procesos al cpu
		this.moveToCPU();

		// Intentar bloquear el proceso
		this.blockedImplementation();

		// Manejar el tiempo si hay algun proceso en la lista de bloqueados
		this.manageBlockedList();

		// Ejecuar el/los procesos en los CPUs
		this.execute();

		// Implementar el timeSharing
		this.timeSharingImplementation();

		// Verificar si se termino de ejecutar algun proceso
		executionFinished();

		// Actualizar los tiempos
		this.updateTimes();

//		if(settings.isConstantSelected()) {
//			System.out.println("Generate CONSTANT: " + this.rpg.getProbability());
//		}else if(settings.isRandomSelected()) {
//			System.out.println("Generate RANDOM: " + this.rpg.getProbability());
//		}

	}

}