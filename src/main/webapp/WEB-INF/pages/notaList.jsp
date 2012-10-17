<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <body>
        <h1>Nota List</h1>

        <c:forEach items="${notaList}" var="nota">
            ID: ${nota.id} <br> 
            Titulo: ${nota.titulo} <br>
            Texto: ${nota.texto} <br>
            <br />
        </c:forEach>



    </body>
</html>