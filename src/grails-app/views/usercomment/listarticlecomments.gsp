
<%@ page import="interactiveFeatures.Usercomments.Usercomment" %>
<%@ page defaultCodec="none" %>

<div id="articlecomments">
  <h2><g:message code="usercomments.usercomments.label" default="User comments" /></h2>
    <ul style="list-style-type: none; margin-left: 0; padding-left: 0;">
      <g:each in="${usercommentInstanceList}" status="i" var="comment">
        <li>
          <g:include controller="usercomment" action="display" id="${comment?.id}"/>
        </li>
      </g:each>
    </ul>
  <g:checkAccess modulname="usercomment" actionname="create">
    <g:include controller="usercomment" action="create" id="${articleInstance?.id}" />
  </g:checkAccess>
</div>
