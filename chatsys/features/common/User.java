package common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/*if[en1]*/
import common.Base64Encrypt;
/*end[en1]*/
/*if[en2]*/
import common.ReverseEncrypt;
/*end[en2]*/

public class User {

	private String userName = null;
	private String password = null;
	//private Base64Encrypt decrypter = null;
		
	public User(TextMessage token){
		String[] strArr = tokenDecoder(token);
		if (strArr != null || strArr.length == 2) {
			this.userName = strArr[0];
			this.password = strArr[1];
		}
	}
	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	private String[] tokenDecoder(TextMessage token){
		try {
			String de_user_psd = token.getContent();

			String[] user_psd = de_user_psd.split(":");
			return user_psd;
			//如果没有取到，这两个值就是空
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean verify () {
		String[][] db = {{"user1","psd1"}, {"user2","psd2"}, {"user3","psd3"}, {"user4","psd4"}}; 
		for(int i=0; i < db.length; i++) {
			if(db[i][0].equals(this.userName) && db[i][1].equals(this.password)) {
				return true;
			}
		}
		return false;
	}
	
	//保存到数据库
	public void save() {
		
	}
}
