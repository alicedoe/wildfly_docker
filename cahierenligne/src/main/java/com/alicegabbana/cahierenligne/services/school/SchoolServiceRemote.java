package com.alicegabbana.cahierenligne.services.school;

import java.io.Serializable;
import javax.ejb.Remote;

import com.alicegabbana.cahierenligne.dto.SchoolDto;
import com.alicegabbana.cahierenligne.entities.School;
import com.alicegabbana.cahierenligne.services.town.TownException;

@Remote
public interface SchoolServiceRemote extends Serializable {

	School create (SchoolDto schoolDto) throws SchoolException, TownException;
}
