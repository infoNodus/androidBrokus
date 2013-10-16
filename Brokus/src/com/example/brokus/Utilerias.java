package com.example.brokus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

public class Utilerias {
	public static String ToStr(String path) {
		File file = new File(path);
		ByteArrayOutputStream bs= new ByteArrayOutputStream();
		ObjectOutputStream os;
		try {
			os = new ObjectOutputStream (bs);
			os.writeObject(file); 
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] bytes =  bs.toByteArray();
		String base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
		return base64;  
		
	}
	
	public static String anexoToPdf(String anexo,String name) {  
	    try {  
	        // Create file 
	    	
	    	byte[] bArray =Base64.decode(anexo, Base64.DEFAULT);
	    	File sdCard = Environment.getExternalStorageDirectory();
	    	File dir = new File (sdCard.getAbsolutePath()+"/brokus/pdf");
	    	dir.mkdirs();
	    	File file = new File(dir, name+".pdf");

	    	OutputStream out = new FileOutputStream(file);
	    	
	    	out.write(bArray);
	    	out.close(); 
	    	return sdCard.getAbsolutePath()+"/brokus/pdf/"+name+".pdf";
	    } catch (Exception e) {  
	        System.err.println("Error: " + e.getMessage());  
	    }  
		return null;  
	}
	
	public static String anexoToImage(String anexo,String name) {  
	    try {  
	        // Create file 
	    	//Bitmap b = null;
	    	byte[] bArray =Base64.decode(anexo, Base64.DEFAULT);
	    	File sdCard = Environment.getExternalStorageDirectory();
	    	File dir = new File (sdCard.getAbsolutePath()+"/brokus/jpg");
	    	dir.mkdirs();
	    	File file = new File(dir, name+".JPG");
	    		
	    	OutputStream out = new FileOutputStream(file);
	    	//b.compress(Bitmap.CompressFormat.JPEG, 85, out);
	    	out.write(bArray);
	    	out.close(); 
	    	return sdCard.getAbsolutePath()+"/brokus/jpg/"+name+".JPG";
	    } catch (Exception e) {  
	        System.err.println("Error: " + e.getMessage());  
	    }  
		return null;  
	}
    public static String bitmapToString(Bitmap image){
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
    	image.compress(Bitmap.CompressFormat.PNG, 100, stream);
    	byte[] byteArray = stream.toByteArray();
    	
    	//String s = new String(byteArray);
    	//return s;
    	return Base64.encodeToString(byteArray, Base64.DEFAULT);

        //ImagenUDP mUDP = new ImagenUDP(image);

      //  byte[] byteArray = mUDP.toByteArray();
        
        //return Base64.encodeToString(byteArray, Base64.DEFAULT);
    	
    }
    
    public static Bitmap stringToBitmap(String imageString){
    	
    	//byte[] byteArray =  Base64.decode(imageString, Base64.);
    	Bitmap bitmap = null;
    	//byte[] byteArray = imageString.getBytes();
    	byte[] byteArray = Base64.decode(imageString, Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(byteArray , 0, byteArray.length);
    	
    	
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
	public static void infouser(BRUsuario u,Context c){
		BRDataSource data = new BRDataSource(c);
		BRUsuario user = data.getUsuario(u.getNombre(), u.getSector());
		
	}

    //Metodo usado para obtener la fecha actual
    //@return Retorna un <b>STRING</b> con la fecha actual formato "dd-MM-yyyy"
    public static String getFechaActual() {
        Date ahora = new Date();
        //SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
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
    public static synchronized java.util.Date deStringToDate(String fecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaEnviar = null;
        try {
            fechaEnviar = formatoDelTexto.parse(fecha);
            return  fechaEnviar;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static synchronized String deDateToString(Date fecha) {
    	
      
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(fecha);
       
    }

	public static String formatoDateToStringMySQL(Date fecha) {
		// TODO Auto-generated method stub
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	     String formatted = df.format(fecha);
		return formatted;
	}
    
    
	
	
    
   
}




