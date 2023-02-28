<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/ckeditor/ckeditor.js"></script>
<title>Insert title here</title>
</head>
<body>
	<form action="/main/editSave.do" method="post">
	<input type="hidden" name="seq" value="${list1.seq }">
		<div class="mb-3" style="width: 50%; margin: 0 auto;">
			<div>제목&nbsp;
			<input type="text" name="title" value="${list1.title}">
			</div><br>
			<textarea class="form-control " name="content" id="editor4">${list1.content }</textarea>
		</div>
		<button type="submit" >저장ㄱ</button>
	</form>
	<script>
		CKEDITOR.replace('editor4',{
			filebrowserUploadUrl:"fileuploads.do",
				height  : '800px',
				width 	: '1000px'
			});
	</script>
</body>
</html>