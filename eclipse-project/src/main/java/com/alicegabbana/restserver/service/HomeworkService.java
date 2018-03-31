package com.alicegabbana.restserver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.HomeworkDao;
import com.alicegabbana.restserver.dto.HomeworkDto;
import com.alicegabbana.restserver.dto.KidsClassDto;
import com.alicegabbana.restserver.entity.Homework;
import com.alicegabbana.restserver.entity.KidsClass;
import com.alicegabbana.restserver.entity.Subject;
import com.alicegabbana.restserver.entity.User;

@Stateless
public class HomeworkService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	KidsClassService kidsClassService;
	
	@EJB
	SubjectService subjectService;
	
	@EJB
	UserService userService;
	
	@EJB
	HomeworkDao homeworkDao;
	
	Logger logger = Logger.getLogger(HomeworkService.class);
	
	public Response createResponse(HomeworkDto homeworkDto) {		
		
		if (homeworkDto.getId() != null || !homeworkDtoIsComplete(homeworkDto, false) ) return authService.returnResponse(400);
		
		HomeworkDto homeworkCreated = createService(homeworkDto);
		return authService.returnResponseWithEntity(201, homeworkCreated);
		
	}
	
	public Response getAllResponse ( ) {		
		return authService.returnResponseWithEntity(200, getAllService());
	}
	
	public List<HomeworkDto> getAllService () {
		List<Homework> kidsClassList = homeworkDao.getAllHomeworks();
		List<HomeworkDto> kidsClassDtoList = daoListToDtoList(kidsClassList);
		return kidsClassDtoList;
	}
	
	public HomeworkDto createService (HomeworkDto homeworkDto) {
		Homework homework = dtoToDao(homeworkDto);
		logger.error(homework.toString());
		homework.setWording("pitièjeveuxcetexte");
		Homework homeworkCreated = em.merge(homework);
		HomeworkDto homeworkDtoCreated = daoToDto(homeworkCreated);
		return homeworkDtoCreated;
	}
	
	public List<HomeworkDto>  daoListToDtoList ( List<Homework> homeworkList) {
		List<HomeworkDto> homeworkDtoList = new ArrayList<HomeworkDto>();
		if (homeworkList != null) {
			for (Homework homework : homeworkList) {
				HomeworkDto homeworkDto = daoToDto(homework);
				homeworkDtoList.add(homeworkDto);
			}
		}
		return homeworkDtoList;
	}
	
	public Homework dtoToDao (HomeworkDto homeworkDto) {
		
		Homework homework = new Homework();
		if (homeworkDto != null) {
			if (homeworkDto.getId() != null) homework.setId(homeworkDto.getId());
			if (homeworkDto.getSubjectName() != null) {
				Subject subject = subjectService.getSubjectByName(homeworkDto.getSubjectName());
				homework.setSubject(subject);
			}
			if (homeworkDto.getCreatorId() != null) {
				User user = userService.getUserById(homeworkDto.getCreatorId());
				homework.setCreator(user);
			}
			if (homeworkDto.getKidsClassId() != null) {
				KidsClass kidsClass = kidsClassService.getById(homeworkDto.getKidsClassId());
				homework.setKidsClass(kidsClass);
			}
			if (homeworkDto.getWording() != null) {
				homework.setWording(homeworkDto.getWording());
			}
			Date now = new Date();
			homework.setCreationDate(now);
			if (homeworkDto.getEndDate() != null) {
				homework.setEndDate(homeworkDto.getEndDate());
			}
		}
		
		return homework;
	}
	
	public HomeworkDto daoToDto (Homework homework) {
		
		HomeworkDto homeworkDto = new HomeworkDto();
		if (homework != null) {
			if (homework.getId() != null) homeworkDto.setId(homeworkDto.getId());
			if (homework.getSubject() != null) {
				homeworkDto.setSubjectName(homework.getSubject().getName());
			}
			if (homework.getCreator() != null) {
				homeworkDto.setCreatorId(homework.getCreator().getId());
			}
			if (homework.getKidsClass() != null) {
				homeworkDto.setKidsClassId(homework.getKidsClass().getId());
			}
			if (homework.getWording() != null) {
				homework.setWording(homeworkDto.getWording());
			}
			if (homework.getCreationDate() != null) {
				homeworkDto.setCreationDate(homework.getCreationDate());
			}
			if (homework.getEndDate() != null) {
				homeworkDto.setEndDate(homework.getEndDate());
			}
		}
		
		return homeworkDto;
	}

	public boolean homeworkDtoIsComplete(HomeworkDto homeworkDto, boolean update) {
		
		if ( homeworkDto.getSubjectName() == null || homeworkDto.getCreatorId() == null
				|| homeworkDto.getKidsClassId() == null || homeworkDto.getWording() == null
				|| homeworkDto.getEndDate() == null )
		{	logger.info("missing_attributes");
			return false; }
		
		else if ( kidsClassService.getById(homeworkDto.getKidsClassId()) == null 
				|| userService.getUserById(homeworkDto.getCreatorId()) == null )
		{	logger.info("wrong attributes");
			return false; }
		
		//TODO check date
		
		else if ( update == false && homeworkDto.getId() != null )
		{	logger.info("forced_id");
			return false; }
		
		return true;
	}
}
