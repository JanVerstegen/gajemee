package com.qien.gajemee.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qien.gajemee.domain.Evenement;
import com.qien.gajemee.domain.Persoon;

import javassist.NotFoundException;
import javassist.compiler.ast.Symbol;

@Service
@Transactional
public class EvenementService {
	
	@Autowired
	private IEvenementRepository iEvenementRepository;
	
	@Autowired
	private IPersoonRepository persoonRepository;
	
	
	public Evenement addEvenement(Evenement evenement, long id) {
		Persoon organisator = new Persoon();
		organisator.setId(id);
		evenement.setOrganisator(organisator);
		evenement.addAanwezigen(persoonRepository.findById(id));
		return iEvenementRepository.save(evenement);
	}
	
	public void addAanwezigen(long eventId, long persoonId) {
		iEvenementRepository.findById(eventId).addAanwezigen(persoonRepository.findById(persoonId));
	}
	
	public void delAanwezigen(long eventId, long persoonId) {
		iEvenementRepository.findById(eventId).delAanwezigen(persoonRepository.findById(persoonId));
	}
	
	public Evenement updateEvenement(Evenement evenement) {
		return iEvenementRepository.save(evenement);
	}
	
	public List <Evenement> findAll(){
		return iEvenementRepository.findAllOrderByDatumDESC();
	}
	
	public List <Evenement> findEvenementenEnPlaatsByEvenementPlaatsNaam(String evenementPlaats){
		return iEvenementRepository.findEvenementenEnPlaatsByEvenementPlaats(evenementPlaats);
	}
	
	public List <Evenement> findEvenementEnPlaatsByPersoonPlaatsNaam(String persoonPlaats){
		return iEvenementRepository.findEvenementenEnPlaatsByPersoonPlaatsNaam(persoonPlaats);
	}
	
	public List <Evenement> findEvenementEnPlaatsByPersoonId(long persoonId){
		return iEvenementRepository.findEvenementenEnPlaatsByPersoonID(persoonId);
	}
	
	/*
	public Iterable <Evenement> findAll(){
		Iterable <Evenement> result = iEvenementRepository.findAll();
		return result;
	}
	*/
	
	public void deleteEvenement(long id) {
		Evenement evenement = iEvenementRepository.findById(id);
		evenement.setActive(false);
		iEvenementRepository.save(evenement);
	}
	
	//werkt
	public Evenement findEvementById(long id) throws NotFoundException {
		if (iEvenementRepository.findById(id) == null) {
			throw new NotFoundException("event not found");
		}
		return iEvenementRepository.findById(id);
	}
	
	//werkt
	public List<Evenement> findEvenementByEvenementNaam(String evenementNaam) throws NotFoundException{
		if (iEvenementRepository.findEvenementByEvenementNaamIgnoreCaseOrderByDatumDESC(evenementNaam) == null) {
			throw new NotFoundException("event not found");
		}
		return iEvenementRepository.findEvenementByEvenementNaamIgnoreCaseOrderByDatumDESC(evenementNaam);
	}
	
	//werkt
	public List<Evenement> findEvenementByWoordInNaam (String woord){
		woord = "%"+ woord + "%";
		return iEvenementRepository.findEvenementenContainingIgnoreCaseOrderByDatumASC(woord);
	}
	
	//werkt
	public List<Evenement> findEvenementByPlaats (String plaats){
		return iEvenementRepository.findEvenementByEvenementPlaatsIgnoreCaseOrderByDatumASC(plaats);
	}
	
	public List<Evenement> findEvenementByCategorie (String categorienaam){
		return iEvenementRepository.findEvenementByEvenementCategorieIgnoreCaseOrderByDatumASC(categorienaam);
	}
	
	public List<Evenement> findEvenementByCategorieId (long categorieId){
		return iEvenementRepository.findEvenementByCategorieId(categorieId);
	}
	
	public List<Evenement> findEvenementenByOrganisatorId (long organisatorId){
		return iEvenementRepository.findByOrganisatorId(organisatorId);
	}
 	
	public List<Evenement> findEvenementByOrganisatorNaam (String organisatorNaam){
		return iEvenementRepository.findByOrganisatorNaam(organisatorNaam);
	}
	
}
