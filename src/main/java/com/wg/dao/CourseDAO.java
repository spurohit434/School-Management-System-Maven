package com.wg.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wg.constants.CourseConstants;
import com.wg.dao.interfaces.InterfaceCourseDAO;
import com.wg.model.Course;

public class CourseDAO extends GenericDAO<Course> implements InterfaceCourseDAO {

	public CourseDAO() {
	}

	@Override
	protected Course mapResultSetToEntity(ResultSet resultSet) throws SQLException {
		Course course = new Course();
		course.setCourseId(resultSet.getString(CourseConstants.COURSE_ID));
		course.setCourseName(resultSet.getString(CourseConstants.COURSE_NAME));
		course.setStandard(resultSet.getInt(CourseConstants.STANDARD));
		return course;
	}

	public Course getCourse(String courseId) throws SQLException, ClassNotFoundException {
		String selectSQL = "SELECT * FROM Course WHERE courseId = \"" + courseId + "\"";
		return executeGetQuery(selectSQL);
	}

	public List<Course> getAllCourses() throws SQLException, ClassNotFoundException {
		String selectSQL = "SELECT * FROM Course";
		return executeGetAllQuery(selectSQL);
	}

	public boolean deleteCourse(String id) throws SQLException, ClassNotFoundException {
		String selectSQL = "DELETE FROM Course WHERE courseId = \"" + id + "\"";
		return executeQuery(selectSQL);
	}

	public boolean addCourse(Course course) throws SQLException, ClassNotFoundException {
		String sqlQuery = String.format("INSERT INTO Course (courseId, courseName, standard) VALUES ('%s','%s','%s')",
				course.getCourseId(), course.getCourseName(), course.getStandard());
		return executeQuery(sqlQuery);
	}

	public boolean updateCourse(Course course, String columnToUpdate) throws SQLException, ClassNotFoundException {
		Map<String, Object> fieldMap = new HashMap<>();
		fieldMap.put("CourseId", course.getCourseId());
		fieldMap.put("CourseName", course.getCourseName());
		fieldMap.put("Standard", course.getStandard());
		if (fieldMap.containsKey(columnToUpdate)) {
			Object value = fieldMap.get(columnToUpdate);
			String sqlQuery = String.format("UPDATE Course SET %s = '%s' WHERE courseId = '%s'", columnToUpdate, value,
					course.getCourseId());
			// System.out.println(sqlQuery);
			return executeQuery(sqlQuery);
		}
		return false;
	}
}