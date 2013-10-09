package com.example.brokus;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import AsyncTasks.getEmpresaPerfil;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class PerfilEmpresaActivity extends Activity {

	public static PerfilEmpresaActivity mthis;
	private BREmpresa empresa;

	private TextView sNombre, sSector;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil_empresa);
		sNombre = (TextView)findViewById(R.id.sNombre_perfil);
		sSector = (TextView)findViewById(R.id.sSector_perfil);
		mthis = this;
		getEmpresaPerfil emp = new getEmpresaPerfil((new Random().nextInt()%10)+1);
		emp.execute();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.perfil_empresa, menu);
		return true;
	}

	public void Convertir(JSONObject json) {
		// TODO Auto-generated method stub
		try {
			JSONArray jarray = json.getJSONArray("empresa");
			
			
			for(int i=0; i<jarray.length(); i++){
				JSONObject jobj = jarray.getJSONObject(i);
				empresa = new BREmpresa();
				
				empresa.setId(jobj.getInt("id"));
				empresa.setNombre(jobj.getString("nombre"));
				empresa.setSector(jobj.getString("sector"));
			}
			

			
			
			
			this.sNombre.setText(empresa.getNombre());
			this.sSector.setText(empresa.getSector());
			
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}

}
