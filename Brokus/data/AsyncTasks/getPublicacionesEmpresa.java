package AsyncTasks;

import org.json.JSONObject;

import com.example.brokus.PerfilEmpresaActivity;

import AsyncTasks.RESTClient.RequestMethod;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class getPublicacionesEmpresa extends AsyncTask<String, Integer, String> {
	private int id;
	private Context context;
	private ProgressDialog pd;
	
	public getPublicacionesEmpresa(int id){
		this.id = id;

	}

	@Override
	protected void onPreExecute(){
	//Log.i("pagina", "afds");

	}
	
	@Override
	protected String doInBackground(String... params) {
		RESTClient request=new RESTClient("http://192.168.1.24/brokus/getEmpresaPerfil.php?id="+id);
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
		return request.getResponse();
	}

	@Override
	protected void onPostExecute(String result){
		if(result==null)return;
		try{
			
			JSONObject json = new JSONObject(result);
			PerfilEmpresaActivity.mthis.Convertir(json);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

}
