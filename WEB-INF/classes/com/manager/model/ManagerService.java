package com.manager.model;

import java.util.List;

public class ManagerService {
	private ManagerDAO_interface dao;

	public ManagerService() {
		dao = new ManagerDAO();
	
	}

	public ManagerVO addMgr(String mgr_account, String mgr_pwd,
			String mgr_name, String mgr_mail, String mgr_phone) {
		ManagerVO managerVO = new ManagerVO();

		
		managerVO.setMgr_account(mgr_account);
		managerVO.setMgr_pwd(mgr_pwd);
		managerVO.setMgr_name(mgr_name);
		managerVO.setMgr_mail(mgr_mail);
		managerVO.setMgr_phone(mgr_phone);

		dao.insert(managerVO);
		return managerVO;
	}

	public List<ManagerVO> getAll() {
		return dao.getAll();

	}

	public void delete(int mgr_no) {
		dao.delete(mgr_no);
	}

	public ManagerVO getOneManager(Integer mgr_no) {
		return dao.findByPrimaryKey(mgr_no);
	}

	public void updateManager(String mgr_pwd,
			String mgr_name, String mgr_mail, String mgr_phone,Integer mgr_no) {
		ManagerVO managerVO=new ManagerVO();
		managerVO.setMgr_pwd(mgr_pwd);
		managerVO.setMgr_name(mgr_name);
		managerVO.setMgr_mail(mgr_mail);
		managerVO.setMgr_phone(mgr_phone);
		managerVO.setMgr_no(mgr_no);
		
		dao.update(managerVO);
	}
	public ManagerVO checkManager(String mgr_account){
		return dao.checkAccount(mgr_account);
		
		
	}

}
