<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="../layout/header.jsp"></jsp:include>
		
	
	<div class="container-md">
		<h3>User List Page!!</h3>
		<hr>
		<div class="row row-cols-1 row-cols-md-3 g-4">
			<c:forEach items="${userList }" var="uvo">
				<div class="card h-300" style="width: 18rem; margin-right: 15px;">
				  <img src="/resources/image/basic.png" class="card-img-top" alt="..." style="margin-top: 12px;">
				  <div class="card-body">
				    <h5 class="card-title">${uvo.email }</h5>
				    <h6 class="card-title">(${uvo.nickName })</h6>
				    <p>가입일 : ${uvo.regDate }</p>
				    <p>접속일 : ${uvo.lastLogin }</p>
				    <c:forEach items="${uvo.authList }" var="authList">
				    	<c:if test="${authList.auth eq 'ROLE_ADMIN'}">
						   	<span class="badge rounded-pill text-bg-danger">${authList.auth }</span>
				    	</c:if>
				    	<c:if test="${authList.auth eq 'ROLE_USER'}">
						   	<span class="badge rounded-pill text-bg-primary">${authList.auth }</span>
				    	</c:if>
				    </c:forEach>
				  </div>
				</div>	
			</c:forEach>
		</div>
			
	</div>
		
		
	<jsp:include page="../layout/footer.jsp"></jsp:include>
</body>
</html>