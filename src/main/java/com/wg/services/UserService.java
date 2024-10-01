package com.wg.services;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import com.wg.dao.UserDAO;
import com.wg.dao.interfaces.InterfaceUserDAO;
import com.wg.helper.LoggingUtil;
import com.wg.helper.PasswordUtil;
import com.wg.helper.UnauthenticatedException;
import com.wg.model.User;

public class UserService {
	private InterfaceUserDAO userDAO = new UserDAO();
	Logger logger = LoggingUtil.getLogger(UserService.class);

	public UserService() {
	}

	public boolean addUser(User user) {
		boolean flag = false;
		System.out.println("Test add mock user");
		try {
			flag = userDAO.addUser(user);
			return flag;
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			System.out.println("Error adding user: " + e.getMessage());
		}
		return flag;
	}

	public User getUserById(String userId) {
		User user = null;
		try {
			user = userDAO.getUserById(userId);
			if (user == null) {
				System.out.println("User not found");
			}
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			System.out.println("Error getting user: " + e.getMessage());

		}
		return user;
	}

	public List<User> getClassDetails(int standard) {
		List<User> list = null;
		try {
			list = userDAO.getClassDetails(standard);
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			System.out.println("Error getting class details: " + e.getMessage());
		}
		return list;
	}

	public boolean deleteUser(String id) {
		User user = null;
		try {
			user = userDAO.getUserById(id);
			if (user == null) {
				System.out.println("User not found");
				return false;
			}
			if (user.getRole().toString().equalsIgnoreCase("Admin")) {
				System.out.println("Admin cannot be deleted");
				return false;
			}
			return userDAO.deleteUser(id);
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			System.out.println("Error deleting user: " + e.getMessage());
		}
		return false;
	}

	public User authenticateUser(String username, String password) {
		User user = null;
		try {
			user = userDAO.getUserByUsername(username);
			if (user.getUserId() == null) {
				System.out.println("User not found");
				throw new UnauthenticatedException("Invalid Credentials!");
			} else {
				String checkName = user.getUsername();
				String checkPass = user.getPassword();
				PasswordUtil passwordUtil = new PasswordUtil();
				if (checkName.equals(username) && passwordUtil.checkPassword(password, checkPass)) {
					logger.info("Authentication successful");
					System.out.println("Authentication successful");
					return user;
				} else if (checkPass != password) {
					logger.info("Authentication unsuccessful password did not match");
					System.out.println("Enter correct password");
					return null;
				} else {
					logger.info("Authentication unsuccessful");
					System.out.println("Authentication Unsuccessful");
					return null;
				}
			}
		} catch (ClassNotFoundException | SQLException | UnauthenticatedException | NullPointerException e) {
			logger.severe(e.getMessage());
			System.out.println("Error authenticating user: " + e.getMessage());
		}
		return null;
	}

	public List<User> getAllUser() {
		List<User> users = null;
		try {
			users = userDAO.getAllUser();
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return users;
	}

	public User getUserByUsername(String username) {
		User user = null;
		try {
			user = userDAO.getUserByUsername(username);
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return user;
	}

	public boolean updateUser(User user, String userId, String columnToUpdate) {
		try {
			return userDAO.updateUser(user, userId, columnToUpdate);
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}