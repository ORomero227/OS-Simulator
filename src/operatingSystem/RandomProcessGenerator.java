package operatingSystem;

import java.util.Random;

public class RandomProcessGenerator {

	// Atributos
	private int counter;
	private int probability;
	private int maxProcessSize;

	// Constructor
	public RandomProcessGenerator() {
		this.counter = 1;
		this.maxProcessSize = 10;
		this.probability = this.getRandomProbability();
	}

//----------- Getters y setters -----------------------------
	
	public int getCounter() {
		return this.counter;
	}
	
	public int getMaxProcessSize() {
		return this.maxProcessSize;
	}
	
	public void setMaxProcessSize(int maxProcessSize) {
		this.maxProcessSize = maxProcessSize;
	}
	
	public void setProbability(int probability) {
		this.probability = probability;
	}
	
	public int getProbability() {
		return this.probability;
	}

	private int getRandomProbability() {
		Random rnd = new Random();
		
		this.probability = rnd.nextInt(100 + 1);
		
		return this.probability;
	}
	
//------------------- Metodos -------------------------------

//== Genera una istancia nueva si n es menor o igual a la probabilidad ==
	
	public Process getRandomProcess() {
		Random rnd = new Random();

		int n = rnd.nextInt(100 + 1);

		if (n <= this.probability) {
			//System.out.println("Se creo un proceso nuevo");
			return new Process(counter++,maxProcessSize);
		}

		//System.out.println("No se creo un proceso nuevo");
		return null;
	}
}