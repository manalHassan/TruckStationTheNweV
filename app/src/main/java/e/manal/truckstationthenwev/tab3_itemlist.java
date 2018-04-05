package e.manal.truckstationthenwev;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

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
 * Created by wafaa7maD on 04/03/18.
 */

public class tab3_itemlist extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String cid="";
    String mid="";
    ListView listViewArtists;
    List<Item> artists;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_tab3_menuitem);

        Bundle b = getIntent().getExtras();
        cid = b.getString("cid");
        //TextView view =(TextView) findViewById(R.id.foodmenu);
        //view.setText(cid);

        listViewArtists = (ListView) findViewById(R.id.listViewTracks);
        //list to store artists
        artists = new ArrayList<>();


    }



    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
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
                            Item artist =new Item(ds.getValue(Item.class));
                            artists.add(artist);
                        }


                        //creating adapter
                        Itemarray artistAdapter = new Itemarray(tab3_itemlist.this, artists);
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





}
