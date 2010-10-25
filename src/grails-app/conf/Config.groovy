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
 
/**
 * Config for the application
 * 
 * @author: all
 */
//war filename

grails.war.destFile = "Storm.war"
	
	
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text-plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      pdf: 'application/pdf',
                      rtf: 'application/rtf',
                      excel: 'application/vnd.ms-excel',
                      ods: 'application/vnd.oasis.opendocument.spreadsheet',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]
// The default codec used to encode data with ${}
grails.views.default.codec="html" // none, html, base64
grails.views.gsp.encoding="UTF-8"
grails.converters.encoding="UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'
// set AJAX Library jQuery
grails.views.javascript.library="jquery"
// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder=false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable fo AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// set rateable evaluator
grails.rateable.rater.evaluator = { session.user }
// set commentable evaluator
grails.commentable.poster.evaluator = { session.user }
// set tag case preserving
grails.taggable.preserve.case = true


//PDF-configuration

service.pdfExport.xslt ="web-app/xml/pdfexport/"
	
//PDF-Configuration: Infocart
service.pdfExport.xmlServiceUrlInfocart="http://localhost:8080/Storm/infocart/xml/"
service.pdfExport.infocartPdfRoot="/infocart/pdf/"
service.pdfExport.infocartXSLT="infocartPdfExport.xsl"
service.pdfExport.infocartPdfOutname="TMP_infocart.pdf"
service.pdfExport.infocartXmlOutname="TMP_infocart.xml"
service.pdfExport.infocartPdfExportXsltName="infocartPdfExport.xsl"
	
//PDF-Configuration: Article
service.pdfExport.xmlServiceUrlArticle="http://localhost:8080/Storm/article/xml/"
service.pdfExport.articlePdfRoot="/nachhaltigkeitsberichte/released/"
service.pdfExport.articlePdfExportXsltName="pdfFormat.xsl"
	
	
//PDF-Configuration: Report
service.pdfExport.xmlServiceUrlReport="http://localhost:8080/Storm/report/xml/"
service.pdfExport.reportPdfRoot="/nachhaltigkeitsberichte/released/"
service.pdfExport.reportPdfExportXsltName="reportPdfExport.xsl"
	
// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://www.changeme.com"
    }
    development {
        grails.serverURL = "http://localhost:8080/${appName}"
    }
    test {
        grails.serverURL = "http://localhost:8080/${appName}"
    }

}

// log4j configuration
log4j = {
    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
	       'org.codehaus.groovy.grails.web.pages', //  GSP
	       'org.codehaus.groovy.grails.web.sitemesh', //  layouts
	       'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
	       'org.codehaus.groovy.grails.web.mapping', // URL mapping
	       'org.codehaus.groovy.grails.commons', // core / classloading
	       'org.codehaus.groovy.grails.plugins', // plugins
	       'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
	       'org.springframework',
	       'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'
}

fckeditor {
	upload {
		basedir = "/Mediathek/"
		baseurl = "/Mediathek/"
		overwrite = true
		link {
			browser = true
			upload = true
			allowed = []
			denied = ['html', 'htm', 'php', 'php2', 'php3', 'php4', 'php5',
					'phtml', 'pwml', 'inc', 'asp', 'aspx', 'ascx', 'jsp',
					'cfm', 'cfc', 'pl', 'bat', 'exe', 'com', 'dll', 'vbs', 'js', 'reg',
					'cgi', 'htaccess', 'asis', 'sh', 'shtml', 'shtm', 'phtm']
		}
		image {
			browser = true
			upload = true
			allowed = ['jpg', 'gif', 'jpeg', 'png']
			denied = []
		}
		flash {
			browser = true
			upload = true
			allowed = ['swf']
			denied = []
		}
		media {
			browser = true
			upload = true
			allowed = ['mpg','mpeg','avi','wmv','asf','mov']
			denied = []
		}
	}
}
grails {
   mail {
	   host = "smtp.gmail.com"
     port = 465
     username = "pgstorm2@gmail.com"
     password = "12340987"
     props = ["mail.smtp.auth":"true", 					   
              "mail.smtp.socketFactory.port":"465",
              "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
              "mail.smtp.socketFactory.fallback":"false"]

} }
