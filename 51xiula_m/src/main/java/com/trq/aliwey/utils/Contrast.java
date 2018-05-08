package com.trq.aliwey.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * 
 * <pre>
 * [概 要] Contrast 定义
 * 
 * [详 细] 风险类型与风险代码对应关系表
 * 
 * [备 考] 无
 * 
 * [环 境] JavaSE 1.7
 * 
 * @author tan.p
 * @date 2016年11月9日下午6:03:02
 * @version 1.0
 * </pre>
 */
public class Contrast {
	//行业类型
	public static Map<String, String> industryMap = new HashMap<String, String>();
	//风险类型
	public static Map<String, String> riskMap = new HashMap<String, String>();
	//风险说明
	public static Map<String, String> explainMap = new HashMap<String, String>();
	
	static {
		industryMap.put("AA", "金融信贷类");
		industryMap.put("AB", "公检法");
		industryMap.put("AC", "金融支付类");
		industryMap.put("AD", "租车行业");
		industryMap.put("AE", "酒店行业");
		industryMap.put("AF", "电商行业");
		industryMap.put("AG", "租房行业");
		industryMap.put("AH", "运营商");
		industryMap.put("AI", "保险行业");
		
		riskMap.put("AA001", "借贷逾期");
		riskMap.put("AA002", "套现");
		riskMap.put("AB001", "被执行人");
		riskMap.put("AC001", "盗卡者/盗卡者同伙");
		riskMap.put("AC002", "欺诈者/欺诈同伙");
		riskMap.put("AC003", "盗用操作/盗用者同伙");
		riskMap.put("AC004", "盗用支出/盗用者同伙");
		riskMap.put("AC005", "骗赔");
		riskMap.put("AC007", "案件库黑名单");
		riskMap.put("AD001", "逾期未支付");
		riskMap.put("AD002", "超期未还车");
		riskMap.put("AD003", "违章未处理");
		riskMap.put("AE001", "逾期未支付");
		riskMap.put("AF001", "虚假交易");
		riskMap.put("AF002", "恶意差评");
		riskMap.put("AF003", "涉嫌售假");
		riskMap.put("AF004", "卖家套现");
		riskMap.put("AG001", "租客房租逾期");
		riskMap.put("AG002", "租客杂费逾期");
		riskMap.put("AG003", "租客违约拒赔");
		riskMap.put("AG004", "租客其它违约");
		riskMap.put("AG005", "非正常解约");
		riskMap.put("AH001", "违约欠费");
		riskMap.put("AI003", "保证保险出险");
		
		explainMap.put("AA001001", "逾期1-30天");
		explainMap.put("AA001002", "逾期31-60天");
		explainMap.put("AA001003", "逾期61-90天");
		explainMap.put("AA001004", "逾期91-120天");
		explainMap.put("AA001005", "逾期121-150天");
		explainMap.put("AA001006", "逾期151-180天");
		explainMap.put("AA001007", "逾期180天以上");
		explainMap.put("AA001010", "逾期1期");
		explainMap.put("AA001011", "逾期2期");
		explainMap.put("AA001012", "逾期3期");
		explainMap.put("AA001013", "逾期4期");
		explainMap.put("AA001014", "逾期5期");
		explainMap.put("AA001015", "逾期6期");
		explainMap.put("AA001016", "逾期6期以上");
		explainMap.put("AA002001", "严重逾期且套现（通过交易类平台，套现且长期未还款）");
		explainMap.put("AB001001", "失信被执行人（俗称“老赖名单”）");
		explainMap.put("AB001002", "被执行人");
		explainMap.put("AC001001", "盗卡者/盗卡者同伙（有盗用或者伙同他人盗用借记卡、信用卡行为的人）");
		explainMap.put("AC002001", "欺诈者/欺诈同伙（有欺诈或者伙同他人进行欺诈行为的人）");
		explainMap.put("AC003001", "盗用操作/盗用者同伙（有盗用他人账号进行操作（未产生资损）行为的人）");
		explainMap.put("AC004001", "盗用支出/盗用者同伙（有盗用他人账号进行支付，使他人账号收到资损行为的人）");
		explainMap.put("AC005001", "骗赔（有骗取支付公司赔偿金行为的人）");
		explainMap.put("AC007001", "营销作弊名单（在营销活动中有明显作弊行为的人）");
		explainMap.put("AD001001", "逾期1-7天");
		explainMap.put("AD001002", "逾期8-14天");
		explainMap.put("AD001003", "逾期15-30天");
		explainMap.put("AD001004", "逾期31-60天");
		explainMap.put("AD001005", "逾期61-90天");
		explainMap.put("AD001006", "逾期91-120天");
		explainMap.put("AD001007", "逾期121-150天");
		explainMap.put("AD001008", "逾期151-180天");
		explainMap.put("AD001009", "逾期180天以上");
		explainMap.put("AD002001", "超期1-7天");
		explainMap.put("AD002002", "超期8-14天");
		explainMap.put("AD002003", "超期15-30天");
		explainMap.put("AD002004", "超期31-60天");
		explainMap.put("AD002005", "超期61-90天");
		explainMap.put("AD002006", "超期91-120天");
		explainMap.put("AD002007", "超期121-150天");
		explainMap.put("AD002008", "超期151-180天");
		explainMap.put("AD002009", "超期180天以上");
		explainMap.put("AD003001", "超期1-7天");
		explainMap.put("AD003002", "超期8-14天");
		explainMap.put("AD003003", "超期15-30天");
		explainMap.put("AD003004", "超期31-60天");
		explainMap.put("AD003005", "超期61-90天");
		explainMap.put("AD003006", "超期91-120天");
		explainMap.put("AD003007", "超期121-150天");
		explainMap.put("AD003008", "超期151-180天");
		explainMap.put("AD003009", "超期180天以上");
		explainMap.put("AE001001", "逾期1-7天");
		explainMap.put("AE001002", "逾期8-14天");
		explainMap.put("AE001003", "逾期15-30天");
		explainMap.put("AE001004", "逾期31-60天");
		explainMap.put("AE001005", "逾期61-90天");
		explainMap.put("AE001006", "逾期91-120天");
		explainMap.put("AE001007", "逾期121-150天");
		explainMap.put("AE001008", "逾期151-180天");
		explainMap.put("AE001009", "逾期180天以上");
		explainMap.put("AF001001", "炒信（一般表现为虚假购买商品，使得商品销量虚高误导普通消费者）");
		explainMap.put("AF002001", "恶意差评（买家对所卖商品进行恶意差评，并以此要求商家给予经济补偿）");
		explainMap.put("AF003001", "涉嫌售假（涉嫌商家销售假货）");
		explainMap.put("AF004001", "卖家套现（卖家为客户提供套现的服务）");
		explainMap.put("AG001001", "租客房租逾期");
		explainMap.put("AG002001", "租客杂费逾期 （除房租外，欠缴合同约定应付的款项 ，比如保洁费、水电媒费用等");
		explainMap.put("AG003001", "租客违约拒赔 （欠缴合同约定的违约金，比如滞纳金、提前解约罚金、财物损毁罚金等）");
		explainMap.put("AG004001", "改变房屋用途（擅自改变房屋用途，比如民宅改为商用）");
		explainMap.put("AG004002", "拆改房屋结构 （擅自拆改房屋固定结构，比如墙身等）");
		explainMap.put("AG004003", "屋内非法活动 （比如在房屋内吸毒）");
		explainMap.put("AG004004", "扰民被投诉 （比如制造噪音，被邻居投诉）");
		explainMap.put("AG004005", "擅自转租 （擅自转租整个房屋，或部分房间）");
		explainMap.put("AG005001", "违约被清退 因违约行为，比如欠缴房租，继而被房东强制清退 ）");
		explainMap.put("AG005002", "失联被强制解约 （因逃租等行为，房东认为与租客失联强制与租客解约等）");
		explainMap.put("AH001001", "欠费1-30天 因欠费被停机1-30天");
		explainMap.put("AH001002", "欠费31-60天 因欠费被停机31-60天");
		explainMap.put("AH001003", "欠费61-90天 因欠费被停机61-90天");
		explainMap.put("AH001004", "欠费91天以上 因欠费被停机91天及以上，一般被认定为恶意拆机");
		explainMap.put("AI003001", "逾期1-30天");
		explainMap.put("AI003002", "逾期31-60天");
		explainMap.put("AI003003", "逾期61-90天");
		explainMap.put("AI003004", "逾期91-120天");
		explainMap.put("AI003005", "逾期121-150天");
		explainMap.put("AI003006", "逾期151-180天");
		explainMap.put("AI003007", "逾期180天以上");
	}
	
	@SuppressWarnings("rawtypes")
	public static String getKeyByValue(int type, String value) {
		try {
			String values = "";
			Iterator it = null;
			if(type==1){
				it = industryMap.entrySet().iterator();
			}else if(type==2){
				it = riskMap.entrySet().iterator();
			}else if(type==3){
				it = explainMap.entrySet().iterator();
			}
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				if (entry.getKey().equals(value)) {
					values = (String) entry.getValue();
				}
			}
			return values;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(getKeyByValue(1, "AA"));
	}
}
