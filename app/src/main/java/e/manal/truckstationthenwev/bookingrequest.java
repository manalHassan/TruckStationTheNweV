package e.manal.truckstationthenwev;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
 * Created by wafa0 on 15/03/18.
 */

public class bookingrequest extends AppCompatActivity {




    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String id = "";
    String cid = "";
    String mid = "";
    ListView listViewArtists;
    List<Request> artists;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.request_layout);


    listViewArtists = (ListView) findViewById(R.id.listViewTracks);
    artists = new ArrayList<>();

        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
           // Item artist = artists.get(position);
            //   String
           // AlertDialog diaBox = AskOption(artist.getItemID());
          //  diaBox.show();
        }
    });

}

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();//customer id is the same as rating id to make it easy to refer

                myRef.child("Request").child(id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        artists.clear();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Request artist = new Request(ds.getValue(Request.class));
                            artists.add(artist);
                        }


                        //creating adapter
                        requestlist artistAdapter = new requestlist(bookingrequest.this, artists);
                        //attaching adapter to the listview
                        listViewArtists.setAdapter(artistAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }


}
