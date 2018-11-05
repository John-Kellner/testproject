package com.project.groovy.presentation.user

import com.project.presentation.server.tx.dom.IHibernateService
import com.project.presentation.shared.dto.AdresseDTO
import com.project.presentation.shared.dto.FotostudioDTO
import com.project.presentation.shared.dto.UserDTO
import com.project.presentation.shared.enumerations.ENotify
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise
import util.PersistenceLoader

/**
 * Created by john on 16.04.2016.
 */
@Stepwise
class SaveUserIntegrationTest extends Specification {

    @Shared
            service = (IHibernateService) PersistenceLoader.getApplicationContext().getBean("HibernateService"),
            userDTO = new UserDTO(),
            adresseDTO = new AdresseDTO(),
            fotostudio = new FotostudioDTO()

    /** Setup */
    def setupSpec() {
        userDTO.setName("Mellanie Hirte")
        userDTO.setPassword("1234")
        userDTO.setEmail("melanie.hirte@t-online.de")

        //Adresse
        adresseDTO.setStrasse("Hirtenweg.4");
        adresseDTO.setOrt("Memmingen");
        adresseDTO.setLand("Deutschland");
        adresseDTO.setPLZ("87000");
        userDTO.setAdresse(adresseDTO)

        // Fotostudio
        def fotostudio = service.findFotostudio("Emotion Qualifikation")
        this.fotostudio.setFotostudioId(fotostudio.getFotostudioId())
        userDTO.setFotostudio(this.fotostudio)
    }

    def "Demo User soll gespeichert werden!"() {
        given:
        def user = service.saveNewUser(userDTO)

        expect:
        user.getNotify() == ENotify.SUCCESS
    }

    @Ignore
    def "Demo User soll wieder geloescht werden!"(){
        given: "Laden des Demo Users"
        def user = service.findUserForLogin("melanie.hirte@t-online.de", "1234")

        when: "User vorhanden"
        user.userId != null

        then: "User Loeschen"
        def user1 = service.deleteUser(user)

        expect: "Require SUCCESS"
        user1.getNotify() == ENotify.SUCCESS
    }

}
