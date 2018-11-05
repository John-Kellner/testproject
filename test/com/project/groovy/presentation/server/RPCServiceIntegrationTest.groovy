package com.project.groovy.presentation.server

import com.project.presentation.server.RPCServiceLaiquendiImpl
import spock.lang.Shared
import spock.lang.Specification
import util.PersistenceLoader

/**
 * Created by john on 17.07.2015.
 */
class RPCServiceIntegrationTest extends Specification {

    @Shared context = PersistenceLoader.getApplicationContext(),
            service = context.getBean("HibernateService"),
            rcpService = new RPCServiceLaiquendiImpl();

    /** Setup */
    def setupSpec() {
        rcpService.domainBean = context.getBean("domain")
        rcpService.service = service;
    }

    def "Simulation User Login"() {
        given:
        def user = service.loginUser("user@t-online.de", "1234")

        expect:
        user != null
    }

}