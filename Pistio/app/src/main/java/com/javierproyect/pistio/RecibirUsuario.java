package com.javierproyect.pistio;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecibirUsuario {

    Usuario Get;
    private DatabaseReference recibir;

    public RecibirUsuario() {
        recibir= FirebaseDatabase.getInstance().getReference();
        recibir.child("Usuario").child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot recorre:dataSnapshot.getChildren()){
                    Get=recorre.getValue(Usuario.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
