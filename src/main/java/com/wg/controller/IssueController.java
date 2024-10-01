package com.wg.controller;

import java.util.List;

import com.wg.model.Issue;
import com.wg.services.IssueService;

public class IssueController {
	private final IssueService issueService = new IssueService();

	public IssueController() {
	}

	public void raiseIssue(Issue issue) {
		issueService.raiseIssue(issue);
	}

	public void resolveIssue(String userId) {
		issueService.resolveIssue(userId);
	}

	public List<Issue> checkIssueStatus(String userId) {
		List<Issue> issues = issueService.checkIssueStatus(userId);
		return issues;
	}

	public List<Issue> viewAllIssues() {
		List<Issue> issues = issueService.viewAllIssues();
		return issues;
	}
}