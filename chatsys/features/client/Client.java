package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
/*if[en1]*/
import common.Base64Encrypt;
/*end[en1]*/
/*if[en2]*/
import common.ReverseEncrypt;
/*end[en2]*/
import common.TextMessage;

/**
 * simple chat client
 */
public class Client implements Runnable { //通信
//	public static void main(String args[]) throws IOException {
//		if (args.length != 2)
//			throw new RuntimeException(">>>Syntax: ChatClient <host> <port>");
//
//		Client client = new Client(args[0], Integer.parseInt(args[1]));
//		new Gui("Chat " + args[0] + ":" + args[1], client);
//	}

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Thread thread;
	
	protected TextMessage tm;
	
	private boolean connectionOpen = true;

	public Client(String host, int port) {
		try {
			System.out.println(">>>Connecting to " + host + " (port " + port+  ")...");
			Socket s = new Socket(host, port);  //建立socket
			this.outputStream = new ObjectOutputStream((s.getOutputStream()));
			this.inputStream = new ObjectInputStream((s.getInputStream()));
			thread = new Thread(this); //建立读，写线程  会run
			thread.start(); //新开的线程，调用run
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * main method. waits for incoming messages.
	 */
	public void run() {
		try {
			while (connectionOpen) {  //此线程一直在读
				try {
					Object msg = inputStream.readObject();//读objcet  common textmsg
					handleIncomingMessage(msg);//处理msg//否则 消息就没了 函数结束
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			thread = null;
			try {
				outputStream.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}


	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message (Object) received from the sockets
	 */
	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			fireAddLine((TextMessage)msg);//所有消息通过handlemsg发出去，handle 根据条件发给fireaddline处理
		}
	}

	public void send(String line) { 
		tm = new TextMessage(line, null, null);
	
		send(tm);
	}

	public void send(TextMessage msg) { //发给server
		try {
			outputStream.writeObject(msg);
			outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
			connectionOpen = false;
		}
	}

	/**
	 * listener-list for the observer pattern
	 */
	private ArrayList listeners = new ArrayList();

	/**
	 * addListner method for the observer pattern
	 */
	public void addLineListener(ChatLineListener listener) {//
		synchronized(listeners){
			listeners.add(listener);
		}
	}
//如果收到
	/**
	 * removeListner method for the observer pattern
	 */
	public void removeLineListener(ChatLineListener listner) {
		synchronized(listeners) {
			listeners.remove(listner);
		}
	}

	/**
	 * fire Listner method for the observer pattern
	 */
	public void  fireAddLine(final TextMessage msg) {//很多listener
		//client 同监听server端消息， new listeners 调用 addnew line 方法
		//lisnter注册在client上监听器，
		//一旦受到 newchatline
		
		//GUI实现这个接口
		
		synchronized(listeners) {
			for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {//
				//listernes是迭代，不断调用newchatline
				//client通过毁掉函数的集合listners,每当收到消息，循环调用newcheatline所有
				//for语句有迭代器，里边有
				final ChatLineListener listener = (ChatLineListener) iterator.next(); //next返回listerns第一个对象
				//lisnter 强制转换 cll上一行
				Thread th = new Thread(new Runnable() {
					
					public void run() {
						// TODO Auto-generated method stub
						listener.newChatLine(msg);//回调函数
					}
				});
				th.start();
				
			}
		}
	}

	public void stop() {
		thread.stop();
	}

}
