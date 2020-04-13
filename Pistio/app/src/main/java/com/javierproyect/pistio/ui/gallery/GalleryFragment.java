package com.javierproyect.pistio.ui.gallery;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.javierproyect.pistio.Base;
import com.javierproyect.pistio.R;

public class GalleryFragment extends Fragment {
    private View root;
    private GalleryViewModel galleryViewModel;
    private Button btndelete;
    private ProgressDialog progres;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        root = inflater.inflate(R.layout.fragment_gallery, container, false);
        btndelete=(Button) root.findViewById(R.id.eliminarUsuario);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progres = new ProgressDialog(root.getContext());
                progres.setMessage("Verificando sus datos");
                progres.show();
                Base admin=new Base(root,getContext());
                admin.Logear();
                progres.dismiss();
            }
        });

        return root;
    }
}
