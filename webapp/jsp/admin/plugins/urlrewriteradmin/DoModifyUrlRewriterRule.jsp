

<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="urlrewriteradmin" scope="session" class="fr.paris.lutece.plugins.urlrewriteradmin.web.UrlRewriterAdminJspBean" />

<%
    urlrewriteradmin.init( request, urlrewriteradmin.RIGHT_MANAGE_URLREWRITERADMIN );
    response.sendRedirect( urlrewriteradmin.doModifyRule( request ) );
%>

<%@ include file="../../AdminFooter.jsp" %>

