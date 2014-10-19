package common;

import java.awt.Color;
import java.io.Serializable;


import common.Base64Encrypt;




import common.TextMessage;
import common.ProfanityFilter;



/**
 * serializable message that can be send over the sockets between client and
 * server. 
 */
public class TextMessage implements Serializable {



	private static final long serialVersionUID = -9161595018411902079L;
	private String content;
	private EncryptHelper encrypter = null;
	private int rgb;
	
	public TextMessage(String content, EncryptHelper encrypter, Color color) {
		super();
		content = ProfanityFilter.ProFilter(content);
		String a = content;
		
	
		




		




       if (color == null || !(color instanceof Color)) {
			this.rgb = new Color(0, 0, 0).getRGB();
		} else {
			this.rgb = color.getRGB();
		}
	}
	
	public String getContent() {
	       String b ="";
	       a = content;
			







			





			return b;
		}


	public EncryptHelper getEncrypter() {
		return encrypter;
	}

	String a = "";

	
	public TextMessage(String content) {
		this(content, null, null);
	}

	
	public Color getColor() {
		return new Color(this.rgb);
	}
}
