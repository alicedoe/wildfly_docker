package com.alicegabbana.restserver.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.KidsClassDao;
import com.alicegabbana.restserver.dto.KidsClassDto;
import com.alicegabbana.restserver.entity.KidsClass;
import com.alicegabbana.restserver.entity.Level;
import com.alicegabbana.restserver.entity.School;

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
	
///////////////	
//	Response //
///////////////	
	
	public Response createResponse ( KidsClassDto kidsClassDto) {
		
		if (kidsClassDto.getId() != null || newKidsClassIsComplete(kidsClassDto) == false) return authService.returnResponse(400);

		if ( kidsClassDao.kidsClassNameFromLevelExist(kidsClassDto.getSchoolName(), kidsClassDto.getLevelName()))
			return authService.returnResponse(409);
		
		return authService.returnResponseWithEntity(201, createService(kidsClassDto));
	}
	
	public Response getResponse ( Long kidsClassId ) {
		
		if (kidsClassId == null ) return authService.returnResponse(400);

		if ( getById(kidsClassId) == null ) return authService.returnResponse(404);		
		
		return authService.returnResponseWithEntity(200, getService(kidsClassId));
	}
	
	public Response getAllResponse ( ) {
		
		
		return authService.returnResponseWithEntity(200, getAllService());
	}
	
	public Response deleteResponse(Long kidsClassId) {
		
		if ( kidsClassId == null ) return authService.returnResponse(400);
		
		if ( getById(kidsClassId) == null ) return authService.returnResponse(404);

		deleteService(kidsClassId);
		return authService.returnResponse(200);
	}
	
	public Response updateResponse (KidsClassDto kidsClassDto) {
		
		if ( kidsClassDto == null || kidsClassDto.getId() == null ) return authService.returnResponse(400);

		if ( getById(kidsClassDto.getId()) == null ) return authService.returnResponse(404);
		
		KidsClassDto kidsClassDtoUpdated = updateService(kidsClassDto);
		
		return authService.returnResponseWithEntity(200, kidsClassDtoUpdated);

	}
	
	public Response getFromSchoolResponse (Long schoolId) {
		
		if ( schoolId == null ) return authService.returnResponse(400);
		
		if ( schoolService.getSchoolById(schoolId) == null ) return authService.returnResponse(404);		
		
		return authService.returnResponseWithEntity(200, getFromSchoolService ( schoolId ));
		
	}

/////////////	
// Service //
/////////////	

	public List<KidsClassDto> getAllService () {
		List<KidsClass> kidsClassList = kidsClassDao.getAllKidsClass();
		List<KidsClassDto> kidsClassDtoList = kidsClassListToKidsClassDtoList(kidsClassList);
		return kidsClassDtoList;
	}
	
	public KidsClassDto getService (Long kidsClassId) {
		KidsClass kidsClass = getById(kidsClassId);
		KidsClassDto kidsClassDto = kidsClassToKidsClassDto(kidsClass);
		return kidsClassDto;
	}
	
	public KidsClassDto createService (KidsClassDto kidsClassDto) {
		KidsClass kidsClass = kidsClassDtoToKidsClass(kidsClassDto);		
		KidsClass kidsClassCreated = em.merge(kidsClass);
		KidsClassDto kidsClassDtoCreated = kidsClassToKidsClassDto(kidsClassCreated);
		return kidsClassDtoCreated;
	}	
	
	public List<KidsClassDto> getFromSchoolService ( Long schoolId ) {
		
		List<KidsClass> kidsClassList = kidsClassDao.getKidsClassFromSchool(schoolId);
		List<KidsClassDto> kidsClassDtoList = kidsClassListToKidsClassDtoList(kidsClassList);
		return kidsClassDtoList;
		
	}
	
	public void deleteService (Long id) {
		KidsClass KidsClass = em.find(KidsClass.class, id);
		em.remove(KidsClass);
	}
	
	public KidsClassDto updateService(KidsClassDto kidsClassDto) {
		
		KidsClass kidsClassToUpdate = getById(kidsClassDto.getId()); 
		kidsClassToUpdate.setName(kidsClassDto.getName());
		kidsClassToUpdate.setLevel(levelService.getLevelByName(kidsClassDto.getLevelName()));
		kidsClassToUpdate.setSchool(schoolService.getSchoolByName(kidsClassDto.getSchoolName()));
		KidsClass updatedKidsClass = em.merge(kidsClassToUpdate);
		KidsClassDto kidsClassDtoUpdated = kidsClassToKidsClassDto(updatedKidsClass);
		
		return kidsClassDtoUpdated;
	}
	
	public KidsClass getByName (String name) {
		return kidsClassDao.getKidsClassByName(name);
	}
	
	public KidsClass getById (Long id) {
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
	
	public List<KidsClassDto> kidsClassListToKidsClassDtoList (List<KidsClass> kidsClassList) {

		List<KidsClassDto> kidsClassDtoList = new ArrayList<KidsClassDto>();
		for (KidsClass kidsClass : kidsClassList) {
			KidsClassDto kidsClassDto = kidsClassToKidsClassDto(kidsClass);
			kidsClassDtoList.add(kidsClassDto);
		}

		return kidsClassDtoList;
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
