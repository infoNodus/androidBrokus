package com.example.brokus;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Base64;

public class Utilerias {

    public static String bitmapToString(Bitmap image){
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
    	image.compress(Bitmap.CompressFormat.PNG, 0, stream);
    	byte[] byteArray = stream.toByteArray();
    	return Base64.encodeToString(byteArray, Base64.DEFAULT);
    	
    }
    
    public static Bitmap stringToBitmap(String imageString){
    	byte[] byteArray =  Base64.decode(imageString, Base64.DEFAULT);
    	Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray , 0, byteArray.length);
    	return bitmap;
    }

	//Metodo estatico que consulta del repositorio los username de cada usuario y 
	 //verifica que exista el usuario u en dicho respositorio en un Contexto c
	public static boolean existeUsuario(Context c, BRUsuario u){
		

		BRDataSource data = new BRDataSource(c);
		
		BRUsuario user = data.getUsuario(u.getUsername(), u.getContrasena());
		Log.i("boolUser!!", (user == null) ? "null" : "noesnull");
		return (user != null) ? true : false;
    }
	
	public static boolean esContrasenaValida(BRUsuario u, Context c){
		BRDataSource data = new BRDataSource(c);	
		BRUsuario user = data.getUsuario(u.getUsername(), u.getContrasena());
		if(user != null && user.getContrasena().compareTo(u.getContrasena()) == 0)
			return true;
		return false;
		
	}
	

    //Metodo usado para obtener la fecha actual
    //@return Retorna un <b>STRING</b> con la fecha actual formato "dd-MM-yyyy"
    public static String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        return formateador.format(ahora);
    }

    //Metodo usado para obtener la hora actual del sistema
    //@return Retorna un <b>STRING</b> con la hora actual formato "hh:mm:ss"
    public static String getHoraActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("hh:mm:ss");
        return formateador.format(ahora);
    }

    //Sumarle dias a una fecha determinada
    //@param fch La fecha para sumarle los dias
    //@param dias Numero de dias a agregar
    //@return La fecha agregando los dias
    public static java.sql.Date sumarFechasDias(java.sql.Date fch, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fch.getTime());
        cal.add(Calendar.DATE, dias);
        return new java.sql.Date(cal.getTimeInMillis());
    }

    //Restarle dias a una fecha determinada
    //@param fch La fecha
    //@param dias Dias a restar
    //@return La fecha restando los dias
    public static synchronized java.sql.Date restarFechasDias(java.sql.Date fch, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fch.getTime());
        cal.add(Calendar.DATE, -dias);
        return new java.sql.Date(cal.getTimeInMillis());
    }

    //Diferencias entre dos fechas
    //@param fechaInicial La fecha de inicio
    //@param fechaFinal  La fecha de fin
    //@return Retorna el numero de dias entre dos fechas
    public static synchronized int diferenciasDeFechas(Date fechaInicial, Date fechaFinal) {

        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String fechaInicioString = df.format(fechaInicial);
        try {
            fechaInicial = df.parse(fechaInicioString);
        } catch (ParseException ex) {
        }

        String fechaFinalString = df.format(fechaFinal);
        try {
            fechaFinal = df.parse(fechaFinalString);
        } catch (ParseException ex) {
        }

        long fechaInicialMs = fechaInicial.getTime();
        long fechaFinalMs = fechaFinal.getTime();
        long diferencia = fechaFinalMs - fechaInicialMs;
        double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
        return ((int) dias);
    }

    //Devuele un java.util.Date desde un String en formato dd-MM-yyyy
    //@param La fecha a convertir a formato date
    //@return Retorna la fecha en formato Date
    public static synchronized java.sql.Date deStringToDate(String fecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaEnviar = null;
        try {
            fechaEnviar = formatoDelTexto.parse(fecha);
            return (java.sql.Date) fechaEnviar;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }
	
	
    
   
}
