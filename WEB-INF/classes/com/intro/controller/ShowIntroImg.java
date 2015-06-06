package com.intro.controller;

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
import com.intro.model.IntroDAO;
import com.intro.model.IntroVO;

public class ShowIntroImg extends HttpServlet {

	public ShowIntroImg() {
		super();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		doGet(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) {

		res.setContentType("image/jpg");
		int intro_no = Integer.parseInt(req.getParameter("intro_no"));
		ServletOutputStream out;
		try {
			out = res.getOutputStream();

			IntroDAO dao = new IntroDAO();
			IntroVO introVO = dao.findByPrimaryKey(intro_no);

			InputStream in = new ByteArrayInputStream(introVO.getIntro_pic());

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
