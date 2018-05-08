package com.trq.aliwey.utils;

import com.jranuan.ext.kit.MD5Kit;
import com.jranuan.ext.kit.TimeKit;
import com.jranuan.kit.HttpKit;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.params.CoreConnectionPNames;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 
 * <pre>
 * [概 要] SendSms定义
 * 
 * [详 细] 发送短信
 * 
 * [备 考] 无
 * 
 * [环 境] JavaSE 1.7
 * 
 * @author tan.p
 * @date 2016年4月8日上午10:13:49
 * @version 1.0
 * </pre>
 */
public class SendSms {

	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

	public static boolean send(String vcode, String mobile) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(Url);

		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");

		String content = "";
		if (vcode.length() <= 6)
			content = "您的验证码是：" + vcode + "。请不要把验证码泄露给其他人。";
		else
			content = vcode;
		NameValuePair[] data = { // 提交短信
				new NameValuePair("account", "cf_taorenqi"),
				new NameValuePair("password", new MD5Kit().toMD5Info("a620aad1f5700e11b052abe252438d4a")), new NameValuePair("mobile", mobile),
				new NameValuePair("content", content) };

		method.setRequestBody(data);

		try {
			client.executeMethod(method);

			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
			String SubmitResult = method.getResponseBodyAsString();

			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();

			String code = root.elementText("code");
			String msg = root.elementText("msg");
			System.out.println("短信发送返回码：" + code + "消息:" + msg);
			if (code.equals("2")) {
				System.out.println("短信提交成功");
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public static void main(String[] args) {
		send("1111", "18372500117");
	}
}
