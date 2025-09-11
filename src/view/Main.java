package view;

import controller.DungeonThread;
import java.util.concurrent.Semaphore;

public class Main {

	public static void main(String[] args) {
		boolean[] itens =  new boolean[2];	// Item 0 = tocha, item 1 = pedra
		itens[0] = true;	// Tocha est� dispon�vel enquanto True
		itens[1] = true;	// Pedra est� dispon�vel enquanto True
		
		Semaphore semaforo = new Semaphore(1);
		int portaSaida = (int) (Math.random() * 4) + 1;	// Define a porta de sa�da verdadeira
		System.out.println("A porta de sa�da � a porta " + portaSaida);
		for (int i = 1; i < 5; i++) {
			Thread t = new DungeonThread(i, semaforo, itens, portaSaida);
			t.start();
		}
	}

}
