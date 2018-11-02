package com.sweet.api.util;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证类
 * @author wangsai
 */
public class ValidateUtil {

	//正则表达式，匹配字符中是否含有特殊的字符
	public static boolean compare(String str){
		String regEx = "[`~!@#$%^&*()+=|{}:;\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}
	
	/**
	 * 计算字符串的字节总数：
	 *  不分编码格式，一个中文视为占2个字节，其他非中文字符统一视为占一个字节
	 * @param str
	 * @return
	 */
	public static int getByteTotal(String str){
		int amount=0;
		int total=0;
		for (int i = 0; i < str.length(); i++) {			
			 boolean matches = Pattern.matches("^[\u4E00-\u9FA5]{0,}$", ""+ str.charAt(i));
			 if (matches) {// 如果是汉字
	                amount++;// 累加计数器
	            }else{
	            	total++;
	            }
		}
		total=total+amount*2;
		return total;
	}
	
	/**
	 * get the sign time. 获取当前签名时间
	 * 
	 */
	public static String getSignDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());
		return format.format(new Date());
	}
	
	/**
	 * 随机生成指定长度的名字
	 * @param length
	 * @return
	 */
	public static String getUserName(int length){
		Random randGen = new Random();
		final String firstName = "YG";
		String userName = "";
		char str[] ={'A','B','C','D','E','F','G','H','R','G','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','r','g','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		char [] randBuffer = new char[length];
		if (length < 1) {
            return null;
        }
        for (int i=0; i<length; i++) {
            randBuffer[i] = str[randGen.nextInt(52)];
        }
        userName = firstName + new String(randBuffer);
		return userName;
	}
	
	/**
	 * 截取重新拼接用户名
	 * @param name
	 * @return
	 */
	public static String getUserName(String name){
		
		if(StringUtils.isBlank(name)){
			return null;
		}
		String userName = "";
//		StringBuffer userName = new StringBuffer();
//		String firstName = "";
//		String midName = "***";
//		String lastName = "";
//		firstName = name.substring(0,4);
//		lastName = name.substring(name.length()-10,name.length());
//		userName.append(firstName).append(midName).append(lastName);
		userName = name.substring(name.length()-10,name.length());
		return userName.toString();
	}
	
	/**
	 * 隐藏email部分信息
	 * @param email
	 * @return
	 */
	public static String getEmail(String email){
		
		if(StringUtils.isBlank(email)){
			return null;
		}
		StringBuffer userName = new StringBuffer();
		String headerName = "YG";
		String firstName = "";
		String midName = "***";
		String lastName = "";
		if(email.indexOf("@") > 0){
			String[] emailFirst = email.split("@");
			if(emailFirst[0].length() > 3){
				firstName = emailFirst[0].substring(0,3);
			}else{
				firstName = emailFirst[0];
			}
			lastName = email.substring(email.indexOf("@"));
			userName.append(firstName).append(midName).append(lastName);
		}else{
			firstName = email.substring(0,3);
			lastName = email.substring(email.length()-4);
			userName.append(headerName).append(firstName).append(midName).append(lastName);
		}
		return userName.toString();
	}
	
}
