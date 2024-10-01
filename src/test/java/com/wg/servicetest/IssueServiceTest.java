package com.wg.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wg.dao.IssueDAO;
import com.wg.model.Issue;
import com.wg.services.IssueService;

public class IssueServiceTest {

	@Mock
	private IssueDAO issueDAOMock;

	@InjectMocks
	private IssueService issueService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testRaiseIssue_Success() throws SQLException, ClassNotFoundException {
		// Arrange
		Issue issue = new Issue();
		when(issueDAOMock.raiseIssue(issue)).thenReturn(true);

		// Act
		issueService.raiseIssue(issue);

		// Assert
		verify(issueDAOMock).raiseIssue(issue);
	}

	@Test
	public void testRaiseIssue_Failure() throws SQLException, ClassNotFoundException {
		// Arrange
		Issue issue = new Issue();
		when(issueDAOMock.raiseIssue(issue)).thenReturn(false);

		// Act
		issueService.raiseIssue(issue);

		// Assert
		verify(issueDAOMock).raiseIssue(issue);
	}

	@Test
	public void testResolveIssue_Success() throws SQLException, ClassNotFoundException {
		// Arrange
		String userId = "user123";
		when(issueDAOMock.resolveIssue(userId)).thenReturn(true);

		// Act
		issueService.resolveIssue(userId);

		// Assert
		verify(issueDAOMock).resolveIssue(userId);
	}

	@Test
	public void testResolveIssue_Failure() throws SQLException, ClassNotFoundException {
		// Arrange
		String userId = "user123";
		when(issueDAOMock.resolveIssue(userId)).thenReturn(false);

		// Act
		issueService.resolveIssue(userId);

		// Assert
		verify(issueDAOMock).resolveIssue(userId);
	}

	@Test
	public void testCheckIssueStatus_Success() throws SQLException, ClassNotFoundException {
		// Arrange
		String userId = "user123";
		List<Issue> expectedIssues = new ArrayList<>();
		when(issueDAOMock.checkIssueStatus(userId)).thenReturn(expectedIssues);

		// Act
		List<Issue> actualIssues = issueService.checkIssueStatus(userId);

		// Assert
		assertEquals(expectedIssues, actualIssues);
		verify(issueDAOMock).checkIssueStatus(userId);
	}

	@Test
	public void testViewAllIssues_Success() throws SQLException, ClassNotFoundException {
		// Arrange
		List<Issue> expectedIssues = new ArrayList<>();
		when(issueDAOMock.viewAllIssues()).thenReturn(expectedIssues);

		// Act
		List<Issue> actualIssues = issueService.viewAllIssues();

		// Assert
		assertEquals(expectedIssues, actualIssues);
		verify(issueDAOMock).viewAllIssues();
	}
}
