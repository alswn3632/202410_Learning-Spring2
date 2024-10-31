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
			<h3>Board Detail Page!!</h3>
			<hr>
			<div class="mb-3">
			  <label for="n" class="form-label">no</label>
			  <span class="badge rounded-pill text-bg-primary">${bvo.regDate }</span>
			  <span class="badge rounded-pill text-bg-primary">${bvo.readCount }</span>
			  <input type="text" class="form-control" id="n" value="${bvo.bno }" readonly>
			</div>
			<div class="mb-3">
			  <label for="f" class="form-label">title</label>
			  <input type="text" class="form-control" id="f" value="${bvo.title }" readonly>
			</div>
			<div class="mb-3">
			  <label for="w" class="form-label">writer</label>
			  <input type="text" class="form-control" id="w" value="${bvo.writer }" readonly>
			</div>
			<div class="mb-3">
			  <label for="c" class="form-label">content</label>
			  <textarea class="form-control" name="content" id="c" rows="3" readonly>${bvo.content }</textarea>
			</div>
			
			<a href="/board/modify?bno=${bvo.bno }"><button type="button" class="btn btn-primary">수정</button></a>
			<a href="/board/delete?bno=${bvo.bno }"><button type="button" class="btn btn-primary">삭제</button></a>
			
			<br>
			<hr>
			<br>
			<!-- Comment Line -->
			<!-- Comment Post -->
			<div class="input-group mb-3">
			  <span class="input-group-text" id="cmtWriter">test12@tester.com</span>
			  <input type="text" class="form-control" id="cmtText" placeholder="Add Comment...">
			  <button class="btn btn-outline-secondary" id="cmtAddBtn" type="button">등록</button>
			</div>
			
			<!-- Comment Print -->
			<ul class="list-group list-group-flush" id="cmtListArea"></ul>
			
			<!-- 더보기 버튼 -->
			<div>
				<button class="btn btn-dark" id="moreBtn" type="button" data-page = "1" style="visibility:hidden">More +</button>
			</div>
			
			<!-- Modal -->
			<div class="modal fade" id="myModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h1 class="modal-title fs-5" id="exampleModalLabel">writer</h1>
			        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			      </div>
			      <div class="modal-body">
			        <input type="text" class="form-control" id="cmtTextMod">
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
			        <button type="button" id="cmtModBtn" class="btn btn-primary">저장</button>
			      </div>
			    </div>
			  </div>
			</div>
			
			<!-- JS Script -->
			<script type="text/javascript">
				let bnoVal = `<c:out value = "${bvo.bno }" />`
				console.log(bnoVal);
			</script>
			<script type="text/javascript" src="/resources/js/boardDetailComment.js"></script>
			<script type="text/javascript">
				spreadCommentList(bnoVal);
			</script>
		</div>
		
		<jsp:include page="../layout/footer.jsp"></jsp:include>
</body>
</html>