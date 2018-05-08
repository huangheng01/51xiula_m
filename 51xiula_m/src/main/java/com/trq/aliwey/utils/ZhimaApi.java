package com.trq.aliwey.utils;

import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthorizeRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditIvsDetailGetRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditIvsDetailGetResponse;

/**
 * 
 * <pre>
 * [概 要] ZhimaApi 定义
 * 
 * [详 细] 各种数据查询信息
 * 
 * [备 考] 无
 * 
 * [环 境] JavaSE 1.7
 * 
 * @author tan.p
 * @date 2016年9月27日上午10:20:17
 * @version 1.0
 * </pre>
 */
public class ZhimaApi {

	/**
	 * 授权
	 * @param identityType
	 * @param identityParam
	 * @param bBizParams
	 * @return
	 */
	public static String zhimaAuthInfoAuthorize(String identityType,String identityParam,String bBizParams) {		
		
		String url="";
		try {
			ZhimaAuthInfoAuthorizeRequest req = new ZhimaAuthInfoAuthorizeRequest();
	        req.setChannel("app");
	        req.setPlatform("zmop");
	        //req.setIdentityType("1");// 必要参数        
	        //req.setIdentityParam("{\"name\":\"张三\",\"certType\":\"IDENTITY_CARD\",\"certNo\":\"330100198806221413\"}");// 必要参数        
	        //req.setBizParams("{\"auth_code\":\"M_H5\",\"channelType\":\"app\",\"state\":\"\"}");        
	        req.setIdentityType(identityType);// 必要参数        
	        req.setIdentityParam(identityParam);// 必要参数        
	        req.setBizParams(bBizParams);        
	        DefaultZhimaClient client = new DefaultZhimaClient(Contants.serverUrl, Contants.appId, Contants.privateKey,Contants.zhimaPublicKey);
            url = client.generatePageRedirectInvokeUrl(req);
        } catch (ZhimaApiException e) {
            e.printStackTrace();
        }
        
        return url;
    }


}
