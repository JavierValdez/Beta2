package com.javierproyect.pistio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Generador extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef ,refer;
    private Spinner tipoTicket;
    private Button generarTicket;
    private Ticket nuevo;
    private ProgressDialog progres;
    String Tipo;
    String id;
    String numero="0";
    String fechain;
    String fechafin;
    boolean con=false;
    private PDF GenerarPDF;
    int n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_generador);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        refer = database.getReference("Usuario");
        GenerarPDF=new PDF(getApplicationContext());

        tipoTicket=(Spinner)findViewById(R.id.TipoTicket);
        generarTicket=(Button)findViewById(R.id.CrearTicket);

        generarTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Tipo=tipoTicket.getSelectedItem().toString();
                 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                String currentDateandTime = simpleDateFormat.format(new Date());

                fechafin="";
                fechain = currentDateandTime;
                id = myRef.push().getKey();
                progres = new ProgressDialog(Generador.this);
                progres.setMessage("Creando Ticket");
                progres.show();
                Almacenar();

                progres.dismiss();

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
                             numero = Get.numero;
                            n = Integer.parseInt(numero);
                            Log.d("anc", " " + n);
                            n++;
                            numero = String.valueOf(n);

                        }
                    }
                    con=false;
                    Ticket grab=new Ticket(Tipo,id,numero,fechain,fechafin);

                    myRef.child("Tickets").child(Tipo).child(id).setValue(grab);
                    try {
                        GenerarPDF.mPDF("TICKET DE ATENCIÓN",Tipo+"   "+numero,fechain,id);
                        Toast.makeText(getApplicationContext(), "Creado "+Tipo+" "+numero, Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
