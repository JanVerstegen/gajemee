package com.qien.gajemee.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Where;

@Entity
public class Bericht{
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	//datum
	//bericht 
	//zender 
	//ontvanger

}
