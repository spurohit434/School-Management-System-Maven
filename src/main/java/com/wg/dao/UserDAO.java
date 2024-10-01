package com.wg.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wg.constants.UserConstants;
import com.wg.dao.interfaces.InterfaceUserDAO;
import com.wg.model.Role;
import com.wg.model.User;

public class UserDAO extends GenericDAO<User> implements InterfaceUserDAO {
	public UserDAO() {
	}

	@Override
	public User mapResultSetToEntity(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setUserId(resultSet.getString(UserConstants.USER_ID_COLUMN));
		user.setName(resultSet.getString(UserConstants.NAME_COLUMN));
		user.setEmail(resultSet.getString(UserConstants.EMAIL_COLUMN));
		user.setUsername(resultSet.getString(UserConstants.USERNAME_COLUMN));
		user.setPassword(resultSet.getString(UserConstants.PASSWORD_COLUMN));
		user.setAge(resultSet.getInt(UserConstants.AGE_COLUMN));
		user.setGender(resultSet.getString(UserConstants.GENDER_COLUMN));
		user.setContactNumber(resultSet.getString(UserConstants.CONTACT_NO_COLUMN));
		user.setAddress(resultSet.getString(UserConstants.ADDRESS_COLUMN));
		user.setRole(Role.valueOf(resultSet.getString(UserConstants.ROLE_COLUMN)));
		user.setDOB(resultSet.getDate(UserConstants.DOB_COLUMN).toLocalDate());
		user.setStandard(resultSet.getInt(UserConstants.STANDARD_COLUMN));
		user.setRollNo(resultSet.getString(UserConstants.ROLL_NO_COLUMN));
		user.setMentorOf(resultSet.getInt(UserConstants.MENTOR_OF_COLUMN));
		return user;
	}

	public boolean addUser(User user) throws SQLException, ClassNotFoundException {
		String sqlQuery = String.format(
				"INSERT INTO User (UserId, username, name, age, password, email, role, dob, contactNumber, standard, gender, rollNo, mentorOf) VALUES ('%s','%s','%s','%s','%s','%s','%s', '%s','%s','%s','%s', '%s','%s')",
				user.getUserId(), user.getUsername(), user.getName(), user.getAge(), user.getPassword(),
				user.getEmail(), user.getRole().toString(), user.getDOB(), user.getContactNumber(), user.getStandard(),
				user.getGender(), user.getRollNo(), user.getMentorOf());

		return executeQuery(sqlQuery);
	}

	public User getUserById(String userId) throws SQLException, ClassNotFoundException {
		String selectSQL = "SELECT * FROM User WHERE userid = \"" + userId + "\"";
		return executeGetQuery(selectSQL);
	}

	public User getUserByUsername(String username) throws SQLException, ClassNotFoundException {
		String selectSQL = "SELECT * FROM User WHERE username = \"" + username + "\"";
		return executeGetQuery(selectSQL);
	}

	public List<User> getClassDetails(int standard) throws SQLException, ClassNotFoundException {
		String selectSQL = "SELECT * FROM User WHERE standard = \"" + standard + "\"";
		return executeGetAllQuery(selectSQL);
	}

	public boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
		String selectSQL = "DELETE FROM User WHERE userid = \"" + id + "\"";
		return executeQuery(selectSQL);
	}

	public List<User> getAllUser() throws SQLException, ClassNotFoundException {
		String selectSQL = "SELECT * FROM User";
		return executeGetAllQuery(selectSQL);
	}

	public boolean updateUser(User user, String userId, String columnToUpdate)
			throws SQLException, ClassNotFoundException {
		Map<String, Object> fieldMap = new HashMap<>();
		fieldMap.put("UserId", user.getUserId());
		fieldMap.put("name", user.getName());
		fieldMap.put("dob", user.getDOB());
		fieldMap.put("contactNumber", user.getContactNumber());
		fieldMap.put("role", user.getRole());
		fieldMap.put("password", user.getPassword());
		fieldMap.put("standard", user.getStandard());
		fieldMap.put("address", user.getAddress());
		fieldMap.put("username", user.getUsername());
		fieldMap.put("age", user.getAge());
		fieldMap.put("email", user.getEmail());
		fieldMap.put("gender", user.getGender());
		fieldMap.put("rollNo", user.getRollNo());
		fieldMap.put("mentorOf", user.getMentorOf());
		if (fieldMap.containsKey(columnToUpdate)) {
			Object value = fieldMap.get(columnToUpdate);
			String sqlQuery = String.format("UPDATE User SET %s = '%s' WHERE userId = '%s'", columnToUpdate, value,
					userId);
			return executeQuery(sqlQuery);
		}
		return false;
	}
}