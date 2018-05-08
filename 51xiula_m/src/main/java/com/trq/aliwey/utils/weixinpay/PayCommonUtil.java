package com.trq.aliwey.utils.weixinpay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import com.sun.net.ssl.HttpsURLConnection;

import net.sf.json.JSONObject;






public class PayCommonUtil {

	// public static Logger log = LoggerFactory.getLogger(PayCommonUtil.class);
	    
	    /**
	     * �Ƿ�ǩ����ȷ,������:���������a-z����,������ֵ�Ĳ���μ�ǩ��
	     * @return boolean
	     */  
	    public static boolean isTenpaySign(String characterEncoding, SortedMap<Object, Object> packageParams, String API_KEY) {  
	        StringBuffer sb = new StringBuffer();  
	        Set es = packageParams.entrySet();  
	        Iterator it = es.iterator();  
	        while(it.hasNext()) {  
	            Map.Entry entry = (Map.Entry)it.next();  
	            String k = (String)entry.getKey();  
	            String v = (String)entry.getValue();  
	            if(!"sign".equals(k) && null != v && !"".equals(v)) {  
	                sb.append(k + "=" + v + "&");  
	            }  
	        }  
	          
	        sb.append("key=" + API_KEY);  
	          
	        //���ժҪ  
	        //String mysign = MD5.MD5Encode(sb.toString(), characterEncoding).toLowerCase();  
	        String tenpaySign = ((String)packageParams.get("sign")).toLowerCase();  
	          
	        //System.out.println(tenpaySign + "    " + mysign);  
	        return tenpaySign.equals("");  
	    }  
	 
	    /**
	     * @author
	     * @date 2016-4-22
	     * @Description��signǩ��
	     * @param characterEncoding
	     *            �����ʽ
	     * @param parameters
	     *            �������
	     * @return
	     */  
	    public static String createSign(String characterEncoding, SortedMap<Object, Object> packageParams, String API_KEY) {  
	        StringBuffer sb = new StringBuffer();  
	        Set es = packageParams.entrySet();  
	        Iterator it = es.iterator();  
	        while (it.hasNext()) {  
	            Map.Entry entry = (Map.Entry) it.next();  
	            String k = (String) entry.getKey();  
	            String v = "";
	            try {
	                v = (String) entry.getValue();
	            } catch (Exception e) {
	                // TODO: handle exception
	                v = entry.getValue() + "";
	            }
	             
	            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {  
	                sb.append(k + "=" + v + "&");  
	            }  
	        }  
	        sb.append("key=" + API_KEY);  
	       // String sign = MD5.MD5Encode(sb.toString(), characterEncoding).toUpperCase();  
	        return "";  
	    }  
	 
	    /**
	     * @author
	     * @date 2016-4-22
	     * @Description�����������ת��Ϊxml��ʽ��string
	     * @param parameters
	     *            �������
	     * @return
	     */  
	    public static String getRequestXml(SortedMap<Object, Object> parameters) {  
	        StringBuffer sb = new StringBuffer();  
	        sb.append("<xml>");  
	        Set es = parameters.entrySet();  
	        Iterator it = es.iterator();  
	        while (it.hasNext()) {  
	            Map.Entry entry = (Map.Entry) it.next();  
	            String k = (String) entry.getKey();
	            String v = "";
	            try {
	                v = (String) entry.getValue();
	            } catch (Exception e) {
	                // TODO: handle exception
	                v = entry.getValue() + "";
	            }
	             
	            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {  
	                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");  
	            } else {  
	                sb.append("<" + k + ">" + v + "</" + k + ">");  
	            }  
	        }  
	        sb.append("</xml>");  
	        return sb.toString();  
	    }  
	 
	    /**
	     * ȡ��һ��ָ�����ȴ�С�����������.
	     *  
	     * @param length
	     *            int �趨��ȡ�������ĳ��ȡ�lengthС��11
	     * @return int ������ɵ������
	     */  
	    public static int buildRandom(int length) {  
	        int num = 1;  
	        double random = Math.random();  
	        if (random < 0.1) {  
	            random = random + 0.1;  
	        }  
	        for (int i = 0; i < length; i++) {  
	            num = num * 10;  
	        }  
	        return (int) ((random * num));  
	    }  
	 
	    /**
	     * ��ȡ��ǰʱ�� yyyyMMddHHmmss
	     *  
	     * @return String
	     */  
	    public static String getCurrTime() {  
	        Date now = new Date();  
	        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");  
	        String s = outFormat.format(now);  
	        return s;  
	    }  
	    
	    
	    public static JSONObject httpsRequestToJsonObject(String requestUrl,
	            String requestMethod, String outputStr) {
	        JSONObject jsonObject = null;
	        try {
	            StringBuffer buffer = httpsRequest(requestUrl, requestMethod,
	                    outputStr);
	            jsonObject = JSONObject.fromObject(buffer.toString());
	        } catch (ConnectException ce) {
	           // log.error("���ӳ�ʱ��" + ce.getMessage());
	        } catch (Exception e) {
	            //log.error("https�����쳣��" + e.getMessage());
	        }
	        return jsonObject;
	    }

	    private static StringBuffer httpsRequest(String requestUrl,
	            String requestMethod, String output)
	            throws NoSuchAlgorithmException, NoSuchProviderException,
	            KeyManagementException, MalformedURLException, IOException,
	            ProtocolException, UnsupportedEncodingException {
	        URL url = new URL(requestUrl);
	        HttpsURLConnection connection = (HttpsURLConnection) url
	                .openConnection();
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        connection.setUseCaches(false);
	        connection.setRequestMethod(requestMethod);
	        if (null != output) {
	            OutputStream outputStream = connection.getOutputStream();
	            outputStream.write(output.getBytes("UTF-8"));
	            outputStream.close();
	        }
	        // ����������ȡ��������
	        InputStream inputStream = connection.getInputStream();
	        InputStreamReader inputStreamReader = new InputStreamReader(
	                inputStream, "utf-8");
	        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	        String str = null;
	        StringBuffer buffer = new StringBuffer();
	        while ((str = bufferedReader.readLine()) != null) {
	            buffer.append(str);
	        }
	        bufferedReader.close();
	        inputStreamReader.close();
	        inputStream.close();
	        inputStream = null;
	        connection.disconnect();
	        return buffer;
	    }
	}

