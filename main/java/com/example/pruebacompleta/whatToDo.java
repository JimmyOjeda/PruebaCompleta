package com.example.pruebacompleta;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link whatToDo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link whatToDo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class whatToDo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public void onStart() {

        super.onStart();
        ((MainActivity)getActivity()).cargarDatos();
    }

    public whatToDo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment whatToDo.
     */
    // TODO: Rename and change types and number of parameters
    public static whatToDo newInstance(String param1, String param2) {
        whatToDo fragment = new whatToDo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_what_to_do, container, false);


        /**
         *Metodo para redireccionar a MainActivity eliminando una actividad.
         * @param v view
         */
        Button buttonDeleto = (Button)view.findViewById(R.id.buttonDel);
        buttonDeleto.setOnClickListener(new View.OnClickListener() {
            /**
             * Metodo OnClick para redigirir a MainActivity y agendar actividad
             * @param v View
             */
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.eliminarActividad();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        /**
         *Metodo para redireccionar a MainActivity terminando una actividad
         * @param v view
         */
        Button buttonDone = (Button)view.findViewById(R.id.buttonDon);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            /**
             * Metodo OnClick para redigirir a MainActivity y agendar actividad
             * @param v View
             */
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.terminarActividad();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        /**
         *Metodo para redireccionar a MainActivity marcando una actividad como incompleta.
         * @param v view
         */
        Button buttonGtfo = (Button)view.findViewById(R.id.buttonNot);
        buttonGtfo.setOnClickListener(new View.OnClickListener() {
            /**
             * Metodo OnClick para redigirir a MainActivity y agendar actividad
             * @param v View
             */
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.actividadIncompleta();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
