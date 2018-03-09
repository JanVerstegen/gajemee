package com.qien.gajemee.api;

 import java.net.URI;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qien.gajemee.config.LoggedIn;
import com.qien.gajemee.domain.Persoon;
import com.qien.gajemee.exceptions.EmailAllreadyInUseException;
import com.qien.gajemee.exceptions.FalseLoginException;
import com.qien.gajemee.exceptions.IncorrectEmailFormatException;
import com.qien.gajemee.exceptions.IncorrectPasswordFormatException;
import com.qien.gajemee.exceptions.NotFoundException;
import com.qien.gajemee.service.PersoonService;




@RestController
@CrossOrigin
public class PersoonEndpoint extends AbstractController {
	
	@Autowired
	private PersoonService persoonService;

	@Autowired
	private HttpServletRequest request;
	
	//werkt!
//	@LoggedIn
	@RequestMapping(method=RequestMethod.GET, value="/personen")
	public ResponseEntity<List<Persoon>> getPersonen() {
		return ResponseEntity.ok(persoonService.getPersonen());
	}
	
	//werkt
    @RequestMapping(value = "/signup", 
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@Valid @RequestBody final Persoon persoon, final Errors errors) {
    		try {
		final Persoon newUser = persoonService.addPersoon(persoon);
		URI responseURI = URI.create(request.getServerName() + ":" + request.getServerPort()+request.getRequestURI() + "/" + newUser.getId());
		return ResponseEntity.created(responseURI).build();
  		} catch (EmailAllreadyInUseException e) {
    			return  ResponseEntity.badRequest().body(Collections.singletonMap("Signing up failed", "Email already in use"));
    		} catch(ValidationException e) {
    			return  ResponseEntity.badRequest().body(Collections.singleton("email not must be at least 10 characters"));
    		} catch(IncorrectPasswordFormatException e) {
    			return ResponseEntity.badRequest().body(Collections.singleton("password at least 8 digits long, have an uppercase, lowercase, number, and a special digit"));
    		} catch (IncorrectEmailFormatException e) {
    			return ResponseEntity.badRequest().body(Collections.singleton("Incorrect email format"));
    		}
    }
	
    @LoggedIn
    @GetMapping("/authenticatedUser")
    public ResponseEntity<?> test() {
    	return ResponseEntity.noContent().build();
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ResponseEntity<?> login(@RequestBody final Persoon persoon) {
		try {
			persoonService.login(persoon);
			Persoon loggedInPersoon = persoonService.findByEmail(persoon.getEmail());
			return ResponseEntity.ok(loggedInPersoon);
		} catch (FalseLoginException e) {
			return ResponseEntity.badRequest().body("false login");
		} 
	}
	
	//werkt
	@RequestMapping(method=RequestMethod.PUT, value="/personen/{persoonId}") // wellicht deze nog aanpassen naar 200 OK. Een updated persoon wordt als een nieuw persoon opgeslagen? nalopen.
	public ResponseEntity<Persoon> updatePersoon(@RequestBody Persoon persoon, @PathVariable long persoonId) {
		Persoon updatedPersoon = persoonService.updatePersoon(persoonId, persoon);
		return ResponseEntity.ok(updatedPersoon);
	}
	
	//werkt!
	@RequestMapping(method=RequestMethod.PUT, value="/personen/{persoonId}/updateVoorkeuren")
	public ResponseEntity<Persoon> updateVoorkeuren(@PathVariable long persoonId, @RequestBody boolean[] aangevinkteVoorkeuren) {
		Persoon updatedPersoon = persoonService.updateVoorkeuren(persoonId, aangevinkteVoorkeuren);
		return ResponseEntity.ok(updatedPersoon); 
	}
	

	//werkt
	@RequestMapping(method=RequestMethod.DELETE, value="/personen/{id}")
	public ResponseEntity<?> deletePersoon(@PathVariable long id) {
		persoonService.deletePersoon(id);
		return ResponseEntity.noContent().build(); //no content
	}
	
	//werkt!
	@RequestMapping(method=RequestMethod.GET, value="/personen/{persoonId}")
	public ResponseEntity<?> findPersoonById(@PathVariable long persoonId) {
		try {
		Persoon persoonById =  persoonService.findById(persoonId);
		return ResponseEntity.ok(persoonById);
		} catch(NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	//werkt!
	@RequestMapping(method=RequestMethod.GET, value="/personen/voorkeuren/id/{voorkeurId}")
	public ResponseEntity<List <Persoon>> findAllByVoorkeuren(@PathVariable long voorkeurId) {
		List<Persoon> allByVoorkeuren = persoonService.findAllByVoorkeuren(voorkeurId);
		return ResponseEntity.ok(allByVoorkeuren);
	}
	
	//werkt!
	@RequestMapping(method = RequestMethod.GET, value = "/personen/voorkeuren/categorie/{categorie}")
	public ResponseEntity<List<Persoon>> findAllByVoorkeur(@PathVariable String categorie){
		List<Persoon> findAllByVoorkeur = persoonService.findAllByVoorkeur(categorie);
		return ResponseEntity.ok(findAllByVoorkeur);
	}
	
	//werkt!
	@RequestMapping(method = RequestMethod.GET, value = "/evenementen/{evenementId}/organisator")
	public ResponseEntity<Persoon> findOrganisatorByEvenementId (@PathVariable long evenementId){
		Persoon findOrganisatorByEvent = persoonService.findOrganisatorByEvenementId(evenementId);
		return ResponseEntity.ok(findOrganisatorByEvent);
	}
	
	//werkt!
	@RequestMapping(method = RequestMethod.GET, value = "/evenementen/{evenementId}/aanwezig")
	public ResponseEntity<List<Persoon>> findPersonenbyAanwezig (@PathVariable long evenementId){
		List<Persoon> findAllByAanwezig = persoonService.findAllByAanwezig(evenementId);
		return ResponseEntity.ok(findAllByAanwezig);
	}
	
	
}
