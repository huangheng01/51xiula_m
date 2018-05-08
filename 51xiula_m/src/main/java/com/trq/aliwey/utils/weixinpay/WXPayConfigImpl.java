package com.trq.aliwey.utils.weixinpay;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


import com.jranuan.ext.kit.ConfigPropKit;

public class WXPayConfigImpl extends WXPayConfig {

	//private byte[] certData;
	 private static WXPayConfigImpl INSTANCE;
	 
	 public static WXPayConfigImpl getInstance() throws Exception{
	        if (INSTANCE == null) {
	            synchronized (WXPayConfigImpl.class) {
	                if (INSTANCE == null) {
	                    INSTANCE = new WXPayConfigImpl();
	                }
	            }
	        }
	        return INSTANCE;
	    }
	 
	@Override
	String getAppID() {
		 return ConfigPropKit.get("app_Id");
	}

	@Override
	String getMchID() {
		// TODO Auto-generated method stub
		return ConfigPropKit.get("mch_id");
	}

	@Override
	String getKey() {
		// TODO Auto-generated method stub
		return ConfigPropKit.get("partnerKey");
	}

	@Override
	InputStream getCertStream() {
		// TODO Auto-generated method stub
		 // ByteArrayInputStream certBis;
	       // certBis = new ByteArrayInputStream(this.certData);
	        //return certBis;
		return null;
	}

	@Override
	IWXPayDomain getWXPayDomain() {
		return WXPayDomainSimpleImpl.instance();
	}

}
