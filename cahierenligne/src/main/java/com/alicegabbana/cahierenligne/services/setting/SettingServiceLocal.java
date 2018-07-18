package com.alicegabbana.cahierenligne.services.setting;

import javax.ejb.Local;

import com.alicegabbana.cahierenligne.entities.Setting;

@Local
public interface SettingServiceLocal {

	Setting get(String name);
}
