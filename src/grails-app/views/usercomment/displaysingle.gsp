
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
                  <img src="${resource(dir:'images/InteractiveFeatures',file:'Thumbs up.png')}" title="Finde ich gut" alt="Finde ich gut" style="margin-top:2px; margin-bottom:-2px;"/>&nbsp;(${thumbsupcount})
                  <img src="${resource(dir:'images/InteractiveFeatures',file:'Thumbs down.png')}" title="Finde ich nicht gut" alt="Finde ich nicht gut" style="margin-top:2px; margin-bottom:-8px;"/>&nbsp;(${thumbsdowncount})
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
