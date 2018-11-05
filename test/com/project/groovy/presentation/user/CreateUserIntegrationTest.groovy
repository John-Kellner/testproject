package com.project.groovy.presentation.user

import com.project.presentation.server.tx.dom.IHibernateService
import com.project.presentation.shared.dto.AdresseDTO
import com.project.presentation.shared.dto.UserDTO
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
import util.PersistenceLoader

/**
 * Created by john on 17.07.2015.
 */
class CreateUserIntegrationTest extends Specification {

    @Shared
            service = (IHibernateService) PersistenceLoader.getApplicationContext().getBean("HibernateService"),
            userDTO = new UserDTO(),
            adresseDTO = new AdresseDTO()

    /** Setup */
    def setupSpec() {
        userDTO.setName("Max Mustermann")
        userDTO.setPassword("1234")
        userDTO.setEmail("user@t-online.de")

        adresseDTO.setStrasse("Memmingerstr.2");
        adresseDTO.setOrt("Memmingen");
        adresseDTO.setLand("Deutschland");
        adresseDTO.setPLZ("87000");
        userDTO.setAdresse(adresseDTO)
    }

    def "User soll angelegt werden, wenn dieser nicht existiert!"() {
        given: "bestehender User aus der Datenbank"
        def user = service.findUserAndAddress(1L)

        when: "User nicht existiert, dann lege ein neuer an"
        if (user == null) {
            service.saveNewUser(userDTO)
        }
        and:
        user = service.findUserAndAddress(1L)

        then: "User existiert"
        user != null
        user.getName().equals(userDTO.getName())
    }

    def "Ergebnis: Finde vorhandenen User #Name #ID #EMail"() {
        given:
        def user = service.findUserAndAddress(1L)

        expect:
        user != null
        user.getName().equals(Name)

        where:
        Name = userDTO.getName()
        ID = userDTO.getUserId()
        EMail = userDTO.getEmail()
    }

}
