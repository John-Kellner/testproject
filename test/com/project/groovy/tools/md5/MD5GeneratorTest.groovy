package com.project.groovy.tools.md5

import com.project.presentation.server.md5.MD5Generator
import spock.lang.Specification

/**
 * Created by john on 14.04.2016.
 */
class MD5GeneratorTest extends Specification {

    def "Test Create MD5 Hashcode" (){
        given:
        def key = MD5Generator.createMD5Key("liquendi.com");

        expect: "generated MD5 key sould be equals"
        key.equals("135058d532d6af10b15fedc5ee084f40")
    }
}
