<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:fo="http://www.w3.org/1999/XSL/Format">
 <xsl:template match="/">
 
 <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
   <fo:layout-master-set>
     <fo:simple-page-master master-name="simple"
                   page-height="29.7cm" 
                   page-width="21cm"
                   margin-top="1cm" 
                   margin-bottom="2cm" 
                   margin-left="2.5cm" 
                   margin-right="2.5cm">
       <fo:region-body margin-top="3cm" margin-bottom="2cm"/>
       <fo:region-before extent="3cm"/>
       <fo:region-after extent="1.5cm"/>
     </fo:simple-page-master>
   </fo:layout-master-set>
 
   <fo:page-sequence initial-page-number="1" master-reference="simple">
     <fo:static-content flow-name="xsl-region-after">
       <fo:block text-align="right">
         <fo:page-number/>
       </fo:block>
     </fo:static-content>
     <fo:flow flow-name="xsl-region-body">
       <xsl:apply-templates select="data"/>
     </fo:flow>
   </fo:page-sequence>
 </fo:root> 

 </xsl:template>
  
  
 
 <xsl:template match="data">

       <fo:block>
         <xsl:apply-templates select="author"/>
         <xsl:apply-templates select="report"/>
       	<xsl:apply-templates select="name"/>        
       	<xsl:apply-templates select="content"/>  
       </fo:block>
 </xsl:template>
 
 <xsl:template match="name">       
       <fo:block font-size="18pt" 
             font-family="sans-serif" 
             line-height="54pt"
             space-after.optimum="15pt"
             font-weight="bold"
             text-align="left"
             padding-top="43pt">
         <xsl:value-of select="."/>
       </fo:block> 
 </xsl:template>

 
 <xsl:template match="content">
       <fo:block font-size="12pt" 
                 font-family="sans-serif" 
                 line-height="15pt"
                 space-after.optimum="3pt"
                 text-align="justify"
         margin-bottom="40pt">
		<xsl:value-of select="." disable-output-escaping="yes"/>
       </fo:block>
 </xsl:template>
  
  <xsl:template match="report">
    <fo:block font-size="12pt" 
      font-family="sans-serif" 
      line-height="15pt"
      space-after.optimum="3pt"
      padding-top="3pt"
      text-align="left">
      <xsl:text>Auszug aus: </xsl:text> 
      <xsl:value-of select="."/>
    </fo:block>
  </xsl:template>
  
  <xsl:template match="author">       
    <fo:block font-size="11pt" 
      font-family="sans-serif" 
      line-height="24pt"
      space-after.optimum="15pt"
      text-align="right"
      padding-top="0pt">
      <xsl:text>Author: </xsl:text> 
      <xsl:value-of select="@firstname"/> 
      <xsl:text> </xsl:text>
      <xsl:value-of select="@lastname"/>
    </fo:block> 
  </xsl:template>
  
 
 </xsl:stylesheet>
 

