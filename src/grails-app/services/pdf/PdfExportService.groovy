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
 
package pdf
import interactiveFeatures.Infocart.Infocart;

import java.net.URI;

import java.io.File;
import java.io.OutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.sax.SAXResult;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.beans.factory.InitializingBean;

import systemadministration.modulmanagement.Module;

import ReportEditorMain.ArticleEditor.Article;
import ReportEditorMain.ReportEditor.Report;

class PdfExportService implements InitializingBean{
	
	def grailsApplication
	def articleService
	
	
	//ReportExportvariables
	def xmlServiceUrlReport
	def reportRoot
	def reportPdfExportXsltName

	
	//ArticleExportvariables
	def xmlServiceUrlArticle
	def articlePdfRoot
	def articlePdfExportXsltName
	
	
	//InfocartExportvariables
	def infocartRoot
	def xmlServiceUrlInfocart
	def infocartPdfOutname
	def infocartXmlOutname
	def infocartPdfExportXsltName
	
	def xsltRootPath
	
	def setting
	//Rather than implementing a constructor for your service class, it is usually more useful to implement the Spring InitializingBean interface so that injected dependencies are available.
	void afterPropertiesSet()
	{
		//Generel DI
		this.setting = grailsApplication.config
		this.xsltRootPath=this.setting.service.pdfExport.xslt
		
		//InfocartSettings
		this.xmlServiceUrlInfocart=this.setting.service.pdfExport.xmlServiceUrlInfocart
		this.infocartRoot = this.setting.service.pdfExport.infocartPdfRoot
		this.infocartPdfOutname=this.setting.service.pdfExport.infocartPdfOutname
		this.infocartXmlOutname=this.setting.service.pdfExport.infocartXmlOutname
		this.infocartPdfExportXsltName=this.setting.service.pdfExport.infocartPdfExportXsltName
		
		
		//ArticleSetting
		this.xmlServiceUrlArticle=this.setting.service.pdfExport.xmlServiceUrlArticle
		this.articlePdfRoot = this.setting.service.pdfExport.articlePdfRoot
		this.articlePdfExportXsltName=this.setting.service.pdfExport.articlePdfExportXsltName
		
		
		//ReportSettings
		this.xmlServiceUrlReport=this.setting.service.pdfExport.xmlServiceUrlReport
		this.reportRoot = this.setting.service.pdfExport.reportPdfRoot
		this.reportPdfExportXsltName=this.setting.service.pdfExport.reportPdfExportXsltName
	}
	
	
	//Module module = Module.getModule('PdfExport')
	
	
	/**
	 * Exports a given article to PDF. 
	 * 
	 * @author Sebastian van Vliet
	 * @param article The article, to be exported
	 * @param root The root-dir where the PDF is saved
	 */
	public String export(Article article, String root){
		
		
		def report =articleService.getReport(article)
		
		String folder =replaceSpecialCharacters(report.name.toLowerCase()+"/"+article.name.toLowerCase()+"/")
		String outname =replaceSpecialCharacters(article.name+"_"+article.id+".pdf")
		String xmloutname =replaceSpecialCharacters(report.name+"_"+article.id+".xml")
		
		
		File baseDir = new File(root)
		
		
		File outDir = new File(baseDir, folder)
		File xml = new File(baseDir, folder+xmloutname)
		outDir.mkdirs()
		
		//XMLSrvice = "http://localhost:8080/Storm/article/xml/"
		java.net.URL storm = new java.net.URL(xmlServiceUrlArticle+article.id)
		
		URLConnection yc = storm.openConnection()
		InputStreamReader isr =  new InputStreamReader(yc.getInputStream())
		
		FileWriter fw = new FileWriter(xml)
		BufferedWriter bw = new BufferedWriter(fw)
		
		bw.write(isr.getText())
		bw.close()
		
		
		//XSLT "web-app/xml/pdfexport/pdfFormat.xsl"
		File xsltfile = new File(this.xsltRootPath+this.articlePdfExportXsltName)
		File pdffile = new File(outDir, outname)
		
		FopFactory fopFactory = FopFactory.newInstance()
		FOUserAgent foUserAgent = fopFactory.newFOUserAgent()
		
		OutputStream out = new java.io.FileOutputStream(pdffile)
		out = new java.io.BufferedOutputStream(out)
		try {
			
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF,
					foUserAgent, out)
			
			TransformerFactory factory = TransformerFactory.newInstance()
			Transformer transformer = factory
					.newTransformer(new StreamSource(xsltfile))
			
			transformer.setParameter("versionParam", "2.0")
			
			Source src = new StreamSource(xml)
			
			
			Result res = new SAXResult(fop.getDefaultHandler())
			
			transformer.transform(src, res)
		} finally {
			out.close();
		}
		
		def url =this.articlePdfRoot+folder+"/"+outname
		
		return url
		
	}
	
	/**
	 * Export all Infocart-Articles to PDF
	 * @param infocart The Infocart, to be exported
	 * 
	 */
	public String export(Infocart infocart, String root, String uname){
		
		//String outname ="TMP_infocart.pdf"
		//String xmloutname ="TMP_infocart.xml"
		
		File outDir = new File(root+"/"+uname)
		File xml = new File(outDir, infocartXmlOutname)
		outDir.mkdirs()
		
		java.net.URL storm = new java.net.URL(this.xmlServiceUrlInfocart+infocart.id)
		
		URLConnection yc = storm.openConnection()
		InputStreamReader isr =  new InputStreamReader(yc.getInputStream())
		
		FileWriter fw = new FileWriter(xml)
		BufferedWriter bw = new BufferedWriter(fw)
		
		bw.write(isr.getText())
		bw.close()
		
		File xsltfile = new File(this.xsltRootPath+this.infocartPdfExportXsltName)
		File pdffile = new File(outDir, infocartPdfOutname)
		
		FopFactory fopFactory = FopFactory.newInstance()
		FOUserAgent foUserAgent = fopFactory.newFOUserAgent()
		
		OutputStream out = new java.io.FileOutputStream(pdffile)
		out = new java.io.BufferedOutputStream(out)
		try {
			
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF,
					foUserAgent, out)
			
			TransformerFactory factory = TransformerFactory.newInstance()
			Transformer transformer = factory
					.newTransformer(new StreamSource(xsltfile))
			
			transformer.setParameter("versionParam", "2.0")
			
			Source src = new StreamSource(xml)
			
			
			Result res = new SAXResult(fop.getDefaultHandler())
			
			transformer.transform(src, res)
		} finally {
			out.close();
		}
		
		def url =this.infocartRoot+uname+"/"+infocartPdfOutname
		
		return url
	}
	
	/**
	 * Export all Infocart-Articles to PDF
	 * @param infocart The Infocart, to be exported
	 * 
	 */
	public String export(Report report, String root){
		
		String outname =replaceSpecialCharacters(report.name+".pdf")
		String xmloutname =replaceSpecialCharacters(report.name+".xml")
		
		
		File outDir = new File(root)
		File xml = new File(outDir, xmloutname)
		outDir.mkdirs()
		
		
		java.net.URL storm = new java.net.URL(this.xmlServiceUrlReport+report.id)
		
		URLConnection yc = storm.openConnection()
		InputStreamReader isr =  new InputStreamReader(yc.getInputStream())
		
		FileWriter fw = new FileWriter(xml)
		BufferedWriter bw = new BufferedWriter(fw)
		
		bw.write(isr.getText())
		bw.close()
		
		File xsltfile = new File(this.xsltRootPath+this.reportPdfExportXsltName)
		File pdffile = new File(outDir, outname)
		
		FopFactory fopFactory = FopFactory.newInstance()
		FOUserAgent foUserAgent = fopFactory.newFOUserAgent()
		
		OutputStream out = new java.io.FileOutputStream(pdffile)
		out = new java.io.BufferedOutputStream(out)
		try {
			
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF,
					foUserAgent, out)
			
			TransformerFactory factory = TransformerFactory.newInstance()
			Transformer transformer = factory
					.newTransformer(new StreamSource(xsltfile))
			
			transformer.setParameter("versionParam", "2.0")
			
			Source src = new StreamSource(xml)
			
			
			Result res = new SAXResult(fop.getDefaultHandler())
			
			transformer.transform(src, res)
		} finally {
			out.close();
		}
		
		def url =this.reportRoot+"/"+outname
		
		return url
	}
	
	def String replaceSpecialCharacters(String string){
		string = string.replaceAll("ö","oe")
		string = string.replaceAll("ä","ae")
		string = string.replaceAll("ü","ue")
		string = string.replaceAll(" ","_")
		return string
	}
	
}
