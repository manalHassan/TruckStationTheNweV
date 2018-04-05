package e.manal.truckstationthenwev;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by manal on 3/19/2018.
 */

public class publicTab1profileforvisitor extends android.support.v4.app.Fragment {
    Button button;
    DatabaseReference databaseref;
    TextView username;
    TextView mail;
    TextView phone;
    TextView wwh;
    //  FirebaseAuth firebaseAuth;
    private TextView location;
    String user1="";
    String address;
    //String user1="jVmYjqfu5leLTx4gkFPQiQ9E3g83";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //  user1 = getArguments().getString("id");
        //Bundle b = getActivity().getIntent().getExtras();
        // user1 = b.getString("id");
        final View rootView= inflater.inflate(R.layout.public_tab1_profileforcustomer, container, false);


        setHasOptionsMenu(true);//Make sure you have this line of codeActivity act=getActivity();

        user1=((Publicownerforvisitor)getActivity()).getuser1();

        databaseref = FirebaseDatabase.getInstance().getReference();
        databaseref.child("PublicFoodTruckOwner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child(user1).child("fusername").getValue(String.class);
                int phonnum = dataSnapshot.child(user1).child("fponeNoumber").getValue(int.class);
                String email = dataSnapshot.child(user1).child("femail").getValue(String.class);
                String cc = dataSnapshot.child(user1).child("qusins").getValue(String.class);


                //String wh=dataSnapshot.child(user1).child("fworkingHours").getValue(String.class);

                username = (TextView) rootView.findViewById(R.id.type);
                username.setText(" " + cc);
                TextView profilename = (TextView) rootView.findViewById(R.id.user);
                profilename.setText(name);
                // wwh= (TextView) rootView.findViewById(R.id.wh);
                //  wwh.setText(" " + wh);
                mail = (TextView) rootView.findViewById(R.id.email);
                mail.setText(" " + email);
                phone = (TextView) rootView.findViewById(R.id.phone);
                phone.setText(" " + phonnum + " ");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getActivity(), "لايوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
            }
        });



        TextView res=rootView.findViewById(R.id.Reserve);

        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goreserve();
            }
        });

        TextView rate=rootView.findViewById(R.id.rating);

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gorating();
            }
        });
        //
        return rootView;
    }
    public void gorating( ){
        Intent intent = new Intent(getActivity(), Rating.class);
        Bundle b=new Bundle();
        b.putString("id",user1);
        intent.putExtras(b);
        startActivity(intent);

    }

    public void goreserve( ){
        Intent intent = new Intent(getActivity(), ReserveTruck.class);
        Bundle b=new Bundle();
        b.putString("id",user1);
        intent.putExtras(b);
        startActivity(intent);

    }

}

