<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1" session="true" import="com.ieee.seguridad.*"%>
<%@ page language="java" import="java.util.*"%>
<%@ page import="javax.servlet.annotation.MultipartConfig" %>
<%@ page import="javax.servlet.annotation.WebServlet" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="javax.servlet.http.Part" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStream" %>       
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="css/styles.css" rel="stylesheet" type="text/css">
	<title>IEEE CS - Registrarse</title>
</head>
<body>
	<header>
		<div align="center">
	        <a href="index.jsp"><img src="images/logo.png" width="400em" alt="Logo IEEE Computer Society" title="Logo IEEE Computer Society"></a>
	    </div>
	</header>
	<nav class="center-content">
		<ul>
        	<li><a href="informacion.jsp">Informaci�n</a></li>
        	<li><a href="eventos.jsp">Eventos</a></li>
        	<li><a href="registrarse.jsp">Registrarse</a></li>
        	<li><a href="desarrolladores.jsp">Desarrolladores</a></li>
        	<li><a href="login.jsp">Login</a></li>
      	</ul>
	</nav>
	<main>
		<hr>
		<section style="color: #0014B7; justify-content: center; display: flex;" class="normal">
			<p>
			<i>Avance de la tecnolog�a para el beneficio de la humanidad</i>
			<p>
		</section>
		<section style="color: black; justify-content: center; align-items: center;" class="normal">
			<h1>Registrarse</h1>
		</section>
		<div style="height: auto; border-radius: 10px;" class="center-content">
    				<%
		Usuario usuario=new Usuario();
		String nlogin=request.getParameter("correo");
		String nclave=request.getParameter("clave");
		HttpSession sesion=request.getSession(); //Se crea la variable de sesi�n
		boolean respuesta=usuario.verificarUsuario(nlogin,nclave);
		if (!respuesta)
		{
		String nombre=request.getParameter("nombre");
		String stredad = request.getParameter("edad");
		// Convertir el valor de edad a int
		int edad = Integer.parseInt(stredad);
		String directorio=request.getParameter("foto");
		usuario.ingresarUsuario(nlogin, nclave, nombre, edad, directorio, "", "");
		response.sendRedirect("login.jsp"); //Se redirecciona al login
		}
		else
		{
		%>
		<jsp:forward page="registrarse.jsp">
		<jsp:param name="error" value="Datos incorrectos.<br>Vuelva a intentarlo."/>
		</jsp:forward>
		<%
		}
		%>
		</div>
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