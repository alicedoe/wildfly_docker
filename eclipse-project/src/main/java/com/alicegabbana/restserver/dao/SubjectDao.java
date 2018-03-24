package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.entity.Subject;

@Stateless
public class SubjectDao {
	
	Logger logger = Logger.getLogger(SubjectDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	public Subject getSubjectById (Long id) {
		
		TypedQuery<Subject> query_name = em.createQuery("SELECT subject FROM Subject subject WHERE subject.id = :id", Subject.class)
				.setParameter("id", id);
		List<Subject> loadedSubjects = query_name.getResultList();

		if ( loadedSubjects.size() != 0 ) {
			return loadedSubjects.get(0);
		}
		logger.info("Dao get : subject with this id doesn't exist");		
		return null;
	}
	
	public Subject getSubjectByName (String name) {
		
		TypedQuery<Subject> query_name = em.createQuery("SELECT subject FROM Subject subject WHERE subject.name = :name", Subject.class)
				.setParameter("name", name);
		List<Subject> loadedSubjects = query_name.getResultList();
		
		if ( loadedSubjects.size() != 0 ) {
			return loadedSubjects.get(0);
		}
		logger.info("Dao get : subject with this name doesn't exist");			
		return null;
	}
	
	public List<Subject> getAllSubjects () {
		
		TypedQuery<Subject> query_subjects = em.createQuery("SELECT subject FROM Subject subject", Subject.class);
		List<Subject> loadedSubjects = query_subjects.getResultList();
		
		if ( loadedSubjects.size() != 0 ) {
			return loadedSubjects;
		}
		logger.info("Dao get : there is no subject");			
		return null;
	}

}
