package com.ieee.eventos;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ieee.datos.*;

public class Tipo {
	private int id_tipo;
	private String nombre;
	
	public int getId_tipo() {
		return id_tipo;
	}

	public void setId_tipo(int id_tipo) {
		this.id_tipo = id_tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Tipo() {
		this.setId_tipo(0);
		this.setNombre("");
	}
	public String mostrarTipo()
	{
		String combo="<select name=cmbTipo>";
		String sql="SELECT * FROM tb_tipo";
		ResultSet rs=null;
		Conexion con=new Conexion();
		try
		{
			rs=con.Consulta(sql);
			while(rs.next())
			{
				combo+="<option value="+rs.getInt(1)+ ">"+rs.getString(2)+"</option>";
			}
			combo+="</select>";
		}
		catch(SQLException e)
		{
			//System.out.print(e.getMessage());
		}
		return combo;
	}

}
