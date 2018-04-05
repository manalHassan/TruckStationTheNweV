package e.manal.truckstationthenwev;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by manal on 2/10/2018.
 */
///
public class PublicOwnerRegistration extends AppCompatActivity {
    //new
    String address ;
    Context context;
    int PLACE_PICKER_REQUEST = 1;
    TextView location ;
    EditText password , phone , email , userName , Qusen ;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Button rigister ;
    DatabaseReference fdb;
    DatabaseReference fdb2;
    double x =0 , y =0 ;
    private ProgressDialog mProgress;
    //for imge
    private DatabaseReference mDatabase2;
    private EditText PName;
    private Button Upload_image,AddP;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    DatabaseReference databaser;
    DatabaseReference databas;
////////////
    ProgressDialog progressDialog ;
    DatabaseReference UDB;
    // Folder path for Firebase Storage.


    //////
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.go_to_public_register_page);
        context = this;
        mAuth = FirebaseAuth.getInstance();
        location = (TextView) findViewById(R.id.location);
        password = (EditText) findViewById(R.id.pass);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        Qusen = (EditText) findViewById(R.id.qusin);
        userName = (EditText) findViewById(R.id.username);
        rigister = (Button) findViewById(R.id.singup);
        //Start Image code
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("PublicFoodTruckOwner");
        databaser = FirebaseDatabase.getInstance().getReference().child("Menu");
        databas = FirebaseDatabase.getInstance().getReference().child("Category");

        Upload_image = (Button) findViewById(R.id.Pimage);
        //Pimage = (ImageView) view.findViewById(R.id.imageView);
        UDB=FirebaseDatabase.getInstance().getReference("APPUsers");
        ////////////////
        // Adding click listener to Choose image button.

        //////////////////

        mProgress = new ProgressDialog(this);
        // fdb = FirebaseDatabase.getInstance().getReference("PublicFoodTruckOwner");
        // fdb2=FirebaseDatabase.getInstance().getReference("truck");

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
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(PublicOwnerRegistration.this, publicOnerLogIn.class);
                    startActivity(intent);
                    finish();
                }
            }
        };}//creat


    /////

    ////


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);

                x = place.getLatLng().latitude;
                y = place.getLatLng().longitude;


            }
        }
    }
    public  void chickInfo(View view ){

        final String  pass =    password.getText().toString().trim();
        final String  phoneN =    phone.getText().toString().trim();
        final String  emailp =    email.getText().toString().trim();
        final String  username = userName.getText().toString().trim();
        final String  qusin =    Qusen.getText().toString().trim();



        if ( !TextUtils.isEmpty(emailp) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(phoneN) && !TextUtils.isEmpty(qusin)  && !TextUtils.isEmpty(username)) {
            if (pass.matches("^(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,10}$")) {
                mProgress.setMessage("انتظر من فضلك....");
                mProgress.show();



                mAuth.createUserWithEmailAndPassword(emailp , pass)  // This method is inside firebaseauth class
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() { // to tell me if the method create.. is done
                            // onComplete will be called when create method fineshed
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                mProgress.dismiss();  //End showing msg
                                try {
                                    if (task.isSuccessful()) { // If we registerd the user
                                        //Add
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        String uid = user.getUid();

                                        try {
                                            // truck t=new truck(username, finalUrl);
                                            PublicFoodTruckOwner owner = new PublicFoodTruckOwner(username, pass,
                                                    emailp, Integer.parseInt(phoneN), x, y, qusin,"" ,true,uid,0,0,false,false);
                                            databaseReference.child(uid).setValue(owner);
                                            // fdb2.child(t.getTruckname()).setValue(t);
                                            //to know the type of the user
                                            String type="PublicOwner";
                                            APPUsers user1=new APPUsers(type);
                                            UDB.child(uid).setValue(user1);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        //add menu plz don't delete it
                                        String mid =databaser.push().getKey();
                                        Menu menu =new Menu(uid,mid);
                                        databaser.child(uid).setValue(menu);
                                        //add menu plz don't delete it

                                     //   String cid =databas.push().getKey();
                                     //   Category c=  new Category ("test_cat", mid , cid);
                                     //   databas.child(mid).child(cid).setValue(c);

                                        //add menu plz don't delete it
                                        Toast.makeText(PublicOwnerRegistration.this, "تم التسجل بنجاح!!", Toast.LENGTH_SHORT).show();


                                        // Intent intent = new Intent(GoTOCustomerRegisterPage.this, .class);
                                        // startActivity(intent);
                                        finish();
                                    } else
                                        Toast.makeText(PublicOwnerRegistration.this, "البريد الالكتروني  غير صحيح او مستخدم مسبقا ", Toast.LENGTH_SHORT).show();
                                }catch (Exception e){ Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();}

                            }
                        });

                // If something goes wrong .



            } else
            {      Toast.makeText(PublicOwnerRegistration.this, "الرقم السري يجب ان يحتوي على رقم واحد على الاقل و حرف خاص واحد على الاقل وطوله ثمانية حروف ", Toast.LENGTH_SHORT).show();
            }}else {   Toast.makeText(PublicOwnerRegistration.this, "تأكد من تعبئة جميع الحقول", Toast.LENGTH_SHORT).show();
        }
    }//m
}
