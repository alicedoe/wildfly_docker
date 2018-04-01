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
import org.joda.time.DateTime;

import com.alicegabbana.restserver.dao.HomeworkDao;
import com.alicegabbana.restserver.dto.HomeworkDto;
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
	
	public Response getResponse ( HomeworkDto homeworkDto ) {	
		
		if ( getService(homeworkDto.getId()) == null ) return authService.returnResponse(404);
		
		HomeworkDto homeworkDtoFound = getService(homeworkDto.getId());
		return authService.returnResponseWithEntity(200, homeworkDtoFound);
	}
	
	public Response getForKidsClassResponse ( KidsClass kidsClass ) {	
		
		if ( kidsClassService.getById(kidsClass.getId()) == null ) return authService.returnResponse(404);
		
		List<HomeworkDto> homeworkDtoList = getForKidsClassService(kidsClass.getId());
		return authService.returnResponseWithEntity(200, homeworkDtoList);
	}
	
	public HomeworkDto getService (Long id) {
		Homework homework = homeworkDao.getById(id);
		HomeworkDto homeworkDto = daoToDto(homework);
		return homeworkDto;
	}
	
	public List<HomeworkDto>  getForKidsClassService (Long id) {
		
		List<Homework> homeworkList = homeworkDao.getForKidsClass(id);
		if ( homeworkList != null ) {
			List<HomeworkDto> homeworkDtoList = daoListToDtoList(homeworkList);
			return homeworkDtoList;
		}
		return null;
	}
	
	public List<HomeworkDto> getAllService () {
		List<Homework> homeworkList = homeworkDao.getAll();
		if ( homeworkList != null ) {
			List<HomeworkDto> homeworkDtoList = daoListToDtoList(homeworkList);
			return homeworkDtoList;
		}
		return null;
	}
	
	public HomeworkDto createService (HomeworkDto homeworkDto) {
		Homework homework = dtoToDao(homeworkDto);
		
		//TODO grab real endDate actually it's everytime tomorrow
		DateTime today = new DateTime();
		DateTime tomorrow = today.plusDays(1);
		homework.setCreationDate(today.toDate());
		homework.setEndDate(tomorrow.toDate());
		
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
			if (homeworkDto.getContent() != null) {
				homework.setContent(homeworkDto.getContent());
			}
			if (homeworkDto.getEndDate() != null) {
				homework.setEndDate(homeworkDto.getEndDate());
			}
		}
		
		return homework;
	}
	
	public HomeworkDto daoToDto (Homework homework) {
		
		HomeworkDto homeworkDto = new HomeworkDto();
		if (homework != null) {
			if (homework.getId() != null) homeworkDto.setId(homework.getId());
			if (homework.getSubject() != null) {
				homeworkDto.setSubjectName(homework.getSubject().getName());
			}
			if (homework.getCreator() != null) {
				homeworkDto.setCreatorId(homework.getCreator().getId());
			}
			if (homework.getKidsClass() != null) {
				homeworkDto.setKidsClassId(homework.getKidsClass().getId());
			}
			if (homework.getContent() != null) {
				homeworkDto.setContent(homework.getContent());
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
				|| homeworkDto.getKidsClassId() == null || homeworkDto.getContent() == null
				|| homeworkDto.getEndDate() == null )
		{	logger.info("missing_attributes");
			return false; }
		
		else if ( kidsClassService.getById(homeworkDto.getKidsClassId()) == null 
				|| userService.getUserById(homeworkDto.getCreatorId()) == null )
		{	logger.info("wrong attributes");
			return false; }
		
		else if ( validateDate(homeworkDto.getEndDate()) )
		{	logger.info("wrong date");
			return false; }
		//TODO check date
		
		else if ( update == false && homeworkDto.getId() != null )
		{	logger.info("forced_id");
			return false; }
		
		return true;
	}
	
	public static boolean validateDate(Date date){
		DateTime today = new DateTime();
		
		if (date.before(today.toDate())) {
			return false;
		}
		
		return true;
	}
}
