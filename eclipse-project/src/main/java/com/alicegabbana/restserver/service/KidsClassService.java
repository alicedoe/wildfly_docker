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
import com.alicegabbana.restserver.dto.LevelDto;
import com.alicegabbana.restserver.dto.SchoolDto;
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
	
	public Response createResponse ( KidsClassDto kidsClassDto) {
		
		if ( kidsClassIsCorrect(kidsClassDto, false) == false) return authService.returnResponse(400);

		if ( kidsClassDao.kidsClassNameFromLevelExist(kidsClassDto.getName(), kidsClassDto.getLevelName()))
			return authService.returnResponse(409);
		
		KidsClassDto kidsClassDtoCreated = createService(kidsClassDto);
		return authService.returnResponseWithEntity(201, kidsClassDtoCreated);
	}
	
	public Response getResponse ( KidsClassDto kidsClassDto ) {
		
		if (kidsClassDto.getId() == null ) return authService.returnResponse(400);

		if ( getById(kidsClassDto.getId()) == null ) return authService.returnResponse(404);		
		
		return authService.returnResponseWithEntity(200, getService(kidsClassDto.getId()));
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
		
		if ( kidsClassDto == null || kidsClassDto.getId() == null || !kidsClassIsCorrect(kidsClassDto, true) ) 
			return authService.returnResponse(400);

		if ( getById(kidsClassDto.getId()) == null ) return authService.returnResponse(404);
		
		KidsClassDto kidsClassDtoUpdated = updateService(kidsClassDto);
		
		return authService.returnResponseWithEntity(200, kidsClassDtoUpdated);

	}
	
	public Response getFromSchoolResponse (SchoolDto schoolDto) {
		
		if ( schoolDto == null || schoolDto.getId() == null ) return authService.returnResponse(400);
		
		if ( schoolService.getSchoolById(schoolDto.getId()) == null ) return authService.returnResponse(404);		
		
		List<KidsClassDto> kidsClassDtoList = getFromSchoolService(schoolDto.getId());
		return authService.returnResponseWithEntity(200, kidsClassDtoList);
		
	}
	
	public Response getWithLevelResponse (LevelDto levelDto) {
		
		if ( levelDto == null || levelDto.getId() == null ) return authService.returnResponse(400);
		
		if ( levelService.getLevelById(levelDto.getId()) == null ) return authService.returnResponse(404);		
		
		List<KidsClassDto> kidsClassDtoList = getWithLevelService(levelDto.getId());
		return authService.returnResponseWithEntity(200, kidsClassDtoList);
		
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
		if (kidsClassList != null) {
			List<KidsClassDto> kidsClassDtoList = kidsClassListToKidsClassDtoList(kidsClassList);
			return kidsClassDtoList;
		}	else return null;
		
	}
	
	public List<KidsClassDto> getWithLevelService ( Long levelId ) {
		
		List<KidsClass> kidsClassList = kidsClassDao.getKidsClassWithLevel(levelId);
		if (kidsClassList != null) {
		List<KidsClassDto> kidsClassDtoList = kidsClassListToKidsClassDtoList(kidsClassList);
		return kidsClassDtoList;
		}	else return null;		
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
	
	public boolean kidsClassIsCorrect (KidsClassDto kidsClassDto, boolean update) {
		
		if ( kidsClassDto.getSchoolName() == null || kidsClassDto.getLevelName() == null ) 
		{	logger.info("missing_attributes");
			return false; }
		
		if ( kidsClassDto.getSchoolName() == "" || kidsClassDto.getLevelName() == "" ) 
		{	logger.info("missing_attributes");
			return false; }
		
		else if ( !schoolService.schoolNameExist(kidsClassDto.getSchoolName()) || !levelService.levelNameExist(kidsClassDto.getLevelName()) )
		{	logger.info("wrong attributes");
			return false; }
		
		else if ( update == false && kidsClassDto.getId() != null )
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
			
			Level level = levelService.getLevelByName(kidsClassDto.getLevelName());
			kidsClass.setLevel(level);
			
			School school = schoolService.getSchoolByName(kidsClassDto.getSchoolName());
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
