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
    private boolean con = false;

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

            }
        });

        btnPantall = (Button) findViewById(R.id.btnPantalla);
        btnPantall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                con = true;

                Validar();

            }
        });


    }

    boolean exist = false;
    private int n = 0;

    public void Validar() {
        con = true;
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        /*refer.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Escritorio n = dataSnapshot.getValue(Escritorio.class);
                //Toast.makeText(getApplicationContext(),"Ocurre "+dataSnapshot.getValue().toString()+dataSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();
                 for (DataSnapshot recorre : dataSnapshot.getChildren()) {
                    Get = recorre.getValue(Escritorio.class);
                    if (Get != null) {
                        Toast.makeText(getApplicationContext(),"Ocurre",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        refer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(), "Llega", Toast.LENGTH_LONG).show();

                if (con) {
                    for (DataSnapshot recorre : dataSnapshot.getChildren()) {
                        n++;
                        Get = recorre.getValue(Escritorio.class);
                        if (Get != null) {
                            Toast.makeText(getApplicationContext(), Get.id, Toast.LENGTH_LONG).show();
                            if (Get.usuario.equals(currentFirebaseUser.getEmail())) {
                                Intent ab = new Intent(getApplicationContext(), PantallaActivity.class);
                                finish();
                                startActivity(ab);
                                exist = true;
                            }
                            if (Get.usuario.equals("")) {
                                Escritorio grab = new Escritorio(Get.id, currentFirebaseUser.getEmail(), Get.numero, Get.cantidad, "");
                                refer.child(Get.id).setValue(grab);
                                exist = true;
                                break;
                            }
                        }
                    }
                    if(exist==false){
                        id=refer.push().getKey();
                        numero=String.valueOf(n);
                        Escritorio grab = new Escritorio(id, currentFirebaseUser.getEmail(),numero, "0", "");
                        refer.child(id).setValue(grab);
                    }
                    con = false;
                    // Escritorio grab=new Escritorio(Tipo,id,numero,fechain,fechafin);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
