package e.manal.truckstationthenwev;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by wafaa7maD on 03/03/18.
 */

public class publictest extends AppCompatActivity {
        DatabaseReference databaseref;
       String user1 = "-L5hPscXmLNDYe8_3KNL";//edit this
        TextView username;
        TextView mail;
        TextView phone;
        TextView wwh;
        FirebaseAuth firebaseAuth;
        private TextView location;
        String address;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

            setContentView(R.layout.activity_main_public);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            databaseref = FirebaseDatabase.getInstance().getReference();

            // Read from the database
            databaseref.child("PublicFoodTruckOwner").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                   // FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                  //  String user1 = user.getUid();//customer id is the same as rating id to make it easy to refer

                    String name = dataSnapshot.child(user1).child("fusername").getValue(String.class);
                    int phonnum = dataSnapshot.child(user1).child("fponeNoumber").getValue(int.class);
                    String email = dataSnapshot.child(user1).child("femail").getValue(String.class);
                    String cc = dataSnapshot.child(user1).child("qusins").getValue(String.class);
                    String wh=dataSnapshot.child(user1).child("fworkingHours").getValue(String.class);

                    username = (TextView) findViewById(R.id.type);
                    username.setText(" " + cc);
                    TextView profilename = (TextView) findViewById(R.id.user);
                    profilename.setText(name);
                    wwh= (TextView) findViewById(R.id.wh);
                    wwh.setText(" " + wh);
                    mail = (TextView) findViewById(R.id.email);
                    mail.setText(" " + email);
                    phone = (TextView) findViewById(R.id.phone);
                    phone.setText(" " + phonnum + " ");
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });


        }


}
