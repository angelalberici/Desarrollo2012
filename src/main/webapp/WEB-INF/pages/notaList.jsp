<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="refresh" content="600"> 
    </head>

    <body>
        <h1>Nota List</h1>

        <c:forEach items="${notaList}" var="nota">
            ID: ${nota.id} <br> 
            Titulo: ${nota.titulo} <br>
            Texto: ${nota.texto} <br>
           
            <br />
        </c:forEach>
        <input id="code" type="text" readonly="readonly" 
                     
               value="4/rRO6HdSinBAnp2t21SVW4f3sn0Oi.8o9v2d_rJtgXshQV0ieZDAqG9hSjdAI" 
               style="width:300px" 
               onclick="this.focus();this.select();_gaq.push(['_trackEvent', 'OOB Result', 'Access Granted', 'Code Selected']);">

        <FORM method="POST" ACTION="test1.htm">
            Select your favorite sport(s): <br><br>
            <table>
                <tr>
                    <td> 
                        <input TYPE=checkbox name=sports VALUE=Cricket>
                    </td>    
                    <td>    
                        Cricket 
                    </td>
                </tr>

                <tr>
                    <td> 
                        <input TYPE=checkbox name=sports VALUE=Football>
                    </td>    
                    <td>    
                        Football
                    </td>
                </tr>

                <tr>
                    <td> 
                        <input TYPE=checkbox name=sports VALUE=Tennis>
                    </td>    

                    <td>    
                        Tennis
                    </td>
                </tr>

                <tr>
                    <td> 
                        <input TYPE=checkbox name=sports VALUE=Rugby>
                    </td>    

                    <td>    
                        Rugby
                    </td>
                </tr>

                <tr>
                    <td> 
                        <input TYPE=checkbox name=sports VALUE=Basketball>
                    </td>    

                    <td>    
                        Basketball
                    </td>
                </tr>
            </table>


            <br> <INPUT TYPE=submit name=submit Value="Submit">

        </form>
        
        
        
        <%! String[] sports;%>
    <center>You have selected: 
        <%
        
        
            sports = request.getParameterValues("sports");
            if (sports != null) {
                for (int i = 0; i < sports.length; i++) {
                    out.println("<b>" + sports[i] + "<b>");
                }
            } else {
                out.println("<b>none<b>");
            }
        %>

    </center>


</body>

</html>