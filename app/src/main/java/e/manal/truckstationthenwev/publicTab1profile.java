package e.manal.truckstationthenwev;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
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
 * Created by hadeel on 2/10/18.
 */

public class publicTab1profile extends Fragment {
    Button button;
    DatabaseReference databaseref;
    DatabaseReference databaseReferences;
    TextView username;
    TextView mail;
    TextView phone;
    TextView wwh;
    Switch allow;
    Switch available;
    FirebaseAuth firebaseAuth;
    private TextView location;
    DatabaseReference database;
    String address;
    List<Post> artists;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView= inflater.inflate(R.layout.public_tab1_profile, container, false);
       // databaseref = FirebaseDatabase.getInstance().getReference("");
        artists = new ArrayList<>();
        allow =(Switch) rootView.findViewById(R.id.allowdisallow);
        available =(Switch) rootView.findViewById(R.id.available);

/*
        databaseref.addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Log.e(dataSnapshot.getKey(),dataSnapshot.getChildrenCount() + "");
    }
 @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});
*/


       // Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
       // ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
      //  setHasOptionsMenu(true);//Make sure you have this line of code
        databaseref = FirebaseDatabase.getInstance().getReference();

        // Read from the database
        databaseref.child("PublicFoodTruckOwner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                String user1 = user.getUid();//customer id is the same as rating id to make it easy to refer

                String name = dataSnapshot.child(user1).child("fusername").getValue(String.class);
              int phonnum = dataSnapshot.child(user1).child("fponeNoumber").getValue(int.class);
                String email = dataSnapshot.child(user1).child("femail").getValue(String.class);
                String cc = dataSnapshot.child(user1).child("qusins").getValue(String.class);
               // String wh=dataSnapshot.child(user1).child("fworkingHours").getValue(String.class);
                String logo = dataSnapshot.child(user1).child("url").getValue(String.class);
                ImageView mg= (ImageView) rootView.findViewById(R.id.mg);
               // Glide.with(getActivity()).load(logo).override(45, 72).into(mg);
                username = (TextView) rootView.findViewById(R.id.type);
                username.setText(" " + cc);
                TextView profilename = (TextView) rootView.findViewById(R.id.user);
                profilename.setText(name);
             //   wwh= (TextView) rootView.findViewById(R.id.wh);
             //   wwh.setText(" " + wh);
                mail = (TextView) rootView.findViewById(R.id.email);
                mail.setText(" " + email);
               phone = (TextView) rootView.findViewById(R.id.phone);
               phone.setText(" " + phonnum + " ");
                boolean getAllo=  dataSnapshot.child(user1).child("allow").getValue(boolean.class);

                boolean getAv=dataSnapshot.child(user1).child("available").getValue(boolean.class);
                allow.setOnCheckedChangeListener (null);
                allow.setChecked(getAllo);
                allow.setOnCheckedChangeListener ((CompoundButton.OnCheckedChangeListener) getActivity());


                available.setOnCheckedChangeListener (null);
                available.setChecked(getAv);
                available.setOnCheckedChangeListener ((CompoundButton.OnCheckedChangeListener) getActivity());

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getActivity(), "لايوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
            }
        });

        databaseReferences = FirebaseDatabase.getInstance().getReference();
       // databaseReferences.orderByChild("uid").equalTo(user1);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String user1 = user.getUid();//customer id is the same as rating id to make it easy to refer
        databaseReferences.child("postsTest3").orderByChild("uid").equalTo(user1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                artists.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Post artist =new Post(ds.getValue(Post.class));
                    artists.add(artist);
                }


                //creating adapter
             int posts= artists.size();
                TextView t=rootView.findViewById(R.id.pp);
                t.setText(posts+" ");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        allow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    // The toggle is enabled
                    databaseref = FirebaseDatabase.getInstance().getReference();
                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    String user1 = user.getUid();//customer id is the same as rating id to make it easy to refer

                    DatabaseReference owner= databaseref.child("PublicFoodTruckOwner").child(user1);
                    owner.child("allow").setValue(false);
                    // Read from the database
                    /*databaseref.child("PublicFoodTruckOwner").child(user1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            dataSnapshot.child("allow").
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });*/

                } else {
                    // The toggle is disabled
                    databaseref = FirebaseDatabase.getInstance().getReference();
                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    String user1 = user.getUid();//customer id is the same as rating id to make it easy to refer

                    DatabaseReference owner= databaseref.child("PublicFoodTruckOwner").child(user1);
                    owner.child("allow").setValue(true);
                }
            }

        });

        available.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    // The toggle is enabled
                    databaseref = FirebaseDatabase.getInstance().getReference();
                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    String user1 = user.getUid();//customer id is the same as rating id to make it easy to refer

                    DatabaseReference owner= databaseref.child("PublicFoodTruckOwner").child(user1);
                    owner.child("available").setValue(false);

                } else {
                    // The toggle is disabled
                    databaseref = FirebaseDatabase.getInstance().getReference();
                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    String user1 = user.getUid();//customer id is the same as rating id to make it easy to refer

                    DatabaseReference owner= databaseref.child("PublicFoodTruckOwner").child(user1);
                    owner.child("available").setValue(true);
                }
            }
        });

        //
        return rootView;
    }


}
