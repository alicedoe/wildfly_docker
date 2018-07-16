package com.alicegabbana.restserver.services.action;


import java.io.Serializable;

import javax.ejb.Remote;

import com.alicegabbana.restserver.entity.Action;

@Remote
public interface ActionServiceRemote extends Serializable {

//	Action create(Action action);
	Action test (Action action);
	
}