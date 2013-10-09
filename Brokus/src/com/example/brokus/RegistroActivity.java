package com.example.brokus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegistroActivity extends Activity {
	public static RegistroActivity mthis;
	EditText User;
	EditText Password;
	EditText re_Password;
	EditText Position;
	EditText userEmail;
	Spinner subsector;
	AutoCompleteTextView autocom;
	ArrayAdapter subsectadapt;
	BRRegistro Registrar;
	String sector;
	ArrayList<String> Lista;
	ImageView imageFile;
	Uri pathFileUri;
	String pathFileStr=null;
	boolean imagendefault=false;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_registro);
			mthis=this;
			User=(EditText)findViewById(R.id.txtRegistro_nombre_usuario);
			autocom=(AutoCompleteTextView)findViewById(R.id.txtRegistro_nombre_company);
			Position=(EditText)findViewById(R.id.txtRegistro_puesto);
			userEmail=(EditText)findViewById(R.id.txtRegistro_email);
			Password=(EditText)findViewById(R.id.txtRegistro_contrasena);
			re_Password=(EditText)findViewById(R.id.txtRegistro_confirmar_contrasena);
			Registrar=new BRRegistro();
			ActionBar a = getActionBar();
			a.hide();
			BRDataSource GetDatos=new BRDataSource();
			GetDatos.getCompanies();
			Lista=new ArrayList<String>();
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			String[] values={"empresa1","empresa2","empresa3","empresa4","empresa5","empresa6","empresa7","empresa8","empresa9"};
			autocom=(AutoCompleteTextView)findViewById(R.id.txtRegistro_nombre_company);
			Spinner sp = (Spinner)findViewById(R.id.spinnerRegistro_sector);
			//ArrayAdapter Spinner
			ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(), 
					R.array.sectores, R.layout.spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			sp.setAdapter(adapter);
			//ArrayAdapter Autocompleteview
			ArrayAdapter<String> adaptervalues =new ArrayAdapter<String>(mthis,android.R.layout.select_dialog_item,Lista);
			autocom.setThreshold(1);
			autocom.setAdapter(adaptervalues);
		
		final AlertDialog.Builder alert=new AlertDialog.Builder(mthis);
		imageFile=(ImageView)findViewById(R.id.imagen_registro);
		imageFile.setImageResource(R.drawable.icon);
		ByteArrayOutputStream bs= new ByteArrayOutputStream();
		ObjectOutputStream os;
		try {
			os = new ObjectOutputStream (bs);
			os.writeObject(imageFile); 
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] bytes =  bs.toByteArray(); // devuelve byte[] de la imagen por default ----- el booleano imagendefault sera verdadero
		Log.i("bytes",""+bytes);
		String p2="";
		for(byte p: bytes ){
			p2 +=""+p;
		}
		imagendefault=true;
		Log.i("verdadero",""+imagendefault);
		alert.setTitle("A V I S O !");
		alert.setCancelable(false);
		alert.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
			//Comentario Jairo 
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
		sp.setAdapter(adapter);
		Button btnAbrirImagen = (Button)findViewById(R.id.btnAbrir_imagen);
		
		
			
		
		btnAbrirImagen.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				   Intent galleryIntent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				   startActivityForResult(galleryIntent,1);
                
                  
			}
			
		});
		
		final Button btnRegistrar=(Button)findViewById(R.id.btnAceptar);
		btnRegistrar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//Validacion de los campos del registro.
				try{
				if(ValidarEntradas()){
					if(ValidarCorreo()){
						if(ValidarPassword()){
							Log.i("validacion","ok");
							Registrar.BREmail(userEmail.getText().toString());
							Registrar.BRnombreUsuario(User.getText().toString());
							Registrar.BREmpresa(autocom.getText().toString());
							Registrar.BRPuesto(Position.getText().toString());
							Registrar.BRPassword(Password.getText().toString());
							//SaveRegistro(Registrar);	
						}
						else
						{
							alert.setMessage("Error en las contrasenas");
							alert.show();
						}
					}
					else
					{
						alert.setMessage("El correo no es valido");
						alert.show();
					}

					
					
				}
				else
				{
					alert.setMessage("Por favor llena todos los campos");
					alert.show();				}
				}
			
			catch(Exception e){
				alert.setMessage("No insertar caracteres especiales");
				alert.show();
			}
		}
		});

	
		
		sp.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				subsector=(Spinner)findViewById(R.id.spinnerRegistro_SubSector);
				Registrar.BRSector(parent.getItemAtPosition(pos).toString());
				int id=(int)parent.getItemIdAtPosition(pos);
				Log.i("sector::",subsector.toString());
				switch(id){
				case 0:
					 subsectadapt= ArrayAdapter.createFromResource(getApplicationContext(), 
							R.array.Arquitectura, R.layout.spinner_item);
					break;
				case 1:
					subsectadapt= ArrayAdapter.createFromResource(getApplicationContext(), 
							R.array.Agricultura_y_Ganaderia, R.layout.spinner_item);
					break;
				case 2:
					subsectadapt= ArrayAdapter.createFromResource(getApplicationContext(), 
							R.array.Calzado, R.layout.spinner_item);
					break;
				case 3:
					subsectadapt= ArrayAdapter.createFromResource(getApplicationContext(), 
							R.array.Quimicos, R.layout.spinner_item);
					break;
				}
				subsectadapt.setDropDownViewResource(android.R.layout.simple_spinner_item);
				subsector.setAdapter(subsectadapt);

				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
		
	}
		
		public void SaveRegistro(BRRegistro Registro){
			BRDataSource Register=new BRDataSource();
			Register.SaveRegister(Registro);
		}

	@Override
	   public void onBackPressed(){ //Regresa a la Actividad de Login Sin matar la Aplicacion -Jairo 
		   super.onBackPressed();
		   
	   }

	
	public boolean ValidarPassword(){
		boolean res=false;
		EditText Password=(EditText)findViewById(R.id.txtRegistro_contrasena);
		EditText re_Password=(EditText)findViewById(R.id.txtRegistro_confirmar_contrasena);
		try{
			if (Password.getText().toString().length()>0 && re_Password.getText().toString().length()>0){
				if(Password.getText().toString().matches(re_Password.getText().toString())){
					res=true;
				}
				else{
					res=false;
				}
			}
			else{
				Log.i("Password",""+Password.getText().toString().length());
				Log.i("re",""+re_Password.getText().toString().length());
			res=false;
			}
		}
		catch(Exception e){
			Log.i("Error", ""+e.getMessage());
		}
		return res;
		
		
	}
	
	public boolean ValidarEntradas(){
		//Se verifica que el usuario, la empresa y el puesto tengan un valor distinto a null
		try{
			
			if(User.getText().length()==0 || autocom.getText().length()==0||Position.getText().length()==0||userEmail.getText().length()==0||Password.getText().length()==0||re_Password.getText().length()==0){
				return false;
			}
			else{
				return true;
			}
		}
		catch(Exception e){
			Log.e("Error",""+e.getMessage().toString());
		}
		return false;
		
	}

	public boolean ValidarCorreo(){
		//Regresa si cumple o no una entrada valida para el correo
		//Validando si se puede dar commit a los archivos:P XD :$
		try{
			
			if(userEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")){
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e){
			Log.e("Error",""+e.getMessage().toString());
		}
		return false;
	}
	public void Convertir(JSONObject result){
		try{
			Log.i("HERE I AM","SEE ME");
			JSONArray JsonArr=result.getJSONArray("empresa");
			for(int i=0;i<JsonArr.length();i++){
				JSONObject job=JsonArr.getJSONObject(i);
				Lista.add(job.getString("nombre"));
				Log.i("Empresa",""+job.getString("nombre"));
			}
			
		}
		catch(Exception e){
			Log.i("ERROR",e.getMessage());
		}
		
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {
			
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
				Bitmap imageScaled =Bitmap.createScaledBitmap(image, imageFile.getWidth(), imageFile.getHeight(), true);
				imageFile.setImageBitmap(imageScaled);
				ByteArrayOutputStream bs= new ByteArrayOutputStream();
				ObjectOutputStream os;
				try {
					os = new ObjectOutputStream (bs);
					os.writeObject(imageFile); 
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				byte[] bytes =  bs.toByteArray(); // devuelve byte[] de la imagen cargada ----- el booleano imagendefault sera falso
				Log.i("bytes",""+bytes);
				String p2="";
				for(byte p: bytes ){
					p2 +=""+p;
				}
				imagendefault=false;
				Log.i("falso","");
			}
		}
	}
		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro, menu);
		return true;
	}

}
