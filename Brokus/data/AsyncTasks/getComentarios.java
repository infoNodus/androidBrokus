package AsyncTasks;

import org.json.JSONObject;

import AsyncTasks.RESTClient.RequestMethod;
import android.os.AsyncTask;
import android.util.Log;

import com.example.brokus.*;
public class getComentarios extends AsyncTask<String, Integer, String>{
	@Override
	protected void onPreExecute(){
	//Log.i("pagina", "afds");
	//pd=ProgressDialog.show(context, null, null);
	}


	@Override
	protected String doInBackground(String... params) {
		int idP=DetalleActivity.mthis.pref.getInt("idPublicacion", 0);
		RESTClient request=new RESTClient("http://192.168.1.24/brokus/getCOMentario.php");
		try {
			request.AddParam("id", Integer.toString(idP));
			request.Execute(RequestMethod.GET);
			if(request.getResponse()!=null){
				Log.i("COMENTARIOS",request.getResponse());
				return request.getResponse();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected void onPostExecute(String result){
		//SendCompanies=new com.example.brokus.RegistroActivity();
		if(result==null)return;
		try{
		JSONObject json = new JSONObject(result);
		DetalleActivity.mthis.ConvertirComentarios(json);
		}
		catch (Exception e){
			//Log.i("ERROR",e.getMessage().toString());
		}
	}
}
