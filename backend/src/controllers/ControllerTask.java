package controllers;

import java.util.List;

import dao.TaskDao;
import dao.VoteAddDao;
import models.Colocation;
import models.Task;
import models.User;
import models.VoteAdd;

public class ControllerTask {
	
	static TaskDao daoTask = new TaskDao();
	static VoteAddDao daoVoteAdd = new VoteAddDao();


	
	public static List<Task> getTasks(Colocation coloc) {
		return daoTask.getEntityManager().createNamedQuery("task.getTasksByColoc").setParameter("coloc", coloc.getId()).getResultList();
	}
	
	public static void createTask(Colocation c, Task t, User u) {
		t.setColoc(c);
		daoTask.create(t);
		
		//On crée un vote pour la creation de la tache.
		daoVoteAdd.create(new VoteAdd(t, u, true));
	}
	
}
