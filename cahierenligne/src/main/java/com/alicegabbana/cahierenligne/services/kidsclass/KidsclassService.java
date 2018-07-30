package com.alicegabbana.cahierenligne.services.kidsclass;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.alicegabbana.cahierenligne.entities.KidsClass;

@Stateless
public class KidsclassService implements KidsclassServiceLocal, KidsclassServiceRemote {

	private static final long serialVersionUID = -3887177022194360723L;
	
	@PersistenceContext(unitName = "MariadbConnexion")
	EntityManager em;
	
	public KidsClass getByName ( String name ) throws KidsclassException {		
		
		TypedQuery<KidsClass> query_name = em.createQuery("SELECT kidsClass FROM KidsClass kidsClass WHERE kidsClass.name = :name", KidsClass.class)
				.setParameter("name", name);
		List<KidsClass> loadedKidsClass = query_name.getResultList();
		
		if ( loadedKidsClass.size() != 0 ) {
			return loadedKidsClass.get(0);
		} 
		
		throw new KidsclassException(404, "Kidsclass "+name+" does not exist !");
	}

}
