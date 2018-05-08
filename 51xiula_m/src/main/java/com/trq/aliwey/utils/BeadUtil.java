package com.trq.aliwey.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;

public class BeadUtil {

	/**
	 * ��¼�û�session
	 */
	public static String LoginUserKey = "login_user_key";
	public static String CacheList = "CacheList";
	
	 public static String getIpAddr(HttpServletRequest request) {
	       String ip = request.getHeader("x-forwarded-for");
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("WL-Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getRemoteAddr();
	       }
	       return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	       //return ip;
	   }
	 /**
		 * ���ip��ȡ���ڵ���
		 * @param ip
		 * @return
		 */
		public static String getAreaFromIp(String ip){
			String res="";
			try{
				res=Jsoup.connect("http://ip.taobao.com/service/getIpInfo.php?ip="+ip).timeout(5000).post().text();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return res;
			
		}
		public static String viliDate(Date dataStr){
			 java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
			// Date date = new Date();
			 String str = sdf.format(dataStr);
			 return str;
		 }
		public static String getCharAndNumr(int length) {
			  String val = "";
			  Random random = new Random();
			  for (int i = 0; i < length; i++) {
			   // �����ĸ��������
			   String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; 
			   // �ַ�
			   if ("char".equalsIgnoreCase(charOrNum)) {
			    
			    int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; 
			    val += (char) (choice + random.nextInt(26));
			   } else if ("num".equalsIgnoreCase(charOrNum)) { // ����
			    val += String.valueOf(random.nextInt(10));
			   }
			  }
			  return val;
		}
		public static boolean isBlank(String str) {
			if (str == null || (str.trim()).equals(""))
				return true;
			return false;
		}
		/**
		 * <pre>
		 *  
		 * [�� Ҫ] getSecurityCode�Ķ���
		 * 
		 * [�� ϸ] �����֤�� ��ʽ 14255456
		 */
		public static String getNumberCode(int number) {
			String str = "";
			String[] strArr = { "3", "4", "5", "6", "7", "8", "9", "0", "1", "2" };
			for (int i = 1; i < number; i++) {
				Random r = new Random();
				int index = r.nextInt(strArr.length);
				str += strArr[index];
			}
			return str;
		}
		/**
		 * ��Date�������ת��Ϊ�ض��ĸ�ʽ, ���ʽΪnull, ��ʹ��ȱʡ��ʽyyyy-MM-dd.
		 * 
		 * @param Date
		 *            day ����
		 * @param String
		 *            toPattern Ҫת���ɵ����ڸ�ʽ
		 * @return String ���������ַ�
		 */
		public static String formatDates(Date day, String toPattern) {
			String date = null;
			if (day != null) {
				try {
					SimpleDateFormat formatter = null;
					if (toPattern != null)
						formatter = new SimpleDateFormat(toPattern);
					else
						formatter = new SimpleDateFormat("yyyy-MM-dd");
					date = formatter.format(day);
				} catch (Exception e) {
					System.out.println(e);
					return null;
				}
				return date;
			} else
				return null;
		}
}
