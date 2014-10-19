package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.CallBack;
import common.TextMessage;

/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public class Connection extends Thread {
	protected Socket socket;

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	private CallBack callback;
	
	private boolean connectionOpen = true;

	public Connection(Socket s, CallBack callback) {
		this.socket = s;
		try {
			inputStream = new ObjectInputStream((s.getInputStream())); 
			outputStream = new ObjectOutputStream((s.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.callback = callback;
	}

	
	// 
	/**
	 * waits for incoming messages from the socket
	 */
	public void run() {
		String clientName = socket.getInetAddress().toString();
		try {
			while (connectionOpen) {
				try {
					Object msg = inputStream.readObject();//停在这
					handleIncomingMessage(clientName, msg);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			callback.remove(this);
			try {
				socket.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	private void handleIncomingMessage(String name, Object msg) {
		if (msg instanceof TextMessage)
			callback.call(this, (TextMessage)msg);//回调的对象，
	}

	/**
	 * sends a message to the client
	 * 
	 * @param line
	 *            text of the message
	 */
	public void send(String line) {
		send(new TextMessage(line));
	}

	public void send(TextMessage msg) {
		try {
			synchronized (outputStream) {
				outputStream.writeObject(msg);
				outputStream.flush();
			}
		} catch (IOException ex) {
			connectionOpen = false;
		}
	}
	
	public CallBack getCallback() {
		return callback;
	}

	public void setCallback(CallBack callback) {
		this.callback = callback;
	}

}
