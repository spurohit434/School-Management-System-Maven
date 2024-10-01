package com.wg.services;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import com.wg.dao.LeavesDAO;
import com.wg.dao.interfaces.InterfaceLeavesDAO;
import com.wg.helper.LoggingUtil;
import com.wg.model.Leaves;

public class LeavesService {
	private InterfaceLeavesDAO leavesDAO = new LeavesDAO();
	Logger logger = LoggingUtil.getLogger(LeavesService.class);

	public LeavesService() {
	}

//	public LeavesService(LeavesDAO leavesDAO) {
//		this.leavesDAO = leavesDAO;
//	}

	public boolean approveLeave(String userId) {
		boolean flag = false;
		try {
			flag = leavesDAO.approveLeave(userId);
			if (flag == true) {
				System.out.println("Leave Approved Successfully");
				return flag;
			} else {
				System.out.println("Leave Not approved");
				return flag;
			}
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	public boolean rejectLeave(String userId) {
		boolean flag = false;
		try {
			flag = leavesDAO.rejectLeave(userId);
			if (flag == true) {
				System.out.println("Leave Rejected Successfully");
				return flag;
			} else {
				System.out.println("Leave Not rejected");
				return flag;
			}
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	public void applyLeave(Leaves leave) {
		try {
			leavesDAO.applyLeave(leave);
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<Leaves> viewAllLeave() {
		List<Leaves> leaves = null;
		try {
			leaves = leavesDAO.viewAllLeave();
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return leaves;
	}

	public List<Leaves> checkLeaveStatus(String userId) {
		List<Leaves> status = null;
		try {
			status = leavesDAO.checkLeaveStatus(userId);
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return status;
	}

}
