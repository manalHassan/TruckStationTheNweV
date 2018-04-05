package e.manal.truckstationthenwev;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

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

public class publicTab3menuforvisitor  extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    // String id="";
    ListView listViewArtists;
    List<Category> artists;
    EditText editTextName;
    String mid1="";
    FirebaseAuth firebaseAuth;
    String id1="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.public_tab3_menuitemforcustomer, container, false);
        //Bundle b = getActivity().getIntent().getExtras();
        // id1 = b.getString("id");
        id1=((Publicownerforvisitor)getActivity()).getuser1();
        artists = new ArrayList<>();
        //getting views
        listViewArtists = (ListView) rootView.findViewById(R.id.listViewTracks);
        //list to store artists
        artists = new ArrayList<>();

        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category artist = artists.get(position);
                //   String
                Intent intent = new Intent(getActivity(), tab3_itemlistforvisitor.class);
                Bundle c = new Bundle();
                c.putString("id",id1);
                c.putString("cid", artist.getCatID());
                intent.putExtras(c);
                startActivity(intent);
            }
        });

        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        //attaching value event listener
        //  FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        // String id = user.getUid();//customer id is the same as rating id to make it easy to refer
        myRef.child("Menu").child(id1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mid1 = dataSnapshot.child("mid").getValue(String.class);
                myRef.child("Category").child(mid1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        artists.clear();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Category artist =new Category(ds.getValue(Category.class));
                            artists.add(artist);
                        }


                        //creating adapter
                        Category_list artistAdapter = new Category_list(getActivity(), artists);
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