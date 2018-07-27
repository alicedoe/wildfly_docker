package com.alicegabbana.cahierenligne.services.town;
import java.util.List;

import javax.ejb.Local;

import com.alicegabbana.cahierenligne.entities.Town;

@Local
public interface TownServiceLocal {
	
	Town create(String name) throws TownException;
	void delete(Long id) throws TownException;
	List<Town> getAllTowns ();
	Town update(Town town) throws TownException;

}
