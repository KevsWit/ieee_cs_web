<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1" session="true" import="com.ieee.seguridad.*"%>
<!DOCTYPE html>
<%@ page language="java" import="javax.swing.ImageIcon"%>
<%@ page language="java" import="java.io.ByteArrayOutputStream"%>
<%@ page language="java" import="javax.imageio.ImageIO"%>
<%@ page language="java" import="java.util.Base64"%>
<%@ page language="java" import="java.awt.image.BufferedImage"%>
<%@ page language="java" import="javax.imageio.stream.MemoryCacheImageOutputStream"%>
<%@ page language="java" import="java.io.IOException"%>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="css/styles.css" rel="stylesheet" type="text/css">
	<title>IEEE CS - Perfil</title>
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
		<hr>
		<section style="color: #0014B7; justify-content: center; display: flex;" class="normal">
			<p>
			<i>Avance de la tecnología para el beneficio de la humanidad</i>
			<p>
		</section>
		<section style="color: black; justify-content: center; align-items: center;" class="normal">
			<h1>Ingresa al sitio</h1>
		</section>
		<div style="height: auto; border-radius: 10px;" >
    				<%
String usuario;
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
 %>
<h1>Sitio Privado de IEEE</h1>
<%
Pagina pag=new Pagina();
String menu=pag.mostrarMenu(perfil);
out.print(menu);
%>
<div style="height: auto;" class="center-content">
			<%
			Usuario usr = new Usuario();
			boolean resp = usr.obtenerUsuario(usuario);
			String dir;
			if (usr.getFoto()== null){
				dir="";
			}
			else{
				dir=usr.getFoto().getDescription();
			}
			if(dir==null){
				dir="";
			}
			System.out.print(dir);
			%>
    		<section class="section-l" style="color: black;">
      			<img src="<%= dir %>" width="300em">
    		</section>
			<aside style="color: black;">
				<h3 style="text-align: left;">Nombre</h3>
      			<div style="text-align: left;"><%out.print(usr.getNombre());%></div>
      			<h3 style="text-align: left;">Edad</h3>
      			<div style="text-align: left;"><%out.print(usr.getEdad());%></div>
      			<h3 style="text-align: left;">Correo</h3>
      			<div style="text-align: left;"><%out.print(usr.getNcorreo());%></div>
			</aside>
			
</div>
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