package start;

import java.util.ArrayList;

import server.Server;
import client.Client;







public class MainServer {

	//the num of client to show
	static int CLIENT_NUM = 2;
	
	//the port server will listen 
	static int LISTEN_PORT = 8012;
	
	
	//the HOST server will listen 
	static String LISTEN_HOST = "127.0.0.1";
	/**
	 * start a server and some client for show 
	 * @param args
	 */
	public static void main(String[] args) {
	
		startServer(LISTEN_PORT);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		startClients(CLIENT_NUM, LISTEN_HOST, LISTEN_PORT);
	}
	
	
	/**
	 * start a server 
	 * @param port listening port
	 * @return Server when suc /null when error happened
	 */
	private static void startServer (final int port) {
		
		Thread th = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				try {
					Server s = new Server(port);//
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		th.start();
	}
	
	/**
	 * start some client with gui
	 * @param host server host
	 * @param port server port
	 * @param num how many client
	 */
	private static void startClients (int num, String host, int port) {
		if (num < 0) {
			return;
		}
		Client c;
		for (int i = 0; i < num ; i++) {
		   c = new Client(host, port);
		   


	
		}
		   






	}
}
