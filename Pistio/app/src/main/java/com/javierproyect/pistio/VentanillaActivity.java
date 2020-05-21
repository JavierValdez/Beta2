package com.javierproyect.pistio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.MediaPlayer;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class VentanillaActivity extends AppCompatActivity {
    DatabaseReference refer;
    DatabaseReference ticket;
    DatabaseReference ticket1;
    FirebaseUser currentFirebaseUser;
    private String localid;
    boolean toma = false;
    boolean toma1 = false;
    boolean val = false;
    boolean val1 = false;
    String numero;
    int contador;
    String tipe;
    private Button atencion,llamar;
    private TextView ventana;
    private String hora1;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventanilla);
        refer = FirebaseDatabase.getInstance().getReference("Escritorio");
        ticket = FirebaseDatabase.getInstance().getReference("Tickets");
        ticket1 = FirebaseDatabase.getInstance().getReference("Tickets");
        atencion = (Button) findViewById(R.id.atender);
        llamar=(Button)findViewById(R.id.Llamada);
        mp = MediaPlayer.create(this, R.raw.alarm);
        contador=0;
        Validar();
        ventana = (TextView) findViewById(R.id.ver);
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador++;
                if(contador==4){
                    clickear();
                }else {
                    Toast.makeText(getApplicationContext(), "LLamada No " + contador, Toast.LENGTH_SHORT).show();

                }
            }
        });
        atencion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickear();
            }

        });

    }

    private void clickear(){

        toma = true;
        ticket.child("Urgente").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (toma) {
                    for (DataSnapshot Tick : dataSnapshot.getChildren()) {
                        Ticket n = Tick.getValue(Ticket.class);
                        if (n != null) {
                            numero = n.numero;
                            tipe = n.tipo;
                            ticket.child("Urgente").child(n.id).removeValue();
                            toma = false;
                            sonar();
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
                if (toma) {
                    for (DataSnapshot Tick : dataSnapshot.getChildren()) {
                        Ticket n = Tick.getValue(Ticket.class);
                        if (n != null) {
                            numero = n.numero;
                            tipe = n.tipo;
                            ticket.child("General").child(n.id).removeValue();
                            toma = false;
                            sonar();
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



    private void sonar(){
        toma1 = true;

        ticket1.child("Urgente").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (toma1) {
                    for (DataSnapshot Tick : dataSnapshot.getChildren()) {
                        Ticket n = Tick.getValue(Ticket.class);
                        if (n != null) {
                            hora1=n.fechain;
                            toma1 = false;
                            Alerta();
                        }
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        toma1 = true;

        ticket1.child("General").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (toma1) {
                    for (DataSnapshot Tick : dataSnapshot.getChildren()) {
                        Ticket n = Tick.getValue(Ticket.class);
                        if (n != null) {

                            hora1 = n.fechain;
                            toma1 = false;
                            Alerta();
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

    private void Alerta() {
        String hora2;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        String currentDateandTime = simpleDateFormat.format(new Date());
        hora2 = currentDateandTime;
        int h1, m1, s1, h2, m2, s2, ht = 0, mt = 0, st = 0;

        h1 = Character.getNumericValue(hora1.charAt(0));
        h1 = (h1 * 10) + Character.getNumericValue(hora1.charAt(1));
        m1 = Character.getNumericValue(hora1.charAt(3));
        m1 = (m1 * 10) + Character.getNumericValue(hora1.charAt(4));
        s1 = Character.getNumericValue(hora1.charAt(6));
        s1 = (s1 * 10) + Character.getNumericValue(hora1.charAt(7));

        h2 = Character.getNumericValue(hora2.charAt(0));
        h2 = (h2 * 10) + Character.getNumericValue(hora2.charAt(1));
        m2 = Character.getNumericValue(hora2.charAt(3));
        m2 = (m2 * 10) + Character.getNumericValue(hora2.charAt(4));
        s2 = Character.getNumericValue(hora2.charAt(6));
        s2 = (s2 * 10) + Character.getNumericValue(hora2.charAt(7));
        if (h1 == h2) {
            ht = 0;
            mt = m2 - m1;
            if (s1 <= s2) {
                st = s2 - s1;
            } else {
                mt--;
                st = 60 - (s1 - s2);
            }
        } else if (h1 < h2) {
            ht = h2 - h1;
            if (m1 <= m2) {
                mt = m2 - m1;
                if (s1 <= s2) {
                    st = s2 - s1;
                } else {
                    mt--;
                    st = 60 - (s1 - s2);
                }
            } else {
                ht--;
                mt = 60 - (m1 - m2);
                if (s1 <= s2) {
                    st = s2 - s1;
                } else {
                    mt--;
                    st = 60 - (s1 - s2);
                }
            }
        }
        if(mt>=3){
            mp.start();
            Toast.makeText(getApplicationContext(),"Tickets con Demasiada Espera ",Toast.LENGTH_SHORT).show();

        }
    }


    private void Validar() {
        val = true;
        refer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (val) {
                    for (DataSnapshot re : dataSnapshot.getChildren()) {
                        Escritorio n = re.getValue(Escritorio.class);
                        if (n.usuario.equals(currentFirebaseUser.getEmail())) {
                            ventana.setText(n.nticket);
                            localid = n.id;
                            return;
                        }
                    }
                    val = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Actualizar() {
        val1 = true;
        toma=false;
        Toast.makeText(getApplicationContext(), "Actualizacion ", Toast.LENGTH_LONG).show();
        refer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (val1) {
                    for (DataSnapshot re : dataSnapshot.getChildren()) {
                        Escritorio n = re.getValue(Escritorio.class);
                        if (n.usuario.equals(currentFirebaseUser.getEmail())) {
                            n.nticket = tipe + " " + numero;
                            n.cantidad=String.valueOf(Integer.parseInt(n.cantidad)+1);
                            refer.child(localid).setValue(n);
                            break;
                        }
                    }
                    val1 = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
