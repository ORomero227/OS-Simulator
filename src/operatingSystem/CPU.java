package operatingSystem;

//Oscar Romero

public class CPU {

	// Atributos
	private int cpuId;
	private int quantumTime;
	private int quantumTimeWatcher;
	private Process executingProcess;
	
	// Constructor
	public CPU() {
		this.cpuId = 1;
		this.quantumTime = 3;
		this.quantumTimeWatcher = 0;
		this.executingProcess = null;
	}

	public CPU(int cpuId,int quantumTime) {
		this.cpuId = cpuId;
		this.quantumTime = quantumTime;
		this.quantumTimeWatcher = 0;
		this.executingProcess = null;
	}

//-------------------Getters and setters--------------------------------
	
	public int getProgramCounter() {
		return this.executingProcess.getPCB().getPc();
	}

	public Process getExecutingProcess() {
		return this.executingProcess;
	}

	public int getQuantumTime() {
		return this.quantumTime;
	}
	
	public int getQuantumTimeWatcher() {
		return this.quantumTimeWatcher;
	}
	
	public void setQuantumTime(int quantumTime) {
		this.quantumTime = quantumTime;
	}
	
	public int getCPUsId() {
		return this.cpuId;
	}
//-------------------Metodos--------------------------------------------

//======= Metodo para añadir el proceso que se va a ejecutar ==========
	
	public void setExcecutingProcess(Process p) {
		if (this.isAvailable() == true) {
			this.executingProcess = p;
			this.executingProcess.getPCB().setState(3);
		}
	}

//==== Metodo para verificar si el cpu esta disponible ================
	
	public boolean isAvailable() {
		if (executingProcess == null) {
			return true;
		}
		return false;
	}

//======== Metodo para vaciar el cpu y colocar el contador a 1 ==========
	public void clear() {
		if (this.executingProcess != null) {
			this.executingProcess = null;
		}
	}

//======= Metodo para ejecutar el proceso que esta en el dispatcher =======
	
	public void execute() {

		if (this.isAvailable()) {
			return;
		}

		this.executingProcess.getPCB().incrementPC();
		
		if(this.quantumTimeWatcher < this.quantumTime) {
			this.quantumTimeWatcher++;
		}

	}
	
//===== Metodo para reiniciar el quantumTimeWatcher ========================
	
	public void resetQuantumTimeWatcher() {
		this.quantumTimeWatcher = 0;
	}

//==== Metodo para verificar si el timeSharing termino ======================
	
	public boolean isTimeSharingFinished() {
		if(this.quantumTimeWatcher == this.quantumTime) {
			return true;
		}
		
		return false;
	}

//===== Metodo para verificar si el proceso que esta en el cpu termino =====
	
	public boolean executionIsFinished() {
		if (this.executingProcess == null) {
			return true;
		}

		// Si el proceso termino cambia el estado a Finished y devuelve cierto
		if (this.executingProcess.getPCB().isFinished()) {
			return true;
		}

		return false;
	}

	
//========================== ToString =================================
	
	public String toString() {

		if (this.isAvailable()) {
			return "CPU Available";
		}

		return "CPU " + this.cpuId + " Running [PID: " + this.executingProcess.getProcessID() + "]";

	}

}
