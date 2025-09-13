package controller;

import java.util.concurrent.Semaphore;

public class DungeonThread extends Thread{
	
	private int[] cavaleiro = new int[2]; 
	private Semaphore semaforo;
	private static boolean[] itens;	
	private static boolean[] portas = new boolean[4];
	private static int portaSaida;
	
	public DungeonThread(int id, Semaphore semaforo, boolean[] itens, int portaSaida) {
		cavaleiro[0] = id;
		cavaleiro[1] = 0;
		this.semaforo = semaforo;
		this.itens = itens;
		this.portaSaida= portaSaida;
		for (int i = 0; i < 4; i++){
			portas[i] = false;
		}
	}
	
	@Override
	public void run() {
		corredor();
		try {
			semaforo.acquire();
			portas();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			semaforo.release();
		}
	}
	
	private void corredor() {
		int distanciaTotal = 2000;
		int distanciaPercorrida = 0;
		int velocidade = 0;
		
		velocidade = (int) (Math.random() * 3) + 2;
		
		System.out.println("Cavaleiro #" + cavaleiro[0] + " entrou no corredor!");
		while (distanciaPercorrida < 500) {
			distanciaPercorrida = caminhar(distanciaPercorrida, velocidade);
		}
		
		try {
			semaforo.acquire();
			if (itens[0] == true) {
				cavaleiro[1] = 1;	// Cavaleiro agora possui tocha
				System.out.println("Cavaleiro #" + cavaleiro[0] + " pegou a tocha!");
				itens[0] = false;
				velocidade += 2;
			}
		} catch(Exception e) {
			System.err.println(e.getMessage());
		} finally {
			semaforo.release();
		}
		
		while (distanciaPercorrida < 1500) {
			distanciaPercorrida = caminhar(distanciaPercorrida, velocidade);
		}
		
		try {
			semaforo.acquire();
			if (itens[1] == true && cavaleiro[1] == 0) {
				cavaleiro[1] = 2;	// Cavaleiro agora possui pedra
				System.out.println("Cavaleiro #" + cavaleiro[0] + " pegou a pedra!");
				itens[1] = false;
				velocidade += 2;
			}
		} catch(Exception e) {
			System.err.println(e.getMessage());
		} finally {
			semaforo.release();
		}
		
		while (distanciaPercorrida < distanciaTotal) {
			distanciaPercorrida = caminhar(distanciaPercorrida, velocidade);
		}
	}
	
	private int caminhar(int distanciaPercorrida, int velocidade) {
		try {
			sleep(50);
			distanciaPercorrida += velocidade;
			return distanciaPercorrida;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return 0;
		}
	}
	
	private void portas() {
		int portaEscolhida = 5;
		System.out.println("Cavaleiro #" + cavaleiro[0] + " chegou às portas");
		
		while (portaEscolhida == 5) {
			portaEscolhida = (int) (Math.random() * 4) + 1;
			
			if (portas[portaEscolhida - 1] == false) {
				portas[portaEscolhida - 1] = true;
				System.out.println("Cavaleiro #" + cavaleiro[0] + " escolheu a porta: " + portaEscolhida);
				break;
			} else {
				portaEscolhida = 5;
			}
			
		}
		
		if (portaEscolhida == portaSaida) {
			System.out.println("Cavaleiro #" + cavaleiro[0] + " saiu da dungeon!");
		} else {
			System.out.println("Cavaleiro #" + cavaleiro[0] + " foi devorado!");
		}
	}
}
