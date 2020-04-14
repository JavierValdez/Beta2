package com.javierproyect.pistio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRegistrar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.PrivateKey;

import static android.widget.Toast.makeText;

public class IngresoActivity extends AppCompatActivity {
    FirebaseAuthRegistrar Aut;
    private boolean permise;
    private FirebaseAuth Autenticacion;
    private DatabaseReference recibir;
    private String us, ke;
    private Button cargar;
    private EditText User;
    private EditText Key;
    private TextView hide;
    private FirebaseUser verify;
    private boolean login = false;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference("Usuario");
    private ProgressDialog progres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);
        Autenticacion = FirebaseAuth.getInstance();
permise=false;
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

    @Override
    protected void onStop() {
        super.onStop();
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
        recibir = FirebaseDatabase.getInstance().getReference();
        User = (EditText) findViewById(R.id.editText1);
        Key = (EditText) findViewById(R.id.editText4);
        us = String.valueOf(User.getText()).replace(" ","").toLowerCase();
        ke = String.valueOf(Key.getText()).replace(" ","");;
        progres = new ProgressDialog(IngresoActivity.this);
        progres.setMessage("Verificando sus datos");
        progres.show();
        if (TextUtils.isEmpty(us) || TextUtils.isEmpty(ke)) {

            Toast.makeText(getApplicationContext(), "Contraseña o Email Vacio", Toast.LENGTH_SHORT).show();
            progres.dismiss();
            Intent a = getIntent();
            finish();
            startActivity(a);
        } else {
            Autenticacion.signInWithEmailAndPassword(us, ke)
                    .addOnCompleteListener(IngresoActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                verify= Autenticacion.getCurrentUser();
                                if(verify.isEmailVerified()){
                                    permise=true;
                                    RolUsuario();
                                }else{
                                    progres.dismiss();
                                    Toast.makeText(getApplicationContext(),"Verifique su correo, para continuar",Toast.LENGTH_LONG).show();
                                }


                            } else {
                                progres.dismiss();
                                Toast.makeText(getApplicationContext(), "Correo o contraseña Invalidos", Toast.LENGTH_SHORT).show();
                                Intent a = getIntent();
                                finish();
                                startActivity(a);

                            }
                        }
                    });
        }

    }

    private void RolUsuario() {
        recibir.child("Usuario").child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(permise)
                for (DataSnapshot recorre : dataSnapshot.getChildren()) {

                    Usuario Get = recorre.getValue(Usuario.class);

                    if (Get != null)
                        if (User.getText().toString().replace(" ","").toLowerCase().equals(Get.user)) {
                            progres.dismiss();
                            if (Get.type.equals("Administrador")) {
                                Intent ab = new Intent(IngresoActivity.this, AdminActivity.class);
                                finish();
                                permise=false;
                                startActivity(ab);

                            }else if(Get.type.equals("Escritorio")){
                                Intent ab = new Intent(IngresoActivity.this, Generador.class);
                                finish();
                                permise=false;
                                startActivity(ab);
                            }

                        }
                }
                permise=false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
