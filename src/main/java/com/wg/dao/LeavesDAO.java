package com.wg.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.wg.constants.LeavesConstants;
import com.wg.dao.interfaces.InterfaceLeavesDAO;
import com.wg.model.Leaves;
import com.wg.model.LeavesStatus;

public class LeavesDAO extends GenericDAO<Leaves> implements InterfaceLeavesDAO {

	public LeavesDAO() {
		super();
	}

	@Override
	protected Leaves mapResultSetToEntity(ResultSet resultSet) throws SQLException {
		Leaves leave = new Leaves();
		leave.setLeaveId(resultSet.getString(LeavesConstants.ID_COLUMN));
		leave.setUserId(resultSet.getString(LeavesConstants.USER_ID_COLUMN));
		leave.setContent(resultSet.getString(LeavesConstants.CONTENT_COLUMN));
		leave.setStatus(LeavesStatus.valueOf(resultSet.getString(LeavesConstants.STATUS_COLUMN)));
		leave.setStartDate(resultSet.getDate(LeavesConstants.START_DATE_COLUMN).toLocalDate());
		leave.setEndDate(resultSet.getDate(LeavesConstants.END_DATE_COLUMN).toLocalDate());
		return leave;
	}

	public boolean approveLeave(String userId) throws SQLException, ClassNotFoundException {
		String updateSQL = String.format("UPDATE Leaves SET status = '%s' WHERE userId = '%s'",
				LeavesStatus.Approved.toString(), userId);
		return executeQuery(updateSQL);
	}

	public boolean rejectLeave(String userId) throws SQLException, ClassNotFoundException {
		String updateSQL = String.format("UPDATE Leaves SET status = '%s' WHERE userId = '%s'",
				LeavesStatus.Rejected.toString(), userId);
		return executeQuery(updateSQL);
	}

	public void applyLeave(Leaves leave) throws SQLException, ClassNotFoundException {
		String query = String.format("SELECT * FROM %s where userId = '%s' and status = '%s'",
				LeavesConstants.LEAVES_TABLE_NAME, leave.getUserId(), LeavesStatus.Pending.toString());
		List<Leaves> leaves = executeGetAllQuery(query);
		if (!leaves.isEmpty()) {
			System.out.println("Leave request already applied. Can not request again");
		} else {
			String insertSQL = String.format(
					"INSERT INTO %s (%s, %s, %s, %s, %s, %s) " + "VALUES ('%s', '%s', '%s', '%s','%s','%s')",
					LeavesConstants.LEAVES_TABLE_NAME, LeavesConstants.ID_COLUMN, LeavesConstants.USER_ID_COLUMN,
					LeavesConstants.CONTENT_COLUMN, LeavesConstants.START_DATE_COLUMN, LeavesConstants.END_DATE_COLUMN,
					LeavesConstants.STATUS_COLUMN, leave.getLeaveId(), leave.getUserId(), leave.getContent(),
					leave.getStartDate(), leave.getEndDate(), leave.getStatus().toString());
			boolean flag = executeQuery(insertSQL);
			if (!flag) {
				System.out.println("Leave request was not applied.");
			} else {
				System.out.println("Leave request successfully applied.");
			}
		}
	}

	public List<Leaves> viewAllLeave() throws ClassNotFoundException, SQLException {
		String query = String.format("SELECT * FROM %s", LeavesConstants.LEAVES_TABLE_NAME);
		return executeGetAllQuery(query);
	}

	public List<Leaves> checkLeaveStatus(String userId) throws ClassNotFoundException, SQLException {
		List<Leaves> leave = null;
		String query = String.format("SELECT * FROM %s WHERE %s = '%s'", LeavesConstants.LEAVES_TABLE_NAME,
				LeavesConstants.USER_ID_COLUMN, userId);
		leave = executeGetAllQuery(query);
		if (leave == null) {
			System.out.println("Leave Not applied yet");
			return leave;
		}
		return leave;
	}
}