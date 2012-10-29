<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
    Document   : newjsp
    Created on : 25/10/2012, 09:10:49 PM
    Author     : Angel Alberici
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
  <form id="create-note-form" action="http://localhost:8080/SpringMVC/enviar.htm" method="POST">
    <fieldset>
      <legend>Creación de Nota</legend>
      <label id="title-frm-label" for ="htte-frm">
        Título
      </label>
      <input id="tittle-frm" name="titulo" type="text" VALUE="asdsd"/>
      <textarea maxlength="5" name="texto" value="asdasd" placeholder="Escriba aquí" name="texto" rows="10" cols="70"></textarea><br></br>
       Tags:
      <textarea maxlength="5" name="tag" rows="2" cols="70"></textarea><br></br>
      <button type="submit"> Crear</button>
    </fieldset>
</form>

    </body>
</html>
