package com.alicegabbana.restserver.services.tag;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dto.TagDto;
import com.alicegabbana.restserver.services.AuthService;

@Stateless
public class TagResponse {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	TagService tagService;
	
	Logger logger = Logger.getLogger(TagResponse.class);
	
	public Response createResponse(TagDto tagDto) {
		
		if ( tagDto == null || tagDto.getId() != null || tagDto.getName().equals("") ) return authService.returnResponse(400);

		if ( tagService.nameExist(tagDto.getName()) == true ) return authService.returnResponse(409);
		
		TagDto tagDtoCreated = tagService.create(tagDto);
		return authService.returnResponseWithEntity(201, tagDtoCreated);

	}
	
	public Response updateResponse(TagDto tagToUpdate) {
		
		if ( tagToUpdate == null || tagToUpdate.getId() == null || tagToUpdate.getName().equals("") || tagToUpdate.getName() == null) return authService.returnResponse(400);

		if ( tagService.nameExist(tagToUpdate.getName()) == true ) return authService.returnResponse(409);
		
		TagDto updatedDtoTag = tagService.update(tagToUpdate);
		return authService.returnResponseWithEntity(200, updatedDtoTag);

	}
	
	public Response deleteTag (TagDto tagDto) {

		if ( tagDto == null || tagDto.getId() == null ) return authService.returnResponse(400);		
		
		if ( tagService.getDaoById(tagDto.getId()) == null ) return authService.returnResponse(404);
		
		tagService.delete(tagDto.getId());
		return authService.returnResponse(200);

	}
	
	public Response getTag ( TagDto tagDto ) {

		
		if ( tagDto == null || tagDto.getId() == null ) return authService.returnResponse(400);
		if ( tagService.getDaoById(tagDto.getId()) == null ) return authService.returnResponse(404);
		
		TagDto tagDtoFind = tagService.getDtoById(tagDto.getId());
		return authService.returnResponseWithEntity(200, tagDtoFind);

	}
	
	public Response getAllTag ( ) {

		List<TagDto> tagDtoList = tagService.getAllDto();		

		return authService.returnResponseWithEntity(200, tagDtoList);

	}
	
}
