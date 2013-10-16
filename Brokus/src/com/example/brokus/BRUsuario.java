package com.example.brokus;

import android.graphics.Bitmap;

public class BRUsuario implements Comparable<BRUsuario>{
	private Integer id;	
	private String nombre;
	private String username;
	private String puesto;
	private Object logo;
	private String sector;
	private String contrasena;
	private String empresa;
	
	public BRUsuario() {
		super();
	}
	
	public BRUsuario(String nombre, String contrasena) {
		super();
		this.username = nombre;
		this.contrasena = contrasena;
	}

	public BRUsuario(String username) {
		super();
		this.username = username;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public Object getLogo() {
		return logo;
	}

	public void setLogo(Object logo) {
		this.logo = logo;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	@Override
	public String toString() {
		return "BRUsuario [id=" + id + ", nombre=" + nombre + ", username="
				+ username + ", puesto=" + puesto 
				+ ", sector=" + sector + ", contrasena=" + contrasena
				+ ", empresa=" + empresa +   ", logo=" + logo + "]";
	}
    

	@Override
	public int compareTo(BRUsuario arg0) {
		// TODO Auto-generated method stub
		return this.getNombre().compareToIgnoreCase(arg0.getNombre());
	}
	
	
	
	
}
