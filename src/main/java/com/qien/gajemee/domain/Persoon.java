package com.qien.gajemee.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import net.minidev.json.annotate.JsonIgnore;

@Entity
@Where(clause="is_active=1")
@EntityListeners(PersoonUpdateListener.class)
public class Persoon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String voornaam;
	
	private String achternaam;
	
	@NotNull
	@Size(min=10, message="Email should have at least 10 characters")
	private String email;
    
    @NotNull
    @Size(min=5, message="Password should have at least 5 characters")
	private String wachtwoord;						
	
	private String woonplaats;
	
	@ManyToOne
	private Plaats persoonPlaats;
	
	@ManyToMany
	private List <Bericht> berichten = new ArrayList<>();

	public Plaats getPersoonPlaats() {
		return persoonPlaats;
	}

	public void setPersoonPlaats(Plaats persoonPlaats) {
		this.persoonPlaats = persoonPlaats;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Column(name="is_active", nullable = true)
	private boolean active;
	
	@Temporal(TemporalType.DATE)
	private Date verjaardag;    		// leeftijd tonen in het profiel van de persoon
	
	@ManyToMany (cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable (name="persoon_voorkeuren", joinColumns = @JoinColumn (name = "persoon_id"), 
	inverseJoinColumns = @JoinColumn (name= "voorkeuren_id"))
	private Set<Categorie> voorkeuren;
	
	public Persoon() {
		this.active = true;
	}
	
	public Persoon(long id, String voornaam, String achternaam, String email, String wachtwoord, String woonplaats,
			Date verjaardag, Set<Categorie> voorkeuren, List<Bericht> berichten) {
		super();
		this.id = id;
		this.voornaam = voornaam;
		this.achternaam = achternaam;
		this.email = email;
		this.wachtwoord = wachtwoord;
		this.woonplaats = woonplaats;
		this.verjaardag = verjaardag;
		this.voorkeuren = voorkeuren;
		this.berichten = berichten;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public long getId() {
		return this.id;
	}

	public Persoon setId(long id) {
		this.id = id;
		return this;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getAchternaam() {
		return achternaam;
	}

	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	public String getEmail() {
		return email;
	}
	
    @NotNull
    @Size(min=10, message="Email should have at least 10 characters")
	public Persoon setEmail(String email) {
		this.email = email;
		return this;
	}
	
	@JsonIgnore
	public String getWachtwoord() {
		return wachtwoord;
	}
	
    @NotNull
    @Size(min=5, message="Password should have at least 5 characters")
	@JsonProperty(access = Access.WRITE_ONLY)
	public Persoon setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
		return this;
	}

	public String getWoonplaats() {
		return woonplaats;
	}

	public void setWoonplaats(String woonplaats) {
		this.woonplaats = woonplaats;
	}

	public Date getVerjaardag() {
		return verjaardag;
	}

	public Persoon setVerjaardag(Date verjaardag) {
		this.verjaardag = verjaardag;
		return this;
	}

	public Set<Categorie> getVoorkeuren() {
		return voorkeuren;
	}

	public Persoon setVoorkeuren(Set<Categorie> voorkeuren) {
		this.voorkeuren = voorkeuren;
		return this;
	}

	public List<Bericht> getBerichten() {
		return berichten;
	}

	public void setBerichten(List<Bericht> berichten) {
		this.berichten = berichten;
	}

}
