package e.manal.truckstationthenwev;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 * Created by wafaa7maD on 05/03/18.
 */

public class publicTab1profileforcustmer  extends Fragment {
    Button button;
    DatabaseReference databaseref;
    DatabaseReference chatre;
    DatabaseReference chatref;
    TextView username;
    TextView mail;
    String name1;
    TextView phone;
    Button FUnfBtn;
    DatabaseReference FUnfRef;
    android.content.res.Resources res;
    String nameButton;
    DatabaseReference name;
    FirebaseAuth mAuth;
    private TextView location;
    String user1="";
    DatabaseReference databaseReferences;
    String address;
    List<Post> artists;
    //String user1="jVmYjqfu5leLTx4gkFPQiQ9E3g83";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
      //  user1 = getArguments().getString("id");
        //Bundle b = getActivity().getIntent().getExtras();
       // user1 = b.getString("id");
        final View rootView= inflater.inflate(R.layout.public_tab1_profileforcustomer, container, false);
        artists = new ArrayList<>();

        setHasOptionsMenu(true);//Make sure you have this line of codeActivity act=getActivity();

        user1=((Publicownerforcustmer)getActivity()).getuser1();


        FUnfBtn=(Button) rootView.findViewById(R.id.followUnfollowBtn);


        FUnfRef= FirebaseDatabase.getInstance().getReference("PublicFowllower");

        databaseref = FirebaseDatabase.getInstance().getReference();
        databaseref.child("PublicFoodTruckOwner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child(user1).child("fusername").getValue(String.class);
                nameButton=name;
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

        TextView chat=rootView.findViewById(R.id.chat);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gochat();
            }
        });
        //
        databaseReferences = FirebaseDatabase.getInstance().getReference();
        // databaseReferences.orderByChild("uid").equalTo(user1);
      //  FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
       // String user1 = user.getUid();//customer id is the same as rating id to make it easy to refer
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



        FUnfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followUnfollow();
            }
        });

        return rootView;
    }

    private void gochat() {
        mAuth = FirebaseAuth.getInstance();
        final String user2=mAuth.getCurrentUser().getUid();

//get name
        name = FirebaseDatabase.getInstance().getReference();
        name.child("Customer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child(user2).child("cfirstName").getValue(String.class);
                name1=name;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getActivity(), "لايوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
            }
        });

        //

       // Toast.makeText(getActivity(),user1+"_"+user2, Toast.LENGTH_LONG).show();
        chatref= FirebaseDatabase.getInstance().getReference();
        chatref.child("Chatroom").child(user1+"_"+user2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String room = dataSnapshot.child("room").getValue(String.class);

                if(room!=null){
               // Toast.makeText(getActivity(), room+"room", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), chatting.class);
                Bundle b=new Bundle();
                b.putString("room",room);
                    b.putString("me",name1);
               intent.putExtras(b);
               startActivity(intent);
            }
            else{

                    //get name
                    name = FirebaseDatabase.getInstance().getReference();
                    name.child("Customer").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child(user2).child("cfirstName").getValue(String.class);
                            String last = dataSnapshot.child(user2).child("clastName").getValue(String.class);
                            chatre= FirebaseDatabase.getInstance().getReference();
                            Chatroom r =new Chatroom();
                            r.setCID(user2);
                            r.setFID(user1);
                            r.setCname(name+" "+last);
                           // Toast.makeText(getActivity(), name+" "+last, Toast.LENGTH_LONG).show();
                            String room2=user1+"_"+user2;
                            r.setroom(room2);
                            chatre.child("Chatroom").child(user1+"_"+user2).setValue(r);
                            Intent intent = new Intent(getActivity(), chatting.class);
                            Bundle b=new Bundle();
                            b.putString("room",room2);
                            b.putString("me",name);
                            intent.putExtras(b);
                            startActivity(intent);

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Toast.makeText(getActivity(), "لايوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getActivity(), "لايوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
            }
        });

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


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser userID= FirebaseAuth.getInstance().getCurrentUser();
        String customerID = userID.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("PublicFowllower").child(customerID);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user1)){
                    FUnfBtn.setText("متابَع");
                    //FUnfBtn.setBackgroundResource(R.drawable.background_signup);
                    FUnfBtn.setTextColor(getActivity().getResources().getColor(R.color.darkG));
                    FUnfBtn.setBackground(getActivity().getResources().getDrawable(R.drawable.background_follow));
                }
                else{
                    FUnfBtn.setText("تَابع");
                    FUnfBtn.setTextColor(getActivity().getResources().getColor(R.color.icons));
                    FUnfBtn.setBackground(getActivity().getResources().getDrawable(R.drawable.background_signup));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }


    public void followUnfollow(){
        String type=FUnfBtn.getText().toString().trim();
        if(type=="متابَع"){
            FUnfRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    String id = user.getUid();//customer id is the same as rating id to make it easy to refer
                    FUnfRef.child(id).child(user1).removeValue();
                    FUnfBtn.setText("تَابع");
                    FUnfBtn.setTextColor(getActivity().getResources().getColor(R.color.icons));
                    FUnfBtn.setBackground(getActivity().getResources().getDrawable(R.drawable.background_signup));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }
        else {

            FUnfRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    String id = user.getUid();//customer id is the same as rating id to make it easy to refer

                    final String FID=user1;
                    final String CID=id;
                    final String Funfnumber="1";

                    if ( !TextUtils.isEmpty(FID) && !TextUtils.isEmpty(CID) ) {

                        PublicFowllower truck= new PublicFowllower("https://firebasestorage.googleapis.com/v0/b/truckstation-3c3eb.appspot.com/o/Trucks%20Images%2F1520524893963.jpg?alt=media&token=15a3e292-bb21-4e53-a761-95a5d4bf3043", nameButton , "public");
                        FUnfRef.child(CID).child(FID).setValue(truck);
                        FUnfBtn.setText("متابَع");
                        FUnfBtn.setTextColor(getActivity().getResources().getColor(R.color.darkG));
                        FUnfBtn.setBackground(getActivity().getResources().getDrawable(R.drawable.background_follow));
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }
    }



}

