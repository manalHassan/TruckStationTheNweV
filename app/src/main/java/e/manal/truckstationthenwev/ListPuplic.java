package e.manal.truckstationthenwev;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

///////////////
public class ListPuplic extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    FirebaseAuth auth ;
    final static String DB_URL = "https://truckstation-3c3eb.firebaseio.com/";
    EditText nameeditText, urleditText;
    Button btnsave;
    ListView listView;
    FirebaseClient firebaseClient;
    DrawerLayout drawer;
    ArrayList<PublicFoodTruckOwner> dogies= new ArrayList<>();
    CustomAdapter customAdapter;
    DatabaseReference f,mDatabase2;
    Context c;
    Spinner spinner1;
    ArrayList<String> categories = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         auth = FirebaseAuth.getInstance() ;
///////////////////////////////////////////
        listView = (ListView) findViewById(R.id.listview);

        firebaseClient = new FirebaseClient(this, DB_URL, listView);
        //  firebaseClient.refreshdata();
        firebaseClient.savedata("pu");
       // Toast.makeText(this, "you hear", Toast.LENGTH_LONG).show();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



       listView= firebaseClient.reListView();
        dogies=firebaseClient.getDogies();
        customAdapter=firebaseClient.customPublic();
        c=firebaseClient.contes();




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

             PublicFoodTruckOwner artist = dogies.get(position);
              //  Toast.makeText(ListPuplic.this, "ID="+artist.getUid()+"." ,Toast.LENGTH_SHORT).show();

        gotoowner(artist.getUid());

            }
        });
    }

    private void gotoowner(String uid) {

        Toast.makeText(ListPuplic.this, "IDlist="+uid+"." ,Toast.LENGTH_SHORT).show();
        // set Fragmentclass Arguments
        // publicTab1profileforcustmer fragobj = new  publicTab1profileforcustmer();
        //  fragobj.setArguments(b);
        //String w=artist.getUid();
        Intent intent = new Intent(ListPuplic.this, Publicownerforcustmer.class);
        Bundle b=new Bundle();
       // Toast.makeText(ListPuplic.this, uid ,Toast.LENGTH_SHORT).show();
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


        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu_with_search, (android.view.Menu) menu);

        MenuItem searchIem =((android.view.Menu) menu).findItem(R.id.action_search);
        SearchView searchView =(SearchView) MenuItemCompat.getActionView(searchIem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<PublicFoodTruckOwner> templist = new ArrayList<>();
                for (PublicFoodTruckOwner temp : dogies){
                    if(temp.getFUsername().toLowerCase().contains(newText.toLowerCase())){

                        templist.add(temp);
                    }

                    customAdapter = new CustomAdapter(ListPuplic.this, templist);
                    listView.setAdapter((ListAdapter) customAdapter);
                    customAdapter.notifyDataSetChanged();


                }


                return true;
            }
        });


        /////spinner code


        MenuItem item =((android.view.Menu) menu).findItem(R.id.spinner);
        spinner1 = (Spinner)item.getActionView(); // get the spinner
        categories = new ArrayList<String>();
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("PublicFoodTruckOwner");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren() ) {
                    PublicFoodTruckOwner category = ds.getValue(PublicFoodTruckOwner.class);
                    categories.add(category.getQusins());
                    final ArrayAdapter<String> arrayadap;
                    arrayadap = new ArrayAdapter<String>(ListPuplic.this, android.R.layout.simple_list_item_1, categories);
                    spinner1.setAdapter(arrayadap);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        mDatabase2.addListenerForSingleValueEvent(eventListener);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                // On selecting a spinner item
                String item = adapter.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(getApplicationContext(), "Selected  : " + item,
                        Toast.LENGTH_LONG).show();

                ArrayList<PublicFoodTruckOwner> templist = new ArrayList<>();
                for (PublicFoodTruckOwner temp : dogies){
                    if(temp.getQusins().toLowerCase().contains(item.toLowerCase())){

                        templist.add(temp);
                    }

                    customAdapter = new CustomAdapter(ListPuplic.this, templist);
                    listView.setAdapter((ListAdapter) customAdapter);
                    customAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub


            }
        });



//End Spinner code



        return super.onCreateOptionsMenu((android.view.Menu) menu);

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, customer_profilenew.class);
            Bundle b=new Bundle();
            //b.putString("id",user);
            //intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        } else if (id == R.id.nav_publiclist) {
            Intent intent = new Intent(this, ListPuplic.class);
            Bundle b=new Bundle();
            // b.putString("id",user);
            // intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }
        else if (id == R.id.nav_privatelist) {
            Intent intent = new Intent(this, ListPrivate.class);
            Bundle b=new Bundle();
            // b.putString("id",user);
            // intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }
        else if (id == R.id.nav_map) {
            Intent intent = new Intent(this, VisitorHomePage.class);
            Bundle b=new Bundle();
            //  b.putString("id",user);
            // intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }

        else if (id == R.id.nav_nearmap) {
            Intent intent = new Intent(this, NearByTrucks.class);
            Bundle b=new Bundle();
            //  b.putString("id",user);
            // intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }

        else if (id == R.id.nav_pre_request) {
/*
            Intent intent = new Intent(this, ownermenu.class);
            Bundle b=new Bundle();
            b.putString("id",user);
            intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
            */
        }


        else if (id == R.id.nav_pre_preorder) {
/*
  Intent intent = new Intent(this, editprofile.class);
            Bundle b=new Bundle();
            b.putString("id",user);
            intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
 */
        }
        else if (id == R.id.nav_app) {

            Intent intent = new Intent(this, Chart.class);
            Bundle b=new Bundle();
            //  b.putString("id",user);
            //  intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }
        else if (id == R.id.nav_logout) {

            auth.signOut();
            if(auth.getCurrentUser() == null){
                Toast.makeText(this , "تم تسجيل الخروج بنجاح" , Toast.LENGTH_SHORT).show();
                startActivity(new Intent (this , MainActivity.class));
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;

            }}
        else if (id == R.id.cart) {

            Intent intent = new Intent(this, viewCart.class);
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
}
