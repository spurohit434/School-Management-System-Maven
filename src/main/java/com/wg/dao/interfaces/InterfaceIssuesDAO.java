package com.wg.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.wg.model.Issue;

public interface InterfaceIssuesDAO {

	public Issue getIssueById(String issueId) throws ClassNotFoundException, SQLException;

	public List<Issue> viewAllIssues() throws ClassNotFoundException, SQLException;

	public List<Issue> checkIssueStatus(String userId) throws ClassNotFoundException, SQLException;

	public boolean resolveIssue(String userId) throws ClassNotFoundException, SQLException;

	public boolean raiseIssue(Issue issue) throws ClassNotFoundException, SQLException;

}