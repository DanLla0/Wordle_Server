package Wordle_Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
	BufferedReader inputStream;
	PrintWriter outputStream;
	Socket clientSocket;
	String secretWord, serverResponse;

	//CONSTRUCTOR
	public ClientThread(Socket s, String word) {
		this.clientSocket = s;
		this.secretWord = word;
		this.serverResponse = "";
		try {
			outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
			inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			System.out.println("In / Out ServerThread Error");
		}

	}

	
	//MAIN THREAD CODE
	public void run() {
		int attempts = 5;
		serverResponse = "";
		if (!clientSocket.isClosed()) {
			try {
				//CLIENT HAVE ATEMPTS
				while (attempts >= 1) {
					String guessWord = "";
					guessWord = (inputStream.readLine()).toUpperCase();
					serverResponse = getHintWord(secretWord, guessWord);
					if (!secretWord.equals(guessWord)) {
						//NORMAL SITUATION
						serverResponse += ("Intentos restantes: " + attempts);
						attempts--;
						outputStream.println(serverResponse);
					} else {
						//CLIENT WINS
						outputStream.println("Felicidades has ganado!!");
						attempts = 0;
					}
				}
				if (attempts == 0) {
					//CLIENT LOSES
					outputStream.println("La palabra secreta era: " + secretWord);
				}
				clientSocket.close();
				inputStream.close();
				outputStream.close();

			} catch (Exception e) {
			}
		}

	}

	//CREATE THE HINT WORD
	private static String getHintWord(String secretWordAux, String guessWordAux) {
		String hintWord = "";
		for (int i = 0; i < secretWordAux.length(); i++) {
			if (secretWordAux.charAt(i) == guessWordAux.charAt(i)) {
				hintWord = hintWord + secretWordAux.charAt(i);
			} else {
				hintWord = hintWord + " _ ";
			}
		}

		return hintWord;

	}

}
