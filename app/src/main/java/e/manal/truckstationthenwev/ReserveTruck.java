package e.manal.truckstationthenwev;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReserveTruck extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    String TruckName;
    String pic=" ";
    private DatabaseReference spinnerRef ,reserveRef,previousRef;
    Spinner DateTimeSpinner;
    String address;
    Context context;
    int PLACE_PICKER_REQUEST = 1;

    double x =0 , y =0;
FirebaseAuth auth ;
    String date=" ";
    String notes="لايوجد ملاحظات";
private  EditText Notes;
private ImageView Picker;
    private ImageView LocatImg;
// for date :
    private static final String TAG = "ReserveTruck";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mdateSetListener;
   private Calendar cal;
 private DatePickerDialog dialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserv_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
String user =finduser();



        Picker=(ImageView)findViewById(R.id.picker);
        LocatImg=(ImageView)findViewById(R.id.Location);
Notes=(EditText)findViewById(R.id.Notes);

        context = this;
        auth = FirebaseAuth.getInstance() ;
        spinnerRef = FirebaseDatabase.getInstance().getReference("PrivateOwnerDate");
        LocatImg.setOnClickListener(new View.OnClickListener() {
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
        //request =reserve
        reserveRef = FirebaseDatabase.getInstance().getReference("Request"); // make sure its identical to the table name in the database
        previousRef=FirebaseDatabase.getInstance().getReference("PreviousReservedTrucks");



// spinner

String ownerID=finduser();
        spinnerRef.child(ownerID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> dateAndTimeList = new ArrayList<String>();

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String dateAndTime = areaSnapshot.getValue(String.class);
                    dateAndTimeList.add(dateAndTime);
                }

                 DateTimeSpinner = (Spinner) findViewById(R.id.spinner);
                ArrayAdapter<String> DateTimeAdapter = new ArrayAdapter<String>(ReserveTruck.this, android.R.layout.simple_spinner_item, dateAndTimeList);
                DateTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                DateTimeSpinner.setAdapter(DateTimeAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    // for date

        mDisplayDate=(TextView)findViewById(R.id.tvDate);

        Picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                 dialog= new DatePickerDialog(
                        ReserveTruck.this,
                        android.R.style.Theme_DeviceDefault_Dialog_NoActionBar,
                        mdateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mdateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                Log.d(TAG,"onDateSet: mm/dd/yyy"+month+"/"+day+"/"+year);
                date=month+"/"+day+"/"+year;
                mDisplayDate.setText(date);
               // dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
            }
        };
    }

    private String finduser() {

        Bundle b = getIntent().getExtras();
        final String fid = b.getString("id");
      return fid;
    }

    public void ReservationInfo(View view){
        //get public truck name + pic b3deen   ,,,, private    problem how to know its pub or priv to go to
        DatabaseReference appUse=FirebaseDatabase.getInstance().getReference("APPUsers");
        final String id=finduser();
        appUse.child(id).child("type").addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
                  //data snapchat is the whole database
                  String type=dataSnapshot.getValue(String.class).toString().trim();

                  if(type.equals("PublicOwner")){
                      DatabaseReference PF=FirebaseDatabase.getInstance().getReference("PublicFoodTruckOwner");
                        PF.child(id).child("fusername").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                TruckName=dataSnapshot.getValue(String.class).toString().trim();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                  }
                  else if(type.equals("PrivateOwner")){
                      DatabaseReference PF=FirebaseDatabase.getInstance().getReference("PrivateFoodTruckOwner");
                      PF.child(id).child("fusername").addListenerForSingleValueEvent(new ValueEventListener() {
                          @Override
                          public void onDataChange(DataSnapshot dataSnapshot) {
                              TruckName=dataSnapshot.getValue(String.class).toString().trim();
                          }

                          @Override
                          public void onCancelled(DatabaseError databaseError) {

                          }
                      });
                  }
         }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
        });


                // get selected value from dropdoenlist(spinner)
        final String TIME=DateTimeSpinner.getSelectedItem().toString().trim();
        final String DT=TIME+"  "+date;
        if(!TextUtils.isEmpty(notes)){
         notes=Notes.getText().toString().trim();}

if(!TextUtils.isEmpty(TIME)){
    if(date.equals(" ")){

        Toast.makeText(ReserveTruck.this, "الرجاء ادخال التاريخ ", Toast.LENGTH_SHORT).show();

    }else {
        reserveRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String id = user.getUid();//customer id is the same as rating id to make it easy to refer
                final String FID = finduser();
                final String CID = id;
                final String RID = reserveRef.push().getKey();  //random id for request  *malh da3i*

                if (x == 0 && y == 0) {
                    Toast.makeText(ReserveTruck.this, "الرجاء ادخال الموقع المراد حضور العربة له ", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.isEmpty(FID) && !TextUtils.isEmpty(CID) && !TextUtils.isEmpty(RID)) {


                    Request request = new Request(CID, FID, RID, DT, x, y,notes);
                    reserveRef.child(FID).child(RID).setValue(request);

                    PreviousReservedTrucks previousReservedTrucks=new PreviousReservedTrucks(pic,TruckName);
                    previousRef.child(CID).child(FID).setValue(previousReservedTrucks);
                    Toast.makeText(ReserveTruck.this, "تم الحجز,بانتظار موافقة صاحب حافلة الطعام", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    }

    else{
    Toast.makeText(ReserveTruck.this, "نأسف, لايوجد وقت للحجز", Toast.LENGTH_SHORT).show();
    finish();
}


    }



    public void canceleRequest(View view){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Do your Yes progress
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //Do your No progress
                        break;
                }
            }
        };
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setMessage("هل أنت متأكد من أنك تريد إغلاق صفحة الحجز ؟").setPositiveButton("نعم", dialogClickListener)
                .setNegativeButton("لا", dialogClickListener).show();

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
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
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

            auth.signOut();
            if(auth.getCurrentUser() == null){
                Toast.makeText(this , "تم تسجيل الدخول بنجاح" , Toast.LENGTH_SHORT).show();
                startActivity(new Intent (this , CustomerLogInPage.class));
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;

            }}

        return false;
    }
}
