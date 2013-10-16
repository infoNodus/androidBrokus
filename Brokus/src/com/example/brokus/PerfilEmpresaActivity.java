package com.example.brokus;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;








import AsyncTasks.PublicacionAnsyTask;
import AsyncTasks.UsuarioPerfilAnsyTask;
import AsyncTasks.getEmpresaPerfil;
import AsyncTasks.getPublicacionAnsyTask;
import AsyncTasks.getPublicacionesEmpresa;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PerfilEmpresaActivity extends Activity {

	private SlidingPaneLayout mPanes;
	private ListView listaUsuarios;
	private ListView listaPublicaciones;
	private TextView sNombre;
	private TextView sPuesto;
	private TextView sSector;
	private ImageView sImagen;
	public ArrayList<BRPublicacion> listPublicacion;
	public ArrayList<BRUsuario> listUsuarios;
    public int id;
    public String User;
    public String Puesto;
    public String Imagen;
	public static PerfilEmpresaActivity mthis;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil_empresa);
		this.setTitle("PERFIL");
		
        mthis = this;
        Intent in= mthis.getIntent();
        Bundle values= in.getExtras();
        id= values.getInt("id");
        User=values.getString("nombre");
        Puesto=values.getString("puesto");
        Imagen=values.getString("imagen");
        Bitmap bmp=Utilerias.stringToBitmap(Imagen);
        PublicacionAnsyTask tarea = new PublicacionAnsyTask(id);
       tarea.execute();
        
        UsuarioPerfilAnsyTask tarea_user = new UsuarioPerfilAnsyTask();
        
       // CirculoConfianzaAnsyTask cirucloTarea = new CirculoConfianzaAnsyTask();
        //cirucloTarea.execute();
       

		this.sNombre = (TextView)findViewById(R.id.sNombre_perfil);
		this.sSector = (TextView)findViewById(R.id.sSector_perfil);
		this.sImagen = (ImageView)findViewById(R.id.imagen_detalle);
		
		this.sNombre.setText(User);
		this.sSector.setText("Tecnologias de informacion");
		this.sImagen.setImageBitmap(bmp);
		
	}
	
	@Override
	protected void onRestart(){
		PublicacionAnsyTask tarea = new PublicacionAnsyTask(id);
        tarea.execute();
		super.onRestart();
	}
	
	private class PublicacionesAdapter extends BaseAdapter{

		private TextView usuario_publicacion;
		private TextView contenido_publicacion;
		private TextView fecha_publicacion;
		
		public PublicacionesAdapter(){
			
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listPublicacion.size();
		}

		@Override
		public Object getItem(int index) {
			// TODO Auto-generated method stub
			return index;
		}

		@Override
		public long getItemId(int index) {
			// TODO Auto-generated method stub
			return index;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup viewGroup) {
			// TODO Auto-generated method stub
			
			if(convertView == null){
				LayoutInflater inflate = (LayoutInflater)mthis.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View vistaView= inflate.inflate(R.layout.item_publicacion, null);
				convertView = vistaView;
			}
			
			if(convertView != null){
				int n = new Random().nextInt();
				LinearLayout layout = (LinearLayout)convertView;
				switch(position%5){
					case 0:
						layout.setBackgroundResource(R.color.publicacion_color_1);
						break;
					case 1:
						layout.setBackgroundResource(R.color.publicacion_color_2);
						break;
					case 2:
						layout.setBackgroundResource(R.color.publicacion_color_3);
						break;
					case 3:
						layout.setBackgroundResource(R.color.publicacion_color_4);
						break;
					case 4:
						layout.setBackgroundResource(R.color.publicacion_color_5);
						break;
					case 5:
						layout.setBackgroundResource(R.color.publicacion_color_6);
						break;
				}
				
				this.usuario_publicacion = (TextView)layout.findViewById(R.id.sNombre_publicacion);
				this.contenido_publicacion = (TextView)layout.findViewById(R.id.sContenido_publicacion);
				this.fecha_publicacion = (TextView)layout.findViewById(R.id.sFecha_publicacion);
				this.usuario_publicacion.setText(listPublicacion.get(position).getNombreUsuario());
				this.contenido_publicacion.setText(listPublicacion.get(position).getContenido());
				this.fecha_publicacion.setText(listPublicacion.get(position).getFechaString());
				
				contenido_publicacion.setOnLongClickListener(new View.OnLongClickListener() {

			        public boolean onLongClick(View v) {
			        	
			        	AlertDialog.Builder mensaje = new AlertDialog.Builder(mthis);
			        	mensaje.setMessage("��Que desea hacer con esta publicacion?");
			        	mensaje.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
						});
			        	
			        	mensaje.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
						});
			        	
			        	mensaje.setNeutralButton("Detalle", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Intent i = new Intent(mthis, DetalleActivity.class);
								i.putExtra("idPublicacion", listPublicacion.get(position).getId());
								startActivity(i);
							}
						});
			        	mensaje.show();
			        	
			        	//mthis.startActivity(new Intent(mthis, DetalleActivity.class));;
			        	return false;
			        }
			    });
				
			}
			
			return convertView;
		}
		
	}

	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.perfil_empresa, menu);
		return true;
	}
	

   
    @Override
	public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
   
	}

	public void convertirJsonPublicaciones(JSONObject json) {
		// TODO Auto-generated method stub
		try {
			JSONArray jarray = json.getJSONArray("publicacion");
			listPublicacion = new ArrayList<BRPublicacion>();
			listPublicacion.clear();

			
			for(int i=0; i<jarray.length(); i++){
				JSONObject jobj = jarray.getJSONObject(i);
				BRPublicacion publicacion = new BRPublicacion();
				publicacion.setId(jobj.getInt("idpublicacion"));
				publicacion.setTitulo(jobj.getString("titulo"));
				publicacion.setContenido(jobj.getString("contenido"));
				
				String contenidoAux = jobj.getString("contenido");
				if(contenidoAux.length() >50){
					contenidoAux = contenidoAux.substring(0, 50)+"...";
				}
				publicacion.setContenido(contenidoAux);
				//publicacion.setExtension(jobj.getString("extension"));
				SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
				try {
					String fecha = formatoDelTexto.parse(jobj.getString("fecha")).toString();
					publicacion.setFechaString(fecha);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				publicacion.setIdUsuario(jobj.getInt("idusuario"));
				//publicacion.setAnexo(jobj.getString("anexo"));
				publicacion.setNombreUsuario(jobj.getString("nombre"));
				listPublicacion.add(publicacion);
			}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
			
		}
		this.listaPublicaciones = (ListView)findViewById(R.id.listaPublicacion);
		this.listaPublicaciones.setAdapter(new PublicacionesAdapter());
		
		Log.i("response", "algo paso   "+listPublicacion.size()+"");
	}
	
	
}
