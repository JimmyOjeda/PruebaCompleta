package com.example.pruebacompleta;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Clase para mostrar el layout de HISTORIAL
 */
public class Fragment03 extends Fragment {

    public void onStart() {

        super.onStart();
        ((MainActivity)getActivity()).cargarHistorial();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment03, container, false);
    }

}
