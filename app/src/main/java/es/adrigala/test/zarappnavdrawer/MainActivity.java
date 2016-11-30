package es.adrigala.test.zarappnavdrawer;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Contador cont = null;
    private TextView text_grandes = null;
    private TextView text_peques = null;
    private Button boton_grandes = null;
    private Button boton_peques = null;
    private String grandes = null;
    private String peques = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Resources res = getResources();
        grandes = res.getString(R.string.total_cajas_grandes);
        peques = res.getString(R.string.total_cajas_peque_as);




        text_grandes = (TextView) findViewById(R.id.textView);
        text_peques = (TextView) findViewById(R.id.textView2);
        cont = new Contador();
        boton_grandes = (Button) findViewById(R.id.button_grandes);
        boton_peques = (Button) findViewById(R.id.button_peque);




        boton_grandes.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                cont.setGrandes(cont.getGrandes() + 1);
                text_grandes.setText(grandes);
                text_grandes.append(Integer.toString(cont.getGrandes()));
                cont.setLast(1);
            }
        });

        boton_peques.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                cont.setPeques(cont.getPeques() + 1);
                text_peques.setText(peques);
                text_peques.append(Integer.toString(cont.getPeques()));
                cont.setLast(0);
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cont.setBases(cont.getBases() + 1);
                cont.setLast(2);
                Snackbar.make(view, "Base añadida", Snackbar.LENGTH_LONG)
                        .show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
    public boolean onPrepareOptionsMenu (Menu menu) {

            menu.getItem(0).setEnabled(cont.canUseHistory());
            // You can also use something like:
            // menu.findItem(R.id.example_foobar).setEnabled(false);

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
            if (cont.getLast() == 1){
                cont.setGrandes(cont.getGrandes() - 1);
                cont.clearLastBox();
                text_grandes.setText(grandes);
                text_grandes.append(Integer.toString(cont.getGrandes()));
            }else if(cont.getLast() == 0){
                cont.setPeques(cont.getPeques() - 1);
                cont.clearLastBox();
                text_peques.setText(peques);
                text_peques.append(Integer.toString(cont.getPeques()));
            }else{
                cont.setBases(cont.getBases() - 1);
                cont.clearLastBox();
            }


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Borrar")
                    .setMessage("¿Estás seguro que deseas reiniciar contadores?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            cont.setPeques(0);
                            cont.setGrandes(0);
                            cont.setBases(0);
                            text_peques.setText(peques);
                            text_peques.append(Integer.toString(cont.getPeques()));
                            text_grandes.setText(grandes);
                            text_grandes.append(Integer.toString(cont.getGrandes()));
                            cont.clearHistory();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else if (id == R.id.nav_gallery) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Resultados")
                    .setMessage("Total de Cajas Grandes: " + cont.getGrandes() +
                            "\n" + "Total de Cajas Pequeñas: " + cont.getPeques() +
                            "\n" + "Total Cajas: " + (cont.getPeques() + cont.getGrandes()) +
                            "\n" + "Total Bases: " + cont.getBases())
                    .setCancelable(false)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .create().show();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
