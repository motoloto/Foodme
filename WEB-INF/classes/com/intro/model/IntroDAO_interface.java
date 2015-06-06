package com.intro.model;

import java.util.List;

public interface IntroDAO_interface {
	public void insert(IntroVO vo);
    public void update(IntroVO vo);
    public int delete(Integer intro_no);
    public IntroVO findByPrimaryKey(Integer intro_no);
    public List<IntroVO> getAll();
    public List<IntroVO> findByForeignKey(Integer rest_no);
	public void updateOrder(Integer rest_no, Integer intro_no, Integer order_no);
}
