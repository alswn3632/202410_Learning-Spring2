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
			<h3>Board Modify Page!!</h3>
			<hr>
			<form action="/board/update" method="post">
			<div class="mb-3">
			  <label for="n" class="form-label">no</label>
			  <span class="badge rounded-pill text-bg-primary">${bvo.regDate }</span>
			  <span class="badge rounded-pill text-bg-primary">${bvo.readCount }</span>
			  <input type="text" name="bno" class="form-control" id="n" value="${bvo.bno }" readonly >
			</div>
			<div class="mb-3">
			  <label for="f" class="form-label">title</label>
			  <input type="text" name="title" class="form-control" id="f" value="${bvo.title }" >
			</div>
			<div class="mb-3">
			  <label for="w" class="form-label">writer</label>
			  <input type="text" name="writer" class="form-control" id="w" value="${bvo.writer }" >
			</div>
			<div class="mb-3">
			  <label for="c" class="form-label">content</label>
			  <textarea class="form-control" name="content" id="c" rows="3" >${bvo.content }</textarea>
			</div>
			
			<button type="submit" class="btn btn-primary">완료</button>
			</form>
			
		</div>
		
		<jsp:include page="../layout/footer.jsp"></jsp:include>
</body>
</html>