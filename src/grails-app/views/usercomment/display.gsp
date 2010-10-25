
<%@ page import="interactiveFeatures.Usercomments.Usercomment" %>
<%@ page defaultCodec="none" %>

        <table style="font-size: small; width: 100%;">
          <tr style="background-color: #eee; font-size: small;">
            <td width="30px">
              <avatar:gravatar email="${usercommentInstance?.commentator?.email}" size="30" gravatarRating="G" defaultGravatarUrl="identicon"/>
            </td>
            <td>
              <b>${usercommentInstance?.title.encodeAsHTML()}</b>
              (<g:displayUsername user="${usercommentInstance?.commentator}"/>,
              <g:formatDate date="${usercommentInstance?.dateCreated}" type="datetime" timeStyle="SHORT"/>)
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <div class="commentactions" style="text-align: right; float: right; font-size: small; margin-left: 16px;">
                <g:if test="${session.user.id == usercommentInstance.commentator.id && session.user.username != 'Anonym'}">
                <g:checkAccess modulname="usercomment" actionname="edit">
                    <g:link controller="usercomment" action="edit" id="${usercommentInstance?.id}"><g:message code="usercomment.edit.label" default="Edit" /></g:link> |
                </g:checkAccess>
                </g:if>
                <g:checkAccess modulname="usercomment" actionname="delete">
                    <g:link controller="usercomment" action="delete" id="${usercommentInstance?.id}"><g:message code="usercomment.delete.label" default="Delete" /></g:link> |
                </g:checkAccess>
                <g:checkAccess modulname="usercomment" actionname="reply">
                    <g:link controller="usercomment" action="reply" id="${usercommentInstance?.id}"><g:message code="usercomment.replyto.label" default="Reply" /></g:link> |
                </g:checkAccess>
                <g:if test="${session.user.id != usercommentInstance.commentator.id && session.user.username != 'Anonym'}">
                  <g:link controller="usercomment" action="thumbsupvote" id="${usercommentInstance?.id}"><img src="${resource(dir:'images/InteractiveFeatures',file:'Thumbs up.png')}" title="Finde ich gut" alt="Finde ich gut" style="margin-top:2px; margin-bottom:-2px;"/></g:link>&nbsp;(${thumbsupcount})
                </g:if><g:else>
                  <img src="${resource(dir:'images/InteractiveFeatures',file:'Thumbs up.png')}" title="Finde ich gut" alt="Finde ich gut" style="margin-top:2px; margin-bottom:-2px;"/>&nbsp;(${thumbsupcount})
                </g:else>
                <g:if test="${session.user.id != usercommentInstance.commentator.id && session.user.username != 'Anonym'}">
                  <g:link controller="usercomment" action="thumbsdownvote" id="${usercommentInstance?.id}"><img src="${resource(dir:'images/InteractiveFeatures',file:'Thumbs down.png')}" title="Finde ich nicht gut" alt="Finde ich nicht gut" style="margin-top:2px; margin-bottom:-8px;"/></g:link>&nbsp;(${thumbsdowncount})
                </g:if><g:else>
                  <img src="${resource(dir:'images/InteractiveFeatures',file:'Thumbs down.png')}" title="Finde ich nicht gut" alt="Finde ich nicht gut" style="margin-top:2px; margin-bottom:-8px;"/>&nbsp;(${thumbsdowncount})
                </g:else>
              </div>
              <div class="commentcontent">
                ${usercommentInstance?.content}
              </div>
              <div class="updated">
              <g:if test="${usercommentInstance?.dateCreated != usercommentInstance?.lastUpdated}">
                (<g:message code="usercomment.updated.label" default="Updated" />:
                <g:formatDate date="${usercommentInstance?.lastUpdated}" type="datetime" timeStyle="SHORT"/>)
              </g:if>
              </div>
            </td>
          </tr>
        </table>

        <ul style="list-style-type: none; margin-left: 0.5em; padding-left: 0.5em;">
          <g:each in="${replyList}" status="i" var="reply">
             <li>
               <g:include controller="usercomment" action="display" id="${reply?.id}"/>
             </li>
          </g:each>
        </ul>