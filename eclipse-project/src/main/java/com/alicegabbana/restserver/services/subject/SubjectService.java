package com.alicegabbana.restserver.services.subject;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.SubjectDao;
import com.alicegabbana.restserver.dto.SchoolDto;
import com.alicegabbana.restserver.dto.SubjectDto;
import com.alicegabbana.restserver.entity.School;
import com.alicegabbana.restserver.entity.Subject;
import com.alicegabbana.restserver.services.AuthService;

@Stateless
public class SubjectService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	SubjectDao subjectDao;
	
	Logger logger = Logger.getLogger(SubjectService.class);
	
	public boolean subjectNameExist(String name) {
		
		if (subjectDao.getSubjectByName(name) == null) {
			return false;
		}
		
		return true;
	}
	
	public void delete(Long id) {
		Subject subject = em.find(Subject.class, id);
		em.remove(subject);
	}
	
	public Subject getDaoById (Long id) {
		return subjectDao.getSubjectById(id);
	}
	
	public SubjectDto getDtoById (Long id) {
		Subject subject = getDaoById(id);
		SubjectDto subjectDto = daoToDto(subject);
		return subjectDto;
	}
	
	public SubjectDto daoToDto (Subject subject) {
		
		SubjectDto subjectDto = new SubjectDto();
		if (subject.getId() != null) {
			subjectDto.setId(subject.getId());
		}
		if (subject.getName() != null) {
			subjectDto.setName(subject.getName());
		}
		return subjectDto;		
	}
	
	public Subject getSubjectByName (String name) {
		return subjectDao.getSubjectByName(name);
	}
	
	public List<Subject> getAll() {
		List<Subject> loadedSubjects = subjectDao.getAllSubjects();
		return loadedSubjects;
	}
	
	public List<SubjectDto> getAllDto() {
		List<Subject> subjectList = getAll();
		List<SubjectDto> subjectDtoList = daoListToDtoList(subjectList);
		return subjectDtoList;
	}
	
	public List<SubjectDto> daoListToDtoList(List<Subject> subjectList) {
		List<SubjectDto> subjectDtoList = new ArrayList<SubjectDto>();		
		if (subjectList!=null) {
			for (Subject subject : subjectList) {
				SubjectDto subjectDto = daoToDto(subject);
				subjectDtoList.add(subjectDto);
			}
		}
		return subjectDtoList;
	}
	
	public SubjectDto update(SubjectDto subjectDtoToUpdate) {
		
		Subject subject = getDaoById(subjectDtoToUpdate.getId());
		subject.setName(subjectDtoToUpdate.getName());		
		Subject updatedSubject = em.merge(subject);
		SubjectDto subjectDto = daoToDto(updatedSubject);
		
		return subjectDto;
	}
	
}
