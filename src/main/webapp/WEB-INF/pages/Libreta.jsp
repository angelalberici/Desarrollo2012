<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
     <body>
          <h1>Libretas</h1>
         <c:forEach items="${LibretaList}" var="libreta">
            Titulo: ${libreta.nombre} <br>
            <br />
        </c:forEach>
    </body>
</html>
