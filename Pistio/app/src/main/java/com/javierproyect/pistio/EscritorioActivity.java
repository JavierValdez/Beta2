package com.javierproyect.pistio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EscritorioActivity extends AppCompatActivity {

    private Button btnEscritori;
    private Button btnPantall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escritorio);
        btnEscritori=(Button) findViewById(R.id.btnEscritorio);
        btnEscritori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnPantall=(Button) findViewById(R.id.btnPantalla);
        btnPantall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ab = new Intent(getApplicationContext(),PantallaActivity.class);
                startActivity(ab);
            }
        });

    }
}
