package com.example.brokus;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class DetalleActivity extends Activity {

	private TextView fecha, titulo, contenido;
	private ImageView imagen;
	public static DetalleActivity mthis;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle);
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		
		
		Bundle extra = getIntent().getExtras();
		
		
		
		mthis = this;
		this.fecha = (TextView)findViewById(R.id.sFecha_detalle);
		this.titulo = (TextView)findViewById(R.id.sTitulo_detalle);
		this.contenido = (TextView)findViewById(R.id.sContenido_publicacion);
		this.imagen = (ImageView)findViewById(R.id.imagen_detalle);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalle, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.action_recomendar:
	        	mensajeRecomandar();
	        	return true;
	        case R.id.action_correo:
	        	actionCorreo();
	        	return true;
	        	
	        	
	        
	            
	        
	    }

	    return super.onOptionsItemSelected(item);
	}

	private void actionCorreo() {
		// TODO Auto-generated method stub
		
	}

	private void mensajeRecomandar() {
		AlertDialog.Builder mensaje = new AlertDialog.Builder(getApplicationContext());
		
		
	}
	

}
