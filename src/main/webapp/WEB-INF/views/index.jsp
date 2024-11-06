<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<jsp:include page="layout/header.jsp"></jsp:include>
<div class="container-md">
	<P>The time on the server is ${serverTime}.</P>
	<img alt="" src="../resources/image/image2.jpg">
</div>

<script>
	let modify_msg = `<c:out value="${modify_msg}" />`;
	console.log(modify_msg);
	if(modify_msg == 'ok'){
		alert("회원 정보가 수정되었습니다. 다시 로그인해주세요.");
	}else if(modify_msg == 'fial'){
		alert("회원 정보가 수정에 실패했습니다. 다시 시도해주세요");		
	}
	
	let remove_msg = `<c:out value="${remove_msg}" />`;
	console.log(remove_msg);
	if(remove_msg == 'ok'){
		alert("회원 정보가 삭제되었습니다.");
	}else if(remove_msg == 'fial'){
		alert("회원 정보가 삭제에 실패했습니다. 다시 시도해주세요");	
	}
</script>
<jsp:include page="layout/footer.jsp"></jsp:include>