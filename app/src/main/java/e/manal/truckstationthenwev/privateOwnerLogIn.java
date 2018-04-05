package e.manal.truckstationthenwev;

import android.app.ProgressDialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by manal on 2/10/2018.
 */

public class privateOwnerLogIn extends AppCompatActivity {
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = db.getReference();
    String type="hi";
    private DatabaseReference appUse=db.getReference("APPUsers");
    //Buttons  ;
    Button rigister ;



    //EditText username ,password ;
    private FirebaseAuth mAuth;
    private EditText textEmail;
    private EditText textPass;
    private Button btnLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView mTextReg ;
    private String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initAuthStateListener();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.private_login);

        rigister = (Button) findViewById(R.id.PrivateSignup);





        progressDialog = new ProgressDialog(this);
        textEmail = (EditText) findViewById(R.id.privteEmail);
        textPass = (EditText) findViewById(R.id.privatePass);
        btnLogin = (Button) findViewById(R.id.privteSignin);
        mAuth = FirebaseAuth.getInstance();

        FirebaseAuth.AuthStateListener mAuthListener;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
    } // End on create



    //  If NOT registerd

   public void goTOPrivatRegisterPage (View view ){
        Intent intent = new Intent(privateOwnerLogIn.this , GoTOPrivatRegisterPage.class );
        startActivity(intent);

    }




    private void initAuthStateListener (){


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };
    }



    // These two methods save user's login


    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }





    @Override
    public void onStop(){
        super.onStop();
        if (mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }





    private void doLogin() {

        final String email = textEmail.getText().toString().trim();
        password = textPass.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "فضلًا ادخل البريد الالكتروني", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "فضلًا ادخل كلمة المرور", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            progressDialog.setMessage("انتظر من فصلك, جاري تسجيل الدخول..");
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                // verfy if he/she a customer
                                String id=mAuth.getCurrentUser().getUid();
                                appUse.child(id).child("type").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        //data snapchat is the whole database
                                        type=dataSnapshot.getValue(String.class).toString().trim();

                                        if(type.equals("PrivateOwner")){
                                            Toast.makeText(privateOwnerLogIn.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(privateOwnerLogIn.this, Homepage.class);
                                            startActivity(intent);
                                        }
                                        else{
                                            Toast.makeText(privateOwnerLogIn.this, "الرجاء التأكد من نوع الدخول", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                            } // Singed in successfull
                            if (!task.isSuccessful()) {
                                Toast.makeText(privateOwnerLogIn.this, "خطأ في ادخال البريد الالكتروني أو كلمة المرور", Toast.LENGTH_LONG).show();
                            }



                        } // On Complete
                    }); // OnComplete listener

        } // Felids not empty

    } // Do login

    public void forgetPassword(View view){
        Intent intent = new Intent(privateOwnerLogIn.this , resetpassword.class );
        startActivity(intent);
    }


}
