package cn.feng.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具
 */
public class DateUtils {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 将Date转换为日期字符串
	 * 
	 * @param date
	 *            要转换的Date
	 * @return 转换后的日期字符串
	 */
	public static String dateToString(Date date) {
		if (date == null) {
			return "";
		}
		return sdf.format(date);
	}
	/**
	 * 将日期字符串转换为Date
	 * 
	 * @param dateStr
	 *            要转换的日期字符串
	 * @return 转换后的Date对象
	 */
	public static Date stringToDate(String dateStr) {
		if (dateStr == null || dateStr.equals("")) {
			return null;
		} else {
			Date parse = null;
			try {
				parse = sdf.parse(dateStr);
			} catch (ParseException e) {
				return null;
			}
			return parse;
		}
	}
}