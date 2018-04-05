package e.manal.truckstationthenwev;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class contentmain extends AppCompatActivity {

    DatabaseReference databaseref;
   // String user = "-L5hPscXmLNDYe8_3KNL";
    TextView type;
    TextView mail;
    TextView phone;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_public);

        //here
        //getting the reference of artists node
        databaseref = FirebaseDatabase.getInstance().getReference();

        // Read from the database
        FirebaseUser user1=FirebaseAuth.getInstance().getCurrentUser();
        String user = user1.getUid();//customer id is the same as rating id to make it easy to refer

        databaseref.child("PublicFoodTruckOwner").child(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                String name = dataSnapshot.child("fusername").getValue(String.class);
                int phonnum = dataSnapshot.child("fponeNoumber").getValue(int.class);
                String email = dataSnapshot.child("femail").getValue(String.class);
                String cc = dataSnapshot.child("qusins").getValue(String.class);


                TextView profilename = (TextView) findViewById(R.id.user);
                profilename.setText(name);

                type= (TextView) findViewById(R.id.type);
                type.setText(" " +cc);
                mail = (TextView) findViewById(R.id.email);
                mail.setText(" " + email);
                phone = (TextView) findViewById(R.id.phone);
                phone.setText(" " + phonnum + " ");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

                Toast.makeText(contentmain.this, "لايوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
            }
        });
    }



    public void editprofilepage(View view) {

        Intent intent = new Intent(contentmain.this, editprofile.class);
        startActivity(intent);
    }



}
