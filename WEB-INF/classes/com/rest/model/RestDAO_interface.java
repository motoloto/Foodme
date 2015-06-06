package com.rest.model;

import java.util.*;

public interface RestDAO_interface {
          public void insert(RestVO restVO);
          public void update(RestVO restVO);
          public void delete(Integer rest_no);
          public RestVO findByPrimaryKey(Integer rest_no);
          public List<RestVO> getAll();
          //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//        public List<RestVO> getAll(Map<String, String[]> map); 
		public void update_state(Integer rest_no, String rest_state);
		public List<RestVO> getAllScore();
		public ReservRecord getOneReserv(Integer rest_no);

		public List<ReservRecord> getAllReserv(Map<String, String[]> map);
		public  void updateOneReserv(ReservRecord rest_reserv);
		public RestVO updateResSeat(RestVO vo);
		public RestVO checkAccount(String rest_account);
}

