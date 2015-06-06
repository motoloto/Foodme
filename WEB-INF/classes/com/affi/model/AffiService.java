package com.affi.model;

import java.util.List;

public class AffiService {

	private AffiDAO_interface dao;

	public AffiService() {
		dao = new AffiDAO();
	}

	public AffiVO addAffi(String bus_no, String rest_name, String rest_addr,
			String rest_tel, String rest_mobil, byte[] rest_photo,
			String rest_mail, String rest_web, String rest_intro,
			String affi_state) //
	{

		AffiVO affiVO = new AffiVO();

		affiVO.setBus_no(bus_no);
		affiVO.setRest_name(rest_name);
		affiVO.setRest_addr(rest_addr);
		affiVO.setRest_tel(rest_tel);
		affiVO.setRest_mobil(rest_mobil);
		affiVO.setRest_photo(rest_photo);
		affiVO.setRest_mail(rest_mail);
		affiVO.setRest_web(rest_web);
		affiVO.setRest_intro(rest_intro);
		affiVO.setAffi_state(affi_state);

		dao.insert(affiVO);

		return affiVO;
	}

	public AffiVO updateAffi(Integer affi_no, String bus_no, String rest_name,
			String rest_addr, String rest_tel, String rest_mobil,
			byte[] rest_photo, String rest_mail, String rest_web,
			String rest_intro, String affi_state) {
		AffiVO affiVO = new AffiVO();
		affiVO.setAffi_no(affi_no);
		affiVO.setBus_no(bus_no);
		affiVO.setRest_name(rest_name);
		affiVO.setRest_addr(rest_addr);
		affiVO.setRest_tel(rest_tel);
		affiVO.setRest_mobil(rest_mobil);
		affiVO.setRest_photo(rest_photo);
		affiVO.setRest_mail(rest_mail);
		affiVO.setRest_web(rest_web);
		affiVO.setRest_intro(rest_intro);
		affiVO.setAffi_state(affi_state);

		dao.update(affiVO);

		return affiVO;
	}

	public AffiVO updateAffiNoImg(Integer affi_no, String bus_no,
			String rest_name, String rest_addr, String rest_tel,
			String rest_mobil, String rest_mail, String rest_web,
			String rest_intro, String affi_state) {
		
		AffiVO affiVO = new AffiVO();
		affiVO.setAffi_no(affi_no);
		affiVO.setBus_no(bus_no);
		affiVO.setRest_name(rest_name);
		affiVO.setRest_addr(rest_addr);
		affiVO.setRest_tel(rest_tel);
		affiVO.setRest_mobil(rest_mobil);
		// affiVO.setRest_photo(rest_photo);
		affiVO.setRest_mail(rest_mail);
		affiVO.setRest_web(rest_web);
		affiVO.setRest_intro(rest_intro);
		affiVO.setAffi_state(affi_state);
		dao.update(affiVO);

		return affiVO;
	}

	public void deleteAffi(Integer affi_no) {
		dao.delete(affi_no);
	}

	public AffiVO getOneAffi(Integer affi_no) {
		return dao.findByPrimaryKey(affi_no);
	}

	public List<AffiVO> getAll() {
		return dao.getAll();
	}
}
