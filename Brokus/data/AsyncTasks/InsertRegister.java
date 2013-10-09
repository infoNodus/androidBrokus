package AsyncTasks;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import AsyncTasks.RESTClient.RequestMethod;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.brokus.*;
public class InsertRegister extends AsyncTask<String , Integer, String> {
com.example.brokus.RegistroActivity Insert;
SharedPreferences pref;
Context c;
	public InsertRegister(){
		
	}
	@Override
	protected void onPreExecute(){
			//Log.i("pagina", "afds");
		//pd=ProgressDialog.show(context, null, null);
	}
	@Override
	protected String doInBackground(String... params) {
		String Empresa=RegistroActivity.mthis.pref.getString("empresaUsuario", null);
		String Correo=RegistroActivity.mthis.pref.getString("correoUsuario", null);
		String Puesto=RegistroActivity.mthis.pref.getString("puestoUsuario", null);
		String Nombre=RegistroActivity.mthis.pref.getString("nombreUsuario", null);
		String Sector=RegistroActivity.mthis.pref.getString("sectorUsuario", null);
		String Password=RegistroActivity.mthis.pref.getString("passwordUsuario", null);
		String Logo=RegistroActivity.mthis.pref.getString("logoUsuario", null);
		String NoEmpresa=RegistroActivity.mthis.pref.getString("noEmpresa", null);
		RESTClient request=new RESTClient("http://192.168.1.24/brokus/setUsuario.php");
		try{
			request.AddParam("empresa", Empresa);
			request.AddParam("nombre", Nombre);
			request.AddParam("puesto", Puesto);
			request.AddParam("correo", Correo);
			request.AddParam("sector", Sector);
			request.AddParam("contrasena", Password);
			request.AddParam("imagen", Logo);
			request.AddParam("esta", NoEmpresa);
			request.Execute(RequestMethod.GET);
			if (request.getResponse()!=null){
				Log.i("Value",request.getResponse());
				return request.getResponse();
			}
		}
		catch(Exception e){
			
		}
		return null;
	}
	
	protected void onPostExecute(String result){
		
		if (result!=null){
			JSONObject json;
			try {
				json = new JSONObject(result);
				RegistroActivity.mthis.ConvertirUsuario(json);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}

}
