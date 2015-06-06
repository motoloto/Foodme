package com.act.model;

import java.util.List;

public class ActService {

	private ActDAO_interface dao;

	public ActService() {
		dao = new ActDAO();
	}

	public ActVO addAct(Integer rest_no, String act_name, String act_cont,
			String act_time, String act_state, byte[] act_photo) {

		ActVO actVO = new ActVO();

		actVO.setRest_no(rest_no);
		actVO.setAct_name(act_name);
		actVO.setAct_cont(act_cont);
		actVO.setAct_time(act_time);
		actVO.setAct_state(act_state);
		actVO.setAct_photo(act_photo);
		
		dao.insert(actVO);

		return actVO;
	}

	public ActVO updateAct(Integer act_no,Integer rest_no, String act_name, String act_cont,
			String act_time, String act_state, byte[] act_photo) {

		ActVO actVO = new ActVO();
		
		actVO.setAct_no(act_no);
		actVO.setRest_no(rest_no);
		actVO.setAct_name(act_name);
		actVO.setAct_cont(act_cont);
		actVO.setAct_time(act_time);
		actVO.setAct_state(act_state);
		actVO.setAct_photo(act_photo);

		dao.update(actVO);

		return actVO;
	}
	
	public ActVO updateActNoImg(Integer act_no,Integer rest_no, String act_name, String act_cont,
			String act_time, String act_state) {

		ActVO actVO = new ActVO();
		
		actVO.setAct_no(act_no);
		actVO.setRest_no(rest_no);
		actVO.setAct_name(act_name);
		actVO.setAct_cont(act_cont);
		actVO.setAct_time(act_time);
		actVO.setAct_state(act_state);
		
		dao.update(actVO);

		return actVO;
	}


	public void deleteAct(Integer act_no) {
		dao.delete(act_no);
	}

	public ActVO getOneAct(Integer act_no) {
		return dao.findByPrimaryKey(act_no);
	}
	public List<ActVO> getActOfRest(Integer rest_no) {
		return dao.findLisActByRest_no(rest_no); 
	}
	public List<ActVO> getOnStateActOfRest(Integer rest_no) {
		return dao.findListOnStateOfRest(rest_no);
	}

	public List<ActVO> getAll() {
		return dao.getAll();
	}
}
