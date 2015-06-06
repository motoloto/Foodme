package com.dish.model;

import java.util.*;

public interface DishDAO_interface {
          public void insert(DishVO dishVO);
          public void update(DishVO dishVO);
          public void delete(Integer dish_no);
          public DishVO findByPrimaryKey(Integer dish_no);
          public List<DishVO> getAll();
          public List<DishVO> query(String dish_cont);
          public String[] querytext(String dish_cont);
          //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//        public List<DishVO> getAll(Map<String, String[]> map);	
          public List<DishVO> findByRest_no(Integer rest_no);
}
