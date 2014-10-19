   /*if[Console]*/
		 

package client;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Event;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Random;
import java.util.Scanner;

import sun.security.krb5.EncryptedData;
/*if[en1]*/
import common.Base64Encrypt;
/*end[en1]*/
/*if[en2]*/
import common.ReverseEncrypt;
/*end[en2]*/
import common.TextMessage;

/**
 * simple AWT gui for the chat client
 */
public class CmdClient extends Frame implements ChatLineListener,Runnable {

	private Client chatClient;
	private String title;
	private boolean connectionOpen = true;
	
	public void newChatLine(TextMessage tm) {
		// TODO Auto-generated method stub
		String line = tm.getContent();
		System.out.println("recive from: "+line);
		
		if (line.equals("auth error")) {
			connectionOpen = false;
		}  else if (line.equals("auth suc")){
			/*if[col]*/
			System.out.println("which color do you want?1 Green 2 Red 3 Blue");
			/*end[col]*/
		}
	}
	public CmdClient(String title, Client chatClient) {
		this.chatClient = chatClient;
		this.title = title;
		chatClient.addLineListener(this);
		Thread th = new Thread(this);
		th.start();
	}

	public void run() {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		String line;
		while (connectionOpen) {
			line = sc .nextLine();

			
			/*if[col]*/
			if(line.equals("1")){
				System.out.println("Your word is Green");
			}else if(line.equals("2")){
				System.out.println("Your word is Red");
			}
			/*end[col]*/
			this.chatClient.send(line);
		}
		System.out.println("cmder is exiting listening");
	}

}


/*end[Console]*/