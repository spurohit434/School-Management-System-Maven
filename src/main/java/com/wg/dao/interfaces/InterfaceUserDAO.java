package com.wg.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.wg.model.User;

public interface InterfaceUserDAO {

	public boolean addUser(User user) throws SQLException, ClassNotFoundException;

	public User getUserById(String userId) throws SQLException, ClassNotFoundException;

	public User getUserByUsername(String username) throws SQLException, ClassNotFoundException;

	public List<User> getClassDetails(int standard) throws SQLException, ClassNotFoundException;

	public boolean deleteUser(String id) throws SQLException, ClassNotFoundException;

	public List<User> getAllUser() throws SQLException, ClassNotFoundException;

	public boolean updateUser(User user, String userId, String columnToUpdate)
			throws SQLException, ClassNotFoundException;
}