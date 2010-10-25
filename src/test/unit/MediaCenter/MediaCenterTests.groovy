/**
 * Copyright (c) 2009/2010, Very Large Business Applications, University Oldenburg. Specifically:
 * Daniel Süpke <suepke@gmx.de>
 * Andreas Solsbach <solsbach@wi-ol.de>
 * Benjamin Wagner vom Berg <wagnervomberg@wi-ol.de>
 * Prof. Dr. Jorge Marx Gomez <jorge.marx.gomez@uni-oldenburg.de>
 * Christian Wenke <cw81@cw81.de>
 * Desislava Dechkova <desislavamd@yahoo.com>
 * Edzard Fisch <edzard.fisch@googlemail.com>
 * Frank Gerken <frank.gerken@uni-oldenburg.de>
 * Gerrit Meerpohl <gerrit.meerpohl@uni-oldenburg.de>
 * Irene Fröhlich <froehlich.irene@web.de>
 * Kerem-Kazim Sezer <Kerem.Sezer@gmx.de>
 * Olaf Roeder <o.roeder@gmx.net>
 * Oliver Norkus <oliver.norkus@googlemail.com>
 * Rachid Lacheheb <rachid.lacheheb@mail.uni-oldenburg.de>
 * Sebastian van Vliet <sebastian.van.vliet@mail.uni-oldenburg.de>
 * Swetlana Lipnitskaya <swetlana.lipnitskaya@uni-oldenburg.de>
 * 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *     * Neither the name of the copyright holders nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
 
package MediaCenter

import grails.test.*
import ReportEditorMain.MediaCenter.*;

class MediaCenterTests extends GrailsUnitTestCase {
    Media testMedia = new Media(name:"Testmedium", //Titel
        fileName:"testmedium.txt", //original name of the file
        description:"a testfile (not existent)", //Beschreibung
        alt:"alternative text", //HTML Alt Text
        url:"/Storm/Mediathek/nofile.txt", //TODO: test for illegal filetypes
        fileType: "txt",
        date: new Date(),
        //the following attributes are for external sources (e.g. SAP)
        login: "olaf",
        password: "geheim", //TODO: implement encryption
        externalUrl: "url",
        externalType: "type");

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() { //FIXME ans Ende verschieben?
        super.tearDown()
    }

    void testSomething() {
        //simplest test, create an instance and check its values


        assertEquals(testMedia.name, "Testmedium")
        assertEquals(testMedia.fileName, "testmedium.txt")
        assertEquals(testMedia.description, "a testfile (not existent)")
        assertEquals(testMedia.alt, "alternative text")
        assertEquals(testMedia.url, "/Storm/Mediathek/nofile.txt") //TODO: test existing files
        //assertEquals(testMedia.date, ) //TODO: test date
        assertEquals(testMedia.login, "olaf")
        assertEquals(testMedia.password, "geheim")
        assertEquals(testMedia.externalUrl, "url")
        assertEquals(testMedia.externalType, "type")


    }

    void testConstraints(){
        mockForConstraintsTests(Media, [testMedia])

        //test the following conistraints
        //        static constraints = {
        //        //set the order
        //        name(unique:true)
        //        fileName(nullable:false, blank:false, unique:true)
        //        description(nullable:true, blank:true, maxSize:1500)
        //        alt(nullable:true, blank:true)
        //        date()
        //        fileType(nullable:false, blank:false)
        //        url(nullable:false, blank:false) //TODO: auf url prüfen
        //        login(nullable:true, blank:true)
        //        password(nullable:true, blank:true)
        //        externalUrl(nullable:true, blank:true)
        //        externalType(nullable:true, blank:true) //TODO: automatisch erkennen
        //    }

        //validation should pass
        assertTrue testMedia.validate()

        //validation should fail
        def media = new Media()
        assertFalse media.validate()

        //new testMedia, so unique constraints can be tested
        Media testMediaNotUnique = new Media(name:"Testmedium", //Titel
            fileName:"testmedium.txt", //original name of the file
            description:"a testfile (not existent)", //Beschreibung
            alt:"alternative text", //HTML Alt Text
            url:"/Storm/Mediathek/nofile.txt", //TODO: test for illegal filetypes
            fileType: "txt",
            date: new Date(),
            //the following attributes are for external sources (e.g. SAP)
            login: "olaf",
            password: "geheim", //TODO: implement encryption
            externalUrl: "url",
            externalType: "type");

        //new testMedia must be validated, to populate errors object
        assertFalse testMediaNotUnique.validate()

        //test name
        assertEquals "unique", testMediaNotUnique.errors["name"]

        //test fileName
        assertEquals "nullable", media.errors["fileName"]
        //assertEquals "blank", media.errors["fileName"] //TODO: test blank

        mockForConstraintsTests(Media)
        def testMedia = new Media()
        assertFalse testMedia.validate()

        println "=" * 20
        println "Total number of errors:"
        println testMedia.errors.errorCount

        println "=" * 20
        println "Here are all of the errors:"
        println testMedia.errors

        println "=" * 20
        println "Here are the errors individually:"
        testMedia.errors.allErrors.each{
            println it
            println "-" * 20
        }
    }
}
