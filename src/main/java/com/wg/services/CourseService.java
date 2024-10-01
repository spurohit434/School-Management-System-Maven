package com.wg.services;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import com.wg.dao.CourseDAO;
import com.wg.dao.interfaces.InterfaceCourseDAO;
import com.wg.helper.LoggingUtil;
import com.wg.model.Course;

public class CourseService {
	private InterfaceCourseDAO courseDAO = new CourseDAO();

	Logger logger = LoggingUtil.getLogger(CourseService.class);
//	logger.severe(e.getMessage());

	public CourseService() {

	}

	public Course getCourse(String courseId) {
		Course course = null;
		try {
			course = courseDAO.getCourse(courseId);
			if (course == null) {
				System.out.println("No course found");
			}
			return course;
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return course;
	}

	public List<Course> getAllCourses() {
		List<Course> list = null;
		try {
			list = courseDAO.getAllCourses();
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public void addCourse(Course course) {
		boolean flag = false;
		try {
			flag = courseDAO.addCourse(course);
			if (flag == true) {
				System.out.println("Course Added successfully");
			} else {
				System.out.println("Course not added");
			}
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean deleteCourse(String id) {
		Course course = null;
		try {
			course = courseDAO.getCourse(id);
			if (course != null) {
				return courseDAO.deleteCourse(id);
			} else {
				System.out.println("Course not found");
			}
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateCourse(Course course, String columnToUpdate) {
		boolean flag = false;
		try {
			flag = courseDAO.updateCourse(course, columnToUpdate);
			return flag;
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

}
