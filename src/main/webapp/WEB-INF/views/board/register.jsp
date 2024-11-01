<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<jsp:include page="../layout/header.jsp"></jsp:include>
	
		<div class="container-md">
			<h3>Board Register Page!!</h3>
			<hr>
			
			<!-- enctype : multipart/form-data -->
			<form action="/board/insert" method="post" enctype="multipart/form-data">
				<div class="mb-3">
				  <label for="t" class="form-label">title</label>
				  <input type="text" class="form-control" name="title" id="t" placeholder="title..">
				</div>
				<div class="mb-3">
				  <label for="w" class="form-label">writer</label>
				  <input type="text" class="form-control" name="writer" id="w" placeholder="writer..">
				</div>
				<div class="mb-3">
				  <label for="c" class="form-label">content</label>
				  <textarea class="form-control" name="content" id="c" rows="3"></textarea>
				</div>
				
				<!-- 첨부파일 입력 라인 추가 -->
				<div class="mb-3">
				  <input type="file" class="form-control" name="files" id="file" multiple="multiple" style="display:none">
				  <label class="input-group-text" for="file" style="display:none"></label>
			      <button type="button" class="btn btn-primary" id="trigger">파일 업로드</button>
				</div>
				<!-- 첨부파일 표시 라인 추가 -->
				<div class="mb-3" id="fileZone">
				
				</div>
				<button type="submit" class="btn btn-primary" id="regBtn">등록</button>
			</form>
		</div>
	
	<script type="text/javascript" src="/resources/js/boardRegister.js"></script>
	
	<jsp:include page="../layout/footer.jsp"></jsp:include>
