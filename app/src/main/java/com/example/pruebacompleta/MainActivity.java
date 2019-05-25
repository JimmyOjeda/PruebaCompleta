package com.example.pruebacompleta;

import java.util.*;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.ListView;
import android.widget.EditText;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,

        whatToDo.OnFragmentInteractionListener {

    private ArrayList<String> items;
    private ArrayList<String> descs;

    private ArrayList<String> itemsH;
    private ArrayList<String> descsH;

    private ArrayList<String> itemsIncomp;

    List<Map<String, String>>  listArray = new ArrayList<>();
    List<Map<String, String>>  historyArray = new ArrayList<>();

    private SimpleAdapter itemsAdapter;
    private SimpleAdapter historyAdapter;
    private ListView lvItems;
    private ListView lvHistory;

    private int oldPos;
    private boolean listDebounce = false;
    public float puntaje = 0;
    private int dia, mes, year, hora, minutos;

    TextView mensajeProgreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        //LECTURA DE ARCHIVO
        leerActividades();

        //AGENDACION DE ACTIVIDADES
        lvItems = (ListView) findViewById(R.id.lista);

        itemsAdapter = new SimpleAdapter(this, listArray,
                android.R.layout.simple_list_item_2,
                new String[] {"titulo", "detalles" },
                new int[] {android.R.id.text1, android.R.id.text2 });

        lvItems.setAdapter(itemsAdapter);

        actualizarLista(true);
        setupListViewListener();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer == null)
        {
            Intent backToMain = new Intent(this, MainActivity.class);
            backToMain.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(backToMain);
            finish();
            return;
        }

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            listDebounce = false;
        }

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickGoToAdd(View view) {
        Intent GoToAdd = new Intent(this,addActivity.class);
        startActivity(GoToAdd);
        finish();
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager=getSupportFragmentManager();

        if (id == R.id.nav_agenda) {
            Intent backToMain = new Intent(this, MainActivity.class);
            //backToMain.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(backToMain);
            finish();
        } else if (id == R.id.nav_addActivity) {
            Intent GoToAdd = new Intent(this,addActivity.class);
            startActivity(GoToAdd);
            finish();
        } else if (id == R.id.nav_historial) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new Fragment03()).addToBackStack("tag").commit();
        } else if (id == R.id.nav_ajustes) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new Fragment04()).addToBackStack("tag").commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupListViewListener()
    {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id)
                    {
                        if (listDebounce) return false;
                        listDebounce = true;
                        oldPos = pos;
                        FragmentManager fragmentManager=getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.contenedor, new whatToDo()).addToBackStack("tag").commit();
                        return true;
                    }
                }
        );
    }


    /**
     * Metodo para mostrar un string al fragment de eliminar actividad
     */
    public void cargarDatos()
    {
        TextView deleteAct = (TextView)findViewById(R.id.deleteName);
        deleteAct.setText(items.get(oldPos)+" | "+descs.get(oldPos));
    }

    /**
     * Metodo para cargar el historial y asignar puntaje
     */
    public void cargarHistorial()
    {

        if (itemsH.size() != 0)
        {
            float puntaje1 = itemsH.size()-itemsIncomp.size();
            puntaje = (puntaje1/itemsH.size())*100;
        }

        mensajeProgreso= (TextView) findViewById(R.id.feedback);
        if (puntaje==100){
            mensajeProgreso.setText("¡Lo estás haciendo excelente!, sigue así.");
        }else if (puntaje >= 75 && puntaje<100){
            mensajeProgreso.setText("¡Vas bien pero puedes mejorar aún más!.");
        }else if (puntaje >= 50 && puntaje<75){
            mensajeProgreso.setText("Vaya... Has descuidado un poco tus actividades.");
        }else if (puntaje >= 25 && puntaje<50){
            mensajeProgreso.setText("¡Lo estás haciendo mal!, te dejo un tip: No dejes para mañana " +
                    "lo que pudes hacer hoy.");
        }else if (puntaje >= 0 && puntaje<25){
            mensajeProgreso.setText("Parece que no has hecho caso a ninguno de mis avisos...");
        }

        Log.d("CALC", "CALCULO TOTAL:"+itemsH.size());
        Log.d("CALC", "CALCULO SOLO INCOMPLETAS:"+itemsIncomp.size());
        Log.d("CALC", "CALCULO SOLO REALIZADAS:"+(itemsH.size()-itemsIncomp.size()));
        Log.d("CALC", "CALCULO PROMEDIO:"+puntaje);


        TextView points = findViewById(R.id.points);
        points.setText(""+Math.round(puntaje)+" / 100");

        lvHistory = (ListView) findViewById(R.id.historial);

        historyAdapter = new SimpleAdapter(this, historyArray,
                android.R.layout.simple_list_item_2,
                new String[] {"titulo", "detalles" },
                new int[] {android.R.id.text1, android.R.id.text2 });

        lvHistory.setAdapter(historyAdapter);

        //Eliminar el historial despues de 7 dias (Cada domingo)
        Calendar c = Calendar.getInstance();
        c.set(year,mes,dia);
        c.set(Calendar.HOUR_OF_DAY, hora);
        c.set(Calendar.MINUTE, minutos);

        dia = c.get(Calendar.DAY_OF_WEEK);
        hora = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);

        if((dia == Calendar.SUNDAY)&&(hora==23)&&(minutos==59)){
            eliminarHistorial();
        }

        actualizarHistorial();
    }

    /**
     * Metodo para eliminar las actividades
     */
    public void eliminarActividad()
    {
        listDebounce = false;
        items.remove(oldPos);
        descs.remove(oldPos);
        actualizarLista(true);
        escribirActividades();
    }

    /**
     * Metodo para marcar la actividad como incompleta
     */
    public void actividadIncompleta()
    {
        listDebounce = false;
        itemsH.add(itemsH.size(),items.get(oldPos) +" [INCOMPLETA]");
        descsH.add(descsH.size(),descs.get(oldPos));
        itemsIncomp.add(itemsIncomp.size(), items.get(oldPos)+" > "+descs.get(oldPos));
        items.remove(oldPos);
        descs.remove(oldPos);
        actualizarLista(true);
        escribirActividades();
    }

    /**
     * Metodo para terminar las actividades
     */
    public void terminarActividad()
    {
        listDebounce = false;
        itemsH.add(itemsH.size(),items.get(oldPos));
        descsH.add(descsH.size(),descs.get(oldPos));
        items.remove(oldPos);
        descs.remove(oldPos);
        actualizarLista(true);
        escribirActividades();
    }

                
     /**
     * Metodo para eliminar el historial
     */
    public void eliminarHistorial()
    {
        File filesDir = getExternalFilesDir(null);
        File guardadoFile3 = new File(filesDir, "historial.txt");
        File guardadoFile4 = new File(filesDir, "historialDetalles.txt");
        File guardadoFile5 = new File(filesDir, "historialIncompletos.txt");

        guardadoFile3.delete();
        guardadoFile4.delete();
        guardadoFile5.delete();
            
        actualizarHistorial();
    }

    /**
     * Metodo para agendar las actividades
     * @param v View
     */
    public void agendarActividad(View v)
    {
        EditText etNewItem = (EditText) findViewById(R.id.et_nombre);
        EditText etNewDate = (EditText) findViewById(R.id.et_date);
        EditText etNewTime = (EditText) findViewById(R.id.et_time);
        Spinner spNewPriority = (Spinner) findViewById(R.id.spinner_priority);
        String itemText = etNewItem.getText().toString();
        String dateText = etNewDate.getText().toString();
        String timeText = etNewTime.getText().toString();
        String spinnerText = spNewPriority.getSelectedItem().toString();

        String act = ""+itemText+"";
        String desc = ""+dateText+"  |  "+timeText+"  |  "+spinnerText+"";

        items.add(items.size(),act);
        descs.add(descs.size(),desc);
        actualizarLista(true);

        etNewItem.setText("");
        escribirActividades();
    }

    /**
     * Metodo para leer las actividades en un archivo de texto
     */
    private void leerActividades()
    {
        File filesDir = getExternalFilesDir(null);
        File guardadoFile1 = new File(filesDir, "actividades.txt");
        File guardadoFile2 = new File(filesDir, "detalles.txt");
        File guardadoFile3 = new File(filesDir, "historial.txt");
        File guardadoFile4 = new File(filesDir, "historialDetalles.txt");

        File guardadoFile5 = new File(filesDir, "historialIncompletos.txt");
        try
        {
            items = new ArrayList<String>(FileUtils.readLines(guardadoFile1));
            descs = new ArrayList<String>(FileUtils.readLines(guardadoFile2));
            itemsH = new ArrayList<String>(FileUtils.readLines(guardadoFile3));
            descsH = new ArrayList<String>(FileUtils.readLines(guardadoFile4));

            itemsIncomp = new ArrayList<String>(FileUtils.readLines(guardadoFile5));
        } catch (IOException e) {
            items = new ArrayList<String>();
            descs = new ArrayList<String>();
            itemsH = new ArrayList<String>();
            descsH = new ArrayList<String>();
            itemsIncomp = new ArrayList<String>();
            Log.d("ERROR", "Se intento leer actividades");
        }
    }

    /**
     * Metodo para escribir las actividades en un archivo de texto
     */
    private void escribirActividades()
    {
        File filesDir = getExternalFilesDir(null);
        File guardadoFile1 = new File(filesDir, "actividades.txt");
        File guardadoFile2 = new File(filesDir, "detalles.txt");
        File guardadoFile3 = new File(filesDir, "historial.txt");
        File guardadoFile4 = new File(filesDir, "historialDetalles.txt");

        File guardadoFile5 = new File(filesDir, "historialIncompletos.txt");
        try {
            FileUtils.writeLines(guardadoFile1, items);
            FileUtils.writeLines(guardadoFile2, descs);
            FileUtils.writeLines(guardadoFile3, itemsH);
            FileUtils.writeLines(guardadoFile4, descsH);
            FileUtils.writeLines(guardadoFile5, itemsIncomp);
            Log.d("NOTIFICACION", "===================================");
            Log.d("NOTIFICACION", items.toString());
            Log.d("NOTIFICACION", descs.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ERROR", "Se intento escribir actividades");
        }
    }

    /**
     * Metodo para actualizar la lista de actividades
     * @param adaptar boolean variable
     */
    private void actualizarLista(boolean adaptar)
    {
        listArray.clear();
        for(int i = 0; i < items.size(); i++)
        {
            Map<String, String> listItem = new HashMap<>();
            listItem.put("titulo", items.get(i));
            listItem.put("detalles", descs.get(i));
            listArray.add(listItem);
        }
        if (adaptar){ itemsAdapter.notifyDataSetChanged(); }
        Log.d("NOTIFICACION", "Se actualizo la lista");
    }

    /**
     * Metodo para actualizar la lista del historial
     */
    private void actualizarHistorial()
    {
        historyArray.clear();
        for(int i = 0; i < itemsH.size(); i++)
        {
            Map<String, String> listHistory = new HashMap<>();

            listHistory.put("titulo", itemsH.get(i));
            listHistory.put("detalles", descsH.get(i));

            historyArray.add(listHistory);
        }
        historyAdapter.notifyDataSetChanged();
        Log.d("NOTIFICACION", "Se actualizo la lista");

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
