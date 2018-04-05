package e.manal.truckstationthenwev;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manal on 3/19/2018.
 */

public class tab3_itemlistforvisitor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference cartRef ;
    String cid = "";
    String mid = "";
    ListView listViewArtists;
    List<Item> artists;
    FirebaseAuth firebaseAuth;
    String id="";
    Bundle b ;
    cart car ;
    private int i = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_for_item_list_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        b = getIntent().getExtras();
        cid = b.getString("cid");
        id = b.getString("id");
        Toast.makeText(this, "ID from manal = "+id ,Toast.LENGTH_SHORT).show();
        cartRef = database.getReference("Cart");
        //TextView view =(TextView) findViewById(R.id.foodmenu);
        //view.setText(cid);
        listViewArtists = (ListView) findViewById(R.id.listViewTracks);
        //list to store artists
        artists = new ArrayList<>();
      //  listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //@Override
           // public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               // Item artist = artists.get(position);
              //  firebaseAuth = FirebaseAuth.getInstance();
                //  Toast.makeText(ListPuplic.this, "ID="+artist.getUid()+"." ,Toast.LENGTH_SHORT).show();
                //   if (car == null){
                //car = new cart ();
             //   cartItem cartitem = new cartItem(artist.getIName() , artist.getIPrice() , b.getString("id"));
               // String cid = cartRef.push().getKey();
              //  cartRef.child(firebaseAuth.getUid()).child(cid).setValue(cartitem);
              //  Toast.makeText(tab3_itemlistforvisitor.this,artist.getIName()+"اضيف لسلتك" ,Toast.LENGTH_SHORT).show();
                //    String s =  artist.getIName();
                //  }
                //  else {
                // car.setQuntity(1);
                //  cartRef.child("cart").updateChildren(car);
                //  }Toast.makeText(tab3_itemlistforcustomer.this,artist.getIName()+"اخر اضيف لسلتك" ,Toast.LENGTH_SHORT).show();
           // }
       // });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // String id = user.getUid();//customer id is the same as rating id to make it easy to refer
        myRef.child("Menu").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mid = dataSnapshot.child("mid").getValue(String.class);
                myRef.child("Item").child(mid).child(cid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        artists.clear();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Item artist = new Item(ds.getValue(Item.class));
                            artists.add(artist);
                        }


                        //creating adapter
                     //   Itemarrayforcustomer artistAdapter = new Itemarrayforcustomer(tab3_itemlistforvisitor.this, (ArrayList<Item>) artists);
                        //attaching adapter to the listview
                      //  listViewArtists.setAdapter(artistAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }
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
        getMenuInflater().inflate(R.menu.main, menu);
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

}




