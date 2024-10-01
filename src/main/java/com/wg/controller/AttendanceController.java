package com.wg.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.wg.model.Attendance;
import com.wg.model.Status;
import com.wg.services.AttendanceServices;

public class AttendanceController {
	private final AttendanceServices attendanceService = new AttendanceServices();
	Scanner scanner = new Scanner(System.in);

//	public AttendanceController(AttendanceServices attendanceService) {
//		this.attendanceService = attendanceService;
//	}

	public AttendanceController() {
	}

	public boolean addAttendance(String studentId, int standard, LocalDate date, Status status) {
		Attendance attendance = new Attendance(studentId, standard, date, status);
		return attendanceService.addAttendance(attendance);
	}

	public List<Attendance> viewAttendanceByStandard(int standard) {
		List<Attendance> list = null;
		list = attendanceService.viewAttendanceByStandard(standard);
		return list;
	}

	public List<Attendance> viewAttendanceById(String studentId) {
		List<Attendance> list = null;
		list = attendanceService.viewAttendanceById(studentId);
		return list;
	}
}
