package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.entity.Action;
import com.alicegabbana.restserver.entity.Homework;
import com.alicegabbana.restserver.entity.Role;

@Stateless
public class HomeworkDao {
	
	Logger logger = Logger.getLogger(HomeworkDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;

	public Homework getById ( Long id ) {		
		
		Homework homework = em.find(Homework.class, id);
		if (homework != null) return homework;
		logger.info("Dao getById : homework with this id doesn't exist");	
		return null;
		
	}
	
	public List<Homework> getByCreator ( Long id ) {		
		
		TypedQuery<Homework> query_homework = em.createQuery("SELECT homework FROM Homework homework WHERE homework.creator.id = :id", Homework.class)
				.setParameter("id", id);
		List<Homework> loadedHomework = query_homework.getResultList();
		
		if ( loadedHomework.size() != 0 ) {
			return loadedHomework;
		}
		logger.info("Dao get : no homework created by this user");				
		return null;
		
	}
	
	public List<Homework> getForKidsClass ( Long id ) {		
		
		TypedQuery<Homework> query_homework = em.createQuery("SELECT homework FROM Homework homework WHERE homework.kidsClass.id = :id", Homework.class)
				.setParameter("id", id);
		List<Homework> loadedHomework = query_homework.getResultList();
		
		if ( loadedHomework.size() != 0 ) {
			return loadedHomework;
		}
		logger.info("Dao get : no homework for this kidsClass");				
		return null;
		
	}
	
	public List<Homework> getAll () {
		TypedQuery<Homework> query_homeworks = em.createQuery("SELECT homework FROM Homework homework", Homework.class);
		List<Homework> loadedHomeworks = query_homeworks.getResultList();
		
		return loadedHomeworks;
	}
}
