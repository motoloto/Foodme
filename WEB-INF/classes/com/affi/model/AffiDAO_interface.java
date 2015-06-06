package com.affi.model;

import java.util.*;

public interface AffiDAO_interface {
          public void insert(AffiVO affiVO);
          public void update(AffiVO affiVO);
          public void delete(Integer affi_no);
          public AffiVO findByPrimaryKey(Integer affi_no);
          public List<AffiVO> getAll();

          //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//        public List<AffiVO> getAll(Map<String, String[]> map); 
}
