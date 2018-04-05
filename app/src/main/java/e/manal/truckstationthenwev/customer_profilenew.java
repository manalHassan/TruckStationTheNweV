package e.manal.truckstationthenwev;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

// //هذا الجديد************************//////////
//////
public class customer_profilenew extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private FirebaseAuth firebaseAuth;
//jbjbjbj

    //view objects
    String address;
    Context context;
    int PLACE_PICKER_REQUEST = 1;
    private double x =0 , y =0 ;
    private DatabaseReference databaseReference;
    private EditText TextName;
    private EditText TextEmail;
    private EditText TextPhone;
    private TextView Textmain;
    private ImageView location;
    private TextView TextLastNme;
    private Button followedList;

    private String username;
    private String lastname;
    private String email;
    private String phone;
    private String locationx;
    private String locationy;
    private  Customer c=new Customer();
    private FirebaseAuth mAuth;
    private FirebaseUser user;

/////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth =  FirebaseAuth.getInstance() ;
        context = this;
        setContentView(R.layout.activity_customer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Textmain=(TextView)findViewById(R.id.name);
        TextName = (EditText) findViewById(R.id.editname);
        TextLastNme=(EditText) findViewById(R.id.editlastname);
        TextEmail = (EditText) findViewById(R.id.editemail);
        TextPhone = (EditText) findViewById(R.id.editphone);
        location = (ImageView) findViewById(R.id.location1);
        followedList= (Button) findViewById(R.id.followedTrucks);

        followedList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(customer_profilenew.this, Main2Activity.class);
                startActivity(intent);
            }
        });



        user=FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();//
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer").child(id);
       // Toast.makeText(this, databaseReference.toString(), Toast.LENGTH_SHORT).show();

        ValueEventListener EventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             //   Toast.makeText(getApplicationContext(),"hdfhdsg", Toast.LENGTH_SHORT).show();
                // c=new Customer();
            //    try {
                    c = dataSnapshot.getValue(Customer.class);
                    Textmain.setText(c.getCFirstName()+c.getCLastName());
                    TextName.setText(c.getCFirstName());
                    TextLastNme.setText(c.getCLastName());
                    TextPhone.setText(String.format("%d", c.getCPoneNoumber()));

              //  } catch (Exception e){
                   // e.getMessage();
               //////
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                TextPhone.setText("4756478564786546576378475346");
            }
        };
        databaseReference.addListenerForSingleValueEvent(EventListener);

        TextEmail.setText(user.getEmail());
        //to create listner, update info
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditCustomerInfo();
                //  Fragment fr = new ListCategories();
                //FragmentManager fm = getFragmentManager();
                // FragmentTransaction ft = fm.beginTransaction();


// ft.replace(R.id.content_frame, fr);
                // ft.commit();
            }
        });


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                // finish();
                Intent intent;
                try {
                    address = "  ";
                    intent = builder.build((Activity)  context);
                    startActivityForResult(intent,PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                  e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                   e.printStackTrace();
                }

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }//creat

    private void EditCustomerInfo(){
        username = TextName.getText().toString().trim();
        email = TextEmail.getText().toString().trim();
        phone = TextPhone.getText().toString().trim();
        lastname=TextLastNme.getText().toString().trim();
        //empty feild
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(lastname)){
            Toast.makeText(this,"Please enter lastname",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Please enter username",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Please enter phone",Toast.LENGTH_LONG).show();
            return;
        }

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern))
        {
            Toast.makeText(this,"Invalid email formate", Toast.LENGTH_LONG).show();
            return;
        }


        c.setCEmail(email);
        c.setCFirstName(username);
        c.setCPoneNoumber(Integer.parseInt(phone));
        c.setCLastName(lastname);


        databaseReference.setValue(c);
        user.updateEmail(email);
        //user.updatePassword(password);
        Toast.makeText(this, "تم حفظ المعلومات ...", Toast.LENGTH_LONG).show();
        //startActivity(new Intent(getActivity(), AdminHome2.class));
    }//edit

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);

                x = place.getLatLng().latitude;
                y = place.getLatLng().longitude;


            }
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, customer_profilenew.class);
            Bundle b=new Bundle();
            //b.putString("id",user);
            //intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        } else if (id == R.id.nav_publiclist) {
            Intent intent = new Intent(this, ListPuplic.class);
            Bundle b=new Bundle();
            // b.putString("id",user);
            // intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }
        else if (id == R.id.nav_privatelist) {
            Intent intent = new Intent(this, ListPrivate.class);
            Bundle b=new Bundle();
            // b.putString("id",user);
            // intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }
        else if (id == R.id.nav_map) {
            Intent intent = new Intent(this, VisitorHomePage.class);
            Bundle b=new Bundle();
            //  b.putString("id",user);
            // intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }

        else if (id == R.id.nav_nearmap) {
            Intent intent = new Intent(this, NearByTrucks.class);
            Bundle b=new Bundle();
            //  b.putString("id",user);
            // intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }

        else if (id == R.id.nav_pre_request) {
/*
            Intent intent = new Intent(this, ownermenu.class);
            Bundle b=new Bundle();
            b.putString("id",user);
            intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
            */
        }


        else if (id == R.id.nav_pre_preorder) {
/*
  Intent intent = new Intent(this, editprofile.class);
            Bundle b=new Bundle();
            b.putString("id",user);
            intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
 */
        }
        else if (id == R.id.nav_app) {

            Intent intent = new Intent(this, Chart.class);
            Bundle b=new Bundle();
            //  b.putString("id",user);
            //  intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }
        else if (id == R.id.nav_logout) {

            firebaseAuth.signOut();
            if(firebaseAuth.getCurrentUser() == null){
                Toast.makeText(this , "تم تسجيل الخروج بنجاح" , Toast.LENGTH_SHORT).show();
                startActivity(new Intent (this , MainActivity.class));
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;

            }}
        else if (id == R.id.cart) {

             /* Intent intent = new Intent(this, editprofile.class);
            Bundle b=new Bundle();
            b.putString("id",user);
            intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
 */

        }
        return false;
    }

}

