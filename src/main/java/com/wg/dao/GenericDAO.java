package com.wg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wg.config.DatabaseConnection;

public abstract class GenericDAO<T> {

	private Connection connection;

	public GenericDAO() {
		try {
			this.connection = DatabaseConnection.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;

	public T executeGetQuery(String query) throws SQLException, ClassNotFoundException {
		T entity = null;
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				entity = mapResultSetToEntity(resultSet);
			}
		}
		return entity;
	}

	public List<T> executeGetAllQuery(String query) throws SQLException, ClassNotFoundException {
		List<T> entities = new ArrayList<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				entities.add(mapResultSetToEntity(resultSet));
			}
		}
		return entities;
	}

	public boolean executeQuery(String query) throws SQLException, ClassNotFoundException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			return preparedStatement.executeUpdate() > 0;
		}
	}
}