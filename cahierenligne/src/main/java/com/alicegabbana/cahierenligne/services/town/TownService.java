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

	public Town create(String name) throws TownException {
		if ( nameExist(name) ) {
			throw new TownException(409);
		}
		Town newTown = new Town();
		newTown.setName(name);
		Town townCreated = em.merge(newTown);
		return townCreated;
	}
	
	public Town get (Long id) throws TownException {
		
		TypedQuery<Town> query_name = em.createQuery("SELECT town FROM Town town WHERE town.id = :id", Town.class)
				.setParameter("id", id);
		List<Town> loadedTowns = query_name.getResultList();

		if ( loadedTowns.size() != 0 ) {
			return loadedTowns.get(0);
		}
		throw new TownException(404);
	}
	
	private boolean nameExist(String name) {
		try {
			get(name);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Town get (String name) throws TownException {
		
		TypedQuery<Town> query_name = em.createQuery("SELECT town FROM Town town WHERE town.name = :name", Town.class)
				.setParameter("name", name);
		List<Town> loadedTowns = query_name.getResultList();
		
		if ( loadedTowns.size() != 0 ) {
			return loadedTowns.get(0);
		}
		throw new TownException(404);
	}
	
	public void delete(Long id) throws TownException {
		try {
			Town town = get(id);
			em.remove(town);
		} catch (TownException e) {
			throw new TownException(404);
		}		
	}
	
	public List<Town> getAllTowns () {
		
		TypedQuery<Town> query_towns = em.createQuery("SELECT town FROM Town town", Town.class);
		List<Town> loadedTowns = query_towns.getResultList();

		return loadedTowns;
	}
	
	public Town update(Town town) throws TownException {
		try {
			get(town.getId());
			Town updatedTown = em.merge(town);
			return updatedTown;			
		} catch (TownException e) {
			throw new TownException(404);
		}
	}
}
