package com.alicegabbana.restserver.services.tag;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.TagDao;
import com.alicegabbana.restserver.dto.TagDto;
import com.alicegabbana.restserver.entity.Tag;
import com.alicegabbana.restserver.services.AuthService;

@Stateless
public class TagService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	TagDao tagDao;
	
	Logger logger = Logger.getLogger(TagService.class);
	
	public boolean nameExist(String name) {
		
		if (getDaoByName(name) == null) {
			return false;
		}		
		return true;
	}
	
	public TagDto create(TagDto tagDto) {
		Tag tagCreated = em.merge(tagDto);
		TagDto tagDtoCreated = daoToDto(tagCreated);
		return tagDtoCreated;
	}
	
	public TagDto update(TagDto tagDto) {
		Tag tag = getDaoById(tagDto.getId());
		if (tagDto.getName() != null) {
			tag.setName(tagDto.getName());
		}		
		Tag updatedTag = em.merge(tag);
		return daoToDto(updatedTag);
	}
	
	public TagDto daoToDto(Tag tag) {
		TagDto tagDto = new TagDto();
		if (tag.getName() != null) {
			tagDto.setName(tag.getName());
		}
		if (tag.getId() != null) {
			tagDto.setId(tag.getId());
		}
		return tagDto;
	}
	
	public void delete(Long id) {
		Tag tag = em.find(Tag.class, id);
		em.remove(tag);
	}
	
	public Tag getDaoById (Long id) {
		return tagDao.getTagById(id);
	}
	
	public Tag getDaoByName (String name) {
		return tagDao.getTagByName(name);
	}
	
	public TagDto getDtoById (Long id) {
		Tag tag = tagDao.getTagById(id);
		return daoToDto(tag);
	}
	
	public TagDto getDtoByName (String name) {
		Tag tag = tagDao.getTagByName(name);
		return daoToDto(tag);
	}
	
	public List<Tag> getAllDao() {
		List<Tag> loadedTags = tagDao.getAllTags();
		return loadedTags;
	}
	
	public List<TagDto> getAllDto() {
		List<Tag> tagDaoList = tagDao.getAllTags();
		List<TagDto> tagDtoList = new ArrayList<TagDto>();
		if (tagDaoList!=null) {
			for (Tag tag : tagDaoList) {
				TagDto tagDto = daoToDto(tag);
				tagDtoList.add(tagDto);
			}
		}
		return tagDtoList;
	}
}
