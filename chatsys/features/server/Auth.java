package server;

import common.CallBack;
import common.TextMessage;
import common.User;

/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
public class Auth implements CallBack {

	Server server = null;
	public Auth(Server server) {// TODO Auto-generated constructor stub
		this.server = server;
	}

	/**
	 * verify the client 
	 * @param 
	 */
	public void call(Connection connection,TextMessage tm) {
		
		try {
		
			User user= new User(tm);
			//verify
			if (user.verify()) {	
				connection.setCallback(server);
				server.connectTo(connection);
				connection.send(new TextMessage("auth suc"));
				System.out.println("<<Accepted user: " + user.getUserName());
			} else {
				connection.send(new TextMessage("auth error"));
				connection.stop();
				System.out.println("<<user auth error: " + user.getUserName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void remove(Connection connection) {
		// TODO Auto-generated method stub
		
	}
}
