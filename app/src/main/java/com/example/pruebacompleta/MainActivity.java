package com.example.pruebacompleta;

import java.util.*;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

        //AGENDACION DE ACTIVIDADES EQUIS DE
        lvItems = (ListView) findViewById(R.id.lista);
        //items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager=getSupportFragmentManager();

        if (id == R.id.nav_agenda) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new Fragment01()).commit();
        } else if (id == R.id.nav_addActivity) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new Fragment02()).commit();
        } else if (id == R.id.nav_historial) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new Fragment03()).commit();
        } else if (id == R.id.nav_ajustes) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new Fragment04()).commit();
        } else if (id == R.id.nav_compartir) {

        } else if (id == R.id.nav_enviar) {

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
                     items.remove(pos);
                     itemsAdapter.notifyDataSetChanged();
                     escribirActividades();
                     return true;
                    }
                }
        );
    }

    public void agendarActividad(View v)
    {
        EditText etNewItem = (EditText) findViewById(R.id.et_nombre);
        String itemText = etNewItem.getText().toString();
        items.add(items.size(),itemText);
        itemsAdapter.notifyDataSetChanged();
        etNewItem.setText("");
        escribirActividades();

        Log.d("NOTIFICACION", "Se ejecuta agendarActividad()");
    }

    private void leerActividades()
    {
        File filesDir = getExternalFilesDir(null);
        File guardadoFile = new File(filesDir, "meme.txt");
        try
        {
            items = new ArrayList<String>(FileUtils.readLines(guardadoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
            Log.d("ERROR", "Se intento leer actividades");
        }
    }

    private void escribirActividades()
    {
        File filesDir = getExternalFilesDir(null);
        File guardadoFile = new File(filesDir, "meme.txt");
        try {
            Log.d("NOTIFICACION", items.toString());
            FileUtils.writeLines(guardadoFile, items);
            Log.d("NOTIFICACION", filesDir.toString());
            Log.d("NOTIFICACION", "===================================");
            Log.d("NOTIFICACION", items.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ERROR", "Se intento escribir actividades");
        }
    }
}
