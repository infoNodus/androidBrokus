package com.example.brokus;

public class BRComentario {
private int id=0;
private String descComentario=null;
private int idPublicacion=0;
private int idUsuario=0;
private String nombreUsuario=null;
	public void getIdComentario(int idComent){
		this.id=idComent;
	}
	public void getDescripcion(String Desc){
		this.descComentario=Desc;
	}
	public void getIduser(int idUser){
		this.idUsuario=idUser;
	}
	public void getIdPublicacion(int idPublic){
		this.idPublicacion=idPublic;
	}
	public void getNombre(String username){
		this.nombreUsuario=username;
	}
	public int setIdComentario(){
		return id;
	}
	public String setDescripcion(){
		return descComentario;
	}
	public int setIdUsuario(){
		return idUsuario;
	}
	public int setIdPublicacion(){
		return idPublicacion;
	}
	public String setNombre(){
		return nombreUsuario;
	}
}
