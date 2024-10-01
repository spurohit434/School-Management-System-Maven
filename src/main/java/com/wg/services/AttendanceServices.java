package com.wg.services;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import com.wg.dao.AttendanceDAO;
import com.wg.dao.interfaces.InterfaceAttendanceDAO;
import com.wg.helper.LoggingUtil;
import com.wg.model.Attendance;

public class AttendanceServices {
	private InterfaceAttendanceDAO attendanceDAO = new AttendanceDAO();
	Logger logger = LoggingUtil.getLogger(AttendanceServices.class);

	public AttendanceServices() {

	}

	public List<Attendance> viewAttendanceByStandard(int standard) {
		List<Attendance> list = null;
		try {
			list = attendanceDAO.viewAttendanceByStandard(standard);
		} catch (SQLException | ClassNotFoundException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public List<Attendance> viewAttendanceById(String studentId) {
		List<Attendance> list = null;
		try {
			list = attendanceDAO.viewAttendanceById(studentId);
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public boolean addAttendance(Attendance attendance) {
		boolean flag = false;
		try {
			flag = attendanceDAO.addAttendance(attendance);
			return flag;
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

}
