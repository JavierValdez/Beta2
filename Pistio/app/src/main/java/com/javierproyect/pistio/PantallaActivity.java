package com.javierproyect.pistio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

public class PantallaActivity extends AppCompatActivity {

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference refer;
    FirebaseUser usuarioActual;
    TextView NumeroT;
    TextView NumeroV;


    MediaPlayer cero, uno, dos, tres, cuatro, cinco, seis, siete, ocho, nueve, diez, once, doce, trece, catorce, quince, dieci, veinti,
            treinta, cuarenta, cincuenta, sesenta, setenta, ochenta, noventa, cien, ciento, cientos, mil, urgen, gener, ventani;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla);
        refer = db.getReference("Escritorio");
        usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
        NumeroT = (TextView) findViewById(R.id.mostrarPantallaT);
        NumeroV = (TextView) findViewById(R.id.mostrarPantallaV);
        final File newFile = new File(Environment.getExternalStorageDirectory(), "Nuevo");

        newFile.mkdirs();
        if (newFile.mkdir()) {
            Toast.makeText(getApplicationContext(), "Creado", Toast.LENGTH_LONG).show();
        }
        refer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot re : dataSnapshot.getChildren()) {
                    Escritorio n = re.getValue(Escritorio.class);
                    if (n.usuario.equals(usuarioActual.getEmail())) {
                        if(!NumeroT.getText().toString().equals("Ticket " + n.nticket)){
                            hablar(n.nticket.replace("Urgente", "").replace("General", "").replace(" ",""),n.nticket.charAt(0));
                        }
                        NumeroT.setText("Ticket " + n.nticket);
                        NumeroV.setText("Ventanilla " + n.numero);

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private int unidad=0, decena=0, centena=0, millar=0,numero;

    private void hablar(String n,char l) {

        unidad=0;decena=0;centena=0;millar=0;
        cero = MediaPlayer.create(this, R.raw.cero);
        uno = MediaPlayer.create(this, R.raw.uno);
        dos = MediaPlayer.create(this, R.raw.dos);
        tres = MediaPlayer.create(this, R.raw.tres);
        cuatro = MediaPlayer.create(this, R.raw.cuatro);
        cinco = MediaPlayer.create(this, R.raw.cinco);
        seis = MediaPlayer.create(this, R.raw.seis);
        siete = MediaPlayer.create(this, R.raw.siete);
        ocho = MediaPlayer.create(this, R.raw.ocho);
        nueve = MediaPlayer.create(this, R.raw.nueve);
        diez = MediaPlayer.create(this, R.raw.diez);
        once = MediaPlayer.create(this, R.raw.once);
        doce = MediaPlayer.create(this, R.raw.doce);
        trece = MediaPlayer.create(this, R.raw.trece);
        catorce = MediaPlayer.create(this, R.raw.catorce);
        quince = MediaPlayer.create(this, R.raw.quince);
        dieci = MediaPlayer.create(this, R.raw.dieci);
        veinti = MediaPlayer.create(this, R.raw.veinti);
        treinta = MediaPlayer.create(this, R.raw.treintay);
        cuarenta = MediaPlayer.create(this, R.raw.cuarentay);
        cincuenta = MediaPlayer.create(this, R.raw.cincuentay);
        sesenta = MediaPlayer.create(this, R.raw.sesentay);
        setenta = MediaPlayer.create(this, R.raw.setentay);
        ochenta = MediaPlayer.create(this, R.raw.ochentay);
        noventa = MediaPlayer.create(this, R.raw.noventay);
        cien = MediaPlayer.create(this, R.raw.cien);
        cientos = MediaPlayer.create(this, R.raw.cientos);
        urgen = MediaPlayer.create(this, R.raw.urgente);
        gener = MediaPlayer.create(this, R.raw.general);
        ventani = MediaPlayer.create(this, R.raw.ventanilla);
        mil = MediaPlayer.create(this, R.raw.mil);
        numero = Integer.parseInt(n);
        if(l=='U'){
            urgen.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        if(l=='G'){
            gener.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        if (numero >= 1) {
            unidad = numero % 10;
            numero /= 10;
        }
      if (numero >= 1) {
            decena = numero % 10;
            numero = numero / 10;
        }
        if (numero >= 1) {
            centena = numero % 10;
            numero = numero / 10;
        }
        if (numero >= 1) {
            millar = numero % 10;
            numero = numero / 10;
        }
        if (millar == 1) {
            mil.start();
        } else if (millar == 2) {
            dos.start();
        } else if (millar == 3) {
            tres.start();
        } else if (millar == 4) {
            cuatro.start();
        } else if (millar == 5) {
            cinco.start();
        } else if (millar == 6) {
            seis.start();
        } else if (millar == 7) {
            siete.start();
        } else if (millar == 8) {
            ocho.start();
        } else if (millar == 9) {
            nueve.start();
        }

        if (millar > 1) {
            mil.start();
        }
        if(millar>=1){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }


        if (centena == 1 && decena == 0 && unidad == 0) {
            cien.start();
        } else if (centena == 1) {//pendiente ciento
            ciento.start();
        } else if (centena == 2) {
            dos.start();
        } else if (centena == 3) {
            tres.start();
        } else if (centena == 4) {
            cuatro.start();
        } else if (centena == 5) {
            cinco.start();
        } else if (centena == 6) {
            seis.start();
        } else if (centena == 7) {
            siete.start();
        } else if (centena == 8) {
            ocho.start();
        } else if (centena == 9) {
            nueve.start();
        }

        if (centena > 1) {
            cientos.start();
        }
        if(centena>=1){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }



        if (decena == 1 && unidad == 0) {
            diez.start();
        } else if (decena == 1 && unidad == 1) {
            once.start();
        } else if (decena == 1 && unidad == 2) {
            doce.start();
        } else if (decena == 1 && unidad == 3) {
            trece.start();
        } else if (decena == 1 && unidad == 4) {
            catorce.start();
        } else if (decena == 1 && unidad == 5) {
            quince.start();
        } else if (decena == 1 && unidad > 5) {
            dieci.start();
        }

        if (decena == 2) {
            veinti.start();
        } else if (decena == 3) {
            treinta.start();
        } else if (decena == 4) {
            cuarenta.start();
        } else if (decena == 5) {
            cincuenta.start();
        } else if (decena == 6) {
            sesenta.start();
        } else if (decena == 7) {
            setenta.start();
        } else if (decena == 8) {
            ochenta.start();
        } else if (decena == 9) {
            noventa.start();
        }
        if(decena>=1){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }


        if (unidad == 1 && decena != 1) {
            uno.start();
        } else if (unidad == 2 && decena != 1) {
            dos.start();
        } else if (unidad == 3 && decena != 1) {
            tres.start();
        } else if (unidad == 4 && decena != 1) {
            cuatro.start();
        } else if (unidad == 5 && decena != 1) {
            cinco.start();
        } else if (unidad == 6) {
            seis.start();
        } else if (unidad == 7) {
            siete.start();
        } else if (unidad == 8) {
            ocho.start();
        } else if (unidad == 9) {
            nueve.start();
        }

    }
}
