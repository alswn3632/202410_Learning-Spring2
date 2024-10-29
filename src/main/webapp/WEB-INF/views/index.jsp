<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<jsp:include page="layout/header.jsp"></jsp:include>
<div class="container-md">
	<P>The time on the server is ${serverTime}.</P>
</div>
<jsp:include page="layout/footer.jsp"></jsp:include>