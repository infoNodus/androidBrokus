package com.example.brokus;
import AsyncTasks.*;
import AsyncTasks.RESTClient.RequestMethod;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

public class BRDataSource {
	SharedPreferences preferencias;
	Context c;
	AsyncTasks.InsertRegister GetData;
	RESTClient request;
    BRUsuario usuario;
	public BRDataSource() {
		super();
	}
	

	public BRDataSource(Context c) {
		super();
		this.c = c;
	}


	public BRDataSource(SharedPreferences preferencias) {
		super();
		this.preferencias = preferencias;
		
	}
	
	/*public void saveUsuario(Usuario u){
		//Prueba crear usuario en banco de datos
		SharedPreferences pref = c.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
		Editor edit = pref.edit();
		edit.putString(u.getUsername(), u.getContrasena());
		
		edit.commit();
	}*/
	
	
	public void SaveRegister(BRRegistro Registro){
		Editor editor=preferencias.edit();
		editor.putString("idusuario", Registro.GetId().toString());
		editor.putString("nombreUsuario", Registro.GetNombreUsuario().toString());
		editor.putString("empresaUsuario", Registro.GetEmpresa().toString());
		editor.putString("correoUsuario", Registro.GetEmail().toString());
		editor.putString("passwordUsuario", Registro.GetPassword().toString());
		editor.putString("puestoUsuario", Registro.GetPuesto().toString());
		editor.putString("sectorUsuario", Registro.GetSector().toString());
		editor.putString("logoUsuario", Registro.GetLogo().toString());
	}
	public void getCompanies(){
		AsyncTasks.GetEmpresaAsyncTask ObtenerEmpresas=new AsyncTasks.GetEmpresaAsyncTask();
		ObtenerEmpresas.execute();
	}
/*	public ArrayList<Usuario> getUsuarios(){
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		preferencias = c.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
		Map<String, ?> mapUsuarios = preferencias.getAll();
		for(Entry<String, ?> keys : mapUsuarios.entrySet()){
			
			
			Usuario u = new Usuario();
			u.setUsername(keys.getKey());
			u.setContrasena(keys.getValue().toString());
			lista.add(u);
			Log.i("Usuario n: " ,u.toString());
		}
		return lista;
	}*/
	public ArrayList<BRPublicacion> getPublicacionesByUserId(final int id){
		final ArrayList<BRPublicacion> publicaciones = new ArrayList<BRPublicacion>();
		
		Thread hilo = new Thread(new Runnable(){
			@Override
			public void run() {				
				request = new RESTClient("http://192.168.1.24/brokus/getPublicacion.php");
				 try{
					 request.AddParam("idUsuario", String.valueOf(id));					
					 request.Execute(RequestMethod.GET);
					 Log.i("response", ""+request.getResponse());				 
					 JSONObject json = new JSONObject(request.getResponse());
				     BRPublicacion pub;
						 JSONArray jarray = json.getJSONArray("publicaciones");
				    		for(int i = 0; i < jarray.length(); i++){
				    			pub = new BRPublicacion();
				    			JSONObject jobj = jarray.getJSONObject(i);
				    			pub.setId(jobj.getInt("idpublicacion"));
				    			pub.setTitulo(jobj.getString("titulo"));
				    			pub.setContenido(jobj.getString("contenido"));
				    			pub.setAnexo(jobj.getString("anexo"));
				    			pub.setFechaCaducidad((java.sql.Date)jobj.get("fecha"));
				    			pub.setIdUsuario(jobj.getInt("idUsuario"));
				    			publicaciones.add(pub);
				    		}
				    		Log.i("Publicaciones!!", String.valueOf(publicaciones.size()));
				    		
				 }
				 catch(JSONException e){ e.printStackTrace(); }
				 catch(Exception e){ e.printStackTrace();}
			}
			
		});		
		hilo.start();
		try{ hilo.join(); }
		catch(InterruptedException e){	e.printStackTrace(); }
		
		return publicaciones;
	}
	
	public void savePublicacion(final BRPublicacion p){
		Thread hilo = new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				request = new RESTClient("http://192.168.1.24/brokus/setPublicacion.php");
				 try{
					 request.AddParam("titulo", p.getTitulo());
					 request.AddParam("contenido", p.getContenido());					 
					 request.AddParam("anexo", p.getAnexo());
					 request.AddParam("fecha", p.getFechaCaducidad().toString());
					 request.AddParam("idusuario", String.valueOf(p.getIdUsuario().intValue()));
					 
					 // request.AddParam(", value);
					
					 request.Execute(RequestMethod.GET);
					 Log.i("response", ""+request.getResponse());					 
					
					 /*JSONObject json = new JSONObject(request.getResponse());
						usuario = new BRUsuario();
						 JSONArray jarray = json.getJSONArray("usuario");
				    		for(int i = 0; i < jarray.length(); i++){
				    			JSONObject jobj = jarray.getJSONObject(i);
				    			usuario.setUsername(jobj.getString("correo"));
				    			usuario.setContrasena(jobj.getString("contrasena"));
				    		}
				    		Log.i("Usuario!!", usuario.toString());
				    		*/
				 }
				 catch(JSONException e){ e.printStackTrace(); }
				 catch(Exception e){ e.printStackTrace();}
			}
			
		});		
		hilo.start();
		try{ hilo.join(); }
		catch(InterruptedException e){	e.printStackTrace(); }
	}
	
	public BRUsuario getUsuario(final String correo, final String password){		
		Thread hilo = new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				request = new RESTClient("http://192.168.1.24/brokus/getUsuario.php");
				 try{
					 request.AddParam("correo", correo);
					 request.AddParam("contrasena", password);
					 request.Execute(RequestMethod.GET);
					 Log.i("response", ""+request.getResponse());					
					 JSONObject json = new JSONObject(request.getResponse());
						usuario = new BRUsuario();
						 JSONArray jarray = json.getJSONArray("usuario");
				    		for(int i = 0; i < jarray.length(); i++){
				    			JSONObject jobj = jarray.getJSONObject(i);
				    			usuario.setUsername(jobj.getString("correo"));
				    			usuario.setContrasena(jobj.getString("contrasena"));
				    			usuario.setId(jobj.getInt("idusuario"));
				    			usuario.setNombre(jobj.getString("nombre"));
				    			usuario.setPuesto(jobj.getString("puesto"));
				    			usuario.setSector(jobj.getString("sector"));
				    			usuario.setLogo(jobj.getString("imagen"));
				    		}
				    		Log.i("Usuario!!", usuario.toString());
				 }
				 catch(JSONException e){
					 e.printStackTrace();
				 }
				 catch(Exception e)
				 {
					 e.printStackTrace();					 
				 }
			}
			
		});
		
		hilo.start();
		try{
			hilo.join();
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		
		return usuario;
	}

}
