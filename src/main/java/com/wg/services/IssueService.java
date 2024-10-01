package com.wg.services;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import com.wg.dao.IssueDAO;
import com.wg.dao.interfaces.InterfaceIssuesDAO;
import com.wg.helper.LoggingUtil;
import com.wg.model.Issue;

public class IssueService {
	private InterfaceIssuesDAO issueDAO = new IssueDAO();
	Logger logger = LoggingUtil.getLogger(IssueService.class);

	public IssueService() {
	}

//	public IssueService(IssueDAO issueDAO) {
//		this.issueDAO = issueDAO; 
//	}

	public void raiseIssue(Issue issue) {
		boolean flag = false;
		try {
			flag = issueDAO.raiseIssue(issue);
			if (flag == true) {
				System.out.println("Issue raised Successfully");
			} else {
				System.out.println("Error raising issue");
			}
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	public void resolveIssue(String userId) {
		boolean flag = false;
		try {
			flag = issueDAO.resolveIssue(userId);
			if (flag == true) {
				System.out.println("Issue resolved Successfully");
			} else {
				System.out.println("Error resolving issue");
			}
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<Issue> checkIssueStatus(String userId) {
		List<Issue> issue = null;
		try {
			issue = issueDAO.checkIssueStatus(userId);
			return issue;
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return issue;
	}

	public List<Issue> viewAllIssues() {
		List<Issue> issue = null;
		try {
			issue = issueDAO.viewAllIssues();
			return issue;
		} catch (ClassNotFoundException | SQLException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return issue;
	}
}
