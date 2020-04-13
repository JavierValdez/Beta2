package com.javierproyect.pistio.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.javierproyect.pistio.AdminActivity;
import com.javierproyect.pistio.IngresoActivity;
import com.javierproyect.pistio.MainActivity;
import com.javierproyect.pistio.R;
import com.javierproyect.pistio.Usuario;

import java.lang.ref.Reference;
import java.security.Key;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private CheckBox hide;
    private EditText usuarioReg;
    private EditText Key;
    private Button btncarga;
    private View view;
    private Spinner select;
    private FirebaseAuth Autenticacion;
    public Context contexto;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference("Usuario");
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Autenticacion=FirebaseAuth.getInstance();
        view = inflater.inflate(R.layout.fragment_home, container, false);
        btncarga = view.findViewById(R.id.buttoncrear);
        MostrarClaves(view);
        btncarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GrabarUsuarios();


            }
        });


        return view;
    }

    private void MostrarClaves(View view) {

        hide = (CheckBox) view.findViewById(R.id.CrearVer);
        Key = (EditText) view.findViewById(R.id.keyCrear);
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

    private String id;
    private Usuario grab;
    private void GrabarUsuarios() {

        Key = (EditText) view.findViewById(R.id.keyCrear);
        usuarioReg=(EditText) view.findViewById(R.id.usuarioCrear);
        select=(Spinner) view.findViewById(R.id.TipoUser);
        contexto=getContext();
        final String a=select.getSelectedItem().toString();

        String user, key = "", type = "";
        user = String.valueOf(usuarioReg.getText()).trim().toLowerCase();
        key = String.valueOf(Key.getText()).trim();
        id = myRef.push().getKey();
        grab = new Usuario(user, id, a);
        Autenticacion.createUserWithEmailAndPassword(user,key).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser Users=Autenticacion.getCurrentUser();
                    Toast.makeText(contexto,"Creado Exitosamente "+Users.getEmail(),Toast.LENGTH_SHORT).show();
                    Users.sendEmailVerification();
                    myRef.child("Users").child(id).setValue(grab);

                }else {
                    Toast.makeText(contexto,"Correo o cantraseña Invalido",Toast.LENGTH_SHORT).show();

                }
            }
        });
        Key.setText("");
        usuarioReg.setText("");


       /*try{
            Autenticacion.createUserWithEmailAndPassword(user,key);

        }catch (Exception Err){
            Toast.makeText(contexto,"Correo o cantraseña Invalido",Toast.LENGTH_SHORT).show();
        }*/




    }




}
