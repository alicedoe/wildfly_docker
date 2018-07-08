package com.alicegabbana.restserver.services.school;

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
import com.alicegabbana.restserver.services.AuthService;
import com.alicegabbana.restserver.services.action.ActionService;
import com.alicegabbana.restserver.services.town.TownService;

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
	
	public boolean schoolNameExist (SchoolDto schoolDto) {
		
		if (schoolDao.getSchoolByName(schoolDto.getName()).getId() != schoolDto.getId() )
			return true;
		
		return false;
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
			Town town = townService.getDaoByName(schoolDto.getTownName());
			school.setTown(town);
		}
		
		return school;
	}
	
	public List<SchoolDto> schoolListToSchoolDtoList (List<School> schoolList) {

		List<SchoolDto> schoolDtoList = new ArrayList<SchoolDto>();
		if (schoolList!=null) {
			for (School school : schoolList) {
				SchoolDto schoolDto = schoolToSchoolDto(school);
				schoolDtoList.add(schoolDto);
			}
		}
		return schoolDtoList;
	}
	
	public School getSchoolByName (String name) {
		return schoolDao.getSchoolByName(name);
	}
	
	public SchoolDto getDtoByIdService (Long id) {
		School school = em.find(School.class, id);
		SchoolDto schooldDto = schoolToSchoolDto(school);
		return schooldDto;
	}
	
	public School getDaoByIdService (Long id) {
		School school = em.find(School.class, id);
		return school;
	}
	
	public SchoolDto createService (SchoolDto schoolDto) {
		School school = schoolDtoToSchool(schoolDto);
		School schoolCreated = em.merge(school);
		SchoolDto schoolDtoCreated = schoolToSchoolDto(schoolCreated);
		return schoolDtoCreated;
	}
	
	public void deleteService (Long id) {
		School school = em.find(School.class, id);
		em.remove(school);
	}
	
	public SchoolDto updateService (SchoolDto schoolDto) {
		School schoolToUpdate = getDaoByIdService(schoolDto.getId()); 
		schoolToUpdate.setName(schoolDto.getName());
		Town town = townService.getDaoByName(schoolDto.getTownName());
		schoolToUpdate.setTown(town);
		School updatedSchool = em.merge(schoolToUpdate);
		SchoolDto schoolDtoUpdated = schoolToSchoolDto(updatedSchool);
		return schoolDtoUpdated;
	}
	
	public List<SchoolDto> getAllService() {
		List<School> loadedSchools = schoolDao.getAllSchools();
		List<SchoolDto> schoolDtoList = schoolListToSchoolDtoList(loadedSchools);
		return schoolDtoList;
	}
	
}
