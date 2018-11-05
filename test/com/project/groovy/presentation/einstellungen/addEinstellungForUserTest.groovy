package com.project.groovy.presentation.einstellungen

import com.project.presentation.shared.dto.EinstellungDTO
import com.project.presentation.shared.dto.TabItemDTO
import com.project.presentation.shared.dto.TemplateDTO
import com.project.presentation.shared.view.UserView
import spock.lang.Specification

/**
 * Created by john on 17.07.2015.
 */
class addEinstellungforUserTest extends Specification {

    def "geladener User bekommt eine Einstellung"() {
        given: "bestehender User aus der DB"
        def user = service.findUserForLogin(userDTO.getEmail(), userDTO.getPassword())

        when: "User  nicht existiert, dann lege ein neuer an"
        if (user == null) {
            service.saveNewUser(userDTO)
        }
        and:
        user = service.findUserAndAddress(1L)
//        then: "User existiert"
        and:
        if (user != null) {
            user.getName().equals(userDTO.getName())
        }
//        def "User bekommmt eine Einstellung"() {
        then:
//            given:
        def finalEinstellungDTO = new EinstellungDTO()
//            and:
        finalEinstellungDTO.setUser(user)
        def finalTemplateDTO = new TemplateDTO()

//        then:
        finalTemplateDTO.setName("Designs")
        finalEinstellungDTO.setTemplate(finalTemplateDTO)
        def finalListTabItemDTO = new ArrayList<>()

        def o = new TabItemDTO(0, "Willkommen");
        o.setImage(DummyImage.dummyImage);
        finalListTabItemDTO.add(o)
        finalListTabItemDTO.add(new TabItemDTO(0, "Qualifikation"))
        finalListTabItemDTO.add(new TabItemDTO(1, "Zertifikate"))
        finalListTabItemDTO.add(new TabItemDTO(2, "Freizeit"))
        finalListTabItemDTO.add(new TabItemDTO(3, "Vision"))

        finalEinstellungDTO.setTabItem(finalListTabItemDTO)
        final UserView userView = service.addEinstellung(finalEinstellungDTO)
        System.out.println(userView.getNotificationBean().getNotify() + "" + userView.getNotificationBean().getMessage())

    }}

