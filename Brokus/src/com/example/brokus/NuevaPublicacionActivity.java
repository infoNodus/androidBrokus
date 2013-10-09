package com.example.brokus;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;

public class NuevaPublicacionActivity extends Activity {
public static NuevaPublicacionActivity mthis;
Uri pathFileUri;
String pathFileStr=null;
ImageView imageFile;
TextView nameFile;
int takePhoto,gallery,pdf;

/****************************/
Bitmap imageScaled;
Date fechaCad;
Button botonCrearPub;
CheckBox checkerCaducidad;
NumberPicker pickerCaducidad;
TextView display;
EditText editTitulo;
EditText editContenido;

int cday, cmonth, cyear;
Calendar c = Calendar.getInstance();
DatePickerDialog.OnDateSetListener d = new OnDateSetListener() {

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
            int dayOfMonth) {

        cday = dayOfMonth;
        cmonth = monthOfYear + 1;
        cyear = year;
        
        String fecha = cday + "-" + cmonth + "-" + cyear;
        
       // fechaCad = Utilerias.deStringToDate(fecha );
        display.setText(fecha );
    }
};
/****************************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nueva_publicacion);
		this.setTitle(null);
		mthis=this;
		
		/**************/
		editTitulo = (EditText) findViewById(R.id.editText1);
		editContenido = (EditText) findViewById(R.id.editText2);
		editContenido.setFilters(new InputFilter[] {new InputFilter.LengthFilter(300)});
		botonCrearPub = (Button) findViewById(R.id.btnRecomendar_detalle);
		botonCrearPub.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(validarCampos()){
					creaPublicacion();
				}else
				{
					
				}
			}
			
		});
		display = (TextView) findViewById(R.id.textView3);
		
		checkerCaducidad = (CheckBox) findViewById(R.id.checkBox1);
		
		checkerCaducidad.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){ //Si se elige caducidad
					new DatePickerDialog(NuevaPublicacionActivity.mthis, d,
	                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
	                                .get(Calendar.DAY_OF_MONTH)).show();
				}
				else{
					display.setText("");
				}
				
			}
			
		});
		/************/
		
		imageFile=(ImageView)findViewById(R.id.imagen_detalle);
		nameFile=(TextView)findViewById(R.id.textView2);
		
		
		//botonCrearPub.setOnClickListener(new OnClickListener());
		
	}
	
	/****************/
	private boolean validarCampos() {
		EditText tituloTxt=(EditText)findViewById(R.id.editText1);
		EditText contenidoTxt=(EditText)findViewById(R.id.editText2);
		CheckBox checkFecha=(CheckBox)findViewById(R.id.checkBox1);
		if(tituloTxt.length()==0||contenidoTxt.length()==0){
		String campo = "";
		if(tituloTxt.length()==0){
		campo="Titulo Vacio";
		}
		if(contenidoTxt.length()==0){
		campo="Contenido Vacio";
		}
		if(tituloTxt.length()==0&&contenidoTxt.length()==0){
		campo="Titulo & Contenido Vacio";
		}
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		  alert.setIcon(R.drawable.ic_launcher);
		  alert.setTitle("Error");
		  alert.setMessage(campo);
		  alert.setCancelable(false);
		  alert.setNeutralButton("Aceptar",
		                new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface dialog, int id) {
		                        dialog.cancel();
		                    }
		                });
		            alert.create().show();
		           
		        if(!checkFecha.isChecked()){
		   
		    Calendar cal = new GregorianCalendar();
		   
		    cal.add(Calendar.DAY_OF_YEAR, +3);

		        fechaCad = cal.getTime();

		        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

		        String formatteDate = df.format(fechaCad);
		   	    
		        display.setText(formatteDate);
		   	    
		   
		 }
		return false;
		}
		return true;
		}
	 private void creaPublicacion(){
		 BRPublicacion publicacion = new BRPublicacion();
		 publicacion.setTitulo(this.editTitulo.toString());
		 publicacion.setContenido(this.editContenido.toString());
		 publicacion.setIdUsuario(LoginActivity.mthis.usuarioActivo.getId());
		 publicacion.setAnexo(Utilerias.bitmapToString(imageScaled)); //WARNING!! Solo Imagenes por el momento
		 
		 //publicacion.setFechaCaducidad((java.sql.Date) ((this.checkerCaducidad.isChecked()) 
				 //? fechaCad : Utilerias.sumarFechasDias(Utilerias.deStringToDate(Utilerias.getFechaActual()), 3))); //3 dias de vigencia como Default
		 publicacion.setFechaCaducidad((java.sql.Date) fechaCad);
		 
		 BRDataSource data = new BRDataSource(this);
		 data.savePublicacion(publicacion);
		 
	 } 	

	 /***************/
	
	
	 private void OptionFile(int opc) {
			
			if(opc==takePhoto){
				Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        	startActivityForResult(takePictureIntent,takePhoto);
			}
			
			if(opc==gallery){
				 Intent galleryIntent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	             startActivityForResult(galleryIntent,gallery);
			}
			
			if(opc==pdf){
				Intent pdfIntent = new Intent(mthis,Explorador_archivo.class);
	            startActivityForResult(pdfIntent,pdf);   
			}
	    }
	
	
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			if (requestCode == takePhoto){
				Bitmap foto= (Bitmap)data.getExtras().get("data");
				imageScaled =Bitmap.createScaledBitmap(foto, imageFile.getHeight(), imageFile.getHeight(), true);
				imageFile.setImageBitmap(imageScaled);
			}
			if (requestCode == gallery) {
				pathFileUri = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(pathFileUri,filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				pathFileStr = cursor.getString(columnIndex);
				cursor.close();
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 4;
				Bitmap image = BitmapFactory.decodeFile(pathFileStr,options);
				imageScaled =Bitmap.createScaledBitmap(image,imageFile.getHeight(), imageFile.getHeight(), true);
				imageFile.setImageBitmap(imageScaled);
				nameFile.setText(pathFileStr);
				
			}
			
			if(requestCode == pdf) {
				 if (data.hasExtra("archivo_seleccionado")) {
					 	pathFileStr = data.getExtras().getString("archivo_seleccionado");
					 	pathFileUri=Uri.parse(pathFileStr);
				        if(pathFileUri.getLastPathSegment().endsWith("pdf"))
			            {
				        	nameFile.setText(pathFileStr);
				        	BitmapFactory.Options options = new BitmapFactory.Options();
				            options.inSampleSize = 4;
				            Bitmap image =BitmapFactory.decodeResource(mthis.getResources(),R.drawable.pdf_image,options);
				            Bitmap imageScaled =Bitmap.createScaledBitmap(image, imageFile.getHeight(), imageFile.getHeight(), true);
				        	imageFile.setImageBitmap(imageScaled);
			            }
				        else 
			            {
			            	Toast.makeText(getApplicationContext(), "Archivo Invalido",Toast.LENGTH_SHORT).show();
			            }
				    
				 }
			}
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nueva_publicacion, menu);
		return true;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
		
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		 switch (item.getItemId()) {
	        case R.id.action_tomar_foto:
	        	takePhoto=1;
	        	pathFileStr=null;
	        	nameFile.setText("");
	        	imageFile.setImageResource(R.drawable.nodus);
	            OptionFile(takePhoto);
	            return true;
	        case R.id.action_adjuntar_imagen:
	        	 gallery=2;
	        	 pathFileStr=null;
	        	 nameFile.setText("");
		         imageFile.setImageResource(R.drawable.nodus);
	        	 OptionFile(gallery);;
	            return true;
	        case R.id.action_adjuntar:
	        	 pdf=3;
	        	 pathFileStr=null;
	        	 nameFile.setText("");
		         imageFile.setImageResource(R.drawable.nodus);
	        	 OptionFile(pdf);;
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
		
	}

}