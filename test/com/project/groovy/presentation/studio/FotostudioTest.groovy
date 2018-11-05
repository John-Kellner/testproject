package com.project.groovy.presentation.studio

import com.project.presentation.server.tx.dom.IHibernateService
import com.project.presentation.shared.dto.AdresseDTO
import com.project.presentation.shared.dto.FotostudioDTO
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
import util.PersistenceLoader

/**
 * Created by john on 17.07.2015.
 */
class FotostudioTest extends Specification {

    @Shared service = (IHibernateService) PersistenceLoader.getApplicationContext().getBean("HibernateService"),
            fotostudioDTO = new FotostudioDTO(),
            adresseDTO = new AdresseDTO()

    /** Setup */
    def setupSpec() {
        fotostudioDTO.setName("Liquendi.com")
        fotostudioDTO.setPassword("1234")
        fotostudioDTO.setEmail("info@liquendi.de")
        fotostudioDTO.setTelephone_number("07568 123")

        adresseDTO.setPLZ("88410")
        adresseDTO.setOrt("Hauerz")
        adresseDTO.setStrasse("Nelkenweg.4")
        adresseDTO.setLand("Deutschland")
        fotostudioDTO.setAdresse(adresseDTO)
    }

    def "Fotostudio soll angelegt werden"() {
        given: "das bestehende Fotostudio aus der DB"
        def studio = service.findFotostudio(fotostudioDTO.getName())

        when: "Fotostudio nicht existiert, dann lege ein neues an"
        if (studio == null) {
            service.saveFotostudio(fotostudioDTO)
        }
        and:
        studio = service.findFotostudio(fotostudioDTO.getName())

        then: "Fotostudio existiert"
        studio != null
        studio.getName().equals(fotostudioDTO.getName())
    }

    @Unroll
    def "Finde ein vorhandenes Fotostudio #Name #ID #EMail #Telefonnummer"() {
        given:
        def studio = service.findFotostudio(Name)

        expect:
        studio != null
        studio.getName().equals(Name)

        where:
        Name = fotostudioDTO.getName()
        ID = fotostudioDTO.getFotostudioId()
        EMail = fotostudioDTO.getEmail()
        Telefonnummer = fotostudioDTO.getTelephone_number()
        fax = fotostudioDTO.getFax()
        
    }

}