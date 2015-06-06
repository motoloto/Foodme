package com.act.model;

import java.util.*;

public interface ActDAO_interface {
          public void insert(ActVO actVO);
          public void update(ActVO actVO);
          public void delete(Integer act_no);
          public ActVO findByPrimaryKey(Integer act_no);
          public List<ActVO> getAll();
          public List<ActVO> findLisActByRest_no(Integer rest_no);
          public List<ActVO> findListOnStateOfRest(Integer rest_no);
          //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//        public List<ActVO> getAll(Map<String, String[]> map); 
}
