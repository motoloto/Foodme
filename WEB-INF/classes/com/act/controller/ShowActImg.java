package com.act.controller;

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

import com.act.model.*;
import com.act.model.ActVO;

public class ShowActImg extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		doGet(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) {

		res.setContentType("image/jpg");
		// int act_no = Integer.parseInt(req.getParameter("act_no"));
		ServletOutputStream out;
		try {
			out = res.getOutputStream();
			ActService actSvr = new ActService();
			int act_no = Integer.parseInt(req.getParameter("act_no"));

			ActVO actVO = actSvr.getOneAct(act_no);

			InputStream in = new ByteArrayInputStream(actVO.getAct_photo());

			// BufferedInputStream is = new BufferedInputStream(in);
			byte[] buf = new byte[4 * 1024]; // 4K buffer
			int len;
			while ((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
			}

			in.close();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
