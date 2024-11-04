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
			<c:set value="${bdto.bvo }" var="bvo"></c:set>
			<c:set value="${bdto.flist }" var="flist"></c:set>
			<form action="/board/update" method="post" enctype="multipart/form-data">
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
			
			<!-- 파일 추가 -->
			<!-- 첨부파일 입력 라인 추가 -->
			<div class="mb-3">
				<input type="file" class="form-control" name="files" id="file" multiple="multiple" style="display:none">
				<label class="input-group-text" for="file" style="display:none"></label>
			    <button type="button" class="btn btn-primary" id="trigger">파일 업로드</button>
			</div>
			<!-- 첨부파일 표시 라인 추가 -->
			<div class="mb-3" id="fileZone"></div>
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
									<a href="/upload/${fvo.saveDir }/${fvo.uuid}_${fvo.fileName}" download>
										<span class="fw-bold">${fvo.fileName }   </span>
										<svg xmlns="http://www.w3.org/2000/svg" width="35" height="35" fill="currentColor" class="bi bi-arrow-down-square" viewBox="0 0 16 16">
										  <path fill-rule="evenodd" d="M15 2a1 1 0 0 0-1-1H2a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1zM0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2zm8.5 2.5a.5.5 0 0 0-1 0v5.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293z"/>
										</svg>
									</a>	
								</c:otherwise>
							</c:choose>
							<button type="button" class="btn btn-outline-dark file-x" data-uuid="${fvo.uuid }">x</button> 
						</li>
					</c:forEach>
				</ul>
			</div>
			
			<button type="submit" id="regBtn" class="btn btn-primary">완료</button>
			
			</form>
			<script type="text/javascript" src="/resources/js/boardModify.js"></script>
			<script type="text/javascript" src="/resources/js/boardRegister.js"></script>
		</div>
		
		<jsp:include page="../layout/footer.jsp"></jsp:include>
</body>
</html>