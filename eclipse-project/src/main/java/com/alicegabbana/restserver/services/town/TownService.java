package com.alicegabbana.restserver.services.town;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.alicegabbana.restserver.dao.TownDao;
import com.alicegabbana.restserver.dto.TownDto;
import com.alicegabbana.restserver.entity.Town;
import com.alicegabbana.restserver.services.AuthService;

@Stateless
public class TownService {
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	@EJB
	AuthService authService;
	
	@EJB
	TownDao townDao;
	
	Logger logger = Logger.getLogger(TownService.class);
	
	public boolean nameExist(String name) {
		
		if (getDaoByName(name) == null) {
			return false;
		}		
		return true;
	}
	
	public TownDto create(TownDto townDto) {
		Town townCreated = em.merge(townDto);
		TownDto townDtoCreated = daoToDto(townCreated);
		return townDtoCreated;
	}
	
	public TownDto update(TownDto townDto) {
		Town town = getDaoById(townDto.getId());
		if (townDto.getName() != null) {
			town.setName(townDto.getName());
		}		
		Town updatedTown = em.merge(town);
		return daoToDto(updatedTown);
	}
	
	public TownDto daoToDto(Town town) {
		TownDto townDto = new TownDto();
		if (town.getName() != null) {
			townDto.setName(town.getName());
		}
		if (town.getId() != null) {
			townDto.setId(town.getId());
		}
		return townDto;
	}
	
	public void delete(Long id) {
		Town town = em.find(Town.class, id);
		em.remove(town);
	}
	
	public Town getDaoById (Long id) {
		return townDao.getTownById(id);
	}
	
	public Town getDaoByName (String name) {
		return townDao.getTownByName(name);
	}
	
	public TownDto getDtoById (Long id) {
		Town town = townDao.getTownById(id);
		return daoToDto(town);
	}
	
	public TownDto getDtoByName (String name) {
		Town town = townDao.getTownByName(name);
		return daoToDto(town);
	}
	
	public List<Town> getAllDao() {
		List<Town> loadedTowns = townDao.getAllTowns();
		return loadedTowns;
	}
	
	public List<TownDto> getAllDto() {
		List<Town> townDaoList = townDao.getAllTowns();
		List<TownDto> townDtoList = new ArrayList<TownDto>();
		if (townDaoList!=null) {
			for (Town town : townDaoList) {
				TownDto townDto = daoToDto(town);
				townDtoList.add(townDto);
			}
		}
		return townDtoList;
	}
	
}
