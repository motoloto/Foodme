package com.dish.model;

import java.util.List;

public class DishService {

	private DishDAO_interface dao;

	public DishService() {
		dao = new DishDAO();
	}

	public DishVO addDish(Integer rest_no, String dish_name, String dish_cont, Integer dish_price, String dish_state,byte[] dish_pic) {

		DishVO dishVO = new DishVO();

//		dishVO.setDish_no(dish_no);
		dishVO.setRest_no(rest_no);
		dishVO.setDish_name(dish_name);
		dishVO.setDish_cont(dish_cont);
		dishVO.setDish_price(dish_price);
		dishVO.setDish_state(dish_state);
		dishVO.setDish_pic(dish_pic);
		dao.insert(dishVO);

		return dishVO;
	}

	public DishVO updateDish(String dish_name, String dish_cont, String dish_state, Integer dish_price, Integer dish_no, Integer rest_no, byte[] dish_pic) {

		DishVO dishVO = new DishVO();
		dishVO.setDish_name(dish_name);
		dishVO.setDish_cont(dish_cont);
		dishVO.setDish_state(dish_state);
		dishVO.setDish_price(dish_price);		
		dishVO.setDish_no(dish_no);
		dishVO.setRest_no(rest_no);
		dishVO.setDish_pic(dish_pic);
		
		dao.update(dishVO);

		return dishVO;
	}

	public void deleteDish(Integer dish_no) {
		dao.delete(dish_no);
	}

	public DishVO getOneDish(Integer dish_no) {
		return dao.findByPrimaryKey(dish_no);
	}

	public List<DishVO> getOneRest(Integer rest_no) {
		return dao.findByRest_no(rest_no);
	}
	
	public List<DishVO> getAll() {
		return dao.getAll();
	}
	
	public List<DishVO> query(String dish_cont){
		return dao.query(dish_cont);
	}
	
	public String[] querytext(String dish_cont){
		return dao.querytext(dish_cont);
	}
}
