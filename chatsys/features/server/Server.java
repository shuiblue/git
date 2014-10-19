package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;

import common.CallBack;
import common.TextMessage;
import common.User;

/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
public class Server implements CallBack {

//	public static void main(String args[]) throws IOException {
//		if (args.length != 1)
//			throw new RuntimeException("Syntax: ChatServer <port>");
//		new Server(Integer.parseInt(args[0]));
//	}

	/**
	 * awaits incoming connections and creates Connection objects accordingly.
	 * 
	 * @param port
	 *            port to listen on
	 */
	public Server(int port) throws IOException {
		ServerSocket server = new ServerSocket(port);
		while (true) {
			System.out.println("<<Waiting for Connections...");
			Socket client = server.accept();
			System.out.println("<<Accepted from " + client.getInetAddress());
			verify(client);
		}
	}

	/**
	 * creates a new connection for a socket
	 * 
	 * @param socket
	 *            socket
	 * @return the Connection object that handles all further communication with
	 *         this socket
	 */
	public Connection connectTo(Connection connection) {
		connections.add(connection);
		return connection;
	}

	/**
	 * list of all known connections
	 */
	protected HashSet connections = new HashSet();

	/**
	 * send a message to all known connections
	 * 
	 * @param text
	 *            content of the message
	 */
	public void call(Connection conn, TextMessage tm) {
		synchronized (connections) {
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				connection.send(tm);
			}
		}
	}

	/**
	 * remove a connection so that broadcasts are no longer sent there.
	 * 
	 * @param connection
	 *            connection to remove
	 */
	public void remove(Connection connection) {
		connections.remove(connection);
	}
	
	
		

	/**
	 * verify the client 
	 * @param s
	 */
	public void verify(Socket s) {
		Auth auth = new Auth(this);
		Connection connection = new Connection(s, auth);
		connection.start();
	}

}
