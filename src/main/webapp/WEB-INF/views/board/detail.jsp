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
			<h3>Board Detail Page!!</h3>
			<hr>
			<!-- 이제 받은 값이 bvo에서 bdto로 바뀜 ${bdto} -->
			<!-- c:set 값을 저장하는 용도  -->
			<c:set value="${bdto.bvo }" var="bvo"></c:set>
			<c:set value="${bdto.flist }" var="flist"></c:set>
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
			
			<!-- file upload 표시 라인 -->
			<div class="mb-3">
				<ul class="list-group list-group-flush">
					<!-- 파일의 개수만큼 li를 반복하여 파일 표시 타입이 1인 경우만 크림으로 표시 -->
					<c:forEach items="${flist }" var="fvo">
						<li class="list-group-item">
							<c:choose>
								<c:when test="${fvo.fileType > 0 }">
									<!-- 그림 파일 : 출력 -->
									<div>
										<img alt="" src="/upload/${fvo.saveDir }/${fvo.uuid}_${fvo.fileName}" style="max-width: 300px; height: auto;">
										<div class="fw-bold">${fvo.fileName }</div>
									</div>
								</c:when>
								<c:otherwise>
									<!-- 일반 파일 : 다운로드 기능 추가 -->
									<a href="/upload/${fvo.saveDir }/${fvo.uuid}_${fvo.fileName}" download="${fvo.fileName }">
										<span class="fw-bold">${fvo.fileName }   </span>
										<svg xmlns="http://www.w3.org/2000/svg" width="35" height="35" fill="currentColor" class="bi bi-arrow-down-square" viewBox="0 0 16 16">
										  <path fill-rule="evenodd" d="M15 2a1 1 0 0 0-1-1H2a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1zM0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2zm8.5 2.5a.5.5 0 0 0-1 0v5.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293z"/>
										</svg>
									</a>	
								</c:otherwise>
							</c:choose> 
						</li>
					</c:forEach>
				</ul>
			</div>
			
			
			<a href="/board/modify?bno=${bvo.bno }"><button type="button" class="btn btn-primary">수정</button></a>
			<a href="/board/delete?bno=${bvo.bno }"><button type="button" class="btn btn-primary">삭제</button></a>
			
			<br>
			<hr>
			<br>
			<!-- Comment Line -->
			<!-- Comment Post -->
			<script type="text/javascript">
				let authNick = `<c:out value = "" />`
			</script>
			<sec:authorize access="isAuthenticated()">
				<div class="input-group mb-3">
				  <span class="input-group-text" id="cmtWriter">
						<sec:authentication property="principal.uvo.nickName" var="authNick"/>
					    ${authNick }
				  </span>
				  <input type="text" class="form-control" id="cmtText" placeholder="Add Comment...">
				  <button class="btn btn-outline-secondary" id="cmtAddBtn" type="button">등록</button>
				</div>
				<script type="text/javascript">
					authNick = `<c:out value = "${authNick }" />`
				</script>
			</sec:authorize>
			
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