package com.alicegabbana.restserver.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.KidsClassDao;
import com.alicegabbana.restserver.dto.KidsClassDto;
import com.alicegabbana.restserver.dto.UserDto;
import com.alicegabbana.restserver.entity.KidsClass;
import com.alicegabbana.restserver.entity.Level;
import com.alicegabbana.restserver.entity.School;
import com.alicegabbana.restserver.entity.User;

@Stateless
public class KidsClassService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	SchoolService schoolService;
	
	@EJB
	LevelService levelService;
	
	@EJB
	KidsClassDao kidsClassDao;
	
	Logger logger = Logger.getLogger(KidsClassService.class);
	
	public Response createKidsClass ( KidsClassDto kidsClassDto) {
		
		if (kidsClassDto.getId() != null || newKidsClassIsComplete(kidsClassDto) == false) return authService.returnResponse(400);

		if ( kidsClassDao.kidsClassNameFromLevelExist(kidsClassDto.getSchoolName(), kidsClassDto.getLevelName()))
			return authService.returnResponse(409);
		
		KidsClass kidsClass = kidsClassDtoToKidsClass(kidsClassDto);		
		KidsClass kidsClassCreated = em.merge(kidsClass);
		KidsClassDto kidsClassDtoCreated = kidsClassToKidsClassDto(kidsClassCreated);
		return authService.returnResponseWithEntity(201, kidsClassDtoCreated);
	}
	
	public KidsClass getKidsClassByName (String name) {
		return kidsClassDao.getKidsClassByName(name);
	}
	
	public KidsClass getKidsClassById (Long id) {
		return kidsClassDao.getKidsClassById(id);
	}
	
	public boolean newKidsClassIsComplete (KidsClassDto kidsClassDto) {
		
		if ( kidsClassDto.getSchoolName() == "" || kidsClassDto.getLevelName() == "" ) 
		{	logger.info("missing_attributes");
			return false; }
		
		else if ( !schoolService.schoolNameExist(kidsClassDto.getSchoolName()) || !levelService.levelNameExist(kidsClassDto.getLevelName()) )
		{	logger.info("wrong attributes");
			return false; }
		
		else if ( kidsClassDto.getId() != null )
		{	logger.info("forced_id");
			return false; }
		
		return true;
	}
	
public KidsClass kidsClassDtoToKidsClass (KidsClassDto kidsClassDto) {
		
		KidsClass kidsClass = new KidsClass();
		if (kidsClassDto != null) {
			kidsClass.setId(kidsClassDto.getId());
			kidsClass.setName(kidsClassDto.getName());
			
			Level level = levelService.getLevelByName(kidsClass.getLevel().getName());
			kidsClass.setLevel(level);
			
			School school = schoolService.getSchoolByName(kidsClass.getSchool().getName());
			kidsClass.setSchool(school);
		}
		
		return kidsClass;
	}
	
	public KidsClassDto kidsClassToKidsClassDto (KidsClass kidsClass) {
		
		KidsClassDto kidsClassDto = new KidsClassDto();
		if (kidsClass != null) {
			kidsClassDto.setId(kidsClass.getId());
			kidsClassDto.setName(kidsClass.getName());
			
			kidsClassDto.setLevelName(kidsClass.getLevel().getName());
			
			kidsClassDto.setSchoolName(kidsClass.getSchool().getName());
		}
		
		return kidsClassDto;
	}
}
