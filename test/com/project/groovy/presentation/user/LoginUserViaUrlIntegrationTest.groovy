package com.project.groovy.presentation.user

import com.project.presentation.shared.dto.LoginDTO
import com.project.presentation.shared.enumerations.ENotify
import com.project.presentation.shared.view.UserView
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise
import util.PersistenceLoader

/**
 * Created by john on 21.04.2016.
 */
@Stepwise
class LoginUserViaUrlIntegrationTest extends Specification{

    @Shared context = PersistenceLoader.getApplicationContext(),
            service = context.getBean("HibernateService"),
            md5Url , dbUser;

    @Shared UserView view;

    /**
     * Login via URL is descriped as a part of a specific login which is used to call url domainname
     */

    /**
     * Setup
     * Login with User and SetUp Database with MD5 Login by URL Only
     */
    def setupSpec() {
        dbUser = service.loginUser("user@t-online.de", "1234");
        dbUser.is128BitLogin = true
        service.updateEncyrptionState(dbUser)

        // Checkout MD5 Key
        md5Url = dbUser.md5 /** "92c74504e601837c1aac7240e2dd944b" **/
    }

    def "Login via MD5 when encyption is disabled and Check MD5"() {
        def username

        when: "View geladen ist"
        view = service.loadViewByUrl(md5Url)
        username = view.user.name.trim().replace(" ",".").toLowerCase() // Max Mustermann -> max.mustermann

        then: "MD5 hash is eqals original MD5 hash"
        view.user.getMd5().equals(md5Url)

        when: "Login with Username"
        view = service.loadViewByUrl(username)

        then: " Should fail, because both login possibility url AND surename.lastname are not allowed at the same time"
        view.notificationBean.notify == ENotify.FAILURE
    }

    def "Login via User max.mustermann when encryption is disabled"(){
        def username

        given: "Loaded User"
            dbUser = service.loginUser("user@t-online.de", "1234")
            dbUser.is128BitLogin = false
            service.updateEncyrptionState(dbUser)
        when: "Login with name URL surename.lastename"
            username = dbUser.name.trim().replace(" ",".").toLowerCase() // Max Mustermann -> max.mustermann
            view = service.loadViewByUrl(username)
        then: "Should ACCEPT"
            view.notificationBean.notify == ENotify.SUCCESS

        when: "Login with URL MD5 hash code"
            view = service.loadViewByUrl(md5Url)

        then: "Should Fail because it is disabled"
            view.notificationBean.notify == ENotify.FAILURE
    }

    def "Login User max.mustermann via loadUserView"(){
        given: "Loaded View"
        def loggedInUser = new LoginDTO("user@t-online.de", "1234")
        loggedInUser.setUserID(1)

        when: "View geladen wird"
        def view = service.loadUserView(loggedInUser)

        then: "check"
        view.images.size() == 0
    }

    def "Login User max.mustermann via Url link"() {
        given: "Loaded View"
        def url = service.loadViewByUrl("max.mustermann")

        expect:
        url.images.size() == 0
    }
}
