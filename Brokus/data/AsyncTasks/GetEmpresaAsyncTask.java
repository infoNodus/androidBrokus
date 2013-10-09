package AsyncTasks;
import org.json.JSONObject;

import AsyncTasks.RESTClient.RequestMethod;
import android.os.AsyncTask;
import android.util.Log;

import com.example.brokus.*;
public class GetEmpresaAsyncTask extends AsyncTask<String, Integer, String> {
com.example.brokus.RegistroActivity SendCompanies;
	
	@Override
	protected void onPreExecute(){
	//Log.i("pagina", "afds");
	//pd=ProgressDialog.show(context, null, null);
	}
	
	@Override
	protected String doInBackground(String... params) {
		RESTClient request=new RESTClient("http://192.168.1.24/brokus/getempresa.php");
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

	protected void onPostExecute(String result){
		SendCompanies=new com.example.brokus.RegistroActivity();
		if(result==null)return;
		try{
		JSONObject json = new JSONObject(result);
		SendCompanies.mthis.Convertir(json);
		}
		catch (Exception e){
			
		}
	}

}
