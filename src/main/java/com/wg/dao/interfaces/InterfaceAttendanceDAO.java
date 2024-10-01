package com.wg.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.wg.model.Attendance;

public interface InterfaceAttendanceDAO {
	boolean addAttendance(Attendance attendance) throws SQLException, ClassNotFoundException;

	List<Attendance> viewAttendanceByStandard(int standard) throws SQLException, ClassNotFoundException;

	List<Attendance> viewAttendanceById(String studentId) throws SQLException, ClassNotFoundException;
}
