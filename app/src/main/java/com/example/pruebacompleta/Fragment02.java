package com.example.pruebacompleta;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Clase para mostrar el layout de AÑADIR ACTIVIDAD
 */
public class Fragment02 extends Fragment implements View.OnClickListener {

    //Variables globales para DateTimePicker
    Button b_date, b_time;
    EditText et_date, et_time;
    private String date;
    private String time;

    /**Método onCreate
     *
     * @param inflater Construye los views
     * @param container Inserta el fragment
     * @param savedInstanceState Provee información sobre el estado anterior del fragment
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment02, container, false);

        //Valores del spinner de prioridad
        Spinner spinner = (Spinner)view.findViewById(R.id.spinner_priority);
        String[] datos = new String[]{"Alta", "Media", "Baja"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, datos);
        spinner.setAdapter(adapter);

        //Variables del DateTimePicker para elegir fecha y hora para la realizacion de la actividad
                b_date = (Button)view.findViewById(R.id.b_date);
                b_time = (Button)view.findViewById(R.id.b_time);
                et_date = (EditText)view.findViewById(R.id.et_date);
                et_time = (EditText)view.findViewById(R.id.et_time);

                b_date.setOnClickListener(this);
                b_time.setOnClickListener(this);

        //Redirigir a MainActivity cuando se oprime "AGREGAR"
        Button buttonAdd = (Button)view.findViewById(R.id.b_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            /**
             * Metodo OnClick para redigirir a MainActivity y agendar actividad
             * @param v View
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                MainActivity activity = (MainActivity) getActivity();
                activity.agendarActividad(v);
                startActivity(intent);
            }
        });

        return view;
    }

    /**Metodo OnClick para el dateTimePicker
     *
     * @param v View
     */
    @Override
    public void onClick(View v) {
        if(v==b_date){
            final Calendar c = Calendar.getInstance();
           int day = c.get(Calendar.DAY_OF_MONTH);
           int month = c.get(Calendar.MONTH);
           int year = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener(){
                /**
                 * Método para insertar la fecha en el EditText
                 * @param view View
                 * @param year Variable entera año
                 * @param month Varaiable entera mes
                 * @param dayOfMonth Variable entera dia
                 */
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    date = String.valueOf(dayOfMonth) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
                    et_date.setText(date);
                }
            },year,month,day);
            datePickerDialog.show();
        }

        if(v==b_time){

            final Calendar c = Calendar.getInstance();
           int hour = c.get(Calendar.HOUR_OF_DAY);
           int minutes = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                /**
                 * Metodo para insertar la hora en el EditText
                 * @param view View
                 * @param hourOfDay Variable entera hora
                 * @param minute Variable entera minuto
                 */
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    time = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
                    et_time.setText(time);
                }
            },hour,minutes,false);
            timePickerDialog.show();
        }
    }
}
