package com.javierproyect.pistio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Generador extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef ,refer;
    private Spinner tipoTicket;
    private Button generarTicket;
    private Ticket nuevo;
    String Tipo;
    String id;
    String numero="0";
    String fechain;
    String fechafin;
    boolean con=false;
    int n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_generador);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        refer = database.getReference("Usuario");

        tipoTicket=(Spinner)findViewById(R.id.TipoTicket);
        generarTicket=(Button)findViewById(R.id.CrearTicket);

        generarTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Tipo=tipoTicket.getSelectedItem().toString();

                //Ticket n= new Ticket("",id, "","", "");
                //Usuario ab=new Usuario("javie",id,"abc");
                //myRef.child("Tickets").child("General").child(id).setValue(n);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String currentDateandTime = simpleDateFormat.format(new Date());

                fechafin="";
                fechain = currentDateandTime;
                id = myRef.push().getKey();
                Tipo=tipoTicket.getSelectedItem().toString();

                Almacenar();

            }
        });
    }



    public void Almacenar(){
        con=true;
        if(Tipo.equals("General")){
            Conteo("General");

        }else{
            Conteo("Urgente");
              //myRef.child("Tickets").child("Urgente").child(nuevo.id).setValue(nuevo);
        }

    }

    public void Conteo(String c) {

        myRef.child("Tickets").child(Tipo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(con) {
                    for (DataSnapshot recorre : dataSnapshot.getChildren()) {
                        Ticket Get = recorre.getValue(Ticket.class);
                        if (Get != null) {
                            Toast.makeText(getApplicationContext(), Get.id, Toast.LENGTH_LONG).show();
                            numero = Get.numero;
                            n = Integer.parseInt(numero);
                            Log.d("anc", " " + n);
                            n++;
                            numero = String.valueOf(n);
                            Toast.makeText(getApplicationContext(), Get.id+" "+numero, Toast.LENGTH_LONG).show();

                        }
                    }
                    con=false;
                    Ticket grab=new Ticket(Tipo,id,numero,fechain,fechafin);
                    myRef.child("Tickets").child(Tipo).child(id).setValue(grab);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
