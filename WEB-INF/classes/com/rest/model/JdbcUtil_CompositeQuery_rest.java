package com.rest.model;

import java.util.*;

public class JdbcUtil_CompositeQuery_rest {

	public static String get_aCondition_For_Oracle(String columnName, String value) {

		String aCondition = "";

		if ("empno".equals(columnName) || "sal".equals(columnName) || "comm".equals(columnName) || "deptno".equals(columnName)) // �Ω��L
			aCondition = columnName + "=" + value;
		else if ("rest_addr".equals(columnName) && !value.isEmpty()) // rest_addr LIKE '%西屯%'
			aCondition = " and " +columnName + " like '%" + value + "%'";
		else if ("class_no".equals(columnName))                          //  rest.rest_no=rest_class.rest_no
			aCondition = " and rest_class.class_no="+value;
		return aCondition + " ";
	}

	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0]; //用KEY取得VALUE
			if (value != null && value.trim().length() != 0	&& !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_Oracle(key, value.trim());
//
//				if (count == 1)
//					whereCondition.append(" and "+aCondition);
//				else
					whereCondition.append(aCondition);

//				System.out.println("���e�X�d�߸�ƪ�����count = " + count);
			}
		}
		
		return whereCondition.toString();
	}

	public static void main(String argv[]) { 

		// �t�X req.getParameterMap()��k �^�� java.util.Map<java.lang.String,java.lang.String[]> �����
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("rest_addr", new String[] { "西屯" });
		map.put("class_no", new String[] { "" });
		map.put("action", new String[] { "getXXX" }); // �`�NMap�̭��|�t��action��key

		String finalSQL = "select  distinct   "
				+ " rest.rest_no, rest_name, rest_addr, rest_tel, rest_opentime, rest_waitmin, RESERVED_TOTALSET,   "
				+ "           reserved_sun1, reserved_sun2, reserved_sun3, reserved_sun4, reserved_sun5, reserved_sun6,"
				+ "			reserved_mon1, reserved_mon2, reserved_mon3, reserved_mon4, reserved_mon5, reserved_mon6, "
				+ "			reserved_tue1, reserved_tue2, reserved_tue3, reserved_tue4, reserved_tue5, reserved_tue6, "
				+ "			reserved_wed1, reserved_wed2, reserved_wed3, reserved_wed4, reserved_wed5, reserved_wed6,"
				+ "	    	reserved_thu1, reserved_thu2, reserved_thu3, reserved_thu4, reserved_thu5, reserved_thu6, "
				+ "			reserved_fri1, reserved_fri2, reserved_fri3, reserved_fri4, reserved_fri5, reserved_fri6, "
				+ "			reserved_sat1, reserved_sat2, reserved_sat3, reserved_sat4, reserved_sat5, reserved_sat6 "
				+"			from   REST,REST_CLASS   where rest.rest_no=rest_class.rest_no"
				+ JdbcUtil_CompositeQuery_rest.get_WhereCondition(map)
				          + "order by rest.rest_no";
//		System.out.println("����finalSQL = " + finalSQL);

	}
}
