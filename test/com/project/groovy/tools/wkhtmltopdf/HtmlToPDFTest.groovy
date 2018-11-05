package com.project.groovy.tools.wkhtmltopdf

import com.project.presentation.server.html.HTMLGenerator
import com.project.presentation.shared.enumerations.ENotify
import com.project.presentation.shared.enumerations.ETheme
import com.project.presentation.shared.view.UserView
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise
import util.PersistenceLoader

/**
 * Created by john on 17.07.2015.
 */
@Stepwise
class HtmlToPDFTest extends Specification {

    @Shared context = PersistenceLoader.getApplicationContext(),
            service = context.getBean("HibernateService"),
            appBean = context.getBean("wkhtmltopdf");

    @Shared UserView view;

    /**
     * Sourcen werden von war resource pdf/defaulttheme genommen
     * Ausgabe der Datei erfolgt in upload/html/1
     */

    /** Setup */
    def setupSpec() {
        view = service.loadViewByUrl("max.mustermann")
        //view = service.loadViewByUrl("christopher.jackson")

        /** Servlet path is another then Spock Framework path */
        appBean.setHtmlTemplateSourceDirWindows("war/resource/pdf/defaulttheme/")
    }

    def "Test System installed Property for wkhtmltopdf application"() {
        given: "System envoirement path variable in reference to HTMLToPDF Generator"
        def path = appBean.applicationPath

        when: "Access system envoirement"
        !System.getenv(path).isEmpty();

        then: "NullPointerException have NOT to be thrown"
        notThrown(NullPointerException)
    }

    def "Test Trying to create PDF"(){
        given : "Generate PDF from HTML Page over corresponding UserView and TAB item 'Willkommen'"
        //def tabItemID = view.einstellung.tabItem.find{it.name.equalsIgnoreCase("Willkommen")}.tabItemID
        def tabItemID = view.einstellung.tabItem.find{it.name.equalsIgnoreCase("Tätigkeiten")}.tabItemID

        def generator = new HTMLGenerator(view, ETheme.DEFAULT_THEME);
        def pdfBean = generator.createPDF(tabItemID, appBean)

        when : "Output is false!"
        pdfBean.notification.notify == ENotify.FAILURE

        then : "Show Failure Message"
        println(pdfBean.notification.getMessage())

        expect: "SUCCESS"
        pdfBean.notification.notify == ENotify.SUCCESS
        println("PDF File " + pdfBean.pdfName + " has been cerated !")
    }
}


//def user = view.user
//def tabitem = view.einstellung.tabItem.find{it.name.equalsIgnoreCase("Willkommen")}