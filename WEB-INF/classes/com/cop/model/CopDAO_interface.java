package com.cop.model;

import java.util.List;
import java.util.Map;



public interface CopDAO_interface {

	
	public int insert(CopVO copVO);
    public int update(CopVO copVO);
    public int delete(Integer cop_NO);
    public CopVO findByPrimaryKey(Integer cop_NO);
    public CopVO findCopByRest_no(Integer rest_NO);//找尋此餐廳正在上架中的餐劵
    public List<CopVO> getAll(); 
    public List<CopVO> getCopByRest_no(Integer rest_NO);//廠商結款用:用餐廳編號找到他發行的餐劵 
    public CopVO getOneCopByOdr(Map<String, String[]> map);
    public List<CopVO> getAll(Map<String, String[]> map);
	CopVO findLatestCop();
	CopVO findHotSaleCop(); 
}
