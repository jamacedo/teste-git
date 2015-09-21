package com.example.sandorferreira.services;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.nio.ByteBuffer;


public class CadastroActivity extends AppCompatActivity{

    public static final String TAG = "CadastroActivity";
    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_LOCATION = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    byte[] filePhoto;
    CheckBox checkBox;
    Button buttonFoto;
    Button buttonEnviar;
    Location location;
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        final EditText registerName = (EditText) findViewById(R.id.editTextName);
        final EditText registerEmail = (EditText) findViewById(R.id.editTextEmail);
        final EditText registerAssunto = (EditText) findViewById(R.id.editTextAssunto);
        final EditText registerDescricao = (EditText) findViewById(R.id.editTextDescricao);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        buttonEnviar = (Button) findViewById(R.id.buttonEnviar);
        buttonFoto = (Button) findViewById(R.id.buttonFoto);


        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome, email, descricao, assunto;
                nome = registerName.getText().toString();
                email = registerEmail.getText().toString();
                descricao = registerDescricao.getText().toString();
                assunto = registerAssunto.getText().toString();

                ParseObject cadastro = new ParseObject("Monitoramento");
                cadastro.put("nome", nome);
                cadastro.put("email", email);
                cadastro.put("descricao", descricao);
                cadastro.put("assunto", assunto);

                final ProgressDialog dialog = ProgressDialog.show(CadastroActivity.this, "",
                        "Enviando. Aguarde...", true);


                if (checkBox.isChecked() && latitude != 0 && longitude != 0) {
                    cadastro.put("latLong", new ParseGeoPoint(latitude, longitude));
                }
                if (filePhoto != null) {
                    cadastro.put("foto", new ParseFile(filePhoto));
                }

                dialog.onStart();
                cadastro.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        dialog.dismiss();
                        Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso!",
                                Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                });

            }
        });

    }


    public void clickButtonFoto(){

        //requisitar permissao antes da call
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView imageView = (ImageView) findViewById(R.id.imageViewCamera);
            imageView.setImageBitmap(imageBitmap);
            ByteBuffer buffer = ByteBuffer.allocate(imageBitmap.getByteCount());
            imageBitmap.copyPixelsToBuffer(buffer);
            filePhoto = buffer.array();

        }
    }


    public void clickCheckBox(View v){
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enable = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enable) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        //requisitar permissao aqui apost inicializaçao do service
        showLocation();
    }


    public void showLocation(){
        Log.i(TAG, "CheckBox clicado para localizacao");
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED){
            requestLocationPermission();
        }else{
            //Permissao dada. Mostrar localizacao
            Log.i(TAG, "LOCATION: Pemissao ja garantida. Mostrar coordenadas");
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_LOW);
            String provider = locationManager.getBestProvider(criteria, false);
            location = locationManager.getLastKnownLocation(provider);

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.d("CursoAndroid", "lat: " + location.getLatitude() + " long: " +
                        location.getLongitude());
            } else {
                Log.d("CursoAndroid", "Lat e Long indiponiveis");
            }

        }
    }


    public void requestLocationPermission(){
        Log.i(TAG,"Permissao de Localizacao nao dada. Requisitar");
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                && ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
            Log.i(TAG, "entrou no if do requestLocationPermission");
            Snackbar.make(findViewById(R.id.activity_cadastro), "Permissao GPS necessaria", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(CadastroActivity.this, new
                                    String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                            ActivityCompat.requestPermissions(CadastroActivity.this, new
                            String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
                            }

                    }).show();
        }else{
            Log.i(TAG, "entrou no else do requestLocation");
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission_group.LOCATION}, REQUEST_LOCATION);

        }
    }

    public void showCamera(View v){
        Log.i(TAG, "Show camera button pressed. Checking permission.");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            requestCameraPermission();

        } else {
            // Permissao dada. Abrir camera.
            Log.i(TAG,
                    "CAMERA: permissao ja garantida. Abrir camera");
            clickButtonFoto();
        }
    }

    private void requestCameraPermission(){
        Log.i(TAG, "Permissao ainda nao dada, requisitar");
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)){
            Log.i(TAG, "entrou no if do requestcamera");
            Snackbar.make(findViewById(R.id.activity_cadastro), "permissao da camera necessaria", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(CadastroActivity.this,
                                    new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA );
                        }
                    }).show();
        }else{
            Log.i(TAG, "entrou no else do requestcamera");
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
    }

}





