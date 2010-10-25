
<%@ page import="interactiveFeatures.Usercomments.Usercomment" %>
<%@ page defaultCodec="none" %>

<ul>
  <g:each in="${usercommentInstanceList}" status="i" var="comment">
    <li>
      <g:remoteLink action="listchildcomments" id="${comment?.id}" update="commentitem${comment?.id}">[+]</g:remoteLink>
      <g:remoteLink action="render" id="${comment?.id}" update="displaycomment${comment?.id}">
        ${comment?.title}
      </g:remoteLink>
      (<g:formatDate date="${comment?.dateCreated}" type="datetime" timeStyle="SHORT"/> | ${comment?.commentator})
    </li>
  </g:each>
  <div id="commentitem${comment?.id}">
    <g:include action="listchildcomments" id="${comment?.id}"/>
    <g:include action="reply" id="${comment?.id}"/>
  </div>
</ul>
