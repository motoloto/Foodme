package com.mem.controller;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ads.model.AdsDAO;
import com.ads.model.AdsVO;
import com.mem.model.MemDAO;
import com.mem.model.MemVO;

public class ShowMemImg extends HttpServlet {

	public ShowMemImg() {
		super();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		doGet(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) {

		res.setContentType("image/jpg");
		String mem_account = req.getParameter("mem_account");
		ServletOutputStream out;
		try {
			out = res.getOutputStream();

			MemDAO dao = new MemDAO();
			MemVO memVO = dao.findByPrimaryKey(mem_account);

			InputStream in = new ByteArrayInputStream(memVO.getMem_pic());

			BufferedInputStream is = new BufferedInputStream(in);
			byte[] buf = new byte[4 * 1024]; // 4K buffer
			int len;
			while ((len = is.read(buf)) != -1) {
				out.write(buf, 0, len);
			}

			in.close();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
