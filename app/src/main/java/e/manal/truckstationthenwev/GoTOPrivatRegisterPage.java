package e.manal.truckstationthenwev;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class GoTOPrivatRegisterPage  extends AppCompatActivity {
    //new////////
    EditText password , phone , email , userName , Qusen ;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Button rigister ;
    DatabaseReference fdb;
    DatabaseReference UDB;
    private ProgressDialog mProgress;
    private Button Upload_image,AddP;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    // Folder path for Firebase Storage.
    String Storage_Path = "Trucks Images/";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.go_to_private_register_page);
        mAuth = FirebaseAuth.getInstance();
        password = (EditText) findViewById(R.id.pass);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        Qusen = (EditText) findViewById(R.id.qusin);
        userName = (EditText) findViewById(R.id.username);
        rigister = (Button) findViewById(R.id.singup);
        UDB=FirebaseDatabase.getInstance().getReference("APPUsers");
        //Start Image code
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("PublicFoodTruckOwner");
        Upload_image = (Button) findViewById(R.id.Pimage);

        // Adding click listener to Choose image button.

        //////////////////

        mProgress = new ProgressDialog(this);
        fdb = FirebaseDatabase.getInstance().getReference("PrivateFoodTruckOwner");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                   // Intent intent = new Intent( GoTOPrivatRegisterPage.this, privateOnerLogIn.class); ///*************هذي privateOnerLogIn  ي منال مو موجدة *******************
                  //  startActivity(intent);
                   // finish();
                }
            }
        };}//creat
    ///



    /////////

    public  void chickInfo(View view ){

        final String  pass =    password.getText().toString().trim();
        final String  phoneN =    phone.getText().toString().trim();
        final String  emailp =    email.getText().toString().trim();
        final String  username = userName.getText().toString().trim();
        final String  qusin =    Qusen.getText().toString().trim();


        if (!TextUtils.isEmpty(emailp) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(phoneN) && !TextUtils.isEmpty(qusin) && !TextUtils.isEmpty(username)) {

                if (pass.matches("^(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,10}$")) {
                    mProgress.setMessage("انتضر من فضلك....");
                    mProgress.show();


                    mAuth.createUserWithEmailAndPassword(emailp, pass)  // This method is inside firebaseauth class///
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() { // to tell me if the method create.. is done
                            // onComplete will be called when create method fineshed
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                mProgress.dismiss();  //End showing msg

                                if (task.isSuccessful()) { // If we registerd the user
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String uid = user.getUid();
                                    //  String id = fdb.push().getKey();
                                    PrivateFoodTruckOwner owner = new PrivateFoodTruckOwner(username, pass,
                                            emailp, Integer.parseInt(phoneN), qusin, uid, "",0,0);
                                    fdb.child(uid).setValue(owner);
                                    //to know the type of the user
                                    String type = "PrivateOwner";
                                    APPUsers user1 = new APPUsers(type);
                                    UDB.child(uid).setValue(user1);
                                    Toast.makeText(GoTOPrivatRegisterPage.this, "تم التسجل بنجاح!!", Toast.LENGTH_SHORT).show();
                                    // Intent intent = new Intent(GoTOCustomerRegisterPage.this, .class);
                                    // startActivity(intent);
                                    finish();
                                } else
                                    Toast.makeText(GoTOPrivatRegisterPage.this, "البريد الالكتروني  غير صحيح او مستخدم مسبقا", Toast.LENGTH_SHORT).show();

                            }
                        });


            } else {
                Toast.makeText(GoTOPrivatRegisterPage.this, "الرقم السري يجب ان يحتوي على رقم واحد على الاقل و حرف خاص واحد على الاقل وطوله ثمانية حروف ", Toast.LENGTH_SHORT).show();
            }
        }else

        {
            Toast.makeText(GoTOPrivatRegisterPage.this, "تأكد من تعبئة جميع الحقول", Toast.LENGTH_SHORT).show();
        }

    }

}
