package com.trq.aliwey.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;

import com.jranuan.ext.kit.TimeKit;
import com.jranuan.kit.HttpKit;
import com.jranuan.kit.StrKit;

public class StringUtil {
	
	/**
	 * 手机号
	 */
	public static final String PHONE = "^1[34578]\\d{9}$";
	
	/**
	 *
	 * 编译传入正则表达式和字符串去匹配,忽略大小写
	 * @param regex
	 * @param beTestString
	 * @return
	 */
	public static boolean match(String regex, String beTestString) {
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(beTestString);
		return matcher.matches();
	}
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static String join(String[] array, String sep) {
		if (array == null) {
			return null;
		}
		if (array.length == 0) {
			return "";
		}
		if (sep == null) {
			sep = "";
		}
		// 预处理，得到长度。
		int capacity = 0;
		for (int i = 0; i < array.length; i++) {
			array[i] = String.valueOf(array[i]);
			capacity = capacity + array[i].length() + sep.length();
		}
		char[] result = new char[capacity - sep.length()];
		int begin = 0;
		for (int i = 0; i < array.length; i++) {
			array[i].getChars(0, array[i].length(), result, begin);
			begin = begin + array[i].length();
			if (begin == result.length) {
				break;
			}
			sep.getChars(0, sep.length(), result, begin);
			begin = begin + sep.length();
		}
		return new String(result);
	}

	public static Integer parseInteger(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			// e.printStackTrace();
			// System.out.println("转换int抛出异常");
			return null;
		}
	}

	public static Integer[] parseIntegerArray(String[] values) {
		try {
			Integer[] result = new Integer[values.length];
			for (int i = 0; i < values.length; i++) {
				result[i] = Integer.parseInt(values[i]);
			}
			return result;
		} catch (Exception e) {
			// System.out.println("转换Long抛出异常");
			return null;

		}
	}

	public static Double parseDouble(String value) {
		try {
			return Double.parseDouble(value);
		} catch (Exception e) {
			// System.out.println("转换Double抛出异常");
			return null;

		}
	}

	public static Long parseLong(String value) {
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
			// System.out.println("转换Long抛出异常");
			return null;

		}
	}

	public static Long[] parseLongArray(String[] values) {
		try {
			Long[] result = new Long[values.length];
			for (int i = 0; i < values.length; i++) {
				result[i] = Long.parseLong(values[i]);
			}
			return result;
		} catch (Exception e) {
			// System.out.println("转换Long抛出异常");
			return null;

		}
	}

	public static String transform(String content) {
		if (content == null)
			return null;
		content = content.replaceAll("&", "&amp;");
		content = content.replaceAll("<", "&lt;");
		content = content.replaceAll(" ", "&nbsp;");
		content = content.replaceAll(">", "&gt;");
		content = content.replaceAll("\n", "<br>");
		// content = content.replaceAll("\r\n", "<br>");
		return content;
	}

	public static String escape(String content) {
		if (GenericValidator.isBlankOrNull(content)) {
			return content;
		} else {
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = new BufferedReader(new StringReader(content));
			try {
				String s;
				while ((s = reader.readLine()) != null) {
					sb.append(s).append("\\n");
				}
				sb.delete(sb.lastIndexOf("\\n"), sb.length());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return sb.toString();
		}
	}

	public static String toString(Object o) {
		return (o == null) ? "" : o.toString();
	}

	// 截取字符串
	public static String Sub(String str, int place, int len) {
		String subStr = "";
		if (str.length() >= len) {
			// 截取字符串前X位
			if (place == 1) {
				subStr = str.substring(0, len);
			}
			// 截取字符串后X位
			if (place == 2) {
				subStr = str.substring(str.length() - len, str.length());
			}
		}
		return subStr;
	}

	/**
	 * 格式化金额
	 * 
	 * @param s
	 * @param len
	 * @return
	 */
	public static String formatMoney(String s, int len) {
		if (s == null || s.length() < 1) {
			return "";
		}
		NumberFormat formater = null;
		double num = Double.parseDouble(s);
		if (len == 0) {
			formater = new DecimalFormat("###,###");

		} else {
			StringBuffer buff = new StringBuffer();
			buff.append("###,###.");
			for (int i = 0; i < len; i++) {
				buff.append("#");
			}
			formater = new DecimalFormat(buff.toString());
		}
		String result = formater.format(num);
		if (result.indexOf(".") == -1) {
			result = result + ".00";
		}
		return result;
	}
	
	//四舍五入
	public static double rounding(double f){
		double f1 = 0.00;
		if (f > 0) {
			BigDecimal b = new BigDecimal(f);
			f1 = b.setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		}
		return f1;
	}
	
	//隐藏手机号中间4位
	public static String encodePhone(String phone) {
		if (StrKit.isBlank(phone)) {
			return "";
		}
		if (match(PHONE, phone)) {
			String begin = phone.substring(0, 3);
			String end   = phone.substring(7, phone.length());
			return begin + "****" + end;
		}
		return phone;
	}
	
	//获取订单号
	public static String getOrderNumber(String prefix) {
		String number = TimeKit.getDateTimeString(new Date(), "yyyyMMddHHmmss");
		if(StrKit.notBlank(prefix)){
			number = prefix + number + random(5, RandomType.INT);
		} else {
			number = number + random(5, RandomType.INT);
		}
		return number;
	}
	
	// 随机字符串
	private static final String _INT = "0123456789";
	private static final String _STR_U = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String _STR_L = "abcdefghijklmnopqrstuvwxyz";
	private static final String _STR = _STR_U + _STR_L;
	private static final String _ALL = _INT + _STR_U + _STR_L;

	private static final Random RANDOM = new Random();

	/**
	 * 生成的随机数类型
	 * 
	 * @author L.cm
	 * @email: 596392912@qq.com
	 * @site: http://www.dreamlu.net
	 * @date 2015年4月20日下午9:15:23
	 */
	public static enum RandomType {
		INT, STRING, LOWER, UPPER, ALL;
	}

	/**
	 * 随机数生成
	 * 
	 * @param count
	 * @return
	 */
	public static String random(int count, RandomType randomType) {
		if (count == 0)
			return "";
		if (count < 0) {
			throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
		}
		char[] buffer = new char[count];
		for (int i = 0; i < count; i++) {
			if (randomType.equals(RandomType.INT)) {
				buffer[i] = _INT.charAt(RANDOM.nextInt(_INT.length()));
			} else if (randomType.equals(RandomType.STRING)) {
				buffer[i] = _STR.charAt(RANDOM.nextInt(_STR.length()));
			} else if (randomType.equals(RandomType.UPPER)) {
				buffer[i] = _STR.charAt(RANDOM.nextInt(_STR_U.length()));
			} else if (randomType.equals(RandomType.LOWER)) {
				buffer[i] = _STR.charAt(RANDOM.nextInt(_STR_L.length()));
			} else {
				buffer[i] = _ALL.charAt(RANDOM.nextInt(_ALL.length()));
			}
		}
		return new String(buffer);
	}
	
	/**
	 * 
	 * <pre>
	 * [方法] getRemoteIP的定义
	 * 
	 * [说明] 获取客户端远程IP地址
	 *
	 * @author tan.p
	 * @date 2016年1月15日 下午12:11:00
	 * </pre>
	 */
	public static String getRemoteIP(HttpServletRequest request) {
		try {
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("X-Real-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				// 获取的IP实际上是代理服务器的地址，并不是客户端的IP地址
				ip = request.getRemoteAddr();
				if (ip.equals("127.0.0.1")) {
					// 根据网卡取本机配置的IP
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {

					}
					ip = inet.getHostAddress();
				}
			}
			/*
			 * 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
			 * X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
			 * 192.168.1.100 用户真实IP为： 192.168.1.110
			 */
			if (ip.contains(",")) {
				ip = ip.split(",")[0];
			}
			return ip;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}
	
	/**
	 * 
	 * <pre>
	 * [方法] getAddresses的定义
	 * 
	 * [说明] 根据ip地址获取所在地址
	 *
	 * @author tan.p
	 * @date 2016年1月18日 下午3:46:21
	 * </pre>
	 */
	public static String getAddresses(String content) {
		try {
			// 淘宝IP地址库 http://ip.taobao.com/service/getIpInfo.php?ip=[ip地址字串]
			// 新浪IP地址库 http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=218.192.3.42
			// 腾讯IP地址库 http://ip.qq.com/cgi-bin/searchip?searchip1=218.192.3.42
			String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
			String returnStr = HttpKit.post(urlStr, "ip=" + content);
			if (returnStr != null) {
				// 处理返回的省市区信息
				String[] temp = returnStr.split(",");
				if (temp.length < 3) {
					return "0";// 无效IP，局域网测试
				}
				String country = "";
				String area = "";
				String region = "";
				String city = "";
				String county = "";
				String isp = "";
				for (int i = 0; i < temp.length; i++) {
					switch (i) {
					case 1:
						country = (temp[i].split(":"))[2].replaceAll("\"", "");
						country = EscapeUtil.decodeUnicode(country);// 国家
						break;
					case 3:
						area = (temp[i].split(":"))[1].replaceAll("\"", "");
						area = EscapeUtil.decodeUnicode(area);// 地区
						break;
					case 5:
						region = (temp[i].split(":"))[1].replaceAll("\"", "");
						region = EscapeUtil.decodeUnicode(region);// 省份
						break;
					case 7:
						city = (temp[i].split(":"))[1].replaceAll("\"", "");
						city = EscapeUtil.decodeUnicode(city);// 市区
						break;
					case 9:
						county = (temp[i].split(":"))[1].replaceAll("\"", "");
						county = EscapeUtil.decodeUnicode(county);// 地区
						break;
					case 11:
						isp = (temp[i].split(":"))[1].replaceAll("\"", "");
						isp = EscapeUtil.decodeUnicode(isp);// ISP公司
						break;
					}
				}
				String ipAddress = country + area + region + city + county + isp;
				return ipAddress;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public static void main(String[] args) {
		
	}
}
