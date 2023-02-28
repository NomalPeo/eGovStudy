<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<table border="1" style="border-collapse: collapse;">
		<tr>
			<th>제목</th>
			<th>작성자</th>
			<th>작성날</th>
		</tr>
		<c:if test="${empty list1 }">
			<tr>
				<td colspan="4">글없</td>
			</tr>
		</c:if>
		<c:if test="${!empty list1 }">
			<c:forEach var="list" items="${list1 }">
			<tr>
				<td><a href="/main/content.do?title=${list.title }"><c:out value="${list.title }"/></a></td>
				<td><c:out value="${list.writer }"/></td>
<%-- 				<td>${list.content }</td>--%>				
				<td>${fn:substring(list.regdate ,0,10)}</td>
			</tr>
			</c:forEach>	
		</c:if>
		<tr>
			<td><button type="button" onclick="location='/main/write.do';">글작성</button></td>
			<td colspan="3"></td>
		</tr>
	</table>
</body>
</html>