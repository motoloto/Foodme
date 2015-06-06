package com.banner.controller;

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

import com.banner.model.BannerDAO;
import com.banner.model.BannerVO;

public class ShowBannerImg extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShowBannerImg() {
		super();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		doGet(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) {

		res.setContentType("image/jpg");
		int banner_no = Integer.parseInt(req.getParameter("banner_no"));
		ServletOutputStream out;
		try {
			out = res.getOutputStream();

			BannerDAO dao = new BannerDAO();
			BannerVO bannerVO = dao.findByPrimaryKey(banner_no);

			InputStream in = new ByteArrayInputStream(bannerVO.getBanner_pic());

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
