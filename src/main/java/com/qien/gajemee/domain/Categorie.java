package com.qien.gajemee.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Where;

@Entity
public class Categorie {
	
	//	DANSEN, POLITIEK, ETEN_EN_DRINKEN, FILMS, SPELLEN, CREATIEF, MUSEUM, WETENSCHAP, CONCERTEN, LITERAIR, THEATER, SPORTIEF, ONTSPANNING;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String categorienaam;

	public long getId() {
		return id;
	}

	public Categorie setId(long id) {
		this.id = id;
		return this;
	}

	public String getCategorie() {
		return categorienaam;
	}

	public Categorie setCategorie(String categorienaam) {
		this.categorienaam = categorienaam;
		return this;
	}
	
	

}
