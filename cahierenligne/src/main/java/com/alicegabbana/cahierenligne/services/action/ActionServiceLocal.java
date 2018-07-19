package com.alicegabbana.cahierenligne.services.action;

import javax.ejb.Local;

import com.alicegabbana.cahierenligne.entities.Action;

@Local
public interface ActionServiceLocal {

	Action get(String name);
}
