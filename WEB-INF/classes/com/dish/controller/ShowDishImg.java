package com.dish.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dish.model.DishService;
import com.dish.model.DishVO;


public class ShowDishImg extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		doGet(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) {

		res.setContentType("image/jpg");
		
		ServletOutputStream out;
		
		try {			
			DishService dishSvr = new DishService();
			int dish_no = Integer.parseInt(req.getParameter("dish_no"));

			DishVO dishVO = (DishVO)dishSvr.getOneDish(dish_no);

			InputStream in = new ByteArrayInputStream(dishVO.getDish_pic());

			// BufferedInputStream is = new BufferedInputStream(in);
			out = res.getOutputStream();
			byte[] buf = new byte[4 * 1024]; // 4K buffer
			int len;
			while ((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
