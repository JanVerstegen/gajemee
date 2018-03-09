package com.qien.gajemee.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.qien.gajemee.domain.Persoon;
import com.qien.gajemee.exceptions.EmailAllreadyInUseException;
import com.qien.gajemee.exceptions.FalseLoginException;
import com.qien.gajemee.exceptions.IncorrectEmailFormatException;
import com.qien.gajemee.exceptions.IncorrectPasswordFormatException;
import com.qien.gajemee.exceptions.NotFoundException;
import com.qien.gajemee.util.Clock;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersoonServiceTests {

	@MockBean
	private IPersoonRepository persoonRepository;
	
	@MockBean
    private PasswordEncoder passwordEncoder;
	
	@MockBean
	private Clock clock;
	
	
	@Autowired
	private PersoonService persoonService;
	
	private final static String WACHTWOORD = "wachtwoord";
	private final static String EMAIL = "email@email.nl";
	private final static String HASHED_WACHTWOORD = "hashedWachtwoord";
	private final static long ID = 1;
	private static Date VERJAARDAG;
	private Persoon persoon;
	private Persoon hashedPersoon;
	private final static long LEEFTIJD = 32;
	
	//.add(new Categorie(), new Categorie(), new Categorie(), new Categorie(), new Categorie(), new Categorie(), new Categorie(), new Categorie(), new Categorie(), new Categorie(), new Categorie(), new Categorie(), new Categorie(), new Categorie());
	private final static boolean[] ARRAY = {true, false};
	private final static boolean[] TE_LANGE_ARRAY = {true, false, false, true};
	
	@Before
	public void setup() throws Exception {
		persoon = new Persoon().setWachtwoord(WACHTWOORD).setEmail(EMAIL).setId(ID).setVerjaardag(VERJAARDAG);;
		hashedPersoon = new Persoon().setWachtwoord(HASHED_WACHTWOORD).setEmail(EMAIL).setId(ID);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		VERJAARDAG = formatter.parse("1985-11-11 00:00:00");
		when(persoonRepository.save(persoon)).thenReturn(hashedPersoon);
		when(passwordEncoder.encode(persoon.getWachtwoord())).thenReturn(HASHED_WACHTWOORD);
		when(persoonRepository.findByEmail(EMAIL)).thenReturn(persoon);
		when(passwordEncoder.matches(WACHTWOORD, persoon.getWachtwoord())).thenReturn(true);
		when(persoonRepository.findById(ID)).thenReturn(hashedPersoon);
		when(persoonRepository.countCategorieen()).thenReturn(2);
		//when(persoonRepository.findById(ID).getVerjaardag()).thenReturn(VERJAARDAG);
		 
		when(clock.getLongTime()).thenReturn(1L);
		
		
	}
	
	 //Testen:
		//updateVoorkeuren methode: wat als de array korter is?
		//deletePersoon: wat als de persoon geen organisator is?
		//gedrag testen bij verschillende invoer
		//Wat als je een waarde ingeeft die niet bestaat?
	
	
	
	@Test
	public void whenAddPersoonInvoked_thenVulVoorkeurenIsInvoked() throws EmailAllreadyInUseException, IncorrectPasswordFormatException, IncorrectEmailFormatException{
		persoon.setEmail("doesnotexist@fake.com");
		persoonService.addPersoon(persoon);
		//assertTrue(UPDATE_VOORKEUREN.equals(persoonService.updateVoorkeuren(ID, ARRAY)));
		verify(persoonRepository, times(2)).vulVoorkeuren(anyLong(), anyLong());
		}

	
	@Test
	public void whenUpdateVoorkeurenInvokedCorrectly_thenVoorkeurenIsReturned() {
		persoonService.updateVoorkeuren(ID, ARRAY);
		verify(persoonRepository, times(1)).leegVoorkeuren(ID);
		verify(persoonRepository, times(1)).countCategorieen();
		verify(persoonRepository, times(1)).vulVoorkeuren(anyLong(), anyLong());
		//LEGE_VOORKEUREN.add(categorie1);
		//assertTrue(LEGE_VOORKEUREN.equals(nieuwePersoon.getVoorkeuren()));
		//assertTrue(VOORKEUREN.contains(categorie1));
	}
	

	@Test(expected = EmailAllreadyInUseException.class)
	public void whenAddPersoonAndEmailAlreadyExists_thenExceptionIsThrown() throws Throwable {  //wachtwoord ipv hashed wachtwoord
		try {
			Persoon nieuwePersoon = persoonService.addPersoon(persoon);
		} catch (Exception e) {
			throw e;
		}
	}

	@Test
	public void whenAddPersoonInvokedCorrectly_thenPersoonWillBeSaved() throws EmailAllreadyInUseException, IncorrectPasswordFormatException, IncorrectEmailFormatException  {  //wachtwoord ipv hashed wachtwoord
		persoon.setEmail("fake@mail.nl");
		Persoon nieuwePersoon = persoonService.addPersoon(persoon); 
		assertEquals(EMAIL, nieuwePersoon.getEmail());
		assertEquals(HASHED_WACHTWOORD, nieuwePersoon.getWachtwoord());
	}
	
	@Test
	public void whenPersoonLookupHappensWithCorrectData_thenAPersoonWillBeReturned() {
		Persoon nieuwePersoon = persoonService.findByEmail(EMAIL);
		assertEquals(EMAIL, nieuwePersoon.getEmail());
		assertNotNull(nieuwePersoon.getWachtwoord());
	}
	
	@Test
	public void whenPersoonLookupHappensWithIncorrectData_thenNoPersoonWillBeReturned() {
		Persoon nieuwePersoon = persoonService.findByEmail("onbekend");
		assertNull(nieuwePersoon);
	}
	
	@Test(expected = FalseLoginException.class)
	public void whenLoginButPersoonCannotBeFound_thenExceptionWillBeThrown() throws FalseLoginException {
		try{
			persoonService.login(persoon.setEmail("onbekend"));
		} catch (Exception e) {
		throw e;
		}
	}
	
	@Test
	public void whenLoginButPasswordIsIncorrect_thenFalseWillBeReturned() throws FalseLoginException {
		boolean loggedIn = persoonService.login(persoon.setWachtwoord("verkeerde invoer"));
		assertFalse(loggedIn);
	}
	
	@Test
	public void whenLoginWithCorrectData_thenTrueWillBeReturned() throws FalseLoginException{ 
		boolean loggedIn = persoonService.login(persoon);
		assertTrue(loggedIn);
	}

	@Test
	public void whenFindByIdWithCorrectData_thenPersoonWillBeReturned() throws NotFoundException { //fails: 0 ipv 1?
		Persoon nieuwePersoon = persoonService.findById(ID);
		assertEquals(1, nieuwePersoon.getId());
	}
	
	


}
