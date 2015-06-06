package com.odr.model;

import java.util.*;
/*此萬用查詢是購買餐劵時，用來查哪家餐劵的*/
public class jdbcUtil_CompositeQuery_OneCopByOdr {

	public static String get_aCondition_For_Oracle(String columnName, String value) {

		String aCondition = null;

		if ("cop_no".equals(columnName) || "cop_price".equals(columnName) || "cop_salamt".equals(columnName) ) // �Ω��L
			aCondition = columnName + "=" + value;
		else if ("cop_name".equals(columnName) ) 
			aCondition = columnName + " like '%" + value + "%'";
		else if ("cop_date".equals(columnName))                          
//			aCondition = "to_char(" + columnName + ",'yyyy-mm')='" + value + "'";
		    aCondition = "to_char(cop_date, 'yyyy-mm') in('" + value+"')";
		else if ( "rest_no".equals(columnName)) // 判斷正在上架中的餐劵
			aCondition = columnName + "=" + value+"and cop_state='1' ";

		return aCondition + " ";
	}

	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0	&& !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_Oracle(key, value.trim());

				if (count == 1)
					whereCondition.append(" where " + aCondition);
				else
					whereCondition.append(" and " + aCondition);

				System.out.println("有送出查詢資料的欄位數count =  " + count);
			}
		}
		System.out.println(whereCondition);
		return whereCondition.toString();
	}

	public static void main(String argv[]) {

		// �t�X req.getParameterMap()��k �^�� java.util.Map<java.lang.String,java.lang.String[]> �����
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("rest_no", new String[] { "7001" });
//		map.put("cop_name", new String[] { "台北【京炙鐵板燒】VIP滿額贈 ($1500抵 $1600)" });
		map.put("cop_date", new String[] { "2015-01" });
		map.put("rest_noByodr", new String[] { "7001" });
//		map.put("cop_price", new String[] { "5000.5" });
//		map.put("cop_salamt", new String[] { "0.0" });
//		map.put("rest_no", new String[] { "10" });
		map.put("action", new String[] { "getXXX" }); // �`�NMap�̭��|�t��action��key

		String finalSQL = "select * from coupon "
				          + jdbcUtil_CompositeQuery_OneCopByOdr.get_WhereCondition(map)
				          + "order by cop_no";
		System.out.println("����finalSQL = " + finalSQL);

	}
}
