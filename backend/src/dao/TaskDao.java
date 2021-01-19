package dao;

import models.Task;

public class TaskDao extends DAOFacade<Task> {

	public TaskDao() {
		super(Task.class);
	}

}