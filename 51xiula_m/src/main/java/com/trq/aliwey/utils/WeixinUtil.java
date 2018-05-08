package com.trq.aliwey.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jranuan.ext.kit.ConfigPropKit;

import javolution.util.FastMap;

/**
 * 
 * <pre>
 * [概 要] WeixinUtil 定义
 * 
 * [详 细] 微信工具类
 * 
 * [备 考] 无
 * 
 * [环 境] JavaSE 1.7
 * 
 * @author tan.p
 * @date 2016年4月14日上午10:53:41
 * @version 1.0
 * </pre>
 */
public class WeixinUtil {
	//全局常量
	private static String accesstoken = "";
	private static String jsapi_ticket = "";
	private static long timestamp = 1460602940;
	private static long timestampatk = 1460602940;
	
	//获取微信配置信息
	public static Map<String, String> getSign(String url) {
		Map<String, String> jsonMap = FastMap.newInstance();
		try {
			// 获取与当前时间戳之间的分钟数
			long now = TimeUtil.getUnixTimestamp();// 当前时间戳
			String j = "1";
			String s = "2";

			// 获取access_token
			String access_token = accesstoken;
			if ((now - timestampatk) > 7000 || "".equals(accesstoken) || accesstoken == null) {
				s = HttpRequestUtil.weChatSendGet("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid=" + ConfigPropKit.get("weixin.appid") + "&secret=" + ConfigPropKit.get("weixin.secret"));
				JSONObject a = JSON.parseObject(s);
				access_token = a.getString("access_token");
				accesstoken = access_token;
				timestampatk = now;
			}
			// jsapi_ticket
			if ((now - timestamp) > 7000 || "".equals(jsapi_ticket) || jsapi_ticket == null) {
				// 获取jsapi_ticket
				j = HttpRequestUtil.weChatSendGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket", "access_token=" + access_token + "&type=jsapi");
				JSONObject b = JSON.parseObject(j);
				String ticket = b.getString("ticket");
				timestamp = now;
				jsapi_ticket = ticket;
			}
			// 生成JS-SDK权限验证的签名
			jsonMap = sign(jsapi_ticket, url);

			jsonMap.put("secret", ConfigPropKit.get("weixin.secret"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonMap;
	}
	
	/*****************************************微信签名算法*********************************************************/
	private static Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("appId", ConfigPropKit.get("weixin.appid"));
		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}
