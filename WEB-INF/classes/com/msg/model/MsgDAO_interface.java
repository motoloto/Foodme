package com.msg.model;

import java.util.*;

public interface MsgDAO_interface {
          public void insert(MsgVO messVO);
          public void update(MsgVO messVO);
          public void delete(Integer mess_no);
          public MsgVO findByPrimaryKey(Integer mess_no);
          public List<MsgVO> getAll();
          //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//        public List<MessVO> getAll(Map<String, String[]> map); 
}
