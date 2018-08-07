package com.alicegabbana.cahierenligne.services.town;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.alicegabbana.cahierenligne.entities.Town;

@Stateless
public class TownService implements TownServiceLocal, TownServiceRemote {

	private static final long serialVersionUID = -8100611275480351308L;
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	public List<Town> getAllTowns () {
		
		TypedQuery<Town> query_towns = em.createQuery("SELECT town FROM Town town", Town.class);
		List<Town> loadedTowns = query_towns.getResultList();

		return loadedTowns;
	}
	
	public Town create(String name) throws TownException {
		if ( nameExist(name) ) {
			throw new TownException(409, "Town "+name+" already exist !");
		}
		Town newTown = new Town();
		newTown.setName(name);
		Town townCreated = em.merge(newTown);
		return townCreated;
	}
	
	private boolean nameExist(String name) {
		try {
			get(name);
			return true;
		} catch (TownException e) {
			return false;
		}
	}
	
	public Town get (Long id) throws TownException {
		
		TypedQuery<Town> query_name = em.createQuery("SELECT town FROM Town town WHERE town.id = :id", Town.class)
				.setParameter("id", id);
		List<Town> loadedTowns = query_name.getResultList();

		if ( loadedTowns.size() != 0 ) {
			return loadedTowns.get(0);
		}
		throw new TownException(404, "Town "+id+" not Found !");
	}
	
	public Town get (String name) throws TownException {
		
		TypedQuery<Town> query_name = em.createQuery("SELECT town FROM Town town WHERE town.name = :name", Town.class)
				.setParameter("name", name);
		List<Town> loadedTowns = query_name.getResultList();
		
		if ( loadedTowns.size() != 0 ) {
			return loadedTowns.get(0);
		}
		throw new TownException(404, "Town "+name+" not Found !");
	}
	
	public void delete(Long id) throws TownException {
		try {
			Town town = get(id);
			em.remove(town);
		} catch (TownException e) {
			throw new TownException(404, "Town "+id+" does not exist !");
		}		
	}
	
	public Town update(Town town) throws TownException {
		if ( town.getName() == "" ) {
			throw new TownException(400, "Town has no name !");
		}
		try {
			get(town.getId());
			Town updatedTown = em.merge(town);
			return updatedTown;			
		} catch (TownException e) {
			throw new TownException(404, "Town "+town.getId()+" does not exist !");
		}
	}
	
}
