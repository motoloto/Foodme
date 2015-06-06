package com.rest.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.rest.model.PerTime;
import com.rest.model.RestService;
import com.rest.model.RestVO;

public class Week {

	public static void main(String[] args) throws SQLException,
			ClassNotFoundException {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String userid = "user2";
		String passwd = "u222";
		// 將日期換算為星期
		String GET_RESERV_FROM_ONE = "select RESERVED_TOTALSET,reserved_sun1, reserved_sun2,reserved_sun3, reserved_sun4,reserved_sun5, reserved_sun6, "
				+ "reserved_mon1, reserved_mon2,reserved_mon3,reserved_mon4,reserved_mon5,reserved_mon6, "
				+ "reserved_tue1, reserved_tue2, reserved_tue3, reserved_tue4, reserved_tue5, reserved_tue6, "
				+ "reserved_wed1, reserved_wed2, reserved_wed3, reserved_wed4, reserved_wed5, reserved_wed6,"
				+ " reserved_thu1, reserved_thu2, reserved_thu3, reserved_thu4, reserved_thu5, reserved_thu6, "
				+ " reserved_fri1, reserved_fri2, reserved_fri3, reserved_fri4, reserved_fri5, reserved_fri6, "
				+ "reserved_sat1, reserved_sat2, reserved_sat3, reserved_sat4, reserved_sat5, reserved_sat6 "
				+ "FROM REST WHERE REST_NO=7001";
		Connection con = null;
		PreparedStatement pstmt = null;

		Class.forName(driver);
		con = DriverManager.getConnection(url, userid, passwd);
		pstmt = con.prepareStatement(GET_RESERV_FROM_ONE);
		ResultSet rs = pstmt.executeQuery();
		System.out.print(rs.getMetaData().getColumnCount()); // 43個欄位 第一個是總數
		List<PerTime> remain_a_day = new ArrayList<PerTime>();
		List<Object> weekMapping = new ArrayList<Object>();
		Week week = new Week();
		PerTime perDay = new PerTime();
		int max = 0; // 最大可定位數
		while (rs.next()) {
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				if (i == 1) {
					max = rs.getInt(i);
				} else {
					perDay.setTime(rs.getMetaData().getColumnName(i));
					perDay.setResidual(rs.getInt(i));
					remain_a_day.add(perDay);
					perDay = new PerTime();
					if ((i - 1) % 6 == 0) {
						weekMapping.add(remain_a_day);
						remain_a_day = new ArrayList<PerTime>();
					}
				}
			}
		} 
		int today = 4;
		int amount = 2;
		// 某天的全部訂位數
		List<PerTime> findaDay = (List<PerTime>) weekMapping.get(today);
		// 查詢剩餘可定位
		// System.out.println("時段:"+findaDay.get(1).getTime()+"已訂位數:"+findaDay.get(1).getResidual());
		// // 42個欄位
		for (PerTime theDay : findaDay) {
			if ((max - theDay.getResidual()) > 13) {
				System.out.println("時段:" + theDay.getTime() + "已訂位數:"
						+ theDay.getResidual());
			}
		}

	}

	
}
