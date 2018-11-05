package com.project.groovy.tools.wkhtmltopdf

import spock.lang.Specification

/**
 * Created by john on 22.11.2015.
 */
class CurrentDirectoryTest extends Specification{

    def "Test currentPath" () {

        given: ""
        def path = new File(new File(".").getAbsolutePath())
        println(path.getAbsolutePath())

    }

}
