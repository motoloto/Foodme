package com.restClass.model;

import java.sql.SQLException;
import java.util.List;

public interface ClassDAO_interface {
    public int insert(ClassVO vo);
    public int update(ClassVO vo);
    public int delete(Integer class_no) throws ClassNotFoundException, SQLException;
    public ClassVO findByPrimaryKey(Integer class_no) throws ClassNotFoundException, SQLException;
    public List<ClassVO> getAll() throws ClassNotFoundException, SQLException;
}
