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
			<h3>Board List Page!!</h3>
			<table class="table table-hover">
				  <thead>
				    <tr>
				      <th scope="col">#</th>
				      <th scope="col">title</th>
				      <th scope="col">writer</th>
				      <th scope="col">regDate</th>
				      <th scope="col">readCount</th>
				    </tr>
				  </thead>
				  <tbody>
				  	<c:forEach items="${list }" var="bvo">
				  		<tr>
					  	  <td>${bvo.bno }</td>
					  	  <td><a>${bvo.title }</a></td>
					  	  <td>${bvo.writer }</td>
					  	  <td>${bvo.regDate }</td>
					  	  <td>${bvo.readCount }</td>
				  		</tr>
				  	</c:forEach>
				  </tbody>
			</table>
			<a href="/"><button type="button" class="btn btn-success">index</button></a>
		</div>
		
		<jsp:include page="../layout/footer.jsp"></jsp:include>
</body>
</html>