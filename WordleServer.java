package Wordle_Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class WordleServer {
	public static void main(String[] args) {
		ServerSocket serverSocket;
		String host = "localhost";
		int port = 2000;
		
		String[] words = { "PLATO", "PISAR", "PLANO", "MAREO", "LISTA", "LISTO", "SUCIO", "PERRO", "MIXTO", "BULTO",
				"CASTO", "PRADO", "MOSCA", "PISTO", "TURCO", "BRAVO", "VISTO", "QUESO", "GUISO", "USADO" };
		int randomPosition = (int) (Math.random() * 20);
		String secretWord = "";
		secretWord = words[randomPosition];
		Socket client;

		try {
			//INICIATE SERVER
			serverSocket = new ServerSocket(port);
			System.out.println("Server is now active");
			System.out.println("SECRET WORD: " + secretWord);	
			while (true) {
				client = serverSocket.accept();
				System.out.println("Client accepted");
				ClientThread clientThread = new ClientThread(client,secretWord);
				clientThread.start();		
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}