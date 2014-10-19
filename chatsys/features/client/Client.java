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
public class Client implements Runnable { //ͨ��
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
			Socket s = new Socket(host, port);  //����socket
			this.outputStream = new ObjectOutputStream((s.getOutputStream()));
			this.inputStream = new ObjectInputStream((s.getInputStream()));
			thread = new Thread(this); //��������д�߳�  ��run
			thread.start(); //�¿����̣߳�����run
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * main method. waits for incoming messages.
	 */
	public void run() {
		try {
			while (connectionOpen) {  //���߳�һֱ�ڶ�
				try {
					Object msg = inputStream.readObject();//��objcet  common textmsg
					handleIncomingMessage(msg);//����msg//���� ��Ϣ��û�� ��������
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
			fireAddLine((TextMessage)msg);//������Ϣͨ��handlemsg����ȥ��handle ������������fireaddline����
		}
	}

	public void send(String line) { 
		tm = new TextMessage(line, null, null);
	
		send(tm);
	}

	public void send(TextMessage msg) { //����server
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
//����յ�
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
	public void  fireAddLine(final TextMessage msg) {//�ܶ�listener
		//client ͬ����server����Ϣ�� new listeners ���� addnew line ����
		//lisnterע����client�ϼ�������
		//һ���ܵ� newchatline
		
		//GUIʵ������ӿ�
		
		synchronized(listeners) {
			for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {//
				//listernes�ǵ��������ϵ���newchatline
				//clientͨ���ٵ������ļ���listners,ÿ���յ���Ϣ��ѭ������newcheatline����
				//for����е������������
				final ChatLineListener listener = (ChatLineListener) iterator.next(); //next����listerns��һ������
				//lisnter ǿ��ת�� cll��һ��
				Thread th = new Thread(new Runnable() {
					
					public void run() {
						// TODO Auto-generated method stub
						listener.newChatLine(msg);//�ص�����
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
