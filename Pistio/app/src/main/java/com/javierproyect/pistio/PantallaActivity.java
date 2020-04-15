package com.javierproyect.pistio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PantallaActivity extends AppCompatActivity {

    FirebaseDatabase db= FirebaseDatabase.getInstance();
    DatabaseReference refer;
    FirebaseUser usuarioActual ;
    TextView NumeroT;
    TextView NumeroV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla);
        refer=db.getReference("Escritorio");
        usuarioActual= FirebaseAuth.getInstance().getCurrentUser();
        NumeroT=(TextView) findViewById(R.id.mostrarPantallaT);
        NumeroV=(TextView) findViewById(R.id.mostrarPantallaV);
        refer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot re : dataSnapshot.getChildren()){
                    Escritorio n= re.getValue(Escritorio.class);
                    if(n.usuario.equals(usuarioActual.getEmail())){
                        NumeroT.setText("Ticket "+n.nticket);
                        NumeroV.setText("Ventanilla "+n.numero);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
