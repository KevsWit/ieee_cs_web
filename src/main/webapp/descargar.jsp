<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1" session="true" import="com.ieee.seguridad.*"%>
 <%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="java.nio.file.Path" %>
<%@ page import="java.nio.file.StandardCopyOption" %>
<%@ page import="java.io.InputStream" %>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="css/styles.css" rel="stylesheet" type="text/css">
	<title>IEEE CS -Gestión de postulaciones</title>
</head>
<body>
	<header>
		<div align="center">
	        <a href="index.jsp"><img src="images/logo.png" width="400em" alt="Logo IEEE Computer Society" title="Logo IEEE Computer Society"></a>
	    </div>
	</header>
	<nav class="center-content">
		<ul>
        	<li><a href="informacion.jsp">Información</a></li>
        	<li><a href="eventos.jsp">Eventos</a></li>
        	<li><a href="registrarse.jsp">Registrarse</a></li>
        	<li><a href="desarrolladores.jsp">Desarrolladores</a></li>
        	<li><a href="login.jsp">Login</a></li>
      	</ul>
	</nav>
	<main>
		    				<%
String usuario="";
HttpSession sesion = request.getSession();
 if (sesion.getAttribute("usuario") == null) //Se verifica si existe la variable
 {
 %>
 <jsp:forward page="login.jsp">
 <jsp:param name="error" value="Debe registrarse en el sistema."/>
 </jsp:forward>
 <%
 }
 else
 {
 usuario=(String)sesion.getAttribute("usuario"); //Se devuelve los valores de atributos
 int perfil=(Integer)sesion.getAttribute("perfil");
 }
 %>
		<hr>
		<section style="color: #0014B7; justify-content: center; display: flex;" class="normal">
			<p>
			<i>Avance de la tecnología para el beneficio de la humanidad</i>
			<p>
		</section>
		<section style="color: black; justify-content: center; align-items: center;" class="normal">
			<h1>Descargar CV</h1>
		</section>
		<section style="color: black; justify-content: center; align-items: center;" class="normal">
			<%
				String correo = request.getParameter("cod");
				Usuario usr = new Usuario();
				byte[] byteaData = usr.obtenerCV(correo);
			%>
<% String fileName = correo+"_CV.pdf"; %>
<% Path tempFilePath = Files.createTempFile("temp-", ".pdf"); %>
<% Files.write(tempFilePath, byteaData); %>
<% response.setContentType("application/pdf"); %>
<% response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\""); %>
<% try (InputStream inputStream = new FileInputStream(tempFilePath.toFile());
       OutputStream outputStream = response.getOutputStream()) {
       int bytesRead;
       byte[] buffer = new byte[4096];
       while ((bytesRead = inputStream.read(buffer)) != -1) {
           outputStream.write(buffer, 0, bytesRead);
       }
   } catch (IOException e) {
       e.printStackTrace();
   }
%>

<% Files.delete(tempFilePath); %>
		</section>
	</main>
	<footer style="margin-top: 15px;">
	  	<ul style="justify-content: center; display: flex;">
			<li><a href="https://www.facebook.com/people/IEEE-Computer-Society-UPS-Quito/100079992650736/" class="enlace-footer">Facebook</a></li>
			<li><a href="https://twitter.com/CSocietyUio" class="enlace-footer">Twitter</a></li>
		</ul>
	    <p>&copy; Copyright 2023 IEEE CS - All rights reserved.</p>
  	</footer>
</body>
</html>