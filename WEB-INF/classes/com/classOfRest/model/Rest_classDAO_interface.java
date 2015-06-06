package com.classOfRest.model;

import java.util.List;

public interface Rest_classDAO_interface {
	public int insert(Rest_classVO vo);
    public int update(Rest_classVO vo);
    public int delete(Rest_classVO vo);
    public List<Rest_classVO> findByPrimaryKey(Integer rest_no);
    public List<Rest_classVO> getAll();
    public void deleteAll(Rest_classVO vo);
}
