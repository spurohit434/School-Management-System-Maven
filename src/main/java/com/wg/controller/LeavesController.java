package com.wg.controller;

import java.util.List;

import com.wg.model.Leaves;
import com.wg.services.LeavesService;

public class LeavesController {
	private final LeavesService leavesService = new LeavesService();

	public LeavesController() {
	}

	public void approveLeave(String userId) {
		leavesService.approveLeave(userId);
	}

	public void rejectLeave(String userId) {
		leavesService.rejectLeave(userId);
	}

	public void applyLeave(Leaves leave) {
		leavesService.applyLeave(leave);
	}

	public List<Leaves> viewAllLeave() {
		List<Leaves> leaves = leavesService.viewAllLeave();
		return leaves;
	}

	public List<Leaves> checkLeaveStatus(String userId) {
		return leavesService.checkLeaveStatus(userId);
	}
}