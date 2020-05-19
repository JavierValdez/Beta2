package com.javierproyect.pistio;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Base {
    private Usuario nuevo;
    DatabaseReference EditarBD;


    FirebaseAuth autenticacion;


    String Usuario, Clave;
    EditText Usuari, Clav;
    Context cont;
    View view;
    ProgressDialog progres;
    boolean permise;
    boolean permise1;
    private DatabaseReference escritorios;
    private DatabaseReference recibir;


    public Base(View view, Context cont) {
        autenticacion = FirebaseAuth.getInstance();
        this.view = view;
        this.cont = cont;
        Usuari = (EditText) view.findViewById(R.id.DelUser);
        Clav = (EditText) view.findViewById(R.id.DelPass);
        recibir = FirebaseDatabase.getInstance().getReference();
        EditarBD = FirebaseDatabase.getInstance().getReference();
        escritorios = FirebaseDatabase.getInstance().getReference("Escritorio");
    }

    public void BorrarUsuario() {

        FirebaseUser user = autenticacion.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                            Toast.makeText(cont, "Usuario Eliminado Exitosamente", Toast.LENGTH_LONG).show();


                            autenticacion.signOut();

                        }
                    }
                });


    }

    public void Logear() {
        Usuario = Usuari.getText().toString().replace(" ", "");
        Clave = Clav.getText().toString().replace(" ", "");


        if (TextUtils.isEmpty(Usuario) || TextUtils.isEmpty(Clave)) {
            Toast.makeText(cont, "Rellene la informacion solicitada", Toast.LENGTH_SHORT).show();
        } else {
            autenticacion.signInWithEmailAndPassword(Usuario, Clave)
                    .addOnCompleteListener((Activity) cont, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                BorrarRoll();
                                BorrarUsuario();
                            } else {

                                Toast.makeText(cont, "Correo o contraseña Invalidos", Toast.LENGTH_SHORT).show();
                                limpiar();

                            }
                        }
                    });
        }
    }

    public void limpiar() {
        Usuari.setText("");
        Clav.setText("");
    }

    public void BorrarRoll() {
        permise = true;
        permise1 = true;
        recibir.child("Usuario").child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(cont, "BD ROLL", Toast.LENGTH_LONG).show();

                if (permise)
                    for (DataSnapshot recorre : dataSnapshot.getChildren()) {
                        Usuario Get = recorre.getValue(Usuario.class);
                        if (Get != null)
                            if (Usuario.equals(Get.user)) {
                                recorre.getKey();
                                nuevo = new Usuario(Get.user, Get.id, Get.type);
                                recibir.child("Usuario").child("Users").child(Get.id).removeValue();

                                break;
                            }
                    }

                permise = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        escritorios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (permise1) {
                    for (DataSnapshot escri : dataSnapshot.getChildren()) {
                        Escritorio nuevo = escri.getValue(Escritorio.class);
                        if (Usuario.equals(nuevo.usuario)) {
                            nuevo.usuario="";
                            nuevo.numero="0";
                            escritorios.child(nuevo.id).setValue(nuevo);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        limpiar();


    }


}
