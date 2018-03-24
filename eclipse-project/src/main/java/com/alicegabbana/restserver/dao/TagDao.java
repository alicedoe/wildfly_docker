package com.alicegabbana.restserver.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.entity.Tag;

@Stateless
public class TagDao {
	
	Logger logger = Logger.getLogger(TagDao.class);
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	public Tag getTagById (Long id) {
		
		TypedQuery<Tag> query_name = em.createQuery("SELECT tag FROM Tag tag WHERE tag.id = :id", Tag.class)
				.setParameter("id", id);
		List<Tag> loadedTags = query_name.getResultList();

		if ( loadedTags.size() != 0 ) {
			return loadedTags.get(0);
		}
		logger.info("Dao get : tag with this id doesn't exist");		
		return null;
	}
	
	public Tag getTagByName (String name) {
		
		TypedQuery<Tag> query_name = em.createQuery("SELECT tag FROM Tag tag WHERE tag.name = :name", Tag.class)
				.setParameter("name", name);
		List<Tag> loadedTags = query_name.getResultList();
		
		if ( loadedTags.size() != 0 ) {
			return loadedTags.get(0);
		}
		logger.info("Dao get : tag with this name doesn't exist");			
		return null;
	}
	
	public List<Tag> getAllTags () {
		
		TypedQuery<Tag> query_tags = em.createQuery("SELECT tag FROM Tag tag", Tag.class);
		List<Tag> loadedTags = query_tags.getResultList();
		
		if ( loadedTags.size() != 0 ) {
			return loadedTags;
		}
		logger.info("Dao get : there is no tag");			
		return null;
	}

}
