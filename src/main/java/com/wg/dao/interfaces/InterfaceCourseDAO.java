package com.wg.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.wg.model.Course;

public interface InterfaceCourseDAO {

	public Course getCourse(String courseId) throws SQLException, ClassNotFoundException;

	public List<Course> getAllCourses() throws SQLException, ClassNotFoundException;

	public boolean deleteCourse(String id) throws SQLException, ClassNotFoundException;

	public boolean addCourse(Course course) throws SQLException, ClassNotFoundException;

	public boolean updateCourse(Course course, String columnToUpdate) throws SQLException, ClassNotFoundException;

}