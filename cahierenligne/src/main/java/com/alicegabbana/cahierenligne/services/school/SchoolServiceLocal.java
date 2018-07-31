package com.alicegabbana.cahierenligne.services.school;
import java.util.List;

import javax.ejb.Local;

import com.alicegabbana.cahierenligne.dto.SchoolDto;
import com.alicegabbana.cahierenligne.entities.School;
import com.alicegabbana.cahierenligne.services.town.TownException;

@Local
public interface SchoolServiceLocal {

	School create (SchoolDto schoolDto) throws SchoolException, TownException;
	List<SchoolDto> getAll();
}
