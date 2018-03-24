package com.alicegabbana.restserver.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;
import org.jboss.logging.Logger;
import com.alicegabbana.restserver.dao.SchoolDao;
import com.alicegabbana.restserver.dto.SchoolDto;
import com.alicegabbana.restserver.entity.School;
import com.alicegabbana.restserver.entity.Town;

@Stateless
public class SchoolService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	TownService townService;
	
	@EJB
	ActionService actionService;
	
	@EJB
	SchoolDao schoolDao;
	
	Logger logger = Logger.getLogger(SchoolService.class);
	
	public Response createSchool(SchoolDto schoolDto) {		
		
		if (schoolDto.getId() != null) return authService.returnResponse(400);

		if ( schoolNameExist(schoolDto.getName()) ) return authService.returnResponse(409);
		
		School school = schoolDtoToSchool(schoolDto);
		School schoolCreated = em.merge(school);
		SchoolDto schoolDtoCreated = schoolToSchoolDto(schoolCreated);
		return authService.returnResponseWithEntity(201, schoolDtoCreated);
		
	}
	
	public Response deleteSchool(School school) {
		
		if ( school == null || school.getId() == null ) return authService.returnResponse(400);
		
		if ( schoolNameExist(school.getName()) == false ) return authService.returnResponse(404);

		school = em.find(School.class, school.getId());
		em.remove(school);
		return authService.returnResponse(200);
	}
	
	public Response updateSchool(SchoolDto schoolDto) {
		
		if ( schoolDto == null || schoolDto.getId() == null ) return authService.returnResponse(400);

		if ( schoolNameExist(schoolDto.getName()) == false ) return authService.returnResponse(404);
		
		School schoolToUpdate = getSchoolById(schoolDto.getId()); 
		schoolToUpdate.setName(schoolDto.getName());
		Town town = townService.getTownByName(schoolDto.getTownName());
		schoolToUpdate.setTown(town);
		School updatedSchool = em.merge(schoolToUpdate);
		SchoolDto schoolDtoUpdated = schoolToSchoolDto(updatedSchool);
		return authService.returnResponseWithEntity(200, schoolDtoUpdated);

	}
	
	public Response addTownToSchool(Long schoolId, Long townId) {
		
		if ( schoolId == null || townId == null) 
			return authService.returnResponse(400);
		
		if ( getSchoolById(schoolId) == null ) return authService.returnResponse(404);
		
		School school = getSchoolById(schoolId);
		Town town = townService.getTownById(townId);
		school.setTown(town);
		School schoolUpdated = em.merge(school);
		return authService.returnResponseWithEntity(200, schoolUpdated);
	}
	
	public Response getSchool(Long schoolId) {
		
		if ( schoolId == null ) return authService.returnResponse(400);
		
		if ( getSchoolById(schoolId) == null ) return authService.returnResponse(404);
		
		School school = em.find(School.class, schoolId);
		SchoolDto schooldDto = schoolToSchoolDto(school);
		return authService.returnResponseWithEntity(200, schooldDto);
		
	}
	
	public Response getAllSchool() {
		
		List<School> loadedSchools = schoolDao.getAllSchools();
				
		if ( loadedSchools == null ) return authService.returnResponse(404);		

		List<SchoolDto> schoolDtoList = schoolListToSchoolDtoList(loadedSchools);
		return authService.returnResponseWithEntity(200, schoolDtoList);
		
	}
	
	public boolean schoolNameExist (String name) {
		
		if ( schoolDao.getSchoolByName(name) == null ) return false;
		return true;
	}
	
	public SchoolDto schoolToSchoolDto (School school) {
		
		SchoolDto schoolDto = new SchoolDto();
		if (school != null) {
			schoolDto.setId(school.getId());
			schoolDto.setName(school.getName());
			schoolDto.setTownName(school.getTown().getName());
		}
		
		return schoolDto;
	}
	
	public School schoolDtoToSchool (SchoolDto schoolDto) {
		
		School school = new School();
		if (schoolDto != null) {
			school.setId(schoolDto.getId());
			school.setName(schoolDto.getName());
			Town town = townService.getTownByName(schoolDto.getTownName());
			school.setTown(town);
		}
		
		return school;
	}
	
	public List<SchoolDto> schoolListToSchoolDtoList (List<School> schoolList) {

		List<SchoolDto> schoolDtoList = new ArrayList<SchoolDto>();
		for (School school : schoolList) {
			SchoolDto schoolDto = schoolToSchoolDto(school);
			schoolDtoList.add(schoolDto);
		}

		return schoolDtoList;
	}
	
	public School getSchoolByName (String name) {
		return schoolDao.getSchoolByName(name);
	}
	
	public School getSchoolById (Long id) {
		return schoolDao.getSchoolById(id);
	}
	
}
