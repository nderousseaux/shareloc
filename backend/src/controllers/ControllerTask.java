package controllers;

import java.util.ArrayList;
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

	public static Task getTask(int idTask) {
		return daoTask.find(idTask);
	}
	
	public static List<Task> getTasks(Colocation coloc) {
		return daoTask.getEntityManager().createNamedQuery("task.getTasksByColoc").setParameter("coloc", coloc.getId()).getResultList();
	}
	
	public static void createTask(Colocation c, Task t, User u) {
		t.setColoc(c);
		daoTask.create(t);
		
		//On crée un vote pour la creation de la tache.
		daoVoteAdd.create(new VoteAdd(t, u, true));
	}
	
	public static Colocation getColocByTask(Task t) {
		return t.getColoc();
	}
	
	public static int nbVoteAddTaskPour(Task t) {
		List<VoteAdd> lva = daoVoteAdd.findAll();
		
		int nb = 0;
		for(VoteAdd va : lva) {
			if(va.getTask().getId() == t.getId() && va.getVote()) {
				nb++;
			}
		}
		
		return nb;
	}
	
	public static int nbVoteAddTaskContre(Task t) {
		List<VoteAdd> lva = daoVoteAdd.findAll();
		
		int nb = 0;
		for(VoteAdd va : lva) {
			if(va.getTask().getId() == t.getId() && !va.getVote()) {
				nb++;
			}
		}
		
		return nb;
	}
	
	public static String getStatus(Task t) {
		//Nombre de personne dans la coloc
		Colocation c = getColocByTask(t);
		int nbPersonne = ControllerColocation.getUserFromColoc(c.getId()).size();
		
	    //Si moins de la moitié des personnes ont voté pour et moins de la moitée des personnes ont voté contre, le status est VOTING CREATING
		if(nbVoteAddTaskPour(t) <= nbPersonne/2 &&  nbVoteAddTaskContre(t) < nbPersonne/2) {
			return "VOTINGCREATING";
		}
		//Si au moins la moitié des personnes ont voté contre, le status est REFUSE
		else if(nbVoteAddTaskContre(t)>=nbPersonne/2){
			return "REFUSE";
		}
		else {
			return "ACTIVE";
		}
		 
	}

	
	public static List<Task> findByStatus(String s){
		List<Task> l = daoTask.findAll();
		List<Task> retour = new ArrayList<Task>();
		
		for(Task t: l) {
			if(getStatus(t).equals(s)) {
				retour.add(t);
			}
		}
		
		return retour;
		
	}
	
	public static void voteAdd(User u, Task t, boolean b) {
		daoVoteAdd.create(new VoteAdd(t, u, b));
	}
}
