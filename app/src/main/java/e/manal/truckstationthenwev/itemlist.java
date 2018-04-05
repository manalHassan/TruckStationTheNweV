package e.manal.truckstationthenwev;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wafaa7maD on 20/02/18.
 */

public class itemlist extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String id = "";
    String cid = "";
    String mid = "";
    ListView listViewArtists;
    List<Item> artists;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemlistinclude);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle b = getIntent().getExtras();
        cid = b.getString("cid");
        //TextView view =(TextView) findViewById(R.id.foodmenu);
        //view.setText(cid);

        listViewArtists = (ListView) findViewById(R.id.listViewTracks);
        //list to store artists
        artists = new ArrayList<>();

        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Item artist = artists.get(position);
                //   String
                AlertDialog diaBox = AskOption(artist.getItemID());
                diaBox.show();
            }
        });
        listViewArtists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Item artist = artists.get(i);
                showUpdateDeleteDialog(artist.getCatID(),artist.getItemID(),artist.getIName(),artist.getIdescription(),""+artist.getIPrice());
                return true;
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        navigationView.setNavigationItemSelectedListener(this);
    }
/*
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = getLayoutInflater();
        View row = inflater.inflate(R.layout.layout_itemlist, parent, false);
      Button delete = (Button) row.findViewById(R.id.deleteitem);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Item tem= artists.get(position);
                AlertDialog diaBox = AskOption(tem.getItemID());
                diaBox.show();
            }
        });
        return row;
    }
*/
    private void showUpdateDeleteDialog(final String cId,final String itemid,final String n ,final String desc, final String p) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_item, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.name);
        final EditText editTextdes = (EditText) dialogView.findViewById(R.id.des);
        final EditText editTextprice = (EditText) dialogView.findViewById(R.id.price);
        editTextName.setText(n);
        editTextdes.setText(desc);
        editTextprice.setText(p);

        final Button buttonimage = (Button) dialogView.findViewById(R.id.Pimage);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.updatetem);

        dialogBuilder.setTitle("تعديل الصنف");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String des = editTextdes.getText().toString().trim();
                String price = editTextprice.getText().toString().trim();
                if (!TextUtils.isEmpty(name)&& !TextUtils.isEmpty(des) && !TextUtils.isEmpty(price)) {
                    updateItem(cid,itemid,name,des,price);
                    b.dismiss();
                }
                else{
                    b.dismiss();
                    Toast.makeText(getApplicationContext(), "الرجاء تعبئة جميع الخانات ", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    private void updateItem(final String cid,final String tid,final String n,final String d,final String price) {

        final double pr;
        try{
            pr = Double.parseDouble(price);}
        catch (Exception e){
            Toast.makeText(this, "الرجاء ادخال السعر بشكل صحيح ", Toast.LENGTH_LONG).show();
            return;
        }
            //1 get menu id
            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
            String id = user.getUid();//customer id is the same as rating id to make it easy to refer
            myRef.child("Menu").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mid = dataSnapshot.child("mid").getValue(String.class);
                    //2 add item
                    Item t=new Item();
                    t.setCatID(cid);
                    t.setIdescription(d);
                    t.setIName(n);
                    t.setIPicture("here url");
                    t.setIPrice(pr);
                    t.setItemID(tid);

                    myRef.child("Item").child(mid).child(cid).child(tid).setValue(t);;
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


    }

    public void additem(View view) {
        Intent intent = new Intent(this, edititem.class);
        Bundle b = new Bundle();
        b.putString("cid", cid);
        intent.putExtras(b);
        startActivity(intent);
        /*String tid =myRef.push().getKey();
        Item t1= new Item ("برجر لحم" ,"http://www.overcaffeinated.org/sites/default/files/styles/large/public/products/tazo_black_shaken_iced_tea.jpg?itok=u6CezagO" ,"مثجلجات بنكهة الشاي" , 23  ,cid , tid );
        myRef.child("Item").child(mid).child(cid).child(tid).setValue(t1);
        Toast.makeText(itemlist.this, "تمت اللإضافة", Toast.LENGTH_LONG).show();*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();//customer id is the same as rating id to make it easy to refer
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
                        Itemarray artistAdapter = new Itemarray(itemlist.this, artists);
                        //attaching adapter to the listview
                        listViewArtists.setAdapter(artistAdapter);
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


    private AlertDialog AskOption(final String item) {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("حذف صنف")
                .setMessage("هل تريد حذف الصنف")
                .setIcon(R.drawable.ic_delete)

                .setPositiveButton("حذف", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        deleteitem(item);

                        dialog.dismiss();
                    }

                })


                .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    private void deleteitem(String item) {
    //getting the specified artist reference
    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Item").child(mid).child(cid).child(item);
    //removing artist
        dR.removeValue();
        Toast.makeText(this,"تم الحـــذف",Toast.LENGTH_LONG).show();
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
    getMenuInflater().inflate(R.menu.menu_public_main, menu);
    return true;
}
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, editprofile.class);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        } else if (id == R.id.nav_preorder) {
            /*Intent intent = new Intent(MainActivity.this, postActivity.class);
            Bundle b=new Bundle();
            b.putString("id",user);
            intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
            */
        }
        else if (id == R.id.nav_booking) {
            /*Intent intent = new Intent(MainActivity.this, postActivity.class);
            Bundle b=new Bundle();
            b.putString("id",user);
            intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
            */
        }


        else if (id == R.id.nav_request) {
/*
  Intent intent = new Intent(MainActivity.this, editprofile.class);
            Bundle b=new Bundle();
            b.putString("id",user);
            intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
 */
        }
        else if (id == R.id.nav_logout) {

            firebaseAuth.signOut();
            if(firebaseAuth.getCurrentUser() == null){
                Toast.makeText(this , "تم تسجيل الخروج بنجاح" , Toast.LENGTH_SHORT).show();
                startActivity(new Intent (this , MainActivity.class));
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;

            }}
        return false;
    }

}
        //start