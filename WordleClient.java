package Wordle_Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class WordleClient {

	public static void main(String[] args) {
		String host = "localhost";
		int port = 2000;
		Scanner keyboard = new Scanner(System.in);
		String guessWord = "";
		Socket client;
		try {
			client = new Socket(host, port);
			PrintWriter outputStream = new PrintWriter(client.getOutputStream(), true);
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String serverResponse = "";
			do {
				// NORMAL GAME
				do {
					// IF THE WORD IS ABOVE 5 CHARS IS INVALID
					System.out.println("Dame una palabra de 5 letras");
					guessWord = keyboard.next();
				} while (!guessWord.matches("^[\\w\\W]{5}$"));
				outputStream.println(guessWord);
				serverResponse = inputStream.readLine();
				System.out.println(serverResponse);
			} while (serverResponse.contains("_"));
			client.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}