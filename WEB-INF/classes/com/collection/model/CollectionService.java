package com.collection.model;

import java.util.List;

public class CollectionService {

	private CollectionDAO_interface dao;

	public CollectionService() {
		dao = new CollectionDAO();
	}

	public CollectionVO addCollection(Integer mem_no, Integer rest_no) {

		CollectionVO CollectionVO = new CollectionVO();

		CollectionVO.setMem_no(mem_no);
		CollectionVO.setRest_no(rest_no);
		dao.insert(CollectionVO);

		return CollectionVO;
	}

	public CollectionVO updateCollection(Integer mem_no, Integer rest_no) {

		CollectionVO CollectionVO = new CollectionVO();

		CollectionVO.setMem_no(mem_no);
		CollectionVO.setRest_no(rest_no);
		
		dao.update(CollectionVO);

		return CollectionVO;
	}

	public void deleteCollection(Integer mem_no, Integer rest_no) {
		dao.delete(mem_no,rest_no);
	}

	/*��o�Y�|��Ҧ������\�U*/
	public List<CollectionVO> getOneCollection(Integer mem_no) {
		return dao.findByPrimaryKey(mem_no);
	}

	public List<CollectionVO> getAll() {
		return dao.getAll();
	}
	public CollectionVO getOneClc(Integer mem_no, Integer rest_no) {
		return dao.findOneClc( mem_no, rest_no);
	}
}

