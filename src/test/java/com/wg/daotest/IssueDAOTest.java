package com.wg.daotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wg.constants.IssueConstants;
import com.wg.dao.IssueDAO;
import com.wg.model.Issue;
import com.wg.model.IssuesStatus;

public class IssueDAOTest {

	@InjectMocks
	private IssueDAO issueDAO;

	@Mock
	private Connection connection;

	@Mock
	private PreparedStatement preparedStatement;

	@Mock
	private ResultSet resultSet;

	@BeforeEach
	public void setUp() throws SQLException {
		MockitoAnnotations.openMocks(this);
		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
	}

	@Test
	public void testRaiseIssue() throws SQLException, ClassNotFoundException {
		Issue issue = new Issue();
		issue.setIssueID("I101");
		issue.setMessage("Sample issue");
		issue.setUserId("U101");
		issue.setStatus(IssuesStatus.PENDING);

		when(preparedStatement.executeUpdate()).thenReturn(1);

		boolean result = issueDAO.raiseIssue(issue);

		assertTrue(result);
		verify(preparedStatement).executeUpdate();
	}

	@Test
	public void testGetIssueById() throws SQLException, ClassNotFoundException {
		Issue issue = new Issue();
		issue.setIssueID("I101");
		issue.setMessage("Sample issue");
		issue.setUserId("U101");
		issue.setStatus(IssuesStatus.PENDING);
		issue.setCreatedAt(java.time.LocalDate.now());

		when(resultSet.next()).thenReturn(true).thenReturn(false);
		when(resultSet.getString(IssueConstants.ISSUE_ID_COLUMN)).thenReturn("I101");
		when(resultSet.getString(IssueConstants.MESSAGE_COLUMN)).thenReturn("Sample issue");
		when(resultSet.getString(IssueConstants.USER_ID_COLUMN)).thenReturn("U101");
		when(resultSet.getString("status")).thenReturn("PENDING");
		when(resultSet.getDate("createdAt")).thenReturn(java.sql.Date.valueOf(java.time.LocalDate.now()));

		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		Issue result = issueDAO.getIssueById("I101");

		assertNotNull(result);
		assertEquals("I101", result.getIssueID());
		assertEquals("Sample issue", result.getMessage());
		assertEquals("U101", result.getUserId());
		assertEquals(IssuesStatus.PENDING, result.getStatus());
		assertEquals(java.time.LocalDate.now(), result.getCreatedAt());
	}

	@Test
	public void testViewAllIssues() throws SQLException, ClassNotFoundException {
		Issue issue1 = new Issue();
		issue1.setIssueID("I101");
		issue1.setMessage("Issue 1");
		issue1.setUserId("U101");
		issue1.setStatus(IssuesStatus.PENDING);
		issue1.setCreatedAt(java.time.LocalDate.now());

		Issue issue2 = new Issue();
		issue2.setIssueID("I102");
		issue2.setMessage("Issue 2");
		issue2.setUserId("U102");
		issue2.setStatus(IssuesStatus.RESOLVED);
		issue2.setCreatedAt(java.time.LocalDate.now());

		List<Issue> issueList = new ArrayList<>();
		issueList.add(issue1);
		issueList.add(issue2);

		when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

		when(resultSet.getString(IssueConstants.ISSUE_ID_COLUMN)).thenReturn("I101").thenReturn("I102");
		when(resultSet.getString(IssueConstants.MESSAGE_COLUMN)).thenReturn("Issue 1").thenReturn("Issue 2");
		when(resultSet.getString(IssueConstants.USER_ID_COLUMN)).thenReturn("U101").thenReturn("U102");
		when(resultSet.getString("status")).thenReturn("PENDING").thenReturn("RESOLVED");
		when(resultSet.getDate("createdAt")).thenReturn(java.sql.Date.valueOf(java.time.LocalDate.now()));

		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		List<Issue> result = issueDAO.viewAllIssues();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("I101", result.get(0).getIssueID());
		assertEquals("I102", result.get(1).getIssueID());
	}

	@Test
	public void testCheckIssueStatus() throws SQLException, ClassNotFoundException {
		Issue issue1 = new Issue();
		issue1.setIssueID("I101");
		issue1.setMessage("Issue 1");
		issue1.setUserId("U101");
		issue1.setStatus(IssuesStatus.PENDING);
		issue1.setCreatedAt(java.time.LocalDate.now());

		Issue issue2 = new Issue();
		issue2.setIssueID("I102");
		issue2.setMessage("Issue 2");
		issue2.setUserId("U101");
		issue2.setStatus(IssuesStatus.RESOLVED);
		issue2.setCreatedAt(java.time.LocalDate.now());

		List<Issue> issueList = new ArrayList<>();
		issueList.add(issue1);
		issueList.add(issue2);

		when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

		when(resultSet.getString(IssueConstants.ISSUE_ID_COLUMN)).thenReturn("I101").thenReturn("I102");
		when(resultSet.getString(IssueConstants.MESSAGE_COLUMN)).thenReturn("Issue 1").thenReturn("Issue 2");
		when(resultSet.getString(IssueConstants.USER_ID_COLUMN)).thenReturn("U101").thenReturn("U101");
		when(resultSet.getString("status")).thenReturn("PENDING").thenReturn("RESOLVED");
		when(resultSet.getDate("createdAt")).thenReturn(java.sql.Date.valueOf(java.time.LocalDate.now()));

		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		List<Issue> result = issueDAO.checkIssueStatus("U101");

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("I101", result.get(0).getIssueID());
		assertEquals("I102", result.get(1).getIssueID());
	}

	@Test
	public void testResolveIssue() throws SQLException, ClassNotFoundException {
		when(preparedStatement.executeUpdate()).thenReturn(1);

		boolean result = issueDAO.resolveIssue("U101");

		assertTrue(result);
		verify(preparedStatement).executeUpdate();
	}
}
