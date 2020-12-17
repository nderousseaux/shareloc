package dao;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;


public abstract class DAOFacade<T> {
	private Class<T> classeEntite;
	
	@PersistenceUnit(unitName="api")
	private EntityManagerFactory emfactory;
	
	@PersistenceContext(unitName="api")
	private EntityManager em;


	public DAOFacade(Class<T> classeEntite) {
		this.classeEntite = classeEntite;
        this.emfactory = Persistence.createEntityManagerFactory("api");
        this.em = this.emfactory.createEntityManager();
	}


	public EntityManager getEntityManager() {
		return em;
	}


	public T find(Object id) {
		return getEntityManager().find(classeEntite, id);
	}

	public List<T> findAll() {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(classeEntite));
        return (List<T>) this.getEntityManager().createQuery(cq).getResultList();
        
	}

	public void create(T entite) {
		
		try {
			UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
			transaction.begin();
			getEntityManager().joinTransaction();
			//getEntityManager().getTransaction().begin();
	    	getEntityManager().persist(entite);
	    	getEntityManager().flush();
	    	transaction.commit();
		} catch (Exception e) {
	        throw new IllegalArgumentException(e.getMessage());
		}
		
		
	}

}
