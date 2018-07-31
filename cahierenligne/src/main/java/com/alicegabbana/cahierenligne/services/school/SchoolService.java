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
			newSchoolIsComplete(schoolDto);
			if ( schoolExist(schoolDto.getSchoolName())) {
				throw new SchoolException(409, "School "+schoolDto.getSchoolName()+" already exist !");
			}
			School school = dtoToDao(schoolDto);
			School schoolCreated = em.merge(school);
			return schoolCreated;
		} catch (TownException e) {
			throw new SchoolException(400, e.getMessage());
		} catch (SchoolException e) {
			throw new SchoolException(e.getCode(), e.getMessage());
		}
	}
	
	public SchoolDto update(SchoolDto schoolDto) throws SchoolException, TownException{
		if ( schoolDto.getSchoolName() == "" ) {
			throw new TownException(400, "School has no name !");
		}
		try {
			get(schoolDto.getId());
			School school = dtoToDao(schoolDto);
			School updatedSchool = em.merge(school);
			return daoToDto(updatedSchool);			
		} catch (SchoolException e) {
			throw new TownException(404, "School "+schoolDto.getId()+" does not exist !");
		}
	}
	
	
	public School get (Long id) throws SchoolException {
		School school = em.find(School.class, id);
		if ( school == null ) {
			throw new SchoolException(404, "School "+id+" not found !");
		}
		return school;
	}
	
	public SchoolDto getDto (Long id) throws SchoolException {
		try {
			School school = get(id);
			return daoToDto(school);
		} catch (SchoolException e) {
			throw new SchoolException(e.getCode(),e.getMessage());
		}
	}
	
	public List<SchoolDto> getAll() {
		List<School> allSchools = em.createQuery("SELECT school FROM School school", School.class)
				.getResultList();
		
		List<SchoolDto> schoolsDto = schoolListToSchoolDtoList(allSchools);
		return schoolsDto;
	}
	
	public void delete(Long id) throws SchoolException {
		try {
			School school = get(id);
			em.remove(school);
		} catch (SchoolException e) {
			throw new SchoolException(404, "School "+id+" does not exist !");
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
	
	private boolean newSchoolIsComplete(SchoolDto schoolDto) throws TownException, SchoolException {
		if (schoolDto.getId() != null || schoolDto.getSchoolName() == ""
				) {
			throw new SchoolException(400, "Schoolname is empty or id is not null");
		}
 
		try {
			townService.get(schoolDto.getTownName());
		} catch (TownException e) {
			throw new TownException(e.getCode(),e.getMessage());
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
