package com.javierproyect.pistio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.widget.Toast.makeText;

public class IngresoActivity extends AppCompatActivity {

    private DatabaseReference recibir;
    private String us,ke;
    private Button cargar;
    private EditText User;
    private EditText Key;
    private TextView hide;
    private boolean login=false;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference("Usuario");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);
        cargar = (Button) findViewById(R.id.button1);
        MostrarClave();

        cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // makeText(getApplicationContext(), "Presionar ", Toast.LENGTH_SHORT).show();

                RecibirUsuario();
            }
        });


// Write a message to the database


    }

    private void MostrarClave() {
        hide = (TextView) findViewById(R.id.hiden);
        Key = (EditText) findViewById(R.id.editText4);
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //145 Texto sin Ocultar
                //129 Texto Oculto
                int n = Key.getInputType();
                if (n == 129) {
                    Key.setInputType(145);
                } else {
                    Key.setInputType(129);
                }
            }
        });
    }

   /* private void GrabarUsuario() {
        String user, key, id = "", type = "";
        User = (TextView) findViewById(R.id.editText1);
        Key = (TextView) findViewById(R.id.editText4);
        user = String.valueOf(User.getText());
        key = String.valueOf(Key.getText());
        id = myRef.push().getKey();
        Usuario grab = new Usuario(user, key, id, type);
        myRef.child("Users").child(id).setValue(grab);
    }*/

    public void RecibirUsuario() {
        recibir= FirebaseDatabase.getInstance().getReference();
        User = (EditText) findViewById(R.id.editText1);
        Key = (EditText) findViewById(R.id.editText4);
        us = String.valueOf(User.getText());
        ke = String.valueOf(Key.getText());
         recibir.child("Usuario").child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot recorre:dataSnapshot.getChildren()){
                    Usuario Get=recorre.getValue(Usuario.class);
                    /*if(Get.key.equals()&&Get.User.equals(String.valueOf(User.getText()))){
                        Toast.makeText(getApplicationContext(),Get.id+" "+ Key.getText(),Toast.LENGTH_SHORT).show();
                        login= false;
                    }*/

                        if (Get != null)
                            if (ke.equals (Get.key)&&us.equals(Get.user)){
                            Toast.makeText(getApplicationContext(), Get.user + " " + Key.getText(), Toast.LENGTH_SHORT).show();
                            if(Get.type.equals("Administrador")){
                                Intent ab = new Intent(IngresoActivity.this, AdminActivity.class);
                                startActivity(ab);
                            }

                        }





                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
