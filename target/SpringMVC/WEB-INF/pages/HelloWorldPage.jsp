<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<html>
	<body>
		<form action="sample_posteddata.jsp" method="get">
			<p>
				<label for="editor1">Editor 1:</label>
				<textarea cols="80" id="editor1" name="editor1" rows="10"></textarea>
			</p>
			<p>
				<input type="submit" value="Submit" />
			</p>
		</form>
	<ckeditor:replace replace="editor1" basePath="/ckeditor/" />
	</body>	
</html>