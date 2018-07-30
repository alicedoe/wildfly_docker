package com.alicegabbana.cahierenligne.services.school;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
			if (newSchoolIsComplete(schoolDto) ) {
				throw new SchoolException(400, "School "+schoolDto.toString()+" is not complete !");
			}
			School school = dtoToDao(schoolDto);
			School schoolCreated = em.merge(school);
			return schoolCreated;
		} catch (TownException e) {
			logger.error(e.getMessage());
			throw new SchoolException(400, e.getMessage());
		}
	}
	
	public School get (Long id) throws SchoolException {
		School school = em.find(School.class, id);
		if ( school == null ) {
			throw new SchoolException(404, "School "+id+" not found !");
		}
		return school;
	}
	
	private boolean newSchoolIsComplete(SchoolDto schoolDto) {
		if (schoolDto.getId() != null
				|| schoolDto.getSchoolName() == null ) {
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
