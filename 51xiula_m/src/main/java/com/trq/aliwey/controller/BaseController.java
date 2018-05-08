package com.trq.aliwey.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.http.EncodedHttpURI;
import org.jsoup.Jsoup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jranuan.core.Controller;
import com.jranuan.ext.kit.ConfigPropKit;
import com.jranuan.ext.kit.MD5Kit;
import com.jranuan.kit.StrKit;
import com.trq.aliwey.utils.StringTools;

import ch.qos.logback.core.encoder.EchoEncoder;
import javolution.util.FastList;
import javolution.util.FastMap;


/**
 * <pre>
 * [概 要] BaseController定义
 * 
 * [详 细] 基础Controller
 * 
 * [备 考] 无
 * 
 * [环 境] JavaSE 1.6.37
 * 
 * @param <M> the generic 类型
 * @author chuanfu.xie
 * @date 2014-8-1 上午10:16:59
 * @version 1.0
 * @see Controller
 * </pre>
 */
public abstract class BaseController extends Controller {
	
	protected static final String SUCCESS = "success";

	protected static final String ERROR = "error";

	protected static boolean flag = false;

	/*** 默认后台分页大小 ***/
	protected static Integer PAGE_SIZE = 30;
	
	public MD5Kit md5 = new MD5Kit();
	
	//用户Session
	public static String LoginSession = "login_session";
	
	//返回json字符串
	public String jsonText = "";
	
	//返回jsonMap
	public Map<String, Object> jsonMap = FastMap.newInstance();
	
	//返回jsonList
	public List<Map<String, Object>> jsonList = FastList.newInstance();

	/**
	 * 构造控制器.
	 * 
	 * @author chuanfu.xie
	 * @date 2014-8-5 13:03:07 </pre>
	 */
	public BaseController() {
		Object genericClz = getClass().getGenericSuperclass();
		genericClz.getClass().getSimpleName();
	}

	/**
	 * <pre>
	 * [概 要] index的定义
	 * 
	 * [详 细] 无
	 *    
	 * @author chuanfu.xie
	 * @date 2014-8-5 上午09:00:19
	 * </pre>
	 */
	public void index() throws Exception {
		this.display();
	}

	/**
	 * <pre>
	 * [概 要] display的定义
	 * 
	 * [详 细] 无.
	 * 
	 * @author chuanfu.xie
	 * @date 2014-8-1 下午05:15:33
	 * </pre>
	 */
	public void display() {
		this.display(null);
	}
	
	

	/**
	 * <pre>
	 * [概 要] display的定义
	 * 
	 * [详 细] 显示方法.
	 * 
	 * @param view 参数
	 * @author chuanfu.xie
	 * @date 2014-8-1 下午05:13:14
	 * </pre>
	 */
	public void display(String view) {
		if (StrKit.isBlank(view)) {
			view = this.getAttr("VIEW_NAME");
		}
		if (StrKit.isBlank(view)) {
			view ="index";
		}
		this.renderFreeMarker(view + ".html");
	}

	/**
	 * <pre>
	 * [概 要] getBasePath的定义
	 * 
	 * [详 细] 获取基础路径
	 * 
	 * @return   
	 * @author chuanfu.xie
	 * @date 2014-8-5 下午10:52:44
	 * </pre>
	 */
	public String getBasePath() {
		return this.getRequest().getScheme() + "://" + this.getRequest().getHeader("host") + this.getRequest().getContextPath();
	}
	
	/** 获取当前操作人信息 */
	/*public Buyer getUser() {
		Buyer user = getSessionAttr(LoginSession);
		return user;
	}

	*//** 获取当前操作人ID *//*
	public int getUserId() {
		return getUser().getInt("buyer_id");
	}

	*//** 获取当前操作人名称 *//*
	public String getRealName() {
		return getUser().getStr("buyer_truename");
	}*/
	
	/**
	 * 
	 * <pre>
	 * [方法] toJson的定义
	 * 
	 * [说明] 返回json
	 *
	 * @author tan.p
	 * @date 2016年3月9日 下午5:35:37
	 * </pre>
	 */
	public String toJson(Object message, String success, int status) {
		Map<String, Object> map = FastMap.newInstance();
		map.put("message", message);
		map.put("success", success);
		map.put("status", status);
		return JSON.toJSONString(map);
	}
	
	/**
	 * 
	 * <pre>
	 * [方法] checkCaptcha的定义
	 * 
	 * [说明] 判断手机验证码
	 *
	 * @author tan.p
	 * @date 2016年3月31日 下午3:03:13
	 * </pre>
	 */
	public int checkCaptcha(String phone, String captcha){
		try {
			String code = getSessionAttr("sms_" + phone);
			if (StrKit.notBlank(code)) {
				if (code.equals(captcha)) {
					removeSessionAttr("sms_" + phone);
					return 1;
				} else {
					return 2;
				}
			} else {
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
	}
	
	
	protected String createSign(Map<String,String> param,String key){
		
		List<String> pnamelist=new ArrayList<String>();
		
		Iterator<String> keys=param.keySet().iterator();
		
		while(keys.hasNext()){
			pnamelist.add(keys.next());
		}
		
		String[] pnames=pnamelist.toArray(new String[0]);
		Arrays.sort(pnames);
		
		StringBuilder keyvalue=new StringBuilder();
		
		for(String item : pnames){
			String vl=param.get(item)==null?"":param.get(item);
			
			if(keyvalue.length()>0)
				keyvalue.append("&");
			
			keyvalue.append(item+"="+vl);
		}
		
		keyvalue.append("&key="+key);
		
		return StringTools.getMD5(keyvalue.toString()).toLowerCase();
	}
	
	/**
	 * 调用api
	 * @param url
	 * @param par
	 * @param token
	 * @return
	 * @throws Exception
	 */
	protected JSONObject callAPI(String url,Map<String,String> par,String token) throws Exception{
		
		String key=ConfigPropKit.get("apikey");
		String apiurl=ConfigPropKit.get("apiurl");
		
		String returnString="";
		if(null!=par && !par.isEmpty()){
			String sign=this.createSign(par, key);
			par.put("sign", sign);
			
			returnString=Jsoup.connect(apiurl+url).data(par).header("Authorization", token).timeout(20000).post().text();
			
			
		}else{
			
			returnString=Jsoup.connect(apiurl+url).header("Authorization", token).timeout(20000).get().text();
			
		}
		
		returnString=StringTools.GetDesDecrypt(URLDecoder.decode(returnString, "utf-8"));
		
		com.alibaba.fastjson.JSONArray jsonArray=JSON.parseArray(returnString);
		JSONObject obj=jsonArray.getJSONObject(0);
		
		return obj;
		
	}

	
    protected String callAPI2(String url,Map<String,String> par,String token) throws Exception{
		
		String key=ConfigPropKit.get("apikey");
		String apiurl=ConfigPropKit.get("apiurl2");
		
		String returnString="";
		if(null!=par && !par.isEmpty()){
			String sign=this.createSign(par, key);
			par.put("sign", sign);
			
			returnString=Jsoup.connect(apiurl+url).data(par).header("Authorization", token).timeout(20000).post().text();
			
			
		}else{
			
			returnString=Jsoup.connect(apiurl+url).ignoreContentType(true).header("Authorization", token).timeout(20000).get().text();
			
		}
		
		//returnString=StringTools.GetDesDecrypt(URLDecoder.decode(returnString, "utf-8"));
		//JSONObject jsonObj = new org.json.JSONObject(returnString);
       // JSONObject obj = new JSONObject(jsonObj.getString("refObj"));
		//com.alibaba.fastjson.JSONArray jsonArray=JSON.parseArray(returnString);
		//JSONObject obj=jsonArray.getJSONObject(0);
		
		return returnString;
		
	}
	
	
	
	//wn  write

	public JSONObject taShuoData(int pageNumber, int pageSize) throws Exception{
		JSONObject json=this.callAPI("/sayList-"+pageNumber+"-"+pageSize, null, "");
		int start1=json.getIntValue("start");
		int sign1=json.getIntValue("sign");
		
		if(start1==1 && sign1==1){
			return json.getJSONObject("dataObject");
		}else{
			jsonText=json.getString("error");
			return null;
		}
	}
	
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param,String token) {
        String result = "";
        BufferedReader in = null;
        try {
        	/*param= java.net.URLEncoder.encode(param, "UTF-8");*/
            String urlNameString = url + "?" + param;
            
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("token", token);
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
               /* System.out.println(key + "--->" + map.get(key));*/
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
	
	 /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url,Map<String,String> par, String param,String token) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("token", token);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
	
	/**
	 * 调用api
	 * @param url
	 * @param par
	 * @param token
	 * @return
	 * @throws Exception
	 */
	protected JSONObject call51API(String url,Map<String,String> par,String param,String token,int choice) throws Exception{
		
		String apiurl=ConfigPropKit.get("apiurl51");
		
		String returnString="";
		if(choice==2){
			returnString=sendPost(apiurl+url, par,param,token);
			
		}else{
			returnString=sendGet(apiurl+url, param,token);
			
		}
		JSONObject json = JSONObject.parseObject(returnString);  
		return json;
		
	}
	protected JSONObject call51API1(String url,Map<String,String> par,String param,String token,int choice) throws Exception{
		
		String apiurl=ConfigPropKit.get("apiurl51");
		
		String returnString="";
		if(choice==2){
			returnString=Jsoup.connect(apiurl+url).data(par).header("token", token).ignoreContentType(true).timeout(20000).post().text();
		}else{
			returnString=sendGet(apiurl+url, param,token);
			
		}
		JSONObject json = JSONObject.parseObject(returnString);  
		
		return json;
		
	}
	
}