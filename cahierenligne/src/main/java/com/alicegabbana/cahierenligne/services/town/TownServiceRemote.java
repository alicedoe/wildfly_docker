package com.alicegabbana.cahierenligne.services.town;

import java.io.Serializable;

import javax.ejb.Remote;

import com.alicegabbana.cahierenligne.entities.Town;

@Remote
public interface TownServiceRemote extends Serializable {
	Town create(String name) throws TownException;
	Town get (String name) throws TownException;
}
