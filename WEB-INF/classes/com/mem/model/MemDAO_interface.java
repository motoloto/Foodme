package com.mem.model;

import java.util.List;

public interface MemDAO_interface {

	 public int insert(MemVO memVO);
     public int update(MemVO memVO);
     public int delete(Integer mem_no);
     public MemVO findByPrimaryKey(String mem_account);
     public List<MemVO> getAll(); 
		
}
