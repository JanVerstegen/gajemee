package com.qien.gajemee.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.qien.gajemee.domain.Plaats;


@Component
public interface IPlaatsRepository extends CrudRepository <Plaats, Long> {
		
		//testen
		@Query(value = "SELECT * FROM plaats WHERE id = ?1", nativeQuery = true)
		Plaats findById (long plaatsId);
		
		//aanpassen
		//@Query(value = "SELECT * FROM persoon JOIN plaats ON persoon_voorkeuren.persoon_id=persoon.id WHERE voorkeuren_id = ?1 AND is_active=true", nativeQuery = true)
		//List <Persoon> findPersonenByPlaats(long categorieId);  
		
	
}
