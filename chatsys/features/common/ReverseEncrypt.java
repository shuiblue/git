/*if[en2]*/
			
package common;

import java.io.Serializable;

import common.ReverseEncrypt;

public class ReverseEncrypt implements EncryptHelper,Serializable {


	private static final long serialVersionUID = 1L;

public String reverse(char[] value){
		for (int i = (value.length - 1) >> 1; i >= 0; i--){
		char temp = value[i];
		value[i] = value[value.length - 1 - i];
		value[value.length - 1 - i] = temp;
	}
			 return new String(value);
	}

public String encrypt(String input) {
	String a = reverse(input.toCharArray());
	
System.out.println("re:"+a);
	return	a;
	 
}

public String decrypt(String input) {
String a = reverse(input.toCharArray());
	
System.out.println("re:"+a);
	return	a;
}
	
	
}

/*end[en2]*/