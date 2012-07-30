

<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="urlrewriteradmin" scope="session" class="fr.paris.lutece.plugins.urlrewriteradmin.web.UrlRewriterAdminJspBean" />

<% urlrewriteradmin.init( request, urlrewriteradmin.RIGHT_MANAGE_URLREWRITERADMIN ); %>
<%= urlrewriteradmin.getModifyRule ( request ) %>

<%@ include file="../../AdminFooter.jsp" %>

