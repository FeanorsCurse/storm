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
 
package systemadministration.modulmanagement
//TODO Doku
//TODO println rausnehmen
/**Für alle Module müssen hier die Einstellungen getroffen werden
 **einfache für ModuleSettings, Config und Rules ein neues switch case eintragen
 */
class ModuleSettingsService {
	
	boolean transactional = true
	
	def serviceMethod() {
		
	}
	
	//TODO Doku
	
	static ArrayList getModuleSettings(String moduleName)
	{
		ArrayList moduleSettings
		//Description(String), Detachable [-1,0,1]
		//-1 not detachable
		// 0 off
		// 1 on
		switch (moduleName)
		{
			case 'function':
				moduleSettings =  [  'Demofunction to show the Pluginfunctionality',  0]
				break;
			case 'user':
				moduleSettings =  [  'This Module holds the Users',  -1]
				break;
			case 'role':
				moduleSettings =  [  'This Module holds the role',  -1]
				break;
			case 'module':
				moduleSettings =  [  'This Module holds the settings for each module',  -1]
				break;
			/* Mediathek Module
			 * written by: Olaf
			 */
			case 'media': moduleSettings = ['This module manages media files', -1]
				break;
			/* Report Module
			 * written by: Olaf
			 */
			case 'report': moduleSettings = ['This module manages reports', -1]
				break;
			/* Article Module
			 * written by: Olaf
			 */
			case 'article': moduleSettings = ['This module manages articles', -1]
				break;
			/*
			 * SchemaEditor - Modules
			 * written by: Sebastian
			 *
			 */
			case 'treport':
				moduleSettings =  [  'This module manages schemas',  -1]
				break;
			case 'tnode':
				moduleSettings =  [  'This module manages schema-layers',  -1]
				break;
			case 'indicator':
				moduleSettings =  [  'This module manages schemaindicators',  -1]
				break;
			case 'category':
				moduleSettings =  [  'This module manages categories',  -1]
				break;
			case 'taggable': moduleSettings = ['This module manages tags', 1]
				break;
			case 'rateable': moduleSettings = ['This module manages ratings', 1]
				break;
			case 'usercomment': moduleSettings = ['This module manages comments', 1]
				break;
			case 'help': moduleSettings = ['This module manages display of help', 1]
				break;
			case 'helpSection': moduleSettings = ['This module manages help sections', 1]
				break;
			case 'helpArticle': moduleSettings = ['This module manages help articles', 1]
				break;
			default:
				moduleSettings = [  'Bitte Config nachpflegen',  1]
			//        println("Achtung bitte Settings für das Module: ${moduleName} in ModuleSettingsService.groovy eintragen")
		}
		return moduleSettings
	}
	
	static ArrayList getRules(Module module) {
		ArrayList rules
		//RuleName(String), Description(String), ActionName(String)
		switch (module.name.toString())
		{
			case 'function':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
				break;
			case 'user':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*'],
				[  'Action logout',  'Dieses Recht erlaubt den Zugriff auf: logout ',  'logout'],
				[  'Action login',  'Dieses Recht erlaubt den Zugriff auf: login ',  'login'],
				[  'Action createUser',  'Dieses Recht erlaubt den Zugriff auf: createUser ',  'createUser'],
				[  'forgotPassword',  'Dieses Recht erlaubt den Zugriff auf: forgotPassword ',  'forgotPassword'],
				[  'changePwdUser',  'Dieses Recht erlaubt den Zugriff auf: changePwdUser ',  'changePwdUser'],
				[  'showUser',  'Dieses Recht erlaubt den Zugriff auf: showUser ',  'showUser'],
				[  'show',  'Dieses Recht erlaubt den Zugriff auf: showUser ',  'show'],
				[  'deleteUser',  'Dieses Recht erlaubt den Zugriff auf: deleteUser ',  'deleteUser'],
				[  'editPwdUser',  'Dieses Recht erlaubt den Zugriff auf: editPwdUser ',  'editPwdUser'],
				[  'editUser',  'Dieses Recht erlaubt den Zugriff auf: editUser ',  'editUser'],
				[  'updateUser',  'Dieses Recht erlaubt den Zugriff auf: updateUser ',  'updateUser'],
				[  'lastArticles',  'Dieses Recht erlaubt den Zugriff auf: lastArticles ',  'lastArticles'],
				[  'myRecommendation',  'Dieses Recht erlaubt den Zugriff auf: myRecommendation ',  'myRecommendation'],
				[  'validateMail',  'Dieses Recht erlaubt den Zugriff auf: validateMail ',  'validateMail'],
				[  'myFollowers',  'Dieses Recht erlaubt den Zugriff auf: myFollowers ',  'myFollowers'],
				[  'authenticate',  'Dieses Recht erlaubt den Zugriff auf: authenticate ',  'authenticate'],
				[  'save',  'Dieses Recht erlaubt den Zugriff auf: save ',  'save'],
				[  'addFriend',  'addFriend',  'addFriend'],
				[  'index',  'Dieses Recht erlaubt den Zugriff auf: index ',  'index'],
				[  'list',  'Recht:list',  'list'],
				[  'forgotPassword_SetandSendMail',  'forgotPassword_SetandSendMail',  'forgotPassword_SetandSendMail'],
				[  'auth',  'auth',  'auth']]
				break;
			case 'role':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
				break;
			case 'module':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
				break;
			case 'welcome':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
				break;
			case 'help':
				rules = [ [ 'all', 'Alle Rechte in dem Module', '*'],
				[ 'overview', 'Dieses Recht erlaubt den Zugriff auf: overview ', 'overview'],
				[ 'section', 'Dieses Recht erlaubt den Zugriff auf: listsections ', 'section'],
				[ 'listsections', 'Dieses Recht erlaubt den Zugriff auf: listsections ', 'listsections'],
				[ 'createsection', 'Dieses Recht erlaubt den Zugriff auf: createsection ', 'createsection'],
				[ 'savesection', 'Dieses Recht erlaubt den Zugriff auf: savesection ', 'savesection'],
				[ 'editsection', 'Dieses Recht erlaubt den Zugriff auf: editsection ', 'editsection'],
				[ 'updatesection', 'Dieses Recht erlaubt den Zugriff auf: updatesection ', 'updatesection'],
				[ 'deletesection', 'Dieses Recht erlaubt den Zugriff auf: deletesection ', 'deletesection'],
				[ 'article', 'Dieses Recht erlaubt den Zugriff auf: createarticle ', 'article'],
				[ 'listarticles', 'Dieses Recht erlaubt den Zugriff auf: createarticle ', 'listarticles'],
				[ 'createarticle', 'Dieses Recht erlaubt den Zugriff auf: createarticle ', 'createarticle'],
				[ 'savearticle', 'Dieses Recht erlaubt den Zugriff auf: savearticle ', 'savearticle'],
				[ 'editarticle', 'Dieses Recht erlaubt den Zugriff auf: editarticle ', 'editarticle'],
				[ 'updatearticle', 'Dieses Recht erlaubt den Zugriff auf: updatearticle ', 'updatearticle'],
				[ 'deletearticle', 'Dieses Recht erlaubt den Zugriff auf: deletearticle ', 'deletearticle'],
				[ 'search', 'Dieses Recht erlaubt den Zugriff auf: search ', 'search']]
				break;
			case 'infocart':
				rules = [ [ 'all', 'Alle Rechte in dem Module', '*'],
				[ 'list', 'Dieses Recht erlaubt den Zugriff auf: list ', 'list'],
				[ 'listvisible', 'Dieses Recht erlaubt den Zugriff auf: listvisible ', 'listvisible'],
				[ 'listpublished', 'Dieses Recht erlaubt den Zugriff auf: listpublished ', 'listpublished'],
				[ 'save', 'Dieses Recht erlaubt den Zugriff auf: save ', 'save'],
				[ 'show', 'Dieses Recht erlaubt den Zugriff auf: show ', 'show'],
				[ 'display', 'Dieses Recht erlaubt den Zugriff auf: display ', 'display'],
				[ 'showAsOnePage', 'Dieses Recht erlaubt den Zugriff auf: showAsOnePage ', 'showAsOnePage'],
				[ 'edit', 'Dieses Recht erlaubt den Zugriff auf: edit ', 'edit'],
				[ 'updatename', 'Dieses Recht erlaubt den Zugriff auf: updatename ', 'updatename'],
				[ 'togglepublished', 'Dieses Recht erlaubt den Zugriff auf: togglepublished ', 'togglepublished'],
				[ 'togglevisible', 'Dieses Recht erlaubt den Zugriff auf: togglevisible ', 'togglevisible'],
				[ 'delete', 'Dieses Recht erlaubt den Zugriff auf: delete ', 'delete'],
				[ 'addArticle', 'Dieses Recht erlaubt den Zugriff auf: addArticle ', 'addArticle'],
				[ 'deleteItem', 'Dieses Recht erlaubt den Zugriff auf: deleteItem ', 'deleteItem'],
				[ 'xml', 'Erlaubt den Zugriff auf xml', 'xml'],
				[ 'toPdf', 'Dieses Recht erlaubt den Zugriff auf: toPdf ', 'toPdf']	]
				break;
			case 'tagcloud':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
				break;
			case 'article':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*'],
				[  'display',  'Anzeigen von Artikeln',  'display'],
				[  'addTag',  'Tag für Artikel vergeben',  'addTag'],
				[  'addTags',  'Tag für Artikel vergeben',  'addTags'],
				[  'findByTag',  'Artikel nach Tag finden',  'findByTag'],
				[  'searcharticle',  'Recht zuordnen',  'searcharticle'],
				[  'xml',  'Recht um das xml zu einem Article anzuzeigen',  'xml'],
				[  'pdf',  'Anzeigen von Artikeln als PDF',  'pdf'],
				[  'starsrated',  'Recht zuordnen',  'starsrated']]
				break;
			case 'question':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*'],
				[  'allAnswered',  'Recht zuordnen',  'allAnswered'],
				[  'allNew',  'Recht zuordnen',  'allNew'],
				[  'allTop',  'Recht zuordnen',  'allTop'],
				[  'display',  'Recht zuordnen',  'display'],
				[  'show2',  'Recht zuordnen',  'show2'],
				[  'userSave',  'Recht zuordnen',  'userSave']]
				break;
			case 'report':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*'],
				[  'display',  'Anzeigen von Artikeln',  'display'],
				[  'pdf',  'Anzeigen von Artikeln als PDF',  'toPdf'],
				[  'xml',  'Anzeigen von Artikeln als PDF',  'xml'],
				[  'sidebar',  'Recht zuordnen',  'sidebar']]
				break;
			case 'category':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
				break;
			case 'indicator':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*'],
				[  'showIndicator',  'Anzeigen von Indikatoren',  'show'],
				[  'showChart',  'Anzeige von Diagrammen',  'chart'] ]
			
				break;
			case 'tNode':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
				break;
			case 'tReport':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
				break;
			case 'accessLog':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
				break;
			case 'changeLog':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
				break;
			case 'securityLog':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
				break;
			case 'config':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
				break;
			case 'language':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
				break;
			case 'plugin':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
				break;
			case 'template':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
				break;
			case 'rule':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
				break;
			case 'taggable':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*'],
				[  'addTags',  'Alle Rechte in dem Module',  'addTag'],
				[  'addTags',  'Alle Rechte in dem Module',  'addTags']
				]
				break;
			case 'rateable':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
				break;
			case 'usercomment':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*'],
				[  'create',  'Create usercoments',  'create'],
				[  'save',  'Save usercomments',  'save'],
				[  'list',  'Lists all usercomments',  'list'],
				[  'listarticlecomments',  'List all usercomments of a specified article',  'listarticlecomments'],
				[  'display',  'Display usercomments',  'display'],
				[  'displaysingle',  'Display single usercomments without answers',  'displaysingle'],
				[  'reply',  'Reply to usercomments',  'reply'],
				[  'edit',  'Edit usercomments',  'edit'],
				[  'update',  'Update usercomments',  'update'],
				[  'delete',  'Delete usercomments',  'delete']
				]
				break;
			case 'feed':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
			case 'helpSection':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
				break;
			case 'helpArticle':
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
				break;
			case 'media':
				rules = [ [ 'all', 'all rights in this module', '*'],
				[ 'Media_thumb', 'lists all media files, without special functions', 'thumb'],
				[ 'Media_list', 'lists all media files', 'list'],
				[ 'Media_index', 'lists all media files', 'index'],
				[ 'Media_listThumbs', 'controls wether images or other media files should be listed for the Rich Text Editor s media plugin', 'listThumbs'],
				[ 'Media_searchFilter', 'organizes search results', 'searchFilter'],
				[ 'Media_searchAJAX', 'autocomplete for search', 'searchAJAX'],
				[ 'Media_create', 'saves a local media to the server', 'create'],
				[ 'Media_save', 'saves a local media to the server', 'save'],
				[ 'Media_download', 'saves an external media to the server', 'download'],
				[ 'Media_show', 'shows a single media', 'show'],
				[ 'Media_update', 'edits a media', 'update'],
				[ 'Media_edit', 'edits a media', 'edit'],
				[ 'Media_delete', 'deletes a media', 'delete'],
				[ 'Media_actionChecked', 'deletes a chosen list of media', 'actionChecked'],
				[ 'Media_updateArticles', 'updates the list of referenced articles', 'updateArticles'],
				[ 'Media_addToView', 'adds a media to a view', 'addToView'],
				[ 'Media_removeFromView', 'removes a media from a view', 'removeFromView']]
				break;
			default:
				rules = [ [  'all',  'Alle Rechte in dem Module',  '*']]
			//         println("Achtung bitte Settings für das Module: ${module.name.toString()} in ModuleSettingsService.groovy eintragen")
		}
		return rules
	}
	
	static ArrayList getConfigs(Module module) {
		ArrayList configs
		//ConfigName(String), Para(String),Description (String)
		switch (module.name.toString())
		{
			case 'function':
				configs = [ [  'config1',  'para1',  'desc1'],
				[  'config2',  'para2',  'desc2'],
				[  'config3',  'para3',  'desc3'],
				[  'config4',  'para4',  'desc4'],
				[  'config5',  'para5',  'desc5'] ]
				break;
			case 'user':
				configs = [ [  'securityOn',  '1',  '0 ist aus und 1 ist an'],
				[  'backend',  'Backend',  'The following role allows access to the backend'],
				[  'config3',  'para3',  'desc3'],
				[  'config4',  'para4',  'desc4'],
				[  'config5',  'para5',  'desc5'] ]
				break;
			case 'role':
				configs = [ [  'config1',  'para1',  'desc1'],
				[  'config2',  'para2',  'desc2'],
				[  'config3',  'para3',  'desc3'],
				[  'config4',  'para4',  'desc4'],
				[  'config5',  'para5',  'desc5'] ]
				break;
			case 'module':
				configs = [ [  'config1',  'para1',  'desc1'],
				[  'config2',  'para2',  'desc2'],
				[  'config3',  'para3',  'desc3'],
				[  'config4',  'para4',  'desc4'],
				[  'config5',  'para5',  'desc5'] ]
				break;
			
			case 'infocart':
				configs = [ [  'xmlurl',  '1',  'desc1'],
				[  'config2',  'para2',  'desc2'],
				[  'config3',  'para3',  'desc3'],
				[  'config4',  'para4',  'desc4'],
				[  'config5',  'para5',  'desc5'] ]
				break;
			case 'media': configs = [['default', '', 'default configuration']]
				break;
			case 'help': configs = [['default', '', 'default configuration']]
				break;
			case 'accessArticleTyp':
				configs = [ [  'lastRun',  new Date().getTime().toString(),  'Datum des letzten laufes'],
				[  'enabled',  '1',  'Aktiv=1, inatikv=0'],
				[  'countPara0',  '1',  'Anzahltage welche dieser Counter benutzen soll. -1 heißt deaktiviert'],
				[  'countPara1',  '7',  'Anzahltage welche dieser Counter benutzen soll. -1 heißt deaktiviert'],
				[  'countPara2',  '14',  'Anzahltage welche dieser Counter benutzen soll. -1 heißt deaktiviert'],
				[  'countPara3',  '21',  'Anzahltage welche dieser Counter benutzen soll. -1 heißt deaktiviert'],
				[  'countPara4',  '-1',  'Anzahltage welche dieser Counter benutzen soll. -1 heißt deaktiviert'],
				[  'config5',  'para5',  'desc5'] ]
				break;
			
			case 'articleToArticleCollaborativeFiltering':
				configs = [ [  'lastRun',  1.toString() ,  'Datum des letzten laufes'],
				[  'enabled',  '1',  'Aktiv=1, inatikv=0']  ]
				break;
			case 'ArticleViewedByInterestgroup':
				configs = [ [  'lastRun',  1.toString() ,  'Datum des letzten laufes'],
				[  'enabled',  '1',  'Aktiv=1, inatikv=0']  ]
				break;    
			case 'ArticleViewedByFriends':
				configs = [ [  'lastRun',  1.toString() ,  'Datum des letzten laufes'],
				[  'enabled',  '1',  'Aktiv=1, inatikv=0']  ]
				break;  
			
			//default
			default:
				configs = null
			// TODO: Println raus
			//println("Achtung bitte Settings für das Module: ${module.name.toString()} in ModuleSettingsService.groovy eintragen")
		}
		return configs
	}
}
