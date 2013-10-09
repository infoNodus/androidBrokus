package com.example.brokus;

public class BREmpresa {

	private int id;
	private String nombre;
	private String sector;
	
	
	
	public BREmpresa(int id, String nombre, String sector){
		this.id = id;
		this.nombre = nombre;
		this.sector = sector;
	}
	
	public BREmpresa() {
		// TODO Auto-generated constructor stub
	}

	public void setId(int id){
		this.id = id;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public void setSector(String sector){
		this.sector = sector;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public String getSector(){
		return this.sector;
	}
	
}
