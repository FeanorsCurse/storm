
<%@ page import="ReportEditorMain.MediaCenter.Media" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="${resource(dir:'css/default',file:'main.css')}" />
  <g:set var="entityName" value="${message(code: 'media.label', default: 'Media')}" />
  <title><g:message code="default.list.label" args="[entityName]" /></title>
  <script type="text/javascript" src="${resource(dir:'js/tinymce/jscripts/tiny_mce', file:'tiny_mce_popup.js')}"></script>
  <script language="javascript" type="text/javascript">
  var FileBrowserDialogue = {
  init : function () {
  // Here goes your code for setting your custom things onLoad.
  },
  mySubmit : function (media) {
  //var URL = document.my_form.my_field.value;
  var URL = media;
  var win = tinyMCEPopup.getWindowArg("window");

  // insert information now
  win.document.getElementById(tinyMCEPopup.getWindowArg("input")).value = URL;

  // are we an image browser
  if (typeof(win.ImageDialog) != "undefined")
  {
  // we are, so update image dimensions and preview if necessary
  if (win.ImageDialog.getImageData) win.ImageDialog.getImageData();
  if (win.ImageDialog.showPreviewImage) win.ImageDialog.showPreviewImage(URL);
  }

  // close popup window
  tinyMCEPopup.close();
  }
  }

  tinyMCEPopup.onInit.add(FileBrowserDialogue.init, FileBrowserDialogue);

  </script>

</head>
<body style="background:#e8e8e8;">

<div class="mediaThumbnails">
  <g:include controller="media" action="listThumbs" />
</div>

</body>
</html>