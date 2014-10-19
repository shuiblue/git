package common;

import java.awt.Color;
import java.io.Serializable;


import common.Base64Encrypt;
/*if[en2]*/

import common.ReverseEncrypt;
/*end[en2]*/
import common.TextMessage;

/*if[ProfanityFilter]*/
import common.ProfanityFilter;
/*end[ProfanityFilter]*/




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
		/*if[ProfanityFilter]*/
		content = ProfanityFilter.ProFilter(content);
		/*end[ProfanityFilter]*/
		String a = content;
		
	
		/*if[en1]*/
		encrypter= new Base64Encrypt();
		this.content = encrypter.encrypt(content);
	    a = this.content;
		/*end[en1]*/
		/*if[en2]*/ 
		EncryptHelper encrypter2 =  new ReverseEncrypt();
	    String content2 = encrypter2.encrypt(a);
		this.content = content2;
		/*end[en2]*/
       if (color == null || !(color instanceof Color)) {
			this.rgb = new Color(0, 0, 0).getRGB();
		} else {
			this.rgb = color.getRGB();
		}
	}
	
	public String getContent() {
	       String b ="";
	       a = content;
			/*if[en2]*/
			ReverseEncrypt re = new ReverseEncrypt();
			 a = re.decrypt(content);
			 System.out.println("en2:"+a);
//			encrypter = new Base64Encrypt();	
	       b =  a;
//			 System.out.println("en1:"+b);
			 /*end[en2]*/
			/*if[en1]*/
				encrypter = new Base64Encrypt();
				b =  encrypter.decrypt(a);
//			b =  encrypter.decrypt(content);
				 System.out.println("en1:"+b);
				/*end[en1]*/
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
