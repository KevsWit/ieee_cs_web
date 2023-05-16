package com.ieee.seguridad;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.io.File;
import java.io.FileInputStream;

import com.ieee.datos.*;

public class Usuario {

	private String ncorreo;
	private String nclave;
	private String nombre;
	private Integer edad;
	private ImageIcon foto;
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
	public ImageIcon getFoto() {
		return foto;
	}
	public void setFoto(ImageIcon foto) {
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
			this.setFoto(null);
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
		if(rs.getBinaryStream(5) != null) {
			InputStream is = rs.getBinaryStream(5);
			BufferedImage bi = ImageIO.read(is);
			ImageIcon imagen = new ImageIcon(bi);
			this.setFoto(imagen);
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
				File fichero=new File("C:\\ieee\\"+directorio);
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
		if(rs.getBinaryStream(5) != null) {
			InputStream is = rs.getBinaryStream(5);
			BufferedImage bi = ImageIO.read(is);
			ImageIcon imagen = new ImageIcon(bi);
			this.setFoto(imagen);
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


}
