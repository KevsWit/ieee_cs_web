package com.ieee.eventos;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import com.ieee.datos.*;

public class Evento {
	private int id_evento;
	private String descripcion;
	private int id_tipo;
	
	public int getId_evento() {
		return id_evento;
	}

	public void setId_evento(int id_evento) {
		this.id_evento = id_evento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getId_tipo() {
		return id_tipo;
	}

	public void setId_tipo(int id_tipo) {
		this.id_tipo = id_tipo;
	}

	public Evento() {
		this.setId_evento(0);
		this.setDescripcion("");
		this.setId_tipo(0);
	}
	public String consultarTodo()
	{
		String sql="SELECT id_evento, descripcion FROM tb_evento ORDER BY id_evento";
		Conexion con=new Conexion();
		String contenedor="";
		ResultSet rs=null;
		rs=con.Consulta(sql);
		try {
			while(rs.next())
			{
				contenedor+="<div style=\"height: auto; border-radius: 10px; border: 1px solid grey;\" class=\"center-content\">"
						+ "    		<section class=\"section-l\" style=\"color: black;\">"
						+ "      			<div id=\"carouselExampleControls\" class=\"carousel slide\" data-bs-ride=\"carousel\">"
						+ "				  <div class=\"carousel-inner\">";
				contenedor+=consultarImagenes(rs.getInt(1));
				contenedor+="</div>"
						+ "				  <button class=\"carousel-control-prev\" type=\"button\" data-bs-target=\"#carouselExampleControls\" data-bs-slide=\"prev\">"
						+ "				    <span class=\"carousel-control-prev-icon\" aria-hidden=\"true\"></span>"
						+ "				    <span class=\"visually-hidden\">Previous</span>"
						+ "				  </button>"
						+ "				  <button class=\"carousel-control-next\" type=\"button\" data-bs-target=\"#carouselExampleControls\" data-bs-slide=\"next\">"
						+ "				    <span class=\"carousel-control-next-icon\" aria-hidden=\"true\"></span>"
						+ "				    <span class=\"visually-hidden\">Next</span>"
						+ "				  </button>"
						+ "				</div>"
						+ "    		</section>"
						+ "			<aside style=\"color: black; border-left: 3px solid #3B0E7B;\">"
						+ "      			<p style=\"text-align: left\">";
				contenedor+=rs.getString(2);
				contenedor+= "      			</p>"
						+ "			</aside>"
						+ "		</div>";
			}
		} catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.print(e.getMessage());
		}
		return contenedor;
	}
	public String consultarImagenes(int id) {
		String sql="SELECT imagen, alt"
				+ "	FROM tb_galeria"
				+ "	WHERE id_evento="+id+";";
		Conexion con=new Conexion();
		String contenedor="";
		ResultSet rs=null;
		rs=con.Consulta(sql);
		try {
			while(rs.next())
			{
				contenedor+="<div class=\"carousel-item active\">";
				InputStream imagenStream = rs.getBinaryStream(1);
			    if (imagenStream != null) {
			        ByteArrayOutputStream baos = new ByteArrayOutputStream();
			        byte[] buffer = new byte[4096];
			        int bytesRead;
			        try {
						while ((bytesRead = imagenStream.read(buffer)) != -1) {
						    baos.write(buffer, 0, bytesRead);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        byte[] imagenBytes = baos.toByteArray();
			        String imagenBase64 = Base64.getEncoder().encodeToString(imagenBytes);
			        contenedor+="<img src=\"data:image/jpeg;base64, "+imagenBase64+"\" class=\"d-block w-100\" width=\"300em\" alt=\""+rs.getString(2)+"\">";
			    }
				contenedor+="</div>";
			}
		} catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.print(e.getMessage());
		}
		return contenedor;
	}
	public int maxID() {
		String sql="SELECT MAX(id_evento) FROM tb_evento;";
		Conexion con=new Conexion();
		int id = 0;
		ResultSet rs=null;
		rs=con.Consulta(sql);
		try {
			while(rs.next()) {
				id=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	public String ingresarEvento(int id_eve, String descripcion, int id_tipo)
	{
	String result="";
	Conexion con=new Conexion();
	PreparedStatement pr=null;
	String sql="INSERT INTO public.tb_evento("
			+ "	id_evento, descripcion, id_tipo)"
			+ "	VALUES (?, ?, ?);";
	try{
	pr=con.getConexion().prepareStatement(sql);
	pr.setInt(1, id_eve);
	pr.setString(2, descripcion);
	pr.setInt(3, id_tipo);
	if(pr.executeUpdate()==1)
	{
	result="Inserción correcta";
	}
	else
	{
	result="Error en inserción";
	}
	}
	catch(Exception ex)
	{
		result=ex.getMessage();
		}
		finally
		{
		try
		{
		pr.close();
		con.getConexion().close();
		}
		catch(Exception ex)
		{
		System.out.print(ex.getMessage());
		}
		}
		return result;
		}
	public String consultarEvento(int cod) {
		String tabla = "<table border=1>";
		String sql = "SELECT id_evento, descripcion FROM tb_evento WHERE id_tipo = "+cod+";";
		ResultSet rs = null;
		tabla += "<tr>"
				+ "<th>Codigo</th>"
				+ "<th>Descripcion</th>"
				+ "</tr>";
		Conexion con=new Conexion();
		
			rs=con.Consulta(sql);
		try {
			while(rs.next()) {
				tabla += "<tr>"
						+ "<td><pre style=\"text-align: center\">"+rs.getInt(1)+"</pre></td>"
						+ "<td><pre style=\"text-align: center\">"+rs.getString(2)+"</pre></td>"
						+ "<td><a href= buscarEvento.jsp?cod="+rs.getInt(1)+"><pre style=\"text-align: center\">Modificar</pre></a></td>"
						+ "<td><a href= eliminarEvento.jsp?cod="+rs.getInt(1)+"><pre style=\"text-align: center\">Eliminar</pre></a></td>"
						+ "<td><a href= agregarFoto.jsp?cod="+rs.getInt(1)+"><pre style=\"text-align: center\">Agregar Foto</pre></a></td>"
						+ "</tr>";
			}
			tabla += "</table>";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.print(e.getMessage());
		}
		return tabla;
	}
	public void ConsulEditarProductos(int cod) {
		Conexion con=new Conexion();
		ResultSet rs = null;
		String sql = "SELECT id_evento, descripcion, id_tipo"
				+ "	FROM tb_evento WHERE id_evento="+cod+";";
		
			rs=con.Consulta(sql);
			try {
			while(rs.next()) {
				setId_evento(rs.getInt(1));
				setDescripcion(rs.getString(2));
				setId_tipo(rs.getInt(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean ModificarEvento(Evento mp) {
		boolean agregado = false;
		Conexion con=new Conexion();
		String sql = "UPDATE tb_evento SET descripcion='" + mp.getDescripcion() + "' WHERE id_evento=" + mp.getId_evento() + ";";

		try {
			con.Ejecutar(sql);
			agregado = true;
		}catch (Exception e) {
			// TODO: handle exception
			agregado = false;
		}
		return agregado;
	}
	public boolean EliminarEvento(int cod) {
		boolean f = false;
		Conexion con=new Conexion();
		String sql = "delete from tb_evento where id_evento=" + cod + ";";
		try {
			con.Ejecutar(sql);
			f = true;
		}catch (Exception e) {
			// TODO: handle exception
			f = false;
		}
		return f;
	}
	public boolean EliminarEvento1(int cod) {
		boolean f = false;
		Conexion con=new Conexion();
		String sql = "delete from tb_galeria where id_evento=" + cod + ";";
		try {
			con.Ejecutar(sql);
			f = true;
		}catch (Exception e) {
			// TODO: handle exception
			f = false;
		}
		return f;
	}
	public String ingresarFoto(int id_eve, String directorio, String alt)
	{
	String result="";
	Conexion con=new Conexion();
	PreparedStatement pr=null;
	String sql="INSERT INTO public.tb_galeria("
			+ "	id_evento, imagen, alt)"
			+ "	VALUES (?, ?, ?);";
	try{
	pr=con.getConexion().prepareStatement(sql);
	pr.setInt(1, id_eve);
	File fichero=new File(directorio);
	FileInputStream streamEntrada=new FileInputStream(fichero);
	pr.setBinaryStream(2, streamEntrada,(int)fichero.length());
	pr.setString(3, alt);
	if(pr.executeUpdate()==1)
	{
	result="Inserción correcta";
	}
	else
	{
	result="Error en inserción";
	}
	}
	catch(Exception ex)
	{
		result=ex.getMessage();
		}
		finally
		{
		try
		{
		pr.close();
		con.getConexion().close();
		}
		catch(Exception ex)
		{
		System.out.print(ex.getMessage());
		}
		}
		return result;
		}
}
