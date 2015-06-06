package com.manager.model;

import java.util.List;

import com.manager.model.*;;

public interface ManagerDAO_interface {
	public  void insert(ManagerVO managerVO) ;
	public  void delete(Integer mgr_no);
	public  void update(ManagerVO managerVO);
	public ManagerVO findByPrimaryKey(Integer mgr_no);
    public List<ManagerVO> getAll();
    public ManagerVO checkAccount(String mgr_account) ;

}
