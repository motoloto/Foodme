package com.reserv.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.rest.model.ReservRecord;

public interface Reservation_recordDAO_interface {
    public void insertReservation_record(Reservation_recordVO Res_record,ReservRecord rest_reserv);
    public int update(Reservation_recordVO Res_record);
    public int delete(Integer Rec_no);
    public Reservation_recordVO findByPrimaryKey(Integer Rec_no);
    public List<Reservation_recordVO> getAll();
	public List<Reservation_recordVO> findRecordByReservationTime(String date,Integer rec_no);
	public List<Reservation_recordVO> getAllByMem(Integer mem_no);
	public List<ReservRecord> getReservFromAllRest(Map<String, String[]> map);
	public  void updateReservFromARest(ReservRecord rest_reserv);
	public ReservRecord getReservFromARest(Integer rest_no);
	public List<Reservation_recordVO> findRecordByMemNo(Integer mem_no);
	public List<Reservation_recordVO> findRecordByMemNo(Integer mem_no,
			Integer rest_no);
	public List<Reservation_recordVO> findRecordByMemNoAndTime(String date,
			Integer mem_no, Integer rest_no);
	public List<Reservation_recordVO> findRecordByMemAccount(String mem_account);

	public String findRestNameByRestNo(Integer rest_no);
	public List<Reservation_recordVO> findRecordByRestNo(Integer rest_no);
	public Integer changeSeating(String seating,String date,Integer period,Integer mem_no);
}
