package com.javierproyect.pistio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EscritorioActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private DatabaseReference refer;
    private Button btnEscritori;
    private Button btnPantall;
    private Escritorio Get;
    private boolean conV = false;
    private boolean conP = false;

    private String id;
    private String usuario;
    private String numero = "1";
    private String cantidad;
    private String nticket;

    private FirebaseUser currentFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escritorio);
        db = FirebaseDatabase.getInstance();
        refer = db.getReference("Escritorio");
        btnEscritori = (Button) findViewById(R.id.btnEscritorio);
        btnEscritori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conV=true;
                Validar();
            }
        });

        btnPantall = (Button) findViewById(R.id.btnPantalla);
        btnPantall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conP = true;

                Validar();

            }
        });


    }

    boolean exist = false;
    private int n = 0;

    public void Validar() {
         currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        refer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (conP||conV) {
                    for (DataSnapshot recorre : dataSnapshot.getChildren()) {
                        n++;
                        Get = recorre.getValue(Escritorio.class);
                        if (Get != null) {
                            if (Get.usuario.equals(currentFirebaseUser.getEmail())) {
                                if(conP){
                                    Intent ab = new Intent(getApplicationContext(), PantallaActivity.class);
                                    finish();
                                    startActivity(ab);

                                }else if(conV){
                                    Intent ab = new Intent(getApplicationContext(), VentanillaActivity.class);
                                    finish();
                                    startActivity(ab);

                                }

                                exist = true;
                                break;
                            }
                            if (Get.usuario.equals("")) {
                                Escritorio grab = new Escritorio(Get.id, currentFirebaseUser.getEmail(), Get.numero, Get.cantidad, "");
                                refer.child(Get.id).setValue(grab);
                                exist = true;
                                if(conP){
                                    Intent ab = new Intent(getApplicationContext(), PantallaActivity.class);
                                    finish();
                                    startActivity(ab);
                                }else if(conV) {
                                    Intent ab = new Intent(getApplicationContext(), VentanillaActivity.class);
                                    finish();
                                    startActivity(ab);
                                }
                                exist=true;
                                break;
                            }
                        }
                    }
                    if(exist==false){
                        id=refer.push().getKey();
                        numero=String.valueOf(n);
                        Escritorio grab = new Escritorio(id, currentFirebaseUser.getEmail(),numero, "0", "");
                        refer.child(id).setValue(grab);
                        if(conP){
                            Intent ab = new Intent(getApplicationContext(), PantallaActivity.class);
                            finish();
                            startActivity(ab);
                        }else if(conV){
                            Intent ab = new Intent(getApplicationContext(), VentanillaActivity.class);
                            finish();
                            startActivity(ab);
                        }
                    }
                    conP = false;
                    // Escritorio grab=new Escritorio(Tipo,id,numero,fechain,fechafin);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
