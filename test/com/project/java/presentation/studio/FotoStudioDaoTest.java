package com.project.java.presentation.studio;

import com.project.java.presentation.server.ServiceDatenbankTest;
import com.project.presentation.server.tx.dom.IHibernateService;
import com.project.presentation.shared.dto.AdresseDTO;
import com.project.presentation.shared.dto.FotostudioDTO;
import com.project.presentation.shared.dto.UserDTO;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;
import util.PersistenceLoader;

/**
 * Created by john on 16.07.2015.
 */
public class FotoStudioDaoTest {
    private final IHibernateService service = (IHibernateService) PersistenceLoader
            .getApplicationContext().getBean("HibernateService");

    /** The logger. */
    private Logger logger = Logger.getLogger(ServiceDatenbankTest.class);


    /**
     * Hinzufuegen eines Dummy Stduios
     */
    @Test(groups = "ServiceTest", enabled = false)
    public void addStudio() {
        final FotostudioDTO fotostudioDTO = new FotostudioDTO();
        fotostudioDTO.setName("Liquendi.com");
        fotostudioDTO.setPassword("1234");
        fotostudioDTO.setEmail("info@liquendi.de");
        fotostudioDTO.setTelephone_number("07568 123");
        fotostudioDTO.setFax("07568888");
        fotostudioDTO.setLogo("data:image/gif;base64,R0lGODdhqgBOAHcAACH5BAEAAAcALAAAAACqAE4AggAAAGNlYzEwMZyanAAAAAAAAAAAAAAAAAP/eLrc/jDKSau9a+jNNf5gKI6k2J3oVq5s645pLL90bZOy6uTe7f/AQ+wzCxqPphMrhWw6ISha9EltKm/XqhbbMWa34NUXOA6bMd1LDs05u9XtCi9Nob/vu7hk+uBH7BEAgoOEAHhkOn+AE4sNjQ2FkYeIe3oZTI6WeYkQkYUfkpMwnEIDjD0KmpmkkJ6DoJ+iIaSmbAyql6gPrq8YobJstWLCuKmsC7yCu8oOv829wMclqNKlugzOz8682J7AQj7E17nCrYSB3ArpB8nfP+Llt8fJhur09t7s9+5Yl4rx+FwFTMeNHjN+NmpJU7XPYL1tDhH2s9aHVcNYkiCe07dR/2INhQCNXbsILWOsgQ9PenwhruJIlcgwftLYK9vKFiChvOzYjSQ0jvtu9oFhzGU5m0AJCowoVJ4fOUU3HYUZsx1KZgabRt06rk5XTkiv/hQo9qDQHky6QtnqtOdPbVTzidVailyakEPrSh3xFla9J9WIxJuBdwfFVYXpytGRuBhAwokzsM2leBTawmMiR4mcarLIykla6tTE2ZqH0jk3gZ5FDTPp0ofBMfIcezUc0UYHwzY8W++qqg5/rbuTSG1mor2/ogpONinPN8XxHg+iXBhzucOht/53Te0S2omuC29OfPvocbtBpP5dlqnzvmeic+8eDvxy8R2zu4mzEFB6Iv/2WYdfSeRpd5l0i/ynRoDA4dfgc/sx5l1mCtbxGWIPXpfhQVRJIJcvn8gHhQAweecWfJ0dEEAARqFT4Inn6AfXhxPQg5s5zXmXlWHcDEAihFYt42AnwdXoHilF+oNjULIVZFOQMzJHpJRTMgWWhuS8F2Qt4kUJ4YbugQmlmGTp0qWQGgpIJYxftsekljsuWaQOA3oZkUJnsokinHG6OeaA9XgAqJxYapCnmC4O6eefg24A6F98tmfMoZJWqeijkPJ5j6ODgqmnIJyuiail+a1zT1BBpjNAAKa6Eup4vKgQpp1mEcphqx+miusvP2LnqqFkbcrlmGiGBeeojPra3K7/sEbyqkz53CejrbV+imx2zJ4Ua6VwPrtRtGZOS22m1laabLPoEiJotoN4S+BBlhBbbIdljRopO8dyGyuwywrE73jdACQvreR6qm9W7L7r7L/pKowUkOJ+WnC9B5/a78ULD6Brrp0aTMHA+d6rqTIJ3+oNw9qSh2m5H4tbMqQIY9ywMiirSzGZqL5Iapshn+vNywZrvK/QND6aQcQSJ5pygRYr6/TSnrhbbZ2T6jwvz0CP23PJiNZc7cgnST0xwUqXavVSMkOt9tooJmka0cZqvbPJNMq5ddoOsw1qDEGdgLRtLpTx9hwsSmUi4FYILpLig1eIeDhPqRf542EUsRgmF5QfMsfmxWReHueYe44Q6IGJLtHkNiQAADs=");
        fotostudioDTO.setOeffnungszeiten("Mo. - Fr.9 - 18 Uhr <br> Samstag 8:30 - 13 Uhr");

        final AdresseDTO adresseDTO = new AdresseDTO();
        adresseDTO.setPLZ("88410");
        adresseDTO.setOrt("Hauerz");
        adresseDTO.setStrasse("Nelkenweg.4");
        adresseDTO.setLand("Deutschland");
        fotostudioDTO.setAdresse(adresseDTO);

        service.saveFotostudio(fotostudioDTO);
    }

    //	/** User kann nicht ohne Fotostudio existieren
    @Test(groups = "ServiceTest", enabled = true /*,dependsOnMethods = "addStudio"*/)
    public void insertUserToStudio() {
        logger.info("Mehrere User zu einem Fotostudio an");

        long id = 2L;
        final FotostudioDTO studio = service.loadStudio(id);
        assert studio != null : "Es wurde kein Fotostudio zu der ID "+ id + " gefunden!";

        final UserDTO us = new UserDTO();
        us.setName("Dietmar Gleinser");
        us.setEmail("digl@gmx.de");
        us.setPassword("1234");
        us.setFotostudio(studio);

        final AdresseDTO adresseUS = new AdresseDTO();
        adresseUS.setStrasse("ottobeurerstr. 12");
        adresseUS.setPLZ("87000");
        adresseUS.setOrt("Ottobeuren");
        adresseUS.setLand("Deutschland");
        us.addAdresse(adresseUS);

        service.saveNewUser(us);
        logger.info("Der User wurde zu dem Studio espeichert !");
    }

}
