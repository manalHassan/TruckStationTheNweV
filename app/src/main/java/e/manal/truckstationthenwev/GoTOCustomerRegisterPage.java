package e.manal.truckstationthenwev;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

/**
 * Created by manal on 2/9/2018.
 */

public class GoTOCustomerRegisterPage  extends AppCompatActivity {
    String address;
    Context context;
    int PLACE_PICKER_REQUEST = 1;
    TextView location ;
    EditText  password , phone , email , Lname , Fname ;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    Button rigister ;
    DatabaseReference fdb;
    DatabaseReference UDB;

    double x =0 , y =0 ;
    private ProgressDialog mProgress;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.go_to_customer_register_page);
        context = this;
        mAuth = FirebaseAuth.getInstance();
        location = (TextView) findViewById(R.id.location);
        password = (EditText) findViewById(R.id.pass);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        Lname = (EditText) findViewById(R.id.lastname);
        Fname = (EditText) findViewById(R.id.firatname);
        rigister = (Button) findViewById(R.id.singup);
        mProgress = new ProgressDialog(this);
        fdb = FirebaseDatabase.getInstance().getReference("Customer");
        UDB=FirebaseDatabase.getInstance().getReference("APPUsers");
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
                    Intent intent = new Intent(GoTOCustomerRegisterPage.this, CustomerLogInPage.class);
                    startActivity(intent);
                    finish();
                }
            }
        };}

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
    final String  emailc =    email.getText().toString().trim();
    final String  fname =    Fname.getText().toString().trim();
    final String  lname =    Lname.getText().toString().trim();



    if ( !TextUtils.isEmpty(emailc) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(phoneN)&& !TextUtils.isEmpty(fname) && !TextUtils.isEmpty(lname) ) {
        if (pass.matches("^(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,10}$")) {
            mProgress.setMessage("انتظر من فضلك....");
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(emailc, pass)  // This method is inside firebaseauth class
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() { // to tell me if the method create.. is done
                        // onComplete will be called when create method fineshed
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            mProgress.dismiss();  //End showing msg

                            if (task.isSuccessful()) { // If we registerd the user
///<<<<<<< Updated upstream
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String uid = user.getUid();
                                // String id = fdb.push().getKey();
                                Customer customer = new Customer(pass, emailc, fname, lname, Integer.parseInt(phoneN), x, y, uid);
                                fdb.child(uid).setValue(customer);

                                //to know the type of the user
                                String type = "Customer";
                                APPUsers user1 = new APPUsers(type);
                                UDB.child(uid).setValue(user1);
                                //Customer customer = new Customer(pass, emailc, fname, lname, Integer.parseInt(phoneN), x, y);
                                //fdb.setValue(customer);

                                Toast.makeText(GoTOCustomerRegisterPage.this, "تم التسجل بنجاح!!", Toast.LENGTH_SHORT).show();
                                // Intent intent = new Intent(GoTOCustomerRegisterPage.this, .class);
                                // startActivity(intent);
                                finish();
                            } else
                                Toast.makeText(GoTOCustomerRegisterPage.this, "البريد الالكتروني  غير صحيح او مستخدم مسبقا", Toast.LENGTH_SHORT).show();

                        }
                    });


  }

    else
        {      Toast.makeText(GoTOCustomerRegisterPage.this, "الرقم السري يجب ان يحتوي على رقم واحد على الاقل و حرف خاص واحد على الاقل وطوله ثمانية حروف ", Toast.LENGTH_SHORT).show();
        }}else {   Toast.makeText(GoTOCustomerRegisterPage.this, "تأكد من تعبئة جميع الحقول", Toast.LENGTH_SHORT).show();
    }

                }
}


