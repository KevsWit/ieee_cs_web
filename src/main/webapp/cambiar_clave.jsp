<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1" session="true" import="com.ieee.seguridad.*"%>
 
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="css/styles.css" rel="stylesheet" type="text/css">
	<title>IEEE CS -Cambio de clave</title>
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
 Pagina pag=new Pagina();
 String menu=pag.mostrarMenu(perfil);
 out.print(menu);
 }
 %>
		<hr>
		<section style="color: #0014B7; justify-content: center; display: flex;" class="normal">
			<p>
			<i>Avance de la tecnología para el beneficio de la humanidad</i>
			<p>
		</section>
		<section style="color: black; justify-content: center; align-items: center;" class="normal">
			<h1>Cambiar clave</h1>
		</section>
		<div style="height: auto; border-radius: 10px;" class="center-content">
    		<form action="respuestaClave.jsp" method="post">
			    <table>
			      <tr>
			        <td><label for="Clave Vieja">Clave Vieja:</label></td>
			        <td><input type="password" name="pwd_old" required="required">*</td>
			      </tr>
			      <tr>
			        <td><label for="Clave Nueva">Clave Nueva:</label></td>
			        <td><input type="password" name="pwd_new" required="required">*</td>
			      </tr>
			      <tr>
			        <td><label for="Confirmar Clave">Confirmar Clave:</label></td>
			        <td><input type="password" name="pwd_newConf" required="required">*</td>
			      </tr>
			      <tr>
			        <td><input type="submit"></td>
			        <td><input type="reset"></td>
			      </tr>
			    </table>
			    <h3 style="text-align: center;">*Campo obligatorio</h3>
			</form>
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