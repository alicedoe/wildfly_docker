package com.alicegabbana.restserver.services.kidsclass;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.KidsClassDao;
import com.alicegabbana.restserver.dto.KidsClassDto;
import com.alicegabbana.restserver.entity.KidsClass;
import com.alicegabbana.restserver.entity.Level;
import com.alicegabbana.restserver.entity.School;
import com.alicegabbana.restserver.services.level.LevelService;
import com.alicegabbana.restserver.services.school.SchoolService;

@Stateless
public class KidsClassService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	SchoolService schoolService;
	
	@EJB
	LevelService levelService;
	
	@EJB
	KidsClassDao kidsClassDao;
	
	Logger logger = Logger.getLogger(KidsClassService.class);

	public List<KidsClassDto> getAllService () {
		List<KidsClass> kidsClassList = kidsClassDao.getAllKidsClass();
		if (kidsClassList!= null) {
			List<KidsClassDto> kidsClassDtoList = daoListToDtoList(kidsClassList);
			return kidsClassDtoList;
		}
		return null;
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
			List<KidsClassDto> kidsClassDtoList = daoListToDtoList(kidsClassList);
			return kidsClassDtoList;
		}	else return null;
		
	}
	
	public List<KidsClassDto> getWithLevelService ( Long levelId ) {
		
		List<KidsClass> kidsClassList = kidsClassDao.getKidsClassWithLevel(levelId);
		if (kidsClassList != null) {
			List<KidsClassDto> kidsClassDtoList = daoListToDtoList(kidsClassList);
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
		
		if ( kidsClassDto.getSchoolName().equals("") || kidsClassDto.getLevelName().equals("")) 
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
	
	public List<KidsClassDto> daoListToDtoList (List<KidsClass> kidsClassList) {

		List<KidsClassDto> kidsClassDtoList = new ArrayList<KidsClassDto>();
		if (kidsClassList != null) {
			for (KidsClass kidsClass : kidsClassList) {
				KidsClassDto kidsClassDto = kidsClassToKidsClassDto(kidsClass);
				kidsClassDtoList.add(kidsClassDto);
			}
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
