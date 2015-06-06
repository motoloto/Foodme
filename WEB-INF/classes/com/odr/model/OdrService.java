package com.odr.model;

import java.sql. Timestamp;
import java.util.List;
import java.util.Map;

import com.cop.model.CopVO;

public class OdrService {

	private OdrDAO_interface dao;

	public OdrService() {
		dao = new OdrDAO();
	}

	public OdrVO addOdr(	  
			  Integer MEM_NO,
			  Integer COP_NO,
			  String  ODR_STATE,
			  String  ODR_SEQNUM,
			  Integer ODR_USDTMS,
			  String  ODR_PAYNAME,
			  String  ODR_PHONE,
			  String  ODR_MAIL,
			  Integer ODR_BUYAMT,
			  Integer ODR_TOPRC,
			  Integer ODR_PAYMODE,
			   Timestamp  ODR_PAYTIME,
			   Timestamp  ODR_TIME) {

		OdrVO OdrVO = new OdrVO();

		OdrVO.setMem_no(MEM_NO);
		OdrVO.setCop_no(COP_NO);
		OdrVO.setOdr_state(ODR_STATE);
		OdrVO.setOdr_seqnum(ODR_SEQNUM);
		OdrVO.setOdr_usdtms(ODR_USDTMS);
		OdrVO.setOdr_payname(ODR_PAYNAME);
		OdrVO.setOdr_phone(ODR_PHONE);
		OdrVO.setOdr_mail(ODR_MAIL);
		OdrVO.setOdr_buyamt(ODR_BUYAMT);
		OdrVO.setOdr_toprc(ODR_TOPRC);
		OdrVO.setOdr_paymode(ODR_PAYMODE);
		OdrVO.setOdr_paytime(ODR_PAYTIME);
		OdrVO.setOdr_time(ODR_TIME);
		dao.insert(OdrVO);

		return OdrVO;
	}

	public OdrVO updateOdr(	  
			
			  String  ODR_NO,
			  Integer MEM_NO,
			  Integer COP_NO,
			  String  ODR_STATE,
			  String  ODR_SEQNUM,
			  Integer ODR_USDTMS,
			  String  ODR_PAYNAME,
			  String  ODR_PHONE,
			  String  ODR_MAIL,
			  Integer ODR_BUYAMT,
			  Integer ODR_TOPRC,
			  Integer ODR_PAYMODE,
			   Timestamp  ODR_PAYTIME,
			   Timestamp  ODR_TIME) {

		OdrVO OdrVO = new OdrVO();

		OdrVO.setOdr_no(ODR_NO);
		OdrVO.setMem_no(MEM_NO);
		OdrVO.setCop_no(COP_NO);
		OdrVO.setOdr_state(ODR_STATE);
		OdrVO.setOdr_seqnum(ODR_SEQNUM);
		OdrVO.setOdr_usdtms(ODR_USDTMS);
		OdrVO.setOdr_payname(ODR_PAYNAME);
		OdrVO.setOdr_phone(ODR_PHONE);
		OdrVO.setOdr_mail(ODR_MAIL);
		OdrVO.setOdr_buyamt(ODR_BUYAMT);
		OdrVO.setOdr_toprc(ODR_TOPRC);
		OdrVO.setOdr_paymode(ODR_PAYMODE);
		OdrVO.setOdr_paytime(ODR_PAYTIME);
		OdrVO.setOdr_time(ODR_TIME);
		dao.update(OdrVO);

		return OdrVO;
	}

	public void deleteOdr(String ordno) {
		dao.delete(ordno);
	}

	public OdrVO getOneOdr(String ordno) {
		return dao.findByPrimaryKey(ordno);
	}

	public List<OdrVO> getAll() {
		return dao.getAll();
	}
	
	public List<OdrVO> getOdrByState(Map<String, String[]> map) {
		return dao.getAllPayedOrder(map);
	}
	public List<OdrVO> getMemOdr(Integer mem_no) {
		return dao.getMemOdr( mem_no);
	}
}
