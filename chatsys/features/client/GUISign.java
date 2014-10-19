
		   /*if[GUI]*/
package client;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import common.Base64Encrypt;
import common.TextMessage;

public class GUISign extends Frame implements ChatLineListener, ActionListener{

	protected TextField username;
	protected TextField psd;
	protected Button button;
	private Client chatClient;
	
	public GUISign (Client chatClient){
		super("sign in");
		setLayout(new BorderLayout());
		username = new TextField();
		psd = new TextField();
		add("North", username);
		add("South", psd);
		button = new Button();
		add("East", button);
		button.setLabel("sign in");
		pack();
		setVisible(true);
		this.chatClient = chatClient;
		chatClient.addLineListener(this);
		button.addActionListener(this);
	 }

	public void newChatLine(TextMessage tm) {
		// TODO Auto-generated method stub
		String line = tm.getContent();
		System.out.println(line);
		if (line.equals("auth error")) {
			//chatClient.stop();

		} else if (line.equals("auth suc")){
			Gui gui = new Gui(username.getText(), this.chatClient);
			chatClient = null;
			
		} else {
			return ;
		}
		setVisible(false);
		this.dispose();
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (username.getText().equals("")) {
			return;
		}
		if (psd.getText().equals("")) {
			return;
		}
		this.chatClient.send(username.getText() + ":" + psd.getText());
		
	} 
	
	public boolean handleEvent(Event e) {//
		if ((e.target == this) && (e.id == Event.WINDOW_DESTROY)) {//处理销毁
			if (chatClient != null)
				chatClient.stop();
				setVisible(false);
				System.exit(0);
				return true;
		}
		return super.handleEvent(e);//继承自父类 子类GUI重写的函数
	}
}

/*end[GUI]*/