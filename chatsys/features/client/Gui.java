/*if[GUI]*/
		  
package client;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Event;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.util.Random;

import client.GUISign;

import sun.security.krb5.EncryptedData;
import common.Base64Encrypt;
/*if[en2]*/
import common.ReverseEncrypt;
/*end[en2]*/
import common.TextMessage;

/**
 * simple AWT gui for the chat client
 */
public class Gui extends Frame implements ChatLineListener {

	private static final long serialVersionUID = 1L;

	protected TextArea outputTextbox;

	protected TextField inputField;

	private Client chatClient;
	
	private TextMessage tm;

	Choice ColorChooser = new Choice();//choose color
	

	/**
	 * creates layout
	 * 
	 * @param title
	 *            title of the window
	 * @param chatClient
	 *            chatClient that is used for sending and receiving messages
	 */
	public Gui(String title, Client chatClient) {
		super(title);
		System.out.println("starting gui...");
		setLayout(new BorderLayout());
		outputTextbox = new TextArea();
		add("Center", outputTextbox);
		outputTextbox.setEditable(false);
		inputField = new TextField();
		add("South", inputField);
		/*if[col]*/
		add("East",ColorChooser);
		 ColorChooser.add("Green");
		 ColorChooser.add("Red");
		 ColorChooser.add("Blue");
		 outputTextbox.setBackground(Color.black);
		 /*end[col]*/
		

		// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		chatClient.addLineListener(this);
	
	

		pack();
		setVisible(true);
		inputField.requestFocus();

		this.chatClient = chatClient;
//		sendUserInfo();
		
		
		
		
	}
//	//send user info to verify
//	public void sendUserInfo () {
//		chatClient.send("client1 111"); //发用户名密码
		
		//chatClient.send(this.getTitle());
		
//	}
	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	public void newChatLine(TextMessage tm) {
		String line = tm.getContent();
		
		outputTextbox.setForeground(tm.getColor());//把消息颜色取出来，
		
		
		
		
		outputTextbox.append(line + "\n");
	}

	/**
	 * handles AWT events (enter in textfield and closing window)
	 */
	public boolean handleEvent(Event e) {//
		if ((e.target == inputField) && (e.id == Event.ACTION_EVENT)) {
//			/*if[en1]*/
//			Base64Encrypt encrypter = new Base64Encrypt();
//			/*end[en1]*/
//			
//			/*if[en2]*/
//			ReverseEncrypt encrypter = new ReverseEncrypt();
//			/*end[en2]*/
//			
			
			
			/*if[col]*/	
			String s_color = ColorChooser.getSelectedItem();
			if (s_color.equals("Blue")){
				tm = new TextMessage(this.getTitle() + " : " + (String) e.arg, null, Color.blue);
			}else if  (s_color.equals("Red")){
				tm = new TextMessage(this.getTitle() + " : " + (String) e.arg, null, Color.red);	
			}else{
				tm = new TextMessage(this.getTitle() + " : " + (String) e.arg, null, Color.green);	
			}
			
			/*end[col]*/
			chatClient.send(tm);//
			inputField.setText("");
			return true;
		} else if ((e.target == this) && (e.id == Event.WINDOW_DESTROY)) {//处理销毁
			if (chatClient != null)
				chatClient.stop();
			setVisible(false);
			this.dispose();
			return true;
		}
		return super.handleEvent(e);//继承自父类 子类GUI重写的函数
	}
}

/*end[GUI]*/