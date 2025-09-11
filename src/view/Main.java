package view;

import controller.DungeonThread;
import java.util.concurrent.Semaphore;

public class Main {

	public static void main(String[] args) {
		boolean[] itens =  new boolean[2];	// Item 0 = tocha, item 1 = pedra
		itens[0] = true;	// Tocha está disponível enquanto True
		itens[1] = true;	// Pedra está disponível enquanto True
		
		Semaphore semaforo = new Semaphore(1);
		int portaSaida = (int) (Math.random() * 4) + 1;	// Define a porta de saída verdadeira
		System.out.println("A porta de saída é a porta " + portaSaida);
		for (int i = 1; i < 5; i++) {
			Thread t = new DungeonThread(i, semaforo, itens, portaSaida);
			t.start();
		}
	}

}
