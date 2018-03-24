package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.entity.School;

@Stateless
public class SchoolDao {
	
	Logger logger = Logger.getLogger(SchoolDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;

	public School getSchoolById ( Long id ) {		
		
		School school = em.find(School.class, id);
		if (school != null) return school;
		logger.info("Dao getById : school with this id doesn't exist");	
		return null;
		
	}
	
	public School getSchoolByName ( String name ) {		
		
		TypedQuery<School> query_name = em.createQuery("SELECT school FROM School school WHERE school.name = :name", School.class)
				.setParameter("name", name);
		List<School> loadedSchool = query_name.getResultList();
		
		if ( loadedSchool.size() != 0 ) {
			return loadedSchool.get(0);
		}
		logger.info("Dao get : school with this name doesn't exist");				
		return null;
		
	}
	
	public List<School> getAllSchools () {
		TypedQuery<School> query_schools = em.createQuery("SELECT school FROM School school", School.class);
		List<School> loadedSchools = query_schools.getResultList();
		
		return loadedSchools;
	}
}
