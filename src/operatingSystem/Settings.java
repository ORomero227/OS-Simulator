package operatingSystem;

import java.util.Random;

public class Settings {

	private int cpusQty;
	private int quantumTimeQty;
	private int constantProbability;
	private int randomProbability;
	private int blockTime;
	private int maxProcessSize;
	private boolean isConstantSelected;
	private boolean isRandomSelected;
	
	public Settings() {
		this.cpusQty = 1;
		this.quantumTimeQty = 3;
		this.maxProcessSize = 10;
		this.blockTime = 20;
		this.constantProbability = this.getConstantProbability();
		this.randomProbability = this.getRandomProbability();
		this.isConstantSelected = false;
		this.isRandomSelected = true;
	}

	public int getCpusQty() {
		return this.cpusQty;
	}

	public int getQuantumTimeQty() {
		return this.quantumTimeQty;
	}
	
	public int getConstantProbability() {
		return this.constantProbability;
	}
	
	public int getMaxSize() {
		return this.maxProcessSize;
	}
	
	public int getBlockTime() {
		return this.blockTime;
	}
	
	public void setQuantumTimeQty(int quantumTimeQty) {
		this.quantumTimeQty = quantumTimeQty;
	}

	public void setCpusQty(int cpusQty) {
		this.cpusQty = cpusQty;
	}
	
	public void setMaxSize(int maxSize) {
		this.maxProcessSize = maxSize;
	}
	
	public void setBlockTime(int blockTime) {
		this.blockTime = blockTime;
	}
	
//===================== Metodos para manejar probabilidad ======
	
	//============= Constant ======================== 
	
	public void setIsConstantSelected(boolean isSelected) {
		this.isConstantSelected = isSelected;
	}

	public boolean isConstantSelected() {
		if(this.isConstantSelected == true) {
			return true;
		}
		
		return false;
	}
	
	public void setConstantProbability(int probability) {
		this.constantProbability = probability;
	}
	
	//==========  Random ===========================
	
	public void setIsRandomSelected(boolean isSelected) {
		this.isRandomSelected = isSelected;
	}
	
	public boolean isRandomSelected() {
		if(this.isRandomSelected == true) {
			return true;
		}
		
		return false;
	}
	
	public int getRandomProbability() {
		Random rnd = new Random();
		
		this.randomProbability = rnd.nextInt(100 + 1);
		
		return this.randomProbability;
	}
}
