package com.reserv.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.rest.model.ReservRecord;

public class Reservation_recordService {
	private Reservation_recordDAO_interface dao = new Reservation_recordDAO();

	public Reservation_recordService() {
		dao = new Reservation_recordDAO();
	}

	public Reservation_recordVO insertReservation_record(Integer mem_no,
			Integer rest_no, Integer count, String date, Integer period,
			String odr_seqnum, ReservRecord rest_reserv) {
		Reservation_recordVO reservation_recordVO = new Reservation_recordVO();
		reservation_recordVO.setMem_no(mem_no);
		reservation_recordVO.setRest_no(rest_no);
		reservation_recordVO.setCount(count);
		reservation_recordVO.setReservation_day(date);
		reservation_recordVO.setPeriod(period);
		reservation_recordVO.setOdr_seqnum(odr_seqnum);
		reservation_recordVO.setSeating("1");
		dao.insertReservation_record(reservation_recordVO, rest_reserv);

		return reservation_recordVO;
	}

	public Reservation_recordVO updateReservation_record(Integer rec_no,
			Integer mem_no, Integer rest_no, Integer count, String date,
			Integer period, String odr_seqnum, String seating) {

		Reservation_recordVO reservation_recordVO = new Reservation_recordVO();

		reservation_recordVO.setRec_no(rec_no);
		reservation_recordVO.setMem_no(mem_no);
		reservation_recordVO.setRest_no(rest_no);
		reservation_recordVO.setCount(count);
		reservation_recordVO.setReservation_day(date);
		reservation_recordVO.setPeriod(period);
		reservation_recordVO.setOdr_seqnum(odr_seqnum);
		reservation_recordVO.setSeating(seating);

		dao.update(reservation_recordVO);

		return reservation_recordVO;

	}

	public void deleteReservation_record(Integer rec_no) {
		dao.delete(rec_no);
	}

	public Reservation_recordVO getOneReservationRecord(Integer rec_no) {
		return dao.findByPrimaryKey(rec_no);
	}

	// 根據輸入日期查詢訂位紀錄的方法
	public List<Reservation_recordVO> getReservationRecord(String date,
			Integer rest_no) {
		return dao.findRecordByReservationTime(date, rest_no);
	}

	public List<Reservation_recordVO> getAll() {
		return dao.getAll();
	}

	public List<Reservation_recordVO> getAllByMem(Integer mem_no) {
		return dao.getAll();
	}

	public ReservRecord getReservFromARest(Integer rest_no) {
		return dao.getReservFromARest(rest_no);
	}

	public List<ReservRecord> getReservFromAllRest(Map<String, String[]> map) {
		return dao.getReservFromAllRest(map);

	}

	public void updateReservFromARest(ReservRecord rest_reserv) {
		dao.updateReservFromARest(rest_reserv);
	}

	// 後端根據輸入會員編號查詢訂位紀錄的方法
	public List<Reservation_recordVO> getReservationRecord(Integer mem_no) {
		return dao.findRecordByMemNo(mem_no);
	}

	// 餐廳端根據輸入會員編號查詢訂位紀錄的方法
	public List<Reservation_recordVO> getReservationRecord(Integer mem_no,
			Integer rest_no) {
		return dao.findRecordByMemNo(mem_no, rest_no);
	}

	// 餐廳端根據輸入會員編號與日期查詢訂位紀錄的方法
	public List<Reservation_recordVO> getReservationRecord(String date,
			Integer mem_no, Integer rest_no) {
		return dao.findRecordByMemNoAndTime(date, mem_no, rest_no);
	}

	// 前端根據登入會員帳號查詢訂位紀錄的方法
	public List<Reservation_recordVO> getReservationRecordByMemAccount(
			String mem_account) {
		return dao.findRecordByMemAccount(mem_account);
	}

	// 前端根據餐廳編號查詢餐廳名稱的方法
	public String getRestNameByRestNo(Integer rest_no) {
		return dao.findRestNameByRestNo(rest_no);
	}

	//後端根據輸入會員編號查詢訂位紀錄的方法
	public List<Reservation_recordVO> getReservationRecordByMemNo(Integer mem_no) {
		return dao.findRecordByMemNo(mem_no);
	}
	//後端根據輸入會員編號查詢訂位紀錄的方法
	public List<Reservation_recordVO> getReservationRecordByRestNo(Integer rest_no) {
		return dao.findRecordByRestNo(rest_no);
	}
	//餐廳端修改是否入座的方法(根據訂位日期、時段、會員編號)
	public Integer changeSeating(String seating,String date,Integer period,Integer mem_no)
	{
		return dao.changeSeating(seating,date, period, mem_no);
	}
}
