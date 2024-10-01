package com.wg.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.wg.constants.AttendanceConstants;
import com.wg.dao.interfaces.InterfaceAttendanceDAO;
import com.wg.model.Attendance;
import com.wg.model.Status;

public class AttendanceDAO extends GenericDAO<Attendance> implements InterfaceAttendanceDAO {
	public AttendanceDAO() {
	}

	@Override
	protected Attendance mapResultSetToEntity(ResultSet resultSet) throws SQLException {
		Attendance attendance = new Attendance();
		attendance.setStudentId(resultSet.getString(AttendanceConstants.STUDENT_ID_COLUMN));
		attendance.setStandard(resultSet.getInt(AttendanceConstants.STANDARD_COLUMN));
		attendance.setDate(resultSet.getDate(AttendanceConstants.DATE_COLUMN).toLocalDate());
		attendance.setStatus(Status.valueOf(resultSet.getString(AttendanceConstants.STATUS_COLUMN)));
		return attendance;
	}

	public boolean addAttendance(Attendance attendance) throws SQLException, ClassNotFoundException {
		String query = "INSERT INTO Attendance (studentId, standard, date, status) VALUES ('"
				+ attendance.getStudentId() + "', " + attendance.getStandard() + ", '" + attendance.getDate() + "', '"
				+ attendance.getStatus() + "')";
		return executeQuery(query);
	}

	public List<Attendance> viewAttendanceByStandard(int standard) throws SQLException, ClassNotFoundException {
		String query = "SELECT * FROM Attendance WHERE standard = " + standard;
		return executeGetAllQuery(query);
	}

	public List<Attendance> viewAttendanceById(String studentId) throws SQLException, ClassNotFoundException {
		String query = "SELECT * FROM Attendance WHERE studentId = '" + studentId + "'";
		return executeGetAllQuery(query);
	}
}