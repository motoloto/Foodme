package com.collection.model;

import java.util.List;

public interface CollectionDAO_interface {

		 public int insert(CollectionVO collectionVO);
	     public int update(CollectionVO collectionVO);
	     public int delete(Integer MEM_NO,Integer REST_NO);
	     public List<CollectionVO>  findByPrimaryKey(Integer MEM_NO);
	     public List<CollectionVO> getAll();
	     public CollectionVO findOneClc(Integer mem_no, Integer rest_no); 
			
	

	
}
