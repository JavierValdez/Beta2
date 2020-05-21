package com.javierproyect.pistio.ui.slideshow;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.javierproyect.pistio.Escritorio;
import com.javierproyect.pistio.R;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private ListView lista;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference Escritori = database.getInstance().getReference("Escritorio");
    private TextView txtMesa;
    private Button actualizar;
    List<String> items;
    ArrayAdapter ADP;
    boolean permise=false;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        txtMesa= (TextView) root.findViewById(R.id.Mesa);
        actualizar=(Button)root.findViewById(R.id.upda);
        Context contexto= getContext();

        lista=root.findViewById(R.id.lista);
        items=new ArrayList<>();
        assert contexto != null;
        ADP=new ArrayAdapter(contexto,android.R.layout.simple_list_item_1,items);
        lista.setAdapter(ADP);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permise=true;
                items.clear();
                Escritori.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(permise) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                Escritorio mostrar = data.getValue(Escritorio.class);
                                items.add("Cantidad: "+mostrar.getCantidad() + "                                      Mesa: " + mostrar.getNumero());
                                ADP.notifyDataSetChanged();
                            }
                            permise=false;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        return root;
    }
}
