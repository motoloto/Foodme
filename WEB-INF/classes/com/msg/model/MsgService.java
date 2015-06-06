package com.msg.model;

import java.util.List;

public class MsgService {
	private MsgDAO dao=new MsgDAO();
	
	public void insert(MsgVO msgVO){
		dao.insert(msgVO);
	}
	
	public void delete (Integer msg_no){
		dao.delete(msg_no);
	}
	
	public void update(MsgVO msgVO){
		dao.update(msgVO);
	}
	public MsgVO findByPrimaryKey(Integer msg_no){
		return dao.findByPrimaryKey(msg_no);
	}
	public  List<MsgVO> findByforeignKey(Integer rest_no){
		return dao.findByforeignKey(rest_no);
	}
}
