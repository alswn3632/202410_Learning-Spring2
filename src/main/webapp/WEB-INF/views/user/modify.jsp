<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="../layout/header.jsp"></jsp:include>
		
	<div class="container-md">
		<h3>User Modify Page!!</h3>
		<hr>
		<sec:authentication property="principal.uvo" var="uvo"/>
		<form action="/user/modify" method="post">
			<div class="mb-3">
				<label for="e" class="form-label">email</label>
				<input type="text" class="form-control" name="email" id="e" value=${uvo.email } readonly>
			</div>
			<div class="mb-3">
				<label for="p" class="form-label">pwd</label>
				<input type="text" class="form-control" name="pwd" id="p" placeholder="password...">
			</div>
			<div class="mb-3">
				<label for="n" class="form-label">nickName</label>
				<input type="text" class="form-control" name="nickName" id="n" value=${uvo.nickName }>
			</div>
			<button type="submit" class="btn btn-primary">저장</button>
			<a href="/user/remove"><button type="button" class="btn btn-primary">삭제</button></a>
		</form>
	</div>
		
	<jsp:include page="../layout/footer.jsp"></jsp:include>
</body>
</html>