package com.javierproyect.pistio;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Base {
    FirebaseDatabase DB;
    FirebaseAuth Auth;
    FirebaseUser User;
    String Usuario,Clave;
    Context cont;

    public Base(final Context cont) {
        FirebaseUser user = Auth.getInstance().getCurrentUser();
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                           Toast.makeText(cont,"Delete",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void BorrarUsuario(){



    }



}
