package com.qien.gajemee.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.qien.gajemee.domain.Evenement;
import com.qien.gajemee.domain.Persoon;

@Component
public interface IEvenementRepository extends CrudRepository <Evenement, Long> {

	//werkt!
	@Query(value = "SELECT * FROM evenement JOIN plaats ON evenement_plaats_id=plaats.id WHERE LOWER (plaats.plaatsnaam) = ?1 AND (is_active=true) AND "
			+ "(eind_datum_tijd >= CURDATE()) ORDER BY aanvangs_datum_tijd ASC", nativeQuery = true)
			List <Evenement> findEvenementenEnPlaatsByEvenementPlaats(String evenementPlaats);  
	
	//werkt!
	@Query(value= "SELECT DISTINCT * FROM evenement JOIN plaats ON evenement_plaats_id=plaats.id WHERE evenement_plaats_id IN (SELECT DISTINCT persoon_plaats_id FROM persoon JOIN plaats ON plaats.id=persoon_plaats_id WHERE LOWER(plaats.plaatsnaam)=?1) "
			+ "AND is_active=true AND (eind_datum_tijd >= CURDATE()) ORDER BY aanvangs_datum_tijd ASC", nativeQuery=true)
	List <Evenement> findEvenementenEnPlaatsByPersoonPlaatsNaam (String persoonPlaats);
	
	//werkt!
	@Query(value= "SELECT DISTINCT * FROM evenement JOIN plaats ON evenement_plaats_id=plaats.id WHERE evenement_plaats_id IN (SELECT DISTINCT persoon_plaats_id FROM persoon JOIN plaats ON plaats.id=persoon_plaats_id WHERE persoon.id=?1) "
			+ "AND is_active=true AND (eind_datum_tijd >= CURDATE()) ORDER BY aanvangs_datum_tijd ASC", nativeQuery=true)
	List <Evenement> findEvenementenEnPlaatsByPersoonID (long persoonId);
	
	
	//werkt!
	@Query(value="SELECT DISTINCT * FROM evenement WHERE (is_active = true) AND (eind_datum_tijd >= CURDATE())"
			+ "ORDER BY aanvangs_datum_tijd ASC", nativeQuery=true)
	List<Evenement> findAllOrderByDatumDESC ();
	//SELECT * FROM evenement WHERE aanvangsDatumTijd >= CURDATE();
	
	//werkt!
	@Query(value="SELECT DISTINCT * FROM evenement WHERE LOWER(evenement_naam) LIKE (?1) AND (is_active = true) AND (eind_datum_tijd >= CURDATE())"
			+ " ORDER BY aanvangs_datum_tijd DESC", nativeQuery=true)
	List<Evenement> findEvenementenContainingIgnoreCaseOrderByDatumASC (String woord);
	//ORDER BY datum DESC

	//werkt!
	@Query(value="SELECT DISTINCT * FROM evenement WHERE LOWER(evenement_naam) = LOWER(?1) AND (is_active =true)"
			+ "ORDER BY aanvangs_datum_tijd DESC", nativeQuery=true)
	List<Evenement> findEvenementByEvenementNaamIgnoreCaseOrderByDatumDESC (String evenementNaam);
	
	
	//werkt!
	@Query(value="SELECT DISTINCT * FROM evenement WHERE LOWER(plaats) = LOWER(?1) AND (is_active =true) AND (eind_datum_tijd >= CURDATE()) ORDER BY aanvangs_datum_tijd ASC", nativeQuery=true)
	List<Evenement> findEvenementByEvenementPlaatsIgnoreCaseOrderByDatumASC (String plaats);
	//ORDER BY datum ASC"
	
	//werkt!
	@Query(value= "SELECT DISTINCT * FROM evenement JOIN categorie ON categorie.id=evenement.categorie_id WHERE LOWER(categorie.categorienaam) = LOWER(?1) AND (is_active =true) AND (eind_datum_tijd >= CURDATE()) "
			+ "ORDER BY aanvangs_datum_tijd ASC", nativeQuery = true)
	List<Evenement> findEvenementByEvenementCategorieIgnoreCaseOrderByDatumASC (String categorienaam);

	
	//werkt!
	@Query(value = "SELECT DISTINCT * FROM evenement WHERE categorie_id = ?1 AND (is_active =true) AND (eind_datum_tijd >= CURDATE()) ORDER BY aanvangs_datum_tijd ASC", nativeQuery=true)
	List<Evenement> findEvenementByCategorieId (long categorieId);
	
	//werkt!
	Evenement findById (long evenementId);
	
	@Query(value = "SELECT * FROM evenement WHERE organisator_id = ?1 AND is_active =true ORDER BY aanvangs_datum_tijd DESC", nativeQuery=true)
	List<Evenement> findByOrganisatorId(long organisatorId);
	
	@Query(value = "SELECT * FROM evenement JOIN persoon ON evenement.organisator_id=persoon.id WHERE LOWER(persoon.voornaam) = LOWER(?1) AND (persoon.is_active = true) ORDER BY aanvangs_datum_tijd DESC", nativeQuery=true)
	List<Evenement> findByOrganisatorNaam (String naamOrganisator);
	
	@Query(value = "SELECT * FROM evenement WHERE organisator_id = ?1 AND is_active = true", nativeQuery=true)
	Evenement findByOrganisator(long id);
	//deze methode wordt nu gebruikt om evenementen op inactief te zetten, maar als iemand meerdere evenementen organiseert dan gaat dit mis. 
	
}
