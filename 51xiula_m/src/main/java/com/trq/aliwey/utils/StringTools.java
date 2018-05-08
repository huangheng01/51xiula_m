package com.trq.aliwey.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import sun.misc.BASE64Decoder; 




public class StringTools {
	private static final String allnums = "0123456789";
	//是否为空
	public static boolean isEmptynot(Object obj) {
		if (obj == null || obj.equals("")) {
			return false;
		}
		if(obj.toString().indexOf("null")==0){
			return false;
		}
		return true;
	}
	//是否为不带空空的空
	public static boolean istrimEmptynot(Object obj) {
		if (obj == null || obj.toString().trim().equals("")) {
			return false;
		}

		if(obj.toString().indexOf("null")==0){
			return false;
		}
		return true;
	}
	public static boolean notEmpty(Object obj) {
		if (obj == null || obj.equals("")) {
			return false;
		}

		if(obj.toString().indexOf("null")==0){
			return false;
		}
		return true;
	}

	public static boolean trimNotEmpty(Object obj) {
		if (obj == null || obj.toString().trim().equals("")) {
			return false;
		}

		if(obj.toString().indexOf("null")==0){
			return false;
		}
		return true;
	}
	//是否为不带空空的空
	public static boolean istrimEmpty(Object obj) {
			if (obj == null || obj.toString().trim().equals("")) {
				return true;
			}

			if(obj.toString().indexOf("null")==0){
				return true;
			}
			return false;
	}
	//是否数字
	public static boolean isNumber(Object obj) {
		Pattern pattern = Pattern.compile("^-?[0-9]+"); 
		   Matcher isNum = pattern.matcher(obj.toString());
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
	}
	//是否日期
		public static boolean isDate(Object datastring)
		{
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd"); //制定日期格式
			try {
				 df.parse(datastring.toString());
				 return true;
			} catch (Exception e) {
				return false;
			}  
		}
	//是否为小数
	public static boolean isDouble(Object obj) {
		Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+"); 
		   Matcher isNum = pattern.matcher(obj.toString());
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
	}
	//转换为空
	public static String toEmpty(Object obj) {
		if (obj == null || obj.toString().trim().equals("") || "null".equals(obj.toString().trim())) {
			return "";
		}
		return obj.toString();
	}
	public static String toNull(Object obj) {
		if (obj == null || obj.toString().trim().equals("") || "null".equals(obj.toString().trim())) {
			return "null";
		}
		return "'"+obj+"'";
	}
	//转换为数字
	public static Integer toInteger(String obj) {
		try {
			return Integer.parseInt(obj);
		} catch (Exception e) {
			return 0;
		}
	}
	//转换为数字
	public static Double toDouble(String obj) {
		try {
			return Double.parseDouble(obj);
		} catch (Exception e) {
			return 0.0;
		}
	}
	//小数格式化
	public static String toDoubleString(double obj){
		try {
			if(obj<0.0001){
				return "0.00";
			}
			DecimalFormat df=new DecimalFormat("###,###,###,###.00");
			String bakString=df.format(obj);
			if(obj<1)
			{
				bakString="0"+bakString;
			}
			return bakString;
		} catch (Exception e) {
			return "0.00";
		}
		
	}
	//字符串日期格式化
	public static String toDatestring(String obj) {
			try {
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //制定日期格式
				return df.format(df.parse(obj));
			} catch (Exception e) {
				return GetDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			}
	}
	
	//日期加减 ：1天，2月，3年，4小时，5分钟
	public static String GetDate(Date date,int num,int type)
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd"); //制定日期格式
		Calendar c=Calendar.getInstance();
		
		c.setTime(date);
		switch (type){
			case 1:
				c.add(Calendar.DAY_OF_MONTH,num); //将当前日期加天
				break;
			case 2:
				c.add(Calendar.MONTH,num); //将当前日期加月
				break;
			case 3:
				c.add(Calendar.YEAR,num); //将当前日期加年
				break;
			case 4:
				c.add(Calendar.HOUR_OF_DAY,num);
				break;
			case 5:
				c.add(Calendar.MINUTE,num);
				break;
		}
		
		

		return df.format(c.getTime());  //返回String型的时间
	}
	
	//日期加减 ：1天，2月，3年，4小时，5分钟
	public static Date GetDateNF(Date date,int num,int type){
		
		Calendar c=Calendar.getInstance();
		
		c.setTime(date);
		switch (type){
			case 1:
				c.add(Calendar.DAY_OF_MONTH,num); //将当前日期加天
				break;
			case 2:
				c.add(Calendar.MONTH,num); //将当前日期加月
				break;
			case 3:
				c.add(Calendar.YEAR,num); //将当前日期加年
				break;
			case 4:
				c.add(Calendar.HOUR_OF_DAY,num);
				break;
			case 5:
				c.add(Calendar.MINUTE,num);
				break;
		}
		
		

		return c.getTime();  //返回String型的时间
	}
	
	
	
	//获取当前日期的每月第一天，最后一天等
	public static String GetDateo(Date date,int type)
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd"); //制定日期格式
		Calendar c=Calendar.getInstance();
		
		c.setTime(date);
		switch (type){
			case 1:
				c.set(Calendar.DAY_OF_MONTH, 1);  //月初
				break;
			case 2:
				c.roll(Calendar.DAY_OF_MONTH, -1); //月末 
				break;
			case 3:
				return c.get(Calendar.YEAR)+"-01-01"; //年初
				
			case 4:
				return c.get(Calendar.YEAR)+"-12-31"; //年末
				
			
		}
		
		

		return df.format(c.getTime());  //返回String型的时间
	}
	
	//时间转字符串，输入转换格式
	public static String GetDate(Date date,String formaString)
	{
		SimpleDateFormat df=new SimpleDateFormat(formaString); //制定日期格式
		return df.format(date);  //返回String型的时间
	}
	public static String GetDate(String date,String formaString)
	{
		try {

			SimpleDateFormat df=new SimpleDateFormat(formaString); //制定日期格式
			SimpleDateFormat dfold=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //制定日期格式
			
			return df.format(dfold.parse(date));  //返回String型的时间
		} catch (Exception e) {
			try {

				SimpleDateFormat df=new SimpleDateFormat(formaString); //制定日期格式
				SimpleDateFormat dfold=new SimpleDateFormat("yyyy-MM-dd"); //制定日期格式
				
				return df.format(dfold.parse(date));  //返回String型的时间
			} catch (Exception e1) {

				SimpleDateFormat df=new SimpleDateFormat(formaString); //制定日期格式
				return df.format(new Date());  //返回String型的时间
			}
		}
	}
	public static Date GetDate()
	{
		Date date=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //制定日期格式
		try {
			return df.parse(df.format(date));
		} catch (ParseException e) {
			return date;
		}  
	}
	public static Date GetDate(Object datastring)
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //制定日期格式
		try {
			return df.parse(datastring.toString());
		} catch (Exception e) {
			try {
				 df=new SimpleDateFormat("yyyy-MM-dd"); //制定日期格式
				 return df.parse(datastring.toString());
			} catch (Exception e2) {
				return null;
			}
		}  
	}
	//获取时间戳
	public static long GetDateTs(Date date)
	{
		long bak=date.getTime()/1000;
		return bak;  //返回String型的时间
	}
	public static long GetDateTs(String date)
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //制定日期格式
		long bak=0;
		try {
			bak = df.parse(date).getTime()/1000;
		} catch (ParseException e) {
		}
		return bak;  //返回String型的时间
	}
	
		
	//替换json字符
	public static String String2Json(String s)
	{
		
                   s= s.replaceAll("\"","\\\""); 
                   s= s.replaceAll("\\","\\\\"); 
                   s= s.replaceAll("/","\\/"); 
                   s= s.replaceAll("\b","\\b"); 
                   s= s.replaceAll("\f","\\f"); 
                   s= s.replaceAll("\n","\\n"); 
                   s= s.replaceAll("\r","\\r"); 
                   s= s.replaceAll("\t","\\t"); 
                   s= s.replaceAll("\'","\\\'"); 
              
            
        return s;
	}

	

	//是否手机
	public static boolean isMobile(String str) {
		return str.matches("^(13|14|15|18|17|16|19)\\d{9}$");
	}
	//是否邮箱
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);


		return m.matches();
	}
	
	public static boolean isChina(String str){
		boolean b=str.matches("^[\u4e00-\u9fa5]{0,}$");
		return b;
	}
	//获取随机数字
	public static String getRandNum(int num) {
		String str = "";
		Random rd = new Random();
		int length = allnums.length();
		for (int i = 0; i < num; i++) {
			int a = (rd.nextInt(length));
			str += allnums.charAt(a);
		}
		return str;
	}
	//获取MD5
	public static String getMD5(String s){
		GenerateMD5 generateMD5=new GenerateMD5();
		generateMD5.getMD5().update(s.getBytes());
		byte digest[] = generateMD5.getMD5().digest();
		StringBuilder builder = new StringBuilder();
		for(byte b : digest){
			builder.append(String.format("%02X", b));
		}
		return builder.toString().toLowerCase();
	}
	public static String getMD5two(String s){
		return getMD5(getMD5(s)).toLowerCase();
	}
	
	
	
	
	//浮点运算
	public static Double roundDouble(double val, int precision) {
		Double ret = null;
		try {
			double factor = Math.pow(10, precision);
			ret = Math.floor(val * factor + 0.5) / factor;
		} catch (Exception e) {
			return 0.0;
		}
		return ret;
	} 
	
	
	
	

	
	//获取客户IP
	public static String getIpAddr(HttpServletRequest request) {  
		try {
			String ipAddress = null;    
		    //ipAddress = this.getRequest().getRemoteAddr();    
		    ipAddress = request.getHeader("x-forwarded-for");    
		    if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {    
		    	ipAddress = request.getHeader("Proxy-Client-IP");    
		    }   
		    if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {    
		        ipAddress = request.getHeader("WL-Proxy-Client-IP");    
		    }   
		    if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {    
		        ipAddress = request.getHeader("X-Real-IP");   
		    }
		    if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {    
		    	ipAddress = request.getRemoteAddr();    
		    	if(ipAddress.equals("127.0.0.1")){    
		       //根据网卡取本机配置的IP    
		    		InetAddress inet=null;    
		    		try {    
		    			inet = InetAddress.getLocalHost();   
		    		} catch (UnknownHostException e) {    
		    			 
		    		}   
		    		ipAddress= inet.getHostAddress();   
		    	}   
		            
		     }   
		  
		     //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割    
		     if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15    
		         if(ipAddress.indexOf(",")>0){    
		             ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));    
		         }   
		     }   
		     return ipAddress;  
		} catch (Exception e) {
			return "";
		}
		       
	}
	
	//理财产品收益计算
	//type 2来活期，3新手理财，1来定存，4，积蓄宝
	public static String GetGiveMeMoney(int type,double buymoney,double rate,int cycle,int fbtype)
	{
		String bak="0";
		
		if(type==1)
		{
			if(fbtype==2)
			{
				bak=String.format("%.2f", (buymoney* ((rate/100)) * cycle / 12)+buymoney);
			}
			if(fbtype==1)
			{
				double fbrate=rate/100/12;
				bak=String.format("%.2f", (buymoney* ((fbrate*Math.pow(1+fbrate, cycle))/(Math.pow(1+fbrate, cycle)-1))));
			}
		}
		if(type==2 || type==4)
		{
			bak=String.format("%.2f", (buymoney* (rate/100)) / 360);
		}
		if(type==3)
		{
			bak=String.format("%.2f", (buymoney* ((rate/100)) / 12)+buymoney);
		}
		return bak;
	}
	//IP获得城市
	public static String GetIptoArea(String urlstr){
		try {
			StringBuffer content=new StringBuffer();
			HttpURLConnection httpConn=null;
			BufferedReader in=null;
			try {
				URL url=new URL(urlstr);
				httpConn=(HttpURLConnection)url.openConnection();

				//读取响应
				if(httpConn.getResponseCode()==HttpURLConnection.HTTP_OK){
					
					String tempStr="";
					in=new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"utf-8"));
					while((tempStr=in.readLine())!=null){
						content.append(tempStr);
					}	
				}
			} catch (Exception e) {
			
			}finally{
				try {
					in.close();
				} catch (Exception e) {
		
				}
				httpConn.disconnect();
			}
			return content.toString();
		} catch (Exception e) {
			return null;
		}
	}
	//转换成中文
	 public   static   String   toUnicodeGB(String   s)   { 
		 try {
			 StringBuffer   sb   =   new   StringBuffer();      
	         StringTokenizer   st   =   new   StringTokenizer(s,   "\\u");      
	         while   (st.hasMoreTokens())   {      
	             sb.append(   (char)   Integer.parseInt(st.nextToken(),   16));      
	         }      
	         return   sb.toString();  
		} catch (Exception e) {
			return s;
		}
             
     }  

	 
	 
	 
	 /**
	 * Base64编码
	 */
	 public static String GetBase64encode(String str) {  
	     try {
			return new sun.misc.BASE64Encoder().encode(str.getBytes("utf-8"));
		} catch (Exception e) {
			return null;
		}  
	 } 
	 /**
	  * Base64编码
	  */
	public static String GetBase64encode(byte[] str) {  
	     try {
			return new sun.misc.BASE64Encoder().encode(str);
		} catch (Exception e) {
			return null;
		}  
	 }
	 //
	 
	 public static String GetBase64decode(String str) {  
	      
	     try {  
	    	 byte[] bt = null; 
	    	 sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();  
	     	 bt = decoder.decodeBuffer(str);  
	     	 return new String(bt,"utf-8");
	     } catch (IOException e) {  
	    	 return null;
	     }  
	        
	 }
	 /**
	  * Base64解码
	  */
	 public static byte[] GetBase64decodetobyte(String str) {  
	      
	     try {  
	    	 byte[] bt = null; 
	    	 sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();  
	     	 bt = decoder.decodeBuffer(str);  
	     	 return bt;
	     } catch (IOException e) {  
	    	 return null;
	     }  
	        
	 }
	
	 
	 /**
	  * 加密
	 * @param datasource byte[]
	  * @param password String
	  * @return byte[]
	  */
	  public static String GetDesEncrypt (String datasource) { 
		  try{
			  SecureRandom random = new SecureRandom();
			  String password="A2l0C1j5";//getMD5(GetDate(new Date(), 1, 1)).substring(0,8);
			  DESKeySpec desKey = new DESKeySpec(password.getBytes());
			  //创建一个密匙工厂，然后用它把DESKeySpec转换成
			 SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			  SecretKey securekey = keyFactory.generateSecret(desKey);
			  //Cipher对象实际完成加密操作
			 Cipher cipher = Cipher.getInstance("DES");
			  //用密匙初始化Cipher对象
			 cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			  //现在，获取数据并加密
			 //正式执行加密操作
			 byte[] batejm=cipher.doFinal(datasource.getBytes());
			 String jm= new String(batejm);
			 return GetBase64encode(batejm);
		  }catch(Exception e){
			  
		  }
		return null;
	  }
	  
	  /**
	  * 解密
	 * @param src byte[]
	  * @param password String
	  * @return byte[]
	  * @throws Exception
	  */
	  public static String GetDesDecrypt(String src)  {
		  try {
			// DES算法要求有一个可信任的随机数源
				 SecureRandom random = new SecureRandom();

				  String password="A2l0C1j5";//getMD5(GetDate(new Date(), 1, 1)).substring(0,8);
				  // 创建一个DESKeySpec对象
				 DESKeySpec desKey = new DESKeySpec(password.getBytes());
				  // 创建一个密匙工厂
				 SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
				  // 将DESKeySpec对象转换成SecretKey对象
				 SecretKey securekey = keyFactory.generateSecret(desKey);
				  // Cipher对象实际完成解密操作
				 Cipher cipher = Cipher.getInstance("DES");
				  // 用密匙初始化Cipher对象
				 cipher.init(Cipher.DECRYPT_MODE, securekey, random);
				  // 真正开始解密操作
				 return new String(cipher.doFinal(GetBase64decodetobyte(src)));
		  } catch (Exception e) {
			  return null;
		  }
	  }


	  
	  
	  
	/**
	 * 读取配置  
	 * @param filename 文件名 不需要后缀
	 * @param key key值
	 * @return 
	 */
	public static String getProperties(String filename,String key){
			
		String value="";
		
		if(null==filename || filename.length()<1)
			return value;
		
		if(null==key || key.length()<1)
			return value;
		
		try{
			Properties props=PropertiesLoaderUtils.loadAllProperties(filename+".properties"); 
			if(null==props)
				return value;
			
			value=props.getProperty(key);
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		return value;
	}
	
	
	/**
	 * 生成某长度随机数字字母串
	 * @param random
	 * @param len
	 * @return
	 */
	public static String getRandomStringByLength(Random random,int len){
		
		if(len<1 || len>100){
			return "";
		}
		
		String r="";
		for(int i=0;i<len;i++){
			int t=random.nextInt(2);
		
			if(t==0){
				r=r+random.nextInt(10);				
			}else{
				char c=(char)(random.nextInt(26)+65);
				r=r+c;
			}
		}
		return r.toLowerCase();
	}
	
	/**
	 * 生成某长度随机数字字母串
	 * @param random
	 * @param len
	 * @param numcount 包含数字个数
	 * @return
	 */
	public static String getRandomStringByLength(Random random,int len,int numcount){
		
		if(len<1 || len>100){
			return "";
		}
		
		String r="";
		
		int nc=0;
		int lc=0;
		for(int i=0;i<len;i++){
			int t=random.nextInt(2);
		
			if(t==0 && nc<numcount){
				r=r+random.nextInt(10);	
				nc=nc+1;
			}else{
				
				if(lc<(len-numcount)){
					char c=(char)(random.nextInt(26)+65);
					r=r+c;
					lc=lc+1;
				}else{
					r=r+random.nextInt(10);	
					nc=nc+1;
				}
			}
		}
		return r.toLowerCase();
	}
	
	/**
	 * 生成某长度随机数字串
	 * @param random
	 * @param len
	 * @return
	 */
	public static String getRandomNumberByLength(Random random,int len){
		
		if(len<1 || len>100){
			return "";
		}
		
		String r="";
		for(int i=0;i<len;i++){
			
			r=r+random.nextInt(10);				
			
		}
		return r.toLowerCase();
	}
	
	/**
	 * 二维码解析
	 * @param file
	 * @return
	 */
	public static String parseQR_CODEImage(String base64Str){ 
		
        try{  
            MultiFormatReader formatReader = new MultiFormatReader();  
            
            byte[] bytes =base64Stream(base64Str);  
            InputStream in_withcode =  new ByteArrayInputStream(bytes); 
   
            BufferedImage image =  ImageIO.read(in_withcode);  
   
            LuminanceSource source = new BufferedImageLuminanceSource(image);  
            Binarizer binarizer = new HybridBinarizer(source);  
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);  
   
            Map hints = new HashMap();  
            
            Vector<BarcodeFormat> decodeFormats = new Vector<BarcodeFormat>();  
            if (decodeFormats == null || decodeFormats.isEmpty()) {  
                decodeFormats = new Vector<BarcodeFormat>();  
              
                // 这里设置可扫描的类型，我这里选择了都支持  
                decodeFormats.add(BarcodeFormat.QR_CODE);  
                decodeFormats.add(BarcodeFormat.DATA_MATRIX);
            }
            hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
            
            
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
   
            Result result = formatReader.decode(binaryBitmap, hints);
            
            return result.toString();
        }catch (Exception e){  
            e.printStackTrace(); 
            return "";
        }  
    }
	
	public static byte[] base64Stream(String imgStr){  
	      
    	BASE64Decoder decoder = new BASE64Decoder();  
    	byte[] bytes = null;  
        try {  
            // Base64解码  
            bytes = decoder.decodeBuffer(imgStr);  
            for (int i = 0; i < bytes.length; ++i) {  
                if (bytes[i] < 0) {// 调整异常数据  
                    bytes[i] += 256;  
                }  
             }  
        }catch(Exception e){  
              
        }  
        return bytes;
	      
	  }
	  
	  
}
