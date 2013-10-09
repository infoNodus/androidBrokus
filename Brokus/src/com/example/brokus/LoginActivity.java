package com.example.brokus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import com.google.android.gms.plus.model.people.Person.Image;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends Activity implements OnClickListener,
ConnectionCallbacks, OnConnectionFailedListener, PlusClient.OnPeopleLoadedListener  {

	
	private ProgressDialog mConnectionProgressDialog;
	private static final int DIALOG_GET_GOOGLE_PLAY_SERVICES = 2;
	private static final int REQUEST_CODE_SIGN_IN =3;
	private static final int REQUEST_CODE_GET_GOOGLE_PLAY_SERVICES =4;
	private ConnectionResult mConnectionResult;
	public static LoginActivity mthis;
	private TextView linkRegistro;
	EditText usertxt, passwordtxt;
	BRUsuario usuarioActivo;
	private View mSignOutButton;
	private SignInButton mSignInButton;
	PlusClient mPlusClient;
	String Usuario;
	String cuenta;
	boolean connected=false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
		setContentView(R.layout.activity_login);
		

		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		mPlusClient = new PlusClient.Builder(this, this, this).setScopes(Scopes.PLUS_LOGIN).setVisibleActivities("http://schemas.google.com/AddActivity",
				"http://schemas.google.com/ListenActivity").build();
		mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
		mSignInButton.setOnClickListener(this);		
		mSignOutButton = findViewById(R.id.button1);
		final ProgressDialog connectionProgressDialog = new ProgressDialog(this);
		connectionProgressDialog.setMessage("Conectando...");
		
		mSignOutButton.setOnClickListener(this);
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
				connected=false;
				Intent i = new Intent(mthis, RegistroActivity.class);
			    i.putExtra("connected", connected);
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
	 @Override
	    public void onStart() {
	        super.onStart();
	        mPlusClient.connect();
	    }

	    @Override
	    public void onStop() {
	        mPlusClient.disconnect();
	        super.onStop();
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
    	
        String password = passwordtxt.getText().toString();//Obtiene el string del Password -Jairo 
      
        BRUsuario u = new BRUsuario(user, password);
        if(Utilerias.existeUsuario(this, u)){ //Utiliza Clase Utilerias creada por Rolando y devuelve un valor tipo Boolean para validar si la cuenta existe-Jairo
            if(Utilerias.esContrasenaValida(u, this)){
            	//Pantalla Muro -Jairo
            	usuarioActivo = (new BRDataSource()).getUsuario(user, password); //Tras haber validado usuario y contrasena se asigna el usuario activo de la app
            	Intent in = new Intent(mthis, CirculoConfianzaActivity.class);
        		
        		startActivity(in);
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
	protected void onActivityResult(int requestCode, int resultCode,int responseCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		 if (requestCode == REQUEST_CODE_SIGN_IN
	                || requestCode == REQUEST_CODE_GET_GOOGLE_PLAY_SERVICES) {
	            if (resultCode == RESULT_OK && !mPlusClient.isConnected()
	                    && !mPlusClient.isConnecting()) {
	                // This time, connect should succeed.
	                mPlusClient.connect();
	            }
	        }
	}
	@Override
    protected Dialog onCreateDialog(int id) {
        if (id != DIALOG_GET_GOOGLE_PLAY_SERVICES) {
            return super.onCreateDialog(id);
        }

        int available = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (available == ConnectionResult.SUCCESS) {
            return null;
        }
        if (GooglePlayServicesUtil.isUserRecoverableError(available)) {
            return GooglePlayServicesUtil.getErrorDialog(
                    available, this, REQUEST_CODE_GET_GOOGLE_PLAY_SERVICES);
        }
        return new AlertDialog.Builder(this) .setCancelable(true)
                .create();
    }
	
	@Override
	public void onPeopleLoaded(ConnectionResult status,
			PersonBuffer personBuffer, String nextPageToken) {
		// TODO Auto-generated method stub
		 if (status.getErrorCode() == ConnectionResult.SUCCESS)
		    {
			 
		    	
		    }
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		 updateButtons(false /* isSignedIn */);
		 mConnectionResult = result;
		 connected=false;
		
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub

		
		String personName;
		Image personPhoto;
	
		
		 Person currentPerson = mPlusClient.getCurrentPerson();
		if (mPlusClient.getCurrentPerson() != null) {
			connected=true;
	    	AlertDialog.Builder alert=new AlertDialog.Builder(mthis);
	    	alert.setMessage(
	      
	       currentPerson.getDisplayName() + "\n" +
	       mPlusClient.getAccountName());
	    	alert.show();
	    	 Usuario=currentPerson.getDisplayName();
	    	 cuenta= mPlusClient.getAccountName();
	    	 mSignOutButton.setEnabled(true);
	    	 Intent i = new Intent(mthis, RegistroActivity.class);
	    	 i.putExtra("connected", connected);
	 		 i.putExtra("nombre", Usuario.toString() );
	 		 i.putExtra("correo", cuenta.toString());
	 	
	 		 startActivity(i);
		}
		
		updateButtons(true);
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		connected=false;
		 updateButtons(false );	
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		
				  switch(view.getId()) {
		          case R.id.sign_in_button:
		              int available = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		              if (available != ConnectionResult.SUCCESS) {
		                  showDialog(DIALOG_GET_GOOGLE_PLAY_SERVICES);
		                  return;
		              }

		              try {
		                
		                  mConnectionResult.startResolutionForResult(this, REQUEST_CODE_SIGN_IN);
		                  
		              } catch (IntentSender.SendIntentException e) {
		                  // Fetch a new result to start.
		                  mPlusClient.connect();
		              }
		              break;
		          case R.id.button1:
		        	  if (mPlusClient.isConnected())
			            {
						 mPlusClient.clearDefaultAccount();
						 mPlusClient.disconnect();
						 //mPlusClient.connect();
			 
			                Toast.makeText(LoginActivity.this,
			                    "Sesión Cerrada.",
			                    Toast.LENGTH_LONG).show();
			                SignInButton btnSignIn = (SignInButton)findViewById(R.id.sign_in_button);
			   	    	 btnSignIn.setEnabled(true);
			
			            }
		            
		              break;
				  }
			

		
	}
	 private void updateButtons(boolean isSignedIn) {
	        if (isSignedIn) { 
		    	 mSignInButton.setEnabled(false);
		    	 mSignOutButton.setEnabled(true);
	           
	        } else {
	        	mSignInButton.setEnabled(true);
         	mSignOutButton.setEnabled(false);
	            if (mConnectionResult == null) {
	                // Disable the sign-in button until onConnectionFailed is called with result.
	            	mSignInButton.setEnabled(false);
	            	mSignOutButton.setEnabled(true);
	            } else {
	                // Enable the sign-in button since a connection result is available.
	            	mSignInButton.setEnabled(true);
	            	mSignOutButton.setEnabled(true);
	                
	            }

	            mSignOutButton.setEnabled(true);
	            
	        }
	    }

	
	
}
