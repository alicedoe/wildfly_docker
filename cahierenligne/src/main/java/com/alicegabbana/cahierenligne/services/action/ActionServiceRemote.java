package com.alicegabbana.cahierenligne.services.action;

import java.io.Serializable;

import javax.ejb.Remote;

import com.alicegabbana.cahierenligne.entities.Action;

@Remote
public interface ActionServiceRemote extends Serializable {

	Action create(Action action);
	Action get (String name) throws ActionException;
	
}