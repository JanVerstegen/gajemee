package com.qien.gajemee.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qien.gajemee.domain.Categorie;
import com.qien.gajemee.domain.Evenement;
import com.qien.gajemee.domain.Persoon;


@Component
public interface IPersoonRepository extends CrudRepository <Persoon, Long>{
	
	//werkt!
	@Query(value = "SELECT * FROM persoon WHERE id = ?1 AND is_active=true", nativeQuery = true)
	Persoon findById (long persoonId);
	
	//werkt!
	@Query(value = "SELECT * FROM persoon JOIN persoon_voorkeuren ON persoon_voorkeuren.persoon_id=persoon.id WHERE voorkeuren_id = ?1 AND is_active=true", nativeQuery = true)
	List <Persoon> findPersonenByCategorieId(long categorieId);  
	
	//werkt!
	@Query(value= "SELECT DISTINCT * FROM persoon WHERE persoon.id IN (SELECT DISTINCT persoon_voorkeuren.persoon_id FROM persoon_voorkeuren JOIN categorie ON persoon_voorkeuren.voorkeuren_id=categorie.id WHERE categorie.categorienaam=?1) AND is_active=true", nativeQuery=true)
	List <Persoon> findPersonenByVoorkeurenCategorienaam (String categorienaam);
	
	//werkt!
	@Query(value= "SELECT * FROM persoon JOIN evenement ON persoon.id=evenement.organisator_id WHERE evenement.id = ?1 AND persoon.is_active=true", nativeQuery=true)
	Persoon findOrganisatorByEvenementId (long evenementId);
	
	@Query(value= "SELECT * FROM persoon JOIN evenement_aanwezigen ON persoon.id=evenement_aanwezigen.aanwezigen_id WHERE evenement_aanwezigen.evenement_id= ?1 AND is_active=true", nativeQuery=true)
	List <Persoon> findPersonenByEvenementAanwezigen (long evenementId);
	
	@Query(value= "SELECT * FROM persoon WHERE email = ?1 AND is_active=true", nativeQuery=true)
	Persoon findByEmail(String email);

	@Query(value= "SELECT COUNT(id) FROM categorie;", nativeQuery=true)
		int countCategorieen();
	
	//werkt!
	@Transactional
	@Modifying
	@Query(value="DELETE FROM persoon_voorkeuren WHERE persoon_id = ?1", nativeQuery=true)
	void leegVoorkeuren(@Param("persoon_id") long persoon_id);
	
	//werkt!
	@Transactional
	@Modifying
	@Query(value= "INSERT INTO `persoon_voorkeuren` (`persoon_id`, `voorkeuren_id`) VALUES (:persoon_id, :voorkeuren_id)", nativeQuery=true)
	void vulVoorkeuren(@Param("persoon_id") long persoon_id, @Param("voorkeuren_id") long voorkeuren_id);

	
}
