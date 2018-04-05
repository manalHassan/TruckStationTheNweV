package e.manal.truckstationthenwev;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class editprofile extends AppCompatActivity {

    // String id="-L5hPscXmLNDYe8_3KNL" ;
    DatabaseReference databaseref;
    DatabaseReference wh;
    EditText username;
    EditText mail;
    EditText phone;
    EditText passwor;
    EditText cusine;
    EditText sund;
    EditText mon;
    EditText the;
    EditText wen;
    EditText thus;
    EditText fri;
    EditText sat;
    String logo;
    private String address;
    //static double xx = 12.32;
  //  static double yy = 12.32;
    FirebaseAuth firebaseAuth;
    Context context;
    int PLACE_PICKER_REQUEST = 1;
    private double x = 0, y = 0;
    private TextView location ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);
        context = this;
        location = (TextView) findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                // finish();
                Intent intent;
                try {
                    address = "  ";
                    intent = builder.build((Activity) context);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });
        databaseref = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();//customer id is the same as rating id to make it easy to refer
        // Read from the database
        databaseref.child("PublicFoodTruckOwner").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                String name = dataSnapshot.child("fusername").getValue(String.class);
                int phonnum = dataSnapshot.child("fponeNoumber").getValue(int.class);
                String email = dataSnapshot.child("femail").getValue(String.class);
                String password = dataSnapshot.child("fpassword").getValue(String.class);
                String cusin = dataSnapshot.child("qusins").getValue(String.class);
                logo = dataSnapshot.child("url").getValue(String.class);
               // String whh = dataSnapshot.child("fworkingHours").getValue(String.class);
                // xx = dataSnapshot.child("xflication").getValue(Double.class);
                // yy = dataSnapshot.child("yflication").getValue(Double.class);

                username = (EditText) findViewById(R.id.name);
                username.setText(name);

                mail = (EditText) findViewById(R.id.email);
                mail.setText(email);

                phone = (EditText) findViewById(R.id.phone);
                phone.setText("" + phonnum);

                cusine = (EditText) findViewById(R.id.cusin);
                cusine.setText(cusin);

                passwor = (EditText) findViewById(R.id.pass);
                passwor.setText(password);

              //  workh = (EditText) findViewById(R.id.etFeedback);
               // workh.setText(whh);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

                Toast.makeText(editprofile.this, "لايوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
            }
        });

        wh = FirebaseDatabase.getInstance().getReference();
        // Read from the database
        //end
        wh.child("PrivateOwnerDate").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                String sun = dataSnapshot.child("d0").getValue(String.class);
                String mon1 = dataSnapshot.child("d1").getValue(String.class);
                String the1 = dataSnapshot.child("d2").getValue(String.class);
                String wen1 = dataSnapshot.child("d3").getValue(String.class);
                String thus1 = dataSnapshot.child("d4").getValue(String.class);
                String fri1 = dataSnapshot.child("d5").getValue(String.class);
                String sat1 = dataSnapshot.child("d6").getValue(String.class);

                sund = (EditText) findViewById(R.id.etFeedback);
                mon = (EditText) findViewById(R.id.mon);
                the = (EditText) findViewById(R.id.ths);
                wen = (EditText) findViewById(R.id.wensday);
                thus = (EditText) findViewById(R.id.thus);
                fri = (EditText) findViewById(R.id.fri);
                sat = (EditText) findViewById(R.id.sat);

               if(!TextUtils.isEmpty(sun)){

                sund.setText(sun+"");}
                   if(!TextUtils.isEmpty(mon1)){

                mon.setText(mon1+"");}
             if(!TextUtils.isEmpty(the1)){

                the.setText(the1+"");}
                 if(!TextUtils.isEmpty(wen1)){

               wen.setText(wen1+"");}
                 if(!TextUtils.isEmpty(thus1)){

                thus.setText(thus1+"");}
                     if(!TextUtils.isEmpty(fri1)){

                fri.setText(fri1+"");}
                if(!TextUtils.isEmpty(sat1)){

                sat.setText(sat1+"");}

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

                Toast.makeText(editprofile.this, "لايوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
            }
        });

    }


    public void chickInfo(View view) {
        final String pass = passwor.getText().toString().trim();
        final String phoneN = phone.getText().toString().trim();
        final String emailp = mail.getText().toString().trim();
        final String usern = username.getText().toString().trim();
        final String qusin = cusine.getText().toString().trim();

       // final double x = 12.321;
      //  final double y = 13.1;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();//customer id is the same as rating id to make it easy to refer
        if (!TextUtils.isEmpty(emailp) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(phoneN) && !TextUtils.isEmpty(qusin) && !TextUtils.isEmpty(usern) ) {

            databaseref = FirebaseDatabase.getInstance().getReference("PublicFoodTruckOwner").child(id);

            PublicFoodTruckOwner owner = new PublicFoodTruckOwner();
            owner.setFUsername(usern);
            owner.setFPassword(pass);
            owner.setFEmail(emailp);
            owner.setFPoneNoumber(Integer.parseInt(phoneN));
            owner.setXFLication(x);
            owner.setYFLocation(y);
            owner.setQusins(qusin);
            owner.setUid(id);
            owner.setUrl(logo);
            databaseref.setValue(owner);
            //wh
            wh = FirebaseDatabase.getInstance().getReference("PrivateOwnerDate").child(id);
            workh s=new workh();
            if(!TextUtils.isEmpty(sund.getText().toString().trim())){final String sunday = sund.getText().toString().trim();
                s.setD0(sunday);}
            if(!TextUtils.isEmpty(mon.getText().toString().trim())){final String monday = mon.getText().toString().trim();
                s.setD1(monday);}
            if(!TextUtils.isEmpty(the.getText().toString().trim())){ final String thes = the.getText().toString().trim();
                s.setD2(thes);}
            if(!TextUtils.isEmpty(wen.getText().toString().trim())){  final String wensday = wen.getText().toString().trim();
                s.setD3(wensday); }
            if(!TextUtils.isEmpty(thus.getText().toString().trim())){     final String thusday = thus.getText().toString().trim();
                s.setD4(thusday); }
            if(!TextUtils.isEmpty(fri.getText().toString().trim())){ final String friday = fri.getText().toString().trim();
                s.setD5(friday);}
            if(!TextUtils.isEmpty(sat.getText().toString().trim())){ final String satrady= sat.getText().toString().trim();
                s.setD6(satrady);}
                wh.setValue(s);
            //wh end
            Toast.makeText(editprofile.this, "تم حفظ التغييرات بنجاح!!", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(editprofile.this, "البريد الالكتروني مستخدم مسبقا", Toast.LENGTH_SHORT).show();


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);

                x = place.getLatLng().latitude;
                y = place.getLatLng().longitude;


            }
        }
    }

}

