package com.javierproyect.pistio.ui.home;

import android.os.Bundle;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.javierproyect.pistio.R;
import com.javierproyect.pistio.Usuario;

import java.lang.ref.Reference;
import java.security.Key;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private CheckBox hide;
    private EditText usuarioReg;
    private EditText Key;
    private EditText Key1;
    private Button btncarga;
    private View view;
    private Spinner select;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference("Usuario");
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        btncarga = view.findViewById(R.id.buttoncrear);
        MostrarClaves(view);
        btncarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GrabarUsuarios();
                Toast.makeText(getContext(), "Nuevo fragment", Toast.LENGTH_LONG).show();

            }
        });
        return view;
    }

    private void MostrarClaves(View view) {

        hide = (CheckBox) view.findViewById(R.id.CrearVer);
        Key = (EditText) view.findViewById(R.id.keyCrear);
        Key1 = (EditText) view.findViewById(R.id.keyAdmin);
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //145 Texto sin Ocultar
                //129 Texto Oculto
                int n = Key.getInputType();
                if (n == 129) {
                    Key.setInputType(145);
                    Key1.setInputType(145);
                } else {
                    Key1.setInputType(129);
                    Key.setInputType(129);
                }
            }
        });
    }

    private void GrabarUsuarios() {
        Key = (EditText) view.findViewById(R.id.keyCrear);
        Key1 = (EditText) view.findViewById(R.id.keyAdmin);
        usuarioReg=(EditText) view.findViewById(R.id.usuarioCrear);
        select=(Spinner) view.findViewById(R.id.TipoUser);
        String a=select.getSelectedItem().toString();
        Toast.makeText(getContext(),a,Toast.LENGTH_LONG).show();
        String user, key, id = "", type = "";
        user = String.valueOf(usuarioReg.getText());
        key = String.valueOf(Key.getText());
        id = myRef.push().getKey();
        Usuario grab = new Usuario(user, key, id, a);
        myRef.child("Users").child(id).setValue(grab);
    }




}
