package com.qien.gajemee.domain;

import java.lang.annotation.Repeatable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;

@Entity
@Where(clause="is_active=1")
public class Evenement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String evenementNaam;
	private Date aanvangsDatumTijd;
	private Date eindDatumTijd;
	private String adres;
	private String plaats;
	
	@Column(name="is_active", nullable = true)
	private boolean active;
	
	@ManyToOne
	private Plaats evenementPlaats;
	
	@ManyToOne
	private Categorie categorie;
	
	private String uitnodigingstekst;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name="evenement_aanwezigen", joinColumns = @JoinColumn(name="evenement_id"), 
	inverseJoinColumns = @JoinColumn(name="aanwezigen_id"))
	private Set<Persoon> aanwezigen;
	
	@ManyToOne
	private Persoon organisator;

	
	public Evenement() {	
		this.active = true;
	}
	
	
	public Evenement(long id, String evenementNaam, Date aanvangsDatumTijd, Date eindDatumTijd, String adres,
			String plaats, boolean active, Categorie categorie, String uitnodigingstekst, Set<Persoon> aanwezigen,
			Persoon organisator) {
		super();
		this.id = id;
		this.evenementNaam = evenementNaam;
		this.aanvangsDatumTijd = aanvangsDatumTijd;
		this.eindDatumTijd = eindDatumTijd;
		this.adres = adres;
		this.plaats = plaats;
		this.active = active;
		this.categorie = categorie;
		this.uitnodigingstekst = uitnodigingstekst;
		this.aanwezigen = aanwezigen;
		this.organisator = organisator;
	}


	public Plaats getEvenementPlaats() {
		return evenementPlaats;
	}


	public void setEvenementPlaats(Plaats evenementPlaats) {
		this.evenementPlaats = evenementPlaats;
	}


	public Categorie getCategorie() {
		return categorie;
	}



	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}



	public Date getAanvangsDatumTijd() {
		return aanvangsDatumTijd;
	}



	public void setAanvangsDatumTijd(Date aanvangsDatumTijd) {
		this.aanvangsDatumTijd = aanvangsDatumTijd;
	}



	public Date getEindDatumTijd() {
		return eindDatumTijd;
	}



	public void setEindDatumTijd(Date eindDatumTijd) {
		this.eindDatumTijd = eindDatumTijd;
	}



	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEvenementNaam() {
		return evenementNaam;
	}

	public void setEvenementNaam(String evenementNaam) {
		this.evenementNaam = evenementNaam;
	}

	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public String getPlaats() {
		return plaats;
	}

	public void setPlaats(String plaats) {
		this.plaats = plaats;
	}

	
	public String getUitnodigingstekst() {
		return uitnodigingstekst;
	}

	public void setUitnodigingstekst(String uitnodigingstekst) {
		this.uitnodigingstekst = uitnodigingstekst;
	}


	public Set<Persoon> getAanwezigen() {
		return aanwezigen;
	}

	public void setAanwezigen(Set<Persoon> aanwezigen) {
		this.aanwezigen = aanwezigen;
	}

	public void addAanwezigen(Persoon persoon) {
		aanwezigen.add(persoon);
	}
	
	public void delAanwezigen(Persoon persoon) {
		aanwezigen.remove(persoon);
	}
	
	public Persoon getOrganisator() {
		return organisator;
	}

	public void setOrganisator(Persoon organisator) {
		this.organisator = organisator;
	}


	
	

}



