package operatingSystem;

import java.util.Random;

public class Process implements Comparable<Process> {

	//Atributos
	private int processID;
	private int processSize;
	private int priority;
	private ProcessControlBlock pcb;
	
	// Constructor
	public Process() {
		this.processID = -1;
		this.processSize = this.randomSize(20);
		this.priority = this.randomPriority();
		this.pcb = new ProcessControlBlock();
	}

	// Constructor recibe un id para identificar el proceso
	public Process(int pid) {
		this.processID = pid;
		this.processSize = this.randomSize(20);
		this.priority = this.randomPriority();
		this.pcb = new ProcessControlBlock(pid,processSize,priority);
	}

	public Process(int pid,int maxSize) {
		this.processID = pid;
		this.processSize = this.randomSize(maxSize);
		this.priority = this.randomPriority();
		this.pcb = new ProcessControlBlock(pid,processSize,priority);
	}
	
//-------------- Getters and setters ------------------------------
	
	public int getProcessID() {
		return processID;
	}

	public void setProcessID(int processID) {
		this.processID = processID;
	}

	public int getProcessSize() {
		return processSize;
	}

	public void setProcessSize(int processSize) {
		this.processSize = processSize;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public ProcessControlBlock getPCB() {
		return this.pcb;
	}
	
	
//----------------- Metodos ---------------------------------------
	
	public void incrementPC() {
		this.pcb.setPc(this.pcb.getPc() + 1);
	}
	
	
//=== Metodo para generar un size aleatorio desde 1 a 10 ===========
	
	public int randomSize(int maxProcess) {
		Random rnd = new Random();

		int randomSize = rnd.nextInt(maxProcess) + 1;

		return randomSize;
	}

//====== Metodo para generar una prioridad aleatoria entre el 1 y el 10 =====
	
	public int randomPriority() {
		Random rnd = new Random();

		int randomPriority = rnd.nextInt(10) + 1;

		return randomPriority;
	}

//============== Muestra la informacion del proceso =========================
	
	public String toString() {
		
		//State is ready
		if(this.getPCB().getState() == 2) {
			return "PID: " + this.processID + " Priority: " + this.priority;
		}
		//State is finished
		if(this.getPCB().getState() == 5) {
			return "PID: " + this.processID + " PC: " + this.getPCB().getPc() + " Size: " + this.processSize;
		}
		//State is blocked
		if(this.getPCB().getState() == 4) {
			return "PID: " + this.processID;
		}
		
		return "PID: " + this.processID + " Size: " + this.processSize + " Priority: " + this.priority;
	}

//================= Metodo para ordenar el dispatcher por prioridad ========
	
	@Override
	public int compareTo(Process o) {

		return Integer.compare(this.getPriority(), o.getPriority());

	}

}
