package controllers;

import java.util.List;

import dao.TaskDao;
import models.Colocation;
import models.Task;

public class ControllerTask {
	
	static TaskDao daoTask = new TaskDao();

	
	public static List<Task> getTasks(Colocation coloc) {
		return daoTask.getEntityManager().createNamedQuery("task.getTasksByColoc").setParameter("coloc", coloc.getId()).getResultList();
	}
	
}
