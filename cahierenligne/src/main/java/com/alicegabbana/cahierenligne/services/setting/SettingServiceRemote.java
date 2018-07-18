package com.alicegabbana.cahierenligne.services.setting;

import java.io.Serializable;

import javax.ejb.Remote;

import com.alicegabbana.cahierenligne.entities.Setting;

@Remote
public interface SettingServiceRemote extends Serializable {
	Setting create( Setting setting );
}
