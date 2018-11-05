/*
 * Test functions to create and delete categories.
 * Database should be empty at the beginning of the test and cleared after the test.
 * 
 * @author Fabian Dietenberger
 * @date 09.10.2014
 */
package com.project.java.presentation.server;
import com.project.groovy.dummy.Dummylogo;
import com.project.groovy.dummy.DummyImageMarius;
import com.project.groovy.dummy.DummyImageMariusWillkommen;
import com.project.presentation.server.tx.dom.IHibernateService;
import com.project.presentation.server.upload.bean.DomainBean;
import com.project.presentation.shared.dto.*;
import com.project.presentation.shared.enumerations.ENotify;
import com.project.presentation.shared.enumerations.EUser;
import com.project.presentation.shared.view.UserView;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;
import util.PersistenceLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ServiceCategoryTest.
 */
public class ServiceDatenbankTest {

	/** The service. */
	private final IHibernateService service = (IHibernateService) PersistenceLoader
			.getApplicationContext().getBean("HibernateService");

	/** The logger. */
	private Logger logger = Logger.getLogger(ServiceDatenbankTest.class);

	/** The category names. */
	private List<String> categoryNames;

	/**
	 * Inits the categories local.
	 */
	@BeforeTest
	public void init() {

	}


	//FIXME DKL Bitte User vorher ueberpruefen und mit der ID laden ! Sonst duplicate entry
	/**
	 * Generierung des Users falls nicht vorhanden
	 * Bitte Test nicht abschalten !!!
	 */
	@Test(groups = "ServiceTest", enabled = true)
	public void initialtest() {
		logger.info("Überprüfe ob User in der Datenbank? Nein füge neuen User hinzu, ja füge keinen neuen User hinzu");
		final UserDTO user = new UserDTO();

		// Setup FotoStudio
		user.setName("Foto Thanner");
		user.setEmail("info@fotothanner.de");
		user.setPassword("1234");

		final FotostudioDTO fotostudioDTO = service.loadStudio(user.getName());

		if (fotostudioDTO == null){
			insertFotoStudio();
			insertFotoStudio2();
		}

		user.setEmail("user@t-online.de");   //Testen mit Userprofil
		user.setPassword("1234");
		UserDTO userDTO = service.findUserForLogin(user.getEmail(), user.getPassword());

		//überprüfe ob user existiert, wenn nein dann füge neuen User hinzu
		if (userDTO == null) {
			insertUser();
			insertUser2();
		}
	}

	public void insertFotoStudio() {
		logger.info("legt ein neues Fotostudio an!");

		final FotostudioDTO studio = new FotostudioDTO();
		studio.setName("Foto Thanner");
		studio.setEmail("info@fotothanner.de");
		studio.setPassword("1234");
		studio.setTelephone_number("08331 2580");
		studio.setOeffnungszeiten("Mo-Fr 09:00 bis 18:00 Uhr, Sa 08:30 bis 13:00 Uhr");
		studio.setFax("08331 - 49 41 28");
		studio.setLogo(Dummylogo.getDummyImage());
		studio.setPage("http://www.fotothanner.de");

		final AdresseDTO adresse = new AdresseDTO();
		adresse.setStrasse("Marktplatz 11");
		adresse.setOrt("Memmingen");
		adresse.setLand("Deutschland");
		adresse.setPLZ("87700");

		studio.addAdresse(adresse);
		service.saveFotostudio(studio);
	}

	public void insertFotoStudio2() {
		logger.info("legt neues Fotostudio Bildwerk89 an!");

		final FotostudioDTO studio = new FotostudioDTO();
		studio.setName("bildwerk89 ulm");
		studio.setEmail("fotos@bildwerk89.de");
		studio.setPassword("1234");
		studio.setTelephone_number("0731 143 92 622");
		studio.setOeffnungszeiten("mo-sa 09:00 bis 18:00 uhr");
		studio.setFax("0731 - 143 92 621");
		studio.setLogo(Dummylogo.getDummyImage());
		studio.setPage("http://www.bildwerk89.de");

		final AdresseDTO adresse = new AdresseDTO();
		adresse.setStrasse("multscherstraße 5");
		adresse.setOrt("ulm");
		adresse.setLand("deutschland");
		adresse.setPLZ("89077");

		studio.addAdresse(adresse);
		service.saveFotostudio(studio);
	}

//	/** User kann nicht ohne Fotostudio existieren
	public void insertUser() {
		logger.info("legt ein neuer User an!");

		final FotostudioDTO studio = service.loadStudio("Foto Thanner");

		final UserDTO us = new UserDTO();
		us.setName("Max Mustermann");
		us.setEmail("user@t-online.de");
		us.setPassword("1234");
		us.setFotostudio(studio);


		final AdresseDTO adresseUS = new AdresseDTO();
		adresseUS.setStrasse("ottobeurerstr. 11");
		adresseUS.setPLZ("87000");
		adresseUS.setOrt("Ottobeuren");
		adresseUS.setLand("Deutschland");
		us.addAdresse(adresseUS);

		service.saveNewUser(us);

	}//*/

	public void insertUser2() {
		logger.info("legt ein zweiten User an!");

		final FotostudioDTO studio = service.loadStudio("Foto Thanner");

		final UserDTO us = new UserDTO();
		us.setName("Moritz Mustermann");
		us.setEmail("2.user@t-online.de");
		us.setPassword("1234");
		us.setFotostudio(studio);


		final AdresseDTO adresseUS = new AdresseDTO();
		adresseUS.setStrasse("Ottobeurerstr. 12");
		adresseUS.setPLZ("87000");
		adresseUS.setOrt("Ottobeuren");
		adresseUS.setLand("Deutschland");
		us.addAdresse(adresseUS);


		service.saveNewUser(us);

	}//*/
	/**
	 * Versuch eine zweite Adresse abzuspeichern
	 */
	@Test(groups = "ServiceTest", enabled = false)
	public void tryingToAddDuplicateAddressEntry() {
		logger.info("Sucht existierenden User!");

		/** Neue Adresse */
		final AdresseDTO adresseNew = new AdresseDTO();
		adresseNew.setStrasse("Nelkenweg.4");
		adresseNew.setPLZ("88410");
		adresseNew.setOrt("Hauerz");
		adresseNew.setLand("Deutschland");

		/** Login als User */
		final UserDTO loginUser = new UserDTO();
		loginUser.setEmail("user@t-online.de");
		loginUser.setPassword("1234");

		/** Suche den User in der Datenbank */
		/*
			FIXME DKL bitte ersetzten durch findUserForLogin, damit keine View an der Stelle kommt,
			Zu viel Info an der Stelle
		 */
		final UserDTO user = service.findUserForLogin(loginUser.getEmail(), loginUser.getPassword());

		assert user.getAdresse() != null :"Es wurde keine Adresse zum User gefunden !";
		adresseNew.setAdresseID(user.getAdresse().getAdresseID());
		user.addAdresse(adresseNew);
		service.saveNewUser(user);

		logger.info("Neue Adresse gespeichert!");
	}//*/

//immer true
	@Test(groups = "ServiceTest", enabled = false)
	public void userLogin() {
		logger.info("Finde den User");
		final LoginDTO loginDTO = new LoginDTO();

		loginDTO.setEmail("user@t-online.de");   //Testen mit Userprofil
		loginDTO.setPassword("11234"); // Test mit bewustem falschen Passweort

		/*
			FIXME DKL bitte ersetzten durch findUserForLogin, damit keine View an der Stelle kommt,
			Zu viel Info an der Stelle
		 */
		UserView fotostudio = service.loadUserView(loginDTO);
		assert fotostudio.getNotificationBean().getNotify() == ENotify.FAILURE : "Es darf kein User gefunden werden, da ich bewusst ein falsches passwort angegeben habe!";

        loginDTO.setPassword("1234"); // Passwort gibt es
        fotostudio = service.loadUserView(loginDTO);
        assert fotostudio.getNotificationBean().getNotify() == ENotify.SUCCESS : "Der User ist nicht in der Datenbank!";

        loginDTO.setEmail("info@fotothanner.de");  //Testen mit Fotostudio Profil
        loginDTO.setPassword("1234");
        fotostudio = service.loadUserView(loginDTO);
        assert fotostudio.getNotificationBean().getNotify() == ENotify.SUCCESS : "Warum wird der User nicht gefunden !";

		final FotostudioDTO fotostudioDTO = service.loadStudio("Foto Thanner");
		loginDTO.setUserID(fotostudioDTO.getFotostudioId());
		loginDTO.setEmail(fotostudioDTO.getEmail());
		loginDTO.setUserTyp(EUser.STUDIO);
		fotostudio = service.loadUserView(loginDTO);
		assert fotostudio.getNotificationBean().getNotify() == ENotify.SUCCESS : "Warum wurde das Fotostudio nicht gefunden ? Wurde dem Benutzer ein nicht vorhandenes Fotostudio hinzugefügt? Bitte Fotostudio überprüfen!";

		loginDTO.setPassword("1234687");
        fotostudio = service.loadUserView(loginDTO);
        assert fotostudio.getNotificationBean().getNotify() == ENotify.FAILURE : "Warum wird das Fotostudio gefunden? Das Passwort war falsch!";

		System.out.println(fotostudio.getNotificationBean().getNotify() + " " + fotostudio.getNotificationBean().getMessage());
	}

	/**
	 * Anlegen der Einstellung für einen User
	 */
	@Test(groups = "ServiceTest", enabled = true)
	public void addEinstellungForUser() {
		logger.info("ETab ");

		/** Simulation vom Frontend */
		final UserDTO userDTO = new UserDTO();
		userDTO.setEmail("user@t-online.de");
		userDTO.setPassword("1234");

		UserDTO user = service.findUserForLogin(userDTO.getEmail(), userDTO.getPassword());

		/** Falls kein User in der Datenbank ist füge einen ein! */
		if (user == null){
			insertUser();
			insertUser2();

			user = service.findUserForLogin(user.getEmail(), user.getPassword());
			assert user == null : "User konnte so nicht in der Datenbank gefunden werden";
		}

		final EinstellungDTO einstellungDTO = new EinstellungDTO();
		einstellungDTO.setUser(user);

		final TemplateDTO templateDTO = new TemplateDTO();
		templateDTO.setName("Designs");
		einstellungDTO.setTemplate(templateDTO);

		final List<TabItemDTO> tabItemDTO1 = new ArrayList<TabItemDTO>();
		final TabItemDTO qualifikation = new TabItemDTO(0, "Qualifikation");
		qualifikation.setPosition(1);
		qualifikation.setTitle("Das Wichtigste im Fokus");
		qualifikation.setContent("Abgeschlossenes Studium zum Dipl. Ing Elektrotechnik FH");
		qualifikation.setAnzeigen(true);
		tabItemDTO1.add(qualifikation);

		final TabItemDTO willkommen = new TabItemDTO(0, "Willkommen");
		willkommen.setPosition(2);
		willkommen.setTitle("Die goldene Nuss");
		willkommen.setContent("10 Jahre Berufserfahrung als Elektrotechnikingenieur Großmaschinen und Anlagenbau");
		willkommen.setAnzeigen(true);
		tabItemDTO1.add(willkommen);

		final TabItemDTO zertifikate = new TabItemDTO(0, "Zertifikate");
		zertifikate.setPosition(3);
		zertifikate.setTitle("Einer kam selten alleine");
		zertifikate.setContent("Erfahrener Hardwarekonstrukteur in COMOS PT, EPlan, AutoCAD");
		zertifikate.setAnzeigen(true);
		tabItemDTO1.add(zertifikate);

		final TabItemDTO freizeit = new TabItemDTO(0, "Freizeit");
		freizeit.setTitle("Bauer sucht frau");
		freizeit.setContent("Matlab und Labview");
		freizeit.setAnzeigen(true);
		tabItemDTO1.add(freizeit);

		final TabItemDTO vision = new TabItemDTO(0, "Vision");
		vision.setTitle("Einer Fuer alle");
		vision.setContent("Intensive Datenbankkentnisse in Access, MySQL und detaillierte Prozesserfahrung in SAP");
		vision.setAnzeigen(false);
		tabItemDTO1.add(vision);

		/**
		 * ID's duerfen nur gesetzt werden , wenn Sie in der Datenbank sind.
		 * Die Eintraege werden dann gemerged.
		 */

		//tabItemDTO1.add(new TabItemDTO(1, "Willkommen"));
		//tabItemDTO1.add(new TabItemDTO(2, "Qualifikation"));
		einstellungDTO.setTabItem(tabItemDTO1);

		final UserView userView = service.addEinstellung(einstellungDTO);

		System.out.println(userView.getNotificationBean().getNotify() + " " + userView.getNotificationBean().getMessage());
	}
	@Test(groups = "ServiceTest", enabled = true, dependsOnMethods ={"initialtest"})
	public void imageupload() {
		logger.info("lade die Bilder hoch");

		final ImageDTO image1 = new ImageDTO();
		image1.setImage(DummyImageMariusWillkommen.getDummyImage());
		image1.setName("Marius Willkommen");

		final ImageDTO image2 = new ImageDTO();
		image2.setImage(DummyImageMarius.getDummyImage());
		image2.setName("Qualifikation");

		final ImageDTO image3 = new ImageDTO();
		image3.setImage(DummyImageMarius.getDummyImage());
				image3.setName("Marius");

		final UserDTO dbUser = service.loginUser("user@t-online.de", "1234");
		final LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUserID(dbUser.getUserId());
		loginDTO.setEmail(dbUser.getEmail());
		loginDTO.setUserTyp(EUser.USER);
		final UserView userView = service.loadUserView(loginDTO);

		image1.setEinstellung(userView.getEinstellung());
		image2.setEinstellung(userView.getEinstellung());
		image3.setEinstellung(userView.getEinstellung());

		service.saveImage(image1, image1.getEinstellung().getEinstellungId());
		service.saveImage(image2, image2.getEinstellung().getEinstellungId());
		service.saveImage(image3, image3.getEinstellung().getEinstellungId());
	}

	@Test(groups = "ServiceTest", enabled = false)
	public void deleteUploadedImages(){

		final UserDTO dbUser = service.loginUser("user@t-online.de", "1234");
		final LoginDTO login = new LoginDTO();
		login.setUserID(dbUser.getUserId());
		login.setEmail(dbUser.getEmail());
		login.setUserTyp(EUser.USER);
		final UserView view = service.loadUserView(login);

		for (ImageDTO image : view.getImages()) {
			service.deleteImage(image.getImagesID());
		}
	}

	@Test(groups = "ServiceTest", enabled = true)
	public void loadView() {
			logger.info("ETab ");

			final LoginDTO loginDTO = new LoginDTO();
			loginDTO.setEmail("user@t-online.de");
			loginDTO.setPassword("1234");
		final LoginDTO userDTO1 = service.userOrStudio(loginDTO);
		assert userDTO1 != null: "User befindet sich nicht in der Datenbank!";
		final UserView view =  service.loadUserView(userDTO1);
			logger.info("complete!");
	}

	@Test(groups = "ServiceTest", enabled = true, dependsOnMethods = "imageupload")
	public void loadSettingAndAddImages() {
		logger.info("Ladet Einstellung und fügt Bilder hinzu ");

		final UserDTO userDTO = service.findUserForLogin("user@t-online.de", "1234");
		final LoginDTO login = new LoginDTO();
		login.setUserID(userDTO.getUserId());
		login.setEmail(userDTO.getEmail());
		login.setUserTyp(EUser.USER);
		final UserView userView = service.loadUserView(login);

		final EinstellungDTO einstellung = userView.getEinstellung();

		final List<ImageDTO> images = userView.getImages();
		for (TabItemDTO tabItem : einstellung.getTabItem()) {
			final ImageDTO imageDTO = images.get((int) tabItem.getTabItemID() % images.size());
			tabItem.setImage(imageDTO);
			imageDTO.setEinstellung(userView.getEinstellung());
		}

		einstellung.setUser(userView.getUser());
		service.addEinstellung(einstellung);

		logger.info("complete!");
	}

	@Test(groups = "ServiceTest", enabled = true)
	public void loadAllUserFromStudio(){
		logger.info("Ladet alle Benutzer zum zugehörigen Studio");

		final FotostudioDTO fotostudioDTO = service.loadStudio("Foto Thanner");
		assert fotostudioDTO != null : "Es ist kein Fotostudio vorhanden";

		final DomainBean domain = (DomainBean) PersistenceLoader.getApplicationContext().getBean("domain");

		final List<AvailableUserDTO> availableUserDTOs = service.loadAllUserFromStudio(fotostudioDTO, domain);

		for (AvailableUserDTO availableUserDTO : availableUserDTOs) {

		}
	}
}





