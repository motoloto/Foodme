package com.mem.model;


import java.sql.Date;
import java.util.List;

public class MemService {

	private MemDAO_interface dao;

	public MemService() {
		dao = new MemDAO();
	}

	public MemVO addMem(         
			
			  String  mem_account,
			  String  mem_pwd,
			  String  mem_name,
			  String  mem_mail,
			  String  mem_phone,
			  String  mem_adrs,
			  Date    mem_birthdate,
			  String  mem_sex,
			  String  mem_nickname,
			  Integer mem_illtms,
			  byte[]  mem_pic      ) {

		MemVO memVO = new MemVO();

		memVO.setMem_account(mem_account);
		memVO.setMem_pwd(mem_pwd);
		memVO.setMem_name(mem_name);
		memVO.setMem_mail(mem_mail);
		memVO.setMem_phone(mem_phone);
		memVO.setMem_adrs(mem_adrs);
		memVO.setMem_birthdate(mem_birthdate);
		memVO.setMem_sex(mem_sex);
		memVO.setMem_nickname(mem_nickname);
		memVO.setMem_illtms(mem_illtms);
		memVO.setMem_pic(mem_pic);
		dao.insert(memVO);

		return memVO;
	}

	public MemVO updateMem(          
			
			  Integer mem_no,
			  String  mem_account,
			  String  mem_pwd,
			  String  mem_name,
			  String  mem_mail,
			  String  mem_phone,
			  String  mem_adrs,
			  Date    mem_birthdate,
			  String  mem_sex,
			  String  mem_nickname,
			  Integer  mem_illtms,
			  byte[]  mem_pic      ) {

		MemVO memVO = new MemVO();

		memVO.setMem_no(mem_no);
		memVO.setMem_account(mem_account);
		memVO.setMem_pwd(mem_pwd);
		memVO.setMem_name(mem_name);
		memVO.setMem_mail(mem_mail);
		memVO.setMem_phone(mem_phone);
		memVO.setMem_adrs(mem_adrs);
		memVO.setMem_birthdate(mem_birthdate);
		memVO.setMem_sex(mem_sex);
		memVO.setMem_nickname(mem_nickname);
		memVO.setMem_illtms(mem_illtms);
		memVO.setMem_pic(mem_pic);

		dao.update(memVO);

		return memVO;
	}

	public void deleteMem(Integer memno) {
		dao.delete(memno);
	}

	public MemVO getOneMem(String mem_account) {
		return dao.findByPrimaryKey(mem_account);
	}

	public List<MemVO> getAll() {
		return dao.getAll();
	}
}

