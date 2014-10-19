
package common;

import java.io.Serializable;

import com.sun.xml.internal.messaging.saaj.util.Base64;

public class Base64Encrypt implements EncryptHelper,Serializable {

	public String encrypt(String input) {
		// TODO Auto-generated method stub
		String encrypt = null; 
		encrypt = new String(Base64.encode(input.getBytes())); 
		System.out.println("64:"+encrypt);
		return encrypt; 
	}

	
	public String decrypt(String input) {
		// TODO Auto-generated method stub
		String descryptedData = null; 

		descryptedData = Base64.base64Decode(input); 
		System.out.println("64:"+descryptedData);
		return descryptedData; 
	}

}
