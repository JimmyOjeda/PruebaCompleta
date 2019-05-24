package com.example.pruebacompleta;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

public class addActivity extends AppCompatActivity implements View.OnClickListener {

    //Variables para DateTimePicker
    Button b_date, b_time;
    EditText et_date, et_time;
    private String date;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //Variables del DateTimePicker para elegir fecha y hora para la realizacion de la actividad
        b_date = (Button) findViewById(R.id.b_date);
        b_time = (Button) findViewById(R.id.b_time);
        et_date = (EditText) findViewById(R.id.et_date);
        et_time = (EditText) findViewById(R.id.et_time);

        b_date.setOnClickListener(this);
        b_time.setOnClickListener(this);

        //Redirigir a MainActivity cuando se oprime "AGREGAR"
        /*Button buttonAdd = (Button)findViewById(R.id.b_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {

        });*/

        //Valores del spinner de prioridad
        Spinner spinner = (Spinner)findViewById(R.id.spinner_priority);
        String[] datos = new String[]{"Alta", "Media", "Baja"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, datos);
        spinner.setAdapter(adapter);
    }

    public void onClickAdd(View v) {
        Intent backToMain = new Intent(this, MainActivity.class);
        startActivity(backToMain);
    }


    //Metodo OnClick para el dateTimePicker
    @Override
    public void onClick(View v) {
        if (v == b_date) {
            final Calendar c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    et_date.setText(dayOfMonth + "/" + month + "/" + year);

                }
            }
                    , day, month, year);
            datePickerDialog.show();
        }

        if (v == b_time) {

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minutes = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    et_time.setText(hourOfDay + ":" + minute);
                }
            }, hour, minutes, false);
            timePickerDialog.show();
        }
    }
}