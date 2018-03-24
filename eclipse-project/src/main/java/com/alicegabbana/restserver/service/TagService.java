package com.alicegabbana.restserver.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.TagDao;
import com.alicegabbana.restserver.entity.Tag;

@Stateless
public class TagService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	TagDao tagDao;
	
	Logger logger = Logger.getLogger(TagService.class);
	
	public Response createTag(Tag newTag) {
		
		if ( newTag == null || newTag.getId() != null || newTag.getName() == "" ) return authService.returnResponse(400);

		if ( tagNameExist(newTag.getName()) == true ) return authService.returnResponse(409);
		
		Tag tagCreated = em.merge(newTag);
		return authService.returnResponseWithEntity(201, tagCreated);

	}
	
	public Response updateTag(Tag tagToUpdate) {
		
		if ( tagToUpdate == null || tagToUpdate.getId() == null || tagToUpdate.getName() == "" ) return authService.returnResponse(400);

		if ( tagNameExist(tagToUpdate.getName()) == true ) return authService.returnResponse(409);
		
		Tag tag = getTagById(tagToUpdate.getId());
		if (tag.getName() == tagToUpdate.getName()) return authService.returnResponse(200);
		
		Tag updatedTag = em.merge(tagToUpdate);
		return authService.returnResponseWithEntity(200, updatedTag);

	}
	
	public Response deleteTag (Tag tag) {

		if ( tag == null || tag.getId() == null ) return authService.returnResponse(400);
		
		if ( tagNameExist(tag.getName()) == false ) return authService.returnResponse(404);

		tag = em.find(Tag.class, tag.getId());
		em.remove(tag);
		return authService.returnResponse(200);

	}
	
	public Response getTag ( Long id ) {

		Tag tag = em.find(Tag.class, id);
		return authService.returnResponseWithEntity(200, tag);

	}
	
	public Response getAllTag ( ) {

		List<Tag> loadedTags = tagDao.getAllTags();
		
		if ( loadedTags == null ) return authService.returnResponse(404);		

		return authService.returnResponseWithEntity(200, loadedTags);

	}
	
	public boolean tagNameExist(String name) {
		
		if (tagDao.getTagByName(name) == null) {
			return false;
		}
		
		return true;
	}
	
	public Tag getTagById (Long id) {
		return tagDao.getTagById(id);
	}
	
	public Tag getTagByName (String name) {
		return tagDao.getTagByName(name);
	}
	
}
