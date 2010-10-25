
<%@ page import="interactiveFeatures.Infocart.*" %>

<h2><g:message code="welcome.publishedinfocarts" default="Editorially published Infocarts" /></h2>
            <div class="list">
                <ul>
                    <g:each in="${infocartInstanceList}" status="i" var="infocartInstance">
                        <li>
                            <g:link action="display" id="${infocartInstance?.id}">${infocartInstance?.name}</g:link>
                            (<g:displayUsername user="${infocartInstance?.user}"/>,
                            <g:formatDate date="${infocartInstance?.lastUpdated}" type="datetime" timeStyle="SHORT"/>,
                            ${infocartInstance?.items?.size()} <g:message code="default.items.label" default="Items"/>)
                        </li>
                    </g:each>
                </ul>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${infocartInstanceTotal}" />
            </div>
