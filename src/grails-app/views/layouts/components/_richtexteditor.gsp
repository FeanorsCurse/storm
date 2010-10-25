<!--
  This component shows the Richtexteditor
  
  @author: Gerrit
-->
<%@ page import="ReportEditorMain.ArticleEditor.Article"%>
<%@ page import="ReportEditorMain.ArticleEditor.ArtInd"%>
<%@ page import="shemaEditor.indicatorAdministration.Indicator"%>

<script type="text/javascript" src="${resource(dir:'js/tinymce/jscripts/tiny_mce', file:'tiny_mce.js')}"></script>

<script type="text/javascript">
// Creates a new plugin class and a custom listbox
tinymce.create('tinymce.plugins.IndicatorPlugin', {
    createControl: function(n, cm) {
        switch (n) {
            case 'indicatorlistbox':
                var mlb = cm.createListBox('indicatorlistbox', {
					//TODO: translate Indicator
                    title : 'Indicator',
                     onselect : function(v) {
                         tinyMCE.activeEditor.selection.setContent(v);
                     }
                });

                <%
					if(indicatorList!=null){
						for (ArtInd artInd : indicatorList) {
							out << " mlb.add('"+artInd.indicator.name+"', '\${"+artInd.indicator.id+"} "+artInd.indicator.unit+"');"
						}
					}
				%>  

                // Return the new listbox instance
                return mlb;
        }
        return null;
    }
});

// Register plugin with a short name
tinymce.PluginManager.add('indicator', tinymce.plugins.IndicatorPlugin);

// Initialize TinyMCE with the new plugin and listbox
tinyMCE.init({
    plugins : '-indicator,safari,spellchecker,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template', // - tells TinyMCE to skip the loading of the plugin
    mode : "textareas",
    theme : "advanced",
    theme_advanced_buttons1 : "indicatorlistbox,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect,fontselect,fontsizeselect",
	theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
	theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen",
	theme_advanced_buttons4 : "insertlayer,moveforward,movebackward,absolute,|,styleprops,spellchecker,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,blockquote,pagebreak",
    theme_advanced_toolbar_location : "top",
    theme_advanced_toolbar_align : "left",
    theme_advanced_statusbar_location : "bottom",
    file_browser_callback : "myFileBrowser",
    theme_advanced_resizing : true,
	theme_advanced_resizing_use_cookie : false,
	width: "100%",
	height: "600px",
	relative_urls : "false",
    remove_script_host : false,
    convert_urls : false,
	media_use_script: false,
	cleanup : true,
	media_strict: true,
	media_types: "flash=swf,flv;shockwave=dcr;qt=mov,qt,mpg,mp3,mp4,mpeg;shockwave=dcr;wmp=avi,wmv,wm,asf,asx,wmx,wvx;rmp=rm,ra,ram"
});
function myFileBrowser (field_name, url, type, win) {

    //alert("Field_Name: " + field_name + "\nURL: " + url + "\nType: " + type + "\nWin: " + win); // debug/testing

    /* If you work with sessions in PHP and your client doesn't accept cookies you might need to carry
       the session name and session ID in the request string (can look like this: "?PHPSESSID=88p0n70s9dsknra96qhuk6etm5").
       These lines of code extract the necessary parameters and add them back to the filebrowser URL again. */

    //var cmsURL = window.location.toString();    // script URL - use an absolute path!
	//var cmsURL="${resource(dir:'js/tinymce/jscripts/tiny_mce/plugins/media/', file:'thumb.gsp')}"
	var cmsURL="${resource(dir:'media/', file:'thumb.gsp')}"
	//alert("cmsURL: "+cmsURL);
    if (cmsURL.indexOf("?") < 0) {
        //add the type as the only query parameter
        cmsURL = cmsURL + "?type=" + type;
    }
    else {
        //add the type as an additional query parameter
        // (PHP session ID is now included if there is one at all)
        cmsURL = cmsURL + "&type=" + type;
    }

    tinyMCE.activeEditor.windowManager.open({
        file : cmsURL,
        title : 'Storm Media Preview',
        width : 800,  // Your dimensions may differ - toy around with them!
        height : 600,
        resizable : "yes",
        inline : "yes",  // This parameter only has an effect if you use the inlinepopups plugin!
        close_previous : "yes",
		popup_css : false // Disable TinyMCE's default popup CSS
    }, {
        window : win,
        input : field_name
    }
    );
    return false;
  }


</script>
