package com.alicegabbana.cahierenligne.services.kidsclass;
import javax.ejb.Local;

import com.alicegabbana.cahierenligne.entities.KidsClass;

@Local
public interface KidsclassServiceLocal {
	KidsClass getByName ( String name );
}
