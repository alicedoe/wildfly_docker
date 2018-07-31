package com.alicegabbana.cahierenligne.services.school;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.alicegabbana.cahierenligne.dto.SchoolDto;
import com.alicegabbana.cahierenligne.entities.School;
import com.alicegabbana.cahierenligne.entities.User;
import com.alicegabbana.cahierenligne.services.town.TownException;
import com.alicegabbana.cahierenligne.services.town.TownServiceLocal;

@Stateless
public class SchoolService implements SchoolServiceLocal, SchoolServiceRemote {

	private static final long serialVersionUID = 716711494229813939L;
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	TownServiceLocal townService;
	
	Logger logger = Logger.getLogger(SchoolService.class);
	
	public School create (SchoolDto schoolDto) throws SchoolException, TownException {		
		try {
			if ( ! newSchoolIsComplete(schoolDto) ) {
				throw new SchoolException(400, "School "+schoolDto.toString()+" is not complete !");
			}
			if ( schoolExist(schoolDto.getSchoolName())) {
				throw new SchoolException(409, "School "+schoolDto.getSchoolName()+" already exist !");
			}
			School school = dtoToDao(schoolDto);
			School schoolCreated = em.merge(school);
			return schoolCreated;
		} catch (TownException e) {
			logger.error(e.getMessage());
			throw new SchoolException(400, e.getMessage());
		}
	}
	
	private boolean schoolExist (String name) {
		try {
			get(name);
			return true;
		} catch (SchoolException e) {
			return false;
		}
	}
	
	private School get(String name) throws SchoolException {
		TypedQuery<School> query_name = em.createQuery("SELECT school FROM School school WHERE school.name = :name", School.class)
				.setParameter("name", name);
		List<School> loadedSchools = query_name.getResultList();
		
		if ( loadedSchools.size() != 0 ) {
			return loadedSchools.get(0);
		} 
		
		throw new SchoolException(404, "School "+name+" does not exist !");
	}
	
	public School get (Long id) throws SchoolException {
		School school = em.find(School.class, id);
		if ( school == null ) {
			throw new SchoolException(404, "School "+id+" not found !");
		}
		return school;
	}
	
	public List<SchoolDto> getAll() {
		List<School> allSchools = em.createQuery("SELECT school FROM School school", School.class)
				.getResultList();
		
		List<SchoolDto> schoolsDto = schoolListToSchoolDtoList(allSchools);
		return schoolsDto;
	}
	
	private List<SchoolDto> schoolListToSchoolDtoList (List<School> schoolList) {

		List<SchoolDto> schoolDtoList = new ArrayList<SchoolDto>();
		for (School school : schoolList) {
			SchoolDto schoolDto = daoToDto(school);
			schoolDtoList.add(schoolDto);
		}
		return schoolDtoList;
	}
	
	private SchoolDto daoToDto (School school) {
		
		SchoolDto schoolDto = new SchoolDto();
		schoolDto.setId(school.getId());
		schoolDto.setSchoolName(school.getName());
		schoolDto.setTownName(school.getTown().getName());
		
		return schoolDto;
	}	
	
	private boolean newSchoolIsComplete(SchoolDto schoolDto) {
		if (schoolDto.getId() != null || schoolDto.getSchoolName() == ""
				) {
			return false;
		}
 
		try {
			townService.get(schoolDto.getTownName());
		} catch (TownException e) {
			return false;
		}		
		return true;
	}
	
	private School dtoToDao (SchoolDto schoolDto) throws TownException {
		School school = new School();
		school.setName(schoolDto.getSchoolName());
		try {
			school.setTown(townService.get(schoolDto.getTownName()));
		} catch (TownException e) {
			throw new TownException(e.getCode(), e.getMessage());
		}
		
		return school;		
	}
}
