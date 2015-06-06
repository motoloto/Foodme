package com.rest.model;

import java.util.List;
import java.util.Map;

public class RestService {

	private RestDAO_interface dao;
	

	public RestService() {
		dao = new RestDAO();
	}
	public  ReservRecord  getOneReserv(Integer rest_no ){
		return dao.getOneReserv(rest_no);
	}
	public void  updateOneReserv(ReservRecord rest_reserv ){
		 dao.updateOneReserv(rest_reserv);
	}
	public void  updateRestByScore(RestVO restVO){
		dao.update(restVO);
}
	public RestVO addRest(
			Integer affi_no,	
			String rest_account,
			String rest_psw,
			String bus_no,
			String rest_name,
			String rest_addr,
			String rest_tel,
			String rest_mail,
			String rest_web,
			String rest_intro,			
			byte[] rest_photo			
			) 
		{
		RestVO restVO = new RestVO();
		
		restVO.setAffi_no(affi_no);
		restVO.setRest_account(rest_account);
		restVO.setRest_psw(rest_psw);
		restVO.setBus_no(bus_no);
		restVO.setRest_name(rest_name);
		restVO.setRest_addr(rest_addr);
		restVO.setRest_tel(rest_tel);
		restVO.setRest_mail(rest_mail);
		restVO.setRest_web(rest_web);
		restVO.setRest_intro(rest_intro);
		restVO.setRest_photo(rest_photo);
		
		dao.insert(restVO);

		return restVO;
		}

	public void  updateRest(	
			String rest_account,
			String rest_psw,
			String bus_no,
			String rest_name,
			String rest_addr,
			String rest_tel,
			String rest_mail,
			String rest_web,
			String rest_opentime,			
			byte[] rest_photo,
			String rest_state,
			
			Integer reserved_totalset,
			Integer rest_no)
	{
		RestVO restVO = new RestVO();
		//取得舊有餐廳資料再做更新
		restVO=dao.findByPrimaryKey(rest_no);
		
		
		restVO.setRest_account(rest_account);
		restVO.setRest_psw(rest_psw);
		restVO.setBus_no(bus_no);
		restVO.setRest_name(rest_name);
		restVO.setRest_addr(rest_addr);
		restVO.setRest_tel(rest_tel);
		restVO.setRest_mail(rest_mail);
		restVO.setRest_web(rest_web);
		restVO.setRest_opentime(rest_opentime);		
		restVO.setRest_photo(rest_photo);
		restVO.setRest_state(rest_state);			
		
		restVO.setReserved_totalset(reserved_totalset);
		restVO.setRest_no(rest_no);
		
		dao.update(restVO);
	}
	public void deleteRest(Integer rest_no) {
		dao.delete(rest_no);
	}

	public RestVO getOneRest(Integer rest_no) {
		return dao.findByPrimaryKey(rest_no);
	}

	public List<RestVO> getAll() {
		return dao.getAll();
	}
	
	public List<RestVO> getAllScore(){
		return dao.getAllScore();
	}
	public void update_state(Integer rest_no, String rest_state) {
		dao.update_state(rest_no,rest_state);
		
	}
	//複合查詢用
	public List<ReservRecord> getAllReserv(Map<String, String[]> map) {
		
		return dao.getAllReserv(map);
	}

	//為修改時段可訂位數而新增的方法(service層)
		public RestVO updateReservation(
				//屬性有餐廳編號與7*6可訂位數，共43個屬性
				Integer rest_no,
				Integer reserved_mon1,
				Integer reserved_mon2,
				Integer reserved_mon3,
				Integer reserved_mon4,
				Integer reserved_mon5,
				Integer reserved_mon6,
				Integer reserved_tue1,
				Integer reserved_tue2,
				Integer reserved_tue3,
				Integer reserved_tue4,
				Integer reserved_tue5,
				Integer reserved_tue6,
				Integer reserved_wed1,
				Integer reserved_wed2,
				Integer reserved_wed3,
				Integer reserved_wed4,
				Integer reserved_wed5,
				Integer reserved_wed6,
				Integer reserved_thu1,
				Integer reserved_thu2,
				Integer reserved_thu3,
				Integer reserved_thu4,
				Integer reserved_thu5,
				Integer reserved_thu6,
				Integer reserved_fri1,
				Integer reserved_fri2,
				Integer reserved_fri3,
				Integer reserved_fri4,
				Integer reserved_fri5,
				Integer reserved_fri6,
				Integer reserved_sat1,
				Integer reserved_sat2,
				Integer reserved_sat3,
				Integer reserved_sat4,
				Integer reserved_sat5,
				Integer reserved_sat6,
				Integer reserved_sun1,
				Integer reserved_sun2,
				Integer reserved_sun3,
				Integer reserved_sun4,
				Integer reserved_sun5,
				Integer reserved_sun6
				
				) {

			RestVO restVO = new RestVO();

			restVO.setRest_no(rest_no);//需要有餐廳編號做辨認
			restVO.setReserved_mon1(reserved_mon1);
			restVO.setReserved_mon2(reserved_mon2);
			restVO.setReserved_mon3(reserved_mon3);
			restVO.setReserved_mon4(reserved_mon4);
			restVO.setReserved_mon5(reserved_mon5);
			restVO.setReserved_mon6(reserved_mon6);
			restVO.setReserved_tue1(reserved_tue1);
			restVO.setReserved_tue2(reserved_tue2);
			restVO.setReserved_tue3(reserved_tue3);
			restVO.setReserved_tue4(reserved_tue4);
			restVO.setReserved_tue5(reserved_tue5);
			restVO.setReserved_tue6(reserved_tue6);
			restVO.setReserved_wed1(reserved_wed1);
			restVO.setReserved_wed2(reserved_wed2);
			restVO.setReserved_wed3(reserved_wed3);
			restVO.setReserved_wed4(reserved_wed4);
			restVO.setReserved_wed5(reserved_wed5);
			restVO.setReserved_wed6(reserved_wed6);
			restVO.setReserved_thu1(reserved_thu1);
			restVO.setReserved_thu2(reserved_thu2);
			restVO.setReserved_thu3(reserved_thu3);
			restVO.setReserved_thu4(reserved_thu4);
			restVO.setReserved_thu5(reserved_thu5);
			restVO.setReserved_thu6(reserved_thu6);
			restVO.setReserved_fri1(reserved_fri1);
			restVO.setReserved_fri2(reserved_fri2);
			restVO.setReserved_fri3(reserved_fri3);
			restVO.setReserved_fri4(reserved_fri4);
			restVO.setReserved_fri5(reserved_fri5);
			restVO.setReserved_fri6(reserved_fri6);
			restVO.setReserved_sat1(reserved_sat1);
			restVO.setReserved_sat2(reserved_sat2);
			restVO.setReserved_sat3(reserved_sat3);
			restVO.setReserved_sat4(reserved_sat4);
			restVO.setReserved_sat5(reserved_sat5);
			restVO.setReserved_sat6(reserved_sat6);
			restVO.setReserved_sun1(reserved_sun1);
			restVO.setReserved_sun2(reserved_sun2);
			restVO.setReserved_sun3(reserved_sun3);
			restVO.setReserved_sun4(reserved_sun4);
			restVO.setReserved_sun5(reserved_sun5);
			restVO.setReserved_sun6(reserved_sun6);
			
			dao.updateResSeat(restVO);//呼叫DAO層的update
			return restVO;
		}
		public RestVO checkManager(String rest_account) {
			return dao.checkAccount(rest_account);
		}
}
