package com.podio.task;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

import com.podio.BaseAPIFactory;
import com.podio.common.Reference;
import com.podio.common.ReferenceType;

public class TaskAPITest {

	private TaskAPI getAPI() {
		return new TaskAPI(BaseAPIFactory.get());
	}

	@Test
	public void getTask() {
		Task task = getAPI().getTask(3);

		Assert.assertEquals(task.getId(), 3);
		Assert.assertEquals(task.getStatus(), TaskStatus.ACTIVE);
		Assert.assertEquals(task.getText(), "Document API");
		Assert.assertEquals(task.isPrivate(), true);
		Assert.assertEquals(task.getDueDate(), new LocalDate(2010, 8, 21));
		Assert.assertEquals(task.getResponsible().getId(), 2);
		Assert.assertEquals(task.getSpaceId(), null);
		Assert.assertEquals(task.getLink(), "https://podio.com/tasks_goto/3");
		Assert.assertEquals(task.getCreatedOn(), new DateTime(2010, 8, 20, 11,
				30, 0, 0));
		Assert.assertEquals(task.getCreatedBy().getId(), 1);
		Assert.assertEquals(task.getReferenceType(), null);
		Assert.assertEquals(task.getReferenceId(), null);
		Assert.assertEquals(task.getReferenceTitle(), null);
		Assert.assertEquals(task.getReferenceLink(), null);
	}

	@Test
	public void assignTask() {
		getAPI().assignTask(1, 1);
	}

	@Test
	public void completeTask() {
		getAPI().completeTask(1);
	}

	@Test
	public void incompleteTask() {
		getAPI().incompleteTask(4);
	}

	@Test
	public void startTask() {
		getAPI().completeTask(1);
	}

	@Test
	public void stopTask() {
		getAPI().incompleteTask(4);
	}

	@Test
	public void createTask() {
		int taskId = getAPI().createTask(
				new TaskCreate("Test task", false, new LocalDate(2010, 11, 10),
						1));

		Assert.assertTrue(taskId > 0);
	}

	@Test
	public void createTaskWithReference() {
		int taskId = getAPI().createTaskWithReference(
				new TaskCreate("Test task", false, new LocalDate(2010, 11, 10),
						1), new Reference(ReferenceType.ITEM, 1));

		Assert.assertTrue(taskId > 0);
	}

	@Test
	public void updateDueDate() {
		getAPI().updateDueDate(1, new LocalDate(2010, 11, 9));
	}

	@Test
	public void updatePrivate() {
		getAPI().updatePrivate(1, true);
	}

	@Test
	public void updateText() {
		getAPI().updateText(1, "Test text");
	}

	@Test
	public void getTasksWithReference() {
		List<Task> tasks = getAPI().getTasksWithReference(
				new Reference(ReferenceType.ITEM, 1));
		Assert.assertEquals(tasks.size(), 1);
		Assert.assertEquals(tasks.get(0).getId(), 4);
	}

	@Test
	public void getActiveTasks() {
		TasksByDue tasks = getAPI().getActiveTasks();
		Assert.assertEquals(tasks.getByDueStatus(TaskDueStatus.OVERDUE).size(),
				1);
		Assert.assertEquals(
				tasks.getByDueStatus(TaskDueStatus.UPCOMING).size(), 2);
	}

	@Test
	public void getAssignedTasks() {
		TasksByDue tasks = getAPI().getAssignedActiveTasks();
		Assert.assertEquals(tasks.getByDueStatus(TaskDueStatus.OVERDUE).size(),
				1);
	}

	@Test
	public void getAssignedCompletedTasks() {
		List<Task> tasks = getAPI().getAssignedCompletedTasks();
		Assert.assertEquals(tasks.size(), 0);
	}

	@Test
	public void getCompletedTasks() {
		List<Task> tasks = getAPI().getCompletedTasks();
		Assert.assertEquals(tasks.size(), 1);
		Assert.assertEquals(tasks.get(0).getId(), 4);
	}

	@Test
	public void getStartedTasks() {
		TasksByDue tasks = getAPI().getStartedTasks();
		Assert.assertEquals(tasks.getByDueStatus(TaskDueStatus.OVERDUE).size(),
				1);
	}
}