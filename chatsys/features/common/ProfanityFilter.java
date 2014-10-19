package common;

import java.util.ArrayList;


public class ProfanityFilter {
	static String newString = "";
	private void ProfanityFilter(){
		
	}
	public static String ProFilter(String content){
		ArrayList<String> ProfanityList = new ArrayList<String>();
		
		if(content.contains("aaa")){
			newString = content.replaceAll("aaa", "***");
		
		}else if(content.contains("bbb")){
			newString = content.replaceAll("bbb", "!!!");
		}else{
			newString = content;
		}
	return	newString;
	}
}