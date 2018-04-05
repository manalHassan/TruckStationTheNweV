package e.manal.truckstationthenwev;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by manal on 3/5/2018.
 */

public class publicListVistor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    FirebaseAuth auth ;
    final static String DB_URL = "https://truckstation-3c3eb.firebaseio.com/";
    EditText nameeditText, urleditText;
    Button btnsave;
    ListView listView;
    FirebaseClient firebaseClient;
    DrawerLayout drawer;
    ArrayList<PublicFoodTruckOwner> dogies= new ArrayList<>();
    CustomAdapter customAdapter;
    DatabaseReference f;
    Context c;
    Intent intent = getIntent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_drawer3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

///////////////////////////////////////////
        listView = (ListView) findViewById(R.id.listview);
        firebaseClient = new FirebaseClient(this, DB_URL, listView);
        //  firebaseClient.refreshdata();
        firebaseClient.savedata("pu");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PublicFoodTruckOwner artist = dogies.get(position);
                //  Toast.makeText(ListPuplic.this, "ID="+artist.getUid()+"." ,Toast.LENGTH_SHORT).show();

                gotoowner(artist.getUid());

            }
        });

        // Toast.makeText(this, "you hear", Toast.LENGTH_LONG).show();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);}//crest

    private void gotoowner(String uid) {

        //Toast.makeText(publicListVistor.this, "IDlist="+uid+"." ,Toast.LENGTH_SHORT).show();
        // set Fragmentclass Arguments
        // publicTab1profileforcustmer fragobj = new  publicTab1profileforcustmer();
        //  fragobj.setArguments(b);
        //String w=artist.getUid();
        Intent intent = new Intent(publicListVistor.this, Publicownerforcustmer.class);
        Bundle b=new Bundle();
        b.putString("id",uid);
        intent.putExtras(b);
        startActivity(intent);

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
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_with_search, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_publiclist1) {
            Intent intent = new Intent(this, publicListVistor.class);
            Bundle b=new Bundle();
            // b.putString("id",user);
            // intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }
        else if (id == R.id.nav_privatelist1) {
            Intent intent = new Intent(this, PravitListVistor.class);
            Bundle b=new Bundle();
            // b.putString("id",user);
            // intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }
        else if (id == R.id.nav_map1) {
            Intent intent = new Intent(this, VsitorAllTrucks.class);
            Bundle b=new Bundle();
            //  b.putString("id",user);
            // intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }

        else if (id == R.id.nav_nearmap1) {
            Intent intent = new Intent(this, NearByTrucksVisitors.class);
            Bundle b=new Bundle();
            //  b.putString("id",user);
            // intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }
        else if (id == R.id.nav_app1) {

            Intent intent = new Intent(this, ChartVisitor.class);
            Bundle b=new Bundle();
            //  b.putString("id",user);
            //  intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }
        else if (id == R.id.home) {

            Intent intent = new Intent(this, MainActivity.class);
            Bundle b=new Bundle();
            //  b.putString("id",user);
            //  intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }

        return false;
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawer.closeDrawers();
                        return true;
                    }
                });
    }

}
