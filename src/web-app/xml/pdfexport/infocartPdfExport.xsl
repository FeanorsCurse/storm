<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:fox="http://xmlgraphics.apache.org/fop/extensions">
	<xsl:template match="/">


		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="simple"
					page-height="29.7cm" page-width="21cm" margin-top="0cm"
					margin-bottom="2cm" margin-left="2.5cm" margin-right="2.5cm">
					<fo:region-body margin-top="3cm" margin-bottom="2cm" />
					<fo:region-before extent="2cm" />
					<fo:region-after extent="1.5cm" />
				</fo:simple-page-master>
			</fo:layout-master-set>

			<fo:page-sequence initial-page-number="1"
				master-reference="simple">
				<fo:static-content flow-name="xsl-region-after">
					<fo:block text-align="right">
						<fo:page-number />
					</fo:block>
				</fo:static-content>

				<fo:flow flow-name="xsl-region-body">

					<fo:block font-size="8pt" font-family="sans-serif"
						line-height="30pt" space-after.optimum="3pt" padding-top="3pt"
						text-align="start">
						<xsl:text>Infokorb von: </xsl:text>
						<xsl:value-of select="data/user/@firstname" />
						<xsl:text> </xsl:text>
						<xsl:value-of select="user/@lastname" />
					</fo:block>

					<fo:block font-size="18pt" font-family="sans-serif"
						font-weight="bold" line-height="15pt" space-after.optimum="3pt"
						margin-top="30pt" padding-top="15pt" text-align="center"
						margin-bottom="100pt">
						<xsl:value-of select="data/infocartname" />
					</fo:block>

					<fo:block font-size="18pt" font-family="sans-serif"
						font-weight="bold" line-height="15pt" space-after.optimum="3pt"
						padding-top="3pt" text-align="start" margin-bottom="100pt">
						<xsl:text>Inhalt</xsl:text>
					</fo:block>

					<fo:block color="blue" margin-bottom="100pt">
						<fo:list-block line-height="1.5em" font-size="12pt"
							font-family="Times">
							<xsl:variable name="index" select="1" />
							<xsl:for-each select="data/itemlist/infoitem/article">
								<fo:list-item>
									<fo:list-item-label end-indent="label-end()">
										<fo:block>
											<xsl:value-of select="position()" />
										</fo:block>
									</fo:list-item-label>
									<fo:list-item-body start-indent="body-start()">
										<fo:block>
											<fo:basic-link internal-destination="{@name}">
												<xsl:value-of select="@name" />
											</fo:basic-link>
										</fo:block>
									</fo:list-item-body>
								</fo:list-item>
							</xsl:for-each>
						</fo:list-block>
					</fo:block>
					<fo:block break-before="page" />

					<xsl:apply-templates select="data" />
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>



	<xsl:template match="data">


		<xsl:for-each select="itemlist/infoitem">

			<fo:block id="{article/@name}" font-size="12pt" font-family="sans-serif"
				line-height="54pt" space-after.optimum="15pt" font-weight="bold"
				text-align="left" padding-top="43pt">
				<xsl:value-of select="article/@name" />
			</fo:block>

			<fo:block font-size="12pt" font-family="sans-serif"
				line-height="15pt" space-after.optimum="3pt" text-align="justify"
				margin-bottom="40pt">
				<xsl:value-of select="article/content" />
			</fo:block>

			<fo:block font-size="9pt" font-family="sans-serif"
				line-height="24pt" space-after.optimum="15pt" text-align="right"
				padding-top="0pt">
				<xsl:text>Author: </xsl:text>
				<xsl:value-of select="article/@author_firstname" />
				<xsl:text> </xsl:text>
				<xsl:value-of select="article/@author_lastname" />
			</fo:block>

		</xsl:for-each>
	</xsl:template>



</xsl:stylesheet>
 

