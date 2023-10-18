package operatingSystem;

public class ProcessControlBlock {

	// Atributos
	private int pid;
	private int priority;
	private int blockTime;
	private int totalTime;
	private int readyTime;
	private int newTime;
	private int state;
	private int size;
	private int pc;
	private int blockCounter;

	static int NEW = 1;
	static int READY = 2;
	static int RUNNING = 3;
	static int BLOCKED = 4;
	static int FINISHED = 5;

	// Constructores

	public ProcessControlBlock() {
		this.pid = -1;
		this.priority = 0;
		this.blockTime = 0;
		this.totalTime = 0;
		this.readyTime = 0;
		this.newTime = 1;
		this.state = NEW;
		this.size = 0;
		this.pc = 0;
		this.blockCounter = 0;
	}

	public ProcessControlBlock(int pid, int size, int priority) {
		this.pid = pid;
		this.priority = priority;
		this.blockTime = 0;
		this.totalTime = 0;
		this.readyTime = 0;
		this.newTime = 1;
		this.state = NEW;
		this.size = size;
		this.pc = 0;
		this.blockCounter = 0;
	}

//--------------------------- Getters and Setters --------------------------------------
	public int getPid() {
		return pid;
	}

	public int getPriority() {
		return priority;
	}

	public int getBlockTime() {
		return blockTime;
	}
	
	public int getTotalTime() {
		return totalTime;
	}
	
	public int getReadyTime() {
		return readyTime;
	}
	
	public int getNewTime() {
		return newTime;
	}

	public int getState() {
		return state;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getPc() {
		return pc;
	}
	
	public int getBlockCounter() {
		return blockCounter;
	}
	
	public void setPid(int pid) {
		this.pid = pid;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setBlockTime(int blockTime) {
		this.blockTime = blockTime;
	}
	
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public void setReadyTime(int readyTime) {
		this.readyTime = readyTime;
	}

	public void setNewTime(int newTime) {
		this.newTime = newTime;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setPc(int pc) {
		this.pc = pc;
	}
	
	public void setBlockCounter(int blockCounter) {
		this.blockCounter = blockCounter;
	}

//--------------------------- Metodos ------------------------------------------------	

	public String getStatesInString() {

		if (state == NEW) {
			return "New";
		} else if (state == READY) {
			return "Ready";
		} else if (state == RUNNING) {
			return "Running";
		} else if (state == BLOCKED) {
			return "Blocked";
		} else if (state == FINISHED) {
			return "Finished";
		} else {
			return "Unknown";
		}
	}

//========= Verifica para determinar si el cpu termino ============

	public boolean isFinished() {
		if (pc == size) {
			return true;
		}

		return false;
	}

//======= Metodo para incrementar el program counter ==============

	public void incrementPC() {
		this.pc++;
	}

//========== Metodo para incrementar el tiempo new ================

	public void incrementNewTime() {
		this.newTime++;
	}

//========== Metodo para incrementar el tiempo ready ================
	
	public void incrementReadyTime() {
		this.readyTime++;
	}

//========= Metodo para incrementar el block time ===================
	
	public void incrementBlockTime() {
		this.blockTime++;
	}

	
//======== Metodo para descontar el blockCounter ====================
	
	public void decreaseBlockCounter() {
		this.blockCounter--;
	}
	
}