<!--
  This component lists the Bookmark Buttons
  
  TODO: Edit the Bookmarks within the Administration
  
  @author: Frank
  @changed: 10.05.2010 Gerrit
  
-->
<!--  
<div class="bookmarklinks">
  <a href="http://www.delicious.com/"><img src="${resource(dir:'images',file:'delicious.png')}" alt="Delicious" /></a>
  <a href="http://www.mister-wong.de"><img src="${resource(dir:'images',file:'mister-wong.png')}" alt="Mister Wong" /></a>
  <a href="http://www.blogger.de"><img src="${resource(dir:'images',file:'blogger.png')}" alt="Blogger" /></a>
  <a href="http://www.linkedin.com"><img src="${resource(dir:'images',file:'linkedin.png')}" alt="LinkedIn" /></a>
</div>
-->
<%
	//create url
	String url=request.request.requestURL
	//create title
	String title=articleInstance.name.encodeAsURL()
	//create teaser
	//String teaser=articleInstance.content.encodeAsURL()
%>
<div id="spSocialBookmark" class="spArticleBottomBox spClearfix">	
			<ul class="spSocialBookmarkDe">
				<li class="spFirst">
					<a href="http://twitter.com/home?status=${url}" target="_blank" title="Twitter"><img src="${resource(dir:'images/bookmarks',file:'twitter.gif')}" alt="Twitter" border="0" height="16" hspace="0" width="16"></a>
				</li>
				<li>
					<a href="http://de.facebook.com/sharer.php?u=${url}&amp;t=${title}" target="_blank"><img src="${resource(dir:'images/bookmarks',file:'facebook.gif')}" alt="Facebook" title="Facebook" align="left" border="0" height="16" hspace="0" width="16"></a>
				</li>

				<li>
					<a target="_blank" href="http://www.studivz.net/Suggest/Selection/?u=${url}&amp;desc=${title}&amp;prov=Storm"><img src="${resource(dir:'images/bookmarks',file:'studiVZ.gif')}" title="studiVZ meinVZ schülerVZ" border="0" height="16" hspace="0" width="16"></a>
				</li>
				<li>
					<a href="http://www.myspace.com/index.cfm?fuseaction=postto&amp;u=${url}&amp;t=${title}" target="_blank"><img src="${resource(dir:'images/bookmarks',file:'myspace.gif')}" alt="MySpace" title="MySpace" align="left" border="0" height="16" hspace="0" width="16"></a>
				</li>
				<li>
					<a href="http://del.icio.us/post?url=${url}&amp;title=${title}" target="_blank"><img src="${resource(dir:'images/bookmarks',file:'delicious.gif')}" alt="deli.cio.us" title="deli.cio.us" align="left" border="0" height="16" hspace="0" width="16"></a>
				</li>

				<li>
					<a href="http://digg.com/submit?phase=2&amp;url=${url}&amp;title=${title}" target="_blank"><img src="${resource(dir:'images/bookmarks',file:'digg.gif')}" alt="Digg" title="Digg" align="left" border="0" height="16" hspace="0" width="16"></a>
				</li>
				<li>
					<a href="http://www.folkd.com/submit/${url}" target="_blank"><img src="${resource(dir:'images/bookmarks',file:'folkd.gif')}" alt="Folkd" title="Folkd" align="left" border="0" height="16" hspace="0" width="16"></a>
				</li>
				<li>
					<a href="http://www.google.com/bookmarks/mark?op=add&amp;bkmk=${url}&amp;title=${title}" target="_blank"><img src="${resource(dir:'images/bookmarks',file:'google.gif')}" alt="Google Bookmarks" title="Google Bookmarks" align="left" border="0" height="16" hspace="0" width="16"></a>
				</li>

				<li>
					<a href="http://linkarena.com/bookmarks/addlink/?url=${url}&amp;title=${title}" target="_blank"><img src="${resource(dir:'images/bookmarks',file:'linkarena.gif')}" alt="Linkarena" title="Linkarena" align="left" border="0" height="16" hspace="0" width="16"></a>
				</li>
				<li>
					<a href="http://www.mister-wong.de/index.php?action=addurl&amp;bm_url=${url}&amp;bm_description=${title}" target="_blank"><img src="${resource(dir:'images/bookmarks',file:'misterwong.gif')}" alt="Mister Wong" title="Mister Wong" align="left" border="0" height="16" hspace="0" width="16"></a>
				</li>
				<li>
				<a href="http://www.newsvine.com/_tools/seed&amp;save?u=${url}&amp;h=${title}" target="_blank"><img src="${resource(dir:'images/bookmarks',file:'newsvine.gif')}" alt="Newsvine" title="Newsvine" align="left" border="0" height="16" hspace="0" width="16"></a>
				</li>

			
				<li>
				<a href="http://reddit.com/submit?url=${url}&amp;title=${title}" target="_blank"><img src="${resource(dir:'images/bookmarks',file:'reddit.gif')}" alt="reddit" title="reddit" align="left" border="0" height="16" hspace="0" width="16"></a>
				</li>
				<li>
					<a href="http://www.stumbleupon.com/submit?url=${url}&amp;title=${title}" target="_blank"><img src="${resource(dir:'images/bookmarks',file:'stumbleupon.gif')}" alt="StumbleUpon" title="StumbleUpon" align="left" border="0" height="16" hspace="0" width="16"></a>
				</li>
				<li>
					<a href="https://favorites.live.com/quickadd.aspx?marklet=1&amp;mkt=en-us&amp;url=${url}&amp;title=${title}&amp;top=1" target="_blank"><img src="${resource(dir:'images/bookmarks',file:'windows.gif')}" alt="Windows Live" title="Windows Live" align="left" border="0" height="16" hspace="0" width="16"></a>
				</li>

				<li>
					<a href="http://myweb2.search.yahoo.com/myresults/bookmarklet?t=${title}&amp;d=&amp;tag=&amp;u=${url}" target="_blank"><img src="${resource(dir:'images/bookmarks',file:'yahoo.gif')}" alt="Yahoo! Bookmarks" title="Yahoo! Bookmarks" align="left" border="0" height="16" hspace="0" width="16"></a>
				</li>
				<li>
					<a href="http://www.yigg.de/neu?exturl=${url}&amp;exttitle=${title}" target="_blank"><img src="${resource(dir:'images/bookmarks',file:'yigg.gif')}" alt="Yigg" title="Yigg" align="left" border="0" height="16" hspace="0" width="16"></a>
				</li>

			</ul>	
			<ul>
							<li>
					<iframe src="http://www.facebook.com/widgets/like.php?href=${url}"
        scrolling="no" frameborder="0"
        style="border:none; width:200px; height:80px"></iframe>
				</li>
				</ul>			
	</div>
	<br>


