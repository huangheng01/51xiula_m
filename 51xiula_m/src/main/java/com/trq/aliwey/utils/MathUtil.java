package com.trq.aliwey.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 
 * <pre>
 * [概 要] MathUtil 定义
 * 
 * [详 细] 计算操作
 * 
 * [备 考] 无
 * 
 * [环 境] JavaSE 1.7
 * 
 * @author tan.p
 * @date 2016年1月18日上午10:40:29
 * @version 1.0
 * </pre>
 */
public class MathUtil {
	//private static final int DEF_DIV_SCALE = 10;
	private static DecimalFormat df = new DecimalFormat("#.##");

	/**
	 * 两个Double数相加
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double add(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		String b = df.format(b1.add(b2).doubleValue());
		return Double.parseDouble(b);
	}

	/**
	 * 两个Double数相减
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double sub(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		String b = df.format(b1.subtract(b2).doubleValue());
		return Double.parseDouble(b);
	}

	/**
	 * 两个Double数相乘
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double mul(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		String b = df.format(b1.multiply(b2).doubleValue());
		return Double.parseDouble(b);
	}

	/**
	 * 两个Double数相除
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double div(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		String b = df.format(b1.divide(b2).doubleValue());
		return Double.parseDouble(b);
		//return new Double(b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_EVEN).doubleValue());
	}

	/**
	 * 两个Double数相除，并保留scale位小数
	 * 
	 * @param v1
	 * @param v2
	 * @param scale
	 * @return Double
	 */
	public static Double div(Double v1, Double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return new Double(b1.divide(b2, scale, BigDecimal.ROUND_HALF_EVEN).doubleValue());
	}
	
	/**
	 * 
	 * <pre>
	 * [方法] getBillLatefee的定义
	 * 
	 * [说明] 滞纳金计算
	 *
	 * @author tan.p
	 * @date 2016年1月20日 上午10:01:19
	 * </pre>
	 */
	public static double getBillLatefee(int latedays, double ratio, double money) {
		double latefee = 0;
		if(latedays > 0){
			latefee = mul(mul(money, ratio), Double.parseDouble(latedays+""));
		}
		/*
		if (latedays > 1 && latedays <= 15) {
			latefee = roundDouble(money * 0.005 * latedays, 2);
		} else if (latedays > 15) {
			latefee = roundDouble(money * 0.01 * latedays, 2);
		}
		*/
		return latefee;
	}
	
	/**
	 * 
	 * <pre>
	 * [方法] compare的定义
	 * 
	 * [说明] 判断double类型是否相等
	 *
	 * @author tan.p
	 * @date 2016年1月20日 上午10:54:10
	 * </pre>
	 */
	public static boolean compare(String type, double a, double b) {
		BigDecimal val1 = new BigDecimal(a);
		BigDecimal val2 = new BigDecimal(b);
		boolean result = false;
		if (type.equals("<")) {// 小于
			if (val1.compareTo(val2) < 0) {
				result = true;
			}
		} else if (type.equals("=")) {
			if (val1.compareTo(val2) == 0) {
				result = true;
			}
		} else if (type.equals(">")) {
			if (val1.compareTo(val2) > 0) {
				result = true;
			}
		} else if (type.equals(">=")){
			if (val1.compareTo(val2) > 0 || val1.compareTo(val2) == 0) {
				result = true;
			}
		} else if (type.equals("<=")){
			if (val1.compareTo(val2) < 0 || val1.compareTo(val2) == 0) {
				result = true;
			}
		}
		return result;
	}
}
