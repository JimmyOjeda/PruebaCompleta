package com.example.pruebacompleta;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

//Clase para mostrar el layout de AÑADIR ACTIVIDAD
public class Fragment02 extends Fragment implements View.OnClickListener {

    Button b_date, b_time;
    EditText et_date, et_time;
    private int day,month,year,hour,minutes;
    private String date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment02, container, false);

        //Creación del DateTimePicker para elegir fecha y hora para la realizacion de la actividad
        b_date = (Button)view.findViewById(R.id.b_date);
        b_time = (Button)view.findViewById(R.id.b_time);
        et_date = (EditText)view.findViewById(R.id.et_date);
        et_time = (EditText)view.findViewById(R.id.et_time);

        b_date.setOnClickListener(this);
        b_time.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
        if(v==b_date){
            final Calendar c = Calendar.getInstance();
            day = c.get(Calendar.DAY_OF_MONTH);
            month = c.get(Calendar.MONTH);
            year = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    date = String.valueOf(dayOfMonth) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
                    et_date.setText(date);
                }
            },day,month,year);
            datePickerDialog.show();
        }


        if(v==b_time){


        }
    }
}
