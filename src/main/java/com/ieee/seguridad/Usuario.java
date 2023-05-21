package com.ieee.seguridad;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import com.ieee.datos.*;
import com.ieee.eventos.Evento;

public class Usuario {

	private String ncorreo;
	private String nclave;
	private String nombre;
	private Integer edad;
	private String foto;
	private String github;
	private String linkedIn;
	private Integer perfil;

		public String getNcorreo() {
		return ncorreo;
	}
	public void setNcorreo(String ncorreo) {
		this.ncorreo = ncorreo;
	}
	public String getNclave() {
		return nclave;
	}
	public void setNclave(String nclave) {
		this.nclave = nclave;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getEdad() {
		return edad;
	}
	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	public String getGithub() {
		return github;
	}
	public void setGithub(String github) {
		this.github = github;
	}
	public String getLinkedIn() {
		return linkedIn;
	}
	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}
	public Integer getPerfil() {
		return perfil;
	}
	public void setPerfil(Integer perfil) {
		this.perfil = perfil;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}

		public Usuario() {
			this.setNcorreo("");
			this.setNclave("");
			this.setNombre("");
			this.setEdad(0);
			this.setGithub("");
			this.setLinkedIn("");
			this.setPerfil(0);
			this.setFoto("");
		}
		public boolean verificarUsuario(String ncorreo, String nclave)
		{
		boolean respuesta=false;
		String sentencia= "Select * from tb_usuario where correo='"+ncorreo+
		"' and clave='"+nclave+"';";
		//System.out.print(sentencia);
		try
		{
		ResultSet rs;
		Conexion clsCon=new Conexion();
		rs=clsCon.Consulta(sentencia);
		if(rs.next())
		{
		respuesta=true;
		this.setNcorreo(ncorreo);
		this.setNclave(nclave);
		this.setPerfil(rs.getInt(8));
		this.setNombre(rs.getString(3));
		this.setEdad(rs.getInt(4));
		InputStream imagenStream = rs.getBinaryStream(5);
	    if (imagenStream != null) {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        byte[] buffer = new byte[4096];
	        int bytesRead;
	        while ((bytesRead = imagenStream.read(buffer)) != -1) {
	            baos.write(buffer, 0, bytesRead);
	        }
	        byte[] imagenBytes = baos.toByteArray();
	        String imagenBase64 = Base64.getEncoder().encodeToString(imagenBytes);
	        this.setFoto(imagenBase64);
	    }
		if(rs.getString(6) != null)
			this.setGithub(rs.getString(6));
		if(rs.getString(7) != null)
			this.setLinkedIn(rs.getString(7));
		}
		else
		{
		respuesta=false;
		rs.close();
		}
		}
		catch(Exception ex)
		{
		System.out.println( ex.getMessage());
		}
		return respuesta;
		}
		public String ingresarUsuario(String correo, String clave, String nombre, int edad, String directorio, String github, String linkedin)
				{
				String result="";
				Conexion con=new Conexion();
				PreparedStatement pr=null;
				String sql="INSERT INTO public.tb_usuario("
						+ "	correo, clave, nombre, edad, foto, github, \"linkedIn\", id_per)"
						+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
				try{
				pr=con.getConexion().prepareStatement(sql);
				pr.setString(1, correo);
				pr.setString(2, clave);
				pr.setString(3, nombre);
				pr.setInt(4, edad);
				File fichero=new File(directorio);
				FileInputStream streamEntrada=new FileInputStream(fichero);
				pr.setBinaryStream(5, streamEntrada,(int)fichero.length());
				pr.setString(6, github);
				pr.setString(7, linkedin);
				pr.setInt(8, 2);
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
		public boolean obtenerUsuario(String ncorreo)
		{
		boolean respuesta=false;
		String sentencia= "Select * from tb_usuario where correo='"+ncorreo+
		"';";
		//System.out.print(sentencia);
		try
		{
		ResultSet rs;
		Conexion clsCon=new Conexion();
		rs=clsCon.Consulta(sentencia);
		if(rs.next())
		{
		respuesta=true;
		this.setNcorreo(ncorreo);
		this.setNclave(nclave);
		this.setPerfil(rs.getInt(8));
		this.setNombre(rs.getString(3));
		this.setEdad(rs.getInt(4));
		InputStream imagenStream = rs.getBinaryStream(5);
	    if (imagenStream != null) {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        byte[] buffer = new byte[4096];
	        int bytesRead;
	        while ((bytesRead = imagenStream.read(buffer)) != -1) {
	            baos.write(buffer, 0, bytesRead);
	        }
	        byte[] imagenBytes = baos.toByteArray();
	        String imagenBase64 = Base64.getEncoder().encodeToString(imagenBytes);
	        this.setFoto(imagenBase64);
	    }
		if(rs.getString(6) != null)
			this.setGithub(rs.getString(6));
		if(rs.getString(7) != null)
			this.setLinkedIn(rs.getString(7));
		}
		else
		{
		respuesta=false;
		rs.close();
		}
		}
		catch(Exception ex)
		{
		System.out.println( ex.getMessage());
		}
		return respuesta;
		}
		
		public String ingresarPostulacion(String correo, String info, String directorio, String estado)
		{
		String result="";
		Conexion con=new Conexion();
		PreparedStatement pr=null;
		String sql="INSERT INTO public.\"tb_infoPostulacion\"("
				+ "	correo, info, cv, estado)"
				+ "	VALUES (?, ?, ?, ?);";
		try{
		pr=con.getConexion().prepareStatement(sql);
		pr.setString(1, correo);
		pr.setString(2, info);
		File fichero=new File(directorio);
		FileInputStream streamEntrada=new FileInputStream(fichero);
		pr.setBinaryStream(3, streamEntrada,(int)fichero.length());
		pr.setString(4, estado);
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
		public String consultarPostulaciones() {
			String tabla = "<table border=1>";
			String sql = "SELECT correo, info, cv"
					+ "	FROM public.\"tb_infoPostulacion\""
					+ "	WHERE estado LIKE '%Seleccionada%';";
			ResultSet rs = null;
			tabla += "<tr>"
					+ "<th>Correo</th>"
					+ "<th>Información</th>"
					+ "<th>CV</th>"
					+ "</tr>";
			Conexion con=new Conexion();
			
				rs=con.Consulta(sql);
			try {
				while(rs.next()) {
					tabla += "<tr>"
							+ "<td><pre style=\"text-align: center\">"+rs.getString(1)+"</pre></td>"
							+ "<td><p style=\"text-align: left\">"+rs.getString(2)+"</p></td>"
							+ "<td><a href= descargar.jsp?cod="+rs.getString(1)+"><pre style=\"text-align: center\">DESCARGAR</pre></a></td>"
							+ "<td><a href= aceptar.jsp?cod="+rs.getString(1)+"><pre style=\"text-align: center\">ACEPTAR</pre></a></td>"
							+ "<td><a href= rechazar.jsp?cod="+rs.getString(1)+"><pre style=\"text-align: center\">RECHAZAR</pre></a></td>"
							+ "</tr>";
				}
				tabla += "</table>";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.print(e.getMessage());
			}
			return tabla;
		}
		public boolean aceptarPostulacion(String correo) {
			boolean agregado = false;
			Conexion con=new Conexion();
			String sql = "UPDATE public.\"tb_infoPostulacion\""
					+ "	SET estado='Aceptada'"
					+ "	WHERE correo LIKE '%"+correo+"';";

			try {
				con.Ejecutar(sql);
				agregado = true;
			}catch (Exception e) {
				// TODO: handle exception
				agregado = false;
			}
			return agregado;
		}
		public boolean rechazarPostulacion(String correo) {
			boolean agregado = false;
			Conexion con=new Conexion();
			String sql = "UPDATE public.\"tb_infoPostulacion\""
					+ "	SET estado='Rechazada'"
					+ "	WHERE correo LIKE '%"+correo+"';";

			try {
				con.Ejecutar(sql);
				agregado = true;
			}catch (Exception e) {
				// TODO: handle exception
				agregado = false;
			}
			return agregado;
		}
		public String consultarEstado(String correo) {
			Conexion con=new Conexion();
			ResultSet rs = null;
			String sql = "SELECT estado"
					+ "	FROM public.\"tb_infoPostulacion\""
					+ "	WHERE correo LIKE '%"+correo+"';";
			String resp="";
				rs=con.Consulta(sql);
				try {
				while(rs.next()) {
					resp="Tu solicitud de pertenecer al grupo ha sido: " + rs.getString(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp="No ha realizado ninguna solicitud";
			}
			return resp;
				
		}
		public int membresiaMID() {
			String sql="SELECT MAX(id_membresia) FROM tb_membresia;";
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
		public String ingresarMembresia(int id_membresia, String descripcion, double costo_sm, double costo_ssm)
		{
		String result="";
		Conexion con=new Conexion();
		PreparedStatement pr=null;
		String sql="INSERT INTO public.tb_membresia("
				+ "	id_membresia, descripcion, costo_sm, costo_ssm)"
				+ "	VALUES (?, ?, ?, ?);";
		try{
		pr=con.getConexion().prepareStatement(sql);
		pr.setInt(1, id_membresia);
		pr.setString(2, descripcion);
		pr.setDouble(3, costo_sm);
		pr.setDouble(4, costo_ssm);
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
		public String consultarMembresia()
		{
			String sql="SELECT descripcion, costo_sm, costo_ssm"
					+ "	FROM public.tb_membresia;";
			Conexion con=new Conexion();
			String tabla="<table border=2><th>Membresía</th><th>Costo Society Member</th><th>Costo Society Student Member</th>";
			ResultSet rs=null;
			rs=con.Consulta(sql);
			try {
				while(rs.next())
				{
					tabla+="<tr><td>"+rs.getString(1)+"</td>"
					+ "<td>"+rs.getDouble(2)+"</td>"
					+ "<td>"+rs.getDouble(3)+"</td>"
					+ "</td></tr>";
				}
			} catch (SQLException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
				//System.out.print(e.getMessage());
			}
			tabla+="</table>";
			return tabla;
		}
		public byte[]obtenerCV(String correo){
			String sql="SELECT cv FROM public.\"tb_infoPostulacion\" WHERE correo LIKE '%"+correo+"';";
			Conexion con=new Conexion();
			byte[] arch = null;
			ResultSet rs=null;
			rs=con.Consulta(sql);
			try {
				while(rs.next()) {
					arch=rs.getBytes(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return arch;
		}
		public String obtenerClave(String correo) {
			String sql="SELECT clave FROM public.tb_usuario WHERE correo LIKE '%"+correo+"';";
			Conexion con=new Conexion();
			String pwd = "";
			ResultSet rs=null;
			rs=con.Consulta(sql);
			try {
				while(rs.next()) {
					pwd=rs.getString(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return pwd;
		}
		public boolean cambiarClave(String correo, String newpwd) {
			boolean agregado = false;
			Conexion con=new Conexion();
			String sql = "UPDATE public.tb_usuario"
					+ "	SET clave='"+newpwd+"'"
					+ "	WHERE correo LIKE '%"+correo+"';";

			try {
				con.Ejecutar(sql);
				agregado = true;
			}catch (Exception e) {
				// TODO: handle exception
				agregado = false;
			}
			return agregado;
		}
		
}
