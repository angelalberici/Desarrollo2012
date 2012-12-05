<%@ include file="/jsp/includes.jsp" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>

<%-- // use our pagedListHolder --%>
<jsp:useBean id="pagedListHolder" scope="request" type="org.springframework.beans.support.PagedListHolder"/>
<%-- // create link for pages, "~" will be replaced later on with the proper page number --%>
<c:url value="/paging.do" var="pagedLink">
	<c:param name="action" value="list"/>
    <c:param name="p" value="~"/>
</c:url>

<%-- // load our paging tag, pass pagedListHolder and the link --%>
<tg:paging pagedListHolder="${pagedListHolder}" pagedLink="${pagedLink}"/>

<%-- // show only current page worth of data --%>
<table width="200px" border="1">
<tr>
	<th width="20px">No.</th>
	<th>Random Number</th>
</tr>
<c:forEach items="${pagedListHolder.pageList}" var="item">
    <tr>
    	<td>${item.key}</td>
    	<td style="color:blue;font-weight:bold;text-align:right">${item.data}</td>
    </tr>
</c:forEach>
</table>

<%-- // load our paging tag, pass pagedListHolder and the link --%>
<tg:paging pagedListHolder="${pagedListHolder}" pagedLink="${pagedLink}"/>
