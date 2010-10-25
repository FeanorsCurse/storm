
<%@ page import="interactiveFeatures.Usercomments.Usercomment" %>

      <g:if test="session.user != 'Anonym'">
              <h2><g:message code="usercomments.your.label" default="Your Comment" /></h2>
        <table>
          <g:form controller="usercomment"><g:hiddenField name="article" value="${usercommentInstance?.article?.id}" />
          <tr>
            <td>Titel: <g:textField style="width:270px;" name="title" value="${message(code: 'usercomment.newtitle.label', default: '')}" /></td>
          </tr>
          <tr>
            <td><richui:richTextEditor name="commentcontent" value="${message(code: 'usercomment.newcontent.label', default: '')}"/></td>
          </tr>
          <tr>
            <td><span class="button"><g:actionSubmit class="save" action="save" value="${message(code: 'usercomment.button.savecomment.label', default: 'Save Comment')}" /></span></td>
          </tr>
          </g:form>
        </table>
      </g:if>