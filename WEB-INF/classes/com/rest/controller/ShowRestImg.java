package com.rest.controller;

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

import com.ImgResize.model.ImageUtil;
import com.rest.model.RestService;
import com.rest.model.RestVO;


public class ShowRestImg extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		doGet(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) {

		res.setContentType("image/jpg");
		// int act_no = Integer.parseInt(req.getParameter("act_no"));
		ServletOutputStream out;
		try {
			RestService restSvr = new RestService();
			int rest_no = Integer.parseInt(req.getParameter("rest_no"));

			RestVO restVO = restSvr.getOneRest(rest_no);
			byte[] resizedImage;
			resizedImage = new ImageUtil().resizeImage(
					restVO.getRest_photo(), 500, 75);
			restVO.setRest_photo(resizedImage);
			InputStream in = new ByteArrayInputStream(restVO.getRest_photo());

			// BufferedInputStream is = new BufferedInputStream(in);
			out = res.getOutputStream();
			byte[] buf = new byte[2 * 1024*1024]; // 4K buffer
			int len;
			while ((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
		
			in.close();

		} catch (IOException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
