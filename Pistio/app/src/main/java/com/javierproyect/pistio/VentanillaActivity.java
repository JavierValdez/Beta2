package com.javierproyect.pistio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VentanillaActivity extends AppCompatActivity {
    DatabaseReference refer;
    DatabaseReference ticket;
    FirebaseUser currentFirebaseUser;
    private String localid;
    boolean toma =false;
    boolean val=false;
    boolean val1=false;
    String numero;
    String tipe;
    private Button atencion;
    private TextView ventana;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventanilla);
        refer= FirebaseDatabase.getInstance().getReference("Escritorio");
        ticket=FirebaseDatabase.getInstance().getReference("Tickets");
        atencion=(Button) findViewById(R.id.atender);
        Validar();
        ventana=(TextView) findViewById(R.id.ver);
        currentFirebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        atencion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toma=true;
                ticket.child("Urgente").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(toma) {
                            for (DataSnapshot Tick : dataSnapshot.getChildren()) {
                                Ticket n = Tick.getValue(Ticket.class);
                                if(n!=null){
                                    numero=n.numero;
                                    tipe=n.tipo;
                                    ticket.child("Urgente").child(n.id).removeValue();
                                    toma=false;
                                    Actualizar();
                                }
                                break;
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                ticket.child("General").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(toma) {
                            for (DataSnapshot Tick : dataSnapshot.getChildren()) {
                                Ticket n = Tick.getValue(Ticket.class);
                                if(n!=null){
                                    numero=n.numero;
                                    tipe=n.tipo;
                                    ticket.child("General").child(n.id).removeValue();
                                    toma=false;
                                    Actualizar();
                                }
                                break;
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });

    }

    private void Validar() {
        val=true;
        refer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(val) {
                    for (DataSnapshot re : dataSnapshot.getChildren()) {
                        Escritorio n = re.getValue(Escritorio.class);
                        if (n.usuario.equals(currentFirebaseUser.getEmail())) {
                            ventana.setText(n.nticket);
                            localid = n.id;
                            return;
                        }
                    }
                    val=false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void Actualizar() {
        val1=true;
        Toast.makeText(getApplicationContext(),"Actualizacion ",Toast.LENGTH_LONG).show();
        refer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(val1) {
                    for (DataSnapshot re : dataSnapshot.getChildren()) {
                        Escritorio n = re.getValue(Escritorio.class);
                        if (n.usuario.equals(currentFirebaseUser.getEmail())) {
                            n.nticket=tipe+" "+Integer.parseInt(numero);
                            refer.child(localid).setValue(n);
                            break;
                        }
                    }
                    val1=false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
