package com.qien.gajemee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.qien.gajemee.domain.Evenement;
import com.qien.gajemee.domain.Persoon;
import com.qien.gajemee.exceptions.EmailAllreadyInUseException;
import com.qien.gajemee.exceptions.FalseLoginException;
import com.qien.gajemee.exceptions.IncorrectEmailFormatException;
import com.qien.gajemee.exceptions.IncorrectPasswordFormatException;
import com.qien.gajemee.exceptions.NotFoundException;
import com.qien.gajemee.util.EmailValidator;
import com.qien.gajemee.util.PasswordValidator;

@Service
public class PersoonService {

	@Autowired
	private IPersoonRepository persoonRepository;

	@Autowired
	private IEvenementRepository evenementRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private PasswordValidator passwordValidator;
	
	@Autowired
	private EmailValidator emailValidator;
	
	public Persoon findByEmail(final String email) {
		return persoonRepository.findByEmail(email);
	}
	
	
	public Persoon addPersoon(Persoon persoon) throws EmailAllreadyInUseException, IncorrectPasswordFormatException, IncorrectEmailFormatException {
		if (persoonRepository.findByEmail(persoon.getEmail()) != null)
			throw new EmailAllreadyInUseException("email already in use");
		if(!passwordValidator.isValid(persoon.getWachtwoord())) 
			throw new IncorrectPasswordFormatException("incorrect password");
		if (!emailValidator.isValidEmailAddress(persoon.getEmail()))
			throw new IncorrectEmailFormatException("incorrect email format");
		persoonRepository.save(persoon);
		long x = persoon.getId();
		long voorkeur;
		int omvang = persoonRepository.countCategorieen();
		for(voorkeur = 1; voorkeur <=omvang; voorkeur++) {
			persoonRepository.vulVoorkeuren(x, voorkeur);
		}
		return persoonRepository.findById(x);
	}
	
	
	public Persoon updateVoorkeuren(long persoonId, boolean[] aangevinkteVoorkeuren) { 
		//boolean [] b = {true, false, false, false, false, false, false, false, false, false, false, false, false, true};
		long voorkeur;
		int y = 0;
		persoonRepository.leegVoorkeuren(persoonId);
		long x = persoonId;
		int omvang = persoonRepository.countCategorieen();
		if(aangevinkteVoorkeuren.length == omvang) {
			OUTER: for (voorkeur = 1; voorkeur<=omvang; voorkeur++) {
				INNER: while (y<aangevinkteVoorkeuren.length) {
					if (aangevinkteVoorkeuren[y]) {
						persoonRepository.vulVoorkeuren(x, voorkeur);
						y++;
					} else {
						y++;
						continue OUTER; 
					}
				}
				}
			}
		return persoonRepository.findById(persoonId);
	}

	public boolean login(Persoon persoon) throws FalseLoginException {
		final Optional<Persoon> dbUser = Optional.ofNullable(persoonRepository.findByEmail(persoon.getEmail()));
		if (dbUser.isPresent()) {
			return passwordEncoder.matches(persoon.getWachtwoord(), dbUser.get().getWachtwoord());
		}
		throw new FalseLoginException("user not found");
	}	

	public List<Persoon> getPersonen() {
		List<Persoon> personen = new ArrayList<>();
		persoonRepository.findAll().forEach(personen::add);
		return personen;
	}

	public Persoon updatePersoon(long id, Persoon persoon) {
		persoon.setId(id);
		return persoonRepository.save(persoon);
	}

	public void deletePersoon(long id) {
		
		Persoon persoon = persoonRepository.findById(id);
		if(persoon == null) { 
			throw new RuntimeException("geen persoon met dit id");
		}
		persoon.setActive(false);
		persoonRepository.save(persoon);
		deleteEvenementen(id);
	}
	
	public void deleteEvenementen(long persoonId) {
		List<Evenement> evenementen = evenementRepository.findByOrganisatorId(persoonId); //wat als hij/zij geen organisator is van een evenement?
		if(!evenementen.isEmpty()) { 
			for (Evenement e: evenementen) {
				e.setActive(false);
				evenementRepository.save(e);
			}
		}
	}

	public Persoon findById(long id) throws NotFoundException {
		if (persoonRepository.findById(id) == null)
			throw new NotFoundException("user not found");
		return persoonRepository.findById(id);
	}

	/*public int getAge(long persoonId) {
		Date birthDate = persoonRepository.findById(persoonId).getVerjaardag();
		long ms = clock.getLongTime() - birthDate.getTime();
		long age = (long) ((long) ms / 1000.0 * 60 * 60 * 24 * 365);
		int firstDigit = (int) Long.parseLong(Long.toString(age).substring(0, 2));
		return firstDigit;
		//zonder meetellen schrikkeljaren
	}
	*/

	public List<Persoon> findAllByVoorkeuren(long id) {
		return persoonRepository.findPersonenByCategorieId(id);
	}

	public List<Persoon> findAllByVoorkeur(String categorienaam) {
		return persoonRepository.findPersonenByVoorkeurenCategorienaam(categorienaam);
	}

	public Persoon findOrganisatorByEvenementId(long id) {
		return persoonRepository.findOrganisatorByEvenementId(id);
	}

	public List<Persoon> findAllByAanwezig(long evenementId) {
		return persoonRepository.findPersonenByEvenementAanwezigen(evenementId);
	}

}
