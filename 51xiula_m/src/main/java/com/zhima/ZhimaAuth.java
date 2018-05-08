package com.zhima;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;
import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.internal.util.RSACoderUtil;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthorizeRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthqueryRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaAuthInfoAuthqueryResponse;

/**
 * 
 * <pre>
 * [概 要] ZhimaAuth 定义
 * 
 * [详 细] 授权调用
 * 
 * [备 考] 无
 * 
 * [环 境] JavaSE 1.7
 * 
 * @author tan.p
 * @date 2016年9月27日上午10:20:38
 * @version 1.0
 * </pre>
 */
public class ZhimaAuth {

	// 获得授权的url地址
	public static String ZhimaAuthInfoAuthorize(String truename, String idnumber) {
		try {
			ZhimaAuthInfoAuthorizeRequest req = new ZhimaAuthInfoAuthorizeRequest();
			req.setChannel("app");
			req.setIdentityType("2");
			// 必要参数 state: 用于给商户提供透传的参数，芝麻会将此参数透传给商户;
			Map<String, String> bizParams = new HashMap<String, String>();
			bizParams.put("auth_code", "M_H5");
			bizParams.put("channelType", "app");
			bizParams.put("state", "100111211");
			req.setBizParams(JSONObject.toJSONString(bizParams));
			// 身份类型
			Map<String, String> identityParams = new HashMap<String, String>();
			identityParams.put("certNo", idnumber);
			identityParams.put("name", truename);
			identityParams.put("certType", "IDENTITY_CARD");
			req.setIdentityParam(JSONObject.toJSONString(identityParams));// 必要参数
			DefaultZhimaClient client = new DefaultZhimaClient(Contants.serverUrl, Contants.appId, Contants.privateKey,
					Contants.zhimaPublicKey);
			String authUrl = client.generatePageRedirectInvokeUrl(req);
			return authUrl;
		} catch (ZhimaApiException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 查询授权信息
	public static String ZhimaAuthInfoReq(String truename, String idnumber) {
		try {
			ZhimaAuthInfoAuthqueryRequest req = new ZhimaAuthInfoAuthqueryRequest();
			req.setIdentityType("2");// 必要参数
			Map<String, String> identityParams = new HashMap<String, String>();
			identityParams.put("certNo", idnumber);
			identityParams.put("name", truename);
			identityParams.put("certType", "IDENTITY_CARD");
			req.setIdentityParam(JSONObject.toJSONString(identityParams));// 必要参数
			DefaultZhimaClient client = new DefaultZhimaClient(Contants.serverUrl, Contants.appId, Contants.privateKey,
					Contants.zhimaPublicKey);
			ZhimaAuthInfoAuthqueryResponse response = client.execute(req);
			return response.getOpenId();
		} catch (ZhimaApiException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 从回调地址中获取返回值
	public static String parseFromReturnUrl(String params) {
		try {
			// 成功返回参数
			// open_id=268800528962931433235787903&error_message=操作成功&state=100111211&error_code=SUCCESS&app_id=1000913&success=true
			// 失败返回参数
			// error_message=支付宝账号不存在&state=100111211&error_code=ZMCSP.alipay_account_notexisted&app_id=1000913&success=false
			//String decryptedParam = RSACoderUtil.decrypt(URLDecoder.decode(params, Contants.charset), Contants.privateKey, Contants.charset);// 通过浏览器返回时，不需要decode
			String decryptedParam = RSACoderUtil.decrypt(params, Contants.privateKey, Contants.charset);// 通过浏览器返回时，不需要decode
			String paramStr = URLDecoder.decode(decryptedParam, Contants.charset);
			String encryptedParam = "";
			String[] paraPairs = paramStr.split("&");
			for (String paramPair : paraPairs) {
				String[] splits = paramPair.split("=");
				if ("open_id".equals(splits[0])) {
					encryptedParam = splits[1];
				}
			}
			return encryptedParam;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}