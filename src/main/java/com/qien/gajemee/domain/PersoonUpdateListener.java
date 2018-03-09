package com.qien.gajemee.domain;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.qien.gajemee.util.AutowireHelper;

public class PersoonUpdateListener {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PreUpdate
	@PrePersist
	public void setPassword(Persoon persoon) {
		AutowireHelper.autowire(this);
		persoon.setWachtwoord(passwordEncoder.encode(persoon.getWachtwoord()));
	}
}
