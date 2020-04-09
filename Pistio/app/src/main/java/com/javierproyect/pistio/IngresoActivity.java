package com.javierproyect.pistio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import static android.widget.Toast.makeText;

public class IngresoActivity extends AppCompatActivity {

    private Button cargar;
    private TextView User;
    private TextView Key;
    private TextView hide;
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
                GrabarUsuario();

            }
        });


// Write a message to the database


    }

    private void MostrarClave() {
        hide = (TextView) findViewById(R.id.hiden);
        Key = (TextView) findViewById(R.id.editText4);
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

    private void GrabarUsuario() {
        String user, key, id = "", type = "";
        User = (TextView) findViewById(R.id.editText1);
        Key = (TextView) findViewById(R.id.editText4);
        user = String.valueOf(User.getText());
        key = String.valueOf(Key.getText());
        id = myRef.push().getKey();
        Usuario grab = new Usuario(user, key, id, type);
        myRef.child("Users").child(id).setValue(grab);
    }


}
