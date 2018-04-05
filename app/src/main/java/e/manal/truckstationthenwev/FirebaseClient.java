package e.manal.truckstationthenwev;

/**
 * Created by amerah on 2/12/2018 AD.
 */

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


//////////////////////////////////
public class FirebaseClient extends AppCompatActivity {

    Context c;
    String DB_URL;
    ListView listView;
    Firebase firebase;
    ArrayList<PublicFoodTruckOwner> dogies= new ArrayList<>();
    CustomAdapter customAdapter;
    ArrayList<PrivateFoodTruckOwner> dogies1= new ArrayList<>();
    CustomAdapterPrivate customAdapter1;
    DatabaseReference f;
    DatabaseReference rate;
    PublicFoodTruckOwner d;

    public  FirebaseClient(Context c, String DB_URL,ListView listView)
    {
        this.c= c;
        this.listView= listView;
        this.DB_URL= DB_URL;

        Firebase.setAndroidContext(c);

    }

    public  void savedata(String name) {
        //Dog d= new Dog();
        // d.setName(name);
        //d.setUrl(url);

        if (name.equals("pu")) {

            f = FirebaseDatabase.getInstance().getReference().child("PublicFoodTruckOwner");

            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildren() == null) {
                        Toast.makeText(c, "no trucks", Toast.LENGTH_SHORT).show();

                        //Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getActivity(), AdminHome2.class));
                    }
                    dogies.clear();
                   // customAdapter.notifyDataSetChanged();
                    for (com.google.firebase.database.DataSnapshot ds : dataSnapshot.getChildren()) {

                        d = new PublicFoodTruckOwner();
                        d.setFUsername(ds.getValue(PublicFoodTruckOwner.class).getFUsername());
                        d.setUrl(ds.getValue(PublicFoodTruckOwner.class).getUrl());
                        d.setQusins(ds.getValue(PublicFoodTruckOwner.class).getQusins());
                        d.setUid(ds.getValue(PublicFoodTruckOwner.class).getUid());
                        d.setSumRate(ds.getValue(PublicFoodTruckOwner.class).getSumRate());
                        d.setNumCus(ds.getValue(PublicFoodTruckOwner.class).getNumCus());
                        //Toast.makeText(c, d.getUid(), Toast.LENGTH_SHORT).show();
                       // Toast.makeText(c, sumRatesarray.size() + "size ", Toast.LENGTH_SHORT).show();
                      //  Toast.makeText(c,d.getQusins(), Toast.LENGTH_SHORT).show();
                      //  Toast.makeText(c,d.getUid(), Toast.LENGTH_SHORT).show();
                      //  if (d.getUid() == null) {

                        // Toast.makeText(c,"hi", Toast.LENGTH_SHORT).show();
/*
                            for (int h=0;h<sumRatesarray.size();h++) {
                              if(sumRatesarray.get(h).getFID().equals(d.getUid())) {

                                  Toast.makeText(c, sumRatesarray.get(h).getNumCus() + "num of cus", Toast.LENGTH_SHORT).show();
                              }
                            }
                            /*
                        //////___________rate//*
                        rate = FirebaseDatabase.getInstance().getReference().child("Rate").child(d.getUid()).child("sum");

                            ValueEventListener eventListener2 = new ValueEventListener() {
                                @Override
                                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildren() == null) {
                                        Toast.makeText(c, "no rating", Toast.LENGTH_SHORT).show();

                                        //Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
                                        //startActivity(new Intent(getActivity(), AdminHome2.class));
                                    }

                                    sumRate rate = new sumRate();
                                    rate.setSum(dataSnapshot.getValue(sumRate.class).getSum());
                                    rate.setNumCus(dataSnapshot.getValue(sumRate.class).getNumCus());
                                    int a = rate.getSum();
                                    int b = rate.getNumCus();
                                    Toast.makeText(c, a + "", Toast.LENGTH_SHORT).show();
                                    d.setNumCus(2);
                                    d.setSumRate(2);


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(c, "cancelled", Toast.LENGTH_SHORT).show();
                                }
                            };
                            rate.addListenerForSingleValueEvent(eventListener2);

                            */

                       // }//end if


                            ///___________rate




                            dogies.add(d);


                        }
                        if (dogies.size() > 0) {

                            customAdapter = new CustomAdapter(c, dogies);
                            listView.setAdapter((ListAdapter) customAdapter);
                            customAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(c, "No data", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled (DatabaseError databaseError){
                        Toast.makeText(c, "cancelled", Toast.LENGTH_SHORT).show();
                    }
                }

                ;
            f.addListenerForSingleValueEvent(eventListener);

            /////////update//////////
            /*
            f.addChildEventListener(new ChildEventListener() {

                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    dogies.add(dataSnapshot.getValue(String.class));
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                     dogies.add(dataSnapshot.getValue(PublicFoodTruckOwner.class));
                   // customAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }

            });
        }
*/

        ////////update////////////


            ////////rate///////////

            /////////rate//////////

            //  _________________________pravet__________________________________

        }else if (name.equals("pr")) {
            f = FirebaseDatabase.getInstance().getReference().child("PrivateFoodTruckOwner");

            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildren() == null) {
                        Toast.makeText(c, "no trucks", Toast.LENGTH_SHORT).show();

                        //Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getActivity(), AdminHome2.class));
                    }
                    dogies1.clear();

                    for (com.google.firebase.database.DataSnapshot ds : dataSnapshot.getChildren()) {
                        PrivateFoodTruckOwner d= new PrivateFoodTruckOwner();
                        d.setUid(""+(ds.getValue(PrivateFoodTruckOwner.class).getUid()));
                        d.setFUsername(ds.getValue(PrivateFoodTruckOwner.class).getFUsername());
                        d.setUrl(ds.getValue(PrivateFoodTruckOwner.class).getUrl());
                        d.setQusins(ds.getValue(PrivateFoodTruckOwner.class).getQusins());
                        dogies1.add(d);

                    }
                    if (dogies1.size() > 0) {
                        customAdapter1 = new CustomAdapterPrivate(c, dogies1);
                        listView.setAdapter((ListAdapter) customAdapter1);
                    } else {
                        Toast.makeText(c, "No data", Toast.LENGTH_SHORT).show();
                    }



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(c, "cancelled", Toast.LENGTH_SHORT).show();
                }
            };
            f.addListenerForSingleValueEvent(eventListener);

        }//if private
    }

    /////////////////
    public ListView reListView(){return  listView;}

    public ArrayList<PrivateFoodTruckOwner> getDogies1(){return  dogies1;}
    public CustomAdapterPrivate custom(){return  customAdapter1;}
    public ArrayList<PublicFoodTruckOwner> getDogies(){return  dogies;}
    public CustomAdapter customPublic(){return  customAdapter;}
    public Context contes(){return  c;}


    //////////////////////

}

