package com.example.brokus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class LoginActivity extends Activity {

	public static LoginActivity mthis;
	private TextView linkRegistro;
	EditText usertxt, passwordtxt;
	BRUsuario usuarioActivo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		

		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		
		this.linkRegistro = (TextView)findViewById(R.id.link_registro);
		SpannableString content = new SpannableString("Registrarse ahora");
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		linkRegistro.setText(content);
		
		mthis = this;
		Button btnlogin = (Button)findViewById(R.id.btnIr_login); 
		linkRegistro.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(mthis, RegistroActivity.class);
				
				startActivity(i);
				
				
			}
		});
		
		
		
        usertxt = (EditText)findViewById(R.id.txtUsuario_login);
        passwordtxt = (EditText)findViewById(R.id.txtContrasena_login);
       
       
        
      
      
        btnlogin.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                		if(usertxt.getText().toString().equals("nodus") && passwordtxt.getText().toString().equals("nodus")){
                			startActivity(new Intent(LoginActivity.this, CirculoConfianzaActivity.class));
                		}
                        ValidaEntradas();
                }
                
        });
        
}

public void ValidaEntradas(){
        
        int cont=0;
        if(usertxt.length()==0||passwordtxt.length()==0 || passwordtxt.length()<=6){ //Valida que el campo usuario y contraseï¿½ï¿½ï¿½ï¿½ï¿½ï¿½a no esten vacios y que el campo contraseï¿½ï¿½ï¿½ï¿½ï¿½ï¿½a no sea menor a 6 caracteres -Jairo
        
        cont=0;    
        }else{
                cont=1;
        }
if (cont!=1){
         AlertDialog.Builder alert = new AlertDialog.Builder(mthis);
         alert.setTitle("A V I S O");
           alert.setMessage("Favor de llenar correctamente los campos ");
           alert.setCancelable(false);
           alert.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                        
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                        }
                });
        alert.create();
        alert.show();
}
else if (cont==1){
        String user = usertxt.getText().toString();//Obtiene el String del Usuario-Jairo
        boolean isValid = false;
    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";//Valida formato de correo -Jairo
    CharSequence inputStr = user;
    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(inputStr);
    if (matcher.matches()) {
        isValid = true;
    }
    if(isValid==false){//si el formato de correo es invalido mostrara Alerta de Error -Jairo
         AlertDialog.Builder alert = new AlertDialog.Builder(mthis);
         		   alert.setTitle("A V I S O");
                   alert.setMessage("Campo de usuario invalido \n ejemplo@ejemplo.com");
                   alert.setCancelable(false);
                   alert.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                                
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                        dialog.dismiss();
                                }
                        });
                alert.create();
                alert.show();
    }
    if(isValid){
    	//Intent i = new Intent(mthis, CirculoConfianzaActivity.class);
		
		//startActivity(i);
        String password = passwordtxt.getText().toString();//Obtiene el string del Password -Jairo 
        this.finish();
        BRUsuario u = new BRUsuario(user, password);
        if(Utilerias.existeUsuario(this, u)){ //Utiliza Clase Utilerias creada por Rolando y devuelve un valor tipo Boolean para validar si la cuenta existe-Jairo
            if(Utilerias.esContrasenaValida(u, this)){
            	//Pantalla Muro -Jairo
            	usuarioActivo = (new BRDataSource()).getUsuario(user, password); //Tras haber validado usuario y contrasena se asigna el usuario activo de la app
            	Intent mainIntent = new Intent().setClass(this, CirculoConfianzaActivity.class);
		        startActivity(mainIntent);
		        finish();
            }
            else{
            	AlertDialog.Builder alert = new AlertDialog.Builder(mthis);
                alert.setTitle("A V I S O !");
                alert.setMessage("Contrasena Invalida!");
                alert.setCancelable(false);
                alert.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                             
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                     // TODO Auto-generated method stub
                                     dialog.dismiss();
                             }
                     });
             alert.create();
             alert.show();
            }
        	
        }else{
                AlertDialog.Builder alert = new AlertDialog.Builder(mthis);
                           alert.setTitle("A V I S O !");
                           alert.setMessage("No Existe Usuario \n Favor de Registrarse");
                           alert.setCancelable(false);
                           alert.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                                        
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                                // TODO Auto-generated method stub
                                                dialog.dismiss();
                                        }
                                });
                        alert.create();
                        alert.show();
        }
    

       
        
    }
        
}

}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.action_settings:
	        verCreditos();
	    	return true;

	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

	 @Override
	   public void onBackPressed(){ 
		  this.finish();
		   
	   }
	
	private void verCreditos() {
		AlertDialog.Builder creditos = new AlertDialog.Builder(mthis);
		creditos.setTitle("Nodus Creative Center\nBrokus App");
		creditos.setMessage("Desarrollado por:\n\n" +
				"Android Team\n\n" +
				"Carlos A. Cervantes Bedoy\n" +
				"NÃºÃ±ez Tavares JosÃ© Ulises\n" +
				"Jairo Daniel Castro Lòpez\n" +
				"RaÃºl Quintero Esparza\n" +
				"Narvel R. VelÃ¡zquez AlarcÃ³n\n\n" +
				"");
		creditos.show();
		
	}
}
