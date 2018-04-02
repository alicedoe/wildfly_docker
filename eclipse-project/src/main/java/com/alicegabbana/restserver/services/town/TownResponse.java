package com.alicegabbana.restserver.services.town;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dto.TownDto;
import com.alicegabbana.restserver.entity.Town;
import com.alicegabbana.restserver.services.AuthService;

@Stateless
public class TownResponse {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	TownService townService;
	
	Logger logger = Logger.getLogger(TownResponse.class);
	
	public Response createResponse(TownDto townDto) {
		
		if ( townDto == null || townDto.getId() != null || townDto.getName().equals("") ) return authService.returnResponse(400);

		if ( townService.nameExist(townDto.getName()) == true ) return authService.returnResponse(409);
		
		Town townCreated = townService.create(townDto);
		return authService.returnResponseWithEntity(201, townCreated);

	}
	
	public Response updateResponse(TownDto townDto) {
		
		if ( townDto == null || townDto.getId() == null || townDto.getName().equals("") || townDto.getName() == null ) return authService.returnResponse(400);
		
		TownDto townDtoUpdated = townService.update(townDto);
		return authService.returnResponseWithEntity(200, townDtoUpdated);

	}
	
	public Response deleteResponse(TownDto townDto) {

		if ( townDto == null || townDto.getId() == null ) return authService.returnResponse(400);
		
		if ( townService.getDaoById(townDto.getId()) == null ) return authService.returnResponse(404);
		
		townService.delete(townDto.getId());
		return authService.returnResponse(200);

	}
	
	public Response getResponse( TownDto townDto ) {
		
		TownDto townDtoFind = townService.getDtoById(townDto.getId());
		if ( townDtoFind == null ) return authService.returnResponse(404);		
		
		return authService.returnResponseWithEntity(200, townDtoFind);

	}
	
	public Response getAllResponse( ) {

		List<TownDto> townDtoList = townService.getAllDto();	

		return authService.returnResponseWithEntity(200, townDtoList);

	}
	
}
