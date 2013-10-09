package com.example.brokus;

public class BRRegistro {
private String id=null;
private String nombreUsuario=null;
private String Empresa=null;
private String Puesto=null;
private String Sector=null;
private String Password=null;
private String Email=null;
private byte[] Logo=null;
private String NoEmpresa=null;
	public BRRegistro(){
		super();
	}

	public void BRid(String BRide){
		this.id=BRide;
	}
	public void BRnombreUsuario(String BRUser){
		this.nombreUsuario=BRUser;
	}
	public void BREmpresa(String BRCompany){
		this.Empresa=BRCompany;
	}
	public void BRPuesto(String Position){
		this.Puesto=Position;
	}
	public void BRSector(String BRSec){
		this.Sector=BRSec;
	}
	public void BRPassword(String BRPass){
		this.Password=BRPass;
	}
	public void BREmail(String Correo){
		this.Email=Correo;
	}
	public void BRLogo(byte[] BRLogo){
		this.Logo=BRLogo;
	}
	public void BRNoEmpresa(String result){
		this.NoEmpresa=result;
	}
	public String GetId(){
		return id;
	}
	public String GetNombreUsuario(){
		return nombreUsuario;
	}
	public String GetEmpresa(){
		return Empresa;
	}
	public String GetPuesto(){
		return Puesto;
	}
	public String GetSector(){
		return Sector;
	}
	public String GetPassword(){
		return Password;
	}
	public String GetEmail(){
		return Email;
	}
	public byte[] GetLogo(){
		return Logo;
	}
	public String GetNoEmpresa(){
		return NoEmpresa;
	}
}