package com.affi.controller;

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

import com.affi.model.AffiService;
import com.affi.model.AffiVO;

public class ShowAffiImg extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		doGet(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) {

		res.setContentType("image/jpg");
		// int act_no = Integer.parseInt(req.getParameter("act_no"));
		ServletOutputStream out;
		try {
			AffiService affiSvr = new AffiService();
			int affi_no = Integer.parseInt(req.getParameter("affi_no"));

			AffiVO affiVO = affiSvr.getOneAffi(affi_no);

			InputStream in = new ByteArrayInputStream(affiVO.getRest_photo());

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
