package com.example.brokus;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

















import AsyncTasks.DetallePublicacionAnsyTask;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.text.util.Linkify;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DetalleActivity extends Activity {

	private TextView fecha, titulo, contenido,nombreuser,descComment,txtNombre,txtPuesto, txtSector;
	private ImageView imagen, imgEmpresa;
	public static DetalleActivity mthis;
	private BRPublicacion publicacion;
	ListView ShowComments;
	public SharedPreferences pref;
	public ArrayList<BRComentario> listaComentarios;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle);
		mthis = this;
		pref=mthis.getPreferences(MODE_PRIVATE);
		ShowComments=new ListView(this);
		ShowComments=(ListView)findViewById(R.id.ListaComentarios);		
		
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		
		
		Bundle extra = getIntent().getExtras();
		int id = extra.getInt("idPublicacion");
		
		this.fecha = (TextView)findViewById(R.id.sFecha_detalle);
		this.titulo = (TextView)findViewById(R.id.sTitulo_detalle);
		this.contenido = (TextView)findViewById(R.id.sContenido_detalle);
		this.imagen = (ImageView)findViewById(R.id.imagen_detalle);
		Editor edit=pref.edit();
		edit.putInt("idPublicacion", id);
		edit.commit();
		
		
		DetallePublicacionAnsyTask tarea = new DetallePublicacionAnsyTask(id);
		tarea.execute();
		AsyncTasks.getComentarios task=new AsyncTasks.getComentarios();
		task.execute();
		
		txtNombre = (TextView) findViewById(R.id.txtNombre);
		txtPuesto = (TextView) findViewById(R.id.txtPuesto);
		txtSector = (TextView) findViewById(R.id.txtSector);
		imgEmpresa = (ImageView) findViewById(R.id.imageView1);
		

		
	}

	private void mostrarDatosUsuario(int idUsuario){
		Log.i("Usuario234rf", String.valueOf(idUsuario));
		BRDataSource datos = new BRDataSource();
		BRUsuario usuario = datos.getUsuarioById(idUsuario);
		Log.i("Usuario234rf", usuario.toString());
		this.txtNombre.setText(usuario.getNombre());
		this.txtPuesto.setText(usuario.getPuesto());
		this.txtSector.setText(usuario.getSector());
		Bitmap img = Utilerias.stringToBitmap(usuario.getLogo().toString());
		if(img != null)
		this.imgEmpresa.setImageBitmap(img);
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
		Intent itSend=new Intent(android.content.Intent.ACTION_SEND);
		itSend.setAction(Intent.ACTION_SEND);
		itSend.setType("plain/text");
		itSend.putExtra(android.content.Intent.EXTRA_EMAIL, "ununezta@gmail.com");
		try{
			startActivity(itSend.createChooser(itSend, "Enviar Correo mediante:"));
		}
		catch(Exception e){
			Log.d("ERROR","NO FUNCIONA"+e.getMessage().toString());
		}
		
	}

	private void mensajeRecomandar() {
		AlertDialog.Builder mensaje = new AlertDialog.Builder(getApplicationContext());
		
		
	}

	public void convertirJsonPublicaciones(JSONObject json) {
		// TODO Auto-generated method stub
		publicacion = new BRPublicacion();
		try {
			JSONArray jarray = json.getJSONArray("publicacion");
			

			for(int i=0; i<jarray.length(); i++){
				JSONObject jobj = jarray.getJSONObject(i);
				mostrarDatosUsuario(jobj.getInt("idusuario"));
				this.publicacion.setId(jobj.getInt("idpublicacion"));
				this.titulo.setText(jobj.getString("titulo"));
				this.contenido.setText(jobj.getString("contenido"));
				Linkify.addLinks(this.contenido, Linkify.ALL);
				//this.fecha.setText();
				final String ext=jobj.getString("extension");
				final String anexo=jobj.getString("anexo");
				final String name=jobj.getString("titulo");
				this.fecha.setText(jobj.getString("fecha"));
				if(jobj.getString("extension").toUpperCase().equals(".JPG")){
					try{
						this.imagen.setImageBitmap(stringToBitmap(jobj.getString("anexo")));	
						}
						catch(Exception e){
						Toast.makeText(getApplicationContext(), "Imagen Corrupta", Toast.LENGTH_SHORT).show();
						}	
					this.imagen.setOnLongClickListener(new OnLongClickListener(){

						@Override
						public boolean onLongClick(View arg0) {
							// TODO Auto-generated method stub
							
							AlertDialog.Builder alert = new AlertDialog.Builder(mthis);
							alert.setTitle("Descargar");
						    alert.setMessage("Quieres descargar "+name+".jpg?");
						    alert.setIcon(R.drawable.ic_launcher);
						    alert.setNegativeButton("No", null);
						    alert.setPositiveButton("Si",new DialogInterface.OnClickListener()
						    {
						        @Override
						        public void onClick(DialogInterface dialog, int whichButton)
						        {
						        	 String pathFile;
						        	 pathFile=Utilerias.anexoToImage(anexo, name);
						        	 displayNotification(pathFile,".jpg");
						        }
						    });
						    alert.create().show();
							 
							return false;
						}});
					
				}else{
					//this.imagen.setVisibility(View.GONE);
					BitmapFactory.Options options = new BitmapFactory.Options();
		            options.inSampleSize = 4;
					Bitmap image =BitmapFactory.decodeResource(mthis.getResources(),R.drawable.pdf_image,options);
			        Bitmap imageScaled =Bitmap.createScaledBitmap(image,300, 300, true);
					this.imagen.setImageBitmap(imageScaled);
					this.imagen.setOnLongClickListener(new OnLongClickListener(){

						@Override
						public boolean onLongClick(View arg0) {
							// TODO Auto-generated method stub
							AlertDialog.Builder alert = new AlertDialog.Builder(mthis);
							alert.setTitle("Descargar");
						    alert.setMessage("Quieres descargar "+name+".pdf?");
						    alert.setIcon(R.drawable.ic_launcher);
						    alert.setNegativeButton("No", null);
						    alert.setPositiveButton("Si",new DialogInterface.OnClickListener()
						    {
						        @Override
						        public void onClick(DialogInterface dialog, int whichButton)
						        {
						        	String pathFile;
						        	pathFile=Utilerias.anexoToPdf(anexo,name);
						        	displayNotification(pathFile,".pdf");
						        }
						    });
						    alert.create().show();
							 
							return false;
						}} );
				}
				this.fecha.setText(jobj.get("fecha").toString());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	public void ConvertirComentarios(JSONObject value){
		listaComentarios=new ArrayList<BRComentario>();
		try {
			JSONArray jar = value.getJSONArray("comentario");
			for(int i=0;i<jar.length();i++){
				JSONObject job=jar.getJSONObject(i);
				BRComentario comment=new BRComentario();
				comment.getDescripcion(job.getString("contenido"));
				comment.getNombre(job.getString("nombre"));
				comment.getIdComentario(job.getInt("idComentario"));
				Log.i("COMENTARIO",job.getString("contenido"));
				Log.i("NOMBRE",job.getString("nombre"));
				listaComentarios.add(comment);
				}
			ShowComments.setAdapter(new AdapterComment());
		} 
		catch (JSONException e) {
			Log.i("ERROR",""+e.getMessage().toString());
			e.printStackTrace();
		}

	}

	//Obtener ListView con Comentarios
		private class AdapterComment extends BaseAdapter{

			public AdapterComment(){
				
			}
			@Override
			public int getCount() {
				return listaComentarios.size();
			}

			@Override
			public Object getItem(int index) {
				Log.v("POSITION",""+index);
				return listaComentarios.get(index);
			}

			@Override
			public long getItemId(int index) {
				// TODO Auto-generated method stub
				return index;
			}

			@Override
			public View getView(int pos, View convertView, ViewGroup viewGroup) {
				if(convertView==null){
				LayoutInflater inflate=(LayoutInflater)mthis.getSystemService(LAYOUT_INFLATER_SERVICE);
				View vistaComments=inflate.inflate(R.layout.activity_comentarios, null);
				convertView=vistaComments;
				LinearLayout lay=(LinearLayout)convertView;
				nombreuser=(TextView)lay.findViewById(R.id.txtComentarioTitulo);
				descComment=(TextView)lay.findViewById(R.id.txtComentarioDesc);
				nombreuser.setText(listaComentarios.get(pos).setNombre());
				descComment.setText(listaComentarios.get(pos).setDescripcion());
				}

				//Log.i("VALOR",""+listaComentarios.get(pos).setNombre());
				return convertView;
			}
		
			
		}

	private  Bitmap stringToBitmap(String imageString){
	    Bitmap bitmap = null;
	    byte[] byteArray = Base64.decode(imageString, Base64.DEFAULT);
	    bitmap = BitmapFactory.decodeByteArray(byteArray , 0, byteArray.length);
	    return bitmap;
	    
	}
	
	int notificationID = 1;
	PendingIntent pendingIntent;
	protected void displayNotification(String path,String ext){
		if(ext.equals(".jpg")){
			File file = new File(path);
			Intent intent = new Intent();  
			intent.setAction(android.content.Intent.ACTION_VIEW);  
			intent.setDataAndType(Uri.fromFile(file), "image/*");  
			pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
		}else{
			File filePdf = new File(path);
	        Uri pathF = Uri.fromFile(filePdf);
	        Intent intentPdf = new Intent(Intent.ACTION_VIEW);    
	        intentPdf.setDataAndType(pathF, "application/pdf");      
	        intentPdf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
	        pendingIntent = PendingIntent.getActivity(this, 0, intentPdf, 0);
		}
		
		NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		CharSequence ticker ="Descarga archivo";
		CharSequence contentTitle = "Bokus";
		CharSequence contentText = "Archivo descargado: "+path;
		Notification noti = new NotificationCompat.Builder(this)
								.setContentIntent(pendingIntent)
								.setTicker(ticker)
								.setContentTitle(contentTitle)
								.setContentText(contentText)
								.setSmallIcon(R.drawable.ic_launcher)
								.addAction(R.drawable.ic_launcher, ticker, pendingIntent)
								.setVibrate(new long[] {100, 250, 100, 500})
								.build();
		noti.flags |= Notification.FLAG_AUTO_CANCEL;
		nm.notify(notificationID, noti);
	}
	
	
	
	
	

}
