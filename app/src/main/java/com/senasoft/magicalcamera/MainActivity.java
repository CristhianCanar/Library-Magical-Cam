package com.senasoft.magicalcamera;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;
import com.frosquivel.magicalcamera.Utilities.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivCamara;
    private Button btnTomar, btnSeleccionar, btnIrFoto;
    private TextView txtTitulo;

    private MagicalCamera magicalCamera;
    private MagicalPermissions magicalPermissions;
    private int RESIZE_PHOTO_PIXELS_PERCENTAGE = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] permisos = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

        magicalPermissions = new MagicalPermissions(this,permisos);
        magicalCamera = new MagicalCamera(this,RESIZE_PHOTO_PIXELS_PERCENTAGE,magicalPermissions);

        ivCamara = findViewById(R.id.ivCamara);
        btnTomar = findViewById(R.id.btnTomarFoto);
        btnTomar.setOnClickListener(this);
        btnSeleccionar=findViewById(R.id.btnSeleccionarFoto);
        btnSeleccionar.setOnClickListener(this);
        btnIrFoto=findViewById(R.id.btnIrFoto);
        btnIrFoto.setOnClickListener(this);
        txtTitulo = findViewById(R.id.tvTitulo);

        txtTitulo.setText("Activity Example");
        btnIrFoto.setText("Go to Fragment");





    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTomarFoto:
                magicalCamera.takePhoto();
                break;

            case R.id.btnSeleccionarFoto:

                magicalCamera.selectedPicture("Seleccione el que mas le agrade");
                break;

            case R.id.btnIrFoto:
                startActivity(new Intent(getApplicationContext(), fragmentImagenes.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        magicalCamera.resultPhoto(requestCode,resultCode,data);
        ivCamara.setImageBitmap(magicalCamera.getPhoto());

        String cod =magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(),"myTestPhoto",
                "Pictures",MagicalCamera.JPEG,true);
        if (cod!=null){
            Toast.makeText(this, "Se guardo la foto", Toast.LENGTH_SHORT).show();
        }else if (cod==null){
            Toast.makeText(this, "No se guardo la fot :(", Toast.LENGTH_SHORT).show();
        }

    }
}
