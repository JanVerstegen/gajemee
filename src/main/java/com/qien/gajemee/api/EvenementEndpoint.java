package com.qien.gajemee.api;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.qien.gajemee.config.LoggedIn;
import com.qien.gajemee.domain.Evenement;
import com.qien.gajemee.domain.Persoon;
import com.qien.gajemee.service.EvenementService;
import com.qien.gajemee.service.PersoonService;

import javassist.NotFoundException;


@RestController
@CrossOrigin
public class EvenementEndpoint {

		
		@Autowired
		private EvenementService evenementService;

		@Autowired
		private HttpServletRequest request;

		@Autowired
		private JavaMailSender verzender;

		@Autowired 
		private PersoonService persoonService;

		
	    @LoggedIn
		@RequestMapping(method = RequestMethod.PUT, value = "/evenementen/{id}")
		public ResponseEntity<?> updateEvenement(@RequestBody Evenement evenement, @PathVariable long id) {
			Evenement updatedEvenement = evenementService.updateEvenement(evenement);
			return ResponseEntity.ok(updatedEvenement);
		}
		
	    @LoggedIn
		@RequestMapping(method = RequestMethod.POST, value = "personen/{id}/evenementen")
		public ResponseEntity<?> addEvenement(@RequestBody Evenement evenement, @PathVariable long id) {
			Evenement newEvenement = evenementService.addEvenement(evenement, id);
			URI responseURI = URI.create(request.getServerName() + ":" + request.getServerPort()+request.getRequestURI() + "/" + newEvenement.getId());
			return ResponseEntity.created(responseURI).build(); 
		}


		
		//throws moet try/catch blok worden!
		//@LoggedIn
		@ResponseBody
	    @LoggedIn
		@RequestMapping(method = RequestMethod.PUT, value = "evenementen/{eventId}/voegtoe/{persoonId}")
		public ResponseEntity<String> addAanwezigen(@PathVariable long eventId, @PathVariable long persoonId) throws com.qien.gajemee.exceptions.NotFoundException, NotFoundException {
			Persoon persoon = persoonService.findById(persoonId);
			Evenement evenement = evenementService.findEvementById(eventId);
			
			final Optional<String> authHeader = Optional.ofNullable(request.getHeader("Authorization"));
			String username = authHeader.get();
			int positie = username.indexOf(":");
			String email = username.substring(0, positie);
			Set <Persoon> aanwezigen = evenement.getAanwezigen();
			
			if(persoon.getEmail().equals(email) && (!(aanwezigen.contains(persoon)))){
					try {
						evenementService.addAanwezigen(eventId, persoonId);
						sendEmail (persoon, evenement);
						return ResponseEntity.ok("Leuk dat je mee gaat, " +persoon.getVoornaam()+ "! Er is een mail naar je verzonden met meer informatie.");

					} catch (Exception ex) {
					
					return ResponseEntity. badRequest().build(); 
					}
			}else {
				return ResponseEntity.badRequest().build();
			}
		}

		private void sendEmail(Persoon persoon, Evenement evenement) throws Exception{
			String evenementNaam = evenement.getEvenementNaam();
			Persoon organisator = evenement.getOrganisator();
			String mailOntvanger = persoon.getEmail();  //of "heikevandervliet@hotmail.com";
			String naamOntvanger = persoon.getVoornaam();
			MimeMessage message = verzender.createMimeMessage();
		    MimeMessageHelper helper = new MimeMessageHelper(message);
		    helper.setTo(mailOntvanger);  //aangemelde persoon getEmail;
		    helper.setText("Hallo " +naamOntvanger+ ", \n\nLeuk dat je je hebt aangemeld voor " +evenement.getEvenementNaam()+ ". \nOm tot concrete afspraken te komen, kun je contact opnemen"
		    		+ " met "+organisator.getVoornaam()+ ", de organisator van het evenement via " +organisator.getEmail()+ ". \nVeel plezier! \n\nHartelijke groet,\nhet 'Ga je mee?'-team");
		    helper.setSubject("Aangemeld voor: "+evenementNaam);
		        
		    verzender.send(message);
		    }
	
	    @LoggedIn
		@RequestMapping(method = RequestMethod.PUT, value = "evenementen/{eventId}/verwijder/{persoonId}")
		public ResponseEntity<?> delAanwezigen(@PathVariable long eventId, @PathVariable long persoonId) {
			evenementService.delAanwezigen(eventId, persoonId);
			return ResponseEntity.noContent().build();
		}
		
		//werkt!
		@RequestMapping(method = RequestMethod.GET, value = "/evenementen")
		public ResponseEntity<Iterable<Evenement>> getEvenementen() {
			return ResponseEntity.ok(evenementService.findAll());
		}
		
		//werkt!
		@RequestMapping(method = RequestMethod.GET, value = "/evenementen/{evenementId}")
		public ResponseEntity<Evenement> getById(@PathVariable long evenementId) {
			try {
			return ResponseEntity.ok(evenementService.findEvementById(evenementId));
			} catch (NotFoundException e) {
				return ResponseEntity.notFound().build();
			}
		}
		
	    @LoggedIn
		@RequestMapping(method = RequestMethod.DELETE, value = "/evenementen/{evenementId}")
		public ResponseEntity<?> deleteEvenement(@PathVariable long evenementId) {
			evenementService.deleteEvenement(evenementId);
			return ResponseEntity.noContent().build();
		}
		
	
		//werkt!
	    @LoggedIn
		@RequestMapping(method = RequestMethod.GET, value = "/evenementen/naam/{evenementNaam}")
		public ResponseEntity<List<Evenement>> getEvenementByNaam(@PathVariable String evenementNaam) {
			try {
			return ResponseEntity.ok(evenementService.findEvenementByEvenementNaam(evenementNaam));
			} catch (NotFoundException e) {
				return ResponseEntity.notFound().build();
			}
		}
		
		//werkt!
	    @LoggedIn
		@RequestMapping(method = RequestMethod.GET, value = "/evenementen/woord")
		public ResponseEntity<List<Evenement>> getEvenementByDeelNaam(@RequestBody String woord){
			return ResponseEntity.ok(evenementService.findEvenementByWoordInNaam(woord));
		}
		
		//werkt!
	    @LoggedIn
		@RequestMapping(method = RequestMethod.GET, value = "/evenementen/plaatsstring")
		public List<Evenement> getEvenementByPlaats(@RequestBody String plaats){
			return evenementService.findEvenementByPlaats(plaats);
		}
		
		//werkt!
	    @LoggedIn
		@RequestMapping(method = RequestMethod.GET, value = "/evenementen/categorie/naam")
		public ResponseEntity<List<Evenement>> getEvenementByCategorie(@RequestBody String categorienaam){
			return ResponseEntity.ok(evenementService.findEvenementByCategorie(categorienaam));
		}
		
		//werkt!
	    @LoggedIn
		@RequestMapping(method = RequestMethod.GET, value = "/evenementen/categorie/id")
		public ResponseEntity<List<Evenement>> getEvenementByCategorieId(@RequestBody long categorieId){
			return ResponseEntity.ok(evenementService.findEvenementByCategorieId(categorieId));
		}
	    
	    @LoggedIn
		@RequestMapping(method = RequestMethod.GET, value = "/evenementen/organisator/id/{organisatorId}")
		public ResponseEntity<List<Evenement>> getEvenementenByOrganisatorId(@PathVariable long organisatorId){
			return ResponseEntity.ok(evenementService.findEvenementenByOrganisatorId(organisatorId));
		}
		
	    @LoggedIn
		@RequestMapping(method = RequestMethod.GET, value = "/evenementen/organisator/naam/{organisatorNaam}")
		public ResponseEntity<List<Evenement>> getEvenementenByOrganisatorNaam(@PathVariable String organisatorNaam){
			return ResponseEntity.ok(evenementService.findEvenementByOrganisatorNaam(organisatorNaam));
		}
		
	    @LoggedIn
		@RequestMapping(method = RequestMethod.GET, value = "/evenementen/objectEvenementPlaatsNaam")
		public ResponseEntity<List <Evenement>> getEvenementenEnPlaatsByEvenementPlaatsNaam(@RequestBody String evenementPlaats){
			List<Evenement> findAllByEvenementPlaats = evenementService.findEvenementenEnPlaatsByEvenementPlaatsNaam(evenementPlaats);
			return ResponseEntity.ok(findAllByEvenementPlaats);
		}
		
	    @LoggedIn
		@RequestMapping(method = RequestMethod.GET, value = "/evenementen/persoonPlaats")
		public ResponseEntity<List<Evenement>> getEvenementenEnPlaatsByPersoonPlaatsNaam(@RequestBody String persoonPlaats){
			List<Evenement> findAllByPersoonPlaats = evenementService.findEvenementEnPlaatsByPersoonPlaatsNaam(persoonPlaats);
			return ResponseEntity.ok(findAllByPersoonPlaats);
		}
	    
	    @LoggedIn
		@RequestMapping(method = RequestMethod.GET, value = "/evenementen/persoonId/{persoonId}")
		public ResponseEntity<List <Evenement>> getEvenementenEnPlaatsByPersoonId(@PathVariable long persoonId){
			List<Evenement> findAllByPersoonId = evenementService.findEvenementEnPlaatsByPersoonId(persoonId);
			return ResponseEntity.ok(findAllByPersoonId);
		}
		

}
