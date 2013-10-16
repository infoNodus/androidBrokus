package AsyncTasks;

import org.json.JSONObject;

import AsyncTasks.RESTClient.RequestMethod;
import android.os.AsyncTask;
import android.util.Log;

import com.example.brokus.CirculoConfianzaActivity;
import com.example.brokus.PerfilEmpresaActivity;

public class UsuarioPerfilAnsyTask extends AsyncTask<String, Integer, String> {


	@Override
	protected void onPreExecute(){
	//Log.i("pagina", "afds");

	}
	
	@Override
	protected String doInBackground(String... params) {
		RESTClient request=new RESTClient("http://192.168.1.24/brokus/getUsuario.php?correo=carlos.bedoy@gmail.com&contrasena=android");
		try {
			request.Execute(RequestMethod.GET);
			
			if(request.getResponse()!=null){
				Log.i("Response",""+request.getResponse());
				return request.getResponse();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected void onPostExecute(String result){
		if(result==null)return;
		try{
		JSONObject json = new JSONObject(result);
			CirculoConfianzaActivity.mthis.convertirJsonPublicaciones(json);
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

}
