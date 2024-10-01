package com.wg.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.wg.model.CourseMarks;

public interface InterfaceCourseMarksDAO {

	public boolean addMarks(CourseMarks courseMarks) throws ClassNotFoundException, SQLException;

	public List<CourseMarks> checkMarks(String userId) throws ClassNotFoundException, SQLException;

}
